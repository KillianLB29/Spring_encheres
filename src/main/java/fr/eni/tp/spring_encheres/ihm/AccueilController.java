package fr.eni.tp.spring_encheres.ihm;

import fr.eni.tp.spring_encheres.bo.ArticleVendu;
import fr.eni.tp.spring_encheres.bo.Enchere;
import fr.eni.tp.spring_encheres.dal.EnchereDAO;
import fr.eni.tp.spring_encheres.dal.ArticleVenduDAO;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AccueilController {

    private final EnchereDAO enchereDAO;
    private final ArticleVenduDAO articleVenduDAO;

    // Injection des DAO nécessaires via le constructeur
    public AccueilController(EnchereDAO enchereDAO, ArticleVenduDAO articleVenduDAO) {
        this.enchereDAO = enchereDAO;
        this.articleVenduDAO = articleVenduDAO;
    }

    // Mapping pour /accueil qui renverra le fichier index.html
    @GetMapping({"/", "/accueil"})  // L'URL "/accueil" redirige vers la page "index.html"
    public String afficherAccueil(Model model, HttpSession session) {
        List<Enchere> encheres = enchereDAO.findAll();

        // Pour chaque enchère, on enrichit l'objet ArticleVendu avec les données complètes
        for (Enchere enchere : encheres) {
            long idArticle = enchere.getArticleVendu().getNoArticle();

            // Appel à la méthode read() du DAO pour récupérer l'article complet
            // (et non findById, qui n'existe pas dans l'interface)
            ArticleVendu article = articleVenduDAO.read(idArticle);
            enchere.setArticleVendu(article); // Ajout de l'article complet à l'enchère
        }

        // Passage des données au modèle pour affichage dans la vue Thymeleaf
        model.addAttribute("encheres", encheres);
        model.addAttribute("utilisateur", session.getAttribute("utilisateur"));

        // Redirection vers le template index.html
        return "index";
    }
}
