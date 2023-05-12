package com.fpt.g2.constant;

public interface URLConstant {

    interface Authentication {
        String SIGN_UP = "/sign-up";
        String INIT_LOGIN = "/";
        String REGISTER = "/register";
        String LOGOUT = "/logout";
        String LOGIN_GOOGLE = "/login-google";
        String LOGIN_POST = "/login";
        String PHONE_REGISTER = "/phone-register";
        String FORGOT = "/forgot-password";
        String VERIFY_FORGOT = "/verify-forgot";
    }

    interface Error{
        String BAD_REQUEST = "/400";
        String UNAUTHORIZED = "/401";
        String PAGE_NOT_FOUND = "/404";
        String INTERNAL_SERVER_ERROR = "/500";
        String ACCESS_DENIED = "/403";
    }

    interface Admin {
        String PERMISSION_LIST = "/dashboard/permissions";
        String ACCOUNT_LIST = "/accounts";
        String ACCOUNT_DETAIL = "/account/details";
        String SYSTEM_SETTINGS = "/settings";
        String SETTING_STATUS_CHANGE = "/setting/status/change";
        String ACCOUNT = "/account";
        String ACCOUNT_INSERT = "/account/insert";

        String ACCOUNT_STATUS_CHANGE = "/account/status/change";
    }

    interface Curriculum{
        String CURRICULUM = "/curriculum";
        String CURRICULUMS_LIST = "/curriculums";
        String OVERVIEW = "/curriculum/overview";
        String CURRICULUM_STATUS_CHANGE = "/curriculum/status/change";
        String POs = "/curriculum/pos";
        String PO_INSERT = "/curriculum/insertpo";
        String PLOs = "/curriculum/plos";
        String PLO_INSERT = "/curriculum/insertplo";
        String PLO_POs = "/curriculum/plopos";
        String PLO_POs_UPDATE = "/curriculum/plopos-update";
        String SUBJECTS = "/curriculum/subjects";
        String SUBJECT_PLOS = "/curriculum/subjectplos";
        String SUBJECT_PLOS_UPDATE = "/curriculum/subjectplos/update";
        String COMBOS = "/curriculum/combos";
        String ELECTIVES = "/curriculum/electives";
        String CURRICULUM_SUBJECTS_STATUS_CHANGE = "/curriculum/subject/status/change";
        String GROUP = "/curriculum/group";
        String CURRICULUM_PLO_DELETE = "/curriculum/plos/delete";
        String CURRICULUM_PO_DELETE = "/curriculum/pos/delete";
        String CURRICULUM_CLONE = "/curriculum/clone";
    }

    interface Syllabus {
        String SYLLABUS = "/syllabus/overview";
        String SYLLABUS_LIST = "/syllabi";
        String SYLLABUS_CLOS = "/syllabus/clos";
        String SYLLABUS_CLO_PLOS = "/syllabus/cloplos";
        String SYLLABUS_MATERIAL = "/syllabus/material";
        String SYLLABUS_CQS = "/syllabus/cqs";
        String SYLLABUS_ASSESSMENTS = "/syllabus/assessments";
        String SYLLABUS_SCHEDULE = "/syllabus/schedule";
        String SYLLABUS_STATUS_CHANGE = "/syllabus/status/change";
        String OVERVIEW_DETAILS = "/syllabus/details";

        String CLO_PLO_DETAILS = "/syllabus/cloplo/details";
    }

    interface Subject{
        String SUBJECT = "/subject";
        String DETAIL = "/subject/details";
        String SUBJECT_PREDECESSORS = "/subject/predecessors";
        String SUBJECT_LIST = "/subjects";
        String SUBJECT_SUCCESSORS = "/subject/successors";
    }

    interface Decision {
        String MATERIAL_DECISIONS = "/decisions";
        String DECISION_INSERT = "/decision/insert";
    }
    String HOME = "/home";
    String PROFILE = "/user/profile";
    String INSERT = "/insert";
    String ADD = "/add";
    String UPDATE = "/update";

    String SETTING_STATUS_CHANGE = "/setting/status/change";

    String SUBJECT = "/subject";
    String DELETE = "/delete";
    String DETAILS = "/details";
}
