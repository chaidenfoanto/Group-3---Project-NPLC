$(document).ready(function () {
    const domain = "http://localhost:8080/";

    async function fetchSession() {
        await fetch(domain + 'api/login/getSession', {
            method: 'GET',
            headers: { 'Token': getCookie('Token') }
        })
            .then(response => response.json())
            .then(data => {
                if (!data.error) {
                    console.log("authorized success");
                    setCookie("Token", getCookie("Token"), 365);
                } else {
                    console.log('Authorization Failed');
                    deleteCookie("Token");
                    window.location.href = "LoginPanitia.html";
                }
            })
            .catch(error => {
                console.error('Error occurred while fetching session:', error);
                deleteCookie("Token");
                window.location.href = "LoginPanitia.html";
            });
    }

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

    function setCookie(name, value, daysToLive) {
        let cookie = name + "=" + encodeURIComponent(value);
        if (typeof daysToLive === "number") {
            cookie += "; max-age=" + (daysToLive * 24 * 60 * 60) + "; path=/";
            document.cookie = cookie;
        }
    }

    function deleteCookie(name) {
        document.cookie = name + '=; Max-Age=-99999999;';
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
                    'Token': getCookie('Token')
                },
                body: JSON.stringify({ fotoBooth, boothName, guard1Name, guard2Name, howtoplay, noRuangan, tipeGame })
            });

            const result = await response.json();

            if (!data.error) {
                console.log('Boothgames Added Successfully')
            } else {
                console.log('Boothgames Failed to Add')
            }
        } catch (error) {
            showErrorMessage('Gagal untuk menambahkan boothgames');
        }
    });
});