package com.fpt.g2.controller;

import com.fpt.g2.constant.CommonConstant;
import com.fpt.g2.constant.ScreenConstant;
import com.fpt.g2.constant.URLConstant;
import com.fpt.g2.dto.GroupDTO;
import com.fpt.g2.dto.SubHeaderDTO;
import com.fpt.g2.entity.Curriculum;
import com.fpt.g2.entity.Group;
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
public class ContentGroupController {
    @Autowired
    CurriculumSubjectService curriculumSubjectService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private CurriculumService curriculumService;

    @Autowired
    private SubjectService subjectService;
    @Autowired
    private GroupService groupService;

    @GetMapping(URLConstant.Curriculum.GROUP)
    public String initElective(Model model, HttpSession session, @RequestParam("id") Long curriculumId) {
        Long userId = Long.valueOf(session.getAttribute(CommonConstant.ID).toString());
        List<SubHeaderDTO> curriculumPages = permissionService.getListCurriculumHeader(userId);
        if (permissionService.isUserAccessAll(userId, URLConstant.Curriculum.GROUP) || permissionService.isUserCanRead(userId, URLConstant.Curriculum.GROUP)) {
            List<Group> groups = groupService.getAllGroupByCurriculum(curriculumId);
            List<Subject> subjectSelect = curriculumSubjectService.getAllSubjectByCurriculum(curriculumId);
            Curriculum curriculum = curriculumService.findById(curriculumId);
            model.addAttribute(CommonConstant.MENU, ScreenConstant.DASHBOARD);
            model.addAttribute(CommonConstant.LOGIN_ROLES, new ArrayList<>());
            model.addAttribute(CommonConstant.CURRICULUM_PAGES, curriculumPages);
            model.addAttribute(CommonConstant.CURRICULUM_NAME, curriculum.getCode());
            model.addAttribute(CommonConstant.SUB_MENU, ScreenConstant.Curriculum.DETAILS);
            model.addAttribute(CommonConstant.SUB_CONTENT, ScreenConstant.Curriculum.GROUP);
            model.addAttribute("curriculumId", curriculumId);
            model.addAttribute("curriculum", curriculum);
            model.addAttribute("groups", groups);
            model.addAttribute("subjectSelect", subjectSelect);
            model.addAttribute("canAdd", permissionService.isUserCanAdd(userId, URLConstant.Curriculum.GROUP));
            model.addAttribute("canEdit", permissionService.isUserCanEdit(userId, URLConstant.Curriculum.GROUP));
            model.addAttribute("canDelete", permissionService.isUserCanDelete(userId, URLConstant.Curriculum.GROUP));

            model.addAttribute(CommonConstant.CURRICULUM_PAGE_TITLE, "Group");
            return ScreenConstant.HOME_PAGE;
        }
        return ScreenConstant.Error.PAGE_401;
    }

    @GetMapping("/group/details")
    public String details(Model model, HttpSession session, @RequestParam("curriculumId") Long curriculumId,
                          @RequestParam("id") Long groupId) {
        Long userId = Long.valueOf(session.getAttribute(CommonConstant.ID).toString());
        List<SubHeaderDTO> curriculumPages = permissionService.getListCurriculumHeader(userId);
        if (permissionService.isUserAccessAll(userId, URLConstant.Curriculum.GROUP) || permissionService.isUserCanRead(userId, URLConstant.Curriculum.GROUP)) {
            Curriculum curriculum = curriculumService.findById(curriculumId);
            List<Subject> childrenSubject = groupService.getSubjectByGroupAndCurriculum(groupId, curriculumId);
            List<Subject> subjectsSelect = curriculumSubjectService.getSubjectGroupNotIn(childrenSubject, curriculumId);
            Group group = groupService.findById(groupId);
            model.addAttribute(CommonConstant.MENU, ScreenConstant.DASHBOARD);
            model.addAttribute(CommonConstant.LOGIN_ROLES, new ArrayList<>());
            model.addAttribute(CommonConstant.CURRICULUM_PAGES, curriculumPages);
            model.addAttribute(CommonConstant.CURRICULUM_NAME, curriculum.getCode());
            model.addAttribute("curriculum", curriculum);
            model.addAttribute("curriculumId", curriculumId);
            model.addAttribute("childrenSubject", childrenSubject);
            model.addAttribute("subjectsSelect", subjectsSelect);
            model.addAttribute("group", group);
            model.addAttribute("canAdd", permissionService.isUserCanAdd(userId, URLConstant.Curriculum.GROUP));
            model.addAttribute("canEdit", permissionService.isUserCanEdit(userId, URLConstant.Curriculum.GROUP));
            model.addAttribute("canDelete", permissionService.isUserCanDelete(userId, URLConstant.Curriculum.GROUP));
            model.addAttribute(CommonConstant.SUB_MENU, ScreenConstant.Curriculum.DETAILS);
            model.addAttribute(CommonConstant.SUB_CONTENT, "curriculum/group/details");
            model.addAttribute(CommonConstant.CURRICULUM_PAGE_TITLE, "Group");
            return ScreenConstant.HOME_PAGE;
        }
        return ScreenConstant.Error.PAGE_401;
    }

    @PostMapping("/group/add")
    public @ResponseBody ResponseEntity addElective(Model model, HttpSession session, @RequestBody GroupDTO group) {
        Long userId = Long.valueOf(session.getAttribute(CommonConstant.ID).toString());
        if (groupService.checkCodeExisted(group.getCode(), group.getId(), "add")) {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
        groupService.addNewGroup(group);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/group/detail/insert")
    public String addSubjectTo(Model model, HttpSession session, @ModelAttribute GroupDTO groupDTO) {
        Long userId = Long.valueOf(session.getAttribute(CommonConstant.ID).toString());
        if (permissionService.isUserCanAdd(userId, URLConstant.Curriculum.GROUP)
                && permissionService.isUserCanEdit(userId, URLConstant.Curriculum.GROUP)) {

            curriculumSubjectService.addSubjectForGroup(groupDTO.getSubjectIds(), groupDTO.getCurriculumId(), groupDTO.getId());
            return "redirect:/group/details?id=" + groupDTO.getId() + "&&curriculumId=" + groupDTO.getCurriculumId();
        }
        return ScreenConstant.Error.PAGE_401;
    }

    @GetMapping("/group/detail/remove")
    public String removeSubject(Model model, HttpSession session, @RequestParam("sid") Long subjectId,
                                @RequestParam("gid") Long groupId, @RequestParam("cid") Long curriculumId) {
        Long userId = Long.valueOf(session.getAttribute(CommonConstant.ID).toString());
        if (permissionService.isUserCanAdd(userId, URLConstant.Curriculum.GROUP)
                && permissionService.isUserCanEdit(userId, URLConstant.Curriculum.GROUP)) {
            curriculumSubjectService.removeGroupSubject(subjectId, curriculumId);
            return "redirect:/group/details?id=" + groupId + "&&curriculumId=" + curriculumId;
        }
        return ScreenConstant.Error.PAGE_401;
    }

    @PostMapping("/group/detail/update")
    public @ResponseBody ResponseEntity updateGroup(@RequestBody GroupDTO group) {
        if (groupService.checkCodeExisted(group.getCode(), group.getId(), "update")) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        groupService.updateGroup(group);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/group/delete")
    public @ResponseBody ResponseEntity updateGroup(@RequestParam Long id) {
        groupService.deleteGroup(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
