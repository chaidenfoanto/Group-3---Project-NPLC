$(document).ready(function () {
  $('.sidebar').load('sidebarpanitia.html', function () {
    const toggleBtn = $('#toggle-btn, #burger-btn');
    const logo = $('.logo_details .logo').eq(1); // Select the second logo
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

  const openBtn = $('#addBtn');
  const editBtn = $('#editBtn');
  const closeBtn = $('#cancel');
  const modal = $('.datacontainer');

  openBtn.click(function () {
    modal.addClass('open');
  });

  editBtn.click(function () {
    modal.addClass('open');
  });

  closeBtn.click(function () {
    modal.removeClass('open');
  });

  $('.inner-container input, .inner-container textarea, .inner-container select').each(function () {
    if ($(this).val() !== '') {
      $(this).addClass('not-empty');
    }

    $(this).on('input', function () {
      if ($(this).val() !== '') {
        $(this).addClass('not-empty');
      } else {
        $(this).removeClass('not-empty');
      }
    });
  });
});
