$(document).ready(function() {
    const apiURL = 'https://api.example.com/games'; // Ganti dengan URL API yang sesuai
    const gamesList = $('.games-list');
    const totalPointsElement = $('.total');

    // Fungsi untuk mengambil data dari API
    async function fetchData() {
        try {
            const response = await fetch(apiURL);
            const data = await response.json();
            displayData(data);
        } catch (error) {
            console.error('Error fetching data:', error);
        }
    }

    // Fungsi untuk menampilkan data
    function displayData(data) {
        let totalPoints = 0;

        data.forEach(game => {
            let content;
            if (game.type === 'duel') {
                const match1Status = game.match1.win ? 'Win' : 'Lose';
                const match2Status = game.match2.win ? 'Win' : 'Lose';

                content = `
                    <p>Match 1: ${match1Status}</p>
                    <p>Match 2: ${match2Status}</p>
                `;
            } else if (game.type === 'single') {
                const points = game.points;
                const stars = game.stars;

                content = `
                    <p>Points: ${points}</p>
                    <p>Stars: ${stars}</p>
                `;

                totalPoints += points;
            }

            const gameCard = $(`
                <div class="game-card">
                    <img src="${game.image}" alt="${game.name}" class="game-image">
                    <div class="badge">${game.type}</div>
                    <h3>${game.name}</h3>
                    ${content}
                </div>
            `);

            gamesList.append(gameCard);
        });

        totalPointsElement.text(totalPoints);
    }

    // Panggil fungsi untuk mengambil data saat halaman dimuat
    fetchData();
    setInterval(fetchData, 1000); 
});
