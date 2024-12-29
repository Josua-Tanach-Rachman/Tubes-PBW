// SLIDE SHOW ARTIST
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

// SLIDE SHOW SONG
    const carouselContainer2 = document.querySelector('.carousel-container2');
    const nextBtn2 = document.querySelector('.next-btn2');
    const prevBtn2 = document.querySelector('.prev-btn2');

    let currentIndex2 = 0; // Indeks posisi saat ini
    let totalShift2 = 0; // Total jarak yang sudah digeser
    const itemsToShow2 = 4;

    // Konfigurasi pergeseran dinamis
    const shifts2 = [0, 1100, 980]; // Array nilai shift

    // Fungsi untuk mengupdate posisi carousel
    function updateCarousel2() {
        carouselContainer2.style.transform = `translateX(-${totalShift2}px)`;
        
        // Cek apakah tombol prev dan next perlu disembunyikan
        toggleButtonVisibility2();
    }

    // Fungsi untuk mengubah visibilitas tombol prev dan next
    function toggleButtonVisibility2() {
        // Sembunyikan tombol prev jika di indeks pertama
        if (currentIndex2 === 0) {
            prevBtn2.style.display = 'none';
        } else {
            prevBtn2.style.display = 'inline'; // Tampilkan tombol prev jika bukan di indeks pertama
        }

        // Sembunyikan tombol next jika di indeks terakhir
        const maxIndex = shifts2.length - 1;
        if (currentIndex2 === maxIndex) {
            nextBtn2.style.display = 'none';
        } else {
            nextBtn2.style.display = 'inline'; // Tampilkan tombol next jika bukan di indeks terakhir
        }
    }

    // Tombol Next
    nextBtn2.addEventListener('click', () => {
        const maxIndex = shifts2.length - 1; // Maksimal indeks berdasarkan shifts array

        if (currentIndex2 < maxIndex) {
            currentIndex2++; // Tambah indeks
            totalShift2 += shifts2[currentIndex2]; // Tambahkan nilai shift
            updateCarousel2();
        }
    });

    // Tombol Prev
    prevBtn2.addEventListener('click', () => {
        if (currentIndex2 > 0) {
            totalShift2 -= shifts2[currentIndex2]; // Kurangi nilai shift
            currentIndex2--; // Kurangi indeks
            updateCarousel2();
        }
    });

    // Inisialisasi carousel pada posisi awal
    carouselContainer2.style.transform = `translateX(-${shifts2[0]}px)`;
    toggleButtonVisibility2(); // Panggil fungsi untuk cek visibilitas tombol saat pertama kali