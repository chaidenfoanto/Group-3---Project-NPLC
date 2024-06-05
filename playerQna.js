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

    const addBox = document.querySelector(".add-box"),
    popupBox = document.querySelector(".popup-box"),
    popupTitle = popupBox.querySelector("header p"),
    closeIcon = popupBox.querySelector(".close-btn"), // Correctly select the close button
    titleTag = popupBox.querySelector("input"),
    descTag = popupBox.querySelector("textarea"),
    addBtn = popupBox.querySelector("button");

    const months = ["January", "February", "March", "April", "May", "June", "July",
                "August", "September", "October", "November", "December"];
    const notes = JSON.parse(localStorage.getItem("notes") || "[]");
    let isUpdate = false, updateId;

    addBox.addEventListener("click", () => {
        popupTitle.innerText = "Welcome Players";
        addBtn.innerText = "Submit";
        popupBox.classList.add("show");
        document.body.style.overflow = "hidden";
        if(window.innerWidth > 660) titleTag.focus();
    });

    closeIcon.addEventListener("click", () => {
        isUpdate = false;
        titleTag.value = descTag.value = "";
        popupBox.classList.remove("show");
        document.body.style.overflow = "auto";
    });

    function showNotes() {
        if(!notes) return;
        document.querySelectorAll(".note").forEach(li => li.remove());
        notes.forEach((note, id) => {
            let filterDesc = note.description.replaceAll("\n", '<br/>');
            let answerText = note.answer ? `<p class="answer">${note.answer}</p>` : '';
            let liTag = `<li class="note">
                            <div class="details">
                                <p>${note.title}</p>
                                <span>${filterDesc}</span>
                                ${answerText}
                            </div>
                            <div class="bottom-content">
                                <span>${note.date}</span>
                                <div class="settings">
                                    <i onclick="showMenu(this)" class="uil uil-ellipsis-h"></i>
                                    <ul class="menu">
                                        <li onclick="updateNote(${id}, '${note.title}', '${filterDesc}')"><i class="uil uil-pen"></i>Edit</li>
                                        <li onclick="deleteNote(${id})"><i class="uil uil-trash"></i>Delete</li>
                                    </ul>
                                </div>
                            </div>
                        </li>`;
            addBox.insertAdjacentHTML("afterend", liTag);
        });
    }
    
    showNotes();

    addBtn.addEventListener("click", e => {
        e.preventDefault();
        let title = titleTag.value.trim(),
        description = descTag.value.trim();

        if(title || description) {
            let currentDate = new Date(),
            month = months[currentDate.getMonth()],
            day = currentDate.getDate(),
            year = currentDate.getFullYear();

            let noteInfo = {title, description, date: `${month} ${day}, ${year}`}
            if(!isUpdate) {
                notes.push(noteInfo);
            } else {
                isUpdate = false;
                notes[updateId] = noteInfo;
            }
            localStorage.setItem("notes", JSON.stringify(notes));
            showNotes();
            closeIcon.click();
        }
    });
});
