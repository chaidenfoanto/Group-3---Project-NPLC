$(document).ready(function() {
const domain = "http://localhost:8080/";

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
          fetchLeaderboard();
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


    // Fungsi untuk mengambil dan merender data leaderboard
    function fetchLeaderboard() {
        $.ajax({
            url: 'https://example.com/api/leaderboard', // Ganti dengan URL endpoint yang sesuai
            method: 'GET',
            dataType: 'json',
            success: function(data) {
                Leaderboard(data); // Panggil fungsi renderLeaderboard dengan data yang diterima
            },
            error: function(xhr, status, error) {
                console.error('Failed to fetch leaderboard data:', error);
                // Handle error jika diperlukan
            }
        });
    }

    // Fungsi untuk merender data leaderboard ke dalam tabel HTML
    function Leaderboard(data) {
        const $leaderboardBody = $('#leaderboard-body');
        $leaderboardBody.empty(); // Kosongkan isi tbody leaderboard

        // Iterasi data untuk menambahkan baris ke dalam tabel
        $.each(data, function(index, item) {
            const $row = $('<tr>');

            // Tambahkan kolom-kolom sesuai dengan struktur HTML yang sudah ada
            $row.append($('<td>').text(index + 1)); // Rank
            $row.append($('<td>').text(item.teamName)); // Team Name
            $row.append($('<td>').text(item.boothsPlayed)); // Booths Played
            $row.append($('<td>').text(item.points)); // Points

            $leaderboardBody.append($row); // Tambahkan baris ke dalam tbody
        });
    }

    fetchSession();
    
    // Contoh untuk mengupdate leaderboard secara berkala (misalnya setiap 10 detik)
    setInterval(function() {
        fetchSession(); // Ambil data leaderboard kembali
    }, 10000); // Ambil setiap 10 detik (sesuaikan interval sesuai kebutuhan)
});
