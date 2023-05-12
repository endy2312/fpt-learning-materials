package com.fpt.g2.dto;


import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

@Getter
@Setter
public class DecisionDTO extends RequestCommonDTO {
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    private Long id;

    private String decisionNo;
    private String decisionDate;
    private String decisionName;
    private String note;

    private List<Object> curriculums;
    private List<Object> syllabuses;

    public String getCustomFormat() {
        return decisionNo + " dated " + sdf.format(decisionDate);
    }
}
