$(document).ready(function () {
    $(".sidebar").load("sidebarplayer.html", function () {
        const toggleBtn = $("#toggle-btn");
        const logo = $(".logo_details .logo").eq(1); // Select the second logo
        toggleBtn.on("click", function () {
            $(".sidebar").toggleClass("open");
            updateLogo();
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

    $(document).on('click', function (e) {
        if (!$(e.target).closest('.sidebar, #toggle-btn').length) {
            closeSidebar();
        }
    });

    function closeSidebar() {
        $('.sidebar').removeClass('open');
        updateLogo();
        // $('.main-content').removeClass('shift');
    }
    
    const closeBtn = $("#closePopup");
    const modal = $(".gamedetails");

    closeBtn.click(function () {
        modal.removeClass("open");
    });

    $("#closePopup").click(function () {
        $(".gamedetails").hide();
        $(".modal-overlay").hide();
    });

});