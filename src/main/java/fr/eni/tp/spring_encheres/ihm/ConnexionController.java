package fr.eni.tp.spring_encheres.ihm;

import fr.eni.tp.spring_encheres.bll.ArticleVenduService;
import fr.eni.tp.spring_encheres.bll.CategorieService;
import fr.eni.tp.spring_encheres.bll.UtilisateurService;
import fr.eni.tp.spring_encheres.bo.ArticleVendu;
import fr.eni.tp.spring_encheres.bo.Categorie;
import fr.eni.tp.spring_encheres.bo.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;

import java.util.List;

@Controller
@SessionAttributes("utilisateurSession")
public class ConnexionController {
    ArticleVenduService articleVenduService;
    CategorieService categorieService;

    public ConnexionController(ArticleVenduService articleVenduService, CategorieService categorieService) {
        this.articleVenduService = articleVenduService;
        this.categorieService = categorieService;
    }

    @GetMapping("/login")
    public String connexion(@ModelAttribute("utilisateurSession") Utilisateur utilisateurSession, Model model) {
        List<ArticleVendu> articles = articleVenduService.consulterArticlesEnCoursDeVente();
        List<Categorie> categories = categorieService.consulterCategories();

        model.addAttribute("showLoginModal", true);
        model.addAttribute("articles", articles);
        model.addAttribute("categories", categories);
        model.addAttribute("utilisateur", utilisateurSession);

        return "index";
    }
}
