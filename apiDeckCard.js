$(document).ready(function() {
    // Fungsi untuk melakukan fetch data dari API
    function fetchData() {
        // Ganti URL API dengan URL sesuai kebutuhan Anda
        fetch('https://example.com/api/cards')
            .then(response => response.json())
            .then(data => {
                // Loop melalui data untuk menampilkan informasi kartu
                data.forEach(card => {
                    // Dapatkan elemen kartu berdasarkan ID kartu
                    var cardElement = $('#' + card.id);
                    // Update jumlah kartu yang dimiliki
                    cardElement.find('.total-owned').text(card.totalOwned);
                    // Update jumlah kartu yang digunakan
                    cardElement.find('.total-used').text(card.totalUsed);
                });
            })
            .catch(error => console.error('Error fetching data:', error));
    }

    // Panggil fungsi fetchData saat dokumen siap
    fetchData();
    setInterval(fetchData, 1000);
});
