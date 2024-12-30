package com.hcl.learning_portal.repository;

import com.hcl.learning_portal.model.StudentMarkModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentMarkRepository extends JpaRepository<StudentMarkModel, Long> {
}
