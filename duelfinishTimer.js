$(document).ready(function() {
    // Memuat sidebar dari file sidebarpanitia.html saat dokumen telah siap
    $(".sidebar").load("sidebarpanitia.html", function() {
        const toggleBtn = $("#toggle-btn");
        const logo = $(".logo_details .logo").eq(1); // Memilih logo kedua
        toggleBtn.on("click", function() {
            $(".sidebar").toggleClass("open");
            menuBtnChange();
        });

        // Fungsi untuk mengubah ikon tombol menu saat sidebar dibuka/tutup
        function menuBtnChange() {
            if ($(".sidebar").hasClass("open")) {
                logo.hide();
            } else {
                logo.show();
            }
        }
    });

    // Fungsi untuk mengatur waktu saat ini pada input time
    function setCurrentTime(inputId) {
        var now = new Date();
        var hours = String(now.getHours()).padStart(2, '0');
        var minutes = String(now.getMinutes()).padStart(2, '0');
        var currentTime = hours + ':' + minutes;
        $(inputId).val(currentTime);
    }

    // Fungsi untuk menghitung durasi antara waktu mulai dan selesai
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

    // Perhitungan durasi saat input waktu berubah
    calculateDuration();
    $('#timeStarted, #timeFinished').on('change', calculateDuration);

    // Fungsi untuk mengecek riwayat permainan
    function checkHistory() {
        if ($('#history').is(':empty')) {
            $('#history').html('<p class="noteam">Belum ada tim yang bermain di booth Anda...</p>');
        }
    }
    checkHistory();

    // Fungsi untuk menambahkan kelas 'not-empty' pada input yang tidak kosong
    // dan menampilkan pesan error jika input kosong
    $('.matchup-container select, .modal-content select, .modal-content input').each(function() {
        if ($(this).val() !== '') {
            $(this).addClass('not-empty');
            $('.input-group .error').text('');
        }

        $(this).on('input', function() {
            if ($(this).val() !== '') {
                $(this).addClass('not-empty');
                $('.input-group .error').text('');
            } else {
                $(this).removeClass('not-empty');
                $('.input-group .error').text('Harap diisi.');
            }
        });
    });

    // Event saat form pengakhiran permainan disubmit
    $('#gameEndForm').on('submit', function(event) {
        event.preventDefault();

        const team1 = $('#team1').val();
        const team2 = $('#team2').val();
        const winningTeam = $('#winningTeam').val();
        const timeStarted = $('#timeStarted').val();
        const timeFinished = $('#timeFinished').val();
        const duration = $('#duration').val();

        if (!winningTeam) {
            // Tampilkan pesan error jika nama tim pemenang tidak diisi
            $('.input-group .error').text('Nama tim pemenang harus diisi.');
            return;
        } else {
            $('.input-group .error').text('');
        }

        // Buat elemen riwayat permainan dan tambahkan ke riwayat
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

        // Atur ulang form dan sembunyikan modal
        $('#gameEndModal').hide();
        $('#gameEndForm')[0].reset();
        $('#history .noteam').remove(); // Hapus pesan "Belum ada tim yang bermain" jika ada riwayat baru
    });

    // Event untuk mengecek perubahan pada riwayat permainan
    $('#history').bind('DOMNodeInserted DOMNodeRemoved', checkHistory);

    // Event saat nama tim pemenang berubah
    $('#winningTeam').on('change', function() {
        const winningTeam = $('#winningTeam').val();
        $('#pointsMessage').text(`100 POIN AKAN DIBERIKAN KEPADA ${winningTeam}`);
    });

    // Inisialisasi variabel dan seleksi elemen DOM
    const timerDisplay = document.querySelector('.timeleft');
    const startButton = document.querySelector('.startButton');
    const stopButton = document.querySelector('.stopButton');
    const setTimeForm = document.getElementById('setTimeForm');
    const setTimeModal = document.getElementById('setTimeModal');
    const gameEndModal = document.getElementById('gameEndModal');
    const historyContainer = document.getElementById('history');

    let timer;
    let remainingTime = 0;

    // Fungsi untuk memulai timer
    function startTimer(minutes, seconds) {
        remainingTime = minutes * 60 + seconds;
        updateDisplay();

        timer = setInterval(function() {
            remainingTime--;
            updateDisplay();

            if (remainingTime <= 0) {
                clearInterval(timer);
                openModal(gameEndModal);
            }
        }, 1000);
    }

    // Fungsi untuk menghentikan timer
    function stopTimer() {
        clearInterval(timer);
        remainingTime = 0;
        updateDisplay();
    }

    // Fungsi untuk memperbarui tampilan timer
    function updateDisplay() {
        const minutes = Math.floor(remainingTime / 60);
        const seconds = remainingTime % 60;
        timerDisplay.innerHTML = `<i class="fa-regular fa-clock" style="color: #000000"></i> ${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}`;
    }

    // Fungsi untuk membuka modal
    function openModal(modal) {
        modal.style.display = 'block';
    }

    // Fungsi untuk menutup modal
    function closeModal(modal) {
        modal.style.display = 'none';
    }

    // Fungsi untuk memulai permainan
    function startGame() {
        openModal(setTimeModal);
    }

    // Fungsi untuk menghentikan permainan
    function stopGame() {
        stopTimer();
        openModal(gameEndModal);
    }

    // Event Listeners
    startButton.addEventListener('click', startGame);
    stopButton.addEventListener('click', stopGame);
    
    setTimeForm.addEventListener('submit', function(event) {
        event.preventDefault();
        const minutes = parseInt(document.getElementById('minutesInput').value, 10);
        const seconds = parseInt(document.getElementById('secondsInput').value, 10);
        
        closeModal(setTimeModal);
        startTimer(minutes, seconds);
    });
    
    // Close modals when clicking the close button
    document.querySelectorAll('.modal .close').forEach(function(closeButton) {
        closeButton.addEventListener('click', function() {
            closeModal(closeButton.closest('.modal'));
        });
    });
});
