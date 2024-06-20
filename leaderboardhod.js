$(document).ready(function() {
    $(".sidebar").load("sidebarHod.html", function() {
        const toggleBtn = $("#toggle-btn, #burger-btn");
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


    // Example of updating data and re-rendering the leaderboard
    // setTimeout(function() {
    //     leaderboardData[1].points = 1100; // Update points for the second team
    //     renderLeaderboard(leaderboardData); // Re-render the leaderboard
    // }, 5000); // Update after 5 seconds
});