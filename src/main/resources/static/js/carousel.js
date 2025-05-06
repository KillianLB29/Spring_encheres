document.addEventListener('DOMContentLoaded', function() {
    const carousels = document.querySelectorAll('.carousel');

    carousels.forEach(carousel => {
        const prevBtn = carousel.querySelector('.carousel-btn.prev');
        const nextBtn = carousel.querySelector('.carousel-btn.next');
        const cardContainer = carousel.querySelector('.card-container');
        const cards = carousel.querySelectorAll('.card');

        const cardsPerView = 4;
        const totalCards = cards.length;
        let currentIndex = 0;

        // Largeur d'une carte (assumée identique)
        const cardWidth = cards[0].offsetWidth;

        function updateCarousel() {
            const offset = currentIndex * cardWidth;
            cardContainer.style.transform = `translateX(-${offset}px)`;
        }

        nextBtn.addEventListener('click', function() {
            if (currentIndex < totalCards - cardsPerView) {
                currentIndex++;
                updateCarousel();
            }
        });

        prevBtn.addEventListener('click', function() {
            if (currentIndex > 0) {
                currentIndex--;
                updateCarousel();
            }
        });

        // Ajuste le container pour éviter retour à la ligne
        cardContainer.style.width = `${cardWidth * totalCards}px`;
    });
});
