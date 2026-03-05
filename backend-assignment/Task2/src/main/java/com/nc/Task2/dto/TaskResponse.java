package com.nc.Task2.dto;

import com.nc.Task2.entity.Task;
import lombok.Data;

@Data
public class TaskResponse {

    private Long id;
    private String title;
    private String description;
    private Boolean completed;
    private Long userId;

    // Constructor mapping from Task entity
    public TaskResponse(Task task) {
        this.id = task.getId();
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.completed = task.getCompleted();
        this.userId = task.getUser().getId();
    }

    public TaskResponse() {}
}