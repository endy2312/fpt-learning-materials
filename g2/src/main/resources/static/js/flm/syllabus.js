$(document).ready(function () {
    $('.changeSyllabusStatus').on("click", function () {
        let tempStatus = $(this).text();
        let status;
        if (tempStatus == 'Submit') {
            status = 'Submitted';
        }
        if (tempStatus == 'Verify') {
            status = 'Verified';
        }
        if (tempStatus == 'Approve') {
            status = 'Approved';
        }
        if (tempStatus == 'Disapprove') {
            status = 'Disapproved';
        }
        let id = $(this).attr("syllabus-id");
        swal({
            title: "Are you sure?",
            text: "You will not be able to recover!",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "Yes, added it!",
            closeOnConfirm: true
        }, function () {
            toastCustom.saveChangeStatus();
            setTimeout(function () {
                location.href = "/syllabus/status/change?id=" + id + "&status=" + status;
            }, 1000);
        });
    })

    $('#btnUpdateSyllabus').on("click", function () {
        console.log($('#syllabus-form').valid())
        if ($('#syllabus-form').valid()) {
            swal({
                title: "Are you sure?",
                text: "You will not be able to recover!",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "Yes, added it!",
                closeOnConfirm: true
            }, function () {
                toastCustom.saveChangeStatus();
                setTimeout(function () {
                    $('#syllabus-form').submit();
                }, 1000);
            });
        }
    });

    $('.deleteSyllabusCLO').on('click', function () {
        let id = $(this).attr("clo-id");
        swal({
            title: "Are you sure?",
            text: "You will not be able to recover!",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "Yes, added it!",
            closeOnConfirm: true
        }, function () {
            toastCustom.removeSubjectSuccess();
            setTimeout(function () {
                location.href = "/syllabus/clo/delete?id=" + id;
            }, 1000);
        });
    });

    $('.clo-detail-modal').on('click', function () {
        let index = $(this).closest('tr').index();
        let id = $('tbody tr').eq(index).children('th').children('.clo-id').val();
        let code = $('tbody tr').eq(index).children('td').eq(0).text();
        let details = $('tbody tr').eq(index).children('td').eq(1).text();

        console.table({id, code, details})
        $('#clo-id').val(id)
        $('#code').val(code)
        $('#details').val(details)
    });

    $('#btn-clo-edit').on("click", function () {
        if ($('#form-clo-edit').valid()) {
            swal({
                title: "Are you sure?",
                text: "You will not be able to recover!",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "Yes, added it!",
                closeOnConfirm: true
            }, function () {
                toastCustom.saveChangeSubject();
                setTimeout(function () {
                    $('#form-clo-edit').submit();
                }, 1000);
            });
        }
    });

    $('#btn-clo-add').on('click', function () {
        if ($('#form-clo-add').valid()) {
            api.doPost('/syllabus/clo/checkcode', JSON.stringify({
                syllabusId: $('#syllabusId').val(),
                cloCode: $('#cloCode').val(),
                cloDetails: null,
            }), function () {
            }, function (e) {
                if (e.status == 409) {
                    $('.clo-code').append('<label id="cloCode-error" class="error" for="cloCode">CLO code existed</label>')
                } else if (e.status == 200) {
                    toastCustom.toatsSuccess("Add CLO success");
                    setTimeout(function () {
                        $('#form-clo-add').submit();
                    }, 1000);
                }
                console.log(e)
            });
        }
    });

    $('#btnEditCloPLoMapping').on('click', function () {
        swal({
                title: "Are you sure?",
                text: "You will not be able to recover!",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "Yes, change it!",
                closeOnConfirm: true
            }, function (e){
            if(e == true){
                let listSubject = [];
                let id = $('#syllabusId').val();
                $.each($('table tbody tr'), function (index, obj) {
                    if ($(obj).children().length > 1) {
                        // console.log(obj)
                        let listClo = []
                        $.each($(obj).children(), function (idx, subObj) {
                            if ($(subObj).children().is(':checked')) {
                                listClo.push($('table thead tr th').eq(idx).attr('clo-id'))
                            }
                        })
                        listSubject.push({ploId: $(obj).children('th').eq(0).attr('plo-id'), clos: listClo})
                    }
                });
                api.doPost("/syllabus/cloplo/update", JSON.stringify({
                    cloPloList: listSubject,
                    syllabusId: $('#syllabusId').val()
                }), function () {
                    location.href = "/syllabus/cloplos?id=" + id;
                }, function (err) {
                    if(err.status == 200){
                        location.href = "/syllabus/cloplos?id=" + id;
                    }
                })
            }
        });
    });
})