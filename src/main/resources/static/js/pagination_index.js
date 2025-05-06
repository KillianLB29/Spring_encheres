document.addEventListener("DOMContentLoaded", function () {
    const itemsPerPage = 9; // Nombre d'éléments par page
    const items = document.querySelectorAll(".card_index"); // Récupère toutes les cartes
    const paginationContainer = document.querySelector(".pagination-controls_index");
    const totalItems = items.length;
    const totalPages = Math.ceil(totalItems / itemsPerPage);

    let currentPage = 1;

    function displayPage(page) {
        // Affiche les articles correspondant à la page
        items.forEach((item, index) => {
            item.style.display = index >= (page - 1) * itemsPerPage && index < page * itemsPerPage ? "block" : "none";
        });

        // Mise à jour des boutons Précédent et Suivant
        const prevBtn = document.querySelector(".prev-btn");
        const nextBtn = document.querySelector(".next-btn");

        if (prevBtn && nextBtn) {
            prevBtn.classList.toggle("disabled", page === 1);
            nextBtn.classList.toggle("disabled", page === totalPages);
        }

        // Mise à jour de l'affichage du numéro de page
        const pageNumber = document.querySelector(".page-number");
        if (pageNumber) {
            pageNumber.textContent = `${page} / ${totalPages}`;
        }
    }

    function createPagination() {
        // S'assurer qu'il y a plus d'une page avant de créer la pagination
        if (totalPages > 1 && paginationContainer) {
            paginationContainer.innerHTML = `
                <button class="pagination-btn_index prev-btn">Précédent</button>
                <span class="page-number">${currentPage} / ${totalPages}</span>
                <button class="pagination-btn_index next-btn">Suivant</button>
            `;

            // Ajouter l'événement pour le bouton Précédent
            const prevBtn = document.querySelector(".prev-btn");
            if (prevBtn) {
                prevBtn.addEventListener("click", () => {
                    if (currentPage > 1) {
                        currentPage--;
                        displayPage(currentPage);
                    }
                });
            }

            // Ajouter l'événement pour le bouton Suivant
            const nextBtn = document.querySelector(".next-btn");
            if (nextBtn) {
                nextBtn.addEventListener("click", () => {
                    if (currentPage < totalPages) {
                        currentPage++;
                        displayPage(currentPage);
                    }
                });
            }
        }
    }

    createPagination(); // Crée la pagination
    displayPage(currentPage); // Affiche la première page au chargement

});
