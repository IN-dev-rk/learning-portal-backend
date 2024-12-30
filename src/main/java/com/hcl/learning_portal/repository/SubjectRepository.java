package com.hcl.learning_portal.repository;

import com.hcl.learning_portal.model.SubjectModel;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<SubjectModel, Long> {
}
