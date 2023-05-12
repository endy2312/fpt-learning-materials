package com.fpt.g2.controller;

import com.fpt.g2.constant.CommonConstant;
import com.fpt.g2.constant.ScreenConstant;
import com.fpt.g2.constant.URLConstant;
import com.fpt.g2.dto.*;
import com.fpt.g2.entity.Clo;
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
public class SyllabusController {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private SyllabusService syllabusService;

    @Autowired
    private CloService cloService;

    @Autowired
    private SettingService settingService;

    @Autowired
    private UserService userService;

    @Autowired
    private CloPloService cloPloService;

    @GetMapping(URLConstant.Syllabus.SYLLABUS_LIST)
    public String initListSyllabus(Model model, SyllabiRequestDTO request, HttpSession session) {
        if (session.getAttribute(CommonConstant.ID) != null) {
            Long userId = Long.valueOf(session.getAttribute(CommonConstant.ID).toString());
            if (permissionService.isUserAccessAll(userId, URLConstant.Syllabus.SYLLABUS_LIST)
                    || permissionService.isUserCanRead(userId, URLConstant.Syllabus.SYLLABUS_LIST)) {

                List<String> loginRoles = (List<String>) session.getAttribute(CommonConstant.LOGIN_ROLES);
                String status = null;
                if (!loginRoles.contains("Syllabus Reviewer") && !permissionService.isUserCanAdd(userId, URLConstant.Syllabus.SYLLABUS_LIST)
                        && !permissionService.isUserCanEdit(userId, URLConstant.Syllabus.SYLLABUS_LIST)
                        && !permissionService.isUserCanDelete(userId, URLConstant.Syllabus.SYLLABUS_LIST)) {
                    status = "Approved";
                }

                if(request.getSortBy().toString().split(":")[0].equals("updatedDate")) {
                    request.setSortBy("subject.code");
                    request.setSortType("asc");
                }
                Page syllabi = syllabusService.getAllSyllabus(status, request);
                model.addAttribute("status", syllabusService.getAllStatus());
                model.addAttribute("canAdd", permissionService.isUserCanAdd(userId, URLConstant.Syllabus.SYLLABUS_LIST));
                model.addAttribute("canEdit", permissionService.isUserCanEdit(userId, URLConstant.Syllabus.SYLLABUS_LIST));
                model.addAttribute("canDelete", permissionService.isUserCanDelete(userId, URLConstant.Syllabus.SYLLABUS_LIST));
                model.addAttribute("syllabi", syllabi);
                model.addAttribute("request", request);
                model.addAttribute(CommonConstant.MENU, "Syllabus");
                model.addAttribute(CommonConstant.SUB_MENU, ScreenConstant.Syllabus.LIST);
                return ScreenConstant.HOME_PAGE;
            }
            return ScreenConstant.Error.PAGE_401;
        }
        return ScreenConstant.Error.PAGE_403;
    }

    @GetMapping(URLConstant.Syllabus.SYLLABUS)
    public String initOverview(Model model, @RequestParam("id") Long id, HttpSession session) {
        Long userId = null;
        List<SubHeaderDTO> syllabusPages = null;
        if (session.getAttribute(CommonConstant.ID) != null) {
            userId = Long.valueOf(session.getAttribute(CommonConstant.ID).toString());
            syllabusPages = permissionService.getListSyllabusHeader(userId);
        } else {
            syllabusPages = permissionService.getListSyllabusHeaderForGuest();
        }
        model.addAttribute("syllabusPages", syllabusPages);
        SyllabusOverviewDTO syllabus = syllabusService.getSyllabusById(id);
        model.addAttribute("syllabus", syllabus);
        model.addAttribute(CommonConstant.MENU, ScreenConstant.DASHBOARD);
        model.addAttribute(CommonConstant.SUB_MENU, ScreenConstant.Syllabus.DETAILS);
        model.addAttribute("canDelete", permissionService.isUserCanDelete(userId, URLConstant.Syllabus.SYLLABUS));
        model.addAttribute("canEdit", permissionService.isUserCanEdit(userId, URLConstant.Syllabus.SYLLABUS));
        model.addAttribute(CommonConstant.LOGIN_ROLES, session.getAttribute(CommonConstant.LOGIN_ROLES));
        model.addAttribute(CommonConstant.CURRICULUM_PAGE_TITLE, "Overview");
        model.addAttribute(CommonConstant.CURRICULUM_PAGE_ACTION, "");
        model.addAttribute("syllabusName", syllabus.getSyllabus().getSubject().getCode());
        model.addAttribute("syllabusId", syllabus.getSyllabus().getId());
        model.addAttribute(CommonConstant.SUB_CONTENT, ScreenConstant.Syllabus.OVERVIEW);
        return ScreenConstant.HOME_PAGE;
    }

    @GetMapping(URLConstant.Syllabus.SYLLABUS_STATUS_CHANGE)
    public String changeSyllabusStatus(@RequestParam("id") Long id, @RequestParam("status") String status, HttpSession session) {
        if (session.getAttribute(CommonConstant.ID) != null) {
            Long userId = Long.valueOf(session.getAttribute(CommonConstant.ID).toString());
            if (permissionService.isUserCanEdit(userId, URLConstant.Syllabus.SYLLABUS_LIST)) {
                syllabusService.changeSyllabusStatus(id, status);
                return "redirect:/syllabi";
            }
            return ScreenConstant.Error.PAGE_401;
        }
        return ScreenConstant.Error.PAGE_403;
    }

    @GetMapping(URLConstant.Syllabus.OVERVIEW_DETAILS)
    public String initOverviewDetail(Model model, @RequestParam(value = "id", required = false) Long id, HttpSession session) {
        if (session.getAttribute(CommonConstant.ID) != null) {
            Long userId = Long.valueOf(session.getAttribute(CommonConstant.ID).toString());
            List<SubHeaderDTO> syllabusPages = permissionService.getListSyllabusHeader(userId);
            model.addAttribute("syllabusPages", syllabusPages);

            if (permissionService.isUserAccessAll(userId, URLConstant.Syllabus.SYLLABUS)
                    || permissionService.isUserCanRead(userId, URLConstant.Syllabus.SYLLABUS)) {
                Syllabus syllabus = syllabusService.getSyllabus(id);
                model.addAttribute("syllabus", syllabus);
                model.addAttribute("designers", userService.getAllDesigner());
                model.addAttribute("reviewers", userService.getAllReviewer());
                model.addAttribute(CommonConstant.CURRICULUM_PAGE_TITLE, "Overview");
                model.addAttribute(CommonConstant.CURRICULUM_PAGE_ACTION, "Details");
                model.addAttribute("syllabusName", syllabus.getSubject().getCode());
                model.addAttribute("syllabusId", syllabus.getId());
                model.addAttribute(CommonConstant.SUB_MENU, ScreenConstant.Syllabus.DETAILS);
                model.addAttribute(CommonConstant.SUB_CONTENT, "syllabus/overview/details");
                model.addAttribute("settings", settingService.getDegreeLevels());
                model.addAttribute(CommonConstant.MENU, ScreenConstant.DASHBOARD);
                return ScreenConstant.HOME_PAGE;
            }
            return ScreenConstant.Error.PAGE_401;
        }
        return ScreenConstant.Error.PAGE_403;
    }

    @PostMapping("/syllabus/update")
    public String insertOverviewDetail(Model model, @RequestParam(value = "id", required = false) Long id, @ModelAttribute SyllabusRequestDTO request, HttpSession session) {
        if (session.getAttribute(CommonConstant.ID) != null) {
            Long userId = Long.valueOf(session.getAttribute(CommonConstant.ID).toString());
            List<SubHeaderDTO> syllabusPages = permissionService.getListSyllabusHeader(userId);
            model.addAttribute("syllabusPages", syllabusPages);

            if (permissionService.isUserCanEdit(userId, URLConstant.Syllabus.SYLLABUS)) {
                syllabusService.updateSyllabus(id, request);
                return "redirect:/syllabus/overview?id=" + id;
            }
            return ScreenConstant.Error.PAGE_401;
        }
        return ScreenConstant.Error.PAGE_403;
    }

    @GetMapping(URLConstant.Syllabus.SYLLABUS_CLOS)
    public String initClos(Model model, @RequestParam("id") Long id, HttpSession session, RequestCommonDTO request){
        Long userId = null;
        List<SubHeaderDTO> syllabusPages = null;
        if (session.getAttribute(CommonConstant.ID) != null) {
            userId = Long.valueOf(session.getAttribute(CommonConstant.ID).toString());
            syllabusPages = permissionService.getListSyllabusHeader(userId);
            model.addAttribute("canDelete", permissionService.isUserCanDelete(userId, URLConstant.Syllabus.SYLLABUS_CLOS));
            model.addAttribute("canEdit", permissionService.isUserCanEdit(userId, URLConstant.Syllabus.SYLLABUS_CLOS));
            model.addAttribute("canAdd", permissionService.isUserCanAdd(userId, URLConstant.Syllabus.SYLLABUS_CLOS));
        } else {
            syllabusPages = permissionService.getListSyllabusHeaderForGuest();
        }
        model.addAttribute("syllabusPages", syllabusPages);
        if(request.getSortBy().toString().split(":")[0].equals("updatedDate")) {
            request.setSortBy("code");
            request.setSortType("asc");
        }
        List<Clo> clos = cloService.getAllBySyllabus(id, request);
        model.addAttribute("request", request);
        model.addAttribute("clos", clos);
        Syllabus syllabus = syllabusService.getSyllabus(id);
        model.addAttribute("syllabusName", syllabus.getSubject().getCode());
        model.addAttribute("syllabusId", syllabus.getId());
        model.addAttribute(CommonConstant.MENU, ScreenConstant.DASHBOARD);
        model.addAttribute(CommonConstant.SUB_MENU, ScreenConstant.Syllabus.DETAILS);
        model.addAttribute(CommonConstant.CURRICULUM_PAGE_TITLE, "CLOs");
        model.addAttribute(CommonConstant.CURRICULUM_PAGE_ACTION, "");
        model.addAttribute(CommonConstant.SUB_CONTENT, ScreenConstant.Syllabus.CLOS);

        return ScreenConstant.HOME_PAGE;
    }

    @GetMapping("/syllabus/clo/delete")
    public String deleteCLO(Model model, @RequestParam("id") Long id, HttpSession session){
        if(session.getAttribute(CommonConstant.ID) != null){
            Long userId = (Long) session.getAttribute(CommonConstant.ID);
            if(permissionService.isUserCanDelete(userId, URLConstant.Syllabus.SYLLABUS_CLOS)) {
                Long syllabusId = cloService.deleteCLO(id);
                return "redirect:/syllabus/clos?id=" + syllabusId;
            }
            return ScreenConstant.Error.PAGE_401;
        }
        return ScreenConstant.Error.PAGE_403;
    }

    @PostMapping("/syllabus/clo/update")
    public String editCLO(Model model, @ModelAttribute Clo clo, HttpSession session){
        if(session.getAttribute(CommonConstant.ID) != null){
            Long userId = (Long) session.getAttribute(CommonConstant.ID);
            if(permissionService.isUserCanDelete(userId, URLConstant.Syllabus.SYLLABUS_CLOS)) {
                Long syllabusId = cloService.updateCLO(clo);
                return "redirect:/syllabus/clos?id=" + syllabusId;
            }
            return ScreenConstant.Error.PAGE_401;
        }
        return ScreenConstant.Error.PAGE_403;
    }

    @PostMapping("/syllabus/clo/add")
    public String addCLO(Model model, @ModelAttribute CLORequestDTO request, HttpSession session){
        if(session.getAttribute(CommonConstant.ID) != null){
            Long userId = (Long) session.getAttribute(CommonConstant.ID);
            if(permissionService.isUserCanDelete(userId, URLConstant.Syllabus.SYLLABUS_CLOS)) {
                cloService.addNewCLO(request);
                return "redirect:/syllabus/clos?id=" + request.getSyllabusId();
            }
            return ScreenConstant.Error.PAGE_401;
        }
        return ScreenConstant.Error.PAGE_403;
    }

    @PostMapping("/syllabus/clo/checkcode")
    public @ResponseBody ResponseEntity checkCodeCLOExisted(@RequestBody CLORequestDTO request){
        if(cloService.isCLOExisted(request.getSyllabusId(), request.getCloCode())){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(URLConstant.Syllabus.SYLLABUS_CLO_PLOS)
    public String initCloPloMapping(Model model, @RequestParam("id") Long id, HttpSession session){
        Long userId = null;
        List<SubHeaderDTO> syllabusPages = null;
        if (session.getAttribute(CommonConstant.ID) != null) {
            userId = Long.valueOf(session.getAttribute(CommonConstant.ID).toString());
            syllabusPages = permissionService.getListSyllabusHeader(userId);
            model.addAttribute("canEdit", permissionService.isUserCanEdit(userId, URLConstant.Syllabus.SYLLABUS_CLO_PLOS));
        } else {
            syllabusPages = permissionService.getListSyllabusHeaderForGuest();
        }
        Syllabus syllabus = syllabusService.getSyllabus(id);
        List<Clo> clos = cloService.getClosBySyllabus(id);
        List<CloPloResponseDTO> responses = cloPloService.getCloPLoMapping(id);

        model.addAttribute("clos", clos);
        model.addAttribute("cloPlos", responses);
        model.addAttribute("syllabusPages", syllabusPages);
        model.addAttribute("syllabusName", syllabus.getSubject().getCode());
        model.addAttribute("syllabusId", syllabus.getId());
        model.addAttribute(CommonConstant.MENU, ScreenConstant.DASHBOARD);
        model.addAttribute(CommonConstant.SUB_MENU, ScreenConstant.Syllabus.DETAILS);
        model.addAttribute(CommonConstant.CURRICULUM_PAGE_TITLE, "CLO-PLOs");
        model.addAttribute(CommonConstant.CURRICULUM_PAGE_ACTION, "");
        model.addAttribute(CommonConstant.SUB_CONTENT, ScreenConstant.Syllabus.CLO_PLOS);
        return ScreenConstant.HOME_PAGE;
    }

    @GetMapping(URLConstant.Syllabus.CLO_PLO_DETAILS)
    public String initCloPloMappingDetails(Model model, @RequestParam("id") Long id, HttpSession session){
        Long userId = null;
        List<SubHeaderDTO> syllabusPages = null;
        if (session.getAttribute(CommonConstant.ID) != null) {
            userId = Long.valueOf(session.getAttribute(CommonConstant.ID).toString());
            syllabusPages = permissionService.getListSyllabusHeader(userId);
            model.addAttribute("canEdit", permissionService.isUserCanEdit(userId, URLConstant.Syllabus.SYLLABUS_CLO_PLOS));
        } else {
            syllabusPages = permissionService.getListSyllabusHeaderForGuest();
        }
        Syllabus syllabus = syllabusService.getSyllabus(id);
        List<Clo> clos = cloService.getClosBySyllabus(id);
        List<CloPloResponseDTO> responses = cloPloService.getCloPLoMapping(id);

        model.addAttribute("clos", clos);
        model.addAttribute("cloPlos", responses);
        model.addAttribute("syllabusPages", syllabusPages);
        model.addAttribute("syllabusName", syllabus.getSubject().getCode());
        model.addAttribute("syllabusId", syllabus.getId());
        model.addAttribute(CommonConstant.MENU, ScreenConstant.DASHBOARD);
        model.addAttribute(CommonConstant.SUB_MENU, ScreenConstant.Syllabus.DETAILS);
        model.addAttribute(CommonConstant.CURRICULUM_PAGE_TITLE, "CLO-PLOs");
        model.addAttribute(CommonConstant.CURRICULUM_PAGE_ACTION, "Details");
        model.addAttribute(CommonConstant.SUB_CONTENT, "syllabus/cloplo/details");
        return ScreenConstant.HOME_PAGE;
    }

    @PostMapping("/syllabus/cloplo/update")
    public @ResponseBody ResponseEntity updateCloPlo(Model model, @RequestBody CloPloListDTO request, HttpSession session){
        if (session.getAttribute(CommonConstant.ID) != null) {
            Long userId = Long.valueOf(session.getAttribute(CommonConstant.ID).toString());
            if(permissionService.isUserCanEdit(userId, URLConstant.Syllabus.SYLLABUS_CLO_PLOS)){
                cloPloService.updateCloPloMapping(request);
                return new ResponseEntity(HttpStatus.OK);
            }
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.FORBIDDEN);
    }
}
