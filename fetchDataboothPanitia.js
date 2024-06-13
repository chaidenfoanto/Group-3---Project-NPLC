$(document).ready(function() {
    // Function to validate the form on submit
    $('#booth-form').submit(function(event) {
        event.preventDefault(); // Prevent the form from submitting
        
        // Check if all fields are filled
        if (validateForm()) {
            // If valid, you can proceed with form submission or other actions
            console.log("Form is valid. Proceeding with submission...");
        } else {
            // If not valid, display error message
            $('.error-message').addClass('visible'); // Make error messages visible
        }
    });

    // Function to validate the form fields
    function validateForm() {
        let isValid = true;

        // Validate each input field
        $('.inner-container input, .inner-container textarea, .inner-container select').each(function() {
            if ($(this).val().trim() === '') {
                isValid = false;
                $(this).closest('.input-group').addClass('error-input');
            } else {
                $(this).closest('.input-group').removeClass('error-input');
            }
        });

        return isValid;
    }

    // Event listener to remove error styles when input is focused
    $('.inner-container input, .inner-container textarea, .inner-container select').on('focus', function() {
        $(this).closest('.input-group').removeClass('error-input');
        $('.error-message').removeClass('visible'); // Hide error message on focus
    });
});
