package com.hcl.learning_portal.controller;

import com.hcl.learning_portal.dto.ResponseDTO;
import com.hcl.learning_portal.dto.StudentDTO;
import com.hcl.learning_portal.dto.StudentMarkDTO;
import com.hcl.learning_portal.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/student")
public class StudentController {

    @Autowired
    StudentService studentService;

    @PostMapping("/create")
    public ResponseEntity<ResponseDTO<?>> createStudent(@RequestBody StudentDTO studentDTO) {
        return studentService.createStudent(studentDTO);
    }

    @GetMapping("/details/{student_id}")
    public ResponseEntity<ResponseDTO<?>> getStudent(@PathVariable(name = "student_id") long studentId) {
        return studentService.getStudent(studentId);
    }

    @PostMapping("/subject-mapping/{student_id}")
    public ResponseEntity<ResponseDTO<?>> studentAndSubjectMapping(@PathVariable(name = "student_id") long studentId, @RequestParam(name = "subject_ids")List<Long> subjectIds) {
        return studentService.studentAndSubjectMapping(studentId, subjectIds);
    }

    @GetMapping("/assigned-subject/{student_id}")
    public ResponseEntity<ResponseDTO<?>> assignedSubjectList(@PathVariable(name = "student_id") long studentId) {
        return studentService.assignedSubjectList(studentId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/marks/{student_id}")
    public ResponseEntity<ResponseDTO<?>> studentsMarkEntry(@PathVariable(name = "student_id") long studentId, @RequestBody List<StudentMarkDTO> studentMarkDTO) {
        return studentService.studentsMarkEntry(studentId, studentMarkDTO);
    }
}
