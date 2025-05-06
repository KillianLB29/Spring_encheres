document.addEventListener("DOMContentLoaded", function () {
    const slides = document.querySelectorAll('.carousel-slide');
    const prevBtn = document.querySelector('.carousel-btn_index.prev_index');
    const nextBtn = document.querySelector('.carousel-btn_index.next_index');
    let currentIndex = 0;

    if (prevBtn && nextBtn) {
        function showSlide(index) {
            const carousel = document.querySelector('.carousel-page');
            carousel.style.transform = `translateX(-${index * 100}%)`;
        }

        prevBtn.addEventListener('click', () => {
            if (currentIndex > 0) currentIndex--;
            showSlide(currentIndex);
        });

        nextBtn.addEventListener('click', () => {
            if (currentIndex < slides.length - 1) currentIndex++;
            showSlide(currentIndex);
        });

        showSlide(currentIndex); // Affiche la première image du carousel
    }
});
