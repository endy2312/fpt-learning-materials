$('#login-form').validate({
    rules: {
        username: {
            required: true,
            maxlength: 50
        },
        password: {
            required: true,
            maxlength: 50
        }
    },
    messages: {
        name: "This field can not empty",
        passport: "This field can not empty",
    }
});

$('#register-form').validate({
    rules: {
        fullName: {
            required: true,
            maxlength: 50
        },
        password: {
            required: true,
            minlength: 8,
            maxlength: 50
        },
        phone: {
            required: true,
            maxlength: 50,
            minlength: 11
        },
        email: {
            required: true,
            maxlength: 50,
            email: true
        },
        confirmPassword: {
            required: true,
            maxlength: 50,
            minlength: 8,
            equalTo: '#password'
        }
    },
    messages: {
        fullName: {
            required: "Full name cannot be left blank !"
        },
        email: {
            required: "Email cannot be left blank !",
            email: "Enter valid email !"
        },
        confirmPassword: {
            required: "Confirm password cannot be left blank !",
            maxlength: 50,
            minlength: 'Please enter at least 8 characters',
        },
        phone: {
            required: 'Phone cannot be left blank !'
        }
    }
});

$('#setting-form').validate({
    rules: {
        title: {
            required: true,
            maxlength: 20
        },
        displayOrder: {
            required: false
        }
    },
    messages: {
        title: {
            required: "This field can not be empty",
            maxlength: "Max length of setting title is 20"
        }
    }
});
$('#curriculum-form').validate({
    rules: {
        code: {
            required: true
        },
        name: {
            required: true
        }
    },
    messages: {
    }
});

$('#user-profile-form').validate({
    rules: {
        fullName: {
            required: true,
            maxlength: 100
        },
        // email: {
        //     required: true,
        //     maxlength: 50
        // },
        // mobile: {
        //     required: true,
        //     maxlength: 10
        // },
        verifyCode: {
            required: true,
            maxlength: 10
        },
        username: {
            required: true,
            maxlength: 50
        }
    },
    messages: {
        // fullName: "This field can not empty",
        verifyCode: "Verification code is required",
        // username: "This field can not empty",
    }
});

$('#create-account-form').validate({
    rules: {
        fullName: {
            required: true,
            maxlength: 100
        },
        email: {
            required: true,
            maxlength: 50
        },
        mobile: {
            required: false,
            minlength: 10,
            maxlength: 13
        },
        verifyCode: {
            required: false,
            maxlength: 10
        },

        verifyMobileCode: {
            required: true,
            maxlength: 10
        },
        username: {
            required: false,
            maxlength: 50
        },
        userRoleIds: {
            required: true
        }
    },
    messages: {
        // fullName: "This field can not empty",
        mobile: {
            minlength: "Mobile min 10 characters",
            maxlength: "Mobile max 13 characters"
        },
        verifyCode: "Verification code is required",
        // username: "This field can not empty",
        userRoleIds: {
            title: "User role is required"
        }
    }
});

$('#forgot-form').validate({
    rules: {
        phone: {
            required: true,
            maxlength: 100
        },
        email: {
            required: true,
            email: true,
            maxlength: 50
        },
        password: {
            required: true,
            minlength: 8
        },
        confirmPassword: {
            required: true,
            minlength: 8,
            equalTo: '#password'
        }
    },
    messages: {
        phone: {
            required: ' Phone can not empty'
        },
        email: {
            required: "Email cannot be left blank !",
            email: "Enter valid email !"
        },
        password: {
            required: "Password cannot be left blank !",
            email: "Enter valid email !"
        },
        confirmPassword: {
            required: "Confirm password cannot be left blank !",
            email: "Enter valid email !"
        },
    }
});

$('#updateDetailForm').validate({
    rules: {
        code: {
            required: true,
            maxlength: 10
        },
        name: {
            required: true,
            maxlength: 100
        }
    },
    messages: {
        code: {
            required: "Code can not empty"
        },
        name: {
            required: "Name can not empty"
        }
    }
});

$('#form-subject-add').validate({
    rules: {
        code: {
            required: true,
            maxlength: 10
        },
        name: {
            required: true,
            maxlength: 100
        },
        semester: {
            required: true,
            maxlength: 10,
            digits: true
        },
        noCredit: {
            required: true,
            maxlength: 10,
            digits: true
        }
    },
    messages: {
        code: {
            required: "Code can not empty"
        },
        name: {
            required: "Name can not empty"
        },
        semester: {
            required: "Semester can not empty"
        },
        noCredit: {
            required: "No credit can not empty"
        }
    }
});

$('#form-add-elective').validate( {
    rules:{
        code: {
            required: true,
            maxlength: 10
        },
        name: {
            required: true,
            maxlength: 100
        }
    },
    messages: {
        code: {
            required: "Code can not empty"
        },
        name: {
            required: "Name can not empty"
        }
    }
});

$('#form-add-group').validate( {
    rules:{
        code: {
            required: true,
        },
        name: {
            required: true,
        },
        subjectIds: {
            required: true,
        }
    },
    messages: {
        code: {
            required: "Code can not empty"
        },
        name: {
            required: "Name can not empty"
        },
        subjectIds: {
            required: "Please choose at least one",
        }
    }
});

$('#form-add-combo').validate( {
    rules:{
        code: {
            required: true,
        },
        name: {
            required: true,
        }
    },
    messages: {
        code: {
            required: "Code can not empty"
        },
        name: {
            required: "Name can not empty"
        }
    }
});
$('#form-existed').validate( {
    rules:{
        ids: {
            required: true,
        }
    },
    messages: {
        ids: {
            required: "Please choose at least one"
        }
    }
});

$('#syllabus-form').validate({
    rules: {
        name: {
            required: true,
            maxlength: 100
        },
        degreeLevel: {
            required: true,
            maxlength: 100
        },
        noCredit: {
            required: true,
            digits: true,
            range:[0,10]
        },
        scoringScale: {
            required: true,
            digits: true,
            range:[0,10]
        },
        minAvg: {
            required: true,
            digits: true,
            range:[1,10]
        },
        timeAllocation: {
            required: true,
            maxlength: 200,
        }
    },
    messages: {
        name: {
            required: "Name can not empty",
            maxlength: "Max length this field is 100"
        },
        degreeLevel: {
            required: "Degree Level can not empty",
            maxlength: "Max length this field is 100"
        },
        noCredit: {
            required: "No Credit can not empty",
            digits: "No credit must be integer",
            range: "No credit must be in range [0,10]"
        },
        scoringScale: {
            required: "Scoring scale can not empty",
            digits: "Scoring scale must be integer",
            range: "Scale must be in range [0,10]"
        },
        minAvg: {
            required: "Min avg can not empty",
            digits: "Min avg must be integer",
            range: "Min avg must be in range [1,10]"
        },
        timeAllocation: {
            required: "Time allocation can not empty",
            maxlength: "Max length this field is 200"
        }
    },
});

$('#form-update-combo').validate( {
    rules:{
        name: {
            required: true,
        },
        code: {
            required: true,
        }
    }
});
$('#form-update-group').validate( {
    rules:{
        name: {
            required: true,
        },
        code: {
            required: true,
        }
    }
});
$('#form-update-elective').validate( {
    rules:{
        name: {
            required: true,
        },
        code: {
            required: true,
        }
    }
});

$('#form-clo-edit').validate({
    rules:{
        details: {
            required: true,
        }
    },
    messages: {
        details: {
            required: "Details can not empty"
        }
    }
})

$('#form-clo-add').validate({
    rules:{
        cloCode: {
            required: true,
        },
        cloDetails: {
            required: true,
        }
    },
    messages: {
        cloCode: {
            required: "Code can not empty",
        },
        cloDetails: {
            required: "Details can not empty"
        }
    }
})

$('#curriculum-subject-edit').validate({
    rules:{
        semester:{
            required: true,
            digits: true,
            range:[0,9]
        },
        noCredit: {
            required: true,
            digits: true,
            range:[0,10]
        }
    },
    messages: {
        semester: {
            required: "Semester can not empty",
            digits: "Semester must be integer",
            range: "Semester must be in range [0,9]"
        },
        noCredit: {
            required: "No Credit can not empty",
            digits: "No credit must be integer",
            range: "No credit must be in range [0,10]"
        }
    }
})

