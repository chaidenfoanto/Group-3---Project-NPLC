$(document).ready(function() {
    // Clear the local storage to remove all notes
    localStorage.removeItem("notes");

    $(".sidebar").load("sidebarplayer.html", function() {
        const toggleBtn = $("#toggle-btn");
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

    const popupBox = $(".popup-box");
    const teamNameInput = $("#team-name");
    const questionText = $("#question-text");
    const answerText = $("#answer-text");
    const submitAnswerButton = $("#submit-answer");
    let currentQuestionBox = null;

    $(".question-box").on("click", function() {
        currentQuestionBox = $(this);
        const teamName = currentQuestionBox.data("team");
        const question = currentQuestionBox.data("question");
        const answer = currentQuestionBox.data("answer");
        teamNameInput.val(teamName);
        questionText.val(question);
        answerText.val(answer);

        popupBox.addClass("show");
    });

    $(".popup header .close-btn").on("click", function() {
        popupBox.removeClass("show");
    });

    submitAnswerButton.on("click", function(e) {
        e.preventDefault();
        if (currentQuestionBox) {
            const answer = answerText.val();
            currentQuestionBox.data("answer", answer);
            currentQuestionBox.find(".answer").text(answer);
            popupBox.removeClass("show");
        }
    });
});
