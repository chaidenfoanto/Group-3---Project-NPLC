$(document).ready(function() {
    // Menangkap elemen formulir
const boothForm = document.getElementById('booth-form');
const boothNameInput = document.getElementById('boothName');
const guard1NameInput = document.getElementById('guard1Name');
const guard2NameInput = document.getElementById('guard2Name');
const howtoplayInput = document.getElementById('howtoplay');
const noRuanganInput = document.getElementById('noRuangan');
const tipeGameInput = document.getElementById('tipeGame');

// Menangkap elemen pesan error
const boothNameError = document.getElementById('boothNameError');
const guard1NameError = document.getElementById('guard1NameError');
const guard2NameError = document.getElementById('guard2NameError');
const howtoplayError = document.getElementById('howtoplayError');
const noRuanganError = document.getElementById('noRuanganError');
const tipeGameError = document.getElementById('tipeGameError');

// Menambahkan event listener untuk event submit
boothForm.addEventListener('submit', function(event) {
    // Cek setiap input jika kosong
    let isError = false;

    if (boothNameInput.value.trim() === '') {
        boothNameError.textContent = 'Nama Booth tidak boleh kosong';
        isError = true;
    } else {
        boothNameError.textContent = '';
    }

    if (guard1NameInput.value.trim() === '') {
        guard1NameError.textContent = 'Penjaga Booth 1 tidak boleh kosong';
        isError = true;
    } else {
        guard1NameError.textContent = '';
    }

    if (guard2NameInput.value.trim() === '') {
        guard2NameError.textContent = 'Penjaga Booth 2 tidak boleh kosong';
        isError = true;
    } else {
        guard2NameError.textContent = '';
    }

    if (howtoplayInput.value.trim() === '') {
        howtoplayError.textContent = 'Cara Bermain tidak boleh kosong';
        isError = true;
    } else {
        howtoplayError.textContent = '';
    }

    if (noRuanganInput.value.trim() === '') {
        noRuanganError.textContent = 'No. Ruangan tidak boleh kosong';
        isError = true;
    } else {
        noRuanganError.textContent = '';
    }

    if (tipeGameInput.value === '') {
        tipeGameError.textContent = 'Tipe Game harus dipilih';
        isError = true;
    } else {
        tipeGameError.textContent = '';
    }

    // Hentikan pengiriman formulir jika ada error
    if (isError) {
        event.preventDefault();
    }
});

});