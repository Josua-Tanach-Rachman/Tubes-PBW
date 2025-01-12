
let arraySong = [];
let arraySongAsli = [];
let teks = "";


    fetch('/add/setlist/lagu')
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            data.forEach(function(song) {
                arraySong.push(song.namaLagu);
                arraySongAsli.push(song.namaLagu);
            });

            // Tambahkan opsi "Add New Artist" ke dalam arrayArtis
            arraySong.push('Add New Song');

            console.log(arraySong);
        })
        .catch(error => {
            console.error('There was a problem with the fetch operation:', error);
        }
    );


// Create an array to store the song names
let songNames = [];

function checkSong(value){
    arraySong = arraySongAsli.slice();
    console.log(arraySongAsli);
    console.log(arraySong);
    songNames = [];
    // Select all <p> elements inside the .artist-list
    let songElements = document.querySelectorAll('.artist-list p');
    // Loop through each <p> element and get its text content
    songElements.forEach(song => {
        songNames.push(song.textContent);
    });
    console.log(songNames);
    console.log("Song" + value);
    let ctr = 0;
    songNames.forEach(song => {
        // Find the index of the song in arraySong
        if(value === song && ctr == 0){
            console.log("songnya dipilih");
            ctr = 1;
        }
        else{
            const songIndex = arraySong.indexOf(song);
            console.log(songIndex);
        
            // If the song exists in arraySong, remove it
            if (songIndex !== -1) {
                arraySong.splice(songIndex, 1);  // Remove the song at the found index
            }
            console.log(arraySong);
        }
    });
}


const popup = document.getElementById('popup');
const overlay = document.getElementById('overlay');
const closeBtn = document.querySelector('.close-btn');
const searchInput = document.getElementById('search-input');
const suggestionsList = document.getElementById('suggestions-list');
const popupTitle = document.getElementById('popup-title');

const songInput = document.getElementById('artist-name');
const buttonAdd = document.querySelector("#addSong");
buttonAdd.addEventListener('click', () => {
    checkSong();
    openPopup('artist');
});
closeBtn.addEventListener('click', closePopup);
overlay.addEventListener('click', closePopup);
searchInput.addEventListener('input', handleSearch);

async function openPopup(field, edit) {
    activeField = field;
    popup.style.display = 'block';
    overlay.style.display = 'block';
    
    // Clear search input
    searchInput.value = '';

    popupTitle.textContent = 'Select Song';
    searchInput.placeholder = 'Search songs...';
    currentSuggestions = arraySong;

    updateSuggestionsList(currentSuggestions ,edit);
    searchInput.focus();
}

function handleSearch(e) {
    const searchTerm = e.target.value.toLowerCase();
    let searchList;
    searchList = arraySong;

    currentSuggestions = searchList.filter(item => 
        item.toLowerCase().includes(searchTerm)
    );

    if (!currentSuggestions.includes('Add New Song')) {
        currentSuggestions.push('Add New Song');
    }
    updateSuggestionsList(currentSuggestions);
}

function updateSuggestionsList(suggestions, edit) {
    suggestionsList.innerHTML = '';
    
    suggestions.forEach(suggestion => {
        const li = document.createElement('li');
        li.textContent = suggestion;
        if (suggestion === 'Add New Song') {
            li.id = "add";      
        }
        li.addEventListener('click', () => {
            if (suggestion === 'Add New Song') {
                window.location.href = '/addSong';         
            } else {
                selectSuggestion(suggestion,edit);
            }
        });
        suggestionsList.appendChild(li);
    });
}

function selectSuggestion(value, edit) {
    teks = value;
    console.log(teks);
    const songIndex = arraySong.indexOf(value);
    if (songIndex !== -1) {
        arraySong.splice(songIndex, 1);  // Remove the song at the found index
    }

    // Now update the suggestion list after removal
    updateSuggestionsList(arraySong);
    // Add the "Add New Song" option if it isn't already in the list
    if (!arraySong.includes('Add New Song')) {
        arraySong.push('Add New Song');
    }
    createElement(edit);
    closePopup();
}

function closePopup() {
    popup.style.display = 'none';
    overlay.style.display = 'none';
    activeField = null;
}

document.addEventListener('keydown', (e) => {
    if (e.key === 'Escape' && activeField) {
        closePopup();
    }
});

// Get the button and container elements
const songContainer = document.querySelector(".artist-list");

function createElement(edit){
    if(edit){
        let parentElement = edit.target.parentElement;
        let previousSibling = parentElement.previousElementSibling;
        previousSibling.textContent = teks;
        previousSibling.previousElementSibling.value = teks;
    }
    else{
        let newLi = document.createElement("li");
        let newDiv = document.createElement("div");
        newDiv.classList.add("list-item");
        let newSong = document.createElement("p");
        newSong.textContent = teks;

        let newInput = document.createElement("input");
        newInput.type = "hidden";
        newInput.name = "songNames";
        newInput.value = teks;
    
        // Create the outer div with class "actions"
        const actionsDiv = document.createElement("div");
        actionsDiv.classList.add("actions");
    
        // Create the edit icon image
        const editIcon = document.createElement("img");
        editIcon.src = "/assets/edit.png";  // Set the source of the image
        editIcon.alt = "edit-icon";        // Alt text for the image
        editIcon.classList.add("icon");   // Add the class for styling
    
        // Create the edit song link
        const editLink = document.createElement("a");
        editLink.classList.add("edit-song");  // Add a class for styling
        editLink.innerText = "Edit this song"; // Link text

        editLink.addEventListener("click",(event) =>{
            let parentElement = event.target.parentElement;
            let previousSibling = parentElement.previousElementSibling;
            let text = previousSibling.textContent;
    
            checkSong(text);
            openPopup('artist',event);
        })
    
        // Create the delete icon image
        const deleteIcon = document.createElement("img");
        deleteIcon.src = "/assets/delete.png";  // Set the source of the image
        deleteIcon.alt = "remove-icon";        // Alt text for the image
        deleteIcon.classList.add("icon");     // Add the class for styling
    
        // Create the remove song link
        const removeLink = document.createElement("a");
        removeLink.classList.add("remove-song");  // Add a class for styling
        removeLink.innerText = "Remove this song"; // Link text

        removeLink.addEventListener("click",(event) =>{
            let parentElement = event.target.parentElement;
            let previousSibling = parentElement.previousElementSibling;
            let listItem = previousSibling.parentElement;
            let li = listItem.parentElement;

            li.remove();
        })
    
        // Append the icons and links to the actions div
        actionsDiv.appendChild(editIcon);
        actionsDiv.appendChild(editLink);
        actionsDiv.appendChild(deleteIcon);
        actionsDiv.appendChild(removeLink);
    
        newDiv.appendChild(newInput);
        newDiv.appendChild(newSong);
        newDiv.appendChild(actionsDiv);
        newLi.appendChild(newDiv);
        songContainer.appendChild(newLi);
    }
}

let editSong = document.querySelectorAll(".edit-song");
let deleteSong = document.querySelectorAll(".remove-song");

editSong.forEach(song =>{
    song.addEventListener("click",(event) =>{
        let parentElement = event.target.parentElement;
        let previousSibling = parentElement.previousElementSibling;
        let text = previousSibling.textContent;

        checkSong(text);
        openPopup('artist',event);
    })
})

deleteSong.forEach(song =>{
    song.addEventListener("click",(event) =>{
        let parentElement = event.target.parentElement;
        let previousSibling = parentElement.previousElementSibling;
        let listItem = previousSibling.parentElement;
        let li = listItem.parentElement;

        li.remove();
    })
})

deleteSong.forEach(song => {
    song.addEventListener("click", (event) => {
        const parentElement = event.target.parentElement;
        const previousSibling = parentElement.previousElementSibling;
        const listItem = previousSibling.parentElement;
        const li = listItem.parentElement;

        li.remove();
        // Check if there are remaining songs
        const songElements = document.querySelectorAll('.artist-list input[name="songNames"]');
        if (songElements.length === 0) {
            // Ensure the default input is visible
            document.getElementById('defaultSongInput').value = 'No Songs';
        }
    });
});