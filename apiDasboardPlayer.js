const domain = 'http://localhost:8080/';
var gameData = {};
$(document).ready(function () {

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

  function fetchTotalPoin() {
    const allPoin = document.querySelector('.points');
    fetch(domain + 'api/team/getTeamGeneral', {
      method: 'GET',
      headers: { TOken: getCookie('Token') },
    })
      .then((response) => response.json())
      .then((data) => {
        document.getElementById('total').innerHTML = data.data.totalPoin;
      });
  }

  async function fetchBoothGames() {
    const listContainer = document.getElementsByClassName('games-list')[0];
    await fetch(domain + 'api/boothgames/getWithResult', {
      method: 'GET',
      headers: { Token: getCookie('Token') },
    })
      .then((response) => response.json())
      .then((data) => {
        if (!data.error) {
          data.data.forEach((boothData) => {
            gameData[boothData.idBoothGame] = boothData;
            if (boothData.tipeGame == 'Duel') {
              var gameCard = `
              <div class="game-card" onclick="gameCardClick(${boothData.idBoothGame})">  
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
              <div class="game-card" onclick="gameCardClick(${boothData.idBoothGame})">  
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
            var tempElement = document.createElement('div');
            tempElement.innerHTML = gameCard.trim();
            listContainer.appendChild(tempElement.firstChild);
          });
        } else {
          console.log('fetch Failed');
          console.log(data.message);
          // deleteCookie("Token");
          // window.location.href = "LogIn.html";
        }
      })
      .catch((error) => {
        console.error('Error occurred while fetching session:', error);
        // deleteCookie("Token");
        // window.location.href = "LogIn.html";
      });
  }

  fetchBoothGames();
  fetchTotalPoin();

  // setInterval(function() {
  //     fetchSession();
  // }, 5000);
});

function gameCardClick(boothId) {
  const modal = $('.gamedetails');
  modal.addClass('open');
  $('.gamedetails').show();
  $('.modal-overlay').show();
  $('#nama-game').html(gameData[boothId].namaBoothGame);
  $('#rules-game').html(gameData[boothId].sopGame);
  $('#foto-game').prop('src', gameData[boothId].fotoBooth);
  $('#foto-game').prop('alt', gameData[boothId].namaBoothGame);
}