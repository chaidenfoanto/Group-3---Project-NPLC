$(document).ready(function () {
  const domain = 'http://localhost:8080/';

  function displayBooths(booths) {
    const boothList = $('.booth-list');
    boothList.empty(); // Clear existing content

    booths.forEach((booth) => {
      var boothItem = `
    <div class="booth-item">
      <div class="insides-item">
        <div class="booth-header">
          <div class="booth-left">
            <p class="namaBooth" >Nama Booth : <span>${booth.namaBoothGame}</span></p>
          </div>
          <div class="booth-right">
            <p><i class='bx bx-map'></i> R. ${booth.lokasi.noRuangan} - Lantai ${booth.lokasi.lantai}</p>
            <p><i class='bx bx-time'></i> ${booth.durasiPermainan} min</p>
            <p class="type">${booth.tipeGame}</p>
          </div>
        </div>
        <p class="penjaga1" >Penjaga Booth 1 : <span>${booth.panitia1 || 'Tidak Ada'}</span></p>
        <p class="penjaga2" >Penjaga Booth 2 : <span>${booth.panitia2 || 'Tidak Ada'}</span></p>
        <button class="edit-btn">Edit</button>
      </div>
      </div>
    `;
      var tempElement = document.createElement('div');
      tempElement.innerHTML = boothItem.trim();
      boothList.append(tempElement.firstChild);
    });
  }

  function getBoothList() {
    fetch(domain + 'api/boothgames/getGeneral', {
      method: 'GET',
      headers: { Token: getCookie('Token') },
    })
      .then((response) => response.json())
      .then((data) => {
        if (!data.error) {
          displayBooths(data.data);
        } else {
          console.log(data.message);
        }
      })
      .catch((error) => {
        console.error('Error fetching data:', error);
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

  getBoothList();
});
