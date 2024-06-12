document.addEventListener("DOMContentLoaded", function () {
    const domain = "http://localhost:8080/";
  
    function fetchSession() {
      fetch(domain + 'api/login/getSession', {
        method: 'GET',
        headers: { 'Token': getCookie('Token') }
      })
        .then(response => response.json())
        .then(data => {
          if (data.service === "Auth Token" && data.message === "Authorization Success") {
            console.log("authorized success");
            setCookie("Token", getCookie("Token"), 365);
            fetchBoothGames();
          } else {
            console.log('Authorization Failed');
            // deleteCookie("Token");
            // window.location.href = "Login.html";
          }
        })
        .catch(error => {
          console.error('Error occurred while fetching session:', error);
          // deleteCookie("Token");
          // window.location.href = "Login.html";
        });
    }
  
    function fetchBoothGames() {
      fetch(domain + 'api/boothgames/{id}', {
        method: 'GET',
        headers: { 'Token': getCookie('Token') }
      })
        .then(response => response.json())
        .then(data => {
          populateGamesList(data);
          updateTotalPoints(data.totalPoints);
        })
        .catch(error => console.error('Error fetching booth games:', error));
    }
  
    function populateGamesList(games) {
      const gamesList = document.getElementById('gamesList');
      gamesList.innerHTML = '';
  
      games.forEach(game => {
        const gameCard = document.createElement('div');
        gameCard.className = 'game-card';
        gameCard.innerHTML = `
          <img src="${game.image}" alt="${game.name}" class="game-image">
          <div class="${game.type === 'single' ? 'badgesingle' : 'badgeduel'}">${game.type.toUpperCase()}</div>
          <p><b>${game.name}</b></p>
          <table class="${game.type}">
            ${game.type === 'single' ? `
              <tr>
                <td>Star</td>
                <td>:</td>
                <td>${game.stars}</td>
              </tr>
              <tr>
                <td>Points</td>
                <td>:</td>
                <td>${game.points}</td>
              </tr>` : `
              <tr>
                <td>Match 1</td>
                <td>:</td>
                <td>${game.match1}</td>
              </tr>
              <tr>
                <td>Match 2</td>
                <td>:</td>
                <td>${game.match2}</td>
              </tr>`}
          </table>
        `;
        gamesList.appendChild(gameCard);
      });
    }
  
    function updateTotalPoints(totalPoints) {
      document.getElementById('totalPoints').innerText = totalPoints;
    }
  
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
  
    function setCookie(name, value, daysToLive) {
      let cookie = name + "=" + encodeURIComponent(value);
      if (typeof daysToLive === "number") {
        cookie += "; max-age=" + (daysToLive * 24 * 60 * 60) + "; path=/";
        document.cookie = cookie;
      }
    }
  
    fetchSession();
  });
  