const domain = 'http://localhost:8080/';
$('.close-btn').click(function () {
  closeAllPopups();
});

function closeAllPopups() {
  $('.popup').fadeOut();
}

async function postAnswer(answer, idPertanyaan) {
  const newAnswer = {
    jawaban: answer,
  };

  try {
    const response = await fetch(domain + `api/qna/answer/${idPertanyaan}`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        Token: getCookie('Token'), // Retrieve the session token from cookies
      },
      body: JSON.stringify(newAnswer),
    });

    const data = await response.json();

    if (!data.error) {
      console.log('Answer successfully added');
      // Optionally handle success message or UI state
    } else {
      console.error('Error adding question:', data.message);
      // Optionally handle error message or UI state
    }
  } catch (error) {
    console.error('Error occurred while adding question:', error);
    // Optionally handle error message or UI state
  }
}

function loadQuestions() {
  fetch(domain + 'api/qna/questions', {
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

function renderQuestions(questions) {
  var questionList = $('#questionList');
  questionList.empty();

  questions.forEach(function (question) {
    var questionItem = $('<div class="question-item"></div>');
    var questionTitle = $('<h3></h3>').text(question.namaTeam);
    var questionText = $('<p></p>').text('Question: ' + question.pertanyaan);
    var statusContainer = $('<div class="status-container"></div>');
    var status = $('<span class="status"></span>').text(question.status);
    var seeAnswer = $('<span class="see-answer"></span>').text('See Answer');
    var editAnswer = $('<span class="edit-answer"></span>').text('Answer');

    if (question.status === 'Answered') {
      status.css('color', 'green');
      seeAnswer.click(function () {
        closeAllPopups();
        $('#answerContent').text(question.jawaban);
        $('#answerPopup').fadeIn();
      });
      statusContainer.append(status, seeAnswer);
    } else {
      status.css('color', 'red');
      editAnswer.click(function () {
        closeAllPopups();
        $('#editTeamName').val(question.namaTeam);
        $('#editQuestion').val(question.pertanyaan);
        $('#editAnswer').val(question.jawaban);
        $('#editAnswerPopup').fadeIn();
        $('#idPertanyaan').val(question.idPertanyaan);
      });
      statusContainer.append(status, editAnswer);
    }

    questionItem.append(questionTitle, questionText, statusContainer);
    questionList.append(questionItem);
  });
}

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

/// Function to handle form submission
document.getElementById('editAnswerForm').addEventListener('submit', async function (event) {
  event.preventDefault(); // Prevent the default form submission

  const idPertanyaan = document.getElementById('idPertanyaan').value;
  const answer = document.getElementById('editAnswer').value;

  if (answer) {
    try {
      await postAnswer(answer, idPertanyaan);
      // Refresh the page after successful submission
      window.location.reload();
    } catch (error) {
      console.log('Error submitting answer:', error);
    }
  } else {
    alert('answer is required.');
  }
});
loadQuestions();
closeAllPopups();