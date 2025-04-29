package fr.eni.tp.spring_encheres.ihm;

import fr.eni.tp.spring_encheres.bll.UtilisateurService;
import fr.eni.tp.spring_encheres.bo.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;

@Controller
public class ConnexionController {

    @Autowired
    private UtilisateurService utilisateurService;

    @GetMapping("/connexion")
    public String afficherFormulaireConnexion(Model model) {
        return "connexion";
    }

    @PostMapping("/connexion")
    public String connexion(@RequestParam String pseudo, @RequestParam String motDePasse, HttpSession session, Model model) {
        Utilisateur utilisateur = utilisateurService.findByUserName(pseudo);

        if (utilisateur != null && utilisateur.getMotDePasse().equals(motDePasse)) {
            session.setAttribute("utilisateur", utilisateur);
            return "redirect:/";
        }

        model.addAttribute("erreur", "Identifiants invalides.");
        return "connexion";
    }
}
