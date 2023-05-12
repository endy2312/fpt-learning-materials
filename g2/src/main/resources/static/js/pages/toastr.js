let toastCustom = {
    saveChangeSubject: function (){
        $.toast({
            heading: 'Notice',
            text: 'Change successful',
            position: 'top-right',
            loaderBg: '#49ff5b',
            icon: 'success',
            hideAfter: 1000,
            stack: 6
        });
    },
    saveChangeStatus: function (){
        $.toast({
            heading: 'Notice',
            text: 'Change status successful',
            position: 'top-right',
            loaderBg: '#49ff5b',
            icon: 'success',
            hideAfter: 1000,
            stack: 6
        });
    },
    updateSettingSuccess: function (){
        $.toast({
            heading: 'Notice',
            text: 'Update Setting successful',
            position: 'top-right',
            loaderBg: '#49ff5b',
            icon: 'success',
            hideAfter: 1000,
            stack: 6
        });
    },
    addSettingSuccess: function (){
        $.toast({
            heading: 'Notice',
            text: 'Add Setting Successful',
            position: 'top-right',
            loaderBg: '#49ff5b',
            icon: 'success',
            hideAfter: 1000,
            stack: 6
        });
    },
    updatePermissionsSuccess: function (){
        $.toast({
            heading: 'Notice',
            text: 'Update Permissions successful',
            position: 'top-right',
            loaderBg: '#49ff5b',
            icon: 'success',
            hideAfter: 1000,
            stack: 6
        });
    },
    toatsSuccess: function (message){
        $.toast({
            heading: 'Notice',
            text: message,
            position: 'top-right',
            loaderBg: '#49ff5b',
            icon: 'success',
            hideAfter: 1000,
            stack: 6
        })
    },
    toatsWarning: function (message){
        $.toast({
            heading: 'Notice',
            text: message,
            position: 'top-right',
            loaderBg: '#49ff5b',
            icon: 'warning',
            hideAfter: 1000,
            stack: 6
        })
    },
    toastError: function (message){
        $.toast({
            heading: 'Notice',
            text: message,
            position: 'top-right',
            loaderBg: '#49ff5b',
            icon: 'error',
            hideAfter: 1000,
            stack: 6
        })
    },

    removeSubjectSuccess: function (){
        $.toast({
            heading: 'Notice',
            text: 'Remove Successful',
            position: 'top-right',
            loaderBg: '#49ff5b',
            icon: 'success',
            hideAfter: 1000,
            stack: 6
        });
    },

}

