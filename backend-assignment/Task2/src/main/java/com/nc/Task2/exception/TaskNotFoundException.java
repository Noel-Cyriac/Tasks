package com.nc.Task2.exception;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(String msg) { super(msg); }
}