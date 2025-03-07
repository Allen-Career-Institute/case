package com.example.demo.resource;

import com.example.demo.model.Case;
import com.example.demo.model.CaseStatus;
import com.example.demo.service.CaseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CaseControllerTest {

    @Mock
    private CaseService caseService;

    @InjectMocks
    private CaseController caseController;

    @Test
    void deleteCaseReturnsOkWhenSuccessful() {
        when(caseService.deleteCase(1L)).thenReturn(true);
        ResponseEntity<Void> response = caseController.deleteCase(1L);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void deleteCaseReturnsNotFoundWhenCaseDoesNotExist() {
        when(caseService.deleteCase(1L)).thenReturn(false);
        ResponseEntity<Void> response = caseController.deleteCase(1L);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void updateCaseStatusReturnsUpdatedCase() {
        Case updatedCase = new Case();
        updatedCase.setStatus(CaseStatus.valueOf("COMPLETED"));
        when(caseService.updateCaseStatus(1L, "COMPLETED", "comment")).thenReturn(updatedCase);

        ResponseEntity<Case> response = caseController.updateCaseStatus(1L, "COMPLETED","comment");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getStatus()).isEqualTo(CaseStatus.valueOf("COMPLETED"));
    }

    @Test
    void reassignCaseReturnsUpdatedCase() {
        Case reassignedCase = new Case();
        reassignedCase.setAssigneeId("user2");
        when(caseService.reassignCase(1L, "user2")).thenReturn(reassignedCase);

        ResponseEntity<Case> response = caseController.reassignCase(1L, "user2");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getAssigneeId()).isEqualTo("user2");
    }

    @Test
    void getCasesByStatusReturnsFilteredCases() {
        List<Case> cases = Arrays.asList(new Case(), new Case());
        when(caseService.getCasesByStatus("OPEN")).thenReturn(cases);

        List<Case> result = caseController.getCasesByStatus("OPEN");

        assertThat(result).hasSize(2);
    }
}