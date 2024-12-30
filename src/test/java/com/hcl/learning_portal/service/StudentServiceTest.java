package com.hcl.learning_portal.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.hcl.learning_portal.dto.ResponseDTO;
import com.hcl.learning_portal.dto.StudentDTO;
import com.hcl.learning_portal.model.UserModel;
import com.hcl.learning_portal.repository.UserRepository;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {StudentService.class})
@ExtendWith(SpringExtension.class)
class StudentServiceTest {
    @MockBean
    private PasswordEncoder passwordEncoder;

    @Autowired
    private StudentService studentService;

    @MockBean
    private UserRepository userRepository;

    /**
     * Test {@link StudentService#createStudent(StudentDTO)}.
     * <p>
     * Method under test: {@link StudentService#createStudent(StudentDTO)}
     */
    @Test
    @DisplayName("Test createStudent(StudentDTO)")
    void testCreateStudent() {
        // Arrange and Act
        ResponseEntity<ResponseDTO<?>> actualCreateStudentResult = studentService.createStudent(null);

        // Assert
        ResponseDTO<?> body = actualCreateStudentResult.getBody();
        assertEquals(
                "Cannot invoke \"com.hcl.learning_portal.dto.StudentDTO.getUsername()\" because \"studentDTO\" is null",
                body.getMessage());
        assertNull(body.getData());
        assertEquals(200, actualCreateStudentResult.getStatusCodeValue());
        assertEquals(500, body.getStatus());
        assertEquals(HttpStatus.OK, actualCreateStudentResult.getStatusCode());
    }

    /**
     * Test {@link StudentService#createStudent(StudentDTO)}.
     * <ul>
     *   <li>Then Body Data return {@link UserModel}.</li>
     * </ul>
     * <p>
     * Method under test: {@link StudentService#createStudent(StudentDTO)}
     */
    @Test
    @DisplayName("Test createStudent(StudentDTO); then Body Data return UserModel")
    void testCreateStudent_thenBodyDataReturnUserModel() {
        // Arrange
        when(passwordEncoder.encode(Mockito.<CharSequence>any())).thenReturn("secret");

        UserModel userModel = new UserModel();
        userModel.setCreatedAt(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        userModel.setEmail("jane.doe@example.org");
        userModel.setPassword("iloveyou");
        userModel.setRole("Role");
        userModel.setUpdatedAt(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        userModel.setUsername("janedoe");
        when(userRepository.save(Mockito.<UserModel>any())).thenReturn(userModel);

        // Act
        ResponseEntity<ResponseDTO<?>> actualCreateStudentResult = studentService.createStudent(new StudentDTO());

        // Assert
        verify(userRepository).save(isA(UserModel.class));
        verify(passwordEncoder).encode(isNull());
        ResponseDTO<?> body = actualCreateStudentResult.getBody();
        Object data = body.getData();
        assertTrue(data instanceof UserModel);
        assertEquals("Created Successfully!", body.getMessage());
        assertEquals("ROLE_STUDENT", ((UserModel) data).getRole());
        assertEquals("secret", ((UserModel) data).getPassword());
        assertNull(((UserModel) data).getId());
        assertNull(((UserModel) data).getEmail());
        assertNull(((UserModel) data).getUsername());
        assertNull(((UserModel) data).getCreatedAt());
        assertNull(((UserModel) data).getUpdatedAt());
        assertEquals(201, body.getStatus());
        assertEquals(201, actualCreateStudentResult.getStatusCodeValue());
        assertEquals(HttpStatus.CREATED, actualCreateStudentResult.getStatusCode());
    }
}
