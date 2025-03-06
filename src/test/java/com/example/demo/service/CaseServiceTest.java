package com.example.demo.service;
import com.example.demo.model.Case;
import com.example.demo.model.Task;
import com.example.demo.repo.CaseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CaseServiceTest {

    @Mock
    private CaseRepository caseRepository;

    @InjectMocks
    private CaseService caseService;

    @Test
    void returnsAllCasesFromRepository() {
        List<Case> cases = Arrays.asList(new Case(), new Case());
        when(caseRepository.findAll()).thenReturn(cases);

        List<Case> result = caseService.getAllCases();

        assertThat(result).isEqualTo(cases);
    }

    @Test
    void returnsAllCasesForGivenAssigneeId() {
        String assigneeId = "A123";
        List<Case> cases = Arrays.asList(new Case(), new Case());
        when(caseRepository.findByAssigneeId(assigneeId)).thenReturn(cases);

        List<Case> result = caseService.getAllCasesForAssignee(assigneeId);

        assertThat(result).isEqualTo(cases);
    }

    @Test
    void returnsAllCasesForGivenStudentId() {
        String studentId = "S123";
        List<Case> cases = Arrays.asList(new Case(), new Case());
        when(caseRepository.findByStudentId(studentId)).thenReturn(cases);

        List<Case> result = caseService.getAllCasesForStudent(studentId);

        assertThat(result).isEqualTo(cases);
    }

    @Test
    void returnsCaseForValidId() {
        Long caseId = 1L;
        Case expectedCase = new Case();
        when(caseRepository.findById(caseId)).thenReturn(Optional.of(expectedCase));

        Case result = caseService.getCaseById(caseId);

        assertThat(result).isEqualTo(expectedCase);
    }

    @Test
    void returnsNullForInvalidCaseId() {
        Long caseId = 999L;
        when(caseRepository.findById(caseId)).thenReturn(Optional.empty());

        Case result = caseService.getCaseById(caseId);

        assertThat(result).isNull();
    }

    @Test
    void savesNewCaseToRepository() {
        Case newCase = new Case();
        when(caseRepository.save(newCase)).thenReturn(newCase);

        Case result = caseService.createCase(newCase);

        assertThat(result).isEqualTo(newCase);
    }

    @Test
    void updatesExistingCase() {
        Long caseId = 1L;
        Case updatedCase = new Case();
        when(caseRepository.existsById(caseId)).thenReturn(true);
        when(caseRepository.save(updatedCase)).thenReturn(updatedCase);

        Case result = caseService.updateCase(caseId, updatedCase);

        assertThat(result).isEqualTo(updatedCase);
        verify(caseRepository).save(updatedCase);
    }

    @Test
    void returnsNullWhenUpdatingNonExistentCase() {
        Long caseId = 999L;
        Case updatedCase = new Case();
        when(caseRepository.existsById(caseId)).thenReturn(false);

        Case result = caseService.updateCase(caseId, updatedCase);

        assertThat(result).isNull();
        verify(caseRepository, never()).save(any());
    }

    @Test
    void createsAndLinksNewTaskToExistingCase() {
        Long caseId = 1L;
        Case existingCase = new Case();
        existingCase.setTasks(new ArrayList<>());
        Task newTask = new Task();
        when(caseRepository.findById(caseId)).thenReturn(Optional.of(existingCase));

        Task result = caseService.createTask(caseId, newTask);

        assertThat(result).isEqualTo(newTask);
        assertThat(existingCase.getTasks()).contains(newTask);
        assertThat(newTask.getLinkedCase()).isEqualTo(existingCase);
        verify(caseRepository).save(existingCase);
    }

    @Test
    void returnsNullWhenCreatingTaskForNonExistentCase() {
        Long caseId = 999L;
        Task newTask = new Task();
        when(caseRepository.findById(caseId)).thenReturn(Optional.empty());

        Task result = caseService.createTask(caseId, newTask);

        assertThat(result).isNull();
        verify(caseRepository, never()).save(any());
    }
}