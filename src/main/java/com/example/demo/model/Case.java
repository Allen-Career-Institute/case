package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Case {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private CaseStatus status; // OPEN, IN_PROGRESS, CLOSED
    private Category category;

    private String assigneeId;

    @ManyToOne(fetch = FetchType.LAZY) // ✅ Lazy load campaign to avoid serialization issues
    @JoinColumn(name = "campaign_id")
    @JsonIgnoreProperties("cases") // ✅ Prevents recursion issues if Campaign has a @OneToMany mapping back to Case
    private Campaign campaign;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "linkedCase") // ✅ Fix mapping (mappedBy should be used)
    @JsonIgnoreProperties("linkedCase") // ✅ Prevents recursion issues if Task has a @ManyToOne mapping back to Case
    private List<Task> tasks = new ArrayList<>();


    private String urmStudentId;

    @JsonIgnore
    private String studentProfile;

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt = LocalDateTime.now();

    @JsonIgnore
    public String getStudentProfile() {
        return "https://astra.allen.in/student-support/resource-management/student/view/"+urmStudentId;
    }
}
