$(document).ready(function() {
    const domain = 'http://localhost:8080/';
    $('.sidebar').load('sidebarpanitia.html', function () {
      const toggleBtn = $('#toggle-btn');
      const logo = $('.logo_details .logo').eq(1);
      toggleBtn.on('click', function () {
        $('.sidebar').toggleClass('open');
        menuBtnChange();
      });
  
      function menuBtnChange() {
        if (sidebar.hasClass('open')) {
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
        // $('.main-content').removeClass('shift');
      }

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

    $('#profile-form').on('submit', function(event) {
        // Clear previous error messages
        $('.error-message').text('');

        var currentPassword = $('#currentPassword').val();
        var newPassword = $('#newPassword').val();
        var retypeNewPassword = $('#retypeNewPassword').val();

        var hasError = false;

        // Validasi jika current password kosong
        if (currentPassword === '') {
            $('#currentPasswordError').text('Password saat ini diperlukan.');
            hasError = true;
        }

        // Validasi jika password baru tidak sesuai dengan konfirmasi
        if (newPassword !== retypeNewPassword) {
            $('#newPasswordError').text('Password baru tidak sesuai.');
            $('#retypeNewPasswordError').text('Password baru tidak sesuai.');
            hasError = true;
        }

        // Jika ada error, cegah form untuk dikirim
        if (hasError) {
            event.preventDefault();
        }
    });
});
