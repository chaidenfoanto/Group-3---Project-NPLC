const domain2 = "http://localhost:8080/";

function pad(val) {
  var valString = val + "";
  if (valString.length < 2) {
    return "0" + valString;
  } else {
    return valString;
  }
}

function stopTimer() {
  // Menghentikan timer
  clearInterval(interval);
}

function startTimer() {
  timestart = new Date();
  var timeArray = timer.split(":");
  var hours = parseInt(timeArray[0]);
  var minutes = parseInt(timeArray[1]);
  var seconds = parseInt(timeArray[2]);

  var totalSeconds = hours * 3600 + minutes * 60 + seconds;

  var interval = setInterval(function () {
    totalSeconds--;
    var hours = Math.floor(totalSeconds / 3600);
    var minutes = Math.floor(totalSeconds % 3600 / 60);
    var seconds = totalSeconds % 60;

    $("span.timeleft").text(
      pad(hours) + ":" + pad(minutes) + ":" + pad(seconds)
    );

    if (hours <= 0 && minutes <= 0 && seconds <= 0) {
      clearInterval(interval);
      stopTimer();
    }
  }, 1000);
}

function getTime() {
  fetch(domain2 + "api/statusnplc/getNPLCStatus", {
    method: "GET",
    headers: {
      Token: getCookie("Token"),
    },
  })
    .then((response) => response.json())
    .then((data) => {
      if (!data.error) {
        const durasiPermainanElement = $("span.timeleft");
        var durasiJam = data.data.sisaWaktu.jam.toString();
        var durasiMenit = data.data.sisaWaktu.menit.toString();
        var durasiDetik = data.data.sisaWaktu.detik.toString();
        timer =
          (durasiJam.length < 2 ? "0" + durasiJam : durasiJam) +
          ":" +
          (durasiMenit.length < 2 ? "0" + durasiMenit : durasiMenit) +
          ":" +
          (durasiDetik.length < 2 ? "0" + durasiDetik : durasiDetik);
        durasiPermainanElement.text(timer);
        if(data.data.statusGame == "In Progress") {
          startTimer();
        }
      }
    });
}

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

  $('.form-group select, .form-group input, .popup input, .popup select').each(function () {
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

  $(".edit-btn").click(function() {
    console.log("Masuk");
    $(".popup").addClass("open");
  });

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

  getTime();

  });

  function showModal(idBoothGame) {
    window.location.href = "editDataBoothHOD.html?id=" + idBoothGame
    
  }

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

