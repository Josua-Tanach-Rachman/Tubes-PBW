function showPopup() {
    document.getElementById('popup').style.display = 'block';
    document.getElementById('overlay').style.display = 'block';
}

function closePopup() {
    document.getElementById('popup').style.display = 'none';
    document.getElementById('overlay').style.display = 'none';
}

document.querySelectorAll('.detailButton').forEach(button => {
    button.addEventListener('click', showPopup);
});

document.querySelector('.close').addEventListener('click', closePopup);
