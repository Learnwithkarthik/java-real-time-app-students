package com.dsuglobal.campus.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CourseRequest(
        @NotBlank @Size(max = 20) String code,
        @NotBlank @Size(max = 140) String title,
        @Min(1) @Max(500) int capacity) { }
