$(document).ready(function () {
    $('.changeCurriculumSubjectStatus').on("click", function () {
        let id = $(this).attr("sc-id")
        swal({
            title: "Are you sure?",
            text: "You will not be able to recover!",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "Yes, changed it!",
            closeOnConfirm: true
        }, function () {
            toastCustom.saveChangeStatus();
            setTimeout(function () {
                location.href = "/curriculum/subject/status/change?id=" + id;
            }, 1000);
        });
    });

    $('.delete-subject').on('click', function () {
        let subjectId = $(this).attr('subject-id');
        let electiveId = $('#elective-id').val();
        let curriculumId = $('#curriculum-id').val();
        swal({
            title: "Are you sure?",
            text: "You will not be able to recover!",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "Yes, delete it!",
            closeOnConfirm: true
        }, function () {
            toastCustom.removeSubjectSuccess();
            setTimeout(function () {
                location.href = '/elective/detail/remove?cid=' + curriculumId + '&sid=' + subjectId + '&eid=' + electiveId;
            }, 1000);
        });
    });

    $('.delete-subject-group').on('click', function () {
        let subjectId = $(this).attr('subject-id');
        let groupId = $('#id').val();
        let curriculumId = $('#curriculum-id').val();
        swal({
            title: "Are you sure?",
            text: "You will not be able to recover!",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "Yes, delete it!",
            closeOnConfirm: true
        }, function () {
            toastCustom.removeSubjectSuccess();
            setTimeout(function () {
                location.href = '/group/detail/remove?cid=' + curriculumId + '&sid=' + subjectId + '&gid=' + groupId;
            }, 1000);
        });
    });
    $('#btn-update-group').on('click', function () {
        if ($('#form-update-group').valid()) {
            api.doPost('/group/detail/update', JSON.stringify({
                id: $('#group-id').val(),
                name: $('#group-name').val(),
                code: $('#group-code').val(),
                subjectIds: null,
                curriculumId: $('#curriculum-id').val()
            }), function () {
            }, function (e) {
                if (e.status == 404) {
                    toastCustom.toastError("Group code existed")
                } else if (e.status == 200) {
                    toastCustom.toatsSuccess("Update group success");
                    setTimeout(function () {
                        location.reload();
                    }, 1000)
                }
            });
        }
    });

    $('#group-code').focus(function () {
        $('#group-code-error').remove();
    })
    $('#btn-add-group').on('click', function () {
        if ($('#form-add-group').valid()) {
            api.doPost('/group/add', JSON.stringify({
                id: null,
                name: $('#group-name').val(),
                code: $('#group-code').val(),
                subjectIds: $('#multi-subject-subject').val(),
                curriculumId: $('#curriculum-id').val()
            }), function () {
            }, function (e) {
                if (e.status == 409) {
                    $('.group-code').append('<label id="group-code-error" class="error" for="group-code">Group code existed</label>')
                } else if (e.status == 200) {
                    toastCustom.toatsSuccess("Add group success");
                    setTimeout(function () {
                        location.reload();
                    }, 1000)
                }
                console.log(e)
            });
        }
    });

    $('.delete-group').on('click', function () {
        let groupId = $(this).attr('group-id');
        swal({
            title: "Are you sure?",
            text: "You will not be able to recover!",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "Yes, delete it!",
            closeOnConfirm: true
        }, function () {
            toastCustom.removeSubjectSuccess();
            setTimeout(function () {
                location.href = '/group/delete?id=' + groupId;
            }, 1000);
        });
    });

    $('.btnChangeElectiveStatus').on('click', function () {
        let curriculumId = $('#curriculumId').val();
        let subjectId = $(this).attr('s-id');
        console.log(subjectId)
        // swal({
        //     title: "Are you sure?",
        //     text: "You will not be able to recover!",
        //     type: "warning",
        //     showCancelButton: true,
        //     confirmButtonColor: "#DD6B55",
        //     confirmButtonText: "Yes, delete it!",
        //     closeOnConfirm: true
        // }, function () {
        //     toastCustom.removeSubjectSuccess();
        //     setTimeout(function () {
        //         //location.href = '/group/delete?id=' + groupId;
        //     }, 1000);
        // });
    });
    $('#btn-update-elective').on('click', function () {
        if ($('#form-update-elective').valid()) {
            api.doPost('/elective/details/update', JSON.stringify({
                id: $('#electiveId').val(),
                name: $('#elective-name').val(),
                code: $('#elective-code').val(),
                note: $('#elective-description').val()
            }), function () {
            }, function (e) {
                if (e.status == 409) {
                    toastCustom.toastError("Elective code existed")
                } else if (e.status == 200) {
                    toastCustom.toatsSuccess("Update group success");
                    setTimeout(function () {
                        location.reload();
                    }, 1000)
                }
            });
        }
    });
    $('#btn-update-combo').on('click', function () {
        if ($('#form-update-combo').valid()) {
            api.doPost('/combo/details/update', JSON.stringify({
                id: $('#comboId').val(),
                name: $('#combo-name').val(),
                code: $('#combo-code').val(),
                note: $('#combo-description').val()
            }), function () {
            }, function (e) {
                if (e.status == 409) {
                    toastCustom.toastError("Elective code existed")
                } else if (e.status == 200) {
                    toastCustom.toatsSuccess("Update group success");
                    setTimeout(function () {
                        location.reload();
                    }, 1000)
                }
            });
            console.log({
                id: $('#comboId').val(),
                name: $('#combo-name').val(),
                code: $('#combo-code').val(),
                note: $('#combo-description').val()
            })
        }
    });
    $('#btn-add-combo').on('click', function () {
        if ($('#form-add-combo').valid()) {
            api.doPost('/combo/check-existed', JSON.stringify({
                code: $('#combo-code').val()
            }), function () {
            }, function (e) {
                if (e.status == 409) {
                    toastCustom.toastError("Elective code existed")
                } else if (e.status == 200) {
                    toastCustom.toatsSuccess("Update group success");
                    setTimeout(function () {
                        $('#form-add-combo').submit();
                    }, 1000)
                }
            });
        }
    });

    $('.delete-combo-subject').on('click', function () {
        let subjectId = $(this).attr('subject-id');
        let comboId = $('#combo-id').val();
        let curriculumId = $('#curriculum-id').val();
        swal({
            title: "Are you sure?",
            text: "You will not be able to recover!",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "Yes, delete it!",
            closeOnConfirm: true
        }, function () {
            toastCustom.removeSubjectSuccess();
            setTimeout(function () {
                location.href = '/combo/detail/remove?cid=' + curriculumId + '&sid=' + subjectId + '&comboId=' + comboId;
            }, 1000);
        });
    });


    $('.edit-subject-modal').on('click', function () {
        $('#predecessor').attr('class', 'form-control d-none');
        $("#predecessor").find('option').attr("selected", false);

        $('#group').attr('class', 'form-control d-none');
        $("#group").find('option').attr("selected", false);
        let index = $(this).closest('tr').index();
        let id = $('tbody tr').eq(index).children('th').children('.sc-id').val();
        let code = $('tbody tr').eq(index).children('td').eq(0).text();
        let group = $('tbody tr').eq(index).children('td').eq(0).children('input').val();
        let name = $('tbody tr').eq(index).children('td').eq(1).text();
        let semester = $('tbody tr').eq(index).children('td').eq(2).text();
        let noCredit = $('tbody tr').eq(index).children('td').eq(3).text();
        let predecessor = $('tbody tr').eq(index).children('td').eq(4).text();
        let active = $('tbody tr').eq(index).children('td').eq(5).text();
        console.log((predecessor.split(',')))

        // console.table({id, code, name, semester, noCredit, predecessor, active})

        $('#csId').val(id)
        $('#name').val(name)
        $('#code').val(code)
        $('#semester').val(semester)
        $('#noCredit').val(noCredit)
        $.each($('#predecessor option'), function (idx, option) {
            $.each(predecessor.split(','), function (idx, code) {
                if ($(option).text().trim() == code.trim()) {
                    $(option).attr('selected', 'true');
                }
            })
        });
        $.each($('#group option'), function (idx, option) {
            if ($(option).text().trim() == group.trim()) {
                $(option).attr('selected', 'true');
            }
        });
        $('#predecessor').attr('class', 'form-control select2 d-none');
        $('#predecessor').trigger('change');
        $('#group').attr('class', 'form-control select2 d-none');
        $('#group').trigger('change');

        if (active == "Active") {
            $('#active').attr('checked', true);
        } else {
            $('#deactivate').attr('checked', true)
        }
    });

    $('#btn-edit-curriculum-subject').on('click', function () {
        if ($('#curriculum-subject-edit').valid()) {
            swal({
                title: "Are you sure?",
                text: "You will not be able to recover!",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "Yes, update it!",
                closeOnConfirm: true
            }, function () {
                toastCustom.saveChangeSubject();
                setTimeout(function () {
                    $('#curriculum-subject-edit').submit();
                }, 1000);
            });
        }
    })

    $('#add-more-subject').on('click', function (e) {
        api.doGet('/check-existed?code=' + $('#code-new').val(), null, function () {
        }, function (e) {
            console.log(e)
            if (e.status == 404) {
                toastCustom.toastError("Subject code existed");
            } else {
                $('#subjectCode').attr('class', 'form-control d-none');
                $('#subjectCode').find('option').attr("selected", false);
                $('#subjectCode').append('<option selected value="' + $('#code-new').val() + '">' + ($('#code-new').val() + ' - ' + $('#name-new').val()) + '</option>')
                $('#subjectCode').attr('class', 'form-control select2 d-none');
                $('#subjectCode').trigger('change');
                api.doPost('/subject/add-more', JSON.stringify({
                    code: $('#code-new').val(),
                    name: $('#name-new').val()
                }), function () {

                }, function (err) {
                    console.log(err)
                    if(err.status == 200){
                        $('.subject-code').append('<label id="group-code-success" class="text-success" for="group-code">Add new successful</label>')
                        $('#add-new-subject').collapse('hide');
                    }
                })
            }
        });
    })

    $('#btn-curriculum-subject-add').on('click', function () {
        if ($('#curriculum-subject-add').valid()) {
            toastCustom.toatsSuccess("Add new curriculum subject successfully");
            setTimeout(function () {
                $('#curriculum-subject-add').submit();
            }, 1000);
        }
    })
});
