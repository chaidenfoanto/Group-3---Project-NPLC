$(document).ready(function() {
    // Load sidebar content
    $(".sidebar").load("sidebarplayer.html", function() {
        const toggleBtn = $("#toggle-btn, #burger-btn");
        const logo = $(".logo_details .logo").eq(1); // Select the second logo
        toggleBtn.on("click", function() {
            $(".sidebar").toggleClass("open");
            menuBtnChange();
        });

        function menuBtnChange() {
            if ($(".sidebar").hasClass("open")) {
                logo.hide();
            } else {
                logo.show();
            }
        }
    });

    // Function to open the question popup
    $('#addQuestionBtn').click(function() {
        $('#popup').fadeIn();
    });

    // Function to close the question popup
    $('#closeContentPopup').click(function() {
        $('#popup').fadeOut();
    });

    // Function to close the answer popup
    $('#closeAnswerPopup').click(function() {
        $('#answerPopup').fadeOut();
    });
});
