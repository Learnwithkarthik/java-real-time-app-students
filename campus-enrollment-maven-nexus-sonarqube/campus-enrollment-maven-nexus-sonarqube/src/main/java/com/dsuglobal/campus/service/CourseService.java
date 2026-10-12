package com.dsuglobal.campus.service;

import com.dsuglobal.campus.dto.CourseRequest;
import com.dsuglobal.campus.dto.CourseResponse;
import com.dsuglobal.campus.model.Course;
import com.dsuglobal.campus.repository.CourseRepository;
import java.util.List;
import java.util.Locale;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CourseService {
    private final CourseRepository courses;

    public CourseService(CourseRepository courses) { this.courses = courses; }

    @Transactional
    public CourseResponse create(CourseRequest request) {
        String code = request.code().trim().toUpperCase(Locale.ROOT);
        if (courses.existsByCodeIgnoreCase(code)) {
            throw new IllegalStateException("Course code already exists: " + code);
        }
        return CourseResponse.from(courses.save(
                new Course(code, request.title().trim(), request.capacity())));
    }

    @Transactional(readOnly = true)
    public List<CourseResponse> list() {
        return courses.findAll().stream().map(CourseResponse::from).toList();
    }
}
