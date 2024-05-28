document.addEventListener('DOMContentLoaded', function () {
    function showErrorMessage(inputElement, message) {
        const errorMessageElement = document.getElementById(inputElement.id + 'Error');
        errorMessageElement.textContent = message;
        errorMessageElement.style.display = 'block';
    }

    function hideErrorMessage(inputElement) {
        const errorMessageElement = document.getElementById(inputElement.id + 'Error');
        errorMessageElement.textContent = '';
        errorMessageElement.style.display = 'none';
    }

    function validateInputs(inputs) {
        let isValid = true;
        let hasError = false;
    
        inputs.forEach(input => {
            if (!input.value.trim()) {
                showErrorMessage(input, 'Harap isi semua data dengan lengkap.');
                isValid = false;
                hasError = true;
            } else {
                hideErrorMessage(input);
            }
        });
    
        const fileInputs = inputs.filter(input => input.type === 'file');
        const anyFileUploaded = fileInputs.some(input => input.files[0]);
        if (!anyFileUploaded && !hasError) {
            fileInputs.forEach(input => {
                showErrorMessage(input, 'Harap pilih file foto.');
            });
            isValid = false;
        }
    
        return isValid;
    }
    

    // const registerForm = document.querySelector('.form-container.register form');
    // registerForm.addEventListener('submit', function (event) {
    //     event.preventDefault();

    //     const inputs = [
    //         document.getElementById('teamName'),
    //         document.getElementById('schoolName'),
    //         document.getElementById('player1'),
    //         document.getElementById('player2'),
    //         document.getElementById('player3'),
    //         document.getElementById('guruPendamping'),
    //         document.getElementById('whatsapp'),
    //         document.getElementById('category'),
    //         document.getElementById('photo1'),
    //         document.getElementById('photo2'),
    //         document.getElementById('photo3'),
    //         document.getElementById('paymentProof')
    //     ];

    //     if (!validateInputs(inputs)) {
    //         return;
    //     }

    //     const photoInputs = [
    //         { input: document.getElementById('photo1')},
    //         { input: document.getElementById('photo2')},
    //         { input: document.getElementById('photo3')},
    //         { input: document.getElementById('paymentProof')}
    //     ];

    //     let isPhotoValid = true;
    //     photoInputs.forEach(photoInput => {
    //         const input = photoInput.input;
    //         const preview = photoInput.preview;
    //         if (!input.files[0]) {
    //             showErrorMessage(input, 'Harap pilih file foto.');
    //             isPhotoValid = false;
    //         } else {
    //             hideErrorMessage(input);
    //             const reader = new FileReader();
    //             reader.onload = function (e) {
    //                 preview.src = e.target.result;
    //                 preview.style.display = 'block';
    //             }
    //             reader.readAsDataURL(input.files[0]);
    //         }
    //     });
        
    //     if (!isPhotoValid) {
    //         return;
    //     }

    //     const formData = new FormData(registerForm);

    //     fetch('https://bot.telkom.club/Warredion/public/api/register', {
    //         method: 'POST',
    //         body: formData
    //     })
    //     .then(response => response.json())
    //     .then(data => {
    //         console.log('Response from API:', data);
    //         if (data.status === 'success') {
    //             // Menampilkan popup jika pendaftaran berhasil
    //             const popup = document.getElementById('registerSuccessPopup');
    //             popup.classList.add('open-popup');

    //             // Reset formulir setelah popup ditutup
    //             const okButton = document.getElementById('okButton');
    //             okButton.addEventListener('click', function() {
    //                 popup.classList.remove('open-popup');
    //                 registerForm.reset();
    //             });
    //         } else {
    //             console.error('Pendaftaran gagal:', data.message);
    //         }
    //     })
    //     .catch(error => {
    //         console.error('Error:', error);
    //     });
    // });

    // Mengambil referensi ke elemen formulir login, popup, dan overlay popup dari dokumen HTML
    const loginForm = document.querySelector('.form-container.log-in form');
    const popup = document.getElementById('popup');
    const popupOverlay = document.getElementById('popup-overlay');
    const popupMessage = document.querySelector('.popup p');

    // Menambahkan event listener untuk menangani pengiriman formulir login
    loginForm.addEventListener('submit', async (event) => {
        // Mencegah perilaku bawaan pengiriman formulir yang memuat ulang halaman
        event.preventDefault();
        
        // Mengambil nilai username dan password dari input formulir login
        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;

        try {
            // Mengirim permintaan POST ke endpoint login server dengan menggunakan fetch API
            const response = await fetch('http://localhost:8080/api/login/process_login_panitia', {
                method: 'POST', // Metode permintaan adalah POST
                headers: {
                    'Content-Type': 'application/json', // Tipe konten yang dikirimkan adalah JSON
                },
                body: JSON.stringify({ username, password }) // Mengonversi objek ke format JSON dan mengirimkannya sebagai tubuh permintaan
            });

            // Mengonversi respons JSON menjadi objek JavaScript
            const result = await response.json();

            // Memeriksa apakah respons adalah sukses dan jenis layanan adalah "Login Panitia"
            if (response.ok && result.service === "Login Panitia") {
                // Jika login berhasil, ambil token dari respons API
                const token = result.data.token;

                // Simpan token di localStorage untuk digunakan di sesi selanjutnya
                localStorage.setItem('authToken', token);

                // Setelah token diperoleh, arahkan pengguna ke halaman dashboard player
                window.location.href = 'dashboardplayer.html';

            } else {
                // Jika login gagal, tampilkan pesan kesalahan kepada pengguna
                popupMessage.textContent = 'Login gagal: ' + (result.message || 'Periksa kembali username dan password Anda.');
                popup.style.display = 'block';
                popupOverlay.style.display = 'block';
            }
        } catch (error) {
            // Tangani kesalahan jika ada kesalahan saat melakukan permintaan
            popupMessage.textContent = 'Login gagal: ' + error.message;
            popup.style.display = 'block';
            popupOverlay.style.display = 'block';
        }
    });

    // Fungsi untuk menutup popup
    window.closePopup = function() {
        popup.style.display = 'none';
        popupOverlay.style.display = 'none';
    };
});