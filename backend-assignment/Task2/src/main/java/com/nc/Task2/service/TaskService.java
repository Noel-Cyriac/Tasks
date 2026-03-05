package com.nc.Task2.service;

import com.nc.Task2.dto.TaskResponse;
import com.nc.Task2.entity.Task;
import com.nc.Task2.entity.User;
import com.nc.Task2.exception.TaskAccessDeniedException;
import com.nc.Task2.exception.TaskNotFoundException;
import com.nc.Task2.repository.TaskRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    // ---------------- CREATE ----------------
    @Transactional
    public TaskResponse create(Task task, User user) {
        task.setUser(user);
        Task savedTask = taskRepository.save(task);
        return new TaskResponse(savedTask);
    }

    // ---------------- GET ALL ----------------
    public List<TaskResponse> getAll(User user) {
        return taskRepository.findByUser(user)
                .stream()
                .map(TaskResponse::new)
                .toList();
    }

    // ---------------- UPDATE ----------------
    @Transactional
    public TaskResponse update(Long id, Task updated, User user) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));

        if (!task.getUser().getId().equals(user.getId())) {
            throw new TaskAccessDeniedException("You cannot update this task");
        }

        if (updated.getTitle() != null) task.setTitle(updated.getTitle());
        if (updated.getDescription() != null) task.setDescription(updated.getDescription());
        if (updated.getCompleted() != null) task.setCompleted(updated.getCompleted());

        Task saved = taskRepository.save(task);
        return new TaskResponse(saved);
    }

    // ---------------- DELETE ----------------
    @Transactional
    public String delete(Long id, User user) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));

        if (!task.getUser().getId().equals(user.getId())) {
            throw new TaskAccessDeniedException("You cannot delete this task");
        }

        taskRepository.delete(task);

        return "Task deleted successfully";
    }
}