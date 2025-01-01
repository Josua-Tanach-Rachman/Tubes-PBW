let arrayNegara = [];
let arrayKota = [];
let arrayLokasi = [];

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
            console.log(arrayLokasi);
            currentSuggestions = arrayLokasi;
            console.log(currentSuggestions);
        })
        .catch(error => {
            console.error('There was a problem with the fetch operation:', error);
        });
}

const form = document.getElementById('setlistForm');
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

countryInput.addEventListener('click', () => openPopup('country'));
cityInput.addEventListener('click', () => openPopup('city'));
venueInput.addEventListener('click', () => openPopup('venue'));
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
        case 'country':
            countryInput.value = value;
            selectedCountry = value;
            // Reset dependent fields
            cityInput.value = '';
            venueInput.value = '';
            selectedCity = '';
            selectedLocation = ''; // Reset location
            console.log("Country selected");
            fetchKota(selectedCountry);
            break;
        case 'city':
            cityInput.value = value;
            selectedCity = value;
            // Reset venue
            venueInput.value = '';
            selectedLocation = ''; // Reset location
            console.log("City selected");
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

function validateForm() {
    if (!document.getElementById('concert-name').value) {
        alert('Please insert the concert name');
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
        alert('Please select a start date');
        return false;
    }
    if (!document.getElementById('enddate').value) {
        alert('Please select an end date');
        return false;
    }
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

document.addEventListener('keydown', (e) => {
    if (e.key === 'Escape' && activeField) {
        closePopup();
    }
});