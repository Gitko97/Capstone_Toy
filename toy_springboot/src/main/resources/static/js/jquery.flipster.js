jQuery(document).ready(function ($) {

    $('.thumb-img a').on("click", function(e){
        e.preventDefault();
        $('.thumb-previewer img').attr("src", $(this).attr("href"));
    });

});