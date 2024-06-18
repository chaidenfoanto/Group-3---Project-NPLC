$(document).ready(function () {
  const domain = 'http://localhost:8080/'; // Make sure this is your correct domain
  let teamData = {};
  let selectedTeamId = null; // Global variable to store the selected team ID

  // Function to get the cookie value by name
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

  // Function to fetch team names
  function fetchTeamNames() {
    fetch(domain + 'api/team/getTeamPerGame', {
      method: 'GET',
      headers: { Token: getCookie('Token') },
    })
      .then((response) => response.json())
      .then((data) => {
        const teamSelect = document.getElementById('teamname');

        // Remove all existing options
        teamSelect.innerHTML = '';

        // Add options for each team from the received data
        data.data.forEach((team) => {
          teamData[team.namaTeam] = team.idTeam; // Store team ID in teamData object
          const option = document.createElement('option');
          option.value = team.namaTeam; // Set the option value and text with team name
          option.textContent = team.namaTeam;
          teamSelect.appendChild(option);
        });

        // Set placeholder option
        const placeholder = document.createElement('option');
        placeholder.value = '';
        teamSelect.prepend(placeholder); // Add placeholder at the top
        teamSelect.value = ''; // Set default value to empty
      })
      .catch((error) => console.error('Error loading team names:', error));
  }

  // Function to fetch available cards for a team by team ID
  function fetchAvailableCards(teamId, teamName) {
    fetch(domain + 'api/listkartu/getByTeam/' + teamId, {
      method: 'GET',
      headers: { Token: getCookie('Token') },
    })
      .then((response) => response.json())
      .then((data) => {
        const cardSelect = document.getElementById('cardskill');

        // Remove all existing options
        cardSelect.innerHTML = '';

        // Check if the team has any available cards
        if (data.data.availableCards && data.data.availableCards.length > 0) {
          // Add options for each card from the received data
          data.data.availableCards.forEach((card) => {
            const option = document.createElement('option');
            option.value = card.cardSkill.namaKartu; // Set the option value and text with card skill name
            option.textContent = card.cardSkill.namaKartu;
            cardSelect.appendChild(option);
          });
        }

        // Always add a placeholder option at the top
        const placeholder = document.createElement('option');
        placeholder.value = '';
        cardSelect.prepend(placeholder); // Add placeholder at the top
        cardSelect.value = ''; // Set default value to empty
      })
      .catch((error) => console.error('Error loading available cards:', error));
  }

  // Event listener for team selection change
  document.getElementById('teamname').addEventListener('change', function () {
    const selectedTeam = this.value;
    if (selectedTeam && teamData[selectedTeam]) {
      selectedTeamId = teamData[selectedTeam]; // Store selected team ID in the global variable
      fetchAvailableCards(selectedTeamId); // Fetch available cards for the selected team
    } else {
      // Clear the card skill select if no team is selected
      const cardSelect = document.getElementById('cardskill');
      cardSelect.innerHTML = '<option value=""></option>';
    }
  });

  // Initialize the page by fetching team names
  fetchTeamNames();
});
