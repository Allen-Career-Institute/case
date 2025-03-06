package com.example.demo.repo;

import com.example.demo.model.Case;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CaseRepository extends JpaRepository<Case, Long> {
}