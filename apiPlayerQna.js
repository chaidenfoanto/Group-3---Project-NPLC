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
  async function postQuestion() {
    await fetch(domain + '', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        Token: { Token: getCookie('Token') },
      },
      body: JSON.stringify(newQuestion),
    })
      .then((response) => response.json())
      .then((data) => {
        if (!data.error) {
          // Append the new question item to the question list
          appendQuestion({ teamName: teamName, question: question, answer: '', status: 'Not Answered' });

          // Reset the form
          $('#questionForm')[0].reset();

          // Close the popup
          $('#popup').fadeOut();
        } else {
          // Handle error
          console.error('Error adding question:', data.message);
        }
      })
      .catch((error) => {
        console.error('Error occurred while adding question:', error);
      });
  }

  // Function to load questions from the server
  async function loadQuestions() {
    await fetch(domain + 'api/questions', {
      method: 'GET',
      headers: { Token: getCookie('Token') },
    })
      .then((response) => response.json())
      .then((data) => {
        if (!data.error) {
          data.questions.forEach(appendQuestion);
        } else {
          console.error('Error loading questions:', data.message);
        }
      })
      .catch((error) => {
        console.error('Error occurred while loading questions:', error);
      });
  }

  // Load questions on document ready
  postQuestion();
  loadQuestions();
});
