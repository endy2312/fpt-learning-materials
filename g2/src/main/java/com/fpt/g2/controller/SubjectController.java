package com.fpt.g2.controller;

import com.fpt.g2.constant.CommonConstant;
import com.fpt.g2.constant.ScreenConstant;
import com.fpt.g2.constant.URLConstant;
import com.fpt.g2.dto.*;
import com.fpt.g2.entity.Syllabus;
import com.fpt.g2.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class SubjectController {

    @Autowired
    private SettingService settingService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    PreRequisiteService preRequisiteService;

    @Autowired
    SyllabusService syllabusService;

    @GetMapping(URLConstant.Subject.SUBJECT_LIST)
    public String init(Model model, HttpSession session, SubjectDTO request) {
        Long userId = Long.valueOf(session.getAttribute(CommonConstant.ID).toString());
        List<String> loginRoles = settingService.getRolesByUserId(userId);
        if (permissionService.isUserAccessAll(userId, URLConstant.Subject.SUBJECT_LIST)
                || permissionService.isUserCanRead(userId, URLConstant.Subject.SUBJECT_LIST )) {
            model.addAttribute(CommonConstant.MENU, ScreenConstant.DASHBOARD);
            model.addAttribute(CommonConstant.SUB_MENU, ScreenConstant.Subject.SUBJECT_LIST);
            Page subjects = subjectService.getAllSubject(request);
            model.addAttribute("subjects", subjects);
            model.addAttribute("canAdd", permissionService.isUserCanAdd(userId, URLConstant.Subject.SUBJECT_LIST));
            model.addAttribute("canEdit", permissionService.isUserCanEdit(userId, URLConstant.Subject.SUBJECT_LIST));
            model.addAttribute(CommonConstant.REQUEST, request);
            return ScreenConstant.HOME_PAGE;
        }
        return ScreenConstant.Error.PAGE_401;
    }

    @PostMapping(URLConstant.SUBJECT)
    public String addOrUpdate(Model model, HttpSession session, @ModelAttribute SubjectDTO subjectDTO) {
        Long userId = Long.valueOf(session.getAttribute(CommonConstant.ID).toString());
        if (permissionService.isUserCanAdd(userId, URLConstant.Subject.SUBJECT_LIST)
                || permissionService.isUserCanEdit(userId, URLConstant.Subject.SUBJECT_LIST)) {
            subjectService.addOrUpdate(subjectDTO, userId);
            return "redirect:/subjects";
        }
        return ScreenConstant.Error.PAGE_401;
    }

    @GetMapping(URLConstant.Subject.SUBJECT_PREDECESSORS)
    public String predecessor(Model model, HttpSession session, @RequestParam(value = "code", required = false, defaultValue = "") String code) {
        List<PredecessorListDTO> predecessors = preRequisiteService.getPredecessor(code);
        model.addAttribute("predecessors", predecessors);
        model.addAttribute("code", code);
        model.addAttribute(CommonConstant.SUB_MENU, ScreenConstant.Subject.PREDECESSOR);
        return ScreenConstant.HOME_PAGE;
    }

    @GetMapping(URLConstant.Subject.DETAIL)
    public String initUpdate(Model model, HttpSession session, @RequestParam Long id) {
        Long userId = Long.valueOf(session.getAttribute(CommonConstant.ID).toString());

        SubjectUpdateDTO subject = subjectService.initUpdateSubject(id);
        List<Syllabus> syllabi = syllabusService.getSyllabusBySubjectId(id);
        model.addAttribute("subject", subject);
        model.addAttribute("syllabi", syllabi);
        model.addAttribute("canEdit", permissionService.isUserCanEdit(userId, URLConstant.Subject.DETAIL));
        model.addAttribute(CommonConstant.SUB_MENU, "subject/details");
        return ScreenConstant.HOME_PAGE;
    }

    @GetMapping(URLConstant.Subject.SUBJECT_SUCCESSORS)
    public String successor(Model model, HttpSession session, @RequestParam(value = "code", required = false, defaultValue = "") String code) {
        List<SuccessorListDTO> successor = preRequisiteService.getSuccessor(code);
        model.addAttribute("successors", successor);
        model.addAttribute("code", code);
        model.addAttribute(CommonConstant.SUB_MENU, ScreenConstant.Subject.SUCCESSOR);
        model.addAttribute(CommonConstant.MENU, ScreenConstant.Subject.SUCCESSOR);
        return ScreenConstant.HOME_PAGE;
    }

    @ResponseBody
    @GetMapping("/check-existed")
    public ResponseEntity isSubjectExist(@RequestParam("code") String code,@RequestParam(value = "id",required = false) Long id) {
        if (id == null && subjectService.isSubjectExisted(code, id,"add")) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        else if(id != null && subjectService.isSubjectExisted(code,id,"update")){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping(URLConstant.Subject.DETAIL + URLConstant.UPDATE)
    public String subjectDetails(Model model, HttpSession session, @ModelAttribute SubjectDTO subject) {
        Long userId = Long.valueOf(session.getAttribute(CommonConstant.ID).toString());
        if (permissionService.isUserCanAdd(userId, URLConstant.Subject.DETAIL)
                || permissionService.isUserCanEdit(userId, URLConstant.Subject.DETAIL)) {
            subjectService.addOrUpdate(subject, userId);
            return "redirect:/subjects";
        }
        return ScreenConstant.Error.PAGE_401;
    }

    @PostMapping("/subjects/update-active")
    public String updateStatus(Model model, HttpSession session, @ModelAttribute SubjectListDTO subjectList) {
        Long userId = Long.valueOf(session.getAttribute(CommonConstant.ID).toString());
        if (permissionService.isUserCanEdit(userId, URLConstant.Subject.SUBJECT_LIST)) {
            subjectService.updateActive(subjectList, userId);
            return "redirect:/subjects";
        }
        return ScreenConstant.Error.PAGE_401;
    }

    @PostMapping("/subject/add-more")
    public @ResponseBody ResponseEntity addNewSubject(Model model, HttpSession session, @RequestBody SubjectDTO subjectDTO) {
        Long userId = Long.valueOf(session.getAttribute(CommonConstant.ID).toString());
            subjectService.addOrUpdate(subjectDTO, userId);
            return new ResponseEntity(HttpStatus.OK);
    }
}
