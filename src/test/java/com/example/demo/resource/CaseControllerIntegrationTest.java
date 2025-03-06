package com.example.demo.resource;

import com.example.demo.model.Case;
import com.example.demo.model.CaseStatus;
import com.example.demo.repo.CaseRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CaseControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CaseRepository caseRepository;

    @BeforeEach
    void setUp() {
        caseRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        caseRepository.deleteAll();
    }

    @Test
    void deleteCaseReturnsNotFoundWhenCaseDoesNotExist() throws Exception {
        mockMvc.perform(delete("/cases/{caseId}", 999L))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateCaseStatusReturnsUpdatedCase() throws Exception {
        Case caseEntity = new Case();
        caseEntity.setStatus(CaseStatus.valueOf("OPEN"));
        caseEntity = caseRepository.save(caseEntity);

        mockMvc.perform(patch("/cases/{caseId}/status", caseEntity.getId())
                .param("status", "COMPLETED"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("COMPLETED"));
    }

    @Test
    void reassignCaseReturnsUpdatedCase() throws Exception {
        Case caseEntity = new Case();
        caseEntity.setAssigneeId("user1");
        caseEntity = caseRepository.save(caseEntity);

        mockMvc.perform(patch("/cases/{caseId}/assignee", caseEntity.getId())
                .param("assigneeId", "user2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.assigneeId").value("user2"));
    }

    @Test
    void getCasesByStatusReturnsFilteredCases() throws Exception {
        Case caseEntity1 = new Case();
        caseEntity1.setStatus(CaseStatus.valueOf("OPEN"));
        caseRepository.save(caseEntity1);

        Case caseEntity2 = new Case();
        caseEntity2.setStatus(CaseStatus.valueOf("OPEN"));
        caseRepository.save(caseEntity2);

        mockMvc.perform(get("/cases/status/{status}", "OPEN"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }
}