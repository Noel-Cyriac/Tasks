package com.nc.Task2.exception;

public class FileAccessDeniedException extends RuntimeException {
    public FileAccessDeniedException(String message) { super(message); }
}
