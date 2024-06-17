$(document).ready(function () {
  const domain = 'http://localhost:8080/';
  var teamData = {};

  function fetchSession() {
    fetch(domain + 'api/login/getSession', {
      method: 'GET',
      headers: { Token: getCookie('Token') },
    })
      .then((response) => response.json())
      .then((data) => {
        if (!data.error) {
          console.log('authorized success');
          setCookie('Token', getCookie('Token'), 365);
          fetchTeam();
        } else {
          console.log('Authorization Failed');
          deleteCookie('Token');
          window.location.href = 'LoginPanitia.html';
        }
      })
      .catch((error) => {
        console.error('Error occurred while fetching session:', error);
        deleteCookie('Token');
        window.location.href = 'LoginPanitia.html';
      });
  }

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

  function setCookie(name, value, daysToLive) {
    let cookie = name + '=' + encodeURIComponent(value);
    if (typeof daysToLive === 'number') {
      cookie += '; max-age=' + daysToLive * 24 * 60 * 60 + '; path=/';
      document.cookie = cookie;
    }
  }

  function deleteCookie(name) {
    document.cookie = name + '=; Max-Age=-99999999;';
  }

  fetchSession();

  function fetchTeam() {
    fetch(domain + "api/team", {
        method: 'GET',
        headers: { 'Token': getCookie('Token') }
    }) 
    .then(response => response.json())
    .then(data => {
        const teamDropdown = document.getElementById('teamname');
        
        data.data.forEach(team => {
            const option = document.createElement('option');
            teamDropdown = `
            <option value=""></option>
                  <option value="Anak Pak Dengklek">${team.team.nama}</option>
            `
        });
    })
    .catch(error => console.error('Error fetching data:', error));
  }
});
