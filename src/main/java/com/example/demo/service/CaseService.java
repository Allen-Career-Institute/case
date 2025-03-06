package com.example.demo.service;

import com.example.demo.model.Case;
import com.example.demo.model.Task;
import com.example.demo.repo.CaseRepository;
import com.example.demo.repo.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class CaseService {

    private final CaseRepository caseRepository;
    private final TaskRepository taskRepository;

    public CaseService(CaseRepository caseRepository, TaskRepository taskRepository) {
        this.caseRepository = caseRepository;
        this.taskRepository = taskRepository;
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
        newTask.setLinkedCase(aCase);
        taskRepository.save(newTask);
        if(aCase.getTasks() == null || aCase.getTasks().isEmpty()) {
            aCase.setTasks(Arrays.asList(newTask));
            return newTask;
        }
        aCase.getTasks().add(newTask);
        caseRepository.save(aCase);
        return newTask;
    }

    public Case getAllCases(Long caseId) {
        return caseRepository.getReferenceById(caseId);
    }
}
