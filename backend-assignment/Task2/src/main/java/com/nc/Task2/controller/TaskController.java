package com.nc.Task2.controller;

import com.nc.Task2.dto.TaskResponse;
import com.nc.Task2.entity.Task;
import com.nc.Task2.entity.User;
import com.nc.Task2.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public TaskResponse create(
            @RequestBody Task task,
            @AuthenticationPrincipal User user
    ) {
        return taskService.create(task, user);
    }

    @GetMapping
    public List<TaskResponse> getAll(
            @AuthenticationPrincipal User user
    ) {
        return taskService.getAll(user);
    }

    @PutMapping("/{id}")
    public TaskResponse update(
            @PathVariable Long id,
            @RequestBody Task updated,
            @AuthenticationPrincipal User user
    ) {
        return taskService.update(id, updated, user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(
            @PathVariable Long id,
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(taskService.delete(id, user));
    }
}