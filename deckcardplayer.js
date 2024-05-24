$(document).ready(function() {
const sidebar = $(".sidebar");
const toggleBtn = $("#toggle-btn");
const logo = $(".logo_details .logo").eq(1); // Select the second logo
const searchBtn = $(".bx-search");

toggleBtn.on("click", function() {
    sidebar.toggleClass("open");
    menuBtnChange();
});
    
    searchBtn.on("click", function() {
        sidebar.toggleClass("open");
        menuBtnChange();
    });
    
    function menuBtnChange() {
        if (sidebar.hasClass("open")) {
            logo.hide();
        } else {
            logo.show();
        }
        }
});

function readMore(btn) {
    var card = $(btn).closest('.card');
    var dots = card.find('.dots');
    var moreText = card.find('.more');

    if (dots.css("display") === "none") {
        dots.css("display", "inline");
        $(btn).text("Read more");
        moreText.css("display", "none");
    } else {
        dots.css("display", "none");
        $(btn).text("Read less");
        moreText.css("display", "inline");
    }
}