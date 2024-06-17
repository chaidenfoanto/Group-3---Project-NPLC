$(document).ready(function() {
const domain = "http://localhost:8080/";

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

  function fetchLeaderBoard() {
    fetch (domain + '', {
      method: 'GET',
      headers: { Token: getCookie('Token') },
      
    })
  }
  
})