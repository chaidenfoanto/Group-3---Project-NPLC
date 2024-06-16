$(document).ready(function() {
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

    // Function to handle form submission
    $('#questionForm').submit(function(event) {
        event.preventDefault(); // Prevent form submission

        // Get the input values
        var teamName = $('#teamName').val();
        var question = $('#question').val();

        // Create a new question object
        var newQuestion = {
            teamName: teamName,
            question: question,
            answer: "", // No answer initially
            status: "Not Answered" // Initial status
        };

        // Append the new question item to the question list
        appendQuestion(newQuestion);

        // Reset the form
        $('#questionForm')[0].reset();

        // Close the popup
        $('#popup').fadeOut();
    });

    function appendQuestion(question) {
        var questionItem = $('<div class="question-item"></div>');
        questionItem.append('<h3>Team Name: ' + question.teamName + '</h3>');
        questionItem.append('<p>Question: ' + question.question + '</p>');
    
        var statusContainer = $('<div class="status-container"></div>');
        var status = $('<span class="status">' + question.status + '</span>');
        statusContainer.append(status);
    
        if (question.status === "Answered") {
            questionItem.addClass('answered'); // Add class for answered questions
            var seeAnswer = $('<span class="see-answer">See Answer</span>');
            statusContainer.append(seeAnswer);
    
            // Add click event to "See Answer"
            seeAnswer.on('click', function() {
                $('#answerContent').text(question.answer);
                $('#answerPopup').fadeIn();
            });
        }
    
        questionItem.append(statusContainer);
        $('#questionList').append(questionItem);
    }
    
    // Load questions on document ready
    function loadQuestions() {
        // Example questions for demo purposes
        var questions = [
            { teamName: "Team A", question: "What is the strategy?", answer: "", status: "Not Answered" },
            { teamName: "Team B", question: "Who is the best player?", answer: "All players are valuable.", status: "Answered" }
        ];

        questions.forEach(appendQuestion);
    }

    loadQuestions();
});
