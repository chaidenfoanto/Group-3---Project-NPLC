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
    $(document).ready(function() {
        $(".edit-btn").click(function() {
          $("#popup").css("display", "block");
        });
      
        $(".close-btn").click(function() {
          $("#popup").css("display", "none");
        });
      
        $(window).click(function(event) {
          if ($(event.target).is("#popup")) {
            $("#popup").css("display", "none");
          }
        });
      });
      
});
