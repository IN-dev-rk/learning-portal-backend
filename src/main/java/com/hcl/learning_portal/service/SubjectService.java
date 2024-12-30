package com.hcl.learning_portal.service;

import com.hcl.learning_portal.constant.Constants;
import com.hcl.learning_portal.dto.ResponseDTO;
import com.hcl.learning_portal.dto.SubjectDTO;
import com.hcl.learning_portal.model.SubjectModel;
import com.hcl.learning_portal.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SubjectService {

    @Autowired
    SubjectRepository subjectRepository;

    public ResponseEntity<ResponseDTO<?>> createSubject(SubjectDTO subjectDTO) {
        try {
            SubjectModel subjectModel = new SubjectModel();
            subjectModel.setName(subjectDTO.getName());
            subjectRepository.save(subjectModel);
            ResponseDTO<?> responseDTO = new ResponseDTO<>(201, Constants.CREATED_MESSAGE, subjectModel);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
        } catch (Exception e) {
            ResponseDTO<?> responseDTO = new ResponseDTO<>(500, e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
        }
    }

    public ResponseEntity<ResponseDTO<?>> getAllSubjectPage(int pageNo, int pageSize) {
        try {
            Pageable pageable = PageRequest.of(pageNo, pageSize);
            Page<SubjectModel> subjectModelPage = subjectRepository.findAll(pageable);
            ResponseDTO<?> responseDTO = new ResponseDTO<>(200, Constants.SUCCESS, subjectModelPage);
            return ResponseEntity.ok().body(responseDTO);
        } catch (Exception e) {
            ResponseDTO<?> responseDTO = new ResponseDTO<>(500, e.getMessage(), null);
            return ResponseEntity.ok().body(responseDTO);
        }
    }

    public ResponseEntity<ResponseDTO<?>> getAllSubjectList() {
        try {
            List<SubjectModel> subjectModelList = subjectRepository.findAll();
            ResponseDTO<?> responseDTO = new ResponseDTO<>(200, Constants.SUCCESS, subjectModelList);
            return ResponseEntity.ok().body(responseDTO);
        } catch (Exception e) {
            ResponseDTO<?> responseDTO = new ResponseDTO<>(500, e.getMessage(), null);
            return ResponseEntity.ok().body(responseDTO);
        }
    }

    public ResponseEntity<ResponseDTO<?>> getSubjectDetail(Long subjectId) {
        try {
            Optional<SubjectModel> subjectModelList = subjectRepository.findById(subjectId);
            ResponseDTO<?> responseDTO = new ResponseDTO<>(200, Constants.SUCCESS, subjectModelList.orElse(null));
            return ResponseEntity.ok().body(responseDTO);
        } catch (Exception e) {
            ResponseDTO<?> responseDTO = new ResponseDTO<>(500, e.getMessage(), null);
            return ResponseEntity.ok().body(responseDTO);
        }
    }

    public ResponseEntity<ResponseDTO<?>> updateSubjects(Long subjectId, SubjectDTO subjectDTO) {
        try {
            Optional<SubjectModel> subjectModel = subjectRepository.findById(subjectId);
            SubjectModel subjectModelUpdate = null;
            if(subjectModel.isPresent()) {
                subjectModelUpdate = subjectModel.get();
                subjectModelUpdate.setName(subjectDTO.getName());
                subjectModelUpdate.setUpdatedAt(new Date());
                subjectRepository.save(subjectModelUpdate);
            }
            ResponseDTO<?> responseDTO = new ResponseDTO<>(200, Constants.SUCCESS, subjectModelUpdate);
            return ResponseEntity.ok().body(responseDTO);
        } catch (Exception e) {
            ResponseDTO<?> responseDTO = new ResponseDTO<>(500, e.getMessage(), null);
            return ResponseEntity.ok().body(responseDTO);
        }
    }

    public ResponseEntity<ResponseDTO<?>> deleteSubjects(Long subjectId) {
        try {
            subjectRepository.deleteById(subjectId);
            ResponseDTO<?> responseDTO = new ResponseDTO<>(200, Constants.SUCCESS, "");
            return ResponseEntity.ok().body(responseDTO);
        } catch (Exception e) {
            ResponseDTO<?> responseDTO = new ResponseDTO<>(500, e.getMessage(), null);
            return ResponseEntity.ok().body(responseDTO);
        }
    }
}
