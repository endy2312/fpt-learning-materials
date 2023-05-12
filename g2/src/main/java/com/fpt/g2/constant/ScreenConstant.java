package com.fpt.g2.constant;

public interface ScreenConstant {
    public interface Authentication {
        String LOGIN_PAGE = "page/authentication/login";
        String PHONE_SIGN_UP = "authentication/phone-register";
        String EMAIL_SIGN_UP = "authentication/email-register";
        String SIGN_UP = "authentication/register";
        String FORGOT = "page/forgot-password";


    }
    public interface Error{
        String PAGE_404 = "page/error/404";
        String PAGE_500 = "page/error/500";
        String PAGE_403 = "page/error/403";
        String PAGE_400 = "page/error/400";
        String PAGE_401 = "page/error/401";
    }
    public interface Admin {
        String SYSTEM_SETTING = "admin/system-setting";
        String PERMISSION_SETTING = "admin/permission-setting";
        String ACCOUNT_LIST = "admin/account-list";
        String ACCOUNT_DETAIL = "admin/account-detail";
        String NEW_SETTING = "admin/new-setting";
    }
    interface Curriculum{
        String DETAILS = "curriculum/details";
        String SUBJECTS = "curriculum/subjects";
        String PLO_POs = "curriculum/plo-pos";
        String PLO_POs_DETAILS = "curriculum/plo-pos/details";
        String PLOs = "curriculum/plos";
        String PLOs_INSERT = "curriculum/plos/insert";
        String POs = "curriculum/pos";
        String POs_INSERT = "curriculum/pos/insert";

        String CURRICULUM_DETAILS = "curriculum/curriculum-details";
        String CURRICULUMS = "curriculums";
        String INSERT = "curriculum/insert";
        String OVERVIEW = "curriculum/overview";
        String SUBJECT_PLOS = "curriculum/subject-plos";
        String SUBJECT_PLOS_DETAILS = "curriculum/subject-plos/details";
        String ELECTIVES = "curriculum/elective";
        String ELECTIVES_DETAILS = "curriculum/electives/details";
        String GROUP = "curriculum/group";
        String COMBO = "curriculum/combo";
    }
    interface Syllabus{
        String DETAILS = "syllabus/details";
        String OVERVIEW = "syllabus/overview";
        String CLOS = "syllabus/clos";
        String CLO_PLOS = "syllabus/cloplos";
        String MATERIAL = "syllabus/material";
        String CQS = "syllabus/cqs";
        String ASSESSMENTS = "syllabus/assessments";
        String SCHEDULE = "syllabus/schedule";
        String LIST = "syllabus/list";
    }
    interface Subject{
        String SUBJECT_LIST = "admin/subjects";
        String PREDECESSOR = "subject/predecessor";
        String SUCCESSOR = "subject/successor";
    }
    interface Decision{
        String DECISION_LIST = "admin/decisions";
        String DECISION_DETAILS = "admin/decision-details";
    }
    String DASHBOARD = "dashboard";
    String HOME_PAGE = "page/home";
    String USER_PROFILE = "profile";
    String FEATURES = "features";
}
