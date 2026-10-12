package com.dsuglobal.campus.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record EnrollmentRequest(
        @NotNull @Positive Long studentId,
        @NotNull @Positive Long courseId) { }
