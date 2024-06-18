$(document).ready(function() {
    $(".sidebar").load("sidebarpanitia.html", function() {
        const toggleBtn = $("#toggle-btn, #burger-btn");
        const logo = $(".logo_details .logo").eq(1); // Select the second logo
        toggleBtn.on("click", function() {
            $(".sidebar").toggleClass("open");
            menuBtnChange();
        });

        function menuBtnChange() {
            if ($(".sidebar").hasClass("open")) {
                logo.hide();
            } else {
                logo.show();
            }
        }
    });

    $(document).on('click', function (e) {
        if (!$(e.target).closest('.sidebar, #toggle-btn').length) {
          closeSidebar();
        }
    });

    function closeSidebar() {
        $('.sidebar').removeClass('open');
    }

    const openBtn = $("#addBtn");
    const editBtn = $("#editBtn");
    const closeBtn = $("#cancel");
    const modal = $(".datacontainer");

    openBtn.click(function() {
        modal.addClass("open");
    });

    editBtn.click(function() {
        modal.addClass("open");
    });

    closeBtn.click(function() {
        modal.removeClass("open");
    });

    $('.inner-container input, .inner-container textarea, .inner-container select').each(function() {
        // Check if the input is not empty on page load
        if ($(this).val() !== '') {
            $(this).addClass('not-empty');
        }

        // Add event listener for input events
        $(this).on('input', function() {
            if ($(this).val() !== '') {
                $(this).addClass('not-empty');
            } else {
                $(this).removeClass('not-empty');
            }
        });
    });

    $('#imageInput').change(function() {
        const file = this.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = function(e) {
                $('#cardImage').attr('src', e.target.result);
                $("#fileSource").text(`File source: ${file.name}`);
            }
            reader.readAsDataURL(file);
        }
    });

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
    boothForm.addEventListener('submit', function (event) {
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

// function readMore(btn) {
//     var card = $(btn).closest('.card');
//     var dots = card.find('.dots');
//     var moreText = card.find('.more');

//     if (dots.css("display") === "none") {
//         dots.css("display", "inline");
//         $(btn).text("Read more");
//         moreText.css("display", "none");
//     } else {
//         dots.css("display", "none");
//         $(btn).text("Read less");
//         moreText.css("display", "inline");
//     }
// }

