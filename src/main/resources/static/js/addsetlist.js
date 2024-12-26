const mockData = {
    artists: ['The Beatles', 'Pink Floyd', 'Led Zeppelin', 'Queen', 'The Rolling Stones'],
    locations: {
        'United States': {
            'New York': ['Madison Square Garden', 'Radio City Music Hall', 'Barclays Center'],
            'Colorado': ['Red Rocks Amphitheatre', 'Ball Arena', 'Fiddler\'s Green'],
            'California': ['Hollywood Bowl', 'The Greek Theatre', 'Chase Center']
        },
        'United Kingdom': {
            'London': ['Wembley Stadium', 'O2 Arena', 'Royal Albert Hall'],
            'Manchester': ['Manchester Arena', 'Old Trafford Cricket Ground'],
            'Liverpool': ['Anfield Stadium', 'M&S Bank Arena']
        },
        'Australia': {
            'Sydney': ['Sydney Opera House', 'Qudos Bank Arena'],
            'Melbourne': ['Rod Laver Arena', 'Marvel Stadium'],
            'Brisbane': ['Brisbane Entertainment Centre', 'The Gabba']
        }
    }
};

// DOM Elements
const form = document.getElementById('setlistForm');
const artistInput = document.getElementById('artist-name');
const countryInput = document.getElementById('country');
const cityInput = document.getElementById('city');
const venueInput = document.getElementById('venue');
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

// Event Listeners
artistInput.addEventListener('click', () => openPopup('artist'));
countryInput.addEventListener('click', () => openPopup('country'));
cityInput.addEventListener('click', () => openPopup('city'));
venueInput.addEventListener('click', () => openPopup('venue'));
closeBtn.addEventListener('click', closePopup);
overlay.addEventListener('click', closePopup);
searchInput.addEventListener('input', handleSearch);
form.addEventListener('submit', handleSubmit);

// Functions
function openPopup(field) {
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
            currentSuggestions = mockData.artists;
            break;
        case 'country':
            popupTitle.textContent = 'Select Country';
            searchInput.placeholder = 'Search countries...';
            currentSuggestions = Object.keys(mockData.locations);
            break;
        case 'city':
            if (!selectedCountry) {
                alert('Please select a country first');
                closePopup();
                return;
            }
            popupTitle.textContent = 'Select City';
            searchInput.placeholder = 'Search cities...';
            currentSuggestions = Object.keys(mockData.locations[selectedCountry]);
            break;
        case 'venue':
            if (!selectedCountry || !selectedCity) {
                alert('Please select both country and city first');
                closePopup();
                return;
            }
            popupTitle.textContent = 'Select Venue';
            searchInput.placeholder = 'Search venues...';
            currentSuggestions = mockData.locations[selectedCountry][selectedCity];
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
            searchList = mockData.artists;
            break;
        case 'country':
            searchList = Object.keys(mockData.locations);
            break;
        case 'city':
            searchList = Object.keys(mockData.locations[selectedCountry]);
            break;
        case 'venue':
            searchList = mockData.locations[selectedCountry][selectedCity];
            break;
    }
    
    currentSuggestions = searchList.filter(item => 
        item.toLowerCase().includes(searchTerm)
    );
    
    updateSuggestionsList(currentSuggestions);
}

function updateSuggestionsList(suggestions) {
    suggestionsList.innerHTML = '';
    
    suggestions.forEach(suggestion => {
        const li = document.createElement('li');
        li.textContent = suggestion;
        li.addEventListener('click', () => {
            selectSuggestion(suggestion);
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
            break;
        case 'city':
            cityInput.value = value;
            selectedCity = value;
            // Reset venue
            venueInput.value = '';
            break;
        case 'venue':
            venueInput.value = value;
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
        date: document.getElementById('date').value,
        time: document.getElementById('time').value
    };
    
    console.log('Form submitted:', formData);
    // Add your API call or data processing logic here
}

function validateForm() {
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
    if (!document.getElementById('date').value) {
        alert('Please select a date');
        return false;
    }
    if (!document.getElementById('time').value) {
        alert('Please select a time');
        return false;
    }
    return true;
}

// Optional: Close popup when pressing Escape key
document.addEventListener('keydown', (e) => {
    if (e.key === 'Escape' && activeField) {
        closePopup();
    }
});