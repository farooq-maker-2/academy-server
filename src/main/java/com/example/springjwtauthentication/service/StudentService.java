package com.example.springjwtauthentication.service;

import com.example.springjwtauthentication.entity.Course;
import com.example.springjwtauthentication.entity.Enrollment;
import com.example.springjwtauthentication.entity.Student;
import com.example.springjwtauthentication.entity.User;
import com.example.springjwtauthentication.repository.CourseRepository;
import com.example.springjwtauthentication.repository.EnrollmentRepository;
import com.example.springjwtauthentication.repository.StudentRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;

@Service
@Log4j2
@Transactional
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    public String enrollStudentForCourse(User student, Long courseId) {

        Course course = courseRepository.findCourseById(courseId);
        if (course != null && student != null) {

            Enrollment enrollment = Enrollment
                    .builder()
                    .course(course)
                    .enrollmentDate(new Date())
                    .build();
            enrollmentRepository.save(enrollment);

            course.getEnrollments().add(enrollment);
            courseRepository.save(course);

            Student student_ =  studentRepository.findStudentById(student.getId());
            student_.getCourses().add(course);
            studentRepository.save(student_);
            return "success";
        }
        return "failure";
    }

    public String optoutStudentFromCourse(User student, Long courseId) {

        Course course = courseRepository.findCourseById(courseId);
        if (course != null && student != null) {
            Student student_ = studentRepository.findStudentById(student.getId());
            student_.getCourses().remove(course);
            studentRepository.save(student_);
            return "success";
        }
        return "failure";
    }

    public boolean optoutAndDeleteStudent(Student student) {
        Student student_ = studentRepository.findStudentById(student.getId());
        student_.setCourses(new HashSet<>());
        studentRepository.delete(student_);
        return true;
    }
}