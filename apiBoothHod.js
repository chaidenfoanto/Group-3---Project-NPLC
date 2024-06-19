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

  // Function to populate select options
  function populateSelect(selectElement, dataArray, valueKey, textKey) {
    dataArray.forEach((item) => {
      const option = document.createElement('option');
      option.value = item[valueKey];
      option.textContent = item[textKey];
      selectElement.appendChild(option);
    });
  }

  // Fetch data from the API and populate select elements
  function fetchDataBooth() {
    fetch(domain + 'api/boothgames/getAvailableDatas', {
      method: 'GET',
      headers: { Token: getCookie('Token') },
    })
      .then((response) => response.json())
      .then((data) => {
        const listPanitia = data.data.listPanitia;
        const listLokasi = data.data.listLokasi;

        const guard1Select = document.getElementById('guard1Name');
        const guard2Select = document.getElementById('guard2Name');
        const noRuanganSelect = document.getElementById('noRuangan');

        populateSelect(guard1Select, listPanitia, 'namaPanitia', 'namaPanitia');
        populateSelect(guard2Select, listPanitia, 'namaPanitia', 'namaPanitia');
        populateSelect(noRuanganSelect, listLokasi, 'noRuangan', 'noRuangan');
      })
      .catch((error) => console.error('Error fetching data:', error));
  }

  // Fungsi untuk memperbarui opsi penjaga yang dipilih
  function updateGuardOptions() {
    const guard1Select = document.getElementById('guard1Name');
    const guard2Select = document.getElementById('guard2Name');
    const selectedGuard1 = guard1Select.value;
    const selectedGuard2 = guard2Select.value;

    // Menonaktifkan opsi di guard1Select jika penjaga tersebut dipilih di guard2Select dan sebaliknya
    for (let i = 0; i < guard1Select.options.length; i++) {
      const option1 = guard1Select.options[i];
      const option2 = guard2Select.options[i];

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

  // Menambahkan event listener untuk mengupdate opsi penjaga ketika pilihan berubah
  document.getElementById('guard1Name').addEventListener('change', updateGuardOptions);
  document.getElementById('guard2Name').addEventListener('change', updateGuardOptions);

  fetchDataBooth();
});
