package com.nc.Task2.service;

import com.nc.Task2.dto.FileResponse;
import com.nc.Task2.entity.FileEntity;
import com.nc.Task2.entity.User;
import com.nc.Task2.exception.FileAccessDeniedException;
import com.nc.Task2.exception.FileNotFoundException;
import com.nc.Task2.exception.FileTypeNotAllowedException;
import com.nc.Task2.repository.FileRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;

    private final String uploadDir = System.getProperty("user.dir") + "/uploads";

    // ---------------- UPLOAD ----------------
    @Transactional
    public String uploadFile(MultipartFile file, User user) throws IOException {

        String originalName = file.getOriginalFilename();

        if (originalName == null || !originalName.matches(".*\\.(pdf|png|jpg)$")) {
            throw new FileTypeNotAllowedException("Only PDF, PNG, JPG files are allowed.");
        }

        Files.createDirectories(Paths.get(uploadDir));

        String uniqueName = UUID.randomUUID() + "_" + originalName;
        String filePath = uploadDir + "/" + uniqueName;

        file.transferTo(new File(filePath));

        FileEntity fileEntity = new FileEntity();
        fileEntity.setFileName(originalName);
        fileEntity.setFileType(file.getContentType());
        fileEntity.setFilePath(filePath);
        fileEntity.setUploadTime(Instant.now());
        fileEntity.setUser(user);

        fileRepository.save(fileEntity);

        return "File uploaded successfully: " + uniqueName;
    }

    // ---------------- LIST ----------------
    public List<FileResponse> listFiles(User user) {

        return fileRepository.findByUser(user)
                .stream()
                .map(f -> new FileResponse(
                        f.getId(),
                        f.getFileName(),
                        f.getFilePath(),
                        f.getFileType(),
                        f.getUploadTime(),
                        f.getUser().getId()
                ))
                .toList();
    }

    // ---------------- GENERATE DOWNLOAD LINK ----------------
    public String generateDownloadLink(Long id, User user) {

        FileEntity file = getFileForDownload(id, user);

        long expiry = System.currentTimeMillis() + (5 * 60 * 1000); // 5 minutes

        String token = Base64.getEncoder()
                .encodeToString((file.getId() + ":" + expiry).getBytes());

        return "http://localhost:8080/api/files/temp-download?token=" + token;
    }

    // ---------------- GET FILE FOR DOWNLOAD ----------------
    public FileEntity getFileForDownload(Long id, User user) {

        FileEntity file = fileRepository.findById(id)
                .orElseThrow(() -> new FileNotFoundException("File not found"));

        if (!file.getUser().getId().equals(user.getId())) {
            throw new FileAccessDeniedException("Not allowed to access this file");
        }

        return file;
    }

    // ---------------- GET FILE BY ID ----------------
    public FileEntity getFileById(Long id) {

        return fileRepository.findById(id)
                .orElseThrow(() -> new FileNotFoundException("File not found"));
    }

    // ---------------- DELETE ----------------
    @Transactional
    public String deleteFile(Long id, User user) throws IOException {

        FileEntity file = fileRepository.findById(id)
                .orElseThrow(() -> new FileNotFoundException("File not found"));

        if (!file.getUser().getId().equals(user.getId())) {
            throw new FileAccessDeniedException("Not allowed to delete this file");
        }

        Files.deleteIfExists(Paths.get(file.getFilePath()));

        fileRepository.delete(file);

        return "File deleted successfully";
    }
}