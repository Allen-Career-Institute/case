package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "case_id", nullable = false)
    private Case linkedCase;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskType taskType;

   private String assigneeId;

    private String description;

    private String transcript;

    private Long duration;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus status;

}
