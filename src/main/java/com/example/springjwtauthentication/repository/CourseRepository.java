package com.example.springjwtauthentication.repository;

import com.example.springjwtauthentication.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query(value = "SELECT c.* FROM courses c " +
            "JOIN user_course e \n" +
            "ON c.id = e.course_id WHERE e.created_at > CURDATE()-7 \n" +
            "GROUP BY e.course_id ORDER BY count(e.course_id) DESC LIMIT 5;", nativeQuery = true)
    List<Course> findFirst10ByOrderByTrendingEnrollmentsDesc();


    @Query(value = "SELECT c.* FROM courses c \n" +
            "JOIN user_course e \n" +
            "ON c.id = e.course_id\n" +
            "GROUP BY e.course_id ORDER BY count(e.course_id) DESC LIMIT 5;", nativeQuery = true)
    List<Course> findFirst10ByOrderByAllTimeEnrollmentsDesc();

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM user_course WHERE courses_id= :courseId", nativeQuery = true)
    void deleteEnrollmentsById(Long courseId);
}
