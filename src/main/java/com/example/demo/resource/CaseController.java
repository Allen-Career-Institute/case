package com.example.demo.resource;


import com.example.demo.model.Case;
import com.example.demo.service.CaseService;
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

    @PostMapping
    public Case createCase(@RequestBody Case newCase) {
        return caseService.createCase(newCase);
    }
}
