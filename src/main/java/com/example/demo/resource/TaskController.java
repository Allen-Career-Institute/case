package com.example.demo.resource;

import com.example.demo.model.Task;
import com.example.demo.repo.TaskRepository;
import com.example.demo.service.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;
    private final TaskRepository taskRepository;

    public TaskController(TaskService taskService, TaskRepository taskRepository) {
        this.taskService = taskService;
        this.taskRepository = taskRepository;
    }

    @GetMapping
    public List<Task> getAllTasksByCaseId(@RequestParam String caseId)
    {
        return taskRepository.findByLinkedCase(caseId);
    };

    @PostMapping
    public Task createTask(@RequestBody Task newTask) { return taskService.createTask(newTask); }

}
