$(document).ready(function() {
    $(".sidebar").load("sidebarpanitia.html", function() {
        const toggleBtn = $("#toggle-btn");
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

