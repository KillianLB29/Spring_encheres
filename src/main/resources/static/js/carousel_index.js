document.addEventListener('DOMContentLoaded', () => {
    const carousel = document.querySelector('.carousel');
    const cardContainer = carousel.querySelector('.card-container');
    const prevBtn = carousel.querySelector('.carousel-btn.prev');
    const nextBtn = carousel.querySelector('.carousel-btn.next');

    let cards = Array.from(cardContainer.children);
    const cardCount = cards.length;
    let currentIndex = cardCount;

    // Dupliquer les cartes pour l'effet infini
    const prependClones = cards.map(card => card.cloneNode(true)).reverse();
    const appendClones = cards.map(card => card.cloneNode(true));

    prependClones.forEach(clone => cardContainer.insertBefore(clone, cardContainer.firstChild));
    appendClones.forEach(clone => cardContainer.appendChild(clone));

    cards = Array.from(cardContainer.children);
    const totalCards = cards.length;

    // Définir la largeur des cartes et du conteneur
    const updateCardWidth = () => {
        const cardWidth = carousel.offsetWidth;
        cards.forEach(card => {
            card.style.minWidth = `${cardWidth}px`;
        });
        cardContainer.style.width = `${cardWidth * totalCards}px`;
        cardContainer.style.transform = `translateX(-${currentIndex * cardWidth}px)`;
    };

    updateCardWidth();
    window.addEventListener('resize', updateCardWidth);

    let isTransitioning = false;

    const moveToIndex = (index) => {
        if (isTransitioning) return;
        isTransitioning = true;
        const cardWidth = carousel.offsetWidth;
        cardContainer.style.transition = 'transform 0.4s ease';
        cardContainer.style.transform = `translateX(-${index * cardWidth}px)`;
        currentIndex = index;

        cardContainer.addEventListener('transitionend', handleTransitionEnd, { once: true });
    };

    const handleTransitionEnd = () => {
        const cardWidth = carousel.offsetWidth;
        cardContainer.style.transition = 'none';

        if (currentIndex >= cardCount * 2) {
            currentIndex = cardCount;
            cardContainer.style.transform = `translateX(-${currentIndex * cardWidth}px)`;
        } else if (currentIndex < cardCount) {
            currentIndex = cardCount * 2 - 1;
            cardContainer.style.transform = `translateX(-${currentIndex * cardWidth}px)`;
        }

        isTransitioning = false;
    };

    prevBtn.addEventListener('click', () => {
        moveToIndex(currentIndex - 1);
    });

    nextBtn.addEventListener('click', () => {
        moveToIndex(currentIndex + 1);
    });
});
