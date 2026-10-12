package com.dsuglobal.campus.repository;

import com.dsuglobal.campus.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    boolean existsByStudent_IdAndCourse_Id(Long studentId, Long courseId);
    long countByCourse_Id(Long courseId);
}
