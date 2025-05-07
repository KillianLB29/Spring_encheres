package fr.eni.tp.spring_encheres.ihm;

import fr.eni.tp.spring_encheres.bo.Utilisateur;
import fr.eni.tp.spring_encheres.ihm.dto.UtilisateurDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class AdminController {

    @GetMapping("/admin")
    public String afficherPageAdmin(@ModelAttribute("utilisateurSession") Utilisateur utilisateurSession, Model model) {

        // Si l'utilisateur n'est pas connecté ou n'est pas un administrateur
        if (utilisateurSession == null || utilisateurSession.getPseudo() == null || !utilisateurSession.isAdmin()) {
            return "redirect:/login";  // Rediriger vers la page de login
        }

        // Si l'utilisateur est admin, on prépare l'objet UtilisateurDTO
        UtilisateurDTO utilisateurDTO = new UtilisateurDTO(utilisateurSession);
        model.addAttribute("utilisateurDTO", utilisateurDTO);

        return "admin";
    }

    // Modèle qui récupère l'utilisateur de la session
    @ModelAttribute("utilisateurSession")
    public Utilisateur utilisateurSession(HttpSession session) {
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateurSession");
        System.out.println("utilisateur session : " + utilisateur);
        return utilisateur != null ? utilisateur : new Utilisateur(); // Retourner un utilisateur vide si aucun utilisateur n'est connecté
    }
}

