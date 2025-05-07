package fr.eni.tp.spring_encheres.ihm;

import fr.eni.tp.spring_encheres.bo.Utilisateur;
import fr.eni.tp.spring_encheres.ihm.dto.UtilisateurDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class AdminController {

    @GetMapping("/admin")
    public String afficherPageAdmin(@ModelAttribute("utilisateurSession") Utilisateur utilisateurSession, Model model) {

        // Si l'utilisateur n'est pas connecté
        if (utilisateurSession == null || utilisateurSession.getPseudo() == null) {
            return "redirect:/login";
        }

        // Si l'utilisateur n'est pas admin
        if (!utilisateurSession.isAdmin()) {
            return "redirect:/";
        }

        // Ajouter le DTO à la vue
        UtilisateurDTO utilisateurDTO = new UtilisateurDTO(utilisateurSession);
        model.addAttribute("utilisateurDTO", utilisateurDTO);

        return "admin"; // renvoie vers admin.html
    }
}
