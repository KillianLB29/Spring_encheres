document.addEventListener('DOMContentLoaded', () => {
    const carousel = document.querySelector('.carousel');
    const cardContainer = carousel.querySelector('.card-container');
    const prevBtn = carousel.querySelector('.carousel-btn.prev');
    const nextBtn = carousel.querySelector('.carousel-btn.next');

    let cards = Array.from(cardContainer.children);
    const cardCount = cards.length;
    const cardsPerView = 3;
    const gap = 16; // en pixels (1rem = 16px)
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
        const totalGap = gap * (cardsPerView - 1);
        const availableWidth = carousel.offsetWidth - totalGap;
        const cardWidth = availableWidth / cardsPerView;

        cards.forEach(card => {
            card.style.minWidth = `${cardWidth}px`;
            card.style.marginRight = `0px`; // Reset
        });

        // Appliquer le gap entre les cartes manuellement
        for (let i = 0; i < cards.length; i++) {
            if ((i + 1) % cardsPerView !== 0) {
                cards[i].style.marginRight = `${gap}px`;
            }
        }

        cardContainer.style.width = `${(cardWidth + gap) * totalCards - gap}px`;
        cardContainer.style.transform = `translateX(-${currentIndex * (cardWidth + gap)}px)`;
    };

    updateCardWidth();
    window.addEventListener('resize', updateCardWidth);

    let isTransitioning = false;

    const moveToIndex = (index) => {
        if (isTransitioning) return;
        isTransitioning = true;

        const totalGap = gap * (cardsPerView - 1);
        const cardWidth = (carousel.offsetWidth - totalGap) / cardsPerView;
        const fullCardWidth = cardWidth + gap;

        cardContainer.style.transition = 'transform 0.4s ease';
        cardContainer.style.transform = `translateX(-${index * fullCardWidth}px)`;
        currentIndex = index;

        cardContainer.addEventListener('transitionend', handleTransitionEnd, { once: true });
    };

    const handleTransitionEnd = () => {
        const totalGap = gap * (cardsPerView - 1);
        const cardWidth = (carousel.offsetWidth - totalGap) / cardsPerView;
        const fullCardWidth = cardWidth + gap;

        cardContainer.style.transition = 'none';

        if (currentIndex >= cardCount * 2) {
            currentIndex = cardCount;
            cardContainer.style.transform = `translateX(-${currentIndex * fullCardWidth}px)`;
        } else if (currentIndex < cardCount) {
            currentIndex = cardCount * 2 - cardsPerView;
            cardContainer.style.transform = `translateX(-${currentIndex * fullCardWidth}px)`;
        }

        isTransitioning = false;
    };

    prevBtn.addEventListener('click', () => {
        moveToIndex(currentIndex - cardsPerView);
    });

    nextBtn.addEventListener('click', () => {
        moveToIndex(currentIndex + cardsPerView);
    });
});

