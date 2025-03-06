package com.example.demo.repo;

import com.example.demo.model.Case;
import com.example.demo.model.CaseStatus;
import com.example.demo.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CaseRepository extends JpaRepository<Case, Long> {

    @Query(value = "SELECT * FROM cases WHERE urm_student_id = :studentId", nativeQuery = true)
    List<Case> findByStudentId(String studentId);


    @Query(value = "SELECT * FROM cases WHERE urm_student_id = :studentId and status = :status", nativeQuery = true)
    List<Case> findByStudentId(String studentId, CaseStatus status);
}