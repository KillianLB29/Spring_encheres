package fr.eni.tp.spring_encheres.ihm;

import fr.eni.tp.spring_encheres.bll.ArticleVenduService;
import fr.eni.tp.spring_encheres.bll.CategorieService;
import fr.eni.tp.spring_encheres.bll.EnchereService;
import fr.eni.tp.spring_encheres.bll.UtilisateurService;
import fr.eni.tp.spring_encheres.bo.*;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@SessionAttributes("utilisateurSession")
public class EnchereController {

    private final ArticleVenduService articleVenduService;
    private final EnchereService enchereService;
    private final UtilisateurService utilisateurService;
    private final CategorieService categorieService;

    public EnchereController(
            ArticleVenduService articleVenduService,
            EnchereService enchereService,
            UtilisateurService utilisateurService,
            CategorieService categorieService
    ) {
        this.articleVenduService = articleVenduService;
        this.enchereService = enchereService;
        this.utilisateurService = utilisateurService;
        this.categorieService = categorieService;
    }

    // === ACCUEIL ===
    @GetMapping({"/", "/accueil"})
    public String afficherAccueil(
            @ModelAttribute("utilisateurSession") Utilisateur utilisateurSession,
            Model model
    ) {
        List<ArticleVendu> articles = articleVenduService.consulterArticlesEnCoursDeVente();
        List<Categorie> categories = categorieService.consulterCategories();

        model.addAttribute("articles", articles);
        model.addAttribute("categories", categories);
        model.addAttribute("utilisateur", utilisateurSession);

        return "index";
    }

    // === DÉTAIL D’UNE ENCHÈRE ===
    @GetMapping("/encheres/{id}")
    public String afficherEnchere(
            @PathVariable long id,
            @ModelAttribute("utilisateurSession") Utilisateur utilisateurSession,
            Model model
    ) {
        Enchere enchere = enchereService.meilleurEnchere(id);
        ArticleVendu article = articleVenduService.consulterArticleParId(id);

        model.addAttribute("article", article);
        model.addAttribute("enchere", enchere);
        model.addAttribute("utilisateur", utilisateurSession);

        return "enchere";
    }

    // === FORMULAIRE DE CRÉATION ===
    @GetMapping("/articles/creer")
    public String afficherFormulaireCreationArticle(
            @ModelAttribute("utilisateurSession") Utilisateur utilisateurSession,
            Model model
    ) {
        ArticleVendu article = new ArticleVendu();
        article.setUtilisateur(utilisateurSession); // associer vendeur

        // Préremplir le lieu de retrait
        Retrait retrait = new Retrait(
                utilisateurSession.getRue(),
                utilisateurSession.getCodePostal(),
                utilisateurSession.getVille()
        );
        article.setLieuRetrait(retrait); // lien 1–1 entre ArticleVendu et Retrait

        model.addAttribute("article", article);
        model.addAttribute("categories", categorieService.consulterCategories());

        return "article"; // retourne vers le template "article.html"
    }

    // === TRAITEMENT DU FORMULAIRE ===
    @PostMapping("/articles/creer")
    public String enregistrerArticle(
            @Valid @ModelAttribute("article") ArticleVendu article,
            BindingResult result,
            @ModelAttribute("utilisateurSession") Utilisateur utilisateurSession,
            Model model
    ) {
        // Validation date fin > début + 24h
        if (article.getDateDebutEncheres() == null || article.getDateFinEncheres() == null ||
                article.getDateFinEncheres().before(new Date(article.getDateDebutEncheres().getTime() + 86400000))) {
            result.rejectValue("dateFinEncheres", "dateFinEncheres.invalide",
                    "La date de fin doit être au moins 24h après le début.");
        }

        if (result.hasErrors()) {
            model.addAttribute("categories", categorieService.consulterCategories());
            return "article";
        }

        // Associer l’utilisateur connecté
        article.setUtilisateur(utilisateurSession);

        // Création de l’article + lieu de retrait lié
        articleVenduService.creerArticle(article);

        return "redirect:/accueil";
    }

    // === GESTION SESSION UTILISATEUR (EXEMPLE FIXE TEMPORAIRE) ===
    @ModelAttribute("utilisateurSession")
    public Utilisateur membreSession() {
        System.out.println("Création du contexte utilisateur en session");
        return utilisateurService.findById(1); // à remplacer plus tard par session réelle
    }
}
