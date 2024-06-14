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
        console.log("Authorization Success");
        setCookie("Token", getCookie("Token"), 365);
        fetchBoothGames();
      } else {
        console.log('Authorization Failed');
        deleteCookie("Token");
        window.location.href = "Login.html";
      }
    })
    .catch(error => {
      console.error('Error occurred while fetching session:', error);
      deleteCookie("Token");
      window.location.href = "Login.html";
    });
  }

  function getCookie(name) {
    let cookieArr = document.cookie.split(";");
    for (let i = 0; i < cookieArr.length; i++) {
      let cookiePair = cookieArr[i].split("=");
      if (name === cookiePair[0].trim()) {
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

  function deleteCookie(name) {
    document.cookie = name + '=; Max-Age=-99999999;';
  }

  function fetchBoothGames() {
    const listContainer = document.getElementsByClassName("games-list")[0];
    listContainer.innerHTML = ''; // Clear any existing content
    fetch(domain + 'api/boothgames', {
      method: 'GET',
      headers: { 'Token': getCookie('Token') }
    })
    .then(response => response.json())
    .then(data => {
      if (!data.error) {
        data.data.forEach((boothData) => {
          let gameCard;
          if (boothData.tipeGame === "Duel") {
            gameCard = `
              <div class="game-card">  
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
                    <td>WIN</td>
                  </tr>
                  <tr>
                    <td>Match 2</td>
                    <td>:</td>
                    <td>LOSE</td>
                  </tr>
                </table>
              </div>
            `;
          } else {
            gameCard = `
              <div class="game-card">  
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
                    <td>0</td>
                  </tr>
                  <tr>
                    <td>Points</td>
                    <td>:</td>
                    <td>0</td>
                  </tr>
                </table>
              </div>
            `;
          }
          listContainer.innerHTML += gameCard;
        });
      } else {
        console.log('Authorization Failed');
        deleteCookie("Token");
        window.location.href = "Login.html";
      }
    })
    .catch(error => {
      console.error('Error occurred while fetching booth games:', error);
      deleteCookie("Token");
      window.location.href = "Login.html";
    });
  }

  fetchSession();
});
