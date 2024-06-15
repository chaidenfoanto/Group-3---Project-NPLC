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

    function deleteCookie(name) {
        document.cookie = name + '=; Max-Age=-99999999;';
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
                    var cardlist = `
                    <div class="card" id="doublePoint">
                    <img src="${cardData.foto}" alt="${cardData.nama}" class="card-image">
                    <p><b>${cardData.nama}</b></p>
                    <p style="text-align: justify;">
                        Effect: Hanya dapat digunakan di game single. Jika tim berhasil mendapatkan bintang
                        <span class="dots">...</span>
                        <span class="more">(bintang 1-3), maka point x2. Jika tim gagal (bintang 0), maka point -30.</span>
                        <button onclick="readMore(this)" class="readMoreBtn">Read more</button>
                    </p>
                    <table>
                        <tr>
                            <td>Total Owned</td>
                            <td>:</td>
                            <td>0</td>
                        </tr>
                        <tr>
                            <td>Total Used</td>
                            <td>:</td>
                            <td>0</td>
                        </tr>
                    </table>
                </div>
                    `
                    cardsContainer.appendChild(cardList);
                });
            } else {
                console.log('Error fetching cards:', data.message);
            }
        })
        .catch(error => {
            console.error('Error occurred while fetching cards:', error);
        });
    }
});
