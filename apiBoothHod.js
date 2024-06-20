$(document).ready(function () {
  const domain = 'http://localhost:8080/';
  let panitiaData = {};

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

  function populateSelect(selectElement, dataArray, valueKey, textKey) {
    dataArray.forEach((item) => {
      const option = document.createElement('option');
      option.value = item[valueKey];
      option.textContent = item[textKey];
      selectElement.appendChild(option);

      // Simpan data ID Panitia
      panitiaData[item[textKey]] = item[valueKey];
    });
  }

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

  function updateGuardOptions() {
    const guard1Select = document.getElementById('guard1Name');
    const guard2Select = document.getElementById('guard2Name');
    const selectedGuard1 = guard1Select.value;
    const selectedGuard2 = guard2Select.value;

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

  document.getElementById('guard1Name').addEventListener('change', updateGuardOptions);
  document.getElementById('guard2Name').addEventListener('change', updateGuardOptions);

  fetchDataBooth();

  function getBase64(file) {
    return new Promise((resolve, reject) => {
      const reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = () => resolve(reader.result);
      reader.onerror = (error) => reject(error);
    });
  }
  
  async function postDataBooth() {
    const boothName = document.getElementById('boothName').value;
    const guard1Name = document.getElementById('guard1Name').value;
    const guard2Name = document.getElementById('guard2Name').value;
    const howToPlay = document.getElementById('howtoplay').value;
    const noRuangan = document.getElementById('noRuangan').value;
    const tipeGame = document.getElementById('tipeGame').value;
    const fileInput = document.getElementById('imageInput').files[0];
  
    // Dapatkan ID Panitia dari nama yang dipilih
    const guard1Id = panitiaData[guard1Name];
    const guard2Id = panitiaData[guard2Name];
  
    let base64File = null;
    if (fileInput) {
      base64File = await getBase64(fileInput);
    }
  
    const payload = {
      namaBoothGame: boothName,
      namaPanitia1: guard1Id,
      namaPanitia2: guard2Id,
      sopGame: howToPlay,
      noRuangan: noRuangan,
      tipeGame: tipeGame,
      fotoBooth: base64File,  // Optional, if needed
    };
  
    fetch(domain + 'api/boothgames', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Token': getCookie('Token')
      },
      body: JSON.stringify(payload)
    })
    .then(response => {
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }
      return response.json();
    })
    .then(data => {
      console.log('Success:', data);
    })
    .catch(error => {
      console.error('Error:', error);
    });
  }
  
  document.getElementById('booth-form').addEventListener('submit', function(event) {
    event.preventDefault();
    postDataBooth();
  });
});
