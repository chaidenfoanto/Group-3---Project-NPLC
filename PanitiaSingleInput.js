var interval;
var timer = '01:00';
let teamData = {};
let selectedTeamId = null;

const domain2 = 'http://localhost:8080/';

function getCookie(name) {
  let cookieArr = document.cookie.split(';'); 
  for (let i = 0; i < cookieArr.length; i++) {
    let cookiePair = cookieArr[i].split('='); 
    if (name == cookiePair[0].trim()) {
      return decodeURIComponent(cookiePair[1]); 
    }
  }
  return null; 
}

function getTime() {
  fetch(domain2 + 'api/boothgames/getSelfBooth', {
    method: 'GET',
    headers: { Token: getCookie('Token') },
  })
    .then((response) => response.json())
    .then((data) => {
      if (!data.error) {
        const durasiPermainanElement = $('.timeleft');
        var durasiPermainan = data.data.durasiPermainan.toString();
        timer = (durasiPermainan.length < 2 ? '0' + durasiPermainan : durasiPermainan) + ':00';
        durasiPermainanElement.text(timer);
      }
    });
}

getTime();


$(document).ready(function() {
    $(".sidebar").load("sidebarpanitia.html", function() {
        const toggleBtn = $("#toggle-btn, #burger-btn");
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

    function setCurrentTimeForTimeInput(selector) {
        var now = new Date();
        var formattedTime = ('0' + now.getHours()).slice(-2) + ':' +
                            ('0' + now.getMinutes()).slice(-2) + ':' +
                            ('0' + now.getSeconds()).slice(-2);
        $(selector).val(formattedTime);
    }

    $(document).on('click', function (e) {
        if (!$(e.target).closest('.sidebar, #toggle-btn, #burger-btn').length) {
          closeSidebar();
        }
    });

    function showModal() {
        $('#gameEndModal').show();
        $(".sidebar").toggleClass("disable");
        // setCurrentTimeForTimeInput('#timeFinished');
        calculateDuration();
        const team = $('#teamname').val();
        $('#teamplayed').val(team);
        $('#teamplayed').addClass('not-empty');
        $('#pointsMessage').text(`0 POINTS WILL BE GIVEN TO ${team}`);
    }

    document.getElementById("gameEndForm").addEventListener("submit", function (event) {
        event.preventDefault(); // Mencegah pengiriman formulir secara default
        submitHistory();
      });

    
    function fetchTeamNames() {
    fetch(domain2 + "api/team/getTeamPerGame", {
        method: "GET",
        headers: { Token: getCookie("Token") },
    })
        .then((response) => response.json())
        .then((data) => {
        const teamSelect = document.getElementById("teamname");

        teamSelect.innerHTML = "";
        data.data.forEach((team) => {
            teamData[team.namaTeam] = team.idTeam;
            const option = document.createElement("option");
            option.value = team.namaTeam;
            option.textContent = team.namaTeam;
            teamSelect.appendChild(option);
        });

        const placeholder = document.createElement("option");
        placeholder.value = "";
        teamSelect.prepend(placeholder);
        teamSelect.value = "";
        })
        .catch((error) => console.error("Error loading team names:", error));
    }
    
      function fetchAvailableCards(teamId) {
        cardsData = [];
        fetch(domain2 + "api/listkartu/getByTeam/" + teamId, {
          method: "GET",
          headers: { Token: getCookie("Token") },
        })
          .then((response) => response.json())
          .then((data) => {
            const cardSelect = document.getElementById("cardskill");
    
            cardSelect.innerHTML = "";
    
            if (data.data.availableCards && data.data.availableCards.length > 0) {
              cardsData = data.data.availableCards;
              data.data.availableCards.forEach((card) => {
                const option = document.createElement("option");
                option.value = card.cardSkill.namaKartu;
                option.textContent = card.cardSkill.namaKartu;
                cardSelect.appendChild(option);
              });
            }
    
            const placeholder = document.createElement("option");
            placeholder.value = "";
            cardSelect.prepend(placeholder);
            cardSelect.value = "";
          })
          .catch((error) => console.error("Error loading available cards:", error));
      }
    
      document.getElementById('teamname').addEventListener('change', function () {
        const selectedTeam = this.value;
        if (selectedTeam && teamData[selectedTeam]) {
          selectedTeamId = teamData[selectedTeam];
          fetchAvailableCards(selectedTeamId);
        } else {
          const cardSelect = document.getElementById('cardskill');
          cardSelect.innerHTML = '<option value=""></option>';
        }
      });
    
      fetchTeamNames();
  
    // Fungsi untuk mendapatkan card number paling atas dari response
    function getTopCardNumber(cardsData) {
      // Memastikan ada kartu yang tersedia
      if (
        cardsData &&
        cardsData.availableCards &&
        cardsData.availableCards.length > 0
      ) {
        // Ambil kartu teratas dan cardNumber teratas
        const topCardSkill = cardsData.availableCards[0];
        if (topCardSkill.cardNumbers && topCardSkill.cardNumbers.length > 0) {
          return topCardSkill.cardNumbers[0].cardNumber; // Kartu teratas
        }
      }
      return null; // Jika tidak ada kartu
    }

    // Fungsi untuk memulai game
    function postStartGame() {
        // Mengambil card number yang paling atas
        const topCardNumber = getTopCardNumber(cardsData);

        // if (!topCardNumber) {
        //   console.error("No available cards to start the game.");
        //   return;
        // }

        // Membuat objek data yang akan dikirim
        const data = {
        idTeam: selectedTeamId, // Menggunakan id team yang telah kamu simpan
        noKartu: topCardNumber, // Mengirim card number teratas
        };

        // Melakukan POST request ke server
        fetch(domain2 + "api/singlematch/start", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            Token: getCookie("Token"), // Token otentikasi jika diperlukan
        },
        body: JSON.stringify(data), // Mengubah objek data menjadi JSON
        })
        .then((response) => response.json())
        .then((data) => {
        console.log("Game started successfully:", data);
        const durasiPermainanElement = $('.timeleft');
        var durasiPermainan = data.data.gameData.durasi.menit.toString();
        timer = (durasiPermainan.length < 2 ? '0' + durasiPermainan : durasiPermainan) + ':00';
        durasiPermainanElement.text(timer);
        // Tambahkan aksi setelah game berhasil dimulai, seperti menavigasi ke halaman lain
        })
        .catch((error) => {
        console.error("Error starting game:", error);
        });
    }

    function sendGameEndData() {
        fetch(domain2 + 'api/singlematch/finish', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                Token: getCookie("Token"),
            },
            body: JSON.stringify()  // Jangan lupa isi dengan data jika diperlukan
        })
        .then(response => response.json())
        .then(data => {
            console.log('Success:', data);

            $('#timeStarted').val(data.data.gameData.startTime);
            $('#timeFinished').val(data.data.gameData.finishTime);
            calculateDuration();
        })
        .catch((error) => {
            console.error('Error:', error);
            // Tindakan jika terjadi error
        });
    }

    function submitHistory() {
        const data = {
            totalBintang: $('#starEarned').val(),
            };

        fetch(domain2 + 'api/singlematch/submit', {
            method: 'POST',
            headers:{
                'content-Type': 'application/json',
                Token: getCookie("Token"),
            },
            body: JSON.stringify(data)
        })
        .then(response => response.json())
        .then(data => {
            console.log('Success:', data);
        })
        .catch((error) => {
            console.error('Error:', error);
            // Tindakan jika terjadi error
        });
    }
    

  $('#startButton').click(function() {
    if ($(this).text() === "Start Game") {
        $(this).text("Finish Game");
        // setCurrentTimeForTimeInput('#timeStarted');
        postStartGame();
        startTimer(); // Fungsi untuk memulai timer
    } else {
        $(this).text("Start Game");
        stopTimer(); // Fungsi untuk menghentikan timer

    }
});

    function startTimer() {
        // Implementasi timer
        timestart = new Date();
        var timeArray = timer.split(':');
        var minutes = parseInt(timeArray[0]);
        var seconds = parseInt(timeArray[1]);

        var totalSeconds = minutes * 60 + seconds;

        var interval = setInterval(function() {
            totalSeconds--;
            var minutes = Math.floor(totalSeconds / 60);
            var seconds = totalSeconds % 60;

            $('.timeleft').text(pad(minutes) + ':' + pad(seconds));

            if (totalSeconds <= 0 || $('#startButton').text() === "Start Game") {
                clearInterval(interval);
                $('#startButton').text("Start Game");
                if (totalSeconds <= 0) {
                    // setCurrentTimeForTimeInput('#timeFinished');
                    showModal();
                } else {
                showModal();
                }
            }
        }, 1000);
    }

    function stopTimer() {
        // Menghentikan timer
        clearInterval(interval);
        showModal();
        sendGameEndData();
    }

    function clearform() {
        $('.timeleft').text(timer);
        $('#teamname').val("");
        $('#cardskill').val("");
        checkTeams();
    }

    function pad(val) {
        var valString = val + "";
        if (valString.length < 2) {
            return "0" + valString;
        } else {
            return valString;
        }
    }

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

    $('.close').on('click', function() {
        $('#gameEndModal').hide();
    });

    $(window).on('click', function(event) {
        if (event.target == modal[0]) {
            $('#gameEndModal').hide();
        }
    }); 
    
    $('#starEarned').on('change', function() {
        const team = $('#teamplayed').val();
        const star = $('#starEarned').val();
        const card = $('#cardskill').val();

        if (star === "1" && card === "Double Point") {
            $('#pointsMessage').text(`60 POINTS WILL BE GIVEN TO ${team}`);
        }
        else if (star === "2" && card === "Double Point") {
            $('#pointsMessage').text(`120 POINTS WILL BE GIVEN TO ${team}`);
        }
        else if (star === "3" && card === "Double Point") {
            $('#pointsMessage').text(`200 POINTS WILL BE GIVEN TO ${team}`);
        }
        else if (star === "1" && card != "Double point") {
            $('#pointsMessage').text(`30 POINTS WILL BE GIVEN TO ${team}`);
        } 
        else if (star === "2" && card != "Double point") {
            $('#pointsMessage').text(`60 POINTS WILL BE GIVEN TO ${team}`);
        }
        else if (star === "3" && card != "Double point") {
            $('#pointsMessage').text(`100 POINTS WILL BE GIVEN TO ${team}`);
        }
        else {
            $('#pointsMessage').text(`0 POINTS WILL BE GIVEN TO ${team}`);
        }
    });

    $('#gameEndForm').on('submit', function(event) {
        event.preventDefault();

        const team = $('#teamplayed').val();
        const star = $('#starEarned').val();
        const card = $('#cardskill').val() || '-';
        const timeStarted = $('#timeStarted').val();
        const timeFinished = $('#timeFinished').val();
        const duration = $('#duration').val();

        if (!team) {
            // Display the error message in the span element
            $('#teamplayed').closest('.input-group').find('.errorteam').text('Team should be filled.');
            return;
        } else {
            // Remove the error message if it exists
            $('#teamplayed').closest('.input-group').find('.errorteam').text('');
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
        clearform();

        $('#gameEndModal').hide();
        $('#gameEndForm')[0].reset();
        $('#history .noteam').remove();  // Remove the no team message if a new history item is added
    });

    $('#history').bind('DOMNodeInserted DOMNodeRemoved', checkHistory);
})
