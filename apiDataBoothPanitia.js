$(document).ready(function () {
  const domain = 'http://localhost:8080/'; 
  var boothData = {}; 
  const popup = document.getElementById("popup");
const popupOverlay = document.getElementById('popup-overlay');

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
  window.openPopup = function (id) {
    const popup = document.getElementById(id);
    popup.classList.add("open-popup");
    popupOverlay.classList.add("active");
}

window.closePopup = function (id) {
    const popup = document.getElementById(id);
    popup.classList.remove("open-popup");
    popupOverlay.classList.remove("active");
}


  function fetchDataBooth() {
    fetch(domain + 'api/boothgames/getSelfBooth', {
      method: 'GET',
      headers: { Token: getCookie('Token') },
    })
    .then((response) => response.json())
    .then((data) => {
      if (!data.error) {
        const booth = data.data;

        $('#cardImage').attr('src', booth.fotoBooth); // Update src attribute of img element
        $('#boothName').val(booth.namaBoothGame);
        $('#howtoplay').val(booth.sopGame);

        $('#guard1Name').empty();
        $('#guard1Name').append(new Option(booth.panitia1, booth.panitia1));

        $('#guard2Name').empty();
        if (booth.panitia2 != null) {
          $('#guard2Name').append(new Option(booth.panitia2, booth.panitia2));
        } else {
          $('#guard2Name').append(new Option("Tidak Ada", "Tidak Ada"));
        }

        $('#noRuangan').empty();
        $('#noRuangan').append(new Option(booth.lokasi.noRuangan, booth.lokasi.noRuangan));

        $('#tipeGame').empty();
        $('#tipeGame').append(new Option(booth.tipeGame, booth.tipeGame));
        $('#tipeGame').val(booth.tipeGame);
      } else {
        console.error('Error:', data.message);
      }
    })
    .catch((error) => console.error('Error fetching booth data:', error));
  }

  
  function putSopGames(id, sopGame) {
    fetch(domain + 'api/boothgames/updateSOP', {
      method: 'PUT',
      headers: { 
        'Content-Type': 'application/json',
        'Token': getCookie('Token')
      },
      body: JSON.stringify({ sopGames: sopGame })
    })
    .then((response) => response.json())
    .then((data) => {
      if (!data.error) {
        openPopup('popup');
      } else {
        console.error('Error:', data.message);
      }
      popupOverlay.addEventListener('click', function() {
      closePopup('popup');
      });
    })
    .catch((error) => console.error('Error updating SOP:', error));
  }

  $('#booth-form').on('submit', function (e) {
    e.preventDefault();
    const id = boothData.idBooth;
    const sopGame = $('#howtoplay').val();
    putSopGames(id, sopGame);
  });
  fetchDataBooth();
});
