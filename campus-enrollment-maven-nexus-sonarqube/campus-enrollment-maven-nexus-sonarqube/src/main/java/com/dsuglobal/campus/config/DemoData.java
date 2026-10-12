package com.dsuglobal.campus.config;

import com.dsuglobal.campus.model.Course;
import com.dsuglobal.campus.model.Student;
import com.dsuglobal.campus.repository.CourseRepository;
import com.dsuglobal.campus.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DemoData {
    @Bean
    CommandLineRunner seedData(StudentRepository students, CourseRepository courses) {
        return args -> {
            if (students.count() == 0) {
                students.save(new Student("Asha Rao", "asha@example.com"));
                students.save(new Student("Ravi Kumar", "ravi@example.com"));
            }
            if (courses.count() == 0) {
                courses.save(new Course("JAVA101", "Java Fundamentals", 3));
                courses.save(new Course("DEVOPS201", "DevOps Toolchain", 2));
            }
        };
    }
}
