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

let arrayArtis = [];
let arrayNegara = [];
let arrayKota = [];
let arrayLokasi = [];

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

fetch('/add/setlist/negara')
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.json(); 
    })
    .then(data => {
        data.forEach(function(negara) {
            arrayNegara.push(negara.namaNegara);
        });
        console.log(arrayNegara);
    })
    .catch(error => {
        console.error('There was a problem with the fetch operation:', error);
    });

function fetchKota(namaNegara) {
    fetch(`/add/setlist/kota?namaNegara=${namaNegara}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json(); 
        })
        .then(data => {
            currentSuggestions = [];
            arrayKota = [];
            data.forEach(function(kota) {
                arrayKota.push(kota.namaKota);
                console.log(kota);
            });
            console.log(arrayKota);
            currentSuggestions = arrayKota;
            console.log(currentSuggestions);
        })
        .catch(error => {
            console.error('There was a problem with the fetch operation:', error);
        });
}

function fetchLokasi(namaKota) {
    fetch(`/add/setlist/lokasi?namaKota=${namaKota}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json(); 
        })
        .then(data => {
            currentSuggestions = [];
            arrayLokasi = [];
            data.forEach(function(lokasi) {
                arrayLokasi.push(lokasi.namaLokasi);
                console.log(lokasi);
            });
            arrayLokasi.push('Add New Show');
            console.log(arrayLokasi);
            currentSuggestions = arrayLokasi;
            console.log(currentSuggestions);
        })
        .catch(error => {
            console.error('There was a problem with the fetch operation:', error);
        });
}


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
    }
    
    currentSuggestions = searchList.filter(item => 
        item.toLowerCase().includes(searchTerm)
    );

    //kalo hasil search ga ada addnya tambahin
    if (activeField === 'artist' && !currentSuggestions.includes('Add New Artist')) {
        currentSuggestions.push('Add New Artist');
    }

    if (activeField === 'venue' && !currentSuggestions.includes('Add New Show')) {
        currentSuggestions.push('Add New Show');
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
        }else if (suggestion == 'Add New Show') {
            li.id = "add";}
        li.addEventListener('click', () => {
            if (suggestion === 'Add New Artist') {
                window.location.href = '/addArtist';         
            }else if (suggestion == 'Add New Show') {
                window.location.href = 'addShow';
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
            console.log("sudah select country");
            fetchKota(selectedCountry);
            break;
        case 'city':
            cityInput.value = value;
            selectedCity = value;
            // Reset venue
            venueInput.value = '';
            console.log("sudah select kota");
            fetchLokasi(selectedCity);
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
    // if (!document.getElementById('date').value) {
    //     alert('Please select a date');
    //     return false;
    // }
    // if (!document.getElementById('time').value) {
    //     alert('Please select a time');
    //     return false;
    // }
    // let timeStart = document.querySelector("input[name='timeStart']").value;
    // let timeEnd = document.querySelector("input[name='timeEnd']").value;
    // console.log(timeStart);
    // console.log(timeEnd);
    // if(timeStart){
    //     if(!timeEnd){
    //         alert('Please select a time end');
    //         return false;
    //     }
    // }

    // if(timeEnd){
    //     if(!timeStart){
    //         alert('Please select a time start');
    //         return false;
    //     }
    // }
    return true;
}

// Optional: Close popup when pressing Escape key
document.addEventListener('keydown', (e) => {
    if (e.key === 'Escape' && activeField) {
        closePopup();
    }
});