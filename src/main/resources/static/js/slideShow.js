const carouselContainer = document.querySelector('.carousel-container');
const nextBtn = document.querySelector('.next-btn');
const prevBtn = document.querySelector('.prev-btn');

let currentIndex = 0; // Indeks posisi saat ini
let totalShift = 0; // Total jarak yang sudah digeser
const itemsToShow = 4;

// Konfigurasi pergeseran dinamis
const shifts = [0, 1100, 980]; // Array nilai shift

// Fungsi untuk mengupdate posisi carousel
function updateCarousel() {
    carouselContainer.style.transform = `translateX(-${totalShift}px)`;
    console.log(`Shift: ${totalShift}px, Current Index: ${currentIndex}`);
    
    // Cek apakah tombol prev dan next perlu disembunyikan
    toggleButtonVisibility();
}

// Fungsi untuk mengubah visibilitas tombol prev dan next
function toggleButtonVisibility() {
    // Sembunyikan tombol prev jika di indeks pertama
    if (currentIndex === 0) {
        prevBtn.style.display = 'none';
    } else {
        prevBtn.style.display = 'inline'; // Tampilkan tombol prev jika bukan di indeks pertama
    }

    // Sembunyikan tombol next jika di indeks terakhir
    const maxIndex = shifts.length - 1;
    if (currentIndex === maxIndex) {
        nextBtn.style.display = 'none';
    } else {
        nextBtn.style.display = 'inline'; // Tampilkan tombol next jika bukan di indeks terakhir
    }
}

// Tombol Next
nextBtn.addEventListener('click', () => {
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

// Inisialisasi carousel pada posisi awal
carouselContainer.style.transform = `translateX(-${shifts[0]}px)`;
toggleButtonVisibility(); // Panggil fungsi untuk cek visibilitas tombol saat pertama kali