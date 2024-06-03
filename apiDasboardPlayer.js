$(document).ready(function() {
    const domain = "http://localhost:8080"
    const gamesList = $('.games-list');
    const totalPointsElement = $('.total');

    // Fungsi untuk mengambil data dari API
    // async function fetchData() {
    //     try {
    //         const response = await fetch(domain + 'api/');
    //         const data = await response.json();
    //         displayData(data);
    //     } catch (error) {
    //         console.error('Error fetching data:', error);
    //     }
    // }

    // Fungsi untuk menampilkan data
    // function displayData(data) {
    //     let totalPoints = 0;

    //     data.forEach(game => {
    //         let content;
    //         if (game.type === 'duel') {
    //             const match1Status = game.match1.win ? 'Win' : 'Lose';
    //             const match2Status = game.match2.win ? 'Win' : 'Lose';

    //             content = `
    //                 <table class="duel">
    //                     <tr>
    //                         <td>Match 1</td>
    //                         <td>:</td>
    //                         <td>${match1Status}</td>
    //                     </tr>
    //                     <tr>
    //                         <td>Match 2</td>
    //                         <td>:</td>
    //                         <td>${match2Status}</td>
    //                     </tr>
    //                 </table>
    //             `;
    //         } else if (game.type === 'single') {
    //             const points = game.points;
    //             const stars = game.stars;

    //             content = `
    //                 <table class="single">
    //                     <tr>
    //                         <td>Points</td>
    //                         <td>:</td>
    //                         <td>${points}</td>
    //                     </tr>
    //                     <tr>
    //                         <td>Stars</td>
    //                         <td>:</td>
    //                         <td>${stars}</td>
    //                     </tr>
    //                 </table>
    //             `;

    //             totalPoints += points;
    //         }

    //         const gameCard = $(`
    //             <div class="game-card">
    //                 <img src="${game.image}" alt="${game.name}" class="game-image">
    //                 <div class="badge">${game.type}</div>
    //                 <h3>${game.name}</h3>
    //                 ${content}
    //             </div>
    //         `);

    //         gamesList.append(gameCard);
    //     });

    //     totalPointsElement.text(totalPoints);
    // }

    //     // Panggil fungsi untuk mengambil data saat halaman dimuat
    //     fetchData();
    //     setInterval(fetchData, 1000); 

});


    var myHeaders = new Headers()
    myHeaders.append("Token", getCookie("Token"))

    fetch(domain + '/api/login/getSession', {
        method: 'GET',
        headers: myHeaders
    }).then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.json();
    }).then(data => {
        if (data.service === "Auth Token") {
            if (data.message === "Authorization Success") {
                setCookie("Token", getCookie("Token"), 365); //Memperbaharui cookie token
            } else {
                console.log('Authorization Failed');
                deleteCookie("Token"); //menghapus cookie
                window.location.href = "Login.html";
            }
        } else {
            console.log('Unexpected service response:', data.service);
        }
    }).catch(error => {
        console.error('Error occurred while fetching session:', error);
        deleteCookie("Token"); //menghapus cookie
        window.location.href = "Login.html";
    });
    

    
function getCookie(name) {
    // Split cookie string and get all individual name=value pairs in an array
    let cookieArr = document.cookie.split(";");
    
    // Loop through the array elements
    for(let i = 0; i < cookieArr.length; i++) {
        let cookiePair = cookieArr[i].split("=");
        
        /* Removing whitespace at the beginning of the cookie name
        and compare it with the given string */
        if(name == cookiePair[0].trim()) {
            // Decode the cookie value and return
            return decodeURIComponent(cookiePair[1]);
        }
    }
    
    // Return null if not found
    return null;
}

function setCookie(name, value, daysToLive) {
    // Encode value in order to escape semicolons, commas, and whitespace
    let cookie = name + "=" + encodeURIComponent(value);
    
    if(typeof daysToLive === "number") {
        /* Sets the max-age attribute so that the cookie expires
        after the specified number of days */
        cookie += "; max-age=" + (daysToLive*24*60*60) + "; path=/";
        
        document.cookie = cookie;
    }
}

function deleteCookie(name) {
    document.cookie = name + '=; Max-age=0; path=/;';
}