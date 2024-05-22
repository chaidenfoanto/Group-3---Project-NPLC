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