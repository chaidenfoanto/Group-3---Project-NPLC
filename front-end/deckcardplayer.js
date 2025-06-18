$(document).ready(function () {
  const domain = 'http://localhost:8080/';
  $('.sidebar').load('sidebarplayer.html', function () {
    const toggleBtn = $('#toggle-btn, #burger-btn');
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

  const popup = document.getElementById("popup");
  const popupOverlay = document.getElementById('popup-overlay');

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

  $(document).on('click', function (e) {
    if (!$(e.target).closest('.sidebar, #toggle-btn').length) {
      closeSidebar();
    }
  });

  function closeSidebar() {
    $('.sidebar').removeClass('open');
    // $('.main-content').removeClass('shift');
  }

  const dice = $('.dice');
  const rollBtn = $('.roll');
  const closeBtn = $('#closeGacha');

  const randomDice = () => {
    const random = Math.floor(Math.random() * 10);

    if (random >= 1 && random <= 6) {
      rollDice(random);
    } else {
      randomDice();
    }
  };

  const rollDice = (random) => {
    closeBtn.prop('disabled', true);

    dice.css('animation', 'rolling 4s');

    dice.one('animationend', async function () {
      switch (random) {
        case 1:
          dice.css('transform', 'rotateX(0deg) rotateY(0deg)');
          break;
        case 6:
          dice.css('transform', 'rotateX(180deg) rotateY(0deg)');
          break;
        case 2:
          dice.css('transform', 'rotateX(-90deg) rotateY(0deg)');
          break;
        case 5:
          dice.css('transform', 'rotateX(90deg) rotateY(0deg)');
          break;
        case 3:
          dice.css('transform', 'rotateX(0deg) rotateY(90deg)');
          break;
        case 4:
          dice.css('transform', 'rotateX(0deg) rotateY(-90deg)');
          break;
        default:
          break;
      }

      dice.css('animation', 'none');
      await fetch(domain + 'api/listkartu/roll', {
        method: 'GET',
        headers: { Token: getCookie('Token') },
      })
        .then((response) => response.json())
        .then((data) => {
          if (!data.error) {
            $('#card-code').html('Card ' + data.data.noKartu);
            $('#card-name').html('<strong>Card Name : </strong>' + data.data.cardSkill.namaKartu);
            $('#card-desc').html(data.data.cardSkill.rules);
            $('#card-img').prop('src', data.data.cardSkill.gambarKartu);
            $('#card-img').prop('alt', data.data.cardSkill.namaKartu);
          }
        });
      $('.container-dice').removeClass('open');
      $('.gacharesult').addClass('open');
      closeBtn.prop('disabled', false);
      
    });
  };

  rollBtn.click(function () {
    randomDice();
  });

  const openBtn = $('#rollButton');
  const modal = $('.container-dice');
  const closeresult = $('#closeResult');

  openBtn.click(async function () {
    await fetch(domain + 'api/listkartu/cardStat', {
      method: 'GET',
      headers: { Token: getCookie('Token') },
    })
      .then((response) => response.json())
      .then((data) => {
        if (!data.error) {
          if (data.data.cardAvailable > 0) {
            modal.addClass('open');
            modal.show();
          } else {
            openPopup('popup-wrong');
          }
          $('.modal-overlay').show();
        }
      });
  });

  closeBtn.click(function () {
    modal.removeClass('open');
  });

  closeresult.click(function () {
    $('.gacharesult').removeClass('open');
  });

  $('#closeResult, #closeGacha, #closeError').click(function () {
    modal.hide();
    $('.modal-overlay').hide();
  });

  $('#closeResult').click(function () {
    location.reload();
  });

  function getCookie(name) {
    let cookieArr = document.cookie.split(';');
    for (let i = 0; i < cookieArr.length; i++) {
      let cookiePair = cookieArr[i].split('=');
      if (name == cookiePair[0].trim()) {
        return decodeURIComponent(cookiePair[1]);
      }
    }
    return null;
  }
});

function readMore(btn) {
  var card = $(btn).closest('.card');
  var dots = card.find('.dots');
  var moreText = card.find('.more');

  if (dots.css('display') === 'none') {
    dots.css('display', 'inline');
    $(btn).text('Read more');
    moreText.css('display', 'none');
  } else {
    dots.css('display', 'none');
    $(btn).text('Read less');
    moreText.css('display', 'inline');
  }
}
