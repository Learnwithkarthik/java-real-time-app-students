package com.dsuglobal.campus.service;

import com.dsuglobal.campus.dto.StudentRequest;
import com.dsuglobal.campus.exception.ResourceNotFoundException;
import com.dsuglobal.campus.model.Student;
import com.dsuglobal.campus.repository.StudentRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
    @Mock StudentRepository repository;
    @InjectMocks StudentService service;

    @Test
    void createsStudentWithNormalizedEmail() {
        when(repository.save(any(Student.class))).thenAnswer(call -> call.getArgument(0));
        var response = service.create(new StudentRequest(" Alice ", " ALICE@EXAMPLE.COM "));
        assertThat(response.name()).isEqualTo("Alice");
        assertThat(response.email()).isEqualTo("alice@example.com");
        verify(repository).existsByEmailIgnoreCase("alice@example.com");
    }

    @Test
    void rejectsDuplicateEmail() {
        when(repository.existsByEmailIgnoreCase("alice@example.com")).thenReturn(true);
        assertThatThrownBy(() -> service.create(new StudentRequest("Alice", "alice@example.com")))
                .isInstanceOf(IllegalStateException.class).hasMessageContaining("already exists");
    }

    @Test
    void missingStudentIs404InController() {
        when(repository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.get(99L))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}
