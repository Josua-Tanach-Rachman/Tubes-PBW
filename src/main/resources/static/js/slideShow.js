const carouselContainer = document.querySelector('.carousel-container');
const nextBtn = document.querySelector('.next-btn');
const prevBtn = document.querySelector('.prev-btn');

let currentIndex = 0; // Indeks posisi saat ini
let totalShift = 0; // Total jarak yang sudah digeser
const itemsToShow = 4;

// Konfigurasi pergeseran dinamis
const shifts = [0, 1100, 980]; // Array nilai shift

// Tombol Next
nextBtn.addEventListener('click', () => {
    const totalItems = carouselContainer.children.length;
    const maxIndex = shifts.length - 1; // Maksimal indeks berdasarkan shifts array

    if (currentIndex < maxIndex) {
        currentIndex++; // Tambah indeks
        totalShift += shifts[currentIndex]; // Tambahkan nilai shift
        updateCarousel();
    }
});

// Tombol Prev
prevBtn.addEventListener('click', () => {
    if (currentIndex > 0) {
        totalShift -= shifts[currentIndex]; // Kurangi nilai shift
        currentIndex--; // Kurangi indeks
        updateCarousel();
    }
});

// Fungsi untuk mengupdate posisi carousel
function updateCarousel() {
    carouselContainer.style.transform = `translateX(-${totalShift}px)`;
    console.log(`Shift: ${totalShift}px, Current Index: ${currentIndex}`);
}

// Inisialisasi carousel pada posisi awal
carouselContainer.style.transform = `translateX(-${shifts[0]}px)`;