package com.example.demo.service;

import com.example.demo.model.Case;
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
        case1.setAssigneeID(newCase.getAssigneeID());
        case1.setCategory(newCase.getCategory());
        case1.setDescription(newCase.getDescription());
        case1.setStatus(newCase.getStatus());
        case1.setTitle(newCase.getTitle());
        return caseRepository.save(case1);


    }
}
