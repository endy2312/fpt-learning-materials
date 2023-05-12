package com.fpt.g2.dto;

import com.fpt.g2.entity.Subject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubjectListDTO {
    public List<Subject> subjectList;
}
