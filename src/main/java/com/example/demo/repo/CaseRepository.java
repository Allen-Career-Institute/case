package com.example.demo.repo;

import com.example.demo.model.Case;
import com.example.demo.model.CaseStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CaseRepository extends JpaRepository<Case, Long> {
    List<Case> findByStudentId(String studentId);

    List<Case> findByStudentIdAndStatus(String studentId, CaseStatus status);

    List<Case> findByAssigneeId(String assigneeId);

    List<Case> findByStatus(CaseStatus status);
}