    document.addEventListener("DOMContentLoaded", function () {
    const carouselPage = document.querySelector('.carousel-page');
    const slides = document.querySelectorAll('.carousel-slide');
    const prevBtn = document.querySelector('.carousel-btn_index.prev_index');
    const nextBtn = document.querySelector('.carousel-btn_index.next_index');
    let currentIndex = 0;

    if (carouselPage && slides.length > 0) {
    carouselPage.style.width = `${100 * slides.length}%`;

    slides.forEach(slide => {
    slide.style.width = `${100 / slides.length}%`;
});

    function showSlide(index) {
    carouselPage.style.transform = `translateX(-${index * (100 / slides.length)}%)`;
}

    prevBtn.addEventListener('click', () => {
    if (currentIndex > 0) currentIndex--;
    showSlide(currentIndex);
});

    nextBtn.addEventListener('click', () => {
    if (currentIndex < slides.length - 1) currentIndex++;
    showSlide(currentIndex);
});

    showSlide(currentIndex);
}
});

