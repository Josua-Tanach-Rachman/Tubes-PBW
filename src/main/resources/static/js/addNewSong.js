let arrayArtis = [];
let arrayNegara = [];
let arrayKota = [];
let arrayLokasi = [];
let arrayShow = [];
let arrayAlbum = [];

fetch('/add/setlist/artist')
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.json();
    })
    .then(data => {
        data.forEach(function(artis) {
            arrayArtis.push(artis.namaArtis);
        });

        // Tambahkan opsi "Add New Artist" ke dalam arrayArtis
        arrayArtis.push('Add New Artist');

        console.log(arrayArtis);
    })
    .catch(error => {
        console.error('There was a problem with the fetch operation:', error);
    });

function fetchAlbum(namaArtis) {

    arrayAlbum = [];

    fetch(`/add/song/album?namaArtis=${namaArtis}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            currentSuggestions = [];
            arrayKota = [];

            data.forEach(function(album) {
                arrayAlbum.push(album.namaAlbum);
                console.log(album);
            })
            console.log(arrayKota);
            currentSuggestions = arrayKota;
            console.log(currentSuggestions);

            arrayAlbum.push('Add New Album');

        })
        .catch(error => {
            console.error('There was a problem with the fetch operation:', error);
        });
}

const form = document.getElementById('setlistForm');
const artistInput = document.getElementById('artist-name');
const albumInput = document.getElementById('album');
const popup = document.getElementById('popup');
const overlay = document.getElementById('overlay');
const closeBtn = document.querySelector('.close-btn');
const searchInput = document.getElementById('search-input');
const suggestionsList = document.getElementById('suggestions-list');
const popupTitle = document.getElementById('popup-title');

let activeField = null;
let currentSuggestions = [];
let selectedArtis = '';

// Event Listeners
artistInput.addEventListener('click', () => openPopup('artist'));
albumInput.addEventListener('click', () => openPopup('album'));
closeBtn.addEventListener('click', closePopup);
overlay.addEventListener('click', closePopup);
searchInput.addEventListener('input', handleSearch);
form.addEventListener('submit', validateForm);

async function openPopup(field) {
    activeField = field;
    popup.style.display = 'block';
    overlay.style.display = 'block';

    // Clear search input
    searchInput.value = '';

    // Set appropriate title and suggestions based on field
    switch(field) {
        case 'artist':
            popupTitle.textContent = 'Select Artist';
            searchInput.placeholder = 'Search artists...';
            currentSuggestions = arrayArtis;
            break;
        case 'album':
            if (!selectedArtis) {
                alert('Please select an artist first');
                closePopup();
                return;
            }
            popupTitle.textContent = 'Select Album';
            searchInput.placeholder = 'Search album...';
            currentSuggestions = arrayAlbum;
            break;
    }

    updateSuggestionsList(currentSuggestions);
    searchInput.focus();
}

function handleSearch(e) {
    const searchTerm = e.target.value.toLowerCase();
    let searchList;

    switch(activeField) {
        case 'artist':
            searchList = arrayArtis;
            break;
        case 'album':
            searchList = arrayAlbum;
            break;
    }

    currentSuggestions = searchList.filter(item =>
        item.toLowerCase().includes(searchTerm)
    );

    //kalo hasil search ga ada addnya tambahin
    if (activeField === 'artist' && !currentSuggestions.includes('Add New Artist')) {
        currentSuggestions.push('Add New Artist');
    }

    if (activeField === 'show' && !currentSuggestions.includes('Add New Concert')) {
        currentSuggestions.push('Add New Concert');
    }

    updateSuggestionsList(currentSuggestions);
}

function updateSuggestionsList(suggestions) {
    suggestionsList.innerHTML = '';

    suggestions.forEach(suggestion => {
        const li = document.createElement('li');
        li.textContent = suggestion;
        if (suggestion === 'Add New Artist') {
            li.id = "add";
        }else if (suggestion == 'Add New Album') {
            li.id = "add";}
        li.addEventListener('click', () => {
            if (suggestion === 'Add New Artist') {
                window.location.href = '/addArtist';
            }else if (suggestion == 'Add New Album') {
                window.location.href = '/addAlbum';
            } else {
                selectSuggestion(suggestion);
            }
        });
        suggestionsList.appendChild(li);
    });
}

function selectSuggestion(value) {
    switch(activeField) {
        case 'artist':
            artistInput.value = value;
            selectedArtis = value;
            // Reset dependent fields
            albumInput.value = '';
            console.log("Artist Selected");
            fetchAlbum(selectedArtis)
            break;
        case 'album':
            albumInput.value = value;
            break;
    }
    closePopup();
}

function closePopup() {
    popup.style.display = 'none';
    overlay.style.display = 'none';
    activeField = null;
}

function validateForm(e) {
    e.preventDefault();  // Prevent immediate submission

    if (!artistInput.value) {
        alert('Please select an artist');
        return false;
    }
    if (!albumInput.value) {
        alert('Please select an album');
        return false;
    }

    // Set submission status to 'submitted'
    document.getElementById('submissionStatus').value = 'submitted';

    // Show success notification
    showSuccessNotification('Setlist submitted successfully!');

    // Delay form submission by 1 second
    setTimeout(() => {
        form.submit();
    }, 1000);

    return true;
}



document.addEventListener('keydown', (e) => {
    if (e.key === 'Escape' && activeField) {
        closePopup();
    }
});

function showSuccessNotification(message) {
    const submissionStatus = document.getElementById('submissionStatus').value;

    // Only show the notification if the form was submitted
    if (submissionStatus !== 'submitted') {
        return;
    }

    const notification = document.createElement('div');
    notification.className = 'success-notification';
    notification.textContent = message;

    document.body.appendChild(notification);

    // Auto-remove notification after 3 seconds
    setTimeout(() => {
        notification.remove();
    }, 3000);
}
