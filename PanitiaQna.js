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

    function showModal(title, content) {
        document.getElementById('modal-title').textContent = title;
        document.getElementById('modal-content').textContent = content;
        document.getElementById('question-modal').style.display = 'block';
    }

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

    // Add event listeners to questions
    document.querySelectorAll('.question').forEach(question => {
        question.addEventListener('click', () => {
            const title = question.getAttribute('data-title');
            const content = question.getAttribute('data-content');
            showModal(title, content);
        });
    });

    // Handle submit answer
    document.getElementById('submit-answer').addEventListener('click', () => {
        const answer = document.getElementById('answer-input').value;
        console.log(`Answer submitted: ${answer}`);
        // You can add your AJAX call here to send the answer to the server
        document.getElementById('question-modal').style.display = 'none';
    });
});
