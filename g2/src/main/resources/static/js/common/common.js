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
    doPost: function(requestUrl, requestData, successFunction, errorFunction) {
        $.ajax({
            url: requestUrl,
            data: requestData,
            method: "POST",
            contentType: "application/json;charset=utf-8",
            dataType: "json",
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
                hideProgressBar();
            },
            error: function(data, status, jqXHR) {
                hideProgressBar();
                onError(data, status);
            },
            complete: function() {
                hideProgressBar();
            }
        });
    }
};
/** ajax */

/** table data */
function sortTable() {
    $('th').click(function() {
        if ($(this).index() != 0 && $(this).index() != 6) {
            let table = $(this).parents('table').eq(0);
            let ths = table.find('tr:gt(0)').toArray().sort(compare($(this).index()));
            this.asc = !this.asc;
            if (this.asc) {
                $(this).find('span').html('<i class="fa fa-arrow-up">');
            } else {
                ths = ths.reverse();
                $(this).find('span').html('<i class="fa fa-arrow-down">');
            }
            for (let i = 0; i < ths.length; i++) {
                table.append(ths[i]);
            }
        }
    });
};

function compare(idx) {
    return function(a, b) {
        let A = tableCell(a, idx),
            B = tableCell(b, idx)
        return $.isNumeric(A) && $.isNumeric(B) ?
            A - B : A.toString().localeCompare(B)
    }
};

function tableCell(tr, index) {
    return $(tr).children('td').eq(index).text()
};
/** table data */

/** paging */
function bidingPagination(page) {
    $('.pagination').html('');
    $(".pagination").append('<li id="previous-page" class="page-item-btn disabled"><a class="page-link" href="javascript:void(0)" >Previous</a></li>');
    $(".pagination").append("<li class='page-item active'><a class='page-link' href='javascript:void(0)'>" + 1 + "</a></li>");
    for (var i = 2; i <= page; i++) {
        $(".pagination").append("<li class='page-item'><a class='page-link' href='javascript:void(0)'>" + i + "</a></li>");
    }
    $(".pagination").append("<li id='next-page' class='page-item-btn'><a class='page-link' href='javascript:void(0)'>Next</a></li>");
}


function btnNext() {
    $('body').delegate('#next-page', 'click', function() {
        var currentPage = $(".pagination li.active").index();
        $(".pagination li#previous-page").removeClass('disabled');
        if (currentPage === totalPages) {
            return false;
        } else {
            currentPage++;
            $(".pagination li").removeClass('active');
            $(".pagination li.page-item:eq(" + (currentPage - 1) + ")").addClass('active');
            let cateId = $('.category-active').attr('cateId');
            let dataReq = {
                cid: cateId,
                itemName: $('#itemName').val(),
                status: $('#status').val() == 0 ? null : $('#status').val(),
                quantityFrom: $('#quantityFrom').val(),
                quantityTo: $('#quantityTo').val(),
                priceFrom: $('#priceFrom').val(),
                priceTo: $('#priceTo').val(),
                pageNo: currentPage
            }
            loadProduct(cateId, dataReq);
            if (currentPage === totalPages) {
                $(".pagination li#next-page").addClass('disabled');
            }
        }
    });
}

function btnPrevious() {
    $('body').delegate('#previous-page', 'click', function() {
        var currentPage = $(".pagination li.active").index();
        $(".pagination li#next-page").removeClass('disabled');
        if (currentPage === 1) {
            return false;
        } else {
            currentPage--;
            $(".pagination li").removeClass('active');

            $(".pagination li.page-item:eq(" + (currentPage - 1) + ")").addClass('active');
            let cateId = $('.category-active').attr('cateId');
            let dataReq = {
                cid: cateId,
                itemName: $('#itemName').val(),
                status: $('#status').val() == 0 ? null : $('#status').val(),
                quantityFrom: $('#quantityFrom').val(),
                quantityTo: $('#quantityTo').val(),
                priceFrom: $('#priceFrom').val(),
                priceTo: $('#priceTo').val(),
                pageNo: currentPage
            }
            loadProduct(cateId, dataReq);
            if (currentPage === 1) {
                $(".pagination li#previous-page").addClass('disabled');
            }
        }
    });
}

function btnPageItem() {
    $('body').delegate('.page-item', 'click', function() {
        $(".pagination li#next-page").removeClass('disabled');
        $(".pagination li#previous-page").removeClass('disabled');
        if ($(this).hasClass('active')) {
            return false;
        } else {
            var currentPage = $(this).index();
            $(".pagination li").removeClass('active');
            $(this).addClass('active');
            let cateId = $('.category-active').attr('cateId');
            let dataReq = {
                cid: cateId,
                itemName: $('#itemName').val(),
                status: $('#status').val() == 0 ? null : $('#status').val(),
                quantityFrom: $('#quantityFrom').val(),
                quantityTo: $('#quantityTo').val(),
                priceFrom: $('#priceFrom').val(),
                priceTo: $('#priceTo').val(),
                pageNo: currentPage
            }
            loadProduct(cateId, dataReq);
            if (currentPage === 1) {
                $(".pagination li#previous-page").addClass('disabled');
            }
            if (currentPage === totalPages) {
                $(".pagination li#next-page").addClass('disabled');
            }
        }
    });
}
/** paging */

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

let constant = {
    name: "name",
    dob:'dob',
    phone: 'phone',
    passport: 'passport',
    factory: 'factory',
    date_arrive: 'dateArrive',
    visa_exp: 'visaExp',
    name_relative: 'nameRelative',
    phone_relative: 'phoneRelative',
    address: 'address',
    bank_account: 'bankAccount',
    bank: 'bank',
    file: 'file',
    employee_file_name:'Thông tin công nhân ngày ',
    empty: ''
}

let host = 'http:localhost:8080';

let url = {
    add_employee: '/employee/add'
}

function getToday() {
    let date = new Date();
    let dd = date.getDate(); //yields day
    let MM = date.getMonth(); //yields month
    let yyyy = date.getFullYear(); //yields year
    let currentDate= dd + "-" +( MM+1) + "-" + yyyy;
    return currentDate;
}
function initDate() {
    $('input[type="date"]').each(function (index, value) {
        let today = new Date().toISOString().split('T')[0];
        $(value).val(today)
    });
};