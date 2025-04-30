package fr.eni.tp.spring_encheres.ihm;

import fr.eni.tp.spring_encheres.bll.ArticleVenduService;
import fr.eni.tp.spring_encheres.bll.EnchereService;
import fr.eni.tp.spring_encheres.bll.UtilisateurService;
import fr.eni.tp.spring_encheres.bo.ArticleVendu;
import fr.eni.tp.spring_encheres.bo.Enchere;
import fr.eni.tp.spring_encheres.bo.Utilisateur;
import fr.eni.tp.spring_encheres.dal.ArticleVenduDAO;
import fr.eni.tp.spring_encheres.dal.EnchereDAO;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;

@Controller
@SessionAttributes("utilisateurSession")
public class AccueilController {

    private ArticleVenduService articleVenduService;
    private EnchereService enchereService;
    private UtilisateurService utilisateurService;


    public AccueilController(ArticleVenduService articleVenduService, EnchereService enchereService, UtilisateurService utilisateurService) {
        this.articleVenduService = articleVenduService;
        this.enchereService = enchereService;
        this.utilisateurService = utilisateurService;
    }

    @GetMapping({"/", "/accueil"})
    public String afficherAccueil( @ModelAttribute("utilisateurSession") Utilisateur utilisateurSession, Model model) {
        // Récupération de toutes les enchères depuis la base
        List<ArticleVendu> articles = articleVenduService.consulterArticlesEnCoursDeVente();

        // Passage des données au modèle pour affichage dans la vue Thymeleaf
        model.addAttribute("articles", articles);
        model.addAttribute("utilisateur", utilisateurSession);

        // Redirection vers le template index.html
        return "index";
    }
    @ModelAttribute("utilisateurSession")
    public Utilisateur membreSession() {
        System.out.println("Création du contexte");
        Utilisateur util = new Utilisateur();
        util= utilisateurService.findById(1);
        return util;
    }

}
