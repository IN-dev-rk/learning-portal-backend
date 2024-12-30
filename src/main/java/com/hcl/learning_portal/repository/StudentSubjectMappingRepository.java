package com.hcl.learning_portal.repository;

import com.hcl.learning_portal.model.StudentSubjectMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentSubjectMappingRepository extends JpaRepository<StudentSubjectMapping, Long> {
    List<StudentSubjectMapping> findAllByStudentId(long studentId);
}
