package com.fpt.g2.service;

import com.fpt.g2.entity.Decision;
import com.fpt.g2.dto.DecisionDTO;
import org.springframework.data.domain.Page;

import java.text.ParseException;
import java.util.List;

public interface DecisionService {
    List<Decision> getDecisions();

    Page getAllDecision(DecisionDTO request);

    Decision findById(Long id);

    boolean isDecisionNoExisted(String decisionNo);

    String saveOrUpdate(DecisionDTO decisionDTO) throws ParseException;
}
