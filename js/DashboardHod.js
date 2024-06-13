$(document).ready(function() {
  $(".sidebar").load("sidebarHod.html", function() {
      const toggleBtn = $("#toggle-btn");
      const logo = $(".logo_details .logo").eq(1); // Select the second logo
      toggleBtn.on("click", function() {
          $(".sidebar").toggleClass("open");
          menuBtnChange();
      });

      function menuBtnChange() {
          if (sidebar.hasClass("open")) {
              logo.hide();
          } else {
              logo.show();
          }
      }
  });

  $('#toggle-btn').on('click', function() {
    $('.sidebar').toggleClass('open');
    $(this).toggleClass('burger-icon');
});

    $('#addGameButton').on('click', function() {
        window.location.href = 'databoothpanitiahod.html'; // Replace with the actual target page
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

  $(".edit-btn").click(function() {
    window.location.href = 'databoothpanitiahod.html';
  });

//   $(".close-btn").click(function() {
//       $("#popup").css("display", "none");
//   });

//   $(window).click(function(event) {
//       if ($(event.target).is("#popup")) {
//           $("#popup").css("display", "none");
//       }
//   });

  adjustMainContent();
});
