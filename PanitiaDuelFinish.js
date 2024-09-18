var interval;
var timer = "01:00";
let teamData = {};
let selectedTeamId1 = null;
let selectedTeamId2 = null;

const domain2 = "http://localhost:8080/";

function getCookie(name) {
  let cookieArr = document.cookie.split(";");
  for (let i = 0; i < cookieArr.length; i++) {
    let cookiePair = cookieArr[i].split("=");
    if (name == cookiePair[0].trim()) {
      return decodeURIComponent(cookiePair[1]);
    }
  }
  return null; // Mengembalikan null jika cookie tidak ditemukan
}

function getTime() {
  fetch(domain2 + "api/boothgames/getSelfBooth", {
    method: "GET",
    headers: { Token: getCookie("Token") },
  })
    .then((response) => response.json())
    .then((data) => {
      if (!data.error) {
        const durasiPermainanElement = $(".timeleft");
        var durasiPermainan = data.data.durasiPermainan.toString();
        timer =
          (durasiPermainan.length < 2
            ? "0" + durasiPermainan
            : durasiPermainan) + ":00";
        durasiPermainanElement.text(timer);
      }
    });
}

$(document).ready(function () {
  function stopTimer() {
    // Menghentikan timer
    clearInterval(interval);
    showModal();
  }
  
  function pad(val) {
    var valString = val + "";
    if (valString.length < 2) {
      return "0" + valString;
    } else {
      return valString;
    }
  }

  function startTimer() {
    // Implementasi timer
    timestart = new Date();
    var timeArray = timer.split(":");
    var minutes = parseInt(timeArray[0]);
    var seconds = parseInt(timeArray[1]);

    var totalSeconds = minutes * 60 + seconds;

    var interval = setInterval(function () {
      totalSeconds--;
      var minutes = Math.floor(totalSeconds / 60);
      var seconds = totalSeconds % 60;

      $(".timeleft").text(pad(minutes) + ":" + pad(seconds));

      if (totalSeconds <= 0 || $("#startButton").text() === "Start Game") {
        clearInterval(interval);
        $("#startButton").text("Start Game");
        if (totalSeconds <= 0) {
          stopTimer();
        }
      }
    }, 1000);
  }

  function startGame(initialStart = true) {
    if ($("#startButton").text() === "Start Game") {
      $("#startButton").text("Finish Game");
      if (initialStart) postStartGame();
      if (!initialStart) startTimer(); // Fungsi untuk memulai timer
      $("#team1").prop("disabled", true);
      $("#team2").prop("disabled", true);
    } else {
      $("#startButton").text("Start Game");
      stopTimer(); // Fungsi untuk menghentikan timer
    }
  }
  $("#startButton").click(startGame);

  $(".sidebar").load("sidebarpanitia.html", function () {
    const toggleBtn = $("#toggle-btn, #burger-btn");
    const logo = $(".logo_details .logo").eq(1); // Select the second logo
    toggleBtn.on("click", function () {
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

  $(document).on("click", function (e) {
    if (!$(e.target).closest(".sidebar, #toggle-btn").length) {
      closeSidebar();
    }
  });

  function showModal() {
    $("#gameEndModal").show();
    $(".sidebar").toggleClass("disable");
    calculateDuration();
    $("#teamPlayed").val($("#teamname").val());
    $("#teamPlayed").addClass("not-empty");
    const team = $("#teamname").val();
    if (team.val().undefined()) {
      $("#pointsMessage").text(`100 POINTS WILL BE GIVEN TO ...`);
    } else {
      $("#pointsMessage").text(`100 POINTS WILL BE GIVEN TO ${team}`);
    }
  }

  // Fungsi untuk memulai game
  // Fungsi untuk memulai game
  function postStartGame() {
    if (!selectedTeamId1 || !selectedTeamId2) {
      console.error("Both teams must be selected.");
      alert("Please select both teams.");
      return;
    }

    // Membuat objek data yang akan dikirim
    const data = {
      team1: selectedTeamId1, // ID tim pertama
      team2: selectedTeamId2, // ID tim kedua
    };

    // Melakukan POST request ke server
    fetch(domain2 + "api/duelmatch/start", {
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
        const durasiPermainanElement = $(".timeleft");
        var durasiMenit = data.data.gameData.durasi.menit.toString();
        var durasiDetik = data.data.gameData.durasi.detik.toString();
        timer =
          (durasiMenit.length < 2 ? "0" + durasiMenit : durasiMenit) +
          ":" +
          (durasiDetik.length < 2 ? "0" + durasiDetik : durasiDetik);
        durasiPermainanElement.text(timer);
        startTimer();
      })
      .catch((error) => {
        console.error("Error starting game:", error);
        alert(data);
      });
  }

  function fetchTeamNames() {
    fetch(domain2 + "api/team/getTeamPerGame", {
      method: "GET",
      headers: { Token: getCookie("Token") }, // Menyertakan token dalam header dari cookie
    })
      .then((response) => response.json())
      .then((data) => {
        const teamSelect1 = document.getElementById("team1");
        const teamSelect2 = document.getElementById("team2");

        // Hapus semua opsi yang ada
        teamSelect1.innerHTML = "";
        teamSelect2.innerHTML = "";

        // Tambahkan opsi untuk setiap tim dari data yang diterima
        data.data.forEach((team) => {
          teamData[team.namaTeam] = team.idTeam; // Simpan data tim dalam objek teamData
          const option1 = document.createElement("option");
          option1.value = team.namaTeam; // Isi nilai dan teks opsi dengan nama tim
          option1.textContent = team.namaTeam;
          teamSelect1.appendChild(option1);

          const option2 = document.createElement("option");
          option2.value = team.namaTeam; // Isi nilai dan teks opsi dengan nama tim
          option2.textContent = team.namaTeam;
          teamSelect2.appendChild(option2);
        });

        // Setel opsi placeholder
        const placeholder1 = document.createElement("option");
        const placeholder2 = document.createElement("option");
        placeholder1.value = "";
        placeholder2.value = "";
        teamSelect1.prepend(placeholder1); // Tambahkan placeholder di bagian atas
        teamSelect2.prepend(placeholder2); // Tambahkan placeholder di bagian atas
        teamSelect1.value = "";
        teamSelect2.value = "";
      })
      .catch((error) => console.error("Error loading team names:", error)); // Menangani dan log error jika permintaan gagal
  }

  function getCurrentGame() {
    fetch(domain2 + "api/duelmatch/getCurrentGame", {
      method: "GET",
      headers: { Token: getCookie("Token") },
    })
      .then((response) => response.json()) // Hapus tanda titik koma di sini
      .then((data) => {
        console.log(data);
        if (data.error) {
          if (data.message === "No Current Game Running") {
            getTime();
            fetchTeamNames();
          }
        } else {
          const durasiPermainanElement = $(".timeleft");
          var sisaMenit = data.data.gameData.sisaWaktu.menit.toString();
          var sisaDetik = data.data.gameData.sisaWaktu.detik.toString();
          timer =
            (sisaMenit.length < 2 ? "0" + sisaMenit : sisaMenit) +
            ":" +
            (sisaDetik.length < 2 ? "0" + sisaDetik : sisaDetik);
          durasiPermainanElement.text(timer);

          const teamSelect1 = document.getElementById("team1");
          const teamSelect2 = document.getElementById("team2");

          // Hapus semua opsi yang ada
          teamSelect1.innerHTML = "";
          teamSelect2.innerHTML = "";

          // Tambahkan opsi untuk setiap tim dari data yang diterima
          teamData[data.data.gameData.team1.namaTeam] =
            data.data.gameData.team1.idTeam;
          const option1 = document.createElement("option");
          option1.value = team1.namaTeam; // Isi nilai dan teks opsi dengan nama tim
          option1.textContent = team1.namaTeam;
          teamSelect1.appendChild(option1);

          teamData[data.data.gameData.team2.namaTeam] =
            data.data.gameData.team2.idTeam;
          const option2 = document.createElement("option");
          option2.value = team2.namaTeam; // Isi nilai dan teks opsi dengan nama tim
          option2.textContent = team2.namaTeam;
          teamSelect2.appendChild(option2);

          // Setel opsi placeholder
          const placeholder1 = document.createElement("option");
          const placeholder2 = document.createElement("option");
          placeholder1.value = "";
          placeholder2.value = "";
          teamSelect1.prepend(placeholder1); // Tambahkan placeholder di bagian atas
          teamSelect2.prepend(placeholder2); // Tambahkan placeholder di bagian atas
          teamSelect1.value = "";
          teamSelect2.value = "";

          selectedTeamId1 = data.data.gameData.team1.idTeam;
          selectedTeamId2 = data.data.gameData.team2.idTeam;
          $("#team1").val(data.data.gameData.team1.namaTeam);
          $("#team2").val(data.data.gameData.team2.namaTeam);
          $("#team1").addClass("not-empty");
          $("#team2").addClass("not-empty");

          checkTeams();
        }
      })
      .catch((error) => {
        console.error("Error:", error);
      });
  }
  getCurrentGame();

  // Simpan ID tim ketika dropdown berubah
  document.getElementById("team1").addEventListener("change", function () {
    const selectedTeamName1 = this.value; // Nama tim yang dipilih
    selectedTeamId1 = teamData[selectedTeamName1]; // Simpan ID tim berdasarkan nama tim
  });

  document.getElementById("team2").addEventListener("change", function () {
    const selectedTeamName2 = this.value; // Nama tim yang dipilih
    selectedTeamId2 = teamData[selectedTeamName2]; // Simpan ID tim berdasarkan nama tim
  });

  // Fungsi untuk memperbarui opsi tim yang dipilih
  function updateTeamOptions() {
    const teamSelect1 = document.getElementById("team1");
    const teamSelect2 = document.getElementById("team2");
    const winningTeamSelect = document.getElementById("winningTeam");
    const selectedTeam1 = teamSelect1.value;
    const selectedTeam2 = teamSelect2.value;

    if (selectedTeam1 != "") {
      $("#team1").addClass("not-empty");
      selectedTeamId1 = teamData[selectedTeam1]; // Simpan ID tim dari teamSelect1
    } else {
      $("#team1").removeClass("not-empty");
      selectedTeamId1 = null; // Reset jika tidak ada tim yang dipilih
    }

    if (selectedTeam2 != "") {
      $("#team2").addClass("not-empty");
      selectedTeamId2 = teamData[selectedTeam2]; // Simpan ID tim dari teamSelect2
    } else {
      $("#team2").removeClass("not-empty");
      selectedTeamId2 = null; // Reset jika tidak ada tim yang dipilih
    }

    // Nonaktifkan opsi di teamSelect1 jika tim tersebut dipilih di teamSelect2 dan sebaliknya
    for (let i = 0; i < teamSelect1.options.length; i++) {
      const option1 = teamSelect1.options[i];
      const option2 = teamSelect2.options[i];

      if (option1.value === selectedTeam2) {
        option1.disabled = true;
      } else {
        option1.disabled = false;
      }

      if (option2.value === selectedTeam1) {
        option2.disabled = true;
      } else {
        option2.disabled = false;
      }
    }

    // Perbarui opsi untuk winningTeam
    winningTeamSelect.innerHTML = "";
    const placeholder = document.createElement("option");
    placeholder.value = "";
    winningTeamSelect.appendChild(placeholder);

    if (selectedTeam1) {
      const option1 = document.createElement("option");
      option1.value = selectedTeam1;
      option1.textContent = selectedTeam1;
      winningTeamSelect.appendChild(option1);
    }

    if (selectedTeam2) {
      const option2 = document.createElement("option");
      option2.value = selectedTeam2;
      option2.textContent = selectedTeam2;
      winningTeamSelect.appendChild(option2);
    }

    checkTeams();
  }

  // Menambahkan event listener untuk mengupdate opsi tim ketika pilihan berubah
  document
    .getElementById("team1")
    .addEventListener("change", updateTeamOptions);
  document
    .getElementById("team2")
    .addEventListener("change", updateTeamOptions);

  // Memanggil fungsi untuk mengambil nama-nama tim saat dokumen siap
  fetchTeamNames();

  function setCurrentTimeForTimeInput(selector) {
    var now = new Date();
    var formattedTime =
      ("0" + now.getHours()).slice(-2) +
      ":" +
      ("0" + now.getMinutes()).slice(-2) +
      ":" +
      ("0" + now.getSeconds()).slice(-2);
    $(selector).val(formattedTime);
  }

  function closeSidebar() {
    $(".sidebar").removeClass("open");
    // $('.main-content').removeClass('shift');
  }

  function setCurrentTime(inputId) {
    var now = new Date();
    var hours = String(now.getHours()).padStart(2, "0");
    var minutes = String(now.getMinutes()).padStart(2, "0");
    var seconds = String(now.getSeconds()).padStart(2, "0");
    var currentTime = hours + ":" + minutes + ":" + seconds;
    $(inputId).val(currentTime);
  }

  function calculateDuration() {
    var startTime = $("#timeStarted").val();
    var endTime = $("#timeFinished").val();

    if (startTime && endTime) {
      const start = new Date(`1970-01-01T${startTime}`);
      const end = new Date(`1970-01-01T${endTime}`);
      const diffMs = end - start;

      if (diffMs >= 0) {
        const diffSecs = Math.floor(diffMs / 1000);
        const minutes = Math.floor(diffSecs / 60);
        const seconds = diffSecs % 60;

        duration = `${minutes}m ${String(seconds).padStart(2, "0")}s`;
        $("#duration").val(duration);
      }
    }
  }

  setCurrentTime("#timeStarted");
  setCurrentTime("#timeFinished");
  calculateDuration();

  $("#timeStarted, #timeFinished").on("change", calculateDuration);

  function checkHistory() {
    if ($("#history").is(":empty")) {
      $("#history").html(
        '<p class = "noteam">There are no team playing in your booth yet...</p>'
      );
    }
  }

  checkHistory();

  function checkTeams() {
    const team1 = $("#team1").val();
    const team2 = $("#team2").val();
    if (team1 && team2) {
      $("#startButton").prop("disabled", false);
    } else {
      $("#startButton").prop("disabled", true);
    }
  }

  // Check on page load
  checkTeams();

  $(
    ".matchup-container select, .modal-content select, .modal-content input"
  ).each(function () {
    // Check if the input is not empty on page load
    if ($(this).val() !== "") {
      $(this).addClass("not-empty");
      $(".input-group .error").text("");
    }

    // Add event listener for input events
    $(this).on("change", function () {
      if ($(this).val() !== "") {
        $(this).addClass("not-empty");
        $(".input-group .error").text("");
      } else {
        $(this).removeClass("not-empty");
        $(".input-group .error").text("Winning team should be filled.");
      }
    });
  });

  $(".close").on("click", function () {
    $("#gameEndModal").hide();
  });

  $("#winningTeam").on("change", function () {
    const winningTeam = $("#winningTeam").val();
    $("#pointsMessage").text(`100 POINTS WILL BE GIVEN TO ${winningTeam}`);
  });

  $("#gameEndForm").on("submit", function (event) {
    event.preventDefault();

    const team1 = $("#team1").val();
    const team2 = $("#team2").val();
    const winningTeam = $("#winningTeam").val();
    const timeStarted = $("#timeStarted").val();
    const timeFinished = $("#timeFinished").val();
    const duration = $("#duration").val();

    if (!winningTeam) {
      // Display the error message in the span element
      $(".input-group .error").text("Winning team should be filled.");
      return;
    } else {
      // Remove the error message if it exists
      $(".input-group .error").text("");
    }

    const historyItem = $(`
        <div class="history-item">
            <div class="history-row">
                <div class = "history-group team">
                    <div class="history-cell team" data-label="Team Name"><p>Team Name</p>
                    <p class="${
                      team1 === winningTeam ? "winner" : ""
                    }">${team1}</p></div>
                    <div class="history-cell" data-label="VS">VS</div>
                    <div class="history-cell team" data-label="Team Name"><p>Team Name</p>
                    <p class="${
                      team2 === winningTeam ? "winner" : ""
                    }">${team2}</p></div>
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

    $("#history").append(historyItem);
    clearform();

    $("#gameEndModal").hide();
    $("#gameEndForm")[0].reset();

    checkHistory();
  });
});
