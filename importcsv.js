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
        if (!$(e.target).closest('.sidebar, #toggle-btn, #burger-btn').length) {
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

    function generatePassword(name) {
        const names = name.split(' ');
        const firstName = names[0].trim();
        const lastNameInitial = names.length > 1 ? names[names.length - 1].charAt(0).trim() : '';
        return (firstName + lastNameInitial).toLowerCase();
    }

    function postCSVData() {
        const input = $('csvFileInput');
        if ('files' in input && input.files.length > 0) {
            const file = input.files[0];
            const reader = new FileReader();
            reader.onload = function(e) {
                const text = e.target.result;
                const rows = text.split('\n');
                rows.forEach((row) => {
                    const columns = row.split(',');
                    const data = {
                        idPanitia: columns[0].trim(),
                        angkatan: parseInt(columns[1].trim()),
                        divisi: columns[2].trim(),
                        nama: columns[3].trim(),
                        spesialisasi: columns[4].trim(),
                        username: columns[5].trim(),
                        passusr: generatePassword(name),
                        isAdmin: 0
                    };
                    fetch('/api/panitia', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        body: JSON.stringify(data)
                    })
                    .then(response => response.json())
                    .then(data => console.log('Success:', data))
                    .catch(error => console.error('Error:', error));
                });
            };
            reader.readAsText(file);
        }
    }

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
