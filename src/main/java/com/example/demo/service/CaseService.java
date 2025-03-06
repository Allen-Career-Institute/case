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
}
