document.addEventListener('DOMContentLoaded', function () {
    const carousels = document.querySelectorAll('.carousel');

    carousels.forEach(carousel => {
        const prevBtn = carousel.querySelector('.carousel-btn.prev');
        const nextBtn = carousel.querySelector('.carousel-btn.next');
        const viewport = carousel.querySelector('.carousel-viewport');
        const cardContainer = carousel.querySelector('.card-container');
        const cards = carousel.querySelectorAll('.card');

        const cardStyle = getComputedStyle(cards[0]);
        const cardWidth = cards[0].offsetWidth;
        const gap = parseInt(cardStyle.marginRight) || 16;
        const cardsPerView = 5;
        const totalCards = cards.length;

        let currentIndex = 0;

        function updateCarousel() {
            const offset = (cardWidth + gap) * currentIndex;
            cardContainer.style.transform = `translateX(-${offset}px)`;
        }

        nextBtn.addEventListener('click', function () {
            if (currentIndex < totalCards - cardsPerView) {
                currentIndex++;
                updateCarousel();
            }
        });

        prevBtn.addEventListener('click', function () {
            if (currentIndex > 0) {
                currentIndex--;
                updateCarousel();
            }
        });

        // Ajuste dynamiquement la largeur du container pour qu'il ne déborde pas
        const totalWidth = (cardWidth + gap) * totalCards - gap;
        cardContainer.style.width = `${Math.min(totalWidth, viewport.offsetWidth)}px`;  // Limite la largeur du container à la largeur du viewport
    });
});
