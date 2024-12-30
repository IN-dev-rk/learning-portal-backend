package com.hcl.learning_portal.dto;

import lombok.Data;

@Data
public class StudentMarkDTO {
    long subjectId;
    int mark;

    public long getSubjectId() {
        return subjectId;
    }

    public int getMark() {
        return mark;
    }
}
