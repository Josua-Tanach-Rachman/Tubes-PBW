const mockData = {
    songs: ['Bohemian Rhapsody', 'Shape of You', 'Blinding Lights', 'Hotel California', 'Take On Me', 'Rolling in the Deep', 'Smells Like Teen Spirit'],
};

let arrayArtis = [];
let arraySong = [];

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

// DOM Elements
const form = document.getElementById('setlistForm');
const artistInput = document.getElementById('artist-name');
const countryInput = document.getElementById('country');
const cityInput = document.getElementById('city');
const venueInput = document.getElementById('venue');
const showInput = document.getElementById('show');
const popup = document.getElementById('popup');
const overlay = document.getElementById('overlay');
const closeBtn = document.querySelector('.close-btn');
const searchInput = document.getElementById('search-input');
const suggestionsList = document.getElementById('suggestions-list');
const popupTitle = document.getElementById('popup-title');

let activeField = null;
let currentSuggestions = [];
let selectedCountry = '';
let selectedCity = '';
let selectedLocation = '';

// Event Listeners
artistInput.addEventListener('click', () => openPopup('artist'));
closeBtn.addEventListener('click', closePopup);
overlay.addEventListener('click', closePopup);
searchInput.addEventListener('input', handleSearch);
form.addEventListener('submit', validateForm);

// Functions
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
        case 'country':
            popupTitle.textContent = 'Select Country';
            searchInput.placeholder = 'Search countries...';
            currentSuggestions = arrayNegara;
            break;
        case 'city':
            if (!selectedCountry) {
                alert('Please select a country first');
                closePopup();
                return;
            }
            popupTitle.textContent = 'Select City';
            searchInput.placeholder = 'Search cities...';
            currentSuggestions = arrayKota;
            break;
        case 'venue':
            if (!selectedCountry || !selectedCity) {
                alert('Please select both country and city first');
                closePopup();
                return;
            }
            popupTitle.textContent = 'Select Venue';
            searchInput.placeholder = 'Search venues...';
            currentSuggestions = arrayLokasi;
            break;
        case 'show':
            if (!selectedCountry || !selectedCity || !selectedLocation) {
                alert('Please select both country, city, and location first');
                closePopup();
                return;
            }
            popupTitle.textContent = 'Select Venue';
            searchInput.placeholder = 'Search venues...';
            currentSuggestions = arrayShow;
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
        case 'country':
            searchList = arrayNegara;
            break;
        case 'city':
            searchList = arrayKota;
            break;
        case 'venue':
            searchList = arrayLokasi;
            break;
        case 'show':
            searchList = arrayShow;
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
        }else if (suggestion == 'Add New Concert') {
            li.id = "add";}
        li.addEventListener('click', () => {
            if (suggestion === 'Add New Artist') {
                window.location.href = '/addArtist';
            }else if (suggestion == 'Add New Concert') {
                window.location.href = '/addConcert';
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
            break;
        case 'country':
            countryInput.value = value;
            selectedCountry = value;
            // Reset dependent fields
            cityInput.value = '';
            venueInput.value = '';
            selectedCity = '';
            selectedLocation = ''; // Reset location
            showInput.value = ''; // Reset show
            console.log("Country selected");
            fetchKota(selectedCountry);
            break;
        case 'city':
            cityInput.value = value;
            selectedCity = value;
            // Reset venue
            venueInput.value = '';
            selectedLocation = ''; // Reset location
            showInput.value = ''; // Reset show
            console.log("City selected");
            fetchLokasi(selectedCity);
            break;
        case 'venue':
            venueInput.value = value;
            selectedLocation = value;
            // Reset show
            showInput.value = '';
            console.log("Venue selected");
            fetchShow(selectedLocation);
            break;
        case 'show':
            showInput.value = value;
            console.log("Show selected: " + value);
            break;
    }
    closePopup();
}

function closePopup() {
    popup.style.display = 'none';
    overlay.style.display = 'none';
    activeField = null;
}

function handleSubmit(e) {
    e.preventDefault();

    if (!validateForm()) {
        return;
    }

    const formData = {
        artist: artistInput.value,
        country: countryInput.value,
        city: cityInput.value,
        venue: venueInput.value,
        // date: document.getElementById('date').value,
        // time: document.getElementById('time').value
    };

    console.log('Form submitted:', formData);
    // Add your API call or data processing logic here
}

function validateForm() {
    console.log(artistInput.value);
    if (!artistInput.value) {
        alert('Please select an artist');
        return false;
    }
    if (!countryInput.value) {
        alert('Please select a country');
        return false;
    }
    if (!cityInput.value) {
        alert('Please select a city');
        return false;
    }
    if (!venueInput.value) {
        alert('Please select a venue');
        return false;
    }
    if (!showInput.value) {
        alert('Please select a show');
        return false;
    }
    return true;
}

document.addEventListener('keydown', (e) => {
    if (e.key === 'Escape' && activeField) {
        closePopup();
    }
});