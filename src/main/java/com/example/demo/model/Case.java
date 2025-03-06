package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cases")
public class Case {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private Enum<Status> status; // OPEN, IN_PROGRESS, CLOSED
    private Enum<Category> category;

    @ManyToOne
    @JoinColumn(name = "assignee_id")
    private Assignee assignee;

    @ManyToOne
    @JoinColumn(name = "campaign_id")
    private Campaign campaign;

    @OneToMany
    @JoinColumn(name = "task_id")
    private List<Task> tasks;

    private LocalDateTime createdAt = LocalDateTime.now();
}
