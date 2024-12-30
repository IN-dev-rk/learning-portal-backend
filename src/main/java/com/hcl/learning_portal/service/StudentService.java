package com.hcl.learning_portal.service;

import com.hcl.learning_portal.constant.Constants;
import com.hcl.learning_portal.dto.ResponseDTO;
import com.hcl.learning_portal.dto.StudentDTO;
import com.hcl.learning_portal.dto.StudentMarkDTO;
import com.hcl.learning_portal.model.StudentMarkModel;
import com.hcl.learning_portal.model.StudentSubjectMapping;
import com.hcl.learning_portal.model.SubjectModel;
import com.hcl.learning_portal.model.UserModel;
import com.hcl.learning_portal.repository.StudentMarkRepository;
import com.hcl.learning_portal.repository.StudentSubjectMappingRepository;
import com.hcl.learning_portal.repository.SubjectRepository;
import com.hcl.learning_portal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    SubjectRepository subjectRepository;

    @Autowired
    StudentSubjectMappingRepository studentSubjectMappingRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    StudentMarkRepository studentMarkRepository;

    public ResponseEntity<ResponseDTO<?>> createStudent(StudentDTO studentDTO) {
        try {
            UserModel userModel = new UserModel();
            userModel.setUsername(studentDTO.getUsername());
            userModel.setEmail(studentDTO.getEmail());
            userModel.setPassword(passwordEncoder.encode(studentDTO.getPassword()));
            userModel.setRole("ROLE_STUDENT");
            userRepository.save(userModel);
            ResponseDTO<?> responseDTO = new ResponseDTO<>(201, Constants.CREATED_MESSAGE, userModel);
            return ResponseEntity.status(201).body(responseDTO);
        } catch (Exception e) {
            ResponseDTO<?> responseDTO = new ResponseDTO<>(500, e.getMessage(), null);
            return ResponseEntity.status(500).body(responseDTO);
        }
    }

    public ResponseEntity<ResponseDTO<?>> getStudent(long studentId) {
        try {
            Optional<UserModel> userModel = userRepository.findById(studentId);
            ResponseDTO<?> responseDTO = new ResponseDTO<>(200, Constants.SUCCESS, userModel.orElse(null));
            return ResponseEntity.status(201).body(responseDTO);
        } catch (Exception e) {
            ResponseDTO<?> responseDTO = new ResponseDTO<>(500, e.getMessage(), null);
            return ResponseEntity.status(500).body(responseDTO);
        }
    }

    public ResponseEntity<ResponseDTO<?>> studentAndSubjectMapping(long studentId, List<Long> subjectIds) {
        try {
            List<StudentSubjectMapping> subjectMappings = new ArrayList<>();
            Optional<UserModel> userModel = userRepository.findById(studentId);
            userModel.ifPresent(model -> subjectIds.forEach(subject -> {
                Optional<SubjectModel> subjectModel = subjectRepository.findById(subject);
                if (subjectModel.isPresent()) {
                    StudentSubjectMapping studentSubjectMapping = new StudentSubjectMapping();
                    studentSubjectMapping.setStudent(model);
                    studentSubjectMapping.setSubject(subjectModel.get());
                    subjectMappings.add(studentSubjectMapping);
                }
            }));

            if(!subjectMappings.isEmpty()) {
                studentSubjectMappingRepository.saveAll(subjectMappings);
            }

            ResponseDTO<?> responseDTO = new ResponseDTO<>(200, Constants.SUCCESS, subjectMappings);
            return ResponseEntity.status(201).body(responseDTO);
        } catch (Exception e) {
            ResponseDTO<?> responseDTO = new ResponseDTO<>(500, e.getMessage(), null);
            return ResponseEntity.status(500).body(responseDTO);
        }
    }

    public ResponseEntity<ResponseDTO<?>> assignedSubjectList(long studentId) {
        try {
            List<StudentSubjectMapping> subjectMappings = studentSubjectMappingRepository.findAllByStudentId(studentId);
            ResponseDTO<?> responseDTO = new ResponseDTO<>(200, Constants.SUCCESS, subjectMappings);
            return ResponseEntity.status(200).body(responseDTO);
        } catch (Exception e) {
            ResponseDTO<?> responseDTO = new ResponseDTO<>(500, e.getMessage(), null);
            return ResponseEntity.status(500).body(responseDTO);
        }
    }

    public ResponseEntity<ResponseDTO<?>> studentsMarkEntry(long studentId, List<StudentMarkDTO> studentMarkDTO) {
        try {
            List<StudentMarkModel> studentMarkModels = new ArrayList<>();
            Optional<UserModel> userModel = userRepository.findById(studentId);
            userModel.ifPresent(user -> {
                studentMarkDTO.forEach(mark -> {
                    Optional<SubjectModel> subjectModel = subjectRepository.findById(mark.getSubjectId());
                    StudentMarkModel studentMarkModel = new StudentMarkModel();
                    studentMarkModel.setUserId(user);
                    studentMarkModel.setSubject(subjectModel.orElse(null));
                    studentMarkModel.setMarks(mark.getMark());
                    studentMarkModels.add(studentMarkModel);
                });
            });
            if(!studentMarkModels.isEmpty()) {
                studentMarkRepository.saveAll(studentMarkModels);
            }
            ResponseDTO<?> responseDTO = new ResponseDTO<>(200, Constants.SUCCESS, studentMarkModels);
            return ResponseEntity.status(200).body(responseDTO);
        } catch (Exception e) {
            ResponseDTO<?> responseDTO = new ResponseDTO<>(500, e.getMessage(), null);
            return ResponseEntity.status(500).body(responseDTO);
        }
    }
}
