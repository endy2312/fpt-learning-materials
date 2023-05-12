$(document).ready(function () {
    /* Register by email */
    $('#btn-register').on('click', function () {
        $('#register-form').valid();
    });

    $('#btn-send-email').on('click', function (e) {
        $('#input-verify-error').remove();
        if (validateEmail($('#input-verify').val()) == false || $.trim($('#input-verify').val()) == '') {
            $('#container-input-verify').append('<label id="input-verify-error" class="error" for="input-verify" style="">Email not valid !</label>')
        } else {
            if ($.trim($('#btn-send-email').text()) == 'Send' && $.trim($('#input-verify').val() != '') && validateEmail($('#input-verify').val())) {
                $('#btn-send-email').attr('class', 'btn btn-primary rounded-0 font-size-14 btn-sm disabled')
                $('#btn-send-email').click(false);

                if ($.trim($('#btn-send-email').text()) == 'Send') {
                    api.doPost('/sendMail', JSON.stringify({
                        recipient: $('#input-verify').val(),
                        msgBody: '',
                        subject: '',
                        attachment: ''
                    }), function () {
                    }, function (err) {
                    })
                }
                let count = 60;
                let timer = 0;
                (function countDown() {
                    // Display counter and start counting down
                    $('#btn-send-email').text(count);
                    // Run the function again every second if the count is not zero
                    if (count !== 0) {
                        timer = setTimeout(countDown, 1000);
                        count--; // decrease the timer
                    } else {
                        // Enable the button
                        $('#btn-send-email').attr('class', 'btn btn-primary rounded-0 font-size-14 btn-sm');
                        $('#btn-send-email').text('Send');
                        $('#btn-send-email').click(true);
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

    function validateEmail(email) {
        let emailReg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
        return emailReg.test(email);
    }

    /* Register by email */

    /* Register by phone */
    $('#btn-register').on('click', function () {
        $('.phone-error').remove();
        $('#register-form').valid();
        if ($('#register-form').valid()) {
            $('#register-form').submit();
        }
    });

    $('#btn-send-mobile').on('click', function (e) {
        $('#phone-error').remove();
        //check if pho
        if ($.trim($('#phone2').val()) == '+84') {
            $('#container-input-verify').append('<label id="phone-error" class="error" for="phone2" style="">Phone cannot be left blank !</label>')
        } else {
            if ($.trim($('#btn-send-mobile').text()) == 'Send' && $.trim($('#phone2').val()) != '') {
                $('#btn-send-mobile').attr('class', 'btn btn-primary rounded-0 font-size-14 btn-sm disabled')
                $('#btn-send-mobile').click(false);
                if ($.trim($('#btn-send-mobile').text()) == 'Send') {
                    api.doPost('/sms/process', JSON.stringify({
                        destination: $('#phone2').val(),
                    }), function () {
                    }, function (err) {
                    })
                }
                let count = 60;
                let timer = 0;
                (function countDown() {
                    $('#btn-send-mobile').text(count);
                    if (count !== 0) {
                        timer = setTimeout(countDown, 1000);
                        count--; // decrease the timer
                    } else {
                        $('#btn-send-mobile').attr('class', 'btn btn-primary rounded-0 font-size-14 btn-sm');
                        $('#btn-send-mobile').text('Send');
                        $('#btn-send-mobile').click(true);
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

    /* Forgot password*/
    $('#change-type').on('click', function () {
        $('#phone2-error').remove();
        if ($(this).attr('send-type') == 'email') {
            $('#phone2').attr('class', 'form-control text-dark bg-transparent')
            $('#email').attr('class', 'form-control text-dark bg-transparent d-none')
            $(this).attr('send-type', 'mobile')
            $(this).text('Reset by email')
            $('#pre-icon').attr('class', 'ti-mobile mr-2')
            $('#label-type').text('Mobile')
            $('#email').val('')
            $('#phone2').val('+84')
            $('#email-error').remove();

        } else {
            $('#phone2').attr('class', 'form-control text-dark bg-transparent d-none')
            $('#email').attr('class', 'form-control text-dark bg-transparent')
            $(this).attr('send-type', 'email')
            $(this).text('Reset by phone')
            $('#pre-icon').attr('class', 'ti-email mr-2')
            $('#label-type').text('Email')
            $('#email').val('')
            $('#phone2').val('+84')
        }
    });

    $('#btn-send-forgot').on('click', function (e) {
        $('#email-error').remove();
        $('#phone2-error').remove();
        console.log($('#email').val())
        if ($('#change-type').attr('send-type') == 'mobile' && $.trim($('#phone2').val()) == '+84') {
            $('.phone-form').append('<label id="phone2-error" class="error" for="phone2">Phone cannot be left blank !</label>');
            return false;
        } else if ($('#change-type').attr('send-type') == 'email'  && $.trim($('#email').val()) == '') {
            $('.phone-form').append('<label id="email-error" class="error" for="email">Email cannot be left blank !</label>');
            return false;
        } else {
            if ($.trim($('#btn-send-forgot').text()) == 'Send') {
                $('#btn-send-forgot').attr('class', 'btn btn-primary rounded-0 font-size-14 btn-sm disabled')
                $('#btn-send-forgot').click(false);
                console.log($('#email').val())
                if ($.trim($('#btn-send-forgot').text()) == 'Send' && $('#change-type').attr('send-type') == 'email' && $.trim($('#email').val()) != '' && validateEmail($('#email').val())) {
                    console.log('send email')
                    api.doPost('/sendMail', JSON.stringify({
                        recipient: $('#email').val(),
                        msgBody: '',
                        subject: '',
                        attachment: ''
                    }), function () {
                    }, function (err) {
                    })
                }
                if ($.trim($('#btn-send-forgot').text()) == 'Send' && $('#change-type').attr('send-type') == 'mobile' && $.trim($('#phone2').val()) != '+84') {
                    console.log('mobile')
                    api.doPost('/sms/process', JSON.stringify({
                        destination: $('#phone2').val(),
                    }), function () {
                    }, function (err) {
                    })
                }
                let count = 60;
                let timer = 0;
                (function countDown() {
                    // Display counter and start counting down
                    $('#btn-send-forgot').text(count);
                    // Run the function again every second if the count is not zero
                    if (count !== 0) {
                        timer = setTimeout(countDown, 1000);
                        count--; // decrease the timer
                    } else {
                        // Enable the button
                        $('#btn-send-forgot').attr('class', 'btn btn-primary rounded-0 font-size-14 btn-sm');
                        $('#btn-send-forgot').text('Send');
                        $('#btn-send-forgot').click(true);
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
    $('#button-reset').on('click', function () {
        $('#phone2-error').remove();
        $('#forgot-form').valid();
        if ($('#change-type').attr('send-type') == 'mobile' && $.trim($('#phone2').val()) == '+84') {
            $('.phone-form').append('<label id="phone2-error" class="error" for="phone2">Phone cannot be left blank !</label>');
            return false;
        }

    });
    /* Forgot password*/
});