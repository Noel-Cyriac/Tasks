package com.nc.Task2.controller;

import com.nc.Task2.dto.FileResponse;
import com.nc.Task2.entity.FileEntity;
import com.nc.Task2.entity.User;
import com.nc.Task2.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(
            @RequestParam("file") MultipartFile file,
            @AuthenticationPrincipal User user
    ) throws IOException {

        return ResponseEntity.ok(fileService.uploadFile(file, user));
    }

    @GetMapping("/list")
    public List<FileResponse> listFiles(@AuthenticationPrincipal User user) {
        return fileService.listFiles(user);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadFile(
            @PathVariable Long id,
            @AuthenticationPrincipal User user
    ) throws IOException {

        FileEntity file = fileService.getFileForDownload(id, user);

        byte[] data = Files.readAllBytes(Paths.get(file.getFilePath()));

        return ResponseEntity.ok()
                .header("Content-Type", file.getFileType())
                .header("Content-Disposition",
                        "attachment; filename=\"" + file.getFileName() + "\"")
                .body(data);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFile(
            @PathVariable Long id,
            @AuthenticationPrincipal User user
    ) throws IOException {

        return ResponseEntity.ok(fileService.deleteFile(id, user));
    }
}