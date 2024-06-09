$(document).ready(function() {
    $(".sidebar").load("sidebarplayer.html", function() {
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
    const closeBtn = $("#closeGacha");

    const randomDice = () => {
        const random = Math.floor(Math.random() * 10);

        if (random >= 1 && random <= 6) {
            rollDice(random);
        } else {
            randomDice();
        }
    }

    const rollDice = (random) => {
        closeBtn.prop('disabled', true);

        dice.css('animation', 'rolling 4s');

        dice.one('animationend', function() {
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
            $(".container-dice").removeClass("open");
            $(".gacharesult").addClass("open");
            closeBtn.prop('disabled', false);
        });
    }

    rollBtn.click(randomDice);

    const openBtn = $("#rollButton");
    const modal = $(".container-dice");
    const closeresult = $("#closeResult");

    openBtn.click(function() {
        modal.addClass("open");
    });

    closeBtn.click(function() {
        modal.removeClass("open");
    });

    closeresult.click(function() {
        $(".gacharesult").removeClass("open");
    });

    $("#rollButton").click(function() {
        $(".container-dice").show();
        $(".modal-overlay").show();
    });

    $("#closeResult, #closeGacha").click(function() {
        $(".container-dice").hide();
        $(".modal-overlay").hide();
    });
});

function readMore(btn) {
    var card = $(btn).closest('.card');
    var dots = card.find('.dots');
    var moreText = card.find('.more');

    if (dots.css("display") === "none") {
        dots.css("display", "inline");
        $(btn).text("Read more");
        moreText.css("display", "none");
    } else {
        dots.css("display", "none");
        $(btn).text("Read less");
        moreText.css("display", "inline");
    }
}