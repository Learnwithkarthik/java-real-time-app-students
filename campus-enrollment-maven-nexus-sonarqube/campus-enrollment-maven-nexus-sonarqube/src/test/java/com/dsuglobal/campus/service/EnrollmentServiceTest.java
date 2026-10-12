package com.dsuglobal.campus.service;

import com.dsuglobal.campus.dto.EnrollmentRequest;
import com.dsuglobal.campus.exception.ResourceNotFoundException;
import com.dsuglobal.campus.model.Course;
import com.dsuglobal.campus.model.Enrollment;
import com.dsuglobal.campus.model.Student;
import com.dsuglobal.campus.repository.CourseRepository;
import com.dsuglobal.campus.repository.EnrollmentRepository;
import com.dsuglobal.campus.repository.StudentRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
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
class EnrollmentServiceTest {
    @Mock EnrollmentRepository enrollments;
    @Mock StudentRepository students;
    @Mock CourseRepository courses;
    @InjectMocks EnrollmentService service;
    Student student;
    Course course;

    @BeforeEach
    void prepare() {
        student = new Student("Asha", "asha@example.com");
        course = new Course("JAVA101", "Java", 2);
    }

    // ORM assigns IDs in production. Mockito unit tests supply IDs by setting the
    // package-private JPA fields via reflection, without introducing a test DB.
    private void setId(Object target, long id) {
        org.springframework.test.util.ReflectionTestUtils.setField(target, "id", id);
    }

    private void stubFound() {
        setId(student, 1L);
        setId(course, 2L);
        when(students.findById(1L)).thenReturn(Optional.of(student));
        when(courses.findByIdForUpdate(2L)).thenReturn(Optional.of(course));
    }

    @Test
    void enrollsWhenSeatAvailable() {
        stubFound();
        when(enrollments.save(any(Enrollment.class))).thenAnswer(call -> call.getArgument(0));
        var result = service.enroll(new EnrollmentRequest(1L, 2L));
        assertThat(result.studentName()).isEqualTo("Asha");
        assertThat(result.courseCode()).isEqualTo("JAVA101");
        assertThat(result.enrolledAt()).isNotNull();
    }

    @Test
    void rejectsDuplicateEnrollment() {
        stubFound();
        when(enrollments.existsByStudent_IdAndCourse_Id(1L, 2L)).thenReturn(true);
        assertThatThrownBy(() -> service.enroll(new EnrollmentRequest(1L, 2L)))
                .isInstanceOf(IllegalStateException.class).hasMessageContaining("already enrolled");
    }

    @Test
    void rejectsFullCourse() {
        stubFound();
        when(enrollments.countByCourse_Id(2L)).thenReturn(2L);
        assertThatThrownBy(() -> service.enroll(new EnrollmentRequest(1L, 2L)))
                .isInstanceOf(IllegalStateException.class).hasMessageContaining("full");
    }

    @Test
    void rejectsUnknownStudent() {
        when(students.findById(1L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.enroll(new EnrollmentRequest(1L, 2L)))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}
