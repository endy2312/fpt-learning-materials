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
public class ComboController {
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

    @GetMapping(URLConstant.Curriculum.COMBOS)
    public String initElective(Model model, HttpSession session, @RequestParam("id") Long curriculumId) {
        Long userId = null;
        List<SubHeaderDTO> curriculumPages = null;
        if (session.getAttribute(CommonConstant.ID) != null) {
            userId = Long.valueOf(session.getAttribute(CommonConstant.ID).toString());
            curriculumPages = permissionService.getListCurriculumHeader(userId);
            model.addAttribute("canAdd", permissionService.isUserCanAdd(userId, URLConstant.Curriculum.COMBOS));
            model.addAttribute("canEdit", permissionService.isUserCanEdit(userId, URLConstant.Curriculum.COMBOS));
        } else {
            curriculumPages = permissionService.getListCurriculumHeaderForGuest();
        }
        Boolean status = null;
        if (!permissionService.isUserCanEdit(userId, URLConstant.Curriculum.COMBOS)) {
            status = false;
        }
        List<Subject> subjects = curriculumSubjectService.getAllCombo(curriculumId);
        List<Subject> combos = subjectService.findByCurriculum(curriculumId, status);
        List<Subject> comboExisted = subjectService.getAllExistedCombo(combos);
        Curriculum curriculum = curriculumService.findById(curriculumId);
        model.addAttribute(CommonConstant.CURRICULUM_NAME, curriculum.getCode());
        model.addAttribute(CommonConstant.MENU, ScreenConstant.DASHBOARD);
        model.addAttribute(CommonConstant.LOGIN_ROLES, new ArrayList<>());
        model.addAttribute(CommonConstant.CURRICULUM_PAGES, curriculumPages);
        model.addAttribute(CommonConstant.SUB_MENU, ScreenConstant.Curriculum.DETAILS);
        model.addAttribute(CommonConstant.SUB_CONTENT, ScreenConstant.Curriculum.COMBO);
        model.addAttribute("curriculumId", curriculumId);
        model.addAttribute("comboExisted", comboExisted);
        model.addAttribute("subjects", subjects);
        model.addAttribute("combos", combos);
        model.addAttribute(CommonConstant.CURRICULUM_PAGE_TITLE, "Combos");
        return ScreenConstant.HOME_PAGE;
    }

    @GetMapping("/combo/details")
    public String details(Model model, HttpSession session, @RequestParam("curriculumId") Long curriculumId,
                          @RequestParam("id") Long parentId) {
        Long userId = null;
        List<SubHeaderDTO> curriculumPages = null;
        if (session.getAttribute(CommonConstant.ID) != null) {
            userId = Long.valueOf(session.getAttribute(CommonConstant.ID).toString());
            curriculumPages = permissionService.getListCurriculumHeader(userId);
            model.addAttribute("canAdd", permissionService.isUserCanAdd(userId, URLConstant.Curriculum.COMBOS));
            model.addAttribute("canEdit", permissionService.isUserCanEdit(userId, URLConstant.Curriculum.COMBOS));
            model.addAttribute("canDelete", permissionService.isUserCanDelete(userId, URLConstant.Curriculum.COMBOS));
        } else {
            curriculumPages = permissionService.getListCurriculumHeaderForGuest();
        }
        Curriculum curriculum = curriculumService.findById(curriculumId);
        Subject combo = subjectService.findById(parentId);
        List<Subject> childrenSubject = subjectService.getAllSubjectChildrenCombo(parentId, curriculumId);
        List<Subject> subjectsSelect = subjectService.getSubjectNotParent(childrenSubject, curriculumId);
        model.addAttribute(CommonConstant.MENU, ScreenConstant.DASHBOARD);
        model.addAttribute(CommonConstant.LOGIN_ROLES, new ArrayList<>());
        model.addAttribute(CommonConstant.CURRICULUM_PAGES, curriculumPages);
        model.addAttribute(CommonConstant.CURRICULUM_NAME, curriculum.getCode());
        model.addAttribute("curriculum", curriculum);
        model.addAttribute("curriculumId", curriculumId);
        model.addAttribute("childrenCombo", childrenSubject);
        model.addAttribute("combo", combo);
        model.addAttribute("subjectsSelect", subjectsSelect);
        model.addAttribute(CommonConstant.SUB_MENU, ScreenConstant.Curriculum.DETAILS);
        model.addAttribute(CommonConstant.SUB_CONTENT, "curriculum/combo/details");
        model.addAttribute(CommonConstant.CURRICULUM_PAGE_TITLE, "Combos");
        return ScreenConstant.HOME_PAGE;
    }

    @PostMapping(URLConstant.Curriculum.COMBOS + URLConstant.INSERT)
    public String addElective(Model model, HttpSession session, @ModelAttribute ComboDTO combo) {
        Long userId = Long.valueOf(session.getAttribute(CommonConstant.ID).toString());
        if (permissionService.isUserCanAdd(userId, URLConstant.Curriculum.COMBOS)) {
            subjectService.addCombo(combo);
            return "redirect:/curriculum/combos?id=" + combo.getCurriculumId();
        }
        return ScreenConstant.Error.PAGE_401;
    }

    @PostMapping("/combo/check-existed")
    public @ResponseBody ResponseEntity update(Model model, HttpSession session, @RequestBody ComboDTO combo) {
        if (combo.getId() == null && subjectService.checkCodeExisted(combo.getCode(), combo.getId(), "add")) {
            return new ResponseEntity(HttpStatus.CONFLICT);
        } else if (combo.getId() != null && subjectService.checkCodeExisted(combo.getCode(), combo.getId(), "update")) {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/combo/detail/insert")
    public String addSubjectTo(Model model, HttpSession session, @ModelAttribute ComboDTO combo) {
        Long userId = Long.valueOf(session.getAttribute(CommonConstant.ID).toString());
        if (permissionService.isUserCanAdd(userId, URLConstant.Curriculum.COMBOS)
                && permissionService.isUserCanEdit(userId, URLConstant.Curriculum.COMBOS)) {
            Subject elective = subjectService.findById(combo.getId());
            subjectService.addElectiveSubject(elective.getId(), combo.getIds());
            return "redirect:/combo/details?id=+" + combo.getId() + "&&curriculumId=" + combo.getCurriculumId();
        }
        return ScreenConstant.Error.PAGE_401;
    }

    @PostMapping(URLConstant.Curriculum.COMBOS + URLConstant.ADD)
    public String addExistedElective(Model model, HttpSession session, @ModelAttribute ComboExistedDTO comboDTO) {
        Long userId = Long.valueOf(session.getAttribute(CommonConstant.ID).toString());
        if (permissionService.isUserCanAdd(userId, URLConstant.Curriculum.COMBOS)
                && permissionService.isUserCanEdit(userId, URLConstant.Curriculum.COMBOS)) {
            curriculumSubjectService.addCurriculumSubject(comboDTO.getComboId(), comboDTO.getCurriculumId());
            return "redirect:/curriculum/combos?id=" + comboDTO.getCurriculumId();
        }
        return ScreenConstant.Error.PAGE_401;
    }

    @GetMapping("/combo/detail/remove")
    public String removeSubject(Model model, HttpSession session, @RequestParam("sid") Long subjectId,
                                @RequestParam("comboId") Long comboId, @RequestParam("cid") Long curriculumId) {
        Long userId = Long.valueOf(session.getAttribute(CommonConstant.ID).toString());
        if (permissionService.isUserCanAdd(userId, URLConstant.Curriculum.COMBOS)
                && permissionService.isUserCanEdit(userId, URLConstant.Curriculum.COMBOS)) {
            subjectService.removeParentSubject(subjectId);
            return "redirect:/combo/details?id=+" + comboId + "&&curriculumId=" + curriculumId;
        }
        return ScreenConstant.Error.PAGE_401;
    }

    @PostMapping("/combo/details/update")
    public @ResponseBody ResponseEntity updateCombo(Model model, HttpSession session, @RequestBody ComboDTO comboDTO) {
        if (subjectService.checkCodeExisted(comboDTO.getCode(), comboDTO.getId(), "update")) {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
        subjectService.updateCombo(comboDTO);
        return new ResponseEntity(HttpStatus.OK);
    }
}
