package fr.eni.tp.spring_encheres.ihm;

import fr.eni.tp.spring_encheres.bll.ArticleVenduService;
import fr.eni.tp.spring_encheres.bll.CategorieService;
import fr.eni.tp.spring_encheres.bll.EnchereService;
import fr.eni.tp.spring_encheres.bll.UtilisateurService;
import fr.eni.tp.spring_encheres.bo.*;
import fr.eni.tp.spring_encheres.ihm.dto.ArticleVenduDTO;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.time.Instant;
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
        System.out.println(articles);

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
        String mode;
        if(article.getDateDebutEncheres().after(Date.from(Instant.now()))) {
            mode = "attente";
        }
        else{
            Boolean enCours = articleVenduService.isEnchereEnCours(id);
            System.out.println(enCours ? "enchere" : "affichage");
            mode = enCours ? "enchere" : "affichage";
        }
        model.addAttribute("article", article);
        model.addAttribute("enchere", enchere);
        model.addAttribute("mode", mode );
        model.addAttribute("utilisateur", utilisateurSession);

        return "enchere";
    }

    // === FORMULAIRE DE CRÉATION ===
    @GetMapping("/articles/creer")
    public String afficherFormulaireCreationArticle(
            @ModelAttribute("utilisateurSession") Utilisateur utilisateurSession,
            Model model
    ) {
        ArticleVenduDTO article = new ArticleVenduDTO();
        article.setRue(utilisateurSession.getRue());
        article.setCodePostal(utilisateurSession.getCodePostal());
        article.setVille(utilisateurSession.getVille());
        article.setDateDebutEncheres(Date.from(Instant.now()));

        model.addAttribute("article", article);
        model.addAttribute("categories", categorieService.consulterCategories());
        model.addAttribute("utilisateur", utilisateurSession);

        return "article"; // retourne vers le template "article.html"
    }

    // === TRAITEMENT DU FORMULAIRE ===
    @PostMapping("/articles/creer")
    public String enregistrerArticle(
            @ModelAttribute("article") ArticleVenduDTO article,
            BindingResult result,
            @ModelAttribute("utilisateurSession") Utilisateur utilisateurSession,
            Model model
    ) throws IOException {
        // Validation date fin > début + 24h
        if (article.getDateDebutEncheres() == null || article.getDateFinEncheres() == null ||
                article.getDateFinEncheres().before(new Date(article.getDateDebutEncheres().getTime() + 86400000))) {
            result.rejectValue("dateFinEncheres", "dateFinEncheres.invalide",
                    "La date de fin doit être au moins 24h après le début.");
        }

        if (result.hasErrors()) {
            model.addAttribute("article", article);
            model.addAttribute("categories", categorieService.consulterCategories());
            return "article";
        }
        ArticleVendu articleVendu = new ArticleVendu();
        articleVendu.setCategorie(article.getCategorie());
        articleVendu.setNomArticle(article.getNomArticle());
        articleVendu.setLieuRetrait(new Retrait(article.getRue(),article.getCodePostal(),article.getVille()));
        articleVendu.setDateDebutEncheres(article.getDateDebutEncheres());
        articleVendu.setDateFinEncheres(article.getDateFinEncheres());
        articleVendu.setDescription(article.getDescription());
        articleVendu.setMiseAPrix(article.getMiseAPrix());
        articleVendu.setUtilisateur(utilisateurSession);
        System.out.println(articleVendu);

        //Gestion de l'image
        String uploadDir = "src/main/resources/static/img/";

        // Nom unique pour éviter les conflits
        String filename = System.currentTimeMillis() + "_" + article.getImage().getOriginalFilename();

        // Sauvegarde du fichier
        Path filePath = Paths.get(uploadDir + filename);
        Files.write(filePath, article.getImage().getBytes());

        // Chemin relatif à utiliser dans les vues HTML
        articleVendu.setUrlImage("/img/" + filename);
        // Création de l’article + lieu de retrait lié
        articleVenduService.creerArticle(articleVendu);


        return "redirect:/accueil";
    }
    @GetMapping("/encheres")
    public String mesEncheres(@ModelAttribute("utilisateurSession") Utilisateur utilisateurSession, Model model) {
        List<ArticleVendu> encheresTerminer = articleVenduService.consulterEncheresTerminer(utilisateurSession.getNoUtilisateur());
        List<ArticleVendu> encheresEnCours = articleVenduService.consulterEncheresEnCours(utilisateurSession.getNoUtilisateur());
        List<ArticleVendu> enchereParticipe = articleVenduService.consulterEncheresParticipe(utilisateurSession.getNoUtilisateur());
        model.addAttribute("encheresTerminer", encheresTerminer);
        model.addAttribute("encheresEnCours", encheresEnCours);
        model.addAttribute("encheresParticipe", enchereParticipe);
        model.addAttribute("utilisateur", utilisateurSession);
        return "mesEncheres";
    }
    // === CREATION D'UNE ENCHERE ===
    @PostMapping("/encherir/{id}")
    public String nouvelleEnchere(@ModelAttribute("utilisateurSession")Utilisateur utilisateurSession, @PathVariable int id, @RequestParam("proposition") int proposition)
    {
        Enchere enchere = new Enchere();
        enchere.setUtilisateur(utilisateurSession);
        enchere.setDateEnchere(new Date(System.currentTimeMillis()));
        ArticleVendu article = new ArticleVendu();
        article.setNoArticle(id);
        enchere.setArticleVendu(article);
        enchere.setMontantEnchere(proposition);
        enchereService.enregistrerEnchere(enchere);
        return "redirect:/encheres/" + id;
    }

    // === GESTION SESSION UTILISATEUR (EXEMPLE FIXE TEMPORAIRE) ===
    @ModelAttribute("utilisateurSession")
    public Utilisateur membreSession() {
        System.out.println("Création du contexte utilisateur en session");
        return utilisateurService.findById(1); // à remplacer plus tard par session réelle
    }
}
