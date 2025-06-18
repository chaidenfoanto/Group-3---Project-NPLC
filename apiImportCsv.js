$(document).ready(function () {
  const domain = 'http://localhost:8080/';

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

  function postCSVData(data) {
    const token = getCookie('Token');

    data.forEach((panitia) => {
      $.ajax({
        url: domain + 'api/panitia',
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          Token: token,
        },
        data: JSON.stringify(panitia),
        success: function (response) {
          console.log('Panitia Successfully Added: ', response);
        },
        error: function (xhr, status, error) {
          console.error('Error posting panitia data: ', error);
        },
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
        },
      });
    } else {
      alert('Please select a file.');
    }
  });
});
