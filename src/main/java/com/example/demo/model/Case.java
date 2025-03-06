package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cases")
@Setter@Getter
public class Case {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private CaseStatus status; // OPEN, IN_PROGRESS, CLOSED
    private Category category;

    private String assigneeId;

    @ManyToOne
    @JoinColumn(name = "campaign_id")
    private Campaign campaign;

    @OneToMany
    @JoinColumn(name = "task_id")
    private List<Task> tasks = new ArrayList<>();

    private String urmStudentId;

    private String studentProfile;

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt = LocalDateTime.now();

    public String getStudentProfile() {
        return "https://astra.allen.in/student-support/resource-management/student/view/"+urmStudentId;
    }
}
