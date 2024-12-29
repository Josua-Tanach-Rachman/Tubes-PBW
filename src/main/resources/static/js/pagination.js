let quantityInput = document.getElementById('quantity');
let max = parseInt(quantityInput.max);  

quantityInput.addEventListener("input", () => {
    let quantity = parseInt(quantityInput.value);  

    if (quantity > max) {
        quantityInput.value = max; 
    }

    if (quantity < 1) {
        quantityInput.value = 1; 
    }
});

document.addEventListener("DOMContentLoaded", function () {
    const paginationLinks = document.querySelectorAll("ul.pagination-container a");

    const pageCount = max; 

    const maxDigits = pageCount.toString().length;

    paginationLinks.forEach(link => {
        link.style.minWidth = `${maxDigits * 0.6}em`;
    });


    quantityInput.style.minWidth = `${maxDigits * 0.6}em`;
});
