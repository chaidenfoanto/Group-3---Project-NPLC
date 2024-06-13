$(document).ready(function() {
    $(".sidebar").load("sidebarpanitia.html", function() {
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

    function setCurrentTime(inputId) {
        var now = new Date();
        var hours = String(now.getHours()).padStart(2, '0');
        var minutes = String(now.getMinutes()).padStart(2, '0');
        var currentTime = hours + ':' + minutes;
        $(inputId).val(currentTime);
    }

    function calculateDuration() {
        var startTime = $('#timeStarted').val();
        var endTime = $('#timeFinished').val();

        if (startTime && endTime) {
        const start = new Date(`1970-01-01T${startTime}`);
        const end = new Date(`1970-01-01T${endTime}`);
        const diffMs = end - start;

        if (diffMs >= 0) {
            const diffSecs = Math.floor(diffMs / 1000);
            const minutes = Math.floor(diffSecs / 60);
            const seconds = diffSecs % 60;

            duration = `${minutes}m ${String(seconds).padStart(2, '0')}s`;
            $('#duration').val(duration);
        }
        // } else {
        //     alert('End time must be after start time.');
        //     return;
        // }
    }
    }

    setCurrentTime('#timeStarted');
    setCurrentTime('#timeFinished');
    calculateDuration();

    $('#timeStarted, #timeFinished').on('change', calculateDuration);

    function checkHistory() {
        if ($('#history').is(':empty')) {
            $('#history').html('<p class = "noteam">There are no team playing in your booth yet...</p>');
        }
    }

    checkHistory();

    function checkTeams() {
        const teamname = $('#teamname').val();
        if (teamname) {
            $('#startButton').prop('disabled', false);
        } else {
            $('#startButton').prop('disabled', true);
        }
    }

    // Check on page load
    checkTeams();

    // Check when either select element changes
    $('#teamname').on('change', checkTeams);

    $('.matchup-container select, .modal-content select, .modal-content input').each(function() {
        // Check if the input is not empty on page load
        if ($(this).val() !== '') {
            $(this).addClass('not-empty');
            $('.input-group .error').text('');
        }

        // Add event listener for input events
        $(this).on('change', function() {
            if ($(this).val() !== '') {
                $(this).addClass('not-empty');
                $('.input-group .error').text('');
            } else {
                $(this).removeClass('not-empty');
                $('.input-group .error').text('Team should be filled.');
            }
        });
    });

    $('.startButton').on('click', function() {
        $('#gameEndModal').show();
        setCurrentTime('#timeStarted');
        setCurrentTime('#timeFinished');
        calculateDuration();
        $('#teamPlayed').val($('#teamname').val());
        $('#teamPlayed').addClass('not-empty');
    });

    $('.close').on('click', function() {
        $('#gameEndModal').hide();
    });

    $(window).on('click', function(event) {
        if (event.target == modal[0]) {
            $('#gameEndModal').hide();
        }
    });

    $('#teamPlayed').on('change', function() {
        const team = $('#teamPlayed').val();
        $('#pointsMessage').text(`100 POINTS WILL BE GIVEN TO ${team}`);
    });

    $('#gameEndForm').on('submit', function(event) {
        event.preventDefault();

        const team = $('#teamPlayed').val();
        const star = $('#starEarned').val();
        const card = $('#cardskill').val() || '-';
        const timeStarted = $('#timeStarted').val();
        const timeFinished = $('#timeFinished').val();
        const duration = $('#duration').val();

        if (!team) {
            // Display the error message in the span element
            $('#teamPlayed').closest('.input-group').find('.errorteam').text('Team should be filled.');
            return;
        } else {
            // Remove the error message if it exists
            $('#teamPlayed').closest('.input-group').find('.errorteam').text('');
        }

        const historyItem = $(`
        <div class="history-item">
            <div class="history-row">
                <div class="history-cell team" data-label="Team Name"><p>Team Name</p>
                <p>${team}</p></div>
                <div class="history-cell card" data-label="Card Used"><p class = "pcard">Card Used</p>
                <p class = "space">${card}</p></div>
                <div class="history-cell star" data-label="Star Earned"><p class = "pstar">Star Earned</p>
                <p class = "space">${star}</p></div>
                <div class = "history-group time">
                    <div class="history-cell time" data-label="Time Started"><p>Time Started</p><p>${timeStarted}</p></div>
                    <div class="history-cell" data-label="VS">-</div>
                    <div class="history-cell time" data-label="Time Finished"><p>Time Finished</p><p>${timeFinished}</p></div>
                </div>
                <div class="history-cell" data-label="Duration"><p class = "pduration">Duration</p><p class = "space">${duration}</p></div>
            </div>
        </div>
    `);

        $('#history').append(historyItem);

        $('#gameEndModal').hide();
        $('#gameEndForm')[0].reset();
        $('#history .noteam').remove();  // Remove the no team message if a new history item is added
    });

    $('#history').bind('DOMNodeInserted DOMNodeRemoved', checkHistory);
});
