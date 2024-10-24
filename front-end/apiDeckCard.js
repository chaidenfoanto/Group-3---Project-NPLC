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

  function fetchRollTicket() {
    fetch(domain + 'api/team/getTeamGeneral', {
      method: 'GET',
      headers: { Token: getCookie('Token') },
    })
      .then((response) => response.json())
      .then((data) => {
        document.getElementById('chanceroll').innerHTML = data.data.chanceRoll;
        if (data.data.chanceRoll > 0) {
          $('#rollButton').prop('disabled', false);
        } else {
          $('#rollButton').prop('disabled', true);
        }
      });
  }

  // Function to fetch deck cards data
  function fetchDeckCard() {
    const cardsContainer = document.querySelector('.cards-list');
    fetch(domain + 'api/cardskills/getWithUser', {
      method: 'GET',
      headers: { Token: getCookie('Token') },
    })
      .then((response) => response.json())
      .then((data) => {
        if (!data.error) {
          data.data.forEach((cardData) => {
            var cardRules = cardData.rules.split(' ');
            var cardRulesLess = '';
            var cardRulesMore = '';
            for (var i = 0; i < Math.min(cardRules.length, 9); i++) {
              cardRulesLess += cardRules[i] + ' ';
            }
            for (var j = i; j < cardRules.length; j++) {
              cardRulesMore += cardRules[j];
              if (j < cardRules.length - 1) cardRulesMore += ' ';
            }
            var cardList = `
                    <div class="card" id="${cardData.idCard}">
                    <img src="${cardData.gambarKartu}" alt="${cardData.namaKartu}" class="card-image">
                    <p><b>${cardData.namaKartu}</b></p>
                    <p style="text-align: justify;">
                        Effect: ${cardRulesLess}
                        <span class="dots">...</span>
                        <span class="more">${cardRulesMore}</span>
                        <button onclick="readMore(this)" class="readMoreBtn">Read more</button>
                    </p>
                    <table>
                        <tr>
                            <td>Total Owned</td>
                            <td>:</td>
                            <td>${cardData.totalOwned}</td>
                        </tr>
                        <tr>
                            <td>Total Used</td>
                            <td>:</td>
                            <td>${cardData.totalUsed}</td>
                        </tr>
                    </table>
                </div>
                    `;
            var tempElement = document.createElement('div');
            tempElement.innerHTML = cardList.trim();
            cardsContainer.appendChild(tempElement.firstChild);
          });
        } else {
          console.log('Error fetching cards:', data.message);
        }
      })
      .catch((error) => {
        console.error('Error occurred while fetching cards:', error);
      });
  }

  fetchDeckCard();
  fetchRollTicket();

  // setInterval(function() {
  //     fetchSession();
  // }, 5000);
});
