package com.example.demo.repo;

import com.example.demo.Campaign;
import com.example.demo.Case;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CaseRepository extends JpaRepository<Case, Long> {
}