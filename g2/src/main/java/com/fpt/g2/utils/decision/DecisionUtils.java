package com.fpt.g2.utils.decision;

import com.fpt.g2.dto.DecisionDTO;
import com.fpt.g2.entity.Decision;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DecisionUtils {
    public static DecisionDTO convertToDTO(Decision decision) throws ParseException {
        DecisionDTO decisionDTO = new DecisionDTO();
        decisionDTO.setId(decision.getId());
        decisionDTO.setDecisionNo(decision.getDecisionNo());
        decisionDTO.setDecisionName(decision.getDecisionName());
        if (decision.getDecisionDate() != null) {
            decisionDTO.setDecisionDate(new SimpleDateFormat("dd/MM/yyyy").format(decision.getDecisionDate()));
        }
        decisionDTO.setNote(decision.getNote());

        return decisionDTO;
    }
}
