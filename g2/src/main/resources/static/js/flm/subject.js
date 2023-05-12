$(document).ready(function () {
    $('#btn-submit').on('click', function () {
        let code = $('#subject-code').val();
        $('#form-subject-add').valid();
        if ($('#form-subject-add').valid()) {
            api.doGet('/check-existed?code=' + code, null, function () {
            }, function (e) {
                if (e.status == 404) {
                    $('.subject-code-form').append('<label id="subject-code-error" class="error" for="subject-code" style="">Subject code existed !</label>')
                } else {
                    toastCustom.toatsSuccess("Add subject successful");
                    setTimeout(function () {
                        $('#form-subject-add').submit();
                    }, 1000);

                }
            });
        }
    });

    $('.btn-active').on('click', function () {
        let status = $(this).attr('aria-pressed');
        $('#active-filed').val(status == 'false' ? 'true' : 'false')
    });

    $('.btn-active').on("click", function () {
        let curIndex = $(this).parent().attr("subject-id");
        console.log(curIndex)
        if ($('.btn-active').eq(curIndex).attr("aria-pressed") == 'false') {
            $('.is-active').eq(curIndex).val(false);
        } else {
            $('.is-active').eq(curIndex).val(true)
        }
    });

    $('#btn-save-change').on('click', function () {
        swal({
            title: "Are you sure?",
            text: "You will not be able to recover!",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "Yes, change it!",
            closeOnConfirm: true
        }, function (e) {
            if (e == true) {
                toastCustom.saveChangeSubject();
                setTimeout(function () {
                    $('#update-active-form').submit()
                }, 1000);
            }
        });
    });

    $('#btn-update-subject').on('click', function () {
        if ($('#updateDetailForm').valid()) {
            api.doGet('/check-existed?code=' + $('#subject-code-update').val() + '&id=' + $('#subject-id-update').val(), null, function () {
            }, function (e) {
                console.log(e)
                if (e.status == 404) {
                    toastCustom.toastError("Subject code existed");
                } else {
                    toastCustom.toatsSuccess("Update subject successful");
                    setTimeout(function () {
                        $('#updateDetailForm').submit()
                    }, 1000);
                }
            });
        }
    });

    $('#btn-save-edit').on('click', function () {
        swal({
            title: "Are you sure?",
            text: "You will not be able to recover!",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "Yes, change it!",
            closeOnConfirm: true
        }, function (e) {
            if (e == true) {
                let id = $('#curriculumId').val();
                let listSubject = [];
                $('#loader').attr("style", "display:flex");
                $.each($('table tbody tr'), function (index, obj) {
                    if ($(obj).children().length > 1) {
                        let listPlo = []
                        $.each($(obj).children(), function (idx, subObj) {
                            if ($(subObj).children().is(':checked')) {
                                listPlo.push($('table thead tr th').eq(idx).text())
                            }
                        })
                        listSubject.push({subjectCode: $(obj).children('td').eq(0).text(), plos: listPlo})
                    }
                });
                api.doPost("/curriculum/subjectplos/update", JSON.stringify({
                    subjectPlos: listSubject,
                    id: $('#curriculumId').val()
                }), function () {
                    location.href = "/curriculum/subjectplos?id=" + id;
                }, function (err) {
                    if (err.status == 200) {
                        location.href = "/curriculum/subjectplos?id=" + id;
                    }
                });
            }
        });
    });

    $('#btn-reset-edit').on('click', function () {
        swal({
            title: "Are you sure?",
            text: "You will not be able to recover!",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "Yes, reset now!",
            closeOnConfirm: true
        }, function (e) {
            if (e == true) {
                toastCustom.saveChangeSubject();
                setTimeout(function () {
                    location.reload();
                }, 1000);
            }
        });
    })
})