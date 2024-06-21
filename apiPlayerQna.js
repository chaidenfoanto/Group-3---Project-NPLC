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

  document.getElementById('questionForm').addEventListener('submit', async function (event) {
    event.preventDefault();

    const question = document.getElementById('question').value;

    if (question) {
      try {
        await postQuestion(question);

        window.location.reload();
      } catch (error) {
        console.log('Error submitting question:', error);
      }
    } else {
      alert('Question is required.');
    }
  });

  async function postQuestion(question) {
    const newQuestion = {
      pertanyaan: question,
    };

    try {
      const response = await fetch(domain + 'api/qna/ask', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          Token: getCookie('Token'),
        },
        body: JSON.stringify(newQuestion),
      });

      const data = await response.json();

      if (!data.error) {
        console.log('Question successfully added');
      } else {
        console.error('Error adding question:', data.message);
      }
    } catch (error) {
      console.error('Error occurred while adding question:', error);
    }
  }

  // Function to append a new question item to the question list
  function appendQuestion(question) {
    var questionItem = $('<div class="question-item"></div>');
    questionItem.append('<h3>' + question.namaTeam + '</h3>');
    questionItem.append('<p>Question: ' + question.pertanyaan + '</p>');

    var statusContainer = $('<div class="status-container"></div>');
    var status = $('<span class="status">' + question.status + '</span>');
    statusContainer.append(status);

    if (question.status === 'Answered') {
      questionItem.addClass('answered'); // Add class for answered questions
      var seeAnswer = $('<span class="see-answer">See Answer</span>');
      statusContainer.append(seeAnswer);

      // Add click event to "See Answer"
      seeAnswer.on('click', function () {
        $('#answerContent').text(question.jawaban);
        $('#answerPopup').fadeIn();
      });
    }

    questionItem.append(statusContainer);
    $('#questionList').append(questionItem);
  }

  // Load questions on document ready
  loadQuestions();
});
