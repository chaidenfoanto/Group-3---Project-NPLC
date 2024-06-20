$(document).ready(function() {
    $(".sidebar").load("sidebarketua.html", function() {
        const toggleBtn = $("#toggle-btn, #burger-btn");
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

    $(document).on('click', function (e) {
        if (!$(e.target).closest('.sidebar, #toggle-btn').length) {
          closeSidebar();
        }
    });

    function closeSidebar() {
        $('.sidebar').removeClass('open');
    }

    $('#csvFileInput').on('change', function() {
        const fileName = $(this).val().split('\\').pop();
        $('.file-name').text(fileName ? fileName : 'No file chosen');
    });

    $('#uploadButton').click(function() {
        const fileInput = $('#csvFileInput')[0];
        const file = fileInput.files[0];
        
        if (file) {
            Papa.parse(file, {
                header: true,
                complete: function(results) {
                    displayCSVData(results.data);
                    postCSVData(results.data);
                },
                error: function(error) {
                    console.error("Error parsing CSV:", error);
                }
            });
        } else {
            alert("Please select a CSV file to upload.");
        }
    });

    function displayCSVData(data) {
        const $container = $('#csvDataContainer');
        $container.empty(); // Clear existing data

        const table = $('<table>').addClass('csv-table');
        const thead = $('<thead>');
        const tbody = $('<tbody>');

        if (data.length > 0) {
            // Create table headers
            const headers = Object.keys(data[0]);
            const headerRow = $('<tr>');
            headers.forEach(header => {
                headerRow.append($('<th class="sticky-header">').text(header));
            });
            thead.append(headerRow);

            // Create table rows
            data.forEach(row => {
                const tableRow = $('<tr>');
                headers.forEach(header => {
                    tableRow.append($('<td>').text(row[header]));
                });
                tbody.append(tableRow);
            });
        } else {
            $container.append('<p>No data found in the CSV file.</p>');
        }

        table.append(thead).append(tbody);
        $container.append(table);
    }
});
