var interval;
var timer = "01:00:00";

$(document).ready(function () {
  $(".sidebar").load("sidebarplayer.html", function () {
    const toggleBtn = $("#toggle-btn, #burger-btn");
    const logo = $(".logo_details .logo").eq(1);
    toggleBtn.on("click", function () {
      $(".sidebar").toggleClass("open");
      updateLogo();
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

  function pad(val) {
    var valString = val + "";
    if (valString.length < 2) {
      return "0" + valString;
    } else {
      return valString;
    }
  }

  function stopTimer() {
    // Menghentikan timer
    clearInterval(interval);
  }

  function startTimer() {
    timestart = new Date();
    var timeArray = timer.split(":");
    var hours = parseInt(timeArray[0]);
    var minutes = parseInt(timeArray[1]);
    var seconds = parseInt(timeArray[2]);

    var totalSeconds = hours * 3600 + minutes * 60 + seconds;

    var interval = setInterval(function () {
      totalSeconds--;
      var hours = Math.floor(totalSeconds / 3600);
      var minutes = Math.floor(totalSeconds % 3600 / 60);
      var seconds = totalSeconds % 60;

      $("span.timeleft").text(
        pad(hours) + ":" + pad(minutes) + ":" + pad(seconds)
      );

      if (hours <= 0 && minutes <= 0 && seconds <= 0) {
        clearInterval(interval);
        stopTimer();
      }
    }, 1000);
  }

  function getTime() {
    fetch(domain + "api/statusnplc/getNPLCStatus", {
      method: "GET",
      headers: {
        Token: getCookie("Token"),
      },
    })
      .then((response) => response.json())
      .then((data) => {
        if (!data.error) {
          const durasiPermainanElement = $("span.timeleft");
          var durasiJam = data.data.sisaWaktu.jam.toString();
          var durasiMenit = data.data.sisaWaktu.menit.toString();
          var durasiDetik = data.data.sisaWaktu.detik.toString();
          timer =
            (durasiJam.length < 2 ? "0" + durasiJam : durasiJam) +
            ":" +
            (durasiMenit.length < 2 ? "0" + durasiMenit : durasiMenit) +
            ":" +
            (durasiDetik.length < 2 ? "0" + durasiDetik : durasiDetik);
          durasiPermainanElement.text(timer);
          if(data.data.statusGame == "In Progress") {
            startTimer();
          }
        }
      });
  }

  $(document).on("click", function (e) {
    if (!$(e.target).closest(".sidebar, #toggle-btn, #burger-btn").length) {
      closeSidebar();
    }
  });

  function closeSidebar() {
    $(".sidebar").removeClass("open");
    updateLogo();
    // $('.main-content').removeClass('shift');
  }

  const closeBtn = $("#closePopup");
  const modal = $(".gamedetails");

  closeBtn.click(function () {
    modal.removeClass("open");
  });

  $("#closePopup").click(function () {
    $(".gamedetails").hide();
    $(".modal-overlay").hide();
  });

  
  var gameData = {};

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

  function deleteCookie(name) {
    document.cookie = name + "=; Max-Age=-99999999;";
  }

  function fetchTotalPoin() {
    const allPoin = document.querySelector(".points");
    fetch(domain + "api/team/getTeamGeneral", {
      method: "GET",
      headers: { TOken: getCookie("Token") },
    })
      .then((response) => response.json())
      .then((data) => {
        document.getElementById("total").innerHTML = data.data.totalPoin;
      });
  }

  async function fetchBoothGames() {
    const listContainer = document.getElementsByClassName("games-list")[0];
    await fetch(domain + "api/boothgames/getWithResult", {
      method: "GET",
      headers: { Token: getCookie("Token") },
    })
      .then((response) => response.json())
      .then((data) => {
        if (!data.error) {
          data.data.forEach((boothData) => {
            gameData[boothData.idBoothGame] = boothData;

            if (boothData.tipeGame == "Duel") {
              var gameCard = `
                  <div class="game-card" onclick="gameCardClick('${boothData.idBoothGame}')">  
                  <img
                    src="${boothData.fotoBooth}"
                    alt="${boothData.namaBoothGame}"
                    class="game-image"
                  />
                  <div class="badgeduel">DUEL</div>
                  <p><b>${boothData.namaBoothGame}</b></p>
                  <table class="duel">
                    <tr>
                      <td>Match 1</td>
                      <td>:</td>
                      <td>${boothData.gameResult.match1}</td>
                    </tr>
                    <tr>
                      <td>Match 2</td>
                      <td>:</td>
                      <td>${boothData.gameResult.match2}</td>
                    </tr>
                  </table>
                </div>
                  `;
            } else {
              var gameCard = `
                  <div class="game-card" onclick="gameCardClick('${boothData.idBoothGame}')">  
                  <img
                    src="${boothData.fotoBooth}"
                    alt="${boothData.namaBoothGame}"
                    class="game-image"
                  />
                  <div class="badgesingle">SINGLE</div>
                  <p><b>${boothData.namaBoothGame}</b></p>
                  <table class="single">
                    <tr>
                      <td>Star</td>
                      <td>:</td>
                      <td>${boothData.gameResult.totalBintang}</td>
                    </tr>
                    <tr>
                      <td>Points</td>
                      <td>:</td>
                      <td>${boothData.gameResult.totalPoin}</td>
                    </tr>
                  </table>
                </div>
                  `;
            }

            var tempElement = document.createElement("div");
            tempElement.innerHTML = gameCard.trim();
            listContainer.appendChild(tempElement.firstChild);
          });
        } else {
          console.log("fetch Failed");
          console.log(data.message);

          deleteCookie("Token");
          window.location.href = "LogIn.html";
        }
      })
      .catch((error) => {
        console.error("Error occurred while fetching session:", error);

        deleteCookie("Token");
        window.location.href = "LogIn.html";
      });
  }

  function gameCardClick(boothId) {
    const modal = $(".gamedetails");
    modal.addClass("open");
    $(".gamedetails").show();
    $(".modal-overlay").show();
    $("#nama-game").html(gameData[boothId].namaBoothGame);
    var sopGame = gameData[boothId].sopGame.split("\n").join("<br>");
    $("#rules-game").html(sopGame);
    $("#foto-game").prop("src", gameData[boothId].fotoBooth);
    $("#foto-game").prop("alt", gameData[boothId].namaBoothGame);
  }

  window.gameCardClick = gameCardClick;

  fetchBoothGames();
  fetchTotalPoin();
  getTime();
});
