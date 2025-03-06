package com.example.demo.service;

import com.example.demo.model.Case;
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

    public Case createCase(Case newCase) {
        return caseRepository.save(newCase);
    }

    public Case update(Long id, Case newCase) {
        Case case1 = caseRepository.getReferenceById(id);
        case1.setAssigneeId(newCase.getAssigneeId());
        case1.setCategory(newCase.getCategory());
        case1.setDescription(newCase.getDescription());
        case1.setStatus(newCase.getStatus());
        case1.setTitle(newCase.getTitle());
        case1.setUrmStudentId(newCase.getUrmStudentId());
        case1.setCategory(newCase.getCategory());
        return caseRepository.save(case1);
    }

    public List<Task> getAllTasks(Long caseId) {
        return caseRepository.getReferenceById(caseId).getTasks();
    }

    public List<Case> getAllCasesForStudent(String studentId) {
        return caseRepository.findByStudentId(studentId);
    }

    public List<Case> getAllCasesForAssignee(String assigneeId) {
        return caseRepository.findByAssignee(assigneeId);
    }

    public Task createTask(Long caseId, Task newTask) {
        Case aCase = caseRepository.getReferenceById(caseId);
        aCase.getTasks().add(newTask);
        caseRepository.save(aCase);
        return aCase.getTasks().get(aCase.getTasks().size() - 1);
    }
}
