package com.dsuglobal.campus.service;

import com.dsuglobal.campus.dto.EnrollmentRequest;
import com.dsuglobal.campus.dto.EnrollmentResponse;
import com.dsuglobal.campus.exception.ResourceNotFoundException;
import com.dsuglobal.campus.model.Course;
import com.dsuglobal.campus.model.Enrollment;
import com.dsuglobal.campus.model.Student;
import com.dsuglobal.campus.repository.CourseRepository;
import com.dsuglobal.campus.repository.EnrollmentRepository;
import com.dsuglobal.campus.repository.StudentRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EnrollmentService {
    private final EnrollmentRepository enrollments;
    private final StudentRepository students;
    private final CourseRepository courses;

    public EnrollmentService(EnrollmentRepository enrollments, StudentRepository students,
                             CourseRepository courses) {
        this.enrollments = enrollments;
        this.students = students;
        this.courses = courses;
    }

    @Transactional
    public EnrollmentResponse enroll(EnrollmentRequest request) {
        Student student = students.findById(request.studentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found: " + request.studentId()));
        Course course = courses.findByIdForUpdate(request.courseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course not found: " + request.courseId()));

        if (enrollments.existsByStudent_IdAndCourse_Id(student.getId(), course.getId())) {
            throw new IllegalStateException("Student is already enrolled in this course");
        }
        if (enrollments.countByCourse_Id(course.getId()) >= course.getCapacity()) {
            throw new IllegalStateException("Course is full: " + course.getCode());
        }
        return EnrollmentResponse.from(enrollments.save(new Enrollment(student, course)));
    }

    @Transactional(readOnly = true)
    public List<EnrollmentResponse> list() {
        return enrollments.findAll().stream().map(EnrollmentResponse::from).toList();
    }

    @Transactional
    public void cancel(Long id) {
        if (!enrollments.existsById(id)) {
            throw new ResourceNotFoundException("Enrollment not found: " + id);
        }
        enrollments.deleteById(id);
    }
}
