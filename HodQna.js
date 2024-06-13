$(document).ready(function() {
    // Load the sidebar and handle the toggle button
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

    // Function to open the main popup
    function openPopup() {
        $("#popup").css("display", "flex");
    }

    // Function to close the main popup
    function closePopup() {
        $("#popup").css("display", "none");
    }

    // Event listener for addQuestionBtn to open the main popup
    $("#addQuestionBtn").on("click", openPopup);

    // Event listener for closePopupBtn to close the main popup
    $(".close-btn").on("click", closePopup);

    // Close the main popup when clicking outside of it
    $(window).on("click", function(event) {
        if (event.target === document.getElementById("popup")) {
            closePopup();
        }
    });

    // Handle form submission
    $("#questionForm").on("submit", function(event) {
        event.preventDefault(); // Prevent the default form submission

        // Get input values
        const teamName = $("#Teamname").val();
        const question = $("#Question").val();

        // Save to localStorage (or send to server via AJAX)
        const questionData = {
            teamName: teamName,
            question: question
        };

        // You can choose to use an array to store multiple entries
        let savedQuestions = JSON.parse(localStorage.getItem('questions')) || [];
        savedQuestions.push(questionData);
        localStorage.setItem('questions', JSON.stringify(savedQuestions));

        // Display confirmation message
        alert("Question submitted and saved!");

        // Clear form inputs
        $("#Teamname").val('');
        $("#Question").val('');

        // Close the main popup
        closePopup();
    });

    // Function to open the content popup with the details of the clicked box
    function openContentPopup(teamName, questionText, statusText) {
        $("#popupTeamName").text(teamName);
        $("#popupQuestionText").text(questionText);
        $("#popupStatusText").text(statusText);
        $("#contentPopup").css("display", "flex");
    }

    // Event listener for closing the content popup
    $("#closeContentPopup").on("click", function() {
        $("#contentPopup").css("display", "none");
    });

    // Close the content popup when clicking outside of it
    $(window).on("click", function(event) {
        if (event.target === document.getElementById("contentPopup")) {
            $("#contentPopup").css("display", "none");
        }
    });

    // Event listeners for "sudah terjawab" boxes to open the content popup
    $(".sudah-terjawab-box").on("click", function() {
        const teamName = $(this).find("h3").text();
        const questionText = $(this).find("p").text();
        const statusText = $(this).find(".status").text();
        openContentPopup(teamName, questionText, statusText);
    });

    // Function to open the question popup
    function openQuestionPopup() {
        $("#questionPopup").css("display", "flex");
    }

    // Event listener for opening the question popup
    $(".belum-terjawab-box").on("click", openQuestionPopup);

    // Event listener for closing the question popup
    $("#closeQuestionPopup").on("click", function() {
        $("#questionPopup").css("display", "none");
    });

    // Handle form submission for the question popup
    $("#questionForm").on("submit", function(event) {
        event.preventDefault(); // Prevent the default form submission

        // Get input values
        const teamName = $("#Teamname").val();
        const question = $("#Question").val();

        // Save to localStorage (or send to server via AJAX)
        const questionData = {
            teamName: teamName,
            question: question
        };

        // You can choose to use an array to store multiple entries
        let savedQuestions = JSON.parse(localStorage.getItem('questions')) || [];
        savedQuestions.push(questionData);
        localStorage.setItem('questions', JSON.stringify(savedQuestions));

        // Display confirmation message
        alert("Question submitted and saved!");

        // Clear form inputs
        $("#Teamname").val('');
        $("#Question").val('');

        // Close the question popup
        $("#questionPopup").css("display", "none");
    });
    
});
