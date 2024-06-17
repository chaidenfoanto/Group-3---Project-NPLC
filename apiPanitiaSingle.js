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
              fetchRollTicket();
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
});