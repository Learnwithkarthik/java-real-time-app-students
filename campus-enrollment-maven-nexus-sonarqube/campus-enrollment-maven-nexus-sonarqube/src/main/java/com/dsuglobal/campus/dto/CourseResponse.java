package com.dsuglobal.campus.dto;

import com.dsuglobal.campus.model.Course;

public record CourseResponse(Long id, String code, String title, int capacity) {
    public static CourseResponse from(Course course) {
        return new CourseResponse(course.getId(), course.getCode(), course.getTitle(), course.getCapacity());
    }
}
