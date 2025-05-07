package fr.eni.tp.spring_encheres.ihm;

import fr.eni.tp.spring_encheres.bll.ArticleVenduService;
import fr.eni.tp.spring_encheres.bll.CategorieService;
import fr.eni.tp.spring_encheres.bll.EnchereService;
import fr.eni.tp.spring_encheres.bll.UtilisateurService;
import fr.eni.tp.spring_encheres.bo.*;
import fr.eni.tp.spring_encheres.exception.EnchereException;
import fr.eni.tp.spring_encheres.ihm.dto.ArticleVenduDTO;
import fr.eni.tp.spring_encheres.ihm.dto.UtilisateurDTO;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
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

        if(utilisateurSession!=null){
            model.addAttribute("utilisateur",utilisateurSession);
            System.out.println(utilisateurSession);
        }
        else{
            model.addAttribute("utilisateur", new Utilisateur());
        }
        List<ArticleVendu> articles = articleVenduService.consulterArticlesEnCoursDeVente();
        List<Categorie> categories = categorieService.consulterCategories();
        System.out.println(articles);

        model.addAttribute("articles", articles);
        model.addAttribute("categories", categories);
        model.addAttribute("utilisateurDTO",new UtilisateurDTO());

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
        model.addAttribute("utilisateurDTO", new UtilisateurDTO());

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

        model.addAttribute("mode","creation");
        model.addAttribute("formAction", "/articles/creer");
        model.addAttribute("article", article);
        model.addAttribute("categories", categorieService.consulterCategories());
        model.addAttribute("utilisateur", utilisateurSession);
        model.addAttribute("utilisateurDTO", new UtilisateurDTO());

        return "article"; // retourne vers le template "article.html"
    }

    @GetMapping("/articles/modifier/{id}")
    public String afficherFormulaireModificationArticle(
            @PathVariable long id,
            @ModelAttribute("utilisateurSession") Utilisateur utilisateurSession,
            Model model
    ) throws IOException {
//        if(!articleVenduService.isEnchereNonCommencer(id)){
//            return "redirect:/";
//        }
        ArticleVendu articleModif = articleVenduService.consulterArticleParId(id);
        LocalDateTime now = LocalDateTime.now();
        Date nowDate = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
        if(articleModif.getDateDebutEncheres().before(nowDate)){
            return "redirect:/";
        }
        ArticleVenduDTO article = new ArticleVenduDTO();
        article.setRue(articleModif.getLieuRetrait().getRue());
        article.setCodePostal(articleModif.getLieuRetrait().getCode_Postal());
        article.setVille(articleModif.getLieuRetrait().getVille());
        article.setNomArticle(articleModif.getNomArticle());
        article.setDescription(articleModif.getDescription());
        article.setDateDebutEncheres(articleModif.getDateDebutEncheres());
        article.setDateFinEncheres(articleModif.getDateFinEncheres());
        article.setMiseAPrix(articleModif.getMiseAPrix());
        article.setCategorie(articleModif.getCategorie());
        model.addAttribute("mode", "modif");
        model.addAttribute("formAction", "/articles/modifier/"+id);
        model.addAttribute("mode","modif");
        model.addAttribute("article", article);
        model.addAttribute("categories", categorieService.consulterCategories());
        model.addAttribute("utilisateur", utilisateurSession);
        model.addAttribute("utilisateurDTO", new UtilisateurDTO());

        return "article"; // retourne vers le template "article.html"
    }

    @PostMapping("/articles/modifier/{id}")
    public String modifierArticle(
            @PathVariable long id,
            @ModelAttribute("utilisateurSession") Utilisateur utilisateurSession,
            @Valid @ModelAttribute("article") ArticleVenduDTO article,
            BindingResult result,
            Model model)
            throws IOException
    {
        EnchereException enchereException = new EnchereException();
        // Validation date fin > début + 24h
        if (article.getDateDebutEncheres() == null || article.getDateFinEncheres() == null ||
                article.getDateFinEncheres().before(new Date(article.getDateDebutEncheres().getTime() + 86400000))) {

            enchereException.addMessage("L'enchère doit durer au moins 24h.");
            enchereException.getMessages().forEach(message -> {
                ObjectError error = new ObjectError("globalError",message);
                result.addError(error);
            });
        }
        System.out.println(article.getCategorie());

        if (result.hasErrors()) {
            model.addAttribute("article", article);
            model.addAttribute("categories", categorieService.consulterCategories());
            model.addAttribute("utilisateurDTO", new UtilisateurDTO());
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
        articleVendu.setNoArticle(id);
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
        try{
            System.out.println("passage");
            articleVenduService.updateArticle(articleVendu);
        }
        catch (EnchereException e) {
            e.getMessages().forEach(message -> {
                ObjectError error = new ObjectError("globalError",message);
                result.addError(error);
            });
        }

        if (result.hasErrors()) {
            model.addAttribute("utilisateur",utilisateurSession);
            model.addAttribute("article", article);
            model.addAttribute("categories", categorieService.consulterCategories());
            model.addAttribute("utilisateurDTO", new UtilisateurDTO());
            return "article";
        }

        return "redirect:/accueil";
    }

    // === TRAITEMENT DU FORMULAIRE ===
    @PostMapping("/articles/creer")
    public String enregistrerArticle(
            @ModelAttribute("utilisateurSession") Utilisateur utilisateurSession,
            @Valid @ModelAttribute("article") ArticleVenduDTO article,
            BindingResult result,
            Model model)
            throws IOException
    {
        EnchereException enchereException = new EnchereException();
        // Validation date fin > début + 24h
        if (article.getDateDebutEncheres() == null || article.getDateFinEncheres() == null ||
                article.getDateFinEncheres().before(new Date(article.getDateDebutEncheres().getTime() + 86400000))) {

            enchereException.addMessage("L'enchère doit durer au moins 24h.");
            enchereException.getMessages().forEach(message -> {
                ObjectError error = new ObjectError("globalError",message);
                result.addError(error);
            });
        }
        System.out.println(article.getCategorie());

        if (result.hasErrors()) {
            model.addAttribute("article", article);
            model.addAttribute("categories", categorieService.consulterCategories());
            model.addAttribute("utilisateurDTO", new UtilisateurDTO());
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
        try{
            articleVenduService.creerArticle(articleVendu);
        }
        catch (EnchereException e) {
            e.getMessages().forEach(message -> {
                ObjectError error = new ObjectError("globalError",message);
                result.addError(error);
            });
        }

        if (result.hasErrors()) {
            model.addAttribute("utilisateur",utilisateurSession);
            model.addAttribute("article", article);
            model.addAttribute("categories", categorieService.consulterCategories());
            model.addAttribute("utilisateurDTO", new UtilisateurDTO());
            return "article";
        }

        return "redirect:/accueil";
    }
    @GetMapping("/encheres")
    public String mesEncheres(@ModelAttribute("utilisateurSession") Utilisateur utilisateurSession, Model model) {
        List<ArticleVendu> encheresOuvertes = new ArrayList<>();
        List<ArticleVendu> encheresEnCours = new ArrayList<>();
        List<ArticleVendu> encheresRemportes = new ArrayList<>();
        List<ArticleVendu> venteEnCours = new ArrayList<>();
        List<ArticleVendu> venteNonDebutes = new ArrayList<>();
        List<ArticleVendu> venteTerminees = new ArrayList<>();

        model.addAttribute("encheresOuvertes", encheresOuvertes);
        model.addAttribute("encheresEnCours", encheresEnCours);
        model.addAttribute("encheresRemportes", encheresRemportes);
        model.addAttribute("venteEnCours", venteEnCours);
        model.addAttribute("venteNonDebutes", venteNonDebutes);
        model.addAttribute("venteTerminees", venteTerminees);
        model.addAttribute("categories",categorieService.consulterCategories());
        model.addAttribute("utilisateur", utilisateurSession);
        model.addAttribute("utilisateurDTO", new UtilisateurDTO());
        return "mesEncheres";
    }
    @PostMapping("/encheres")
    public String filtrerEncheres(@ModelAttribute("utilisateurSession") Utilisateur utilisateurSession,
            @RequestParam(name = "filtre", required = false) String filtre,
            @RequestParam(name = "categorie", required = false) String categorie,
            @RequestParam(name = "g1_opt1", required = false) String g1Opt1,
            @RequestParam(name = "g1_opt2", required = false) String g1Opt2,
            @RequestParam(name = "g1_opt3", required = false) String g1Opt3,
            @RequestParam(name = "g2_opt1", required = false) String g2Opt1,
            @RequestParam(name = "g2_opt2", required = false) String g2Opt2,
            @RequestParam(name = "g2_opt3", required = false) String g2Opt3,
            Model model
    ) {
        //Initialisé toutes mes listes
        List<ArticleVendu> encheresOuvertes = new ArrayList<>();
        List<ArticleVendu> encheresEnCours = new ArrayList<>();
        List<ArticleVendu> encheresRemportes = new ArrayList<>();
        List<ArticleVendu> venteEnCours = new ArrayList<>();
        List<ArticleVendu> venteNonDebutes = new ArrayList<>();
        List<ArticleVendu> venteTerminees = new ArrayList<>();


        // Exemple de debug
        System.out.println("Filtre texte : " + filtre);
        System.out.println("Catégorie : " + categorie);

        if (g1Opt1 != null){
            System.out.println("✔ Enchères ouvertes cochée");
            encheresOuvertes = articleVenduService.consulterEncheresOuvertes(utilisateurSession.getNoUtilisateur(),filtre,categorie);
            System.out.println(encheresOuvertes);
        }
        if (g1Opt2 != null){
            System.out.println("✔ Mes enchères en cours cochée");
            encheresEnCours = articleVenduService.consulterEncheresEnCoursFiltrer(utilisateurSession.getNoUtilisateur(),filtre,categorie);
        }
        if (g1Opt3 != null){
            System.out.println("✔ Mes enchères remportées cochée");
            encheresRemportes = articleVenduService.consulterEncheresRemportes(utilisateurSession.getNoUtilisateur(),filtre,categorie);

        }

        if (g2Opt1 != null) {
            System.out.println("✔ Mes ventes en cours cochée");
            venteEnCours=articleVenduService.consulterVentesEnCours(utilisateurSession.getNoUtilisateur(),filtre,categorie);
        }
        if (g2Opt2 != null){
            System.out.println("✔ Mes ventes non débutées cochée");
            venteNonDebutes = articleVenduService.consulterVentesNonDebutes(utilisateurSession.getNoUtilisateur(),filtre,categorie);
        }
        if (g2Opt3 != null) {
            System.out.println("✔ Ventes terminées cochée");
            venteTerminees=articleVenduService.consulterVentesTerminees(utilisateurSession.getNoUtilisateur(),filtre,categorie);
        }


        model.addAttribute("encheresOuvertes", encheresOuvertes);
        model.addAttribute("encheresEnCours", encheresEnCours);
        model.addAttribute("encheresRemportes", encheresRemportes);
        model.addAttribute("venteEnCours", venteEnCours);
        model.addAttribute("venteNonDebutes", venteNonDebutes);
        model.addAttribute("venteTerminees", venteTerminees);
        model.addAttribute("categories", categorieService.consulterCategories());
        model.addAttribute("utilisateur", utilisateurSession);
        model.addAttribute("utilisateurDTO", new UtilisateurDTO());
        return "mesEncheres";
    }
    //=== SUPPRESSION D'UNE ENCHERE ===
    @GetMapping("/articles/supprimer/{id}")
    public String supprimerArticle(@PathVariable int id,@ModelAttribute("utilisateurSession") Utilisateur utilisateurSession, Model model) {
        ArticleVendu articleVendu = articleVenduService.consulterArticleParId(id);
        LocalDateTime now = LocalDateTime.now();
        Date nowDate = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
        if(articleVendu.getDateDebutEncheres().after(nowDate) && articleVendu.getUtilisateur().getNoUtilisateur()==utilisateurSession.getNoUtilisateur()){
            System.out.println("passage suppression article");
            articleVenduService.supprimerArticle(id);
        }
        return "redirect:/encheres";
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
        if(enchereService.enregistrerEnchere(enchere))
            utilisateurSession.setCredit(utilisateurSession.getCredit() -proposition);
        return "redirect:/encheres/" + id;
    }
    @ModelAttribute("utilisateurSession")
    public Utilisateur utilisateurSession(HttpSession session) {
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateurSession");
        System.out.println("util session : "+utilisateur);
        return utilisateur != null ? utilisateur : new Utilisateur(); // ou null si tu veux gérer l'absence
    }


}
