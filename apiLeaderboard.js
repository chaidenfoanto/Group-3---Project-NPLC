$(document).ready(function () {
  const domain = 'http://localhost:8080/';

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

  function fetchLeaderBoard() {
    fetch(domain + 'api/team', {
      method: 'GET',
      headers: { Token: getCookie('Token') },
    })
    .then((response) => response.json())
    .then((data) => {
      if(!data.error) {
        const leaderboardBody = $('#leaderboard-body');
        leaderboardBody.empty();
        data.data.sort((a, b) => b.totalPoin - a.totalPoin); // Sortir berdasarkan total poin, descending
        data.data.forEach((team, index) => {
          const row = `<tr>
                         <td>${index + 1}</td>
                         <td>${team.namaTeam}</td>
                         <td>${index+1}/18</td>
                         <td>${team.totalPoin}</td>
                       </tr>`;
          leaderboardBody.append(row);
        });
      } else {
        console.error("Error fetching leaderboard data: ", data.message);
      }
    })
    .catch((error) => {
      console.error("Error: ", error);
    });
  }

  fetchLeaderBoard();
});
