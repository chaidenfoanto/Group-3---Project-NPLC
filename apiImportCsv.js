$(document).ready(function () {
    const domain = 'http://localhost:8080/'; // Basis domain untuk permintaan API
  
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

    function postCSVData(data) {
        const token = getCookie('Token'); // Mendapatkan token dari cookie

        data.forEach(panitia => {
            $.ajax({
                url: domain + 'api/panitia', // URL endpoint API
                method: 'POST', // Metode HTTP POST
                headers: {
                    'Content-Type': 'application/json',
                    'Token': token
                },
                data: JSON.stringify(panitia), // Mengirim data panitia sebagai JSON string
                success: function(response) {
                    console.log("Panitia Successfully Added: ", response);
                },
                error: function(xhr, status, error) {
                    console.error("Error posting panitia data: ", error);
                }
            });
        });
    }

    $('#uploadButton').on('click', function () {
        const fileInput = document.getElementById('csvFileInput');
        if (fileInput.files.length > 0) {
            const file = fileInput.files[0];
            Papa.parse(file, {
                header: true,
                complete: function (results) {
                    const data = results.data;
                    postCSVData(data);
                }
            });
        } else {
            alert("Please select a file.");
        }
    });
});
