package com.example.demo.repo;

import com.example.demo.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query(value = "SELECT * FROM tasks WHERE linked_case = :caseId", nativeQuery = true)
    List<Task> findByLinkedCase(String caseId);
}