//[Javascript]



$(function () {
    "use strict";
	  // To make Pace works on Ajax calls
	  $(document).ajaxStart(function () {
          $('#loader').attr("style","display:flex");
	  });
    $(document).ajaxComplete(function () {
        $('#loader').attr("style","display:none");
    });
  }); // End of use strict