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
        Token: getCookie('Token'),
      },
    })
      .then((response) => response.json())
      .then((data) => {
        if (!data.error) {
          const questionList = document.getElementById('questionList');

          questionList.innerHTML = '';

          data.data.forEach((question) => {
            appendQuestion(question);
          });
        } else {
          console.error('Error loading questions:', data.message);
        }
      })
      .catch((error) => {
        console.error('Error occurred while loading questions:', error);
      });
  }

  function appendQuestion(question) {
    var questionItem = $('<div class="question-item"></div>');
    questionItem.append('<h3>' + question.namaTeam + '</h3>');
    questionItem.append('<p>Question: ' + question.pertanyaan + '</p>');

    var statusContainer = $('<div class="status-container"></div>');
    var status = $('<span class="status">' + question.status + '</span>');
    statusContainer.append(status);

    if (question.status === 'Answered') {
      questionItem.addClass('answered');
      var seeAnswer = $('<span class="see-answer">See Answer</span>');
      statusContainer.append(seeAnswer);

      seeAnswer.on('click', function () {
        $('#answerContent').text(question.jawaban);
        $('#answerPopup').fadeIn();
      });
    }

    questionItem.append(statusContainer);
    $('#questionList').append(questionItem);
  }

  loadQuestions();
});
