package com.dsuglobal.campus.service;

import com.dsuglobal.campus.dto.StudentRequest;
import com.dsuglobal.campus.dto.StudentResponse;
import com.dsuglobal.campus.exception.ResourceNotFoundException;
import com.dsuglobal.campus.model.Student;
import com.dsuglobal.campus.repository.StudentRepository;
import java.util.List;
import java.util.Locale;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentService {
    private final StudentRepository students;

    public StudentService(StudentRepository students) { this.students = students; }

    @Transactional
    public StudentResponse create(StudentRequest request) {
        String email = request.email().trim().toLowerCase(Locale.ROOT);
        if (students.existsByEmailIgnoreCase(email)) {
            throw new IllegalStateException("Student email already exists: " + email);
        }
        return StudentResponse.from(students.save(new Student(request.name().trim(), email)));
    }

    @Transactional(readOnly = true)
    public List<StudentResponse> list() {
        return students.findAll().stream().map(StudentResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public StudentResponse get(Long id) {
        return StudentResponse.from(students.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found: " + id)));
    }
}
