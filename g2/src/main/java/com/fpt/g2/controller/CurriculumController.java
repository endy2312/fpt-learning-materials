package com.fpt.g2.controller;

import com.fpt.g2.constant.CommonConstant;
import com.fpt.g2.constant.ScreenConstant;
import com.fpt.g2.constant.URLConstant;
import com.fpt.g2.dto.*;
import com.fpt.g2.entity.*;
import com.fpt.g2.service.*;
import com.fpt.g2.utils.CommonUtils;
import com.fpt.g2.utils.curriculum.CurriculumUtils;
import com.fpt.g2.utils.plo.PLOUtils;
import com.fpt.g2.utils.po.POUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CurriculumController {
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private CurriculumService curriculumService;
    @Autowired
    private PLOService ploService;
    @Autowired
    private POService poService;
    @Autowired
    private CurriculumSubjectService curriculumSubjectService;
    @Autowired
    private SubjectPLOService subjectPLOService;
    @Autowired
    private UserService userService;
    @Autowired
    private DecisionService decisionService;
    @Autowired
    private PoPloService poPloService;

    @Autowired
    private SubjectService subjectService;

    @GetMapping(URLConstant.Curriculum.SUBJECTS)
    public String initSubjects(Model model, @ModelAttribute CSubjectRequestDTO request, HttpSession session) {
        Long userId = null;
        List<SubHeaderDTO> curriculumPages = null;
        if (session.getAttribute(CommonConstant.ID) != null) {
            userId = Long.valueOf(session.getAttribute(CommonConstant.ID).toString());
            curriculumPages = permissionService.getListCurriculumHeader(userId);
        } else {
            curriculumPages = permissionService.getListCurriculumHeaderForGuest();
        }
        Boolean status = false;
        if (userId != null) {
            status = permissionService.isUserCanEdit(userId, URLConstant.Curriculum.SUBJECTS);
            model.addAttribute("canEdit", permissionService.isUserCanEdit(userId, URLConstant.Curriculum.SUBJECTS));
            model.addAttribute("canAdd", permissionService.isUserCanAdd(userId, URLConstant.Curriculum.SUBJECTS));
        }
        List<CSubjectResponseDTO> subjects = curriculumSubjectService.getCurriculumSubjects(status, request.getId(), request);
        Curriculum curriculum = curriculumService.findById(request.getId());
        List<Subject> subjectList = subjectService.getSubjectsOutsideCurriculum(request.getId());
        List<Group> groups = curriculumSubjectService.getGroupsByCurriculum(request.getId());
        model.addAttribute("groups", groups);
        model.addAttribute("subjects", subjectList);
        model.addAttribute("curriculumSubjects", subjects);
        model.addAttribute(CommonConstant.MENU, ScreenConstant.DASHBOARD);
        model.addAttribute(CommonConstant.SUB_MENU, ScreenConstant.Curriculum.DETAILS);
        model.addAttribute(CommonConstant.LOGIN_ROLES, new ArrayList<>());
        model.addAttribute(CommonConstant.CURRICULUM_PAGES, curriculumPages);
        model.addAttribute(CommonConstant.CURRICULUM_PAGE_TITLE, "Subjects");
        model.addAttribute(CommonConstant.CURRICULUM_PAGE_ACTION, "");
        model.addAttribute(CommonConstant.CURRICULUM_NAME, curriculum.getCode());
        model.addAttribute("curriculumId", curriculum.getId());
        model.addAttribute(CommonConstant.REQUEST, request);
        model.addAttribute(CommonConstant.SUB_CONTENT, ScreenConstant.Curriculum.SUBJECTS);
        return ScreenConstant.HOME_PAGE;
    }

    @GetMapping(URLConstant.Curriculum.CURRICULUMS_LIST)
    public String init(Model model, @ModelAttribute RequestCommonDTO request, @RequestParam(value = "status", required = false) String status, HttpSession session) {
        Long userId = null;
        Permission permission = new Permission();
        if (session.getAttribute(CommonConstant.ID) != null) {
            userId = Long.valueOf(session.getAttribute(CommonConstant.ID).toString());
            permission.setAccessAll(permissionService.isUserAccessAll(userId, URLConstant.Curriculum.CURRICULUMS_LIST));
            permission.setCanRead(permissionService.isUserCanRead(userId, URLConstant.Curriculum.CURRICULUMS_LIST));
            permission.setCanAdd(permissionService.isUserCanAdd(userId, URLConstant.Curriculum.CURRICULUMS_LIST));
            permission.setCanEdit(permissionService.isUserCanEdit(userId, URLConstant.Curriculum.CURRICULUMS_LIST));
            permission.setCanDelete(permissionService.isUserCanDelete(userId, URLConstant.Curriculum.CURRICULUMS_LIST));
            model.addAttribute("permission", permission);
        }
        List<String> loginRoles = (List<String>) session.getAttribute(CommonConstant.LOGIN_ROLES);
        Page<Curriculum> curriculums = null;
        if (loginRoles.contains("CRDD Staff") && !loginRoles.contains("Admin")) {
            curriculums = curriculumService.getAllCurriculum(request, status, userId);
        } else {
            curriculums = curriculumService.getAllCurriculum(request, status, null);
        }

        model.addAttribute(CommonConstant.LOGIN_ROLES, session.getAttribute(CommonConstant.LOGIN_ROLES));

        model.addAttribute("status", status);
        model.addAttribute("curriculums", curriculums);
        model.addAttribute("request", request);
        model.addAttribute(CommonConstant.MENU, ScreenConstant.DASHBOARD);
        model.addAttribute(CommonConstant.SUB_MENU, ScreenConstant.Curriculum.CURRICULUMS);
        model.addAttribute("pUrl", URLConstant.Curriculum.CURRICULUMS_LIST);
        return ScreenConstant.HOME_PAGE;


    }

    @GetMapping(URLConstant.Curriculum.OVERVIEW)
    public String initOverview(Model model, @RequestParam Long id, HttpSession session) {
        Long userId;
        List<SubHeaderDTO> curriculumPages = null;
        if (session.getAttribute(CommonConstant.ID) != null) {
            userId = Long.valueOf(session.getAttribute(CommonConstant.ID).toString());
            curriculumPages = permissionService.getListCurriculumHeader(userId);
        } else {
            curriculumPages = permissionService.getListCurriculumHeaderForGuest();
        }
        Curriculum curriculum = curriculumService.findById(id);
        session.setAttribute(CommonConstant.CURRICULUM_NAME, curriculum.getCode());
        model.addAttribute("curriculum", curriculum);
        model.addAttribute(CommonConstant.MENU, ScreenConstant.DASHBOARD);
        model.addAttribute(CommonConstant.SUB_MENU, ScreenConstant.Curriculum.DETAILS);
        model.addAttribute("pUrl", URLConstant.Curriculum.CURRICULUMS_LIST);

        model.addAttribute(CommonConstant.LOGIN_ROLES, session.getAttribute(CommonConstant.LOGIN_ROLES));
        model.addAttribute(CommonConstant.CURRICULUM_PAGES, curriculumPages);
        model.addAttribute(CommonConstant.CURRICULUM_PAGE_TITLE, "Overview");
        model.addAttribute(CommonConstant.CURRICULUM_PAGE_ACTION, "");
        model.addAttribute(CommonConstant.CURRICULUM_NAME, curriculum.getCode());
        model.addAttribute("curriculumId", curriculum.getId());
        model.addAttribute(CommonConstant.SUB_CONTENT, ScreenConstant.Curriculum.OVERVIEW);

        session.setAttribute("curriculumId", curriculum.getId());
        return ScreenConstant.HOME_PAGE;
    }

    @GetMapping(URLConstant.Curriculum.PLOs)
    public String initPloList(Model model, @RequestParam Long id, HttpSession session) {
        Long userId;
        List<SubHeaderDTO> curriculumPages = null;
        if (session.getAttribute(CommonConstant.ID) != null) {
            userId = Long.valueOf(session.getAttribute(CommonConstant.ID).toString());
            curriculumPages = permissionService.getListCurriculumHeader(userId);
        } else {
            curriculumPages = permissionService.getListCurriculumHeaderForGuest();
        }
        model.addAttribute("ploList", ploService.getPloListByCurriculumId(id));
        model.addAttribute(CommonConstant.MENU, ScreenConstant.DASHBOARD);
        model.addAttribute(CommonConstant.SUB_MENU, ScreenConstant.Curriculum.DETAILS);
        model.addAttribute("pUrl", URLConstant.Curriculum.CURRICULUMS_LIST);
        model.addAttribute("curriculumId", id);
        model.addAttribute(CommonConstant.LOGIN_ROLES, session.getAttribute(CommonConstant.LOGIN_ROLES));
        model.addAttribute(CommonConstant.ACTION_TYPE, "isView");
        model.addAttribute(CommonConstant.CURRICULUM_PAGES, curriculumPages);
        model.addAttribute(CommonConstant.CURRICULUM_PAGE_TITLE, "PLOs");
        model.addAttribute(CommonConstant.CURRICULUM_PAGE_ACTION, "");
        model.addAttribute(CommonConstant.CURRICULUM_NAME, session.getAttribute(CommonConstant.CURRICULUM_NAME));
        model.addAttribute(CommonConstant.SUB_CONTENT, ScreenConstant.Curriculum.PLOs);
        return ScreenConstant.HOME_PAGE;
    }

    @GetMapping(URLConstant.Curriculum.POs)
    public String initPoList(Model model, @RequestParam Long id, HttpSession session) {
        Long userId;
        List<SubHeaderDTO> curriculumPages = null;
        if (session.getAttribute(CommonConstant.ID) != null) {
            userId = Long.valueOf(session.getAttribute(CommonConstant.ID).toString());
            curriculumPages = permissionService.getListCurriculumHeader(userId);
        } else {
            curriculumPages = permissionService.getListCurriculumHeaderForGuest();
        }
        model.addAttribute("poList", poService.getPoListByCurriculumId(id));
        model.addAttribute(CommonConstant.MENU, ScreenConstant.DASHBOARD);
        model.addAttribute(CommonConstant.SUB_MENU, ScreenConstant.Curriculum.DETAILS);
        model.addAttribute("pUrl", URLConstant.Curriculum.CURRICULUMS_LIST);
        model.addAttribute("curriculumId", id);
        model.addAttribute(CommonConstant.LOGIN_ROLES, session.getAttribute(CommonConstant.LOGIN_ROLES));
        model.addAttribute(CommonConstant.ACTION_TYPE, "isView");
        model.addAttribute(CommonConstant.CURRICULUM_PAGES, curriculumPages);
        model.addAttribute(CommonConstant.CURRICULUM_PAGE_TITLE, "POs");
        model.addAttribute(CommonConstant.CURRICULUM_PAGE_ACTION, "");
        model.addAttribute(CommonConstant.CURRICULUM_NAME, session.getAttribute(CommonConstant.CURRICULUM_NAME));
        model.addAttribute(CommonConstant.SUB_CONTENT, ScreenConstant.Curriculum.POs);
        return ScreenConstant.HOME_PAGE;
    }

    @RequestMapping(URLConstant.Curriculum.CURRICULUM + URLConstant.INSERT)
    public String saveOrUpdate(Model model, @ModelAttribute CurriculumRequestDTO curriculumForm, HttpSession session) {
        if (session.getAttribute(CommonConstant.ID) != null) {
            Long userId = Long.valueOf(session.getAttribute(CommonConstant.ID).toString());
            if (permissionService.isUserAccessAll(userId, URLConstant.Curriculum.CURRICULUMS_LIST)
                    || permissionService.isUserCanRead(userId, URLConstant.Curriculum.CURRICULUMS_LIST)) {

                String page = null;
                String actionType = null;
                if (curriculumForm.getId() != null) {
                    if (CommonUtils.isNullOrEmpty(curriculumForm.getCode()) && CommonUtils.isNullOrEmpty(curriculumForm.getName())) {
                        curriculumForm = CurriculumUtils.convertToCurriculumDTO(curriculumService.findById(curriculumForm.getId()));
                        actionType = "isUpdate";
                    } else {
                        if (permissionService.isUserCanEdit(userId, URLConstant.Curriculum.CURRICULUMS_LIST)) {
                            page = curriculumService.saveOrUpdate(curriculumForm, userId);
                        }
                    }
                } else {
                    if (CommonUtils.isNullOrEmpty(curriculumForm.getCode()) && CommonUtils.isNullOrEmpty(curriculumForm.getName())) {
                        curriculumForm = new CurriculumRequestDTO();
                        curriculumForm.setIsActive("deactivate");
                        actionType = "isCreate";
                    } else {
                        if (permissionService.isUserCanAdd(userId, URLConstant.Curriculum.CURRICULUMS_LIST)) {
                            page = curriculumService.saveOrUpdate(curriculumForm, userId);
                        }
                    }
                }
                List<SubHeaderDTO> curriculumPages = permissionService.getListCurriculumHeader(userId);
                List<User> staffList = userService.getAllStaff();
                List<Decision> decisions = decisionService.getDecisions();
                model.addAttribute(CommonConstant.ACTION_TYPE, actionType);
                model.addAttribute("curriculumForm", curriculumForm);

                model.addAttribute("staffList", staffList);
                model.addAttribute("decisions", decisions);

                model.addAttribute(CommonConstant.LOGIN_ROLES, session.getAttribute(CommonConstant.LOGIN_ROLES));
                model.addAttribute(CommonConstant.MENU, ScreenConstant.DASHBOARD);
                model.addAttribute(CommonConstant.SUB_MENU, ScreenConstant.Curriculum.DETAILS);
                model.addAttribute("pUrl", URLConstant.Curriculum.CURRICULUMS_LIST);

                model.addAttribute(CommonConstant.CURRICULUM_PAGES, curriculumPages);
                model.addAttribute(CommonConstant.CURRICULUM_PAGE_TITLE, "Overview");
                model.addAttribute(CommonConstant.CURRICULUM_PAGE_ACTION, "");
                model.addAttribute(CommonConstant.CURRICULUM_NAME, session.getAttribute(CommonConstant.CURRICULUM_NAME));
                model.addAttribute(CommonConstant.SUB_CONTENT, ScreenConstant.Curriculum.INSERT);

                if (!CommonUtils.isNullOrEmpty(page) && page.equalsIgnoreCase(CommonConstant.CODE_EXIST)) {
                    model.addAttribute("message", "Curriculum code existed!");
                }

                if (!CommonUtils.isNullOrEmpty(page) && (page.equalsIgnoreCase(CommonConstant.ADD_SUCCESS) || page.equalsIgnoreCase(CommonConstant.UPDATE_SUCCESS))) {
                    session.removeAttribute(CommonConstant.CURRICULUM_NAME);
                    return "redirect:/curriculums";
                }

                return ScreenConstant.HOME_PAGE;
            }
            return ScreenConstant.Error.PAGE_401;
        }
        return ScreenConstant.Error.PAGE_403;
    }

    @GetMapping(URLConstant.Curriculum.CURRICULUM_CLONE)
    public String clone(Model model, @RequestParam(name = "id") Long id, HttpSession session) {
        if (session.getAttribute(CommonConstant.ID) != null) {
            Long userId = Long.valueOf(session.getAttribute(CommonConstant.ID).toString());
            if (permissionService.isUserAccessAll(userId, URLConstant.Curriculum.CURRICULUMS_LIST)
                    || permissionService.isUserCanRead(userId, URLConstant.Curriculum.CURRICULUMS_LIST)) {

                String page = null;
                String actionType = null;
                CurriculumRequestDTO curriculumForm = null;
                if (id != null) {
                        curriculumForm = CurriculumUtils.convertToCurriculumDTO(curriculumService.findById(id));
                        String codeClone = curriculumForm.getCode().concat("_clone");
                        curriculumForm.setCode(codeClone);
                        curriculumForm.setDecisionId(null);
                        curriculumForm.setDecisionNo(null);
                        curriculumForm.setIsActive("deactivate");
                        curriculumForm.setUserId(null);
                        curriculumForm.setId(null);
                        actionType = "isClone";
                }
                List<SubHeaderDTO> curriculumPages = permissionService.getListCurriculumHeader(userId);
                List<User> staffList = userService.getAllStaff();
                List<Decision> decisions = decisionService.getDecisions();
                model.addAttribute(CommonConstant.ACTION_TYPE, actionType);
                model.addAttribute("curriculumForm", curriculumForm);

                model.addAttribute("staffList", staffList);
                model.addAttribute("decisions", decisions);

                model.addAttribute(CommonConstant.LOGIN_ROLES, session.getAttribute(CommonConstant.LOGIN_ROLES));
                model.addAttribute(CommonConstant.MENU, ScreenConstant.DASHBOARD);
                model.addAttribute(CommonConstant.SUB_MENU, ScreenConstant.Curriculum.DETAILS);
                model.addAttribute("pUrl", URLConstant.Curriculum.CURRICULUMS_LIST);

                model.addAttribute(CommonConstant.CURRICULUM_PAGES, curriculumPages);
                model.addAttribute(CommonConstant.CURRICULUM_PAGE_TITLE, "Overview");
                model.addAttribute(CommonConstant.CURRICULUM_PAGE_ACTION, "");
                model.addAttribute(CommonConstant.CURRICULUM_NAME, session.getAttribute(CommonConstant.CURRICULUM_NAME));
                model.addAttribute(CommonConstant.SUB_CONTENT, ScreenConstant.Curriculum.INSERT);

                if (!CommonUtils.isNullOrEmpty(page) && (page.equalsIgnoreCase(CommonConstant.ADD_SUCCESS) || page.equalsIgnoreCase(CommonConstant.UPDATE_SUCCESS))) {
                    session.removeAttribute(CommonConstant.CURRICULUM_NAME);
                    return "redirect:/curriculums";
                }

                return ScreenConstant.HOME_PAGE;
            }
            return ScreenConstant.Error.PAGE_401;
        }
        return ScreenConstant.Error.PAGE_403;
    }

    @GetMapping(URLConstant.Curriculum.CURRICULUM_STATUS_CHANGE)
    public String changeCurriculumStatus(Model model, @RequestParam("id") Long id, HttpSession session) {
        if (session.getAttribute(CommonConstant.ID) != null) {
            Long userId = Long.valueOf(session.getAttribute(CommonConstant.ID).toString());
            if (permissionService.isUserAccessAll(userId, URLConstant.Curriculum.CURRICULUMS_LIST) ||
                    permissionService.isUserCanRead(userId, URLConstant.Curriculum.CURRICULUMS_LIST)) {
                curriculumService.changeStatus(id, session);
                session.removeAttribute(CommonConstant.CURRICULUM_NAME);
                return "redirect:/curriculums";
            }
            return ScreenConstant.Error.PAGE_401;
        }
        return ScreenConstant.Error.PAGE_403;
    }

    @GetMapping(URLConstant.Curriculum.SUBJECT_PLOS)
    public String initSubjectPLO(Model model, @RequestParam("id") Long id, HttpSession session) {
        Long userId = null;
        List<SubHeaderDTO> curriculumPages = null;
        if (session.getAttribute(CommonConstant.ID) != null) {
            userId = Long.valueOf(session.getAttribute(CommonConstant.ID).toString());
            curriculumPages = permissionService.getListCurriculumHeader(userId);
            model.addAttribute("canEdit", permissionService.isUserCanEdit(userId, URLConstant.Curriculum.SUBJECT_PLOS));
        } else {
            curriculumPages = permissionService.getListCurriculumHeaderForGuest();
        }
        List<Plo> plos = ploService.getPloListByCurriculumId(id);
        List<SubjectPLOMappingDTO> subjects = subjectPLOService.getListSubjectPLOs(id);
        Curriculum curriculum = curriculumService.findById(id);

        model.addAttribute("ploSubjects", plos);
        model.addAttribute("subjectPLOs", subjects);
        model.addAttribute(CommonConstant.MENU, ScreenConstant.DASHBOARD);
        model.addAttribute(CommonConstant.SUB_MENU, ScreenConstant.Curriculum.DETAILS);
        model.addAttribute(CommonConstant.LOGIN_ROLES, new ArrayList<>());
        model.addAttribute(CommonConstant.CURRICULUM_PAGES, curriculumPages);
        model.addAttribute(CommonConstant.CURRICULUM_PAGE_TITLE, "Subject-PLOs");
        model.addAttribute(CommonConstant.CURRICULUM_PAGE_ACTION, "");
        model.addAttribute(CommonConstant.CURRICULUM_NAME, curriculum.getCode());
        model.addAttribute("curriculumId", curriculum.getId());
        model.addAttribute(CommonConstant.SUB_CONTENT, ScreenConstant.Curriculum.SUBJECT_PLOS);

        return ScreenConstant.HOME_PAGE;
    }

    @GetMapping(URLConstant.Curriculum.CURRICULUM_SUBJECTS_STATUS_CHANGE)
    public String changeCurriculumSubjectStatus(Model model, @RequestParam("id") Long id, HttpSession session) {
        if (session.getAttribute(CommonConstant.ID) != null) {
            Long userId = Long.valueOf(session.getAttribute(CommonConstant.ID).toString());
            if (permissionService.isUserCanEdit(userId, URLConstant.Curriculum.SUBJECTS)) {
                Long curriculumId = curriculumSubjectService.changeCurriculumSubjectStatus(userId, id);
                if (curriculumId != null) {
                    return "redirect:/curriculum/subjects?id=" + curriculumId;
                }
                return ScreenConstant.Error.PAGE_400;
            }
            return ScreenConstant.Error.PAGE_401;
        }
        return ScreenConstant.Error.PAGE_403;
    }

    @RequestMapping(URLConstant.Curriculum.SUBJECT_PLOS_UPDATE)
    public String initUpdateSubjectPLO(Model model, @RequestParam("id") Long id, HttpSession session) {
        if (session.getAttribute(CommonConstant.ID) != null) {
            Long userId = Long.valueOf(session.getAttribute(CommonConstant.ID).toString());
            List<SubHeaderDTO> curriculumPages = permissionService.getListCurriculumHeader(userId);
            if (permissionService.isUserAccessAll(userId, URLConstant.Curriculum.SUBJECT_PLOS) || permissionService.isUserCanRead(userId, URLConstant.Curriculum.SUBJECT_PLOS)) {
                List<Plo> plos = ploService.getPloListByCurriculumId(id);
                List<SubjectPLOMappingDTO> subjects = subjectPLOService.getListSubjectPLOs(id);
                Curriculum curriculum = curriculumService.findById(id);

                model.addAttribute("ploSubjects", plos);
                model.addAttribute("subjectPLOs", subjects);
                model.addAttribute(CommonConstant.MENU, ScreenConstant.DASHBOARD);
                model.addAttribute(CommonConstant.SUB_MENU, ScreenConstant.Curriculum.DETAILS);
                model.addAttribute(CommonConstant.LOGIN_ROLES, new ArrayList<>());
                model.addAttribute(CommonConstant.CURRICULUM_PAGES, curriculumPages);
                model.addAttribute(CommonConstant.CURRICULUM_PAGE_TITLE, "Subject-PLOs");
                model.addAttribute(CommonConstant.CURRICULUM_PAGE_ACTION, "Update");
                model.addAttribute(CommonConstant.CURRICULUM_NAME, curriculum.getCode());
                model.addAttribute("curriculumId", curriculum.getId());
                model.addAttribute("canEdit", permissionService.isUserCanEdit(userId, URLConstant.Curriculum.SUBJECT_PLOS));
                model.addAttribute(CommonConstant.SUB_CONTENT, ScreenConstant.Curriculum.SUBJECT_PLOS_DETAILS);

                return ScreenConstant.HOME_PAGE;
            }
            return ScreenConstant.Error.PAGE_401;
        }
        return ScreenConstant.Error.PAGE_403;
    }

    @PostMapping(URLConstant.Curriculum.SUBJECT_PLOS_UPDATE)
    @ResponseBody
    public String update(Model model, @RequestBody SubjectListPlosDTO subjectPlo, HttpSession session) {
        if (session.getAttribute(CommonConstant.ID) != null) {
            Long userId = Long.valueOf(session.getAttribute(CommonConstant.ID).toString());
            if (permissionService.isUserAccessAll(userId, URLConstant.Curriculum.SUBJECT_PLOS) || permissionService.isUserCanRead(userId, URLConstant.Curriculum.SUBJECT_PLOS)) {
                subjectPLOService.updateSubjectPLOMapping(subjectPlo);
                return "redirect:/curriculum/subjectplos?id=" + subjectPlo.getId();
            }
            return ScreenConstant.Error.PAGE_401;
        }
        return ScreenConstant.Error.PAGE_403;
    }

    @GetMapping(URLConstant.Curriculum.PLO_INSERT)
    public String getPLOInsert(Model model, @ModelAttribute CurriculumPloDTO curriculumPloDTO, HttpSession session) {
        if (session.getAttribute(CommonConstant.ID) != null) {
            Long userId = Long.valueOf(session.getAttribute(CommonConstant.ID).toString());
            List<SubHeaderDTO> curriculumPages = permissionService.getListCurriculumHeader(userId);
            String actionType = null;
            if (permissionService.isUserAccessAll(userId, URLConstant.Curriculum.PLOs)
                    || permissionService.isUserCanRead(userId, URLConstant.Curriculum.PLOs)) {
                Long curriculumId = (Long) session.getAttribute("curriculumId");
                if (curriculumPloDTO.getId() != null) {
                    actionType = "isUpdate";
                    curriculumPloDTO = PLOUtils.convertToCurriculumPOLDTO(ploService.getPLOByCurriculumIdAndPLOId(
                            curriculumId, curriculumPloDTO.getId()), curriculumPloDTO.getCurriculumId());
                } else {
                    actionType = "isCreate";
                    curriculumPloDTO = new CurriculumPloDTO();
                    curriculumPloDTO.setIsActive("deactivate");
                }

                model.addAttribute("curriculumId", curriculumId);
                model.addAttribute("ploForm", curriculumPloDTO);
                model.addAttribute(CommonConstant.MENU, ScreenConstant.DASHBOARD);
                model.addAttribute(CommonConstant.SUB_MENU, ScreenConstant.Curriculum.DETAILS);
                model.addAttribute(CommonConstant.LOGIN_ROLES, session.getAttribute(CommonConstant.LOGIN_ROLES));
                model.addAttribute(CommonConstant.ACTION_TYPE, actionType);
                model.addAttribute(CommonConstant.CURRICULUM_PAGES, curriculumPages);
                model.addAttribute(CommonConstant.CURRICULUM_PAGE_TITLE, "PLOs");
                model.addAttribute(CommonConstant.CURRICULUM_PAGE_ACTION, "");
                model.addAttribute(CommonConstant.CURRICULUM_NAME, session.getAttribute(CommonConstant.CURRICULUM_NAME));
                model.addAttribute(CommonConstant.SUB_CONTENT, ScreenConstant.Curriculum.PLOs_INSERT);
                return ScreenConstant.HOME_PAGE;
            }
            return ScreenConstant.Error.PAGE_401;
        }
        return ScreenConstant.Error.PAGE_403;
    }

    @PostMapping(URLConstant.Curriculum.PLO_INSERT)
    public String saveOrUpdatePLO(Model model, @ModelAttribute CurriculumPloDTO curriculumPloDTO, HttpSession session) {
        if (session.getAttribute(CommonConstant.ID) != null) {
            Long userId = Long.valueOf(session.getAttribute(CommonConstant.ID).toString());
            List<SubHeaderDTO> curriculumPages = permissionService.getListCurriculumHeader(userId);
            if (permissionService.isUserAccessAll(userId, URLConstant.Curriculum.PLOs)
                    || permissionService.isUserCanRead(userId, URLConstant.Curriculum.PLOs)) {
                String actionType = null;
                String message;
                if (curriculumPloDTO.getId() != null) {
                    actionType = "isUpdate";
                    message = ploService.saveOrUpdate(curriculumPloDTO);
                } else {
                    actionType = "isCreate";
                    message = ploService.saveOrUpdate(curriculumPloDTO);
                }

                if (message.equalsIgnoreCase(CommonConstant.UPDATE_SUCCESS) || message.equalsIgnoreCase(CommonConstant.ADD_SUCCESS)) {
                    return "redirect:/curriculum/plos?id=" + curriculumPloDTO.getCurriculumId();
                }

                if (message.equalsIgnoreCase(CommonConstant.CODE_EXIST)) {
                    model.addAttribute("message", "PLO name is existed!");
                }
                model.addAttribute("curriculumId", curriculumPloDTO.getCurriculumId());
                model.addAttribute("ploForm", curriculumPloDTO);
                model.addAttribute(CommonConstant.MENU, ScreenConstant.DASHBOARD);
                model.addAttribute(CommonConstant.SUB_MENU, ScreenConstant.Curriculum.DETAILS);
                model.addAttribute(CommonConstant.LOGIN_ROLES, session.getAttribute(CommonConstant.LOGIN_ROLES));
                model.addAttribute(CommonConstant.ACTION_TYPE, actionType);
                model.addAttribute(CommonConstant.CURRICULUM_PAGES, curriculumPages);
                model.addAttribute(CommonConstant.CURRICULUM_PAGE_TITLE, "PLOs");
                model.addAttribute(CommonConstant.CURRICULUM_PAGE_ACTION, "");
                model.addAttribute(CommonConstant.CURRICULUM_NAME, session.getAttribute(CommonConstant.CURRICULUM_NAME));
                model.addAttribute(CommonConstant.SUB_CONTENT, ScreenConstant.Curriculum.PLOs_INSERT);
                return ScreenConstant.HOME_PAGE;
            }
            return ScreenConstant.Error.PAGE_401;
        }
        return ScreenConstant.Error.PAGE_403;
    }

    @GetMapping(URLConstant.Curriculum.CURRICULUM_PLO_DELETE)
    public String deleteCurriculumPLO(Model model, @RequestParam("id") Long id, HttpSession session) {
        if (session.getAttribute(CommonConstant.ID) != null) {
            Long userId = Long.valueOf(session.getAttribute(CommonConstant.ID).toString());
            if (permissionService.isUserCanEdit(userId, URLConstant.Curriculum.PLOs)) {
                Long curriculumId = (Long) session.getAttribute("curriculumId");
                ploService.deleteCurriculumPLO(curriculumId, id);
                if (curriculumId != null) {
                    return "redirect:/curriculum/plos?id=" + curriculumId;
                }
                return ScreenConstant.Error.PAGE_400;
            }
            return ScreenConstant.Error.PAGE_401;
        }
        return ScreenConstant.Error.PAGE_403;
    }

    @GetMapping(URLConstant.Curriculum.PO_INSERT)
    public String getPOInsert(Model model, @ModelAttribute CurriculumPoRequestDTO curriculumPoRequestDTO, HttpSession session) {
        if (session.getAttribute(CommonConstant.ID) != null) {
            Long userId = Long.valueOf(session.getAttribute(CommonConstant.ID).toString());
            List<SubHeaderDTO> curriculumPages = permissionService.getListCurriculumHeader(userId);
            String actionType = null;
            if (permissionService.isUserAccessAll(userId, URLConstant.Curriculum.POs)
                    || permissionService.isUserCanRead(userId, URLConstant.Curriculum.POs)) {
                Long curriculumId = (Long) session.getAttribute("curriculumId");
                if (curriculumPoRequestDTO.getId() != null) {
                    actionType = "isUpdate";
                    curriculumPoRequestDTO = POUtils.convertToCurriculumPoRequestDTO(poService.getPoByCurriculumIdAndPOId(
                            curriculumId, curriculumPoRequestDTO.getId()), curriculumPoRequestDTO.getCurriculumId());
                } else {
                    actionType = "isCreate";
                    curriculumPoRequestDTO = new CurriculumPoRequestDTO();
                    curriculumPoRequestDTO.setIsActive("deactivate");
                }

                model.addAttribute("curriculumId", curriculumId);
                model.addAttribute("poForm", curriculumPoRequestDTO);
                model.addAttribute(CommonConstant.MENU, ScreenConstant.DASHBOARD);
                model.addAttribute(CommonConstant.SUB_MENU, ScreenConstant.Curriculum.DETAILS);
                model.addAttribute(CommonConstant.LOGIN_ROLES, session.getAttribute(CommonConstant.LOGIN_ROLES));
                model.addAttribute(CommonConstant.ACTION_TYPE, actionType);
                model.addAttribute(CommonConstant.CURRICULUM_PAGES, curriculumPages);
                model.addAttribute(CommonConstant.CURRICULUM_PAGE_TITLE, "POs");
                model.addAttribute(CommonConstant.CURRICULUM_PAGE_ACTION, "");
                model.addAttribute(CommonConstant.CURRICULUM_NAME, session.getAttribute(CommonConstant.CURRICULUM_NAME));
                model.addAttribute(CommonConstant.SUB_CONTENT, ScreenConstant.Curriculum.POs_INSERT);
                return ScreenConstant.HOME_PAGE;
            }
            return ScreenConstant.Error.PAGE_401;
        }
        return ScreenConstant.Error.PAGE_403;
    }

    @PostMapping(URLConstant.Curriculum.PO_INSERT)
    public String saveOrUpdatePO(Model model, @ModelAttribute CurriculumPoRequestDTO curriculumPoRequestDTO, HttpSession session) {
        if (session.getAttribute(CommonConstant.ID) != null) {
            Long userId = Long.valueOf(session.getAttribute(CommonConstant.ID).toString());
            List<SubHeaderDTO> curriculumPages = permissionService.getListCurriculumHeader(userId);
            if (permissionService.isUserAccessAll(userId, URLConstant.Curriculum.POs)
                    || permissionService.isUserCanRead(userId, URLConstant.Curriculum.POs)) {
                String actionType = null;
                String message;
                if (curriculumPoRequestDTO.getId() != null) {
                    actionType = "isUpdate";
                    message = poService.saveOrUpdate(curriculumPoRequestDTO);
                } else {
                    actionType = "isCreate";
                    message = poService.saveOrUpdate(curriculumPoRequestDTO);
                }

                if (message.equalsIgnoreCase(CommonConstant.UPDATE_SUCCESS) || message.equalsIgnoreCase(CommonConstant.ADD_SUCCESS)) {
                    return "redirect:/curriculum/pos?id=" + curriculumPoRequestDTO.getCurriculumId();
                }
                Long curriculumId = (Long) session.getAttribute("curriculumId");
                if (message.equalsIgnoreCase(CommonConstant.CODE_EXIST)) {
                    model.addAttribute("message", "PO name is existed!");
                }
                model.addAttribute("curriculumId", curriculumId);
                model.addAttribute("poForm", curriculumPoRequestDTO);
                model.addAttribute(CommonConstant.MENU, ScreenConstant.DASHBOARD);
                model.addAttribute(CommonConstant.SUB_MENU, ScreenConstant.Curriculum.DETAILS);
                model.addAttribute(CommonConstant.LOGIN_ROLES, session.getAttribute(CommonConstant.LOGIN_ROLES));
                model.addAttribute(CommonConstant.ACTION_TYPE, actionType);
                model.addAttribute(CommonConstant.CURRICULUM_PAGES, curriculumPages);
                model.addAttribute(CommonConstant.CURRICULUM_PAGE_TITLE, "POs");
                model.addAttribute(CommonConstant.CURRICULUM_PAGE_ACTION, "");
                model.addAttribute(CommonConstant.CURRICULUM_NAME, session.getAttribute(CommonConstant.CURRICULUM_NAME));
                model.addAttribute(CommonConstant.SUB_CONTENT, ScreenConstant.Curriculum.POs_INSERT);
                return ScreenConstant.HOME_PAGE;
            }
            return ScreenConstant.Error.PAGE_401;
        }
        return ScreenConstant.Error.PAGE_403;
    }

    @GetMapping(URLConstant.Curriculum.CURRICULUM_PO_DELETE)
    public String deleteCurriculumPO(Model model, @RequestParam("id") Long id, HttpSession session) {
        if (session.getAttribute(CommonConstant.ID) != null) {
            Long userId = Long.valueOf(session.getAttribute(CommonConstant.ID).toString());
            if (permissionService.isUserCanEdit(userId, URLConstant.Curriculum.PLOs)) {
                Long curriculumId = (Long) session.getAttribute("curriculumId");
                poService.deleteCurriculumPO(curriculumId, id);
                if (curriculumId != null) {
                    return "redirect:/curriculum/pos?id=" + curriculumId;
                }
                return ScreenConstant.Error.PAGE_400;
            }
            return ScreenConstant.Error.PAGE_401;
        }
        return ScreenConstant.Error.PAGE_403;
    }

    @GetMapping(URLConstant.Curriculum.PLO_POs)
    public String initPLOPos(Model model, @RequestParam("id") Long id, HttpSession session) {
        Long userId = null;
        List<SubHeaderDTO> curriculumPages = null;
        if (session.getAttribute(CommonConstant.ID) != null) {
            userId = Long.valueOf(session.getAttribute(CommonConstant.ID).toString());
            curriculumPages = permissionService.getListCurriculumHeader(userId);
            model.addAttribute("canEdit", permissionService.isUserCanEdit(userId, URLConstant.Curriculum.SUBJECT_PLOS));
        } else {
            curriculumPages = permissionService.getListCurriculumHeaderForGuest();
        }
        List<Po> pos = poService.getPoListByCurriculumId(id);
        List<PloCustomDTO> plos = curriculumService.getListPloCustom(id);
        Curriculum curriculum = curriculumService.findById(id);

                model.addAttribute("ploList", plos);
                model.addAttribute("poList", pos);
                model.addAttribute(CommonConstant.MENU, ScreenConstant.DASHBOARD);
                model.addAttribute(CommonConstant.SUB_MENU, ScreenConstant.Curriculum.DETAILS);
                model.addAttribute(CommonConstant.LOGIN_ROLES, session.getAttribute(CommonConstant.LOGIN_ROLES));
                model.addAttribute(CommonConstant.CURRICULUM_PAGES, curriculumPages);
                model.addAttribute(CommonConstant.CURRICULUM_PAGE_TITLE, "PLO-POs");
                model.addAttribute(CommonConstant.CURRICULUM_PAGE_ACTION, "");
                model.addAttribute(CommonConstant.CURRICULUM_NAME, curriculum.getCode());
                model.addAttribute("curriculumId", curriculum.getId());
                model.addAttribute(CommonConstant.SUB_CONTENT, ScreenConstant.Curriculum.PLO_POs);
                model.addAttribute("canEdit", permissionService.isUserCanEdit(userId, URLConstant.Curriculum.PLO_POs));

        return ScreenConstant.HOME_PAGE;
    }

    @GetMapping(URLConstant.Curriculum.PLO_POs_UPDATE)
    public String initUpdatePLOPOs(Model model, @RequestParam("id") Long id, HttpSession session) {
        if (session.getAttribute(CommonConstant.ID) != null) {
            Long userId = Long.valueOf(session.getAttribute(CommonConstant.ID).toString());
            List<SubHeaderDTO> curriculumPages = permissionService.getListCurriculumHeader(userId);
            if (permissionService.isUserAccessAll(userId, URLConstant.Curriculum.PLO_POs) || permissionService.isUserCanRead(userId, URLConstant.Curriculum.PLO_POs)) {
                List<Po> pos = poService.getPoListByCurriculumId(id);
                List<PloCustomDTO> plos = curriculumService.getListPloCustom(id);
                Curriculum curriculum = curriculumService.findById(id);

                model.addAttribute("ploList", plos);
                model.addAttribute("poList", pos);
                model.addAttribute(CommonConstant.MENU, ScreenConstant.DASHBOARD);
                model.addAttribute(CommonConstant.SUB_MENU, ScreenConstant.Curriculum.DETAILS);
                model.addAttribute(CommonConstant.LOGIN_ROLES, new ArrayList<>());
                model.addAttribute(CommonConstant.CURRICULUM_PAGES, curriculumPages);
                model.addAttribute(CommonConstant.CURRICULUM_PAGE_TITLE, "PLO-POs");
                model.addAttribute(CommonConstant.CURRICULUM_PAGE_ACTION, "");
                model.addAttribute(CommonConstant.CURRICULUM_NAME, curriculum.getCode());
                model.addAttribute("curriculumId", curriculum.getId());
                model.addAttribute("canEdit", permissionService.isUserCanEdit(userId, URLConstant.Curriculum.PLO_POs));

                model.addAttribute(CommonConstant.SUB_CONTENT, ScreenConstant.Curriculum.PLO_POs_DETAILS);

                return ScreenConstant.HOME_PAGE;
            }
            return ScreenConstant.Error.PAGE_401;
        }
        return ScreenConstant.Error.PAGE_403;
    }

    @PostMapping(URLConstant.Curriculum.PLO_POs_UPDATE)
    @ResponseBody
    public ResponseEntity updatePLOPOs(@RequestBody PloPoRequestDTO request, HttpSession session) {
        if (session.getAttribute(CommonConstant.ID) != null) {
            Long userId = Long.valueOf(session.getAttribute(CommonConstant.ID).toString());
            if (permissionService.isUserCanEdit(userId, URLConstant.Curriculum.PLO_POs)) {
                poPloService.updateMapping(request);
                return new ResponseEntity(HttpStatus.OK);
            }
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.FORBIDDEN);
    }

    @PostMapping("/curriculum/subject/update")
    public String updateCurriculumSubject(Model model, @ModelAttribute CurriculumSubjectRequestDTO request, HttpSession session){
        if (session.getAttribute(CommonConstant.ID) != null) {
            Long userId = Long.valueOf(session.getAttribute(CommonConstant.ID).toString());
            if(permissionService.isUserCanEdit(userId, URLConstant.Curriculum.SUBJECTS)){
                Long id = curriculumSubjectService.updateCurriculumSubjectDetails(request);
                return "redirect:/curriculum/subjects?id="+id;
            }
            return ScreenConstant.Error.PAGE_401;
        }
        return ScreenConstant.Error.PAGE_403;
    }

    @PostMapping("/curriculum/subject/add")
    public String addNewCurriculumSubject(Model model, @ModelAttribute CurriculumSubjectDTO request, HttpSession session){
        if (session.getAttribute(CommonConstant.ID) != null) {
            Long userId = Long.valueOf(session.getAttribute(CommonConstant.ID).toString());
            if(permissionService.isUserCanAdd(userId, URLConstant.Curriculum.SUBJECTS)){
                curriculumSubjectService.addNewCurriculumSubject(request);
                return "redirect:/curriculum/subjects?id="+request.getCurriculumId();
            }
        }
        return ScreenConstant.Error.PAGE_403;
    }
}
