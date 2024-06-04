$(document).ready(function() {
    $("#booth-form").on("submit", function(event) {
        event.preventDefault();
        let isValid = true;

        // Reset previous errors
        $(".error-message").remove();

        // Validate each input
        $("#booth-form input, #booth-form select, #booth-form textarea").each(function() {
            const $this = $(this);
            if ($this.val() === "") {
                isValid = false;
                showError($this, "Please fill out this field !!!");
            }
        });
    });

    function showError(element, message) {
        const errorMessage = $('<span class="error-message">' + message + '</span>');
        errorMessage.appendTo(element.closest(".input-group"));
    }
});
