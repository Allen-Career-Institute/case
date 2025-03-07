package com.example.demo.resource;

import com.example.demo.model.Case;
import com.example.demo.model.Task;
import com.example.demo.pojo.StatusComment;
import com.example.demo.service.CaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<Case> getCasesByAssignee(@RequestParam String assigneeId) {
        return caseService.getAllCasesForAssignee(assigneeId);
    }

    @GetMapping("/students")
    public List<Case> getCasesByStudent(@RequestParam String studentId) {
        return caseService.getAllCasesForStudent(studentId);
    }

    @GetMapping("/{caseId}")
    public Case getCaseById(@PathVariable("caseId") Long caseId) {
        return caseService.getCaseById(caseId);
    }

    @PostMapping
    public Case createCase(@RequestBody Case newCase) {
        return caseService.createCase(newCase);
    }

    @PutMapping("/{caseId}")
    public Case updateCase(@PathVariable Long caseId, @RequestBody Case updatedCase) {
        return caseService.updateCase(caseId, updatedCase);
    }

    @PostMapping("/{caseId}/tasks")
    public Task createTaskForCase(@PathVariable Long caseId, @RequestBody Task newTask) {
        return caseService.createTask(caseId, newTask);
    }


    @DeleteMapping("/{caseId}")
    public ResponseEntity<Void> deleteCase(@PathVariable Long caseId) {
        return caseService.deleteCase(caseId)
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }

    @PatchMapping("/{caseId}/status")
    public ResponseEntity<Case> updateCaseStatus(
            @PathVariable Long caseId, @RequestBody StatusComment statusComment) {
        return ResponseEntity.ok(caseService.updateCaseStatus(caseId, statusComment.getStatus(), statusComment.getComment()));
    }

    @PatchMapping("/{caseId}/assignee")
    public ResponseEntity<Case> reassignCase(
            @PathVariable Long caseId,
            @RequestParam String assigneeId) {
        return ResponseEntity.ok(caseService.reassignCase(caseId, assigneeId));
    }

    @GetMapping("/status/{status}")
    public List<Case> getCasesByStatus(@PathVariable String status) {
        return caseService.getCasesByStatus(status);
    }
}
