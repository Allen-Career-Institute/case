package com.example.demo.service;

import com.example.demo.model.Case;
import com.example.demo.model.CaseStatus;
import com.example.demo.model.Task;
import com.example.demo.repo.CaseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CaseService {

    private final CaseRepository caseRepository;

    public CaseService(CaseRepository caseRepository) {
        this.caseRepository = caseRepository;
    }

    public List<Case> getAllCases() {
        return caseRepository.findAll();
    }

    public List<Case> getAllCasesForAssignee(String assigneeId) {
        return caseRepository.findByAssigneeId(assigneeId);
    }

    public List<Case> getAllCasesForStudent(String studentId) {
        return caseRepository.findByStudentId(studentId);
    }

    public Case getCaseById(Long caseId) {
        return caseRepository.findById(caseId).orElse(null);
    }

    public Case createCase(Case newCase) {
        return caseRepository.save(newCase);
    }

    public Case updateCase(Long caseId, Case updatedCase) {
        if (caseRepository.existsById(caseId)) {
            updatedCase.setId(caseId);
            return caseRepository.save(updatedCase);
        }
        return null;
    }

    public Task createTask(Long caseId, Task newTask) {
        Case existingCase = caseRepository.findById(caseId).orElse(null);
        if (existingCase != null) {
            newTask.setLinkedCase(existingCase);
            existingCase.getTasks().add(newTask);
            caseRepository.save(existingCase);
            return newTask;
        }
        return null;
    }
    public boolean deleteCase(Long caseId) {
        if (caseRepository.existsById(caseId)) {
            caseRepository.deleteById(caseId);
            return true;
        }
        return false;
    }

    public Case updateCaseStatus(Long caseId, String status, String comment) {
        Case existingCase = caseRepository.findById(caseId).orElse(null);
        if (existingCase != null) {
            existingCase.setStatus(CaseStatus.valueOf(status));
            existingCase.setResolutionComment(comment);
            return caseRepository.save(existingCase);
        }
        return null;
    }

    public Case reassignCase(Long caseId, String assigneeId) {
        Case existingCase = caseRepository.findById(caseId).orElse(null);
        if (existingCase != null) {
            existingCase.setAssigneeId(assigneeId);
            return caseRepository.save(existingCase);
        }
        return null;
    }

    public List<Case> getCasesByStatus(String status) {
        return caseRepository.findByStatus(CaseStatus.valueOf(status));
    }

}