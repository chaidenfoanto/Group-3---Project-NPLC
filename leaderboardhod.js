$(document).ready(function() {
    $(".sidebar").load("sidebarhod.html", function() {
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

    $(document).on('click', function (e) {
        if (!$(e.target).closest('.sidebar, #toggle-btn').length) {
          closeSidebar();
        }
    });

    function closeSidebar() {
        $('.sidebar').removeClass('open');
        // $('.main-content').removeClass('shift');
    }

    const leaderboardData = [
        { teamName: 'Nama Tim 1', boothsPlayed: '10/18', points: 1000 },
        { teamName: 'Nama Tim 2', boothsPlayed: '10/18', points: 900 },
        { teamName: 'Nama Tim 3', boothsPlayed: '10/18', points: 800 },
        // Add more data as needed
    ];

    const renderLeaderboard = (data) => {
        const $leaderboardBody = $('#leaderboard-body');
        $leaderboardBody.empty(); // Clear existing rows

        // Sort data by points in descending order
        data.sort((a, b) => b.points - a.points);

        $.each(data, function(index, item) {
            const $row = $('<tr>');

            const $rankCell = $('<td>').text(index + 1);
            $row.append($rankCell);

            const $teamNameCell = $('<td>').text(item.teamName);
            $row.append($teamNameCell);

            const $boothsPlayedCell = $('<td>').text(item.boothsPlayed);
            $row.append($boothsPlayedCell);

            const $pointsCell = $('<td>').text(item.points);
            $row.append($pointsCell);

            $leaderboardBody.append($row);
        });
    };

    renderLeaderboard(leaderboardData);

    // Example of updating data and re-rendering the leaderboard
    // setTimeout(function() {
    //     leaderboardData[1].points = 1100; // Update points for the second team
    //     renderLeaderboard(leaderboardData); // Re-render the leaderboard
    // }, 5000); // Update after 5 seconds
});
