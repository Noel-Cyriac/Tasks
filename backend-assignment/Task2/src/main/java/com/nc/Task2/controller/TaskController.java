package com.nc.Task2.controller;

import com.nc.Task2.entity.Task;
import com.nc.Task2.entity.User;
import com.nc.Task2.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskRepository taskRepository;

    @PostMapping
    public Task create(@RequestBody Task task, @AuthenticationPrincipal User user){
        task.setUser(user);
        return taskRepository.save(task);
    }

    @GetMapping
    public List<Task> getAll(@AuthenticationPrincipal User user){
        return taskRepository.findByUser(user);
    }

    @PutMapping("/{id}")
    public Task update(@PathVariable Long id, @RequestBody Task updated){
        Task task = taskRepository.findById(id).orElseThrow();
        task.setTitle(updated.getTitle());
        task.setDescription(updated.getDescription());
        task.setCompleted(updated.isCompleted());
        return taskRepository.save(task);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(
            @PathVariable Long id,
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (!task.getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("You cannot delete this task");
        }

        taskRepository.delete(task);

        return ResponseEntity.ok("Task deleted successfully");
    }
}
