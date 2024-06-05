$(document).ready(function() {
    // Fungsi untuk menampilkan sidebar
    $(".sidebar").load("sidebarpanitia.html", function() {
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

    // Fungsi untuk mengatur waktu saat ini
    function setCurrentTime(inputId) {
        var now = new Date();
        var hours = String(now.getHours()).padStart(2, '0');
        var minutes = String(now.getMinutes()).padStart(2, '0');
        var currentTime = hours + ':' + minutes;
        $(inputId).val(currentTime);
    }

    // Fungsi untuk menghitung durasi
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
        }
    }

    // Inisialisasi waktu saat ini pada input time
    setCurrentTime('#timeStarted');
    setCurrentTime('#timeFinished');

    // Perhitungan durasi
    calculateDuration();

    // Event untuk menghitung durasi saat input waktu berubah
    $('#timeStarted, #timeFinished').on('change', calculateDuration);

    // Fungsi untuk mengecek riwayat
    function checkHistory() {
        if ($('#history').is(':empty')) {
            $('#history').html('<p class="noteam">There are no teams playing in your booth yet...</p>');
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

    
        // Fungsi untuk menampilkan popup set waktu saat tombol Start Game ditekan
        $(".startButton").click(function() {
            $(".set-time-modal").css("display", "block");
        });
    
        // Fungsi untuk menutup popup set waktu saat tombol close di klik
        $(".set-time-modal .close").click(function() {
            $(".set-time-modal").css("display", "none");
        });
    
        // Fungsi untuk memulai countdown ketika tombol Start di popup set waktu ditekan
        $(".startTime").click(function() {
            var minutes = parseInt($("#minutesInput").val());
            var seconds = parseInt($("#secondsInput").val());
    
            if (isNaN(minutes) || isNaN(seconds) || minutes < 0 || seconds < 0 || seconds >= 60) {
                alert('Please enter valid time.');
                return;
            }
    
            var totalTime = minutes * 60 + seconds;
            startCountdown(totalTime);
    
            // Menutup popup set waktu
            $(".set-time-modal").css("display", "none");
        });
    
        // Fungsi untuk memulai countdown
        function startCountdown(totalTime) {
            var timerInterval = setInterval(function() {
                var minutes = Math.floor(totalTime / 60);
                var seconds = totalTime % 60;
    
                $('.timeleft').text(padZero(minutes) + ":" + padZero(seconds));
    
                if (totalTime <= 0) {
                    clearInterval(timerInterval);
                    $('#gameEndModal').show();
                } else {
                    totalTime--;
                }
            }, 1000);
        }
    
        // Fungsi untuk menambah nol di depan angka jika hanya satu digit
        function padZero(num) {
            return (num < 10 ? "0" : "") + num;
        }

    

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

    // Event untuk mengecek riwayat
    $('#history').bind('DOMNodeInserted DOMNodeRemoved', checkHistory);



    $('.stopButton').on('click', function() {
        $('#gameEndModal').show(); // Menampilkan popup "Game Has Ended"
    });
    
    // Fungsi untuk menutup popup permainan berakhir
    $('#gameEndModal .close').on('click', function() {
        $('#gameEndModal').hide();
    });

    // Fungsi untuk mengatur pesan poin berdasarkan pemenang
    $('#winningTeam').on('change', function() {
        const winningTeam = $('#winningTeam').val();
        $('#pointsMessage').text(`100 POINTS WILL BE GIVEN TO ${winningTeam}`);
    });

    // Fungsi untuk menambahkan riwayat permainan
    $('#setTimeForm').on('submit', function(event) {
        event.preventDefault();
        const minutes = parseInt($('#minutesInput').val());
        const seconds = parseInt($('#secondsInput').val());

        if (isNaN(minutes) || isNaN(seconds) || minutes < 0 || seconds < 0 || seconds >= 60) {
            alert('Please enter valid time.');
            return;
        }

        const totalSeconds = minutes * 60 + seconds;
        startTimer(totalSeconds);
        $('#set-time-modal').hide();
    });

    // Fungsi untuk memulai timer mundur
    function startTimer(totalSeconds) {
        var timer = setInterval(function() {
            totalSeconds--;
            var minutes = Math.floor(totalSeconds / 60);
            var seconds = totalSeconds % 60;

            $('.timeleft').text(minutes + ":" + (seconds < 10 ? "0" : "") + seconds);

            if (totalSeconds <= 0) {
                clearInterval(timer);
                $('#gameEndModal').show();
            }
        }, 1000);
    }

    // Event saat submit form set time
    $('#setTimeForm').on('submit', function(event) {
        event.preventDefault();
        const minutes = parseInt($('#minutesInput').val());
        const seconds = parseInt($('#secondsInput').val());

        if (isNaN(minutes) || isNaN(seconds) || minutes < 0 || seconds < 0 || seconds >= 60) {
            alert('Please enter valid time.');
            return;
        }

        const totalSeconds = minutes * 60 + seconds;
        startTimer(totalSeconds);
        $('#set-time-modal').hide();
    });
});
