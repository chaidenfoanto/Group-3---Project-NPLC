$(document).ready(function () {
  const domain = 'http://localhost:8080/'; // Basis domain untuk permintaan API

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

  // Fungsi untuk mengambil dan menampilkan jumlah tiket roll
  function fetchRollTicket() {
    fetch(domain + 'api/team/getTeamGeneral', {
      method: 'GET',
      headers: { Token: getCookie('Token') }, // Menyertakan token dalam header dari cookie
    })
      .then((response) => response.json())
      .then((data) => {
        // Menampilkan jumlah chanceRoll di elemen dengan id 'chanceroll'
        document.getElementById('chanceroll').innerHTML = data.data.chanceRoll;
        // Mengaktifkan atau menonaktifkan tombol roll berdasarkan chanceRoll
        if (data.data.chanceRoll > 0) {
          $('#rollButton').prop('disabled', false); // Mengaktifkan tombol roll jika chanceRoll > 0
        } else {
          $('#rollButton').prop('disabled', true); // Menonaktifkan tombol roll jika chanceRoll <= 0
        }
      });
  }

  // Fungsi untuk mengambil dan menampilkan data kartu dek
  function fetchDeckCard() {
    const cardsContainer = document.querySelector('.cards-list'); // Memilih wadah daftar kartu
    fetch(domain + 'api/cardskills/getWithUser', {
      method: 'GET',
      headers: { Token: getCookie('Token') }, // Menyertakan token dalam header dari cookie
    })
      .then((response) => response.json())
      .then((data) => {
        if (!data.error) {
          // Jika tidak ada error, iterasi melalui data kartu dan buat elemen HTML untuk setiap kartu
          data.data.forEach((cardData) => {
            // Memisahkan aturan kartu ke dalam dua bagian: cardRulesLess (untuk pratinjau) dan cardRulesMore (untuk detail)
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

            // Membuat elemen HTML untuk kartu dan menyusunnya dengan data dari API
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
            // Membuat elemen div sementara untuk mengurai string HTML dan menambahkannya ke dalam wadah daftar kartu
            var tempElement = document.createElement('div');
            tempElement.innerHTML = cardList.trim();
            cardsContainer.appendChild(tempElement.firstChild);
          });
        } else {
          console.log('Error fetching cards:', data.message); // Log jika ada error saat mengambil data kartu
        }
      })
      .catch((error) => {
        console.error('Error occurred while fetching cards:', error); // Menangani dan log error jika permintaan gagal
      });
  }

  // Memanggil fungsi untuk mengambil data kartu dek dan tiket roll saat dokumen siap
  fetchDeckCard();
  fetchRollTicket();

  // Hapus komentar pada baris berikut jika ingin mengambil sesi secara berkala
  // setInterval(function() {
  //     fetchSession();
  // }, 5000);
});
