package com.fpt.g2.controller;

import com.fpt.g2.constant.CommonConstant;
import com.fpt.g2.constant.ScreenConstant;
import com.fpt.g2.constant.URLConstant;
import com.fpt.g2.dto.*;
import com.fpt.g2.entity.Curriculum;
import com.fpt.g2.entity.Subject;
import com.fpt.g2.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ElectiveController {
    @Autowired
    CurriculumSubjectService curriculumSubjectService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private CurriculumService curriculumService;

    @Autowired
    private ElectiveService electiveService;

    @Autowired
    private SubjectService subjectService;


    @GetMapping(URLConstant.Curriculum.ELECTIVES)
    public String initElective(Model model, HttpSession session, @RequestParam("id") Long curriculumId) {
        Long userId = null;
        List<SubHeaderDTO> curriculumPages = null;
        if (session.getAttribute(CommonConstant.ID) != null) {
            userId = Long.valueOf(session.getAttribute(CommonConstant.ID).toString());
            curriculumPages = permissionService.getListCurriculumHeader(userId);
            model.addAttribute("canAdd", permissionService.isUserCanAdd(userId, URLConstant.Curriculum.ELECTIVES));
            model.addAttribute("canEdit", permissionService.isUserCanEdit(userId, URLConstant.Curriculum.ELECTIVES));
        } else {
            curriculumPages = permissionService.getListCurriculumHeaderForGuest();
        }
        Boolean status = null;
        if(!permissionService.isUserCanEdit(userId, URLConstant.Curriculum.ELECTIVES)){
            status = false;
        }
        List<Subject> subjects = curriculumSubjectService.getAllElective(curriculumId);
        List<Subject> electives = electiveService.findByCurriculum(curriculumId, status);
        List<Subject> subjectSelect = curriculumSubjectService.getElectiveForSelect(curriculumId, subjects);
        List<Subject> electiveExisted = electiveService.getAllExistedElective(electives);
        Curriculum curriculum = curriculumService.findById(curriculumId);
        model.addAttribute(CommonConstant.MENU, ScreenConstant.DASHBOARD);
        model.addAttribute(CommonConstant.LOGIN_ROLES, new ArrayList<>());
        model.addAttribute(CommonConstant.CURRICULUM_PAGES, curriculumPages);
        model.addAttribute(CommonConstant.CURRICULUM_NAME, curriculum.getCode());
        model.addAttribute(CommonConstant.SUB_MENU, ScreenConstant.Curriculum.DETAILS);
        model.addAttribute(CommonConstant.SUB_CONTENT, ScreenConstant.Curriculum.ELECTIVES);
        model.addAttribute("curriculumId", curriculumId);
        model.addAttribute("curriculum", curriculum);
        model.addAttribute("electiveExisted", electiveExisted);
        model.addAttribute("electives", electives);
        model.addAttribute("subjectSelect", subjectSelect);
        model.addAttribute(CommonConstant.CURRICULUM_PAGE_TITLE, "Elective");
        return ScreenConstant.HOME_PAGE;
    }

    @GetMapping("/elective/details")
    public String details(Model model, HttpSession session, @RequestParam("curriculumId") Long curriculumId,
                          @RequestParam("id") Long parentId) {
        Long userId = null;
        List<SubHeaderDTO> curriculumPages = null;
        if (session.getAttribute(CommonConstant.ID) != null) {
            userId = Long.valueOf(session.getAttribute(CommonConstant.ID).toString());
            curriculumPages = permissionService.getListCurriculumHeader(userId);
            model.addAttribute("canAdd", permissionService.isUserCanAdd(userId, URLConstant.Curriculum.ELECTIVES));
            model.addAttribute("canEdit", permissionService.isUserCanEdit(userId, URLConstant.Curriculum.ELECTIVES));
            model.addAttribute("canDelete", permissionService.isUserCanDelete(userId, URLConstant.Curriculum.ELECTIVES));
        } else {
            curriculumPages = permissionService.getListCurriculumHeaderForGuest();
        }
        Curriculum curriculum = curriculumService.findById(curriculumId);
        Subject elective = subjectService.findById(parentId);
        List<Subject> childrenSubject = electiveService.getAllSubjectChildren(parentId, curriculumId);
        List<Subject> subjectsSelect = electiveService.getSubjectNotParent(childrenSubject, curriculumId);
        model.addAttribute(CommonConstant.MENU, ScreenConstant.DASHBOARD);
        model.addAttribute(CommonConstant.LOGIN_ROLES, new ArrayList<>());
        model.addAttribute(CommonConstant.CURRICULUM_PAGES, curriculumPages);
        model.addAttribute(CommonConstant.CURRICULUM_NAME, curriculum.getCode());
        model.addAttribute("curriculum", curriculum);
        model.addAttribute("curriculumId", curriculumId);
        model.addAttribute("childrenElective", childrenSubject);
        model.addAttribute("elective", elective);
        model.addAttribute("subjectsSelect", subjectsSelect);
        model.addAttribute(CommonConstant.SUB_MENU, ScreenConstant.Curriculum.DETAILS);
        model.addAttribute(CommonConstant.SUB_CONTENT, ScreenConstant.Curriculum.ELECTIVES_DETAILS);
        model.addAttribute(CommonConstant.CURRICULUM_PAGE_TITLE, "Elective");
        return ScreenConstant.HOME_PAGE;
    }

    @PostMapping(URLConstant.Curriculum.ELECTIVES + URLConstant.INSERT)
    public String addElective(Model model, HttpSession session, @ModelAttribute ElectiveDTO elective) {
        Long userId = Long.valueOf(session.getAttribute(CommonConstant.ID).toString());
        if (permissionService.isUserCanAdd(userId, URLConstant.Curriculum.ELECTIVES)) {
            subjectService.addElective(elective);
            return "redirect:/curriculum/electives?id=" + elective.getCurriculumId();
        }
        return ScreenConstant.Error.PAGE_401;
    }

    @PostMapping(URLConstant.Curriculum.ELECTIVES + URLConstant.ADD)
    public String addExistedElective(Model model, HttpSession session, @ModelAttribute ElectiveExistedDTO existedDto) {
        Long userId = Long.valueOf(session.getAttribute(CommonConstant.ID).toString());
        if (permissionService.isUserCanAdd(userId, URLConstant.Curriculum.ELECTIVES)
                && permissionService.isUserCanEdit(userId, URLConstant.Curriculum.ELECTIVES)) {
            Subject elective = subjectService.findById(existedDto.getElectiveId());
            curriculumSubjectService.addCurriculumSubject(elective.getId(), existedDto.getCurriculumId());
            return "redirect:/curriculum/electives?id=" + existedDto.getCurriculumId();
        }
        return ScreenConstant.Error.PAGE_401;
    }

    @PostMapping("/elective/detail/insert")
    public String addSubjectTo(Model model, HttpSession session, @ModelAttribute ElectiveDetailDTO electiveDetail) {
        Long userId = Long.valueOf(session.getAttribute(CommonConstant.ID).toString());
        if (permissionService.isUserCanAdd(userId, URLConstant.Curriculum.ELECTIVES)
                && permissionService.isUserCanEdit(userId, URLConstant.Curriculum.ELECTIVES)) {
            Subject elective = subjectService.findById(electiveDetail.getElectiveId());
            subjectService.addElectiveSubject(elective.getId(), electiveDetail.getSubjectIds());
            return "redirect:/elective/details?id=+" + electiveDetail.getElectiveId() + "&&curriculumId=" + electiveDetail.getCurriculumId();
        }
        return ScreenConstant.Error.PAGE_401;
    }

    @GetMapping("/elective/detail/remove")
    public String removeSubject(Model model, HttpSession session, @RequestParam("sid") Long subjectId,
                                @RequestParam("eid") Long electiveId, @RequestParam("cid") Long curriculumId) {
        Long userId = Long.valueOf(session.getAttribute(CommonConstant.ID).toString());
        if (permissionService.isUserCanAdd(userId, URLConstant.Curriculum.ELECTIVES)
                && permissionService.isUserCanEdit(userId, URLConstant.Curriculum.ELECTIVES)) {
            subjectService.removeParentSubject(subjectId);
            return "redirect:/elective/details?id=+" + electiveId + "&&curriculumId=" + curriculumId;
        }
        return ScreenConstant.Error.PAGE_401;
    }

    @PostMapping("/elective/details/update")
    public @ResponseBody ResponseEntity update(Model model, HttpSession session, @RequestBody ElectiveDTO elective) {
        if (subjectService.checkCodeExisted(elective.getCode(), elective.getId(), "update")) {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
        subjectService.updateCombo(elective);
        return new ResponseEntity(HttpStatus.OK);
    }
}
