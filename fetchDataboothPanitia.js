$(document).ready(function() {
    const domain = "http://localhost:8080"
});

// mengambil sesi
var myHeaders = new Headers()
    myHeaders.append("Token", getCookie("Token"))

    fetch(domain + '###########', {
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

// menghapus cookie
function deleteCookie(name) {
    document.cookie = name + '=; Max-age=0; path=/;';
}

// validasi 
$(document).ready(function() {
    $("#booth-form").on("submit", function(event) {
        event.preventDefault();
        let isValid = true;

        // Reset previous errors
        $(".error-message").remove();

        // Validate each input
        $("#booth-form input, #booth-form select, #booth-form textarea").each(function() {
            const $this = $(this);
            if ($this.val() === "") {
                isValid = false;
                showError($this, "Please fill out this field !!!");
            }
        });
    });

    function showError(element, message) {
        const errorMessage = $('<span class="error-message">' + message + '</span>');
        errorMessage.appendTo(element.closest(".input-group"));
    }
});


// fetch
$(document).ready(function() {
    $("#booth-form").on("submit", function(event) {
        event.preventDefault();

        // Reset previous errors
        $(".error-message").remove();

        // Validate form inputs
        let isValid = true;
        $("#booth-form input, #booth-form select, #booth-form textarea").each(function() {
            const $this = $(this);
            if ($this.val() === "") {
                isValid = false;
                showError($this, "Please fill out this field !!!");
            }
        });

        // If form is valid, submit data
        if (isValid) {
            const formData = new FormData(this);

            fetch('URL_API_ANDA', {
                method: 'POST',
                body: formData
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                // Handle response data as needed
                console.log(data);
            })
            .catch(error => {
                console.error('There was a problem with your fetch operation:', error);
            });
        }
    });

    function showError(element, message) {
        const errorMessage = $('<span class="error-message">' + message + '</span>');
        errorMessage.appendTo(element.closest(".input-group"));
    }
});

$(document).ready(function() {
    // Fetch data initially and then every second
    fetchData();
    setInterval(fetchData, 1000); // Fetch data every second
});

function fetchData() {
    // Fetch data for guard 1, guard 2, and room dropdowns simultaneously
    Promise.all([
        fetch('URL_API_ANDA/data-penjaga-booth').then(response => response.json()),
        fetch('URL_API_ANDA/data-penjaga-booth').then(response => response.json()),
        fetch('URL_API_ANDA/data-nama-ruangan').then(response => response.json())
    ])
    .then(([guard1Data, guard2Data, roomData]) => {
        // Populate dropdowns with fetched data
        populateDropdown('#guard1Name', guard1Data);
        populateDropdown('#guard2Name', guard2Data);
        populateDropdown('#noRuangan', roomData);
    })
    .catch(error => {
        console.error('Error fetching data:', error);
    });
}

function populateDropdown(selector, data) {
    const dropdown = $(selector);
    dropdown.empty(); // Clear dropdown before populating
    data.forEach(item => {
        dropdown.append(`<option value="${item.id}">${item.name}</option>`);
    });
}
