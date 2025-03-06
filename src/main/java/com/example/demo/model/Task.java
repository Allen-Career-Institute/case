package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "case_id")
    private Case linkedCase;

    private TaskType taskType;

    @OneToOne
    @JoinColumn(name = "assignee_id")
    private Assignee assignee;

    private String description;

    private Status status;

}
