package com.hcl.learning_portal.controller;

import com.hcl.learning_portal.dto.ResponseDTO;
import com.hcl.learning_portal.dto.SubjectDTO;
import com.hcl.learning_portal.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/subject")
public class SubjectController {

    @Autowired
    SubjectService subjectService;

    @GetMapping("/page")
    public ResponseEntity<ResponseDTO<?>> getAllSubjectPage(@RequestParam(name = "pageNo", defaultValue = "0") int pageNo,
                                                         @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
        return subjectService.getAllSubjectPage(pageNo, pageSize);
    }

    @GetMapping("/list")
    public ResponseEntity<ResponseDTO<?>> getAllSubjectList() {
        return subjectService.getAllSubjectList();
    }

    @GetMapping("/detail/{subject_id}")
    public ResponseEntity<ResponseDTO<?>> getSubjectDetail(@PathVariable(name = "subject_id") Long subjectId) {
        return subjectService.getSubjectDetail(subjectId);
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseDTO<?>> createSubjects(@RequestBody SubjectDTO subjectDTO) {
        return subjectService.createSubject(subjectDTO);
    }

    @PutMapping("/update/{subject_id}")
    public ResponseEntity<ResponseDTO<?>> updateSubjects(@PathVariable(name = "subject_id") Long subjectId, @RequestBody SubjectDTO subjectDTO) {
        return subjectService.updateSubjects(subjectId, subjectDTO);
    }

    @DeleteMapping("/delete/{subject_id}")
    public ResponseEntity<ResponseDTO<?>> deleteSubjects(@PathVariable(name = "subject_id") Long subjectId) {
        return subjectService.deleteSubjects(subjectId);
    }
}
