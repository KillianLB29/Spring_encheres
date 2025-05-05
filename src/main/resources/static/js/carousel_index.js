document.addEventListener('DOMContentLoaded', function () {
    const carousel = document.querySelector('.carousel');
    const cardContainer = carousel.querySelector('.card-container');
    const cards = carousel.querySelectorAll('.card');
    const prevBtn = carousel.querySelector('.carousel-btn.prev');
    const nextBtn = carousel.querySelector('.carousel-btn.next');

    let cardWidth = cards[0].offsetWidth;
    let currentIndex = 0;

    // Dupliquer les cartes pour un effet infini
    cards.forEach(card => {
        const cloneFirst = card.cloneNode(true);
        const cloneLast = card.cloneNode(true);
        cardContainer.appendChild(cloneFirst); // ajout en fin
        cardContainer.insertBefore(cloneLast, cardContainer.firstChild); // ajout au début
    });

    const totalCards = cardContainer.querySelectorAll('.card').length;

    // Ajuster largeur du container
    cardContainer.style.width = `${totalCards * cardWidth}px`;

    // Position de départ (après les clones du début)
    currentIndex = cards.length;
    cardContainer.style.transform = `translateX(-${currentIndex * cardWidth}px)`;

    function moveTo(index) {
        cardContainer.style.transition = 'transform 0.5s ease-in-out';
        cardContainer.style.transform = `translateX(-${index * cardWidth}px)`;
    }

    function handleNext() {
        currentIndex++;
        moveTo(currentIndex);

        if (currentIndex >= totalCards - cards.length) {
            setTimeout(() => {
                cardContainer.style.transition = 'none';
                currentIndex = cards.length;
                cardContainer.style.transform = `translateX(-${currentIndex * cardWidth}px)`;
            }, 500);
        }
    }

    function handlePrev() {
        currentIndex--;
        moveTo(currentIndex);

        if (currentIndex < cards.length) {
            setTimeout(() => {
                cardContainer.style.transition = 'none';
                currentIndex = totalCards - cards.length * 2;
                cardContainer.style.transform = `translateX(-${currentIndex * cardWidth}px)`;
            }, 500);
        }
    }

    nextBtn.addEventListener('click', handleNext);
    prevBtn.addEventListener('click', handlePrev);

    // Recalculer la largeur à la redimension
    window.addEventListener('resize', () => {
        cardWidth = cards[0].offsetWidth;
        cardContainer.style.width = `${totalCards * cardWidth}px`;
        moveTo(currentIndex);
    });
});
