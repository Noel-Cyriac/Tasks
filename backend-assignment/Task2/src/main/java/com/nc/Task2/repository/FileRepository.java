package com.nc.Task2.repository;

import com.nc.Task2.entity.FileEntity;
import com.nc.Task2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FileRepository extends JpaRepository<FileEntity, Long> {

    // Get all files uploaded by a specific user
    List<FileEntity> findByUser(User user);

    // Find a file by id and user
    Optional<FileEntity> findByIdAndUser(Long id, User user);
}