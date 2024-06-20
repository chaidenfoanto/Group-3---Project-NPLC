$(document).ready(function () {
    const domain = 'http://localhost:8080/';
  
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

    async function loadQuestions() {
        await fetch(domain + 'api/qna/questions', {
          method: 'GET',
          headers: {
            Token: getCookie('Token'), // Retrieve the session token from cookies
          },
        })
          .then((response) => response.json())
          .then((data) => {
            if (!data.error) {
              const questions = data.data;
              renderQuestions(questions); // Pass the entire array to renderQuestions
            } else {
              console.error('Error loading questions:', data.message);
            }
          })
          .catch((error) => {
            console.error('Error occurred while loading questions:', error);
          });
      }
  
    // Function to load questions from the server
    function renderQuestions(questions) {
        var questionList = $("#questionList");
        questionList.empty();

        questions.forEach(function(question) {
            var questionItem = $('<div class="question-item"></div>');
            var questionTitle = $('<h3></h3>').text(question.namaTeam);
            var questionText = $('<p></p>').text('Question: ' + question.pertanyaan);
            var statusContainer = $('<div class="status-container"></div>');
            var status = $('<span class="status"></span>').text(question.status);
            var seeAnswer = $('<span class="see-answer"></span>').text('See Answer');
            var editAnswer = $('<span class="edit-answer"></span>').text('Answer');

            if (question.status === "Answered") {
                status.css('color', 'green');
                seeAnswer.click(function() {
                    closeAllPopups();
                    $("#answerContent").text(question.jawaban);
                    $("#answerPopup").fadeIn();
                });
                statusContainer.append(status, seeAnswer);
            } else {
                status.css('color', 'red');
                editAnswer.click(function() {
                    closeAllPopups();
                    $("#editTeamName").val(question.namaTeam);
                    $("#editQuestion").val(question.pertanyaan);
                    $("#editAnswer").val(question.jawaban);
                    $("#editAnswerPopup").fadeIn();
                });
                statusContainer.append(status, editAnswer);
            }

            questionItem.append(questionTitle, questionText, statusContainer);
            questionList.append(questionItem);
        });
    }

    function closeAllPopups() {
        $(".popup").fadeOut();
    }

    $(".close-btn").click(function() {
        closeAllPopups();
    });

    $("#editAnswerForm").submit(function(event) {
        event.preventDefault();
        var teamName = $("#editTeamName").val();
        var question = $("#editQuestion").val();
        var answer = $("#editAnswer").val();

        var questionIndex = questions.findIndex(function(q) {
            return q.teamName === teamName && q.question === question;
        });

        if (questionIndex !== -1) {
            questions[questionIndex].answer = answer;
            questions[questionIndex].status = "Answered";
            renderQuestions();
            $("#editAnswerPopup").fadeOut();
        }
    });

    loadQuestions();
});
