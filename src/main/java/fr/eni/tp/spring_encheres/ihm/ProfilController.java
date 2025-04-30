package fr.eni.tp.spring_encheres.ihm;

import fr.eni.tp.spring_encheres.bll.UtilisateurService;
import fr.eni.tp.spring_encheres.bo.Utilisateur;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("utilisateurSession")
public class ProfilController {

    private UtilisateurService utilisateurService;

    public ProfilController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    /*TODO Modifier monProfil pour afficher un autre formulaire pour modifier le mot de passe etc/Actuellement monProfil affiche en réalité
    le profil de l'utilisateur du point de vue admin.
    */

    @GetMapping("/monProfil")
    public String afficherProfil(@ModelAttribute("utilisateurSession") Utilisateur utilisateurSession, Model model) {
        model.addAttribute("utilisateur", utilisateurSession);
        return "profil/monProfil";
    }

    @GetMapping("/profil/{pseudo}")
    public String profil(@PathVariable("pseudo") String pseudo, Model model) {
        Utilisateur util = utilisateurService.findByUserName(pseudo);
        System.out.println(util);
        model.addAttribute("utilisateur", util);
        return "profil/profil";
    }
}
