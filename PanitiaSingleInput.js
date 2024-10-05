let changeTab = false;
$(window).focus(function () {
  if (changeTab) window.location.reload();
});

$(window).blur(function () {
  changeTab = true;
});

var interval;
var timer = "01:00";
let teamData = {};
let selectedTeamId = null;
let cardsData = [];

const domain2 = "http://localhost:8080/";

function getCookie(name) {
  let cookieArr = document.cookie.split(";");
  for (let i = 0; i < cookieArr.length; i++) {
    let cookiePair = cookieArr[i].split("=");
    if (name == cookiePair[0].trim()) {
      return decodeURIComponent(cookiePair[1]);
    }
  }
  return null;
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
        var durasiMenit = data.data.durasiPermainan.menit.toString();
        var durasiDetik = data.data.durasiPermainan.detik.toString();
        timer =
          (durasiMenit.length < 2 ? "0" + durasiMenit : durasiMenit) +
          ":" +
          (durasiDetik.length < 2 ? "0" + durasiDetik : durasiDetik);
        durasiPermainanElement.text(timer);
      }
    });
}

function fetchHistory() {
  fetch(domain2 + "api/singlematch/getHistory", {
    method: "GET",
    headers: { Token: getCookie("Token") },
  })
    .then((response) => response.json())
    .then((data) => {
      console.log("History:", data);
      if (data.data.length > 0) {
        $("#history .noteam").remove();
        data.data.forEach((history) => {
          const historyItem = $(`
                                <div class="history-item">
                                    <div class="history-row">
                                        <div class="history-cell team" data-label="Team Name">
                                            <p>Team Name</p>
                                            <p>${history.team.namaTeam}</p>
                                        </div>
                                        <div class="history-cell card" data-label="Card Used">
                                            <p class="pcard">Card Used</p>
                                            <p class="space">${
                                              history.cardUsed === "None"
                                                ? "-"
                                                : history.cardUsed.cardName
                                            }</p>
                                        </div>
                                        <div class="history-cell star" data-label="Star Earned">
                                            <p class="pstar">Star Earned</p>
                                            <p class="space">${
                                              history.totalBintang
                                            }</p>
                                        </div>
                                        <div class="history-group time">
                                            <div class="history-cell time" data-label="Time Started">
                                                <p>Time Started</p>
                                                <p>${history.waktuMulai}</p>
                                            </div>
                                            <div class="history-cell" data-label="VS">-</div>
                                            <div class="history-cell time" data-label="Time Finished">
                                                <p>Time Finished</p>
                                                <p>${history.waktuSelesai}</p>
                                            </div>
                                            <div class="history-cell time" data-label="Duration"><p>Duration</p><p>${
                                              history.durasi.menit +
                                              "m " +
                                              history.durasi.detik +
                                              "s"
                                            }</p></div>
                                        </div>
                                    </div>
                                </div>
                            `);

          // Append historyItem ke container history
          $("#history").append(historyItem);
        });
      }
    })
    .catch((error) => console.error("Error fetching history:", error));
}
fetchHistory();

$(document).ready(function () {
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
    if (!$(e.target).closest(".sidebar, #toggle-btn, #burger-btn").length) {
      closeSidebar();
    }
  });

  function showModal() {
    $("#gameEndModal").show();
    $(".sidebar").toggleClass("disable");
    calculateDuration();
    const team = $("#teamname").val();
    $("#teamplayed").val(team);
    $("#teamplayed").addClass("not-empty");
    $("#pointsMessage").text(`0 POINTS WILL BE GIVEN TO ${team}`);
  }

  document
    .getElementById("gameEndForm")
    .addEventListener("submit", function (event) {
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
    fetch(domain2 + "api/listkartu/getByTeamBooth/" + teamId, {
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

  document.getElementById("teamname").addEventListener("change", function () {
    const selectedTeam = this.value;
    if (selectedTeam && teamData[selectedTeam]) {
      selectedTeamId = teamData[selectedTeam];
      fetchAvailableCards(selectedTeamId);
    } else {
      const cardSelect = document.getElementById("cardskill");
      cardSelect.innerHTML = '<option value=""></option>';
    }
  });

  // Fungsi untuk mendapatkan card number paling atas dari response
  function getTopCardNumber(selectedCard) {
    let selectedCardNumber = null;
    // Memastikan ada kartu yang tersedia
    if (cardsData.length > 0) {
      cardsData.forEach((card) => {
        if (card.cardSkill.namaKartu == selectedCard) {
          selectedCardNumber = card.cardNumbers[0].cardNumber;
        }
      });
    }
    return selectedCardNumber; // Jika tidak ada kartu
  }

  // Fungsi untuk memulai game
  function postStartGame() {
    // Mengambil card number yang paling atas
    const topCardNumber = getTopCardNumber($("#cardskill").val());

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
        const durasiPermainanElement = $(".timeleft");
        var durasiMenit = data.data.gameData.durasi.menit.toString();
        var durasiDetik = data.data.gameData.durasi.detik.toString();
        timer =
          (durasiMenit.length < 2 ? "0" + durasiMenit : durasiMenit) +
          ":" +
          (durasiDetik.length < 2 ? "0" + durasiDetik : durasiDetik);
        durasiPermainanElement.text(timer);
        startTimer();
        // Tambahkan aksi setelah game berhasil dimulai, seperti menavigasi ke halaman lain
      })
      .catch((error) => {
        console.error("Error starting game:", error);
      });
  }

  function sendGameEndData() {
    fetch(domain2 + "api/singlematch/finish", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Token: getCookie("Token"),
      },
      body: JSON.stringify(), // Jangan lupa isi dengan data jika diperlukan
    })
      .then((response) => response.json())
      .then((data) => {
        console.log("Success:", data);

        $("#timeStarted").val(data.data.gameData.startTime);
        $("#timeFinished").val(data.data.gameData.finishTime);
        calculateDuration();
      })
      .catch((error) => {
        console.error("Error:", error);
        // Tindakan jika terjadi error
      });
  }

  async function submitHistory() {
    const data = {
      totalBintang: $("#starEarned").val(),
    };

    await fetch(domain2 + "api/singlematch/submit", {
      method: "POST",
      headers: {
        "content-Type": "application/json",
        Token: getCookie("Token"),
      },
      body: JSON.stringify(data),
    })
      .then((response) => response.json())
      .then((data) => {
        console.log("Success:", data);
      })
      .catch((error) => {
        console.error("Error:", error);
        // Tindakan jika terjadi error
      });
      // window.location.reload()
  }

  function getCurrentGame() {
    fetch(domain2 + "api/singlematch/getCurrentGame", {
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

          const teamSelect = document.getElementById("teamname");

          teamSelect.innerHTML = "";
          teamData[data.data.gameData.team.namaTeam] =
            data.data.gameData.team.idTeam;
          let option = document.createElement("option");
          option.value = data.data.gameData.team.namaTeam;
          option.textContent = data.data.gameData.team.namaTeam;
          teamSelect.appendChild(option);

          let placeholder = document.createElement("option");
          placeholder.value = "";
          teamSelect.prepend(placeholder);
          teamSelect.value = "";

          selectedTeamId = data.data.gameData.team.idTeam;
          $("#teamname").val(data.data.gameData.team.namaTeam);
          $("#teamname").addClass("not-empty");
          checkTeams();

          let cardSelect = document.getElementById("cardskill");

          cardSelect.innerHTML = "";

          let cardUsed =
            data.data.gameData.cardUsed === "None"
              ? ""
              : data.data.gameData.cardUsed.cardName;

          if (cardUsed !== "") {
            option = document.createElement("option");
            option.value = cardUsed;
            option.textContent = cardUsed;
            cardSelect.appendChild(option);

            placeholder = document.createElement("option");
            placeholder.value = "";
            cardSelect.prepend(placeholder);
            cardSelect.value = "";
          }

          $("#cardskill").val(cardUsed);
          $("#cardskill").addClass("not-empty");
          if (data.data.gameStatus === "Started") {
            startGame(false);
            $("#teamname").prop("disabled", true);
            $("#cardskill").prop("disabled", true);
          } else {
            $("#timeStarted").val(data.data.gameData.startTime);
            $("#timeFinished").val(data.data.gameData.finishTime);
            $("#teamplayed").val(data.data.gameData.team.namaTeam);
            calculateDuration();
            showModal();
          }
        }
      })
      .catch((error) => {
        console.error("Error:", error);
      });
  }
  getCurrentGame();

  function startGame(initialStart = true) {
    if ($("#startButton").text() === "Start Game") {
      $("#startButton").text("Finish Game");
      if (initialStart) postStartGame();
      if (!initialStart) startTimer(); // Fungsi untuk memulai timer
      $("#teamname").prop("disabled", true);
      $("#cardskill").prop("disabled", true);
    } else {
      $("#startButton").text("Start Game");
      stopTimer(); // Fungsi untuk menghentikan timer
    }
  }
  $("#startButton").click(startGame);

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

  function stopTimer() {
    // Menghentikan timer
    clearInterval(interval);
    showModal();
    sendGameEndData();
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
    $(".sidebar").removeClass("open");
    // $('.main-content').removeClass('shift');
  }

  function setCurrentTime(inputId) {
    var now = new Date();
    var hours = String(now.getHours()).padStart(2, "0");
    var minutes = String(now.getMinutes()).padStart(2, "0");
    var currentTime = hours + ":" + minutes;
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
    const teamname = $("#teamname").val();
    if (teamname) {
      $("#startButton").prop("disabled", false);
    } else {
      $("#startButton").prop("disabled", true);
    }
  }

  // Check on page load
  checkTeams();

  // Check when either select element changes
  $("#teamname").on("change", checkTeams);

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
        $(".input-group .error").text("Team should be filled.");
      }
    });
  });

  $(".close").on("click", function () {
    $("#gameEndModal").hide();
  });

  //   $(window).on("click", function (event) {
  //     if (event.target == modal[0]) {
  //       $("#gameEndModal").hide();
  //     }
  //   });

  $("#starEarned").on("change", function () {
    const team = $("#teamplayed").val();
    const star = $("#starEarned").val();
    const card = $("#cardskill").val();

    if (star === "1" && card === "Double Point") {
      $("#pointsMessage").text(`60 POINTS WILL BE GIVEN TO ${team}`);
    } else if (star === "2" && card === "Double Point") {
      $("#pointsMessage").text(`120 POINTS WILL BE GIVEN TO ${team}`);
    } else if (star === "3" && card === "Double Point") {
      $("#pointsMessage").text(`200 POINTS WILL BE GIVEN TO ${team}`);
    } else if (star === "1" && card != "Double point") {
      $("#pointsMessage").text(`30 POINTS WILL BE GIVEN TO ${team}`);
    } else if (star === "2" && card != "Double point") {
      $("#pointsMessage").text(`60 POINTS WILL BE GIVEN TO ${team}`);
    } else if (star === "3" && card != "Double point") {
      $("#pointsMessage").text(`100 POINTS WILL BE GIVEN TO ${team}`);
    } else {
      $("#pointsMessage").text(`0 POINTS WILL BE GIVEN TO ${team}`);
    }
  });

  $("#gameEndForm").on("submit", function (event) {
    event.preventDefault();

    const team = $("#teamplayed").val();
    const star = $("#starEarned").val();
    const card = $("#cardskill").val() || "-";
    const timeStarted = $("#timeStarted").val();
    const timeFinished = $("#timeFinished").val();
    const duration = $("#duration").val();

    if (!team) {
      // Display the error message in the span element
      $("#teamplayed")
        .closest(".input-group")
        .find(".errorteam")
        .text("Team should be filled.");
      return;
    } else {
      // Remove the error message if it exists
      $("#teamplayed").closest(".input-group").find(".errorteam").text("");
    }

    $("#gameEndModal").hide();
    $("#gameEndForm")[0].reset();
    $("#history .noteam").remove(); // Remove the no team message if a new history item is added
  });

  $("#history").bind("DOMNodeInserted DOMNodeRemoved", checkHistory);
});
