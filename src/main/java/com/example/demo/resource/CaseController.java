package com.example.demo.resource;


import com.example.demo.model.Case;
import com.example.demo.model.Task;
import com.example.demo.service.CaseService;
import jakarta.websocket.server.PathParam;
import org.springframework.web.bind.annotation.*;

import java.security.PublicKey;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cases")
public class CaseController {

    private final CaseService caseService;

    public CaseController(CaseService caseService) {
        this.caseService = caseService;
    }

    @GetMapping
    public List<Case> getAllCases() {
        return caseService.getAllCases();
    }

    @GetMapping("/assignee")
    public List<Case> getCaseForAssigneeDetails(@RequestParam String assigneeId) {
        return caseService.getAllCasesForAssignee(assigneeId);
    }

    @GetMapping("/students")
    public List<Case> getStudentDetails(@RequestParam String studentId) {
        return caseService.getAllCasesForStudent(studentId);
    }

    @GetMapping("/{caseId}")
    public List<Task> getAllTasks(@PathVariable("caseId") Long caseId) { return caseService.getAllTasks(caseId);}

    @PostMapping
    public Case createCase(@RequestBody Case newCase) {
        return caseService.createCase(newCase);
    }

    @PutMapping("/{caseId}")
    public Case addAssignee(@PathVariable Long caseId, @RequestBody Case newCase) {
        return caseService.update(caseId, newCase);
    }

    @PostMapping("/{caseId}/tasks")
    public Task createTask(@PathVariable Long caseId, @RequestBody Task newTask) {
        return caseService.createTask(caseId, newTask);
    }


}
