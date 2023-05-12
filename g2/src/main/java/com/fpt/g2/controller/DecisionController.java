package com.fpt.g2.controller;

import com.fpt.g2.constant.CommonConstant;
import com.fpt.g2.constant.ScreenConstant;
import com.fpt.g2.constant.URLConstant;
import com.fpt.g2.dto.DecisionDTO;
import com.fpt.g2.entity.Decision;
import com.fpt.g2.service.*;
import com.fpt.g2.utils.decision.DecisionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.Collections;
import java.util.List;

@Controller
public class DecisionController {
    @Autowired
    private SettingService settingService;

    @Autowired
    private DecisionService decisionService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    PreRequisiteService preRequisiteService;

    @Autowired
    private CurriculumService curriculumService;

    @Autowired
    private SyllabusService syllabusService;

    @GetMapping(URLConstant.Decision.MATERIAL_DECISIONS)
    public String init(Model model, HttpSession session, DecisionDTO request) {
        Long userId = Long.valueOf(session.getAttribute(CommonConstant.ID).toString());
        List<String> loginRoles = settingService.getRolesByUserId(userId);
        if (permissionService.isUserAccessAll(userId, URLConstant.Decision.MATERIAL_DECISIONS)
                || permissionService.isUserCanRead(userId, URLConstant.Decision.MATERIAL_DECISIONS )) {
            model.addAttribute(CommonConstant.MENU, ScreenConstant.DASHBOARD);
            model.addAttribute(CommonConstant.SUB_MENU, ScreenConstant.Decision.DECISION_LIST);
            Page decisions = decisionService.getAllDecision(request);
            model.addAttribute("decisions", decisions);
            model.addAttribute("canAdd", permissionService.isUserCanAdd(userId, URLConstant.Decision.MATERIAL_DECISIONS));
            model.addAttribute("canEdit", permissionService.isUserCanEdit(userId, URLConstant.Decision.MATERIAL_DECISIONS));
            model.addAttribute(CommonConstant.REQUEST, request);
            return ScreenConstant.HOME_PAGE;
        }
        return ScreenConstant.Error.PAGE_401;
    }

    @GetMapping(URLConstant.Decision.DECISION_INSERT)
    public String getDecision(Model model, HttpSession session, DecisionDTO decision) throws ParseException {
        Long userId = Long.valueOf(session.getAttribute(CommonConstant.ID).toString());
        if (permissionService.isUserAccessAll(userId, URLConstant.Decision.MATERIAL_DECISIONS)
                || permissionService.isUserCanRead(userId, URLConstant.Decision.MATERIAL_DECISIONS )) {
            model.addAttribute(CommonConstant.MENU, ScreenConstant.DASHBOARD);
            model.addAttribute(CommonConstant.SUB_MENU, ScreenConstant.Decision.DECISION_DETAILS);
            String actionType = "";
            if (decision.getId() != null) {
                actionType = "isUpdate";
                decision = DecisionUtils.convertToDTO(decisionService.findById(decision.getId()));
                decision.setCurriculums(Collections.singletonList(curriculumService.getListCurriculumByDecisionId(decision.getId())));
                decision.setSyllabuses(Collections.singletonList(syllabusService.findAllSyllabusByDecisionId(decision.getId())));
            } else {
                actionType = "isCreate";
                decision = new DecisionDTO();
            }

            model.addAttribute("decisionForm", decision);
            model.addAttribute("actionType", actionType);
            model.addAttribute("canAdd", permissionService.isUserCanAdd(userId, URLConstant.Decision.MATERIAL_DECISIONS));
            model.addAttribute("canEdit", permissionService.isUserCanEdit(userId, URLConstant.Decision.MATERIAL_DECISIONS));
            return ScreenConstant.HOME_PAGE;
        }
        return ScreenConstant.Error.PAGE_401;
    }

    @PostMapping(URLConstant.Decision.DECISION_INSERT)
    public String saveOrUpdate(Model model, HttpSession session, DecisionDTO decision) throws ParseException {
        Long userId = Long.valueOf(session.getAttribute(CommonConstant.ID).toString());
        if (permissionService.isUserAccessAll(userId, URLConstant.Decision.MATERIAL_DECISIONS)
                || permissionService.isUserCanRead(userId, URLConstant.Decision.MATERIAL_DECISIONS )) {
            model.addAttribute(CommonConstant.MENU, ScreenConstant.DASHBOARD);
            model.addAttribute(CommonConstant.SUB_MENU, ScreenConstant.Decision.DECISION_DETAILS);
            String message  = "";
                if (decision.getId() == null && decisionService.isDecisionNoExisted(decision.getDecisionNo())) {
                    model.addAttribute(CommonConstant.CODE_EXIST, "DecisionNo is existed in system");
                } else {
                    message = decisionService.saveOrUpdate(decision);
                    return "redirect:/decisions";
                }

            model.addAttribute("canAdd", permissionService.isUserCanAdd(userId, URLConstant.Decision.MATERIAL_DECISIONS));
            model.addAttribute("canEdit", permissionService.isUserCanEdit(userId, URLConstant.Decision.MATERIAL_DECISIONS));
            return ScreenConstant.HOME_PAGE;
        }
        return ScreenConstant.Error.PAGE_401;
    }
}
