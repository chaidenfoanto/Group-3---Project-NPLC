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
    var questions = [
        {
            teamName: "Team A",
            question: "What is the strategy?",
            answer: "",
            status: "Not Answered"
        },
        {
            teamName: "Team B",
            question: "Who is the best player?",
            answer: "All players are valuable.",
            status: "Answered"
        }
    ];

    // function renderQuestions() {
    //     var questionList = $("#questionList");
    //     questionList.empty();

    //     questions.forEach(function(q) {
    //         var questionItem = $('<div class="question-item"></div>');
    //         var questionTitle = $('<h3></h3>').text('Team Name: ' + q.teamName);
    //         var questionText = $('<p></p>').text('Question: ' + q.question);
    //         var statusContainer = $('<div class="status-container"></div>');
    //         var status = $('<span class="status"></span>').text(q.status);
    //         var seeAnswer = $('<span class="see-answer"></span>').text('See Answer');
    //         var editAnswer = $('<span class="edit-answer"></span>').text('Answer');

    //         if (q.status === "Answered") {
    //             status.css('color', 'green');
    //             seeAnswer.click(function() {
    //                 closeAllPopups();
    //                 $("#answerContent").text(q.answer);
    //                 $("#answerPopup").fadeIn();
    //             });
    //             statusContainer.append(status, seeAnswer);
    //         } else {
    //             status.css('color', 'red');
    //             editAnswer.click(function() {
    //                 closeAllPopups();
    //                 $("#editTeamName").val(q.teamName);
    //                 $("#editQuestion").val(q.question);
    //                 $("#editAnswer").val(q.answer);
    //                 $("#editAnswerPopup").fadeIn();
    //             });
    //             statusContainer.append(status, editAnswer);
    //         }

    //         questionItem.append(questionTitle, questionText, statusContainer);
    //         questionList.append(questionItem);
    //     });
    // }
});
