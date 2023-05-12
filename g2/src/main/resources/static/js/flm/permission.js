$(document).ready(function () {
    $('.btnAccessAll').on("click", function () {
        let curIndex = $(this).parent().attr("permission-id");
        if ($('.btnAccessAll').eq(curIndex).attr("aria-pressed") === 'false') {
            $('.accessAll').eq(curIndex).val(true);
            $('.btnCanRead').eq(curIndex).attr("aria-pressed",'true');
            $('.btnCanRead').eq(curIndex).attr("class",'btn btn-switch btnCanRead focus active');
            $('.accessAll').eq(curIndex).val(true)
        } else {
            $('.accessAll').eq(curIndex).val(false)
        }
    })
    $('.btnCanRead').on("click", function (){
        let curIndex = $(this).parent().attr("permission-id");
        if ($('.btnCanRead').eq(curIndex).attr("aria-pressed") === 'false') {
            $('.canRead').eq(curIndex).val(true)
        } else {
            $('.canRead').eq(curIndex).val(false)
        }
    })
    $('.btnCanAdd').on("click", function (){
        let curIndex = $(this).parent().attr("permission-id");
        if ($('.btnCanAdd').eq(curIndex).attr("aria-pressed") === 'false') {
            $('.canAdd').eq(curIndex).val(true)
        } else {
            $('.canAdd').eq(curIndex).val(false)
        }
    })
    $('.btnCanEdit').on("click", function (){
        let curIndex = $(this).parent().attr("permission-id");
        if ($('.btnCanEdit').eq(curIndex).attr("aria-pressed") === 'false') {
            $('.canEdit').eq(curIndex).val(true)
        } else {
            $('.canEdit').eq(curIndex).val(false)
        }
    })
    $('.btnCanDelete').on("click", function (){
        let curIndex = $(this).parent().attr("permission-id");
        if ($('.btnCanDelete').eq(curIndex).attr("aria-pressed") === 'false') {
            $('.canDelete').eq(curIndex).val(true)
        } else {
            $('.canDelete').eq(curIndex).val(false)
        }
    })
    $('#btnUpdatePermissions').on("click", function (){
        swal({
            title: "Are you sure?",
            text: "You will not be able to recover!",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "Yes, update it!",
            closeOnConfirm: true
        }, function(){
            toastCustom.updatePermissionsSuccess();
            setTimeout(function () {
                $('#permissionsForm').submit()
            },1000);
        });
    })
    $('#btnAddSetting').on("click", function (){
        if($('#setting-form').valid()) {
            toastCustom.addSettingSuccess();
            setTimeout(function () {
                $('#setting-form').submit()
            }, 1000);
        }
    })
    $('#btnUpdateSetting').on("click", function (){
        if($('#setting-form').valid()){
            swal({
                title: "Are you sure?",
                text: "You will not be able to recover!",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "Yes, updated it!",
                closeOnConfirm: true
            }, function(){
                toastCustom.updateSettingSuccess();
                setTimeout(function () {
                    $('#setting-form').submit()
                },1000);
            });
        }
    })
    $('.changeSettingStatus').on("click", function (){
        let id = $(this).attr("setting-id")
        swal({
            title: "Are you sure?",
            text: "You will not be able to recover!",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "Yes, changed it!",
            closeOnConfirm: true
        }, function(){
            toastCustom.saveChangeStatus();
            setTimeout(function () {
                location.href="/setting/status/change?id=" + id;
            },1000);
        });
    })
});