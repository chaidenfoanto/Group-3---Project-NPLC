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
    fetch(domain + 'api/team/getLeaderboard', {
      method: 'GET',
      headers: { Token: getCookie('Token') },
    })
    .then((response) => response.json())
    .then((data) => {
      if(!data.error) {
        const leaderboardBody = $('#leaderboard-body');
        leaderboardBody.empty();

        // Dapatkan data tim dan jumlahBoothgame dari respons API
        const teams = data.data.teams;
        const jumlahBoothgame = data.data.jumlahBoothgame.totalGame;

        // Sortir berdasarkan total poin, descending
        teams.sort((a, b) => b.totalPoin - a.totalPoin);

        // Iterasi melalui setiap tim dan tambahkan baris ke tabel
        teams.forEach((team, index) => {
          const row = `<tr>
                         <td>${index + 1}</td>
                         <td>${team.team.namaTeam}</td>
                         <td>${team.gameStats.totalGamesPlayed}/${jumlahBoothgame}</td>
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