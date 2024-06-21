$(document).ready(function () {
  // Fungsi ini memastikan bahwa kode di dalamnya dijalankan setelah dokumen HTML selesai dimuat
  const domain = 'http://localhost:8080/'; // Basis domain untuk permintaan API
  var teamData = {}; // Objek untuk menyimpan data tim

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


  function gethistory () {
    fetch(domain + 'api/boothgames/getSelfBoothDuel', {
      method: 'GET',
      headers: { Token: getCookie('Token') },
    })
    .then((response) => response.json())
    .then((data) => {
      if (!data.error) {
        data.data.forEach((history) => {
        const historyItem = `
          <div class="history-item">
              <div class="history-row">
                  <div class = "history-group team">
                      <div class="history-cell team" data-label="Team Name"><p>Team Name</p>
                      <p class="${history.team1 === winningTeam ? 'winner' : ''}">${history.team1}</p></div>
                      <div class="history-cell" data-label="VS">VS</div>
                      <div class="history-cell team" data-label="Team Name"><p>Team Name</p>
                      <p class="${history.team2=== winningTeam ? 'winner' : ''}">${history.team2}</p></div>
                  </div>
                  <div class = "history-group time">
                      <div class="history-cell time" data-label="Time Started"><p>Time Started</p><p>${history.waktuMulai}</p></div>
                      <div class="history-cell" data-label="VS">-</div>
                      <div class="history-cell time" data-label="Time Finished"><p>Time Finished</p><p>${history.waktuSelesai}</p></div>
                  </div>
                  <div class="history-cell time" data-label="Duration"><p>Duration</p><p>${history.durasiPermainan}</p></div>
              </div>
          </div>
          `;

          $('#history').append(historyItem);
        })
      }
    })
  }
  
  // Fungsi untuk mengambil dan menampilkan nama-nama tim
  function fetchTeamNames() {
    fetch(domain + 'api/team/getTeamPerGame', {
      method: 'GET',
      headers: { Token: getCookie('Token') }, // Menyertakan token dalam header dari cookie
    })
      .then((response) => response.json())
      .then((data) => {
        const teamSelect1 = document.getElementById('team1');
        const teamSelect2 = document.getElementById('team2');

        // Hapus semua opsi yang ada
        teamSelect1.innerHTML = '';
        teamSelect2.innerHTML = '';

        // Tambahkan opsi untuk setiap tim dari data yang diterima
        data.data.forEach((team) => {
          teamData[team.namaTeam] = team; // Simpan data tim dalam objek teamData
          const option1 = document.createElement('option');
          option1.value = team.namaTeam; // Isi nilai dan teks opsi dengan nama tim
          option1.textContent = team.namaTeam;
          teamSelect1.appendChild(option1);

          const option2 = document.createElement('option');
          option2.value = team.namaTeam; // Isi nilai dan teks opsi dengan nama tim
          option2.textContent = team.namaTeam;
          teamSelect2.appendChild(option2);
        });

        // Setel opsi placeholder
        const placeholder1 = document.createElement('option');
        const placeholder2 = document.createElement('option');
        placeholder1.value = '';
        placeholder2.value = '';
        teamSelect1.prepend(placeholder1); // Tambahkan placeholder di bagian atas
        teamSelect2.prepend(placeholder2); // Tambahkan placeholder di bagian atas
        teamSelect1.value = '';
        teamSelect2.value = '';
      })
      .catch((error) => console.error('Error loading team names:', error)); // Menangani dan log error jika permintaan gagal
  }

  // Fungsi untuk memperbarui opsi tim yang dipilih
  function updateTeamOptions() {
    const teamSelect1 = document.getElementById('team1');
    const teamSelect2 = document.getElementById('team2');
    const winningTeamSelect = document.getElementById('winningTeam');
    const selectedTeam1 = teamSelect1.value;
    const selectedTeam2 = teamSelect2.value;

    // Menonaktifkan opsi di teamSelect1 jika tim tersebut dipilih di teamSelect2 dan sebaliknya
    for (let i = 0; i < teamSelect1.options.length; i++) {
      const option1 = teamSelect1.options[i];
      const option2 = teamSelect2.options[i];

      if (option1.value === selectedTeam2) {
        option1.disabled = true;
      } else {
        option1.disabled = false;
      }

      if (option2.value === selectedTeam1) {
        option2.disabled = true;
      } else {
        option2.disabled = false;
      }
    }

    // Perbarui opsi untuk winningTeam
    winningTeamSelect.innerHTML = '';
    const placeholder = document.createElement('option');
    placeholder.value = '';
    winningTeamSelect.appendChild(placeholder);

    if (selectedTeam1) {
      const option1 = document.createElement('option');
      option1.value = selectedTeam1;
      option1.textContent = selectedTeam1;
      winningTeamSelect.appendChild(option1);
    }

    if (selectedTeam2) {
      const option2 = document.createElement('option');
      option2.value = selectedTeam2;
      option2.textContent = selectedTeam2;
      winningTeamSelect.appendChild(option2);
    }
  }

  // Menambahkan event listener untuk mengupdate opsi tim ketika pilihan berubah
  document.getElementById('team1').addEventListener('change', updateTeamOptions);
  document.getElementById('team2').addEventListener('change', updateTeamOptions);

  // Memanggil fungsi untuk mengambil nama-nama tim saat dokumen siap
  fetchTeamNames();
  gethistory();
});
