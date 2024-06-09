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

    $('.matchup-container select, .modal-content select, .modal-content input').each(function() {
        // Check if the input is not empty on page load
        if ($(this).val() !== '') {
            $(this).addClass('not-empty');
            $('.input-group .error').text('');
        }

        // Add event listener for input events
        $(this).on('input', function() {
            if ($(this).val() !== '') {
                $(this).addClass('not-empty');
                $('.input-group .error').text('');
            } else {
                $(this).removeClass('not-empty');
                $('.input-group .error').text('Winning team should be filled.');
            }
        });
    });

    $('.startButton').on('click', function() {
        $('#gameEndModal').show();
        setCurrentTime('#timeStarted');
        setCurrentTime('#timeFinished');
        calculateDuration();
    });

    $('.close').on('click', function() {
        $('#gameEndModal').hide();
    });

    $(window).on('click', function(event) {
        if (event.target == modal[0]) {
            $('#gameEndModal').hide();
        }
    });

    $('#winningTeam').on('change', function() {
        const winningTeam = $('#winningTeam').val();
        $('#pointsMessage').text(`100 POINTS WILL BE GIVEN TO ${winningTeam}`);
    });

    $('#gameEndForm').on('submit', function(event) {
        event.preventDefault();

        const team1 = $('#team1').val();
        const team2 = $('#team2').val();
        const winningTeam = $('#winningTeam').val();
        const timeStarted = $('#timeStarted').val();
        const timeFinished = $('#timeFinished').val();
        const duration = $('#duration').val();

        if (!winningTeam) {
            // Display the error message in the span element
            $('.input-group .error').text('Winning team should be filled.');
            return;
        } else {
            // Remove the error message if it exists
            $('.input-group .error').text('');
        }

        const historyItem = $(`
        <div class="history-item">
            <div class="history-row">
                <div class = "history-group team">
                    <div class="history-cell team" data-label="Team Name"><p>Team Name</p>
                    <p class="${team1 === winningTeam ? 'winner' : ''}">${team1}</p></div>
                    <div class="history-cell" data-label="VS">VS</div>
                    <div class="history-cell team" data-label="Team Name"><p>Team Name</p>
                    <p class="${team2 === winningTeam ? 'winner' : ''}">${team2}</p></div>
                </div>
                <div class = "history-group time">
                    <div class="history-cell time" data-label="Time Started"><p>Time Started</p><p>${timeStarted}</p></div>
                    <div class="history-cell" data-label="VS">-</div>
                    <div class="history-cell time" data-label="Time Finished"><p>Time Finished</p><p>${timeFinished}</p></div>
                </div>
                <div class="history-cell time" data-label="Duration"><p>Duration</p><p>${duration}</p></div>
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