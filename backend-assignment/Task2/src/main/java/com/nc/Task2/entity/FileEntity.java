package com.nc.Task2.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "files")
@Getter
@Setter
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    private String fileType;

    private String filePath;

    private Instant uploadTime;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}