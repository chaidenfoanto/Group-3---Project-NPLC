$(document).ready(function () {
  $('.sidebar').load('sidebarHod.html', function () {
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

  $('.form-group select, .form-group input').each(function () {
    // Check if the input is not empty on page load
    if ($(this).val() !== '') {
      $(this).addClass('not-empty');
      $('.input-group .error').text('');
    }

    $(this).on('input', function () {
      if ($(this).val() !== '') {
        $(this).addClass('not-empty');
        $('.input-group .error').text('');
      } else {
        $(this).removeClass('not-empty');
        // $('.input-group .error').text('Winning team should be filled.');
      }
    });
  });

  const showbtn = $("#showPopup");

  showbtn.click(function () {
    showModal();
  });

  function showModal() {
    $(".popup").toggleClass("open");
  }

  $(".close-btn").click(function() {
    $(".popup").removeClass("open");
  });

  $('#addGameButton').on('click', function () {
    window.location.href = 'databoothpanitiahod.html'; // Replace with the actual target page
    $('.sidebar').load('sidebarHod.html', function () {
      const toggleBtn = $('#toggle-btn, #burger-btn');
      const logo = $('.logo_details .logo').eq(1); // Select the second logo
      toggleBtn.on('click', function () {
        $('.sidebar').toggleClass('open');
        menuBtnChange();
      });
    });
  });

  $('#toggle-btn').on('click', function () {
    $('.sidebar').toggleClass('open');
    $(this).toggleClass('burger-icon');
  });

    // $('.edit-btn').click(function () {
    //     window.location.href = 'databoothpanitiahod.html';
    //   });
    
    
        // $(window).click(function(event) {
        //     if ($(event.target).is("#popup")) {
        //         $("#popup").css("display", "none");
        //     }
        // });

        

  });

  //   function adjustMainContent() {
  //       if ($(".sidebar").hasClass("open")) {
  //           $(".main-content").css({
  //               "margin-left": "250px",
  //               "width": "calc(100% - 250px)"
  //           });
  //       } else {
  //           $(".main-content").css({
  //               "margin-left": "80px",
  //               "width": "calc(100% - 80px)"
  //           });
  //       }
  //   }

  //   $(window).resize(function() {
  //       if ($(window).width() <= 768) {
  //           $(".main-content").css({
  //               "margin-left": "0",
  //               "width": "100%"
  //           });
  //           if ($(".sidebar").hasClass("open")) {
  //               $(".main-content").css({
  //                   "margin-left": "250px",
  //                   "width": "calc(100% - 250px)"
  //               });
  //           }
  //       } else {
  //           adjustMainContent();
  //       }
  //   });

  //   $(".close-btn").click(function() {
  //       $("#popup").css("display", "none");
  //   });

  //   $(window).click(function(event) {
  //       if ($(event.target).is("#popup")) {
  //           $("#popup").css("display", "none");
  //       }
  //   });

  // adjustMainContent();

