package fr.eni.tp.spring_encheres.ihm;

import fr.eni.tp.spring_encheres.bll.UtilisateurService;
import fr.eni.tp.spring_encheres.bo.Utilisateur;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("utilisateurSession")
public class ProfilController {

    private final UtilisateurService utilisateurService;

    public ProfilController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    /**
     * Initialise un utilisateur dans la session si nécessaire.
     */
    @ModelAttribute("utilisateurSession")
    public Utilisateur getUtilisateurSession() {
        // Ne pas créer un UtilisateurDTO avec des valeurs par défaut.
        return null; // Aucun utilisateur connecté par défaut
    }

    /**
     * Affiche le profil de l’utilisateur actuellement connecté.
     */
    @GetMapping("/monProfil")
    public String afficherProfil(
            @ModelAttribute("utilisateurSession") Utilisateur utilisateurSession,
            Model model
    ) {
        if (utilisateurSession == null || utilisateurSession.getNoUtilisateur() == 0) {
            return "redirect:/login"; // Redirection vers la page de connexion si non connecté
        }

        // Recherche de l'utilisateur en base
        Utilisateur utilisateur = utilisateurService.findById(utilisateurSession.getNoUtilisateur());
        if (utilisateur == null) {
            model.addAttribute("messageErreur", "Utilisateur introuvable.");
            return "erreur"; // Page d'erreur si l'utilisateur n'est pas trouvé
        }

        model.addAttribute("utilisateur", utilisateur);
        return "profil/monProfil"; // Affichage du profil utilisateur
    }

    /**
     * Met à jour les informations du profil de l’utilisateur connecté.
     */
    @PostMapping("/monProfil")
    public String modifierProfil(
            @ModelAttribute("utilisateur") Utilisateur utilisateurModifie,
            @ModelAttribute("utilisateurSession") Utilisateur utilisateurSession,
            Model model
    ) {
        if (utilisateurSession == null || utilisateurSession.getNoUtilisateur() == 0) {
            return "redirect:/login"; // Redirection vers la page de connexion
        }

        // Préservation des données sensibles de l'utilisateur connecté
        utilisateurModifie.setNoUtilisateur(utilisateurSession.getNoUtilisateur());
        utilisateurModifie.setMotDePasse(utilisateurSession.getMotDePasse());
        utilisateurModifie.setCredit(utilisateurSession.getCredit());
        utilisateurModifie.setAdmin(utilisateurSession.isAdmin());

        // Enregistrement des modifications dans la base de données
        utilisateurService.enregistrerUtilisateur(utilisateurModifie);

        // Mise à jour de la session utilisateur
        model.addAttribute("utilisateurSession", utilisateurModifie);
        model.addAttribute("message", "Profil mis à jour avec succès !");
        return "redirect:/monProfil"; // Redirection vers le profil après mise à jour
    }

    /**
     * Affiche le profil public d’un utilisateur à partir de son pseudo.
     */
    @GetMapping("/profil/{pseudo}")
    public String afficherProfilParPseudo(@PathVariable("pseudo") String pseudo, Model model) {
        Utilisateur utilisateur = utilisateurService.findByUserName(pseudo);

        if (utilisateur == null) {
            model.addAttribute("messageErreur", "Utilisateur non trouvé.");
            return "erreur"; // Redirection vers une page d'erreur ou retour à la recherche
        }

        model.addAttribute("utilisateur", utilisateur);
        return "profil/profil"; // Affichage du profil public
    }
}
