let cardsData = [];

$(document).ready(function () {
  const domain = "http://localhost:8080/";
  
  
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

  

  
});

// function postresults() {
//   // Mengambil data dari elemen formulir
//   const teamPlayed = document.getElementById("teamplayed").value;
//   const starEarned = document.getElementById("starEarned").value;
//   const timeStarted = document.getElementById("timeStarted").value;
//   const timeFinished = document.getElementById("timeFinished").value;
//   const duration = document.getElementById("duration").value;

//   // Membuat objek data yang akan dikirim
//   const data = {
//     teamPlayed: teamPlayed,
//     starEarned: starEarned,
//     timeStarted: timeStarted,
//     timeFinished: timeFinished,
//     duration: duration,
//   };

//   // Melakukan fetch ke API
//   fetch(domain + "api/singlematch/finish", {
//     method: "POST",
//     headers: {
//       "Content-Type": "application/json",
//       Token: getCookie("Token"), // Tambahkan token untuk otentikasi
//     },
//     body: JSON.stringify(data), // Mengubah objek data menjadi JSON
//   })
//     .then((response) => response.json())
//     .then((data) => {
//       console.log("Success:", data);
//       // Anda dapat menambahkan aksi lain di sini, misalnya menutup modal atau menampilkan pesan sukses
//     })
//     .catch((error) => {
//       console.error("Error:", error);
//       // Anda dapat menambahkan aksi lain di sini untuk menangani error
//     });
// }

  // function gethistory() {
  //   fetch(domain + 'api/', {
  //     method: 'GET',
  //     headers: { Token: getCookie('Token') },
  //   })
  //     .then((response) => response, json())
  //     .then((data) => {
  //       if (!data.error) {
  //         const historyItem = `
  //         <div class="history-item">
  //             <div class="history-row">
  //                 <div class = "history-group team">
  //                     <div class="history-cell team" data-label="Team Name"><p>Team Name</p>
  //                     <p class="${team1 === winningTeam ? 'winner' : ''}">${team1}</p></div>
  //                     <div class="history-cell" data-label="VS">VS</div>
  //                     <div class="history-cell team" data-label="Team Name"><p>Team Name</p>
  //                     <p class="${team2 === winningTeam ? 'winner' : ''}">${team2}</p></div>
  //                 </div>
  //                 <div class = "history-group time">
  //                     <div class="history-cell time" data-label="Time Started"><p>Time Started</p><p>${timeStarted}</p></div>
  //                     <div class="history-cell" data-label="VS">-</div>
  //                     <div class="history-cell time" data-label="Time Finished"><p>Time Finished</p><p>${timeFinished}</p></div>
  //                 </div>
  //                 <div class="history-cell time" data-label="Duration"><p>Duration</p><p>${duration}</p></div>
  //             </div>
  //         </div>
  //         `;

  //         $('#history').append(historyItem);
  //       }
  //     });
  // }
