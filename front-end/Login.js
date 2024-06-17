const container = document.getElementById('container');
const registerBtn = document.getElementById('register');
const loginBtn = document.getElementById('login');
const popup = document.getElementById("popup");
const popupOverlay = document.getElementById('popup-overlay');

registerBtn.addEventListener('click', () => {
    container.classList.add("active");
});

loginBtn.addEventListener('click', () => {
    container.classList.remove("active");
});

function openPopup() {
    popup.classList.add("open-popup");
    popupOverlay.classList.add("active");
    popup.style.zIndex = "1500";
    popupOverlay.style.zIndex = "1000";
}

function closePopup() {
    popup.classList.remove("open-popup");
    popupOverlay.classList.remove("active");
    popup.style.zIndex = "100";
    popupOverlay.style.zIndex = "100";
}

window.openPopup = function (id) {
    const popup = document.getElementById(id);
    popup.classList.add("open-popup");
    popupOverlay.classList.add("active");
    popup.style.zIndex = "1500";
    popupOverlay.style.zIndex = "1000";
}

window.closePopup = function (id) {
    const popup = document.getElementById(id);
    popup.classList.remove("open-popup");
    popupOverlay.classList.remove("active");
    popup.style.zIndex = "100";
    popupOverlay.style.zIndex = "100";
}

function showErrorMessage(inputElement, message) {
    const errorMessageElement = document.getElementById(inputElement.id + 'Error');
    errorMessageElement.textContent = message;
    errorMessageElement.style.display = 'block';
}

function showErrorMessage(message) {
    const errorMessageElement = document.getElementById('loginErrorMessage');
    errorMessageElement.textContent = message;
    errorMessageElement.style.display = 'block';
}

// function hideErrorMessage(inputElement) {
//     const errorMessageElement = document.getElementById(inputElement.id + 'Error');
//     errorMessageElement.textContent = '';
//     errorMessageElement.style.display = 'none';
// }

// function hideErrorMessage() {
//     const errorMessageElement = document.getElementById('loginErrorMessage');
//     errorMessageElement.textContent = '';
//     errorMessageElement.style.display = 'none';
// }



popupOverlay.addEventListener('click', closePopup);
