$(document).ready(function() {
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
    document.getElementById('add-question').addEventListener('click', () => {
        document.getElementById('question-modal').style.display = 'block';
    });

    // Close modal
    document.querySelector('.close-button').addEventListener('click', () => {
        document.getElementById('question-modal').style.display = 'none';
    });

    // Close modal when clicking outside of it
    window.addEventListener('click', (event) => {
        if (event.target === document.getElementById('question-modal')) {
            document.getElementById('question-modal').style.display = 'none';
        }
    });

    // Handle submit question
    document.getElementById('submit-question').addEventListener('click', () => {
        const title = document.getElementById('question-title').value;
        const content = document.getElementById('question-content').value;

        if (title && content) {
            const questionContainer = document.getElementById('questions-container');
            
            const newQuestion = document.createElement('div');
            newQuestion.classList.add('question');
            newQuestion.innerHTML = `
                <div class="question-footer">
                    <span> New Question </span>
                </div>
                <h3>${title}</h3>
                <p>${content}</p>
            `;
            newQuestion.addEventListener('click', () => {
                showModal(title, content);
            });

            questionContainer.appendChild(newQuestion);

            document.getElementById('question-title').value = '';
            document.getElementById('question-content').value = '';
            document.getElementById('question-modal').style.display = 'none';
        } else {
            alert('Please fill out both the title and content.');
        }
    });

    // Show modal for viewing question
    function showModal(title, content) {
        document.getElementById('modal-title').textContent = title;
        document.getElementById('modal-content').textContent = content;
        document.getElementById('question-modal').style.display = 'block';
    }
});
