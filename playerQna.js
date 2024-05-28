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

    // Event listener for the submit button
    $('#submitBtn').on('click', function() {
        var questionInput = $('#questionInput');
        var questionText = questionInput.val().trim();

        if (questionText !== "") {
            var questionsContainer = $('#questionsContainer');

            var questionElement = $('<div></div>');
            questionElement.addClass('question');
            questionElement.text(questionText);

            questionsContainer.append(questionElement);

            questionInput.val(''); // Clear the input field
        } else {
            alert('Please enter a question.');
        }
    });
});
