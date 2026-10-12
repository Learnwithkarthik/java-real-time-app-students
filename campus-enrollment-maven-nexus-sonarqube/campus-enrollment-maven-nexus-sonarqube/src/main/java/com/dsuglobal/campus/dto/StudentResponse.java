package com.dsuglobal.campus.dto;

import com.dsuglobal.campus.model.Student;

public record StudentResponse(Long id, String name, String email) {
    public static StudentResponse from(Student student) {
        return new StudentResponse(student.getId(), student.getName(), student.getEmail());
    }
}
