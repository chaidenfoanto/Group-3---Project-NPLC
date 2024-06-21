$(document).ready(function () {
  const domain = 'http://localhost:8080/';

  function displayBooths(booths) {
    const boothList = $('.booth-list');
    boothList.empty();

    booths.forEach((booth) => {
      let boothItem;
      if (booth.tipeGame === 'Duel') {
        boothItem = `
                    <div class="booth-item">
                        <div class="insides-item">
                            <div class="booth-header">
                                <div class="booth-left">
                                    <p class="namaBooth">Nama Booth : <span>${booth.namaBoothGame}</span></p>
                                </div>
                                <div class="booth-right">
                                    <p><i class='bx bx-map'></i> R. ${booth.lokasi.noRuangan} - Lantai ${booth.lokasi.lantai}</p>
                                    <p><i class='bx bx-time'></i> ${booth.durasiPermainan} min</p>
                                    <p class="duel" id="typeduel">${booth.tipeGame}</p>
                                </div>
                            </div>
                            <p class="penjaga1">Penjaga Booth 1 : <span>${booth.panitia1 || 'Tidak Ada'}</span></p>
                            <p class="penjaga2">Penjaga Booth 2 : <span>${booth.panitia2 || 'Tidak Ada'}</span></p>
                            <button id="showPopup" class="edit-btn">Edit</button>
                        </div>
                    </div>
                `;
      } else {
        boothItem = `
                    <div class="booth-item">
                        <div class="insides-item">
                            <div class="booth-header">
                                <div class="booth-left">
                                    <p class="namaBooth">Nama Booth : <span>${booth.namaBoothGame}</span></p>
                                </div>
                                <div class="booth-right">
                                    <p><i class='bx bx-map'></i> R. ${booth.lokasi.noRuangan} - Lantai ${booth.lokasi.lantai}</p>
                                    <p><i class='bx bx-time'></i> ${booth.durasiPermainan} min</p>
                                    <p class="single" id="type">${booth.tipeGame}</p>
                                </div>
                            </div>
                            <p class="penjaga1">Penjaga Booth 1 : <span>${booth.panitia1 || 'Tidak Ada'}</span></p>
                            <p class="penjaga2">Penjaga Booth 2 : <span>${booth.panitia2 || 'Tidak Ada'}</span></p>
                            <button id="showPopup" class="edit-btn">Edit</button>
                        </div>
                    </div>
                `;
      }
      var tempElement = document.createElement('div');
      tempElement.innerHTML = boothItem.trim();
      boothList.append(tempElement.firstChild);
    });
  }

  function populateFloorDropdown(floors) {
    const floorSelect = $('#Floor');

    floors.forEach((floor) => {
      const option = `<option value="${floor}">Lantai ${floor}</option>`;
      floorSelect.append(option);
    });
  }

  function getLantai() {
    fetch(domain + 'api/lokasi/getAllLantai', {
      method: 'GET',
      headers: { Token: getCookie('Token') },
    })
      .then((response) => response.json())
      .then((data) => {
        console.log('Response from getAllLantai:', data);
        if (!data.error && data.data.dataLantai) {
          populateFloorDropdown(data.data.dataLantai);
        } else {
          console.log(data.message || 'Unexpected data format');
        }
      })
      .catch((error) => {
        console.error('Error fetching lantai data:', error);
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
          console.log('success get booth list');
        } else {
          console.log(data.message);
        }
      })
      .catch((error) => {
        console.error('Error fetching booth data:', error);
      });
  }

  async function searchBooth() {
    const nama = document.getElementById('boothName').value;
    const lantai = document.getElementById('Floor').value;
    const tipegame = document.getElementById('gametype').value;

    const search = {
      nama: nama,
      lantai: lantai,
      tipegame: tipegame,
    };

    const response = fetch(domain + 'api/boothgames/searchBoothgame', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        Token: getCookie('Token'),
      },
      body: JSON.stringify(search),
    })
      .then((response) => response.json())
      .then((data) => {
        if (!data.error) {
          console.log('search succes');
          displayBooths(data.data);
        } else {
          console.log('failed search:', data.message);
          const boothList = $('.booth-list');
          boothList.empty();
          const text = document.createElement('h1');
          text.innerHTML = data.message;
          boothList.append(text);
        }
      })
      .catch((error) => {
        console.error('Error fetching booth data:', error);
      });
  }

  $('.btn-secondary').on('click', function () {
    searchBooth();
  });

  function getCookie(name) {
    let cookieArr = document.cookie.split(';');
    for (let i = 0; i < cookieArr.length; i++) {
      let cookiePair = cookieArr[i].split('=');
      if (name === cookiePair[0].trim()) {
        return decodeURIComponent(cookiePair[1]);
      }
    }
    return null;
  }

  getBoothList();
  getLantai();
});
