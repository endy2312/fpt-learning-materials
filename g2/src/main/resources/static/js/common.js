/** ajax */
let api = {
    doGet: function(requestUrl, requestData, successFunction, errorFunction) {
        $.ajax({
            url: requestUrl,
            data: requestData,
            method: "GET",
            contentType: "application/json;charset=utf-8",
            dataType: "json",
            beforeSend: function(data) {
                $('#loader').attr("style","display:flex");
            },
            success: function(data) {
                if ($.isFunction(successFunction)) {
                    successFunction(data);
                }
            },
            error: function(error) {
                if ($.isFunction(errorFunction)) {
                    errorFunction(error);
                }
            },
            complete: function() {
                $('#loader').attr("style","display:none");
            }
        });
    },
    doPost: function(requestUrl, requestData, successFunction, errorFunction) {
        $.ajax({
            url: requestUrl,
            data: requestData,
            method: "POST",
            contentType: "application/json;",
            dataType: "json",
            beforeSend: function(data) {
                $('#loader').attr("style","display:flex");
                console.log('befo')
            },
            success: function(data) {
                if ($.isFunction(successFunction)) {
                    successFunction(data);
                }
            },
            error: function(error) {
                if ($.isFunction(errorFunction)) {
                    errorFunction(error);
                }
            },
            complete: function() {
                $('#loader').attr("style","display:none");
            }
        });
    },

    doPostImage: function(requestUrl, requestData, successFunction, errorFunction) {
        $.ajax({
            url: requestUrl,
            data: requestData,
            method: "POST",
            contentType: false,
            dataType: "json",
            processData: false,
            beforeSend: function(data) {
                $('#loading').show();
            },
            success: function(data) {
                if ($.isFunction(successFunction)) {
                    successFunction(data);
                }
            },
            error: function(error) {
                if ($.isFunction(errorFunction)) {
                    errorFunction(error);
                }
            },
            complete: function() {
                $('#loading').hide();
            }
        });
    },
    doGetByStep: function(requestUrl, requestData, beforeSendFunction, successFunction, errorFunction, completeFunction) {
        $.ajax({
            url: requestUrl,
            data: requestData,
            method: "GET",
            contentType: "application/json;charset=utf-8",
            dataType: "json",
            beforeSend: function(data) {
                if ($.isFunction(beforeSendFunction)) {
                    beforeSendFunction();
                }
            },
            success: function(data) {
                if ($.isFunction(successFunction)) {
                    successFunction(data);
                }
            },
            error: function(error) {
                if ($.isFunction(errorFunction)) {
                    errorFunction(error);
                }
            },
            complete: function() {
                if ($.isFunction(completeFunction)) {
                    completeFunction();
                }
            }
        });
    },
    doUpload: function(url, postData, onSuccess, onError) {
        $.ajax({
            type: "POST",
            url: url,
            data: postData,
            processData: false,
            contentType: false,
            cache: false,
            xhr: function() {
                var jqXHR = null;
                if (window.ActiveXObject) {
                    jqXHR = new window.ActiveXObject("Microsoft.XMLHTTP");
                } else {
                    jqXHR = new window.XMLHttpRequest();
                }
                //Upload progress
                jqXHR.upload.addEventListener("progress", function(evt) {
                    if (evt.lengthComputable) {
                        var percentComplete = Math.round((evt.loaded * 100) / evt.total);
                        console.log('Uploaded percent', percentComplete);
                        showProgressBar(percentComplete);
                    }
                }, false);
                return jqXHR;
            },
            success: function(data) {
                onSuccess(data);
            },
            error: function(data, status, jqXHR) {
                onError(data, status);
            },
            complete: function() {
            }
        });
    }
};
/** ajax */

function convertDateTime(dateTime) {
    let date = DateTime.ParseExact(dateTime, "yyyy-MM-dd HH:mm:ss", CultureInfo.InvariantCulture);
    let formattedDate = date.ToString("yyyy-MM-dd HH:mm:ss")
    return formattedDate;
}

jQuery.fn.inputOnlyNumber = function() {
    return this.each(function() {
        $(this).keydown(function(e) {
            var key = e.charCode || e.keyCode || 0;
            // allow backspace, tab, delete, enter, arrows, numbers and keypad numbers ONLY
            // home, end, period, and numpad decimal
            return (
                key == 8 ||
                key == 9 ||
                key == 13 ||
                key == 46 ||
                key == 110 ||
                key == 190 ||
                (key >= 35 && key <= 40) ||
                (key >= 48 && key <= 57) ||
                (key >= 96 && key <= 105));
        });
    });
};

function escapeHtml(unsafe) {
    return unsafe
        .replace(/&/g, "&amp;")
        .replace(/</g, "&lt;")
        .replace(/>/g, "&gt;")
        .replace(/"/g, "&quot;")
        .replace(/'/g, "&#039;");
}

function getToday() {
    let date = new Date();
    let dd = date.getDate(); //yields day
    let MM = date.getMonth(); //yields month
    let yyyy = date.getFullYear(); //yields year
    let currentDate= dd + "-" +( MM+1) + "-" + yyyy;
    return currentDate;
}