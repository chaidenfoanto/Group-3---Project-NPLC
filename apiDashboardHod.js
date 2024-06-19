$(document).ready(function () {
    // Fungsi ini memastikan bahwa kode di dalamnya dijalankan setelah dokumen HTML selesai dimuat
    const domain = 'http://localhost:8080/'; // Basis domain untuk permintaan API
    var boothData = {}; // Objek untuk menyimpan data tim
  
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
  
    // Fungsi untuk mengambil dan menampilkan nama-nama tim
    function fetchDataBooth() {
      fetch(domain + 'api/', {
        method: 'GET',
        headers: { Token: getCookie('Token') }, // Menyertakan token dalam header dari cookie
      })
        .then((response) => response.json())
        .then((data) => {
          const guardSelect1 = document.getElementById('guard1Name');
          const guardSelect2 = document.getElementById('guard2Name');
          const noRuangan = document.getElementById('noRuangan');
  
          // Hapus semua opsi yang ada
          guardSelect1.innerHTML = '';
          guardSelect2.innerHTML = '';
          noRuangan.innerHTML = '';
  
          // Tambahkan opsi untuk setiap tim dari data yang diterima
          data.data.forEach((booth) => {
            boothData[booth.penjaga] = booth; // Simpan data tim dalam objek teamData
            const option1 = document.createElement('option');
            option1.value = booth.penjaga; // Isi nilai dan teks opsi dengan nama tim
            option1.textContent = booth.penjaga;
            guardSelect1.appendChild(option1);
  
            const option2 = document.createElement('option');
            option2.value = booth.penjaga; // Isi nilai dan teks opsi dengan nama tim
            option2.textContent = booth.penjaga;
            guardSelect2.appendChild(option2);
  
            const option3 = document.createElem('option');
            option3.value = booth.noRuangan;
            option3.textContent = booth.noRuangan;
            noRuangan.appendChild(option3);
          });
  
          // Setel opsi placeholder
          const placeholder1 = document.createElement('option');
          const placeholder2 = document.createElement('option');
          const placeholder3 = document.createElement('option');
          placeholder1.value = '';
          placeholder2.value = '';
          placeholder3.value = '';
          teamSelect1.prepend(placeholder1); // Tambahkan placeholder di bagian atas
          teamSelect2.prepend(placeholder2); // Tambahkan placeholder di bagian atas
          noRuangan.prepend(placeholder3); // Tambahkan placeholder di bagian atas
          teamSelect1.value = '';
          teamSelect2.value = '';
          noRuangan.value = '';
        })
        .catch((error) => console.error('Error loading booth:', error)); // Menangani dan log error jika permintaan gagal
    }
  
    // Fungsi untuk memperbarui opsi tim yang dipilih
    function updateBoothOptions() {
      const guardSelect1 = document.getElementById('guard1Name');
      const guardSelect2 = document.getElementById('guard2Name');
      const selectedGuard1 = guardSelect1.value;
      const selectedGuard2 = guardSelect2.value;
  
      // Menonaktifkan opsi di teamSelect1 jika tim tersebut dipilih di teamSelect2 dan sebaliknya
      for (let i = 0; i < guardSelect1.options.length; i++) {
        const option1 = guardSelect1.options[i];
        const option2 = guardSelect2.options[i];
  
        if (option1.value === selectedGuard2) {
          option1.disabled = true;
        } else {
          option1.disabled = false;
        }
  
        if (option2.value === selectedGuard1) {
          option2.disabled = true;
        } else {
          option2.disabled = false;
        }
      }
    }
  
    // Menambahkan event listener untuk mengupdate opsi tim ketika pilihan berubah
    document.getElementById('guard1Name').addEventListener('change', updateBoothOptions);
    document.getElementById('guard2Name').addEventListener('change', updateBoothOptions);
  
    // Memanggil fungsi untuk mengambil nama-nama tim saat dokumen siap
    fetchDataBooth();
  });
  