package com.hcl.learning_portal.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "student_subject_mapping")
public class StudentSubjectMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private UserModel student;

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private SubjectModel subject;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    public Long getId() {
        return id;
    }

    public UserModel getStudent() {
        return student;
    }

    public SubjectModel getSubject() {
        return subject;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setSubject(SubjectModel subject) {
        this.subject = subject;
    }

    public void setStudent(UserModel student) {
        this.student = student;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
