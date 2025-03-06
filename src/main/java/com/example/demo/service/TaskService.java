package com.example.demo.service;

import com.example.demo.model.Task;
import com.example.demo.repo.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task createTask(Task newTask) { return taskRepository.save(newTask); }
}
