package com.dsuglobal.campus.service;

import com.dsuglobal.campus.dto.CourseRequest;
import com.dsuglobal.campus.model.Course;
import com.dsuglobal.campus.repository.CourseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {
    @Mock CourseRepository repository;
    @InjectMocks CourseService service;

    @Test
    void createsCourseWithUppercaseCode() {
        when(repository.save(any(Course.class))).thenAnswer(call -> call.getArgument(0));
        var result = service.create(new CourseRequest("java101", " Java Basics ", 5));
        assertThat(result.code()).isEqualTo("JAVA101");
        assertThat(result.title()).isEqualTo("Java Basics");
        assertThat(result.capacity()).isEqualTo(5);
    }

    @Test
    void duplicateCourseCodeIsRejected() {
        when(repository.existsByCodeIgnoreCase("JAVA101")).thenReturn(true);
        assertThatThrownBy(() -> service.create(new CourseRequest("java101", "Java", 1)))
                .isInstanceOf(IllegalStateException.class);
    }
}
