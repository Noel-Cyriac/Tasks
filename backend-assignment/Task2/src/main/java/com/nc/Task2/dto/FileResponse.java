package com.nc.Task2.dto;

import java.time.Instant;

public record FileResponse(
        Long id,
        String fileName,
        String filePath,
        String fileType,
        Instant uploadTime,
        Long userId
) {}