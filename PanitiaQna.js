$(document).ready(function() {
    // Clear the local storage to remove all notes
    localStorage.removeItem("notes");

    $(".sidebar").load("sidebarplayer.html", function() {
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
    $(document).ready(function() {
        const questionList = document.querySelector(".question-list"),
        popupBox = document.querySelector(".popup-box"),
        closeIcon = popupBox.querySelector("header i"),
        questionText = popupBox.querySelector("#question-text"),
        answerText = popupBox.querySelector("#answer-text"),
        submitAnswerBtn = popupBox.querySelector("#submit-answer");
    
        // Example questions from players (replace this with actual data fetching logic)
        const questions = JSON.parse(localStorage.getItem("questions") || "[]");
    
        function showQuestions() {
            if(!questions) return;
            document.querySelectorAll(".question").forEach(li => li.remove());
            questions.forEach((question, id) => {
                let liTag = `<li class="question" data-id="${id}">
                                <p>${question.title}</p>
                                <span>${question.description}</span>
                            </li>`;
                questionList.insertAdjacentHTML("beforeend", liTag);
            });
        }
        showQuestions();
    
        questionList.addEventListener("click", function(e) {
            if(e.target.tagName === "LI" || e.target.closest("li")) {
                const questionId = e.target.closest("li").dataset.id;
                openPopup(questionId);
            }
        });
    
        function openPopup(id) {
            const question = questions[id];
            questionText.value = question.description;
            answerText.value = '';
            submitAnswerBtn.dataset.id = id;
            popupBox.classList.add("show");
            document.querySelector("body").style.overflow = "hidden";
        }
    
        closeIcon.addEventListener("click", () => {
            popupBox.classList.remove("show");
            document.querySelector("body").style.overflow = "auto";
        });
    
        submitAnswerBtn.addEventListener("click", (e) => {
            e.preventDefault();
            const id = e.target.dataset.id;
            const answer = answerText.value.trim();
            if(answer) {
                questions[id].answer = answer;
                localStorage.setItem("questions", JSON.stringify(questions));
                popupBox.classList.remove("show");
                document.querySelector("body").style.overflow = "auto";
                showQuestions();
            }
        });
    });
    
});
