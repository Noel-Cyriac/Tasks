package com.nc.Task2.repository;

import com.nc.Task2.entity.Task;
import com.nc.Task2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    // Get all tasks of a specific user
    List<Task> findByUser(User user);

    // find by completion status
    List<Task> findByUserAndCompleted(User user, boolean completed);
}