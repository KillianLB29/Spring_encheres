package fr.eni.tp.spring_encheres.ihm;

import fr.eni.tp.spring_encheres.bll.ArticleVenduService;
import fr.eni.tp.spring_encheres.bll.CategorieService;
import fr.eni.tp.spring_encheres.bll.UtilisateurService;
import fr.eni.tp.spring_encheres.bo.ArticleVendu;
import fr.eni.tp.spring_encheres.bo.Categorie;
import fr.eni.tp.spring_encheres.bo.Utilisateur;
import fr.eni.tp.spring_encheres.exception.UtilisateurException;
import fr.eni.tp.spring_encheres.ihm.dto.UtilisateurDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;

import java.util.List;

@Controller
public class ConnexionController {

    ArticleVenduService articleVenduService;
    CategorieService categorieService;
    UtilisateurService utilisateurService;
    UserDetailsManager userDetailsManager;

    public ConnexionController(ArticleVenduService articleVenduService, CategorieService categorieService, UtilisateurService utilisateurService, UserDetailsManager userDetailsManager) {
        this.articleVenduService = articleVenduService;
        this.categorieService = categorieService;
        this.utilisateurService = utilisateurService;
        this.userDetailsManager = userDetailsManager;
    }

    @GetMapping("/login")
    public String connexion(@ModelAttribute("utilisateurSession") Utilisateur utilisateurSession, Model model) {
        List<ArticleVendu> articles = articleVenduService.consulterArticlesEnCoursDeVente();
        List<Categorie> categories = categorieService.consulterCategories();

        model.addAttribute("showLoginModal", true);
        model.addAttribute("utilisateurDTO", new UtilisateurDTO());
        model.addAttribute("articles", articles);
        model.addAttribute("categories", categories);
        model.addAttribute("utilisateur", utilisateurSession);

        return "index";
    }
    @PostMapping("/inscription")
    public String inscription(
            @ModelAttribute("utilisateurSession") Utilisateur utilisateurSession,
            @Valid @ModelAttribute("utilisateurDTO") UtilisateurDTO utilisateurDTO,
            BindingResult bindingResult,
            Model model){
        Utilisateur nouvelUtilisateur = new Utilisateur();

        if(utilisateurDTO.getNewMotDePasse().equals(utilisateurDTO.getConfirmeMotDePasse())){
            nouvelUtilisateur.setMotDePasse(utilisateurDTO.getNewMotDePasse());
        }
        else{
            ObjectError error = new ObjectError("globalError", "Les mots de passe ne sont pas identiques");
            bindingResult.addError(error);
        }
        nouvelUtilisateur.setPseudo(utilisateurDTO.getPseudo());
        nouvelUtilisateur.setNom(utilisateurDTO.getNom());
        nouvelUtilisateur.setPrenom(utilisateurDTO.getPrenom());
        nouvelUtilisateur.setEmail(utilisateurDTO.getEmail());
        nouvelUtilisateur.setTelephone(utilisateurDTO.getTelephone());
        nouvelUtilisateur.setRue(utilisateurDTO.getRue());
        nouvelUtilisateur.setCodePostal(utilisateurDTO.getCodePostal());
        nouvelUtilisateur.setVille(utilisateurDTO.getVille());
        nouvelUtilisateur.setAdmin(false);
        System.out.println(nouvelUtilisateur);

        if(!bindingResult.hasErrors()){
            try {
                utilisateurService.enregistrerUtilisateur(nouvelUtilisateur);
            }
            catch (UtilisateurException utilisateurException) {
                utilisateurException.getMessages().forEach(message -> {
                    ObjectError error = new ObjectError("globalError", message);
                    System.out.println(message);
                    bindingResult.addError(error);
                });
            }
        }


        if(bindingResult.hasErrors()){
            if (utilisateurSession != null) {
                model.addAttribute("utilisateur", utilisateurSession);
            } else {
                model.addAttribute("utilisateur", new Utilisateur());
            }
            List<ArticleVendu> articles = articleVenduService.consulterArticlesEnCoursDeVente();
            List<Categorie> categories = categorieService.consulterCategories();
            model.addAttribute("showInscriptionModal", true);
            model.addAttribute("articles", articles);
            model.addAttribute("categories", categories);
            model.addAttribute("utilisateurDTO", utilisateurDTO);

            return "index";
        }

        return "redirect:/login";

    }
    @GetMapping("login/connect/{pseudo}")
    public String connect(HttpSession s ,
                          @PathVariable("pseudo") String pseudo,Model model){
        Utilisateur utilisateurSession = utilisateurService.findByUserName(pseudo);
        System.out.println("passage login/connect :"+utilisateurSession);
        s.setAttribute("utilisateurSession", utilisateurSession);
        return "redirect:/";
    }

}
