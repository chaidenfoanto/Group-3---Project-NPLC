$(document).ready(function() {
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

    finishGameBtn.on('click', function() {
        modal.show();
    });

    closeBtn.on('click', function() {
        modal.hide();
    });

    $(window).on('click', function(event) {
        if (event.target == modal[0]) {
            modal.hide();
        }
    });

    winningTeamSelect.on('change', function() {
        const winningTeam = winningTeamSelect.val();
        pointsMessage.text(`100 POINTS WILL BE GIVEN TO ${winningTeam}`);
    });

    $('#gameEndForm').on('submit', function(event) {
        event.preventDefault();

        const winningTeam = $('#winningTeam').val();
        const timeStarted = $('#timeStarted').val();
        const timeFinished = $('#timeFinished').val();
        const duration = $('#duration').val();

        const historyItem = $(`
            <div class="history-item">
                <h2>Team Name <span>VS</span> Team Name</h2>
                <p>Time Started: <span>${timeStarted}</span> - Time Finished: <span>${timeFinished}</span></p>
                <p>Duration: <span>${duration}</span></p>
                <p>Winning Team: <span>${winningTeam}</span></p>
            </div>
        `);

        historyContainer.append(historyItem);

        modal.hide();
        $('#gameEndForm')[0].reset();
    });
});
