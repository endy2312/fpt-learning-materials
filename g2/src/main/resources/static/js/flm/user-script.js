$(document).ready(function () {

    $('a[rel=popover]').popover({
        html: true,
        trigger: 'hover',
        placement: 'bottom',
        content: function () {
            return '<img src="' + $(this).data('img') + '" />';
        }
    });

    $('#btn-profile-send-email').on('click', function (e) {
        $('#input-verify-error').remove();
        if ($.trim($('#btn-profile-send-email').text()) == 'Send' && $.trim($('#email').val() == '') && validateEmail($('#email').val())) {
            console.log(true)
            $('#btn-profile-send-email').attr('class', 'btn btn-primary rounded-0 font-size-14 btn-sm disabled')
            $('#btn-profile-send-email').click(false);
            if ($.trim($('#btn-profile-send-email').attr('type-verify')) == 'email' && $.trim($('#btn-profile-send-email').text()) == 'Send') {
                api.doPost('/sendMailWithPassword', JSON.stringify({
                    recipient: $('#email').val(),
                    msgBody: '',
                    subject: '',
                    attachment: ''
                }), function () {
                }, function (err) {
                    console.log(err)
                    if (err.status == 200) {

                    } else {

                    }
                });
            }
            let count = 60;
            let timer = 0;
            (function countDown() {
                // Display counter and start counting down
                $('#btn-profile-send-email').text(count);
                // Run the function again every second if the count is not zero
                if (count !== 0) {
                    timer = setTimeout(countDown, 1000);
                    count--; // decrease the timer
                } else {
                    // Enable the button
                    $('#btn-profile-send-email').attr('class', 'btn btn-primary rounded-0 font-size-14 btn-sm');
                    $('#btn-profile-send-email').text('Send');
                    $('#btn-profile-send-email').click(true);
                }
            }());
        } else {
            e.stopPropagation();
            e.preventDefault();
            e.stopImmediatePropagation();
            return false;
        }
    });

    $('#btn-profile-send-mobile').on('click', function (e) {
        $('#phone-error').remove();
        //check if pho
        if ($.trim($('#phone2').val()) == '+84') {
            $('#container-profile-input-verify').append('<label id="phone-error" class="error" for="phone2" style="">Phone cannot be left blank !</label>')
        } else {
            if ($.trim($('#btn-profile-send-mobile').text()) == 'Send' && $.trim($('#mobile').val()) != '') {
                $('#btn-profile-send-mobile').attr('class', 'btn btn-primary rounded-0 font-size-14 btn-sm disabled')
                $('#btn-profile-send-mobile').click(false);
                if ($.trim($('#btn-profile-send-mobile').text()) == 'Send') {
                    api.doPost('/sms/process', JSON.stringify({
                        destination: $('#mobile').val(),
                    }), function () {
                    }, function (err) {
                    })
                }
                let count = 60;
                let timer = 0;
                (function countDown() {
                    $('#btn-profile-send-mobile').text(count);
                    if (count !== 0) {
                        timer = setTimeout(countDown, 1000);
                        count--; // decrease the timer
                    } else {
                        $('#btn-profile-send-mobile').attr('class', 'btn btn-primary rounded-0 font-size-14 btn-sm');
                        $('#btn-profile-send-mobile').text('Send');
                        $('#btn-profile-send-mobile').click(true);
                    }
                }());
            } else {
                e.stopPropagation();
                e.preventDefault();
                e.stopImmediatePropagation();
                return false;
            }
        }
    });

    /* Register by phone */

    function validateEmail(email) {
        let emailReg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
        return emailReg.test(email);
    }

    $('#addAccountModal').modal({
        keyboard: false,
        backdrop: 'static',
        show: false
    })

    $("#show_hide_password a").on('click', function (event) {
        event.preventDefault();
        if ($('#show_hide_password input').attr("type") == "text") {
            $('#show_hide_password input').attr('type', 'password');
            $('#show_hide_password i').addClass("fa-eye-slash");
            $('#show_hide_password i').removeClass("fa-eye");
        } else if ($('#show_hide_password input').attr("type") == "password") {
            $('#show_hide_password input').attr('type', 'text');
            $('#show_hide_password i').removeClass("fa-eye-slash");
            $('#show_hide_password i').addClass("fa-eye");
        }
    });

    $('.btn-change-active-account').on("click", function () {
        let uId = $.trim($(this).parent().attr('user-id'));
        swal({
            title: "Are you sure?",
            text: "You will change status of this account!",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#f3ad32",
            confirmButtonText: "Change",
            closeOnConfirm: true
        }, function () {
            toastCustom.toatsSuccess("Change status successful");
            setTimeout(function () {
                location.href = "/account/status/change?id=" + uId;
            }, 1500);
        });
    });

    $('#btn-submit-curriculum').on('click', function () {
        console.log($('#curriculum-form').valid())
        if ($('#curriculum-form').valid()) {
            let message = '';
            if ($('#curriculumId').val() != null && $('#curriculumId').val().trim() != '') {
                message = 'Update curriculum successful'
            } else {
                message = 'Create curriculum successful'
            }
            setTimeout(function () {
                $('#curriculum-form').submit();
                toastCustom.toatsSuccess(message);
                console.log('submit', $('#curriculum-form').get())
                return false;
            }, 1000);
        }
    });

    $('#btn-submit-cplo').on('click', function () {
        console.log($('#cplo-form').valid(), $('#cplo-id').val().trim())
        if ($('#cplo-form').valid()) {
            let message = '';
            if ($('#cplo-id').val() != null && $('#cplo-id').val().trim() != '') {
                message = 'Update PLO successful'
            } else {
                message = 'Create PLO successful'
            }
            setTimeout(function () {
                $('#cplo-form').submit();
                toastCustom.toatsSuccess(message);
                console.log('submit', $('#cplo-form').get())
                return false;
            }, 1000);
        }
    });

    $('#btn-submit-cpo').on('click', function () {
        console.log($('#cpo-form').valid(), $('#cpo-id').val().trim())
        if ($('#cpo-form').valid()) {
            let message = '';
            if ($('#cpo-id').val() != null && $('#cpo-id').val().trim() != '') {
                message = 'Update PO successful'
            } else {
                message = 'Create PO successful'
            }
            setTimeout(function () {
                $('#cpo-form').submit();
                toastCustom.toatsSuccess(message);
                console.log('submit', $('#cpo-form').get())
                return false;
            }, 1000);
        }
    });

    $('.btn-change-active-curriculum').on("click", function () {
        let cId = $.trim($(this).parent().attr('curriculumId'));
        swal({
            title: "Are you sure?",
            text: "You will change status of this curriculum!",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#f3ad32",
            confirmButtonText: "Change",
            closeOnConfirm: true
        }, function () {
            toastCustom.toatsSuccess("Change status successful");
            setTimeout(function () {
                location.href = "/curriculum/status/change?id=" + cId;
            }, 1500);
        });
    });

    $('.btn-change-active-cplo').on("click", function () {
        let cId = $.trim($(this).parent().attr('plo-id'));
        swal({
            title: "Are you sure?",
            text: "You will not be able to recover!",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#F45050",
            confirmButtonText: "Delete",
            closeOnConfirm: true
        }, function () {
            toastCustom.toatsSuccess("Change status successful");
            setTimeout(function () {
                location.href = "/curriculum/plos/delete?id=" + cId;
            }, 1500);
        });
    });

    $('.btn-change-active-cpo').on("click", function () {
        let cId = $.trim($(this).parent().attr('po-id'));
        swal({
            title: "Are you sure?",
            text: "You will not be able to recover!",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#F45050",
            confirmButtonText: "Delete",
            closeOnConfirm: true
        }, function () {
            toastCustom.toatsSuccess("Change status successful");
            setTimeout(function () {
                location.href = "/curriculum/pos/delete?id=" + cId;
            }, 1500);
        });
    });

    $('#btn-save-edit-plopos').on('click', function () {
        let listPlo = [];
        let cId = $.trim($(this).parent().attr('curriculumId'));
        $.each($('table tbody tr'), function (index, obj) {
            if ($(obj).children().length > 1) {
                let listPo = []
                $.each($(obj).children(), function (idx, ploObj) {
                    if ($(ploObj).children().is(':checked')) {
                        listPo.push($('table thead tr th').eq(idx).text())
                    }
                })
                listPlo.push({plo: $(obj).children('th').eq(0).text(), pos: listPo})
            }
        })
        api.doPost("/curriculum/plopos-update",JSON.stringify({curriculumId: cId, plopos: listPlo}),function (res) {
            location.href = '/curriculum/plopos?id=' + cId;
        },function (err) {
            if (err.status == 200) {
                location.href = '/curriculum/plopos?id=' + cId;
            }
        })
    });

    $('#upload-file').on('click', function () {
        let formData = new FormData();
        formData.append('file', $('#image-file')[0].files[0])
        api.doPostImage('/upload-file', formData, function () {

        }, function (err) {
            $('#profile-img').attr('src', err.responseText);
            $('#profile-image').val(err.responseText);

        })
    });

    $('#btn-update-decisionForm').on('click', function () {
        console.log($('#updateDecisionForm').valid())
        if ($('#updateDecisionForm').valid()) {
            let message = '';
            if ($('#decisionId').val() != null && $('#decisionId').val().trim() != '') {
                message = 'Update decision successful'
            } else {
                message = 'Create decision successful'
            }
            setTimeout(function () {
                $('#updateDecisionForm').submit();
                toastCustom.toatsSuccess(message);
                return false;
            }, 1000);
        }
    });

});