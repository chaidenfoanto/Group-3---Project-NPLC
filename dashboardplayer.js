$(document).ready(async function() {
    $(".sidebar").load("sidebarplayer.html", function() {
        const toggleBtn = $("#toggle-btn");
        const logo = $(".logo_details .logo").eq(1); // Select the second logo
        toggleBtn.on("click", function() {
            $(".sidebar").toggleClass("open");
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
        // $('.main-content').removeClass('shift');
    }

    const openBtn = $(".game-card");
    const closeBtn = $("#closePopup");
    const modal = $(".gamedetails");

    openBtn.click(function() {
        modal.addClass("open");
    });

    closeBtn.click(function() {
        modal.removeClass("open");
    });

    $(".game-card").click(function() {
        $(".gamedetails").show();
        $(".modal-overlay").show();
    });

    $("#closePopup").click(function() {
        $(".gamedetails").hide();
        $(".modal-overlay").hide();
    });

});