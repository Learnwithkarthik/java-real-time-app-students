package com.dsuglobal.campus.dto;

import com.dsuglobal.campus.model.Enrollment;
import java.time.Instant;

public record EnrollmentResponse(Long id, Long studentId, String studentName,
                                 Long courseId, String courseCode, Instant enrolledAt) {
    public static EnrollmentResponse from(Enrollment enrollment) {
        return new EnrollmentResponse(
                enrollment.getId(),
                enrollment.getStudent().getId(), enrollment.getStudent().getName(),
                enrollment.getCourse().getId(), enrollment.getCourse().getCode(),
                enrollment.getEnrolledAt());
    }
}
