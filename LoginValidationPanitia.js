document.addEventListener('DOMContentLoaded', function () {
    const domain = "http://localhost:8080"
    const loginForm = document.querySelector('.form-container.log-in form');
    const popupOverlay = document.getElementById('popup-overlay');
    
    loginForm.addEventListener('submit', async (event) => {
        event.preventDefault();
        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;

        try {
            const response = await fetch(domain + '/api/login/process_login_panitia', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ username, password })
            });

            const result = await response.json();

            if (response.ok && result.message === "Login Success") {
                const token = result.data.token;
                setCookie("Token", token, 365);
                switch(result.data.role){
                    case "Admin" :
                        window.location.href = "DashboardHod.html";
                        break;
                    case "Ketua" :
                        window.location.href = "HodStartGame.html"
                        break;
                    case "Penjaga Single" :
                        window.location.href = "PanitiaSingleInput.html"
                        break;
                    case "Penjaga Duel" :
                        window.location.href = "PanitiaDuelFinish.html"
                        break;
                }

                // window.location.href = "DashboardHod.html";
            } else {
                console.log('Login gagal: ' + (result.message || 'Periksa kembali username dan password Anda.'));
                openPopup('popup-wrong');
            }
        } catch (error) {
            showErrorMessage('Login gagal: Harap isi username dan password' );
        }
        popupOverlay.addEventListener('click', function() {
            closePopup('popup-wrong');
        });
    });

    function deleteCookie(name) {
        document.cookie = name + '=; Max-Age=-99999999;';
    }

    function setCookie(name, value, daysToLive) {
        let cookie = name + "=" + encodeURIComponent(value);
        if (typeof daysToLive === "number") {
            cookie += "; max-age=" + (daysToLive*24*60*60) + "; path=/";
            document.cookie = cookie;
        }
    }

    deleteCookie("Token");
    
    //     const fileInputs = inputs.filter(input => input.type === 'file');
    //     const anyFileUploaded = fileInputs.some(input => input.files[0]);
    //     if (!anyFileUploaded && !hasError) {
    //         fileInputs.forEach(input => {
    //             showErrorMessage(input, 'Harap pilih file foto.');
    //         });
    //         isValid = false;
    //     }
    
    //     return isValid;
    // }
    

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
    
});