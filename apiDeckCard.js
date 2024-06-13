$(document).ready(function() {
    const domain = "http://localhost:8080/"

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
              fetchDeckCard();
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
      
    // Function to fetch deck cards data
    function fetchDeckCard() {
        const cardsContainer = document.querySelector('.cards-list');
        fetch(domain + 'api/listkartu', {
            method: 'GET',
            headers: { 'Token': getCookie('Token') }
        })
        .then(response => response.json())
        .then(data => {
            if (!data.error) {
                data.body.forEach(cardData => {
                    const cardElement = createCardElement(cardData);
                    cardsContainer.appendChild(cardElement);
                });
            } else {
                console.log('Error fetching cards:', data.message);
            }
        })
        .catch(error => {
            console.error('Error occurred while fetching cards:', error);
        });
    }

    // Function to create card HTML element
    function createCardElement(cardData) {
        const card = document.createElement('div');
        card.classList.add('card');
        card.id = cardData.id; // Assuming cardData has an id field

        // Set image
        const img = document.createElement('img');
        img.src = cardData.image_url; // Replace with actual image URL field from API
        img.alt = cardData.name; // Replace with actual alt text
        img.classList.add('card-image');
        card.appendChild(img);

        // Set card description
        const description = document.createElement('p');
        description.innerHTML = `<b>${cardData.name}</b><br>${cardData.description}`;
        description.classList.add('card-description');
        card.appendChild(description);

        // Set total owned
        const totalOwned = document.createElement('td');
        totalOwned.textContent = cardData.total_owned;
        totalOwned.classList.add('total-owned');
        card.querySelector('.total-owned').appendChild(totalOwned);

        // Set total used
        const totalUsed = document.createElement('td');
        totalUsed.textContent = cardData.total_used;
        totalUsed.classList.add('total-used');
        card.querySelector('.total-used').appendChild(totalUsed);

        return card;
    }

    fetchDeckCard(); // Fetch deck cards when document is ready
});
