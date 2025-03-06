package com.example.demo.resource;

import com.example.demo.model.Task;
import com.example.demo.model.TaskStatus;
import com.example.demo.service.TaskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    @Test
    void getAllTasksReturnsListOfTasks() {
        List<Task> tasks = Arrays.asList(new Task(), new Task());
        when(taskService.getAllTasks()).thenReturn(tasks);

        List<Task> result = taskController.getAllTasks();

        assertThat(result).hasSize(2);
        assertThat(result).isEqualTo(tasks);
    }

    @Test
    void getTaskByIdReturnsTaskWhenFound() {
        Task task = new Task();
        task.setId(1L);
        when(taskService.getTaskById(1L)).thenReturn(task);

        ResponseEntity<Task> response = taskController.getTaskById(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getId()).isEqualTo(1L);
    }

    @Test
    void getTaskByIdReturnsNotFoundWhenTaskDoesNotExist() {
        when(taskService.getTaskById(1L)).thenReturn(null);

        ResponseEntity<Task> response = taskController.getTaskById(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
        verify(taskService).getTaskById(1L);   }

    @Test
    void getTasksByCaseReturnsListOfTasks() {
        List<Task> tasks = Arrays.asList(new Task(), new Task());
        when(taskService.getTasksByCase(1L)).thenReturn(tasks);

        List<Task> result = taskController.getTasksByCase(1L);

        assertThat(result).hasSize(2);
    }

    @Test
    void updateTaskReturnsUpdatedTask() {
        Task updatedTask = new Task();
        updatedTask.setId(1L);
        when(taskService.updateTask(1L, updatedTask)).thenReturn(updatedTask);

        ResponseEntity<Task> response = taskController.updateTask(1L, updatedTask);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getId()).isEqualTo(1L);
    }

    @Test
    void deleteTaskReturnsOkWhenSuccessful() {
        when(taskService.deleteTask(1L)).thenReturn(true);

        ResponseEntity<Void> response = taskController.deleteTask(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void deleteTaskReturnsNotFoundWhenTaskDoesNotExist() {
        when(taskService.deleteTask(1L)).thenReturn(false);

        ResponseEntity<Void> response = taskController.deleteTask(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void updateTaskStatusReturnsUpdatedTask() {
        Task updatedTask = new Task();
        updatedTask.setStatus(TaskStatus.COMPLETED);
        when(taskService.updateTaskStatus(1L, "COMPLETED")).thenReturn(updatedTask);

        ResponseEntity<Task> response = taskController.updateTaskStatus(1L, "COMPLETED");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getStatus()).isEqualTo(TaskStatus.COMPLETED);
    }

    @Test
    void reassignTaskReturnsUpdatedTask() {
        Task reassignedTask = new Task();
        reassignedTask.setAssigneeId("user2");
        when(taskService.reassignTask(1L, "user2")).thenReturn(reassignedTask);

        ResponseEntity<Task> response = taskController.reassignTask(1L, "user2");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getAssigneeId()).isEqualTo("user2");
    }

    @Test
    void getTasksByAssigneeReturnsListOfTasks() {
        List<Task> tasks = Arrays.asList(new Task(), new Task());
        when(taskService.getTasksByAssignee("user1")).thenReturn(tasks);

        List<Task> result = taskController.getTasksByAssignee("user1");

        assertThat(result).hasSize(2);
    }

    @Test
    void getTasksByStatusReturnsListOfTasks() {
        List<Task> tasks = Arrays.asList(new Task(), new Task());
        when(taskService.getTasksByStatus("OPEN")).thenReturn(tasks);

        List<Task> result = taskController.getTasksByStatus("OPEN");

        assertThat(result).hasSize(2);
    }
}