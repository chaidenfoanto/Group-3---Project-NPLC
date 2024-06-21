$(document).ready(function () {
  const domain = 'http://localhost:8080/';
  let teamData = {};
  let selectedTeamId = null;

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

  function gethistory() {
    fetch(domain + 'api/', {
      method: 'GET',
      headers: { Token: getCookie('Token') },
    })
      .then((response) => response, json())
      .then((data) => {
        if (!data.error) {
          const historyItem = `
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
          `;

          $('#history').append(historyItem);
        }
      });
  }

  function fetchTeamNames() {
    fetch(domain + 'api/team/getTeamPerGame', {
      method: 'GET',
      headers: { Token: getCookie('Token') },
    })
      .then((response) => response.json())
      .then((data) => {
        const teamSelect = document.getElementById('teamname');

        teamSelect.innerHTML = '';
        data.data.forEach((team) => {
          teamData[team.namaTeam] = team.idTeam;
          const option = document.createElement('option');
          option.value = team.namaTeam;
          option.textContent = team.namaTeam;
          teamSelect.appendChild(option);
        });

        const placeholder = document.createElement('option');
        placeholder.value = '';
        teamSelect.prepend(placeholder);
        teamSelect.value = '';
      })
      .catch((error) => console.error('Error loading team names:', error));
  }

  function fetchAvailableCards(teamId) {
    fetch(domain + 'api/listkartu/getByTeam/' + teamId, {
      method: 'GET',
      headers: { Token: getCookie('Token') },
    })
      .then((response) => response.json())
      .then((data) => {
        const cardSelect = document.getElementById('cardskill');

        cardSelect.innerHTML = '';

        if (data.data.availableCards && data.data.availableCards.length > 0) {
          data.data.availableCards.forEach((card) => {
            const option = document.createElement('option');
            option.value = card.cardSkill.namaKartu;
            option.textContent = card.cardSkill.namaKartu;
            cardSelect.appendChild(option);
          });
        }

        const placeholder = document.createElement('option');
        placeholder.value = '';
        cardSelect.prepend(placeholder);
        cardSelect.value = '';
      })
      .catch((error) => console.error('Error loading available cards:', error));
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
});
