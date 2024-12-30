package com.hcl.learning_portal.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.hcl.learning_portal.dto.ResponseDTO;
import com.hcl.learning_portal.dto.SubjectDTO;
import com.hcl.learning_portal.model.SubjectModel;
import com.hcl.learning_portal.repository.SubjectRepository;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {SubjectService.class})
@ExtendWith(SpringExtension.class)
class SubjectServiceTest {
    @MockBean
    private SubjectRepository subjectRepository;

    @Autowired
    private SubjectService subjectService;

    /**
     * Test {@link SubjectService#createSubject(SubjectDTO)}.
     * <p>
     * Method under test: {@link SubjectService#createSubject(SubjectDTO)}
     */
    @Test
    @DisplayName("Test createSubject(SubjectDTO)")
    void testCreateSubject() {
        // Arrange and Act
        ResponseEntity<ResponseDTO<?>> actualCreateSubjectResult = subjectService.createSubject(null);

        // Assert
        ResponseDTO<?> body = actualCreateSubjectResult.getBody();
        assertEquals("Cannot invoke \"com.hcl.learning_portal.dto.SubjectDTO.getName()\" because \"subjectDTO\" is null",
                body.getMessage());
        assertNull(body.getData());
        assertEquals(500, actualCreateSubjectResult.getStatusCodeValue());
        assertEquals(500, body.getStatus());
        assertEquals(HttpStatus.OK, actualCreateSubjectResult.getStatusCode());
    }

    /**
     * Test {@link SubjectService#createSubject(SubjectDTO)}.
     * <ul>
     *   <li>Then Body Data return {@link SubjectModel}.</li>
     * </ul>
     * <p>
     * Method under test: {@link SubjectService#createSubject(SubjectDTO)}
     */
    @Test
    @DisplayName("Test createSubject(SubjectDTO); then Body Data return SubjectModel")
    void testCreateSubject_thenBodyDataReturnSubjectModel() {
        // Arrange
        SubjectModel subjectModel = new SubjectModel();
        subjectModel.setCreatedAt(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        subjectModel.setName("Name");
        subjectModel.setUpdatedAt(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        when(subjectRepository.save(Mockito.<SubjectModel>any())).thenReturn(subjectModel);

        // Act
        ResponseEntity<ResponseDTO<?>> actualCreateSubjectResult = subjectService.createSubject(new SubjectDTO());

        // Assert
        verify(subjectRepository).save(isA(SubjectModel.class));
        ResponseDTO<?> body = actualCreateSubjectResult.getBody();
        Object data = body.getData();
        assertTrue(data instanceof SubjectModel);
        assertEquals("Created Successfully!", body.getMessage());
        assertNull(((SubjectModel) data).getId());
        assertNull(((SubjectModel) data).getName());
        assertNull(((SubjectModel) data).getCreatedAt());
        assertNull(((SubjectModel) data).getUpdatedAt());
        assertEquals(201, body.getStatus());
        assertEquals(201, actualCreateSubjectResult.getStatusCodeValue());
        assertEquals(HttpStatus.CREATED, actualCreateSubjectResult.getStatusCode());
    }

    /**
     * Test {@link SubjectService#getAllSubjectPage(int, int)}.
     * <ul>
     *   <li>Then Body Data return {@link PageImpl}.</li>
     * </ul>
     * <p>
     * Method under test: {@link SubjectService#getAllSubjectPage(int, int)}
     */
    @Test
    @DisplayName("Test getAllSubjectPage(int, int); then Body Data return PageImpl")
    void testGetAllSubjectPage_thenBodyDataReturnPageImpl() {
        // Arrange
        List<SubjectModel> subjectModelList = new ArrayList<>();
        SubjectModel subjectModel = new SubjectModel();

        PageImpl<SubjectModel> pageImpl = new PageImpl<>(new ArrayList<>());
        when(subjectRepository.findAll(Mockito.<Pageable>any())).thenReturn(pageImpl);

        // Act
        ResponseEntity<ResponseDTO<?>> actualAllSubjectPage = subjectService.getAllSubjectPage(1, 3);

        // Assert
        verify(subjectRepository).findAll(isA(Pageable.class));
        ResponseDTO<?> body = actualAllSubjectPage.getBody();
        Object data = body.getData();
        assertTrue(data instanceof PageImpl);
        assertEquals("Success!", body.getMessage());
        assertEquals(200, body.getStatus());
        assertTrue(((PageImpl<Object>) data).toList().isEmpty());
        assertSame(pageImpl, data);
    }

    /**
     * Test {@link SubjectService#getAllSubjectPage(int, int)}.
     * <ul>
     *   <li>Then return Body Message is
     * {@code Page index must not be less than zero}.</li>
     * </ul>
     * <p>
     * Method under test: {@link SubjectService#getAllSubjectPage(int, int)}
     */
    @Test
    @DisplayName("Test getAllSubjectPage(int, int); then return Body Message is 'Page index must not be less than zero'")
    void testGetAllSubjectPage_thenReturnBodyMessageIsPageIndexMustNotBeLessThanZero() {
        // Arrange and Act
        ResponseEntity<ResponseDTO<?>> actualAllSubjectPage = subjectService.getAllSubjectPage(-1, 3);

        // Assert
        ResponseDTO<?> body = actualAllSubjectPage.getBody();
        assertEquals("Page index must not be less than zero", body.getMessage());
        assertNull(body.getData());
        assertEquals(200, actualAllSubjectPage.getStatusCodeValue());
        assertEquals(500, body.getStatus());
        assertEquals(HttpStatus.OK, actualAllSubjectPage.getStatusCode());
        assertTrue(actualAllSubjectPage.hasBody());
        assertTrue(actualAllSubjectPage.getHeaders().isEmpty());
    }

    /**
     * Test {@link SubjectService#getAllSubjectList()}.
     * <p>
     * Method under test: {@link SubjectService#getAllSubjectList()}
     */
    @Test
    @DisplayName("Test getAllSubjectList()")
    void testGetAllSubjectList() {
        // Arrange
        ArrayList<SubjectModel> subjectModelList = new ArrayList<>();
        when(subjectRepository.findAll()).thenReturn(subjectModelList);

        // Act
        ResponseEntity<ResponseDTO<?>> actualAllSubjectList = subjectService.getAllSubjectList();

        // Assert
        verify(subjectRepository).findAll();
        ResponseDTO<?> body = actualAllSubjectList.getBody();
        Object data = body.getData();
        assertTrue(data instanceof List);
        assertEquals("Success!", body.getMessage());
        assertEquals(200, body.getStatus());
        assertEquals(200, actualAllSubjectList.getStatusCodeValue());
        assertEquals(HttpStatus.OK, actualAllSubjectList.getStatusCode());
        assertTrue(((List<Object>) data).isEmpty());
        assertTrue(actualAllSubjectList.hasBody());
        assertTrue(actualAllSubjectList.getHeaders().isEmpty());
        assertSame(subjectModelList, data);
    }

    /**
     * Test {@link SubjectService#getSubjectDetail(Long)}.
     * <p>
     * Method under test: {@link SubjectService#getSubjectDetail(Long)}
     */
    @Test
    @DisplayName("Test getSubjectDetail(Long)")
    void testGetSubjectDetail() {
        // Arrange
        SubjectModel subjectModel = new SubjectModel();
        subjectModel.setCreatedAt(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        subjectModel.setName("Name");
        subjectModel.setUpdatedAt(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        Optional<SubjectModel> ofResult = Optional.of(subjectModel);
        when(subjectRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        ResponseEntity<ResponseDTO<?>> actualSubjectDetail = subjectService.getSubjectDetail(1L);

        // Assert
        verify(subjectRepository).findById(eq(1L));
        ResponseDTO<?> body = actualSubjectDetail.getBody();
        assertEquals("Success!", body.getMessage());
        assertEquals(200, body.getStatus());
        assertEquals(200, actualSubjectDetail.getStatusCodeValue());
        assertEquals(HttpStatus.OK, actualSubjectDetail.getStatusCode());
        assertTrue(actualSubjectDetail.hasBody());
        assertTrue(actualSubjectDetail.getHeaders().isEmpty());
        assertSame(subjectModel, body.getData());
    }

    /**
     * Test {@link SubjectService#updateSubjects(Long, SubjectDTO)}.
     * <p>
     * Method under test: {@link SubjectService#updateSubjects(Long, SubjectDTO)}
     */
    @Test
    @DisplayName("Test updateSubjects(Long, SubjectDTO)")
    void testUpdateSubjects() {
        // Arrange
        SubjectModel subjectModel = new SubjectModel();
        subjectModel.setCreatedAt(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        subjectModel.setName("Name");
        subjectModel.setUpdatedAt(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        Optional<SubjectModel> ofResult = Optional.of(subjectModel);
        when(subjectRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        ResponseEntity<ResponseDTO<?>> actualUpdateSubjectsResult = subjectService.updateSubjects(1L, null);

        // Assert
        verify(subjectRepository).findById(eq(1L));
        ResponseDTO<?> body = actualUpdateSubjectsResult.getBody();
        assertEquals("Cannot invoke \"com.hcl.learning_portal.dto.SubjectDTO.getName()\" because \"subjectDTO\" is null",
                body.getMessage());
        assertNull(body.getData());
        assertEquals(200, actualUpdateSubjectsResult.getStatusCodeValue());
        assertEquals(500, body.getStatus());
        assertEquals(HttpStatus.OK, actualUpdateSubjectsResult.getStatusCode());
        assertTrue(actualUpdateSubjectsResult.hasBody());
        assertTrue(actualUpdateSubjectsResult.getHeaders().isEmpty());
    }

    /**
     * Test {@link SubjectService#updateSubjects(Long, SubjectDTO)}.
     * <ul>
     *   <li>Then return Body Data is {@link SubjectModel} (default constructor).</li>
     * </ul>
     * <p>
     * Method under test: {@link SubjectService#updateSubjects(Long, SubjectDTO)}
     */
    @Test
    @DisplayName("Test updateSubjects(Long, SubjectDTO); then return Body Data is SubjectModel (default constructor)")
    void testUpdateSubjects_thenReturnBodyDataIsSubjectModel() {
        // Arrange
        SubjectModel subjectModel = new SubjectModel();
        subjectModel.setCreatedAt(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        subjectModel.setName("Name");
        subjectModel.setUpdatedAt(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        Optional<SubjectModel> ofResult = Optional.of(subjectModel);

        SubjectModel subjectModel2 = new SubjectModel();
        subjectModel2.setCreatedAt(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        subjectModel2.setName("Name");
        subjectModel2.setUpdatedAt(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        when(subjectRepository.save(Mockito.<SubjectModel>any())).thenReturn(subjectModel2);
        when(subjectRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        ResponseEntity<ResponseDTO<?>> actualUpdateSubjectsResult = subjectService.updateSubjects(1L, new SubjectDTO());

        // Assert
        verify(subjectRepository).findById(eq(1L));
        verify(subjectRepository).save(isA(SubjectModel.class));
        assertSame(subjectModel, actualUpdateSubjectsResult.getBody().getData());
    }

    /**
     * Test {@link SubjectService#updateSubjects(Long, SubjectDTO)}.
     * <ul>
     *   <li>Then return Body Message is {@code Success!}.</li>
     * </ul>
     * <p>
     * Method under test: {@link SubjectService#updateSubjects(Long, SubjectDTO)}
     */
    @Test
    @DisplayName("Test updateSubjects(Long, SubjectDTO); then return Body Message is 'Success!'")
    void testUpdateSubjects_thenReturnBodyMessageIsSuccess() {
        // Arrange
        Optional<SubjectModel> emptyResult = Optional.empty();
        when(subjectRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        // Act
        ResponseEntity<ResponseDTO<?>> actualUpdateSubjectsResult = subjectService.updateSubjects(1L, new SubjectDTO());

        // Assert
        verify(subjectRepository).findById(eq(1L));
        ResponseDTO<?> body = actualUpdateSubjectsResult.getBody();
        assertEquals("Success!", body.getMessage());
        assertNull(body.getData());
        assertEquals(200, body.getStatus());
        assertEquals(200, actualUpdateSubjectsResult.getStatusCodeValue());
        assertEquals(HttpStatus.OK, actualUpdateSubjectsResult.getStatusCode());
        assertTrue(actualUpdateSubjectsResult.hasBody());
        assertTrue(actualUpdateSubjectsResult.getHeaders().isEmpty());
    }

    /**
     * Test {@link SubjectService#deleteSubjects(Long)}.
     * <p>
     * Method under test: {@link SubjectService#deleteSubjects(Long)}
     */
    @Test
    @DisplayName("Test deleteSubjects(Long)")
    void testDeleteSubjects() {
        // Arrange
        doNothing().when(subjectRepository).deleteById(Mockito.<Long>any());

        // Act
        ResponseEntity<ResponseDTO<?>> actualDeleteSubjectsResult = subjectService.deleteSubjects(1L);

        // Assert
        verify(subjectRepository).deleteById(eq(1L));
        ResponseDTO<?> body = actualDeleteSubjectsResult.getBody();
        assertEquals("", body.getData());
        assertEquals("Success!", body.getMessage());
        assertEquals(200, body.getStatus());
        assertEquals(200, actualDeleteSubjectsResult.getStatusCodeValue());
        assertEquals(HttpStatus.OK, actualDeleteSubjectsResult.getStatusCode());
        assertTrue(actualDeleteSubjectsResult.hasBody());
        assertTrue(actualDeleteSubjectsResult.getHeaders().isEmpty());
    }
}
