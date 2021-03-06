package com.example.springjwtauthentication.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "courses")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String courseName;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String level;

    @JsonIgnore
    @OneToMany(mappedBy = "course")
    private List<Content> courseContents;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private User teacher;

    @JsonIgnore
    @ManyToMany(mappedBy="studentCourses")
    //@JoinColumn(name = "student_id")
    private List<User> student;
}

