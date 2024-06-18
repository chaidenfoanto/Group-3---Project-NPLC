$(document).ready(function () {
    const domain = "http://localhost:8080/"
    $(".sidebar").load("sidebarplayer.html", function () {
        const toggleBtn = $("#toggle-btn, #burger-btn");
        const logo = $(".logo_details .logo").eq(1);
        toggleBtn.on("click", function () {
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

        dice.one('animationend', function () {
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

    rollBtn.click(async function () {
        randomDice();

        await fetch(domain + 'api/listkartu/roll', {
            method: 'GET',
            headers: { 'Token': getCookie('Token') }
        })
            .then(response => response.json())
            .then(data => {
                if (!data.error) {
                    $("#card-code").html("Card " + data.data.noKartu);
                    $("#card-name").html("<strong>Card Name : </strong>" + data.data.cardSkill.namaKartu);
                    $("#card-desc").html(data.data.cardSkill.rules);
                    $("#card-img").prop("src", data.data.cardSkill.gambarKartu);
                    $("#card-img").prop("alt", data.data.cardSkill.namaKartu);
                }
            });
    });

    const openBtn = $("#rollButton");
    const modal = $(".container-dice");
    const modalError = $(".container-error");
    const closeresult = $("#closeResult");

    openBtn.click(async function () {
        await fetch(domain + 'api/listkartu/cardStat', {
            method: 'GET',
            headers: { 'Token': getCookie('Token') }
        })
            .then(response => response.json())
            .then(data => {
                if (!data.error) {
                    if(data.data.cardAvailable > 0) {
                        modal.addClass("open");
                        modal.show();
                        modalError.hide();
                    } else {
                        modalError.addClass("open");
                        modalError.show();
                        modal.hide();
                    }
                    $(".modal-overlay").show();
                }
            });
    });

    closeBtn.click(function () {
        modal.removeClass("open");
        modalError.removeClass("open");
    });

    closeresult.click(function () {
        $(".gacharesult").removeClass("open");
    });

    $("#closeResult, #closeGacha, #closeError").click(function () {
        modal.hide();
        modalError.hide();
        $(".modal-overlay").hide();
    });

    $("#closeResult, #closeError").click(function () {
        location.reload();
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

    function getCookie(name) {
        let cookieArr = document.cookie.split(";");
        for (let i = 0; i < cookieArr.length; i++) {
            let cookiePair = cookieArr[i].split("=");
            if (name == cookiePair[0].trim()) {
            return decodeURIComponent(cookiePair[1]);
            }
        }
        return null;
    }
});