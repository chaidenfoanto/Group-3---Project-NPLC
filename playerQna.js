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

    document.querySelectorAll('.submit-answer').forEach(button => {
        button.addEventListener('click', function() {
            const answerInput = this.previousElementSibling;
            const answerText = answerInput.value.trim();
            
            if (answerText) {
                const answerDiv = document.createElement('div');
                answerDiv.className = 'answer-text';
                answerDiv.textContent = 'Answer: ' + answerText;
                
                const answerContainer = this.parentElement;
                answerContainer.innerHTML = '';
                answerContainer.appendChild(answerDiv);
            }
        });
    });
    
});