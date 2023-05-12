//[Data Table Javascript]

//Project:	Florence Admin - Responsive Admin Template
//Primary use:   Used only for the Data Table

$(function () {
    "use strict";

    $('#example1').DataTable();
    $('#example2').DataTable({
        'paging': true,
        'lengthChange': false,
        'searching': false,
        'ordering': true,
        'info': true,
        'autoWidth': false
    });

    let filename = constant.employee_file_name + getToday();
    $('#table-data').DataTable({
        dom: '<"dt-top-container row"<"dt-center-in-div col-sm-5 col-md-6 col-lg-4 col-xl-3"B><"col-md-6 col-sm-4 col-lg-4 col-xl-3"l><"col-lg-5 col-xl-7 md-justify-content-start sm-justify-content-start d-flex justify-content-end"f>r>t<ip>',
        'paging': false,
        'lengthChange': false,
        'searching': false,
        'ordering': false,
        'info':false,
        "oLanguage": {
            "pPrint": "Tìm kiếm"
        },
        buttons: [{
            extend: 'pdf',
            title: filename,
            filename: filename
        }, {
            extend: 'excel',
            title: filename,
            filename: filename
        }, {
            extend: 'print',
            filename: filename
        }]
    });

    // $('#table-data tfoot .th-search').each(function () {
    //     var title = $(this).text();
    //     if (title.indexOf('Ngày') != -1 || title.indexOf('Thời Hạn') != -1) {
    //         $(this).html('<input type="date" class="form-control-sm" placeholder="dd/mm/yy ' + title + '" />');
    //     }
    //     else $(this).html('<input type="text" class="form-control-sm" placeholder="Search ' + title + '" />');
    // });

    // $('#table-data').DataTable({
    //     dom: '<"dt-top-container row"<"dt-center-in-div col-sm-4 col-md-6 col-lg-3 col-xl-2"B><"col-md-6 col-sm-4 col-lg-4 col-xl-3"l><"col-lg-5 col-xl-7 md-justify-content-start sm-justify-content-start d-flex justify-content-end"f>r>t<ip>',
    //     buttons: [
    //         'excel', 'pdf', 'print'
    //     ],
    //     "oLanguage": {
    //         "sSearch": "Tìm kiếm"
    //     },
    //     lengthMenu: [
    //         [10, 25, 50, -1],
    //         [10, 25, 50, 'All'],
    //     ],
    //     initComplete: function () {
    //         // Apply the search
    //         this.api()
    //             .columns()
    //             .every(function () {
    //                 var that = this;
    //
    //                 $('input', this.footer()).on('keyup change clear', function () {
    //                     if (that.search() !== this.value) {
    //                         that.search(this.value).draw();
    //                     }
    //                 });
    //             });
    //     }
    // });
    //
    // $('#table-data tbody').on('click', 'tr', function () {
    //     $(this).toggleClass('selected');
    // });
    // $('#table-data_filter').append($('#factory-filter'));
    //
    // $('#daily-table').DataTable({
    //     // dom: 'Bfrtip',
    //     // buttons: [
    //     //     'copy', 'csv', 'excel', 'pdf', 'print', 'pageLength'
    //     // ],
    //     "oLanguage": {
    //         "sSearch": "Tìm kiếm",
    //     },
    //     lengthMenu: [
    //         [10, 25, 50, -1],
    //         [10, 25, 50, 'All'],
    //     ]
    // });

    // Automatically add a first row of data
    $('#add-row').click();
    $('#tickets').DataTable({
        'paging': true,
        'lengthChange': true,
        'searching': true,
        'ordering': true,
        'info': true,
        'autoWidth': false,
    });

    $('#productorder').DataTable({
        'paging': true,
        'lengthChange': true,
        'searching': true,
        'ordering': true,
        'info': true,
        'autoWidth': false,
    });


    $('#complex_header').DataTable();

    //--------Individual column searching

    // Setup - add a text input to each footer cell
    // $('#example5 tfoot th').each(function () {
    //     var title = $(this).text();
    //     $(this).html('<input type="text" placeholder="Search ' + title + '" />');
    // });

    // DataTable
    var table = $('#example5').DataTable();

    // Apply the search
    table.columns().every(function () {
        var that = this;

        $('input', this.footer()).on('keyup change', function () {
            if (that.search() !== this.value) {
                that
                    .search(this.value)
                    .draw();
            }
        });
    });


    //---------------Form inputs
    var table = $('#example6').DataTable();

    $('#data-update').click(function () {
        var data = table.$('input, select').serialize();
        alert(
            "The following data would have been submitted to the server: \n\n" +
            data.substr(0, 120) + '...'
        );
        return false;
    });


}); // End of use strict