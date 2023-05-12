package com.fpt.g2.service.impl;

import com.fpt.g2.constant.CommonConstant;
import com.fpt.g2.entity.Decision;
import com.fpt.g2.dto.DecisionDTO;
import com.fpt.g2.repository.DecisionRepository;
import com.fpt.g2.service.DecisionService;
import com.fpt.g2.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.ParseException;
import java.util.List;

@Service
public class DecisionServiceImpl implements DecisionService {

    @Autowired
    private DecisionRepository decisionRepository;

    @Override
    public List<Decision> getDecisions() {
        return decisionRepository.findAll();
    }

    @Override
    public Page getAllDecision(DecisionDTO request) {
        if(request.getSortBy().toString().split(":")[0].equals("updatedDate")) {
            request.setSortBy("decisionName");
            request.setSortType("asc");
        }
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(),request.getSortBy());
        return decisionRepository.getAllDecision(pageable,CommonUtils.getEscapeKeyword(request.getKeyword()));
    }

    @Override
    public Decision findById(Long id) {
        return decisionRepository.findById(id).get();
    }

    @Override
    public boolean isDecisionNoExisted(String decisionNo) {
        return decisionRepository.existsDecisionByDecisionNo(decisionNo);
    }

    @Override
    public String saveOrUpdate(DecisionDTO decisionDTO) throws ParseException {

        Decision decision;
        Date date = null;
        if (!CommonUtils.isNullOrEmpty(decisionDTO.getDecisionDate())) {
            String[] dateInput = decisionDTO.getDecisionDate().split("/");
            date = Date.valueOf(dateInput[2] + "-" + dateInput[1] + "-" + dateInput[0]);
        }
         
        if (decisionDTO.getId() != null) {
            decision = decisionRepository.findById(decisionDTO.getId()).get();
            decision.setDecisionDate(date);
        } else {
            decision = new Decision();
        }

        decision.setDecisionNo(decisionDTO.getDecisionNo());
        decision.setDecisionName(decisionDTO.getDecisionName());
        decision.setNote(decisionDTO.getNote());

        decisionRepository.save(decision);
        return decisionDTO.getId() != null ? CommonConstant.UPDATE_SUCCESS : CommonConstant.ADD_SUCCESS;
    }
}
