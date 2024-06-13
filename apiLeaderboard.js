$(document).ready(function() {
    // Fungsi untuk mengambil dan merender data leaderboard
    function fetchAndRenderLeaderboard() {
        $.ajax({
            url: 'https://example.com/api/leaderboard', // Ganti dengan URL endpoint yang sesuai
            method: 'GET',
            dataType: 'json',
            success: function(data) {
                renderLeaderboard(data); // Panggil fungsi renderLeaderboard dengan data yang diterima
            },
            error: function(xhr, status, error) {
                console.error('Failed to fetch leaderboard data:', error);
                // Handle error jika diperlukan
            }
        });
    }

    // Fungsi untuk merender data leaderboard ke dalam tabel HTML
    function renderLeaderboard(data) {
        const $leaderboardBody = $('#leaderboard-body');
        $leaderboardBody.empty(); // Kosongkan isi tbody leaderboard

        // Iterasi data untuk menambahkan baris ke dalam tabel
        $.each(data, function(index, item) {
            const $row = $('<tr>');

            // Tambahkan kolom-kolom sesuai dengan struktur HTML yang sudah ada
            $row.append($('<td>').text(index + 1)); // Rank
            $row.append($('<td>').text(item.teamName)); // Team Name
            $row.append($('<td>').text(item.boothsPlayed)); // Booths Played
            $row.append($('<td>').text(item.points)); // Points

            $leaderboardBody.append($row); // Tambahkan baris ke dalam tbody
        });
    }

    // Panggil fetchAndRenderLeaderboard saat halaman pertama kali dimuat
    fetchAndRenderLeaderboard();

    // Contoh untuk mengupdate leaderboard secara berkala (misalnya setiap 10 detik)
    setInterval(function() {
        fetchAndRenderLeaderboard(); // Ambil data leaderboard kembali
    }, 10000); // Ambil setiap 10 detik (sesuaikan interval sesuai kebutuhan)

});
