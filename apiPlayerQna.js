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

  // Send the question to the server
  async function postQuestion(teamName, question) {
    const newQuestion = {
      teamName: teamName,
      question: question,
      answer: '', // Assuming answer is initially empty
      status: 'Not Answered', // Initial status
    };

    await fetch(domain + '/api/qna/ask', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        Token: getCookie('Token'), // Retrieve the session token from cookies
      },
      body: JSON.stringify(newQuestion),
    })
      .then((response) => response.json())
      .then((data) => {
        if (!data.error) {
          console.log('Question successfully added');
          // Append the new question item to the question list
          appendQuestion(newQuestion);

          // Reset the form
          $('#questionForm')[0].reset();

          // Close the popup
          $('#popup').fadeOut();
        } else {
          console.error('Error adding question:', data.message);
        }
      })
      .catch((error) => {
        console.error('Error occurred while adding question:', error);
      });
  }

  // Function to load questions from the server
  async function loadQuestions() {
    await fetch(domain + '/api/qna/questions', {
      method: 'GET',
      headers: {
        Token: getCookie('Token'), // Retrieve the session token from cookies
      },
    })
      .then((response) => response.json())
      .then((data) => {
        if (!data.error) {
          data.questions.forEach((question) => {
            appendQuestion({
              teamName: `${question.namaTeam}`,
              question: `${question.pertanyaan}`,
              answer: `${question.jawaban}`,
              status: question.jawaban ? 'Answered' : 'Not Answered',
            });
          });
        } else {
          console.error('Error loading questions:', data.message);
        }
      })
      .catch((error) => {
        console.error('Error occurred while loading questions:', error);
      });
  }

  // Event listener for form submission
  $('#questionForm').submit(function (event) {
    event.preventDefault(); // Prevent form submission

    // Get the input values
    var teamName = $('#teamName').val();
    var question = $('#question').val();

    // Call postQuestion function to send data to server
    postQuestion(teamName, question);
  });

  // Load questions on document ready
  loadQuestions();
});
