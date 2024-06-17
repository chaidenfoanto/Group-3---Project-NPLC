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

  const addBoothgames = document.getElementById('save');

  addBoothgames.addEventListener('submit', async (event) => {
    event.preventDefault();

    const fotoBooth = document.getElementById('imageInput').value;
    const boothName = document.getElementById('boothName').value;
    const guard1Name = document.getElementById('guard1Name').value;
    const guard2Name = document.getElementById('guard2Name').value;
    const howtoplay = document.getElementById('howtoplay').value;
    const noRuangan = document.getElementById('noRuangan').value;
    const tipeGame = document.getElementById('tipeGame').value;

    try {
      const response = await fetch(domain + '/api/boothgames', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          Token: getCookie('Token'),
        },
        body: JSON.stringify({ fotoBooth, boothName, guard1Name, guard2Name, howtoplay, noRuangan, tipeGame }),
      });

      const result = await response.json();

      if (!data.error) {
        console.log('Boothgames Added Successfully');
      } else {
        console.log('Boothgames Failed to Add');
      }
    } catch (error) {
      showErrorMessage('Gagal untuk menambahkan boothgames');
    }
  });
});
