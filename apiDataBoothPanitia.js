$(document).ready(function () {
  const domain = 'http://localhost:8080/';

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
  function addDataBooth() {
    // Ambil nilai dari formulir
    var boothName = document.getElementById('boothName').value;
    var guard1Name = document.getElementById('guard1Name').value;
    var guard2Name = document.getElementById('guard2Name').value;
    var howtoplay = document.getElementById('howtoplay').value;
    var noRuangan = document.getElementById('noRuangan').value;
    var tipeGame = document.getElementById('tipeGame').value;

    // Objek data yang akan dikirimkan ke backend
    var data = {
      boothName: boothName,
      guard1Name: guard1Name,
      guard2Name: guard2Name,
      howtoplay: howtoplay,
      noRuangan: noRuangan,
      tipeGame: tipeGame,
    };

    // Konfigurasi fetch API untuk mengirim data
    fetch(domain + '', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        Token: getCookie('Token'),
      },
      body: JSON.stringify(data),
    })
      .then(function (response) {
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        return response.json(); // Ubah response menjadi JSON jika perlu
      })
      .then(function (data) {
        // Handle response dari server jika diperlukan
        console.log('Data berhasil ditambahkan ke database', data);
      })
      .catch(function (error) {
        // Handle error jika terjadi kesalahan saat pengiriman
        console.error('Terjadi kesalahan:', error);
      });
  }
});
