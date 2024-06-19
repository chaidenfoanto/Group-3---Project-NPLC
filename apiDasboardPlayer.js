$(document).ready(function () {
  const domain = 'http://localhost:8080/'; // Basis domain untuk permintaan API
  var gameData = {}; // Objek untuk menyimpan data permainan yang diambil dari API

  // Fungsi untuk mendapatkan cookie tertentu berdasarkan nama
  function getCookie(name) {
    let cookieArr = document.cookie.split(';'); // Membagi cookie string menjadi array
    for (let i = 0; i < cookieArr.length; i++) {
      let cookiePair = cookieArr[i].split('='); // Membagi setiap pasangan cookie ke nama dan nilai
      if (name == cookiePair[0].trim()) {
        return decodeURIComponent(cookiePair[1]); // Mengembalikan nilai cookie jika nama cocok
      }
    }
    return null; // Mengembalikan null jika cookie tidak ditemukan
  }

  function deleteCookie(name) {
    document.cookie = name + '=; Max-Age=-99999999;';
  }

  // Fungsi untuk mengambil dan menampilkan total poin
  function fetchTotalPoin() {
    const allPoin = document.querySelector('.points'); // Memilih elemen poin
    fetch(domain + 'api/team/getTeamGeneral', {
      method: 'GET',
      headers: { TOken: getCookie('Token') }, // Mengatur header Token dari cookie
    })
      .then((response) => response.json())
      .then((data) => {
        document.getElementById('total').innerHTML = data.data.totalPoin; // Memperbarui total poin di DOM
      });
  }

  // Fungsi untuk mengambil dan menampilkan permainan booth
  async function fetchBoothGames() {
    const listContainer = document.getElementsByClassName('games-list')[0]; // Memilih wadah daftar permainan
    await fetch(domain + 'api/boothgames/getWithResult', {
      method: 'GET',
      headers: { Token: getCookie('Token') }, // Mengatur header Token dari cookie
    })
      .then((response) => response.json())
      .then((data) => {
        if (!data.error) {
          data.data.forEach((boothData) => {
            gameData[boothData.idBoothGame] = boothData; // Menyimpan data booth ke dalam objek gameData

            // Membuat kartu permainan tergantung pada jenis permainan (Duel atau Single)
            if (boothData.tipeGame == 'Duel') {
              var gameCard = `
              <div class="game-card" onclick="gameCardClick(${boothData.idBoothGame})">  
              <img
                src="${boothData.fotoBooth}"
                alt="${boothData.namaBoothGame}"
                class="game-image"
              />
              <div class="badgeduel">DUEL</div>
              <p><b>${boothData.namaBoothGame}</b></p>
              <table class="duel">
                <tr>
                  <td>Match 1</td>
                  <td>:</td>
                  <td>${boothData.gameResult.match1}</td>
                </tr>
                <tr>
                  <td>Match 2</td>
                  <td>:</td>
                  <td>${boothData.gameResult.match2}</td>
                </tr>
              </table>
            </div>
              `;
            } else {
              var gameCard = `
              <div class="game-card" onclick="gameCardClick(${boothData.idBoothGame})">  
              <img
                src="${boothData.fotoBooth}"
                alt="${boothData.namaBoothGame}"
                class="game-image"
              />
              <div class="badgesingle">SINGLE</div>
              <p><b>${boothData.namaBoothGame}</b></p>
              <table class="single">
                <tr>
                  <td>Star</td>
                  <td>:</td>
                  <td>${boothData.gameResult.totalBintang}</td>
                </tr>
                <tr>
                  <td>Points</td>
                  <td>:</td>
                  <td>${boothData.gameResult.totalPoin}</td>
                </tr>
              </table>
            </div>
              `;
            }

            // Membuat elemen div sementara untuk mengurai string HTML dan menambahkannya ke dalam wadah daftar
            var tempElement = document.createElement('div');
            tempElement.innerHTML = gameCard.trim();
            listContainer.appendChild(tempElement.firstChild);
          });
        } else {
          console.log('fetch Failed');
          console.log(data.message);
          // Hapus komentar pada baris berikut jika ingin mengatasi kesalahan dengan mengarahkan pengguna
          deleteCookie('Token');
          window.location.href = 'LogIn.html';
        }
      })
      .catch((error) => {
        console.error('Error occurred while fetching session:', error);
        // Hapus komentar pada baris berikut jika ingin mengatasi kesalahan dengan mengarahkan pengguna
        deleteCookie('Token');
        window.location.href = 'LogIn.html';
      });
  }

  // Fungsi untuk menangani klik pada kartu permainan dan menampilkan detail permainan dalam modal
  function gameCardClick(boothId) {
    const modal = $('.gamedetails'); // Memilih elemen modal
    modal.addClass('open'); // Menambahkan kelas 'open' ke modal
    $('.gamedetails').show(); // Menampilkan modal
    $('.modal-overlay').show(); // Menampilkan overlay modal
    $('#nama-game').html(gameData[boothId].namaBoothGame); // Memperbarui nama permainan di modal
    var sopGame = gameData[boothId].sopGame.split('\n').join('<br>'); // Memformat teks SOP permainan dengan jeda baris
    $('#rules-game').html(sopGame); // Memperbarui aturan dalam modal
    $('#foto-game').prop('src', gameData[boothId].fotoBooth); // Memperbarui sumber gambar permainan dalam modal
    $('#foto-game').prop('alt', gameData[boothId].namaBoothGame); // Memperbarui teks alt gambar permainan dalam modal
  }
  // Tentukan gameCardClick sebagai fungsi global agar bisa diakses dari HTML
  window.gameCardClick = gameCardClick;

  // Ambil data permainan booth dan total poin saat dokumen siap
  fetchBoothGames();
  fetchTotalPoin();

  // Hapus komentar pada baris berikut jika ingin mengambil sesi secara berkala
  // setInterval(function() {
  //     fetchSession();
  // }, 5000);
});
