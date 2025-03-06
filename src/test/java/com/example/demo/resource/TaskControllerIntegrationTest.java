//package com.example.demo.resource;
//
//import com.example.demo.model.Case;
//import com.example.demo.model.CaseStatus;
//import com.example.demo.model.Task;
//import com.example.demo.repo.CaseRepository;
//import com.example.demo.repo.TaskRepository;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.RequestBuilder;
//
//import java.time.LocalDateTime;
//import java.time.ZoneOffset;
//import java.util.List;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//@SpringBootTest
//@AutoConfigureMockMvc
//@ActiveProfiles("test")
//class TaskControllerIntegrationTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private TaskRepository taskRepository;
//
//    @Autowired
//    private CaseRepository caseRepository;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @BeforeEach
//    void setUp() {
//        taskRepository.deleteAll();
//        caseRepository.deleteAll();
//    }
//
//    @AfterEach
//    void tearDown() {
//        taskRepository.deleteAll();
//        caseRepository.deleteAll();
//    }
//
//    @Test
//    void getAllTasksReturnsListOfTasks() throws Exception {
//        Case testCase = createAndSaveCase();
//        createAndSaveTask("Task 1", testCase);
//        createAndSaveTask("Task 2", testCase);
//
//        mockMvc.perform(get("/tasks"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$").isArray())
//                .andExpect(jsonPath("$.length()").value(2));
//    }
//
//    @Test
//    void getTaskByIdReturnsTaskWhenFound() throws Exception {
//        Case testCase = createAndSaveCase();
//        Task task = createAndSaveTask("Test Task", testCase);
//
//        mockMvc.perform(get("/tasks/{taskId}", task.getId()))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(task.getId()))
//                .andExpect(jsonPath("$.transcript").value("Test Task"))
//                .andExpect(jsonPath("$.linkedCase.id").value(testCase.getId()));
//    }
//
//    @Test
//    void getTaskByIdReturnsNotFoundWhenTaskDoesNotExist() throws Exception {
//        mockMvc.perform(get("/tasks/{taskId}", 999L))
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    void createTaskReturnsCreatedTask() throws Exception {
//        Case testCase = createAndSaveCase();
//        Task task = createTask("New Task", testCase);
//
//        mockMvc.perform(post("/tasks")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(task)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.transcript").value("New Task"))
//                .andExpect(jsonPath("$.linkedCase.id").value(testCase.getId()))
//                .andExpect(jsonPath("$.assigneeId").value("user1"));
//    }
//
//    @Test
//    void createTaskWithInvalidCaseReturnsBadRequest() throws Exception {
//        Task task = new Task();
//        task.setTranscript("Invalid Task");
//
//        mockMvc.perform(post("/tasks")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(task)))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void updateTaskReturnsUpdatedTask() throws Exception {
//        Case testCase = createAndSaveCase();
//        Task task = createAndSaveTask("Original Task", testCase);
//        task.setDescription("Updated Description");
//
//        mockMvc.perform(put("/tasks/{taskId}", task.getId())
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(task)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.description").value("Updated Description"))
//                .andExpect(jsonPath("$.transcript").value("Original Task"));
//    }
//
//    @Test
//    void updateTaskReturnsNotFoundWhenTaskDoesNotExist() throws Exception {
//        Case testCase = createAndSaveCase();
//        Task task = createTask("Test Task", testCase);
//
//        mockMvc.perform(put("/tasks/{taskId}", 999L)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(task)))
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    void deleteTaskReturnsNoContent() throws Exception {
//        Case testCase = createAndSaveCase();
//        Task task = createAndSaveTask("Task to Delete", testCase);
//
//        mockMvc.perform(delete("/tasks/{taskId}", task.getId()))
//                .andExpect(status().isNoContent());
//    }
//
//    @Test
//    void getTasksByAssigneeReturnsFilteredTasks() throws Exception {
//        Case testCase = createAndSaveCase();
//        createAndSaveTask("Task 1", testCase, "user1");
//        createAndSaveTask("Task 2", testCase, "user1");
//        createAndSaveTask("Task 3", testCase, "user2");
//
//        mockMvc.perform(get("/tasks/assignee")
//                .param("assigneeId", "user1"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$").isArray())
//                .andExpect(jsonPath("$.length()").value(2))
//                .andExpect(jsonPath("$[0].assigneeId").value("user1"));
//    }
//
//    private Case createAndSaveCase() {
//        Case aCase = new Case();
//        aCase.setStatus(CaseStatus.OPEN);
//        return caseRepository.save(aCase);
//    }
//
//    private Task createTask(String title, Case linkedCase) {
//        Task task = new Task();
//        task.setTranscript(title);
//        task.setDescription("Test Description");
//        task.setDuration(LocalDateTime.now(ZoneOffset.of("+05:30")).toEpochSecond(ZoneOffset.of("+05:30")));
//        task.setAssigneeId("user1");
//        task.setLinkedCase(linkedCase);
//        return task;
//    }
//
//    private Task createAndSaveTask(String title, Case linkedCase) {
//        return createAndSaveTask(title, linkedCase, "user1");
//    }
//
//    private Task createAndSaveTask(String title, Case linkedCase, String assigneeId) {
//        Task task = createTask(title, linkedCase);
//        task.setAssigneeId(assigneeId);
//        return taskRepository.save(task);
//    }
//}