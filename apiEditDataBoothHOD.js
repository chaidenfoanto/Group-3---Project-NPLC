$(document).ready(function () {
    let panitiaData = {};
    const domain = 'http://localhost:8080/'; 
    const idBoothGame = window.location.href.split("?id=")[1];
    if(idBoothGame == "") window.location.href="DashboardHod.html";
    else fetchDataBooth(idBoothGame);
    console.log(idBoothGame);
    var boothData = {}; 
    const popup = document.getElementById("popup");
  const popupOverlay = document.getElementById('popup-overlay');
  
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
    window.openPopup = function (id) {
      const popup = document.getElementById(id);
      popup.classList.add("open-popup");
      popupOverlay.classList.add("active");
  }
  
  window.closePopup = function (id) {
      const popup = document.getElementById(id);
      popup.classList.remove("open-popup");
      popupOverlay.classList.remove("active");
  }

  function populateSelect(selectElement, dataArray, valueKey, textKey) {
    dataArray.forEach((item) => {
      const option = document.createElement("option");
      option.value = item[textKey];
      option.textContent = item[textKey];
      selectElement.appendChild(option);

      // Simpan data ID Panitia
      panitiaData[item[textKey]] = item[valueKey];
    });
  }
  
  function fetchPanitia() {
    fetch(domain + "api/boothgames/getAvailableDatas", {
      method: "GET",
      headers: { Token: getCookie("Token") },
    })
      .then((response) => response.json())
      .then((data) => {
        const listPanitia = data.data.listPanitia;
        const listLokasi = data.data.listLokasi;

        const guard1Select = document.getElementById("guard1Name");
        const guard2Select = document.getElementById("guard2Name");
        const noRuanganSelect = document.getElementById("noRuangan");

        populateSelect(guard1Select, listPanitia, "idPanitia", "namaPanitia");
        populateSelect(guard2Select, listPanitia, "idPanitia", "namaPanitia");
        populateSelect(noRuanganSelect, listLokasi, "noRuangan", "noRuangan");
      })
      .catch((error) => console.error("Error fetching data:", error));
  }

  function updateGuardOptions() {
    const guard1Select = document.getElementById("guard1Name");
    const guard2Select = document.getElementById("guard2Name");
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

  document
    .getElementById("guard1Name")
    .addEventListener("change", updateGuardOptions);
  document
    .getElementById("guard2Name")
    .addEventListener("change", updateGuardOptions);

    fetchPanitia();
   
    function fetchDataBooth(idBoothGame) {
      fetch(domain + 'api/boothgames/getById/'+idBoothGame, {
        method: 'GET',
        headers: { Token: getCookie('Token') },
      })
      .then((response) => response.json())
      .then((data) => {
        if (!data.error) {
          const booth = data.data;
  
          $('#cardImage').attr('src', booth.fotoBooth); // Update src attribute of img element
          $('#boothName').val(booth.namaBoothGame);
          $('#howtoplay').val(booth.sopGame);
  
          $('#guard1Name').empty();
          $('#guard1Name').append(new Option(booth.panitia1.namaPanitia, booth.panitia1.IDPanitia));
  
          $('#guard2Name').empty();
          if (booth.panitia2) {
            $('#guard2Name').append(new Option("Tidak Ada", "Tidak Ada"));
            var opsi = new Option(booth.panitia2.namaPanitia, booth.panitia2.IDPanitia);
            opsi.setAttribute("selected", true);
            $('#guard2Name').append(opsi);
          } else {
            $('#guard2Name').append(new Option("Tidak Ada", "Tidak Ada"));
          }
  
          $('#noRuangan').empty();
          $('#noRuangan').append(new Option(booth.lokasi.noRuangan, booth.lokasi.noRuangan));
  
          $('#tipeGame').empty();
          $('#tipeGame').append(new Option("Single", "Single"));
          $('#tipeGame').append(new Option("Duel", "Duel"));
          $('#tipeGame').val(booth.tipeGame);
        } else {
          console.error('Error:', data.message);
        //   window.location.href = "DashboardHod.html";
        }
      })
      .catch((error) => console.error('Error fetching booth data:', error));
    }
  
    
    async function postDataBooth() {
        const boothName = document.getElementById("boothName").value;
        const guard1Name = document.getElementById("guard1Name").value;
        const guard2Name = document.getElementById("guard2Name").value;
        const howToPlay = document.getElementById("howtoplay").value;
        const noRuangan = document.getElementById("noRuangan").value;
        const tipeGame = document.getElementById("tipeGame").value;
        const inputDurasi = document.getElementById("inputTime").value;
        const fileInput = document.getElementById("imageInput").files[0];
    
        // Dapatkan ID Panitia dari nama yang dipilih
        const guard1Id = panitiaData[guard1Name];
        const guard2Id = panitiaData[guard2Name];
    
        let base64File = null;
        if (fileInput) {
          base64File = await compressImage(fileInput, 0.7);
        }
    
        const payload = {
          nama: boothName,
          idPenjaga1: guard1Id,
          idPenjaga2: guard2Id,
          sopGames: howToPlay,
          lokasi: noRuangan,
          tipeGame: tipeGame,
          durasiPermainan: inputDurasi,
          fotoBooth: base64File, // Optional, if needed
        };
    
        fetch(domain + "api/boothgames/"+idBoothGame, {
          method: "PUT",
          headers: {
            "Content-Type": "application/json",
            Token: getCookie("Token"),
          },
          body: JSON.stringify(payload),
        })
          .then((response) => response.json())
          .then((data) => {
            console.log(data);
            if (!data.error) {
              window.location.href = "DashboardHod.html";
            } else {
              console.log(data.message);
            }
          })
          .catch((error) => {
            console.error("Error to post:", error);
          });
      }
    
      const boothForm = document.getElementById("booth-form");
      const fileUploadInput = document.getElementById("imageInput");
      const boothNameInput = document.getElementById("boothName");
      const guard1NameInput = document.getElementById("guard1Name");
      const guard2NameInput = document.getElementById("guard2Name");
      const howtoplayInput = document.getElementById("howtoplay");
      const noRuanganInput = document.getElementById("noRuangan");
      const tipeGameInput = document.getElementById("tipeGame");
      const inputTime = document.getElementById("inputTime");
    
      // Menangkap elemen pesan error
      const fileUploadError = document.getElementById("image-inputError");
      const boothNameError = document.getElementById("boothNameError");
      const guard1NameError = document.getElementById("guard1NameError");
      const guard2NameError = document.getElementById("guard2NameError");
      const howtoplayError = document.getElementById("howtoplayError");
      const noRuanganError = document.getElementById("noRuanganError");
      const tipeGameError = document.getElementById("tipeGameError");
      const inputTimeError = document.getElementById("inputTimeError");
      
      // Menambahkan event listener untuk event submit
      boothForm.addEventListener("submit", function (event) {
        event.preventDefault();
        let isError = false;
    
        if (boothNameInput.value.trim() === "") {
          boothNameError.textContent = "Nama Booth tidak boleh kosong";
          isError = true;
        } else {
          boothNameError.textContent = "";
        }
    
        if (guard1NameInput.value.trim() === "") {
          guard1NameError.textContent = "Penjaga Booth 1 tidak boleh kosong";
          isError = true;
        } else {
          guard1NameError.textContent = "";
        }
    
        // if (guard2NameInput.value.trim() === '') {
        //     guard2NameError.textContent = 'Penjaga Booth 2 tidak boleh kosong';
        //     isError = true;
        // } else {
        //     guard2NameError.textContent = '';
        // }
    
       
        if (howtoplayInput.value.trim() === "") {
          howtoplayError.textContent = "Cara Bermain tidak boleh kosong";
          isError = true;
        } else {
          howtoplayError.textContent = "";
        }
    
        if (noRuanganInput.value.trim() === "") {
          noRuanganError.textContent = "No. Ruangan tidak boleh kosong";
          isError = true;
        } else {
          noRuanganError.textContent = "";
        }
    
        if (tipeGameInput.value === "") {
          tipeGameError.textContent = "Tipe Game harus dipilih";
          isError = true;
        } else {
          tipeGameError.textContent = "";
        }
    
        if (inputTime.value === "00:00") {
          inputTimeError.textContent = "Waktu harus dipilih";
          isError = true;
        } else {
          inputTimeError.textContent = "";
        }
    
        postDataBooth();
    
        // Hentikan pengiriman formulir jika ada error
        if (isError) {
          event.preventDefault();
        }
      });
  });
  