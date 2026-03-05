package com.nc.Task2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@ControllerAdvice
public class GlobalExceptionHandler {

    // ---------------- File size limit ----------------
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<String> handleMaxSizeException(MaxUploadSizeExceededException ex) {
        return ResponseEntity.status(org.springframework.http.HttpStatusCode.valueOf(413))
                .body("File too large! Maximum allowed size is 5MB.");
    }

    // ---------------- Invalid file type ----------------
    @ExceptionHandler(FileTypeNotAllowedException.class)
    public ResponseEntity<String> handleFileType(FileTypeNotAllowedException ex) {
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE) // 415
                .body(ex.getMessage());
    }

    // ---------------- File not found ----------------
    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<String> handleFileNotFound(FileNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND) // 404
                .body(ex.getMessage());
    }

    // ---------------- File access denied ----------------
    @ExceptionHandler(FileAccessDeniedException.class)
    public ResponseEntity<String> handleFileAccessDenied(FileAccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN) // 403
                .body(ex.getMessage());
    }

    // ---------------- Task not found ----------------
    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<String> handleTaskNotFound(TaskNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND) // 404
                .body(ex.getMessage());
    }

    // ---------------- Task access denied ----------------
    @ExceptionHandler(TaskAccessDeniedException.class)
    public ResponseEntity<String> handleTaskAccessDenied(TaskAccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN) // 403
                .body(ex.getMessage());
    }

    // ---------------- Other runtime exceptions ----------------
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST) // 400
                .body(ex.getMessage());
    }
}