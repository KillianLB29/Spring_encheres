package fr.eni.tp.spring_encheres.ihm;

import fr.eni.tp.spring_encheres.bll.UtilisateurService;
import fr.eni.tp.spring_encheres.bo.Utilisateur;
import fr.eni.tp.spring_encheres.ihm.dto.UtilisateurDTO;
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
//        if (utilisateurSession == null || utilisateurSession.getNoUtilisateur() == 0) {
//            return "redirect:/login"; // Redirection vers la page de connexion si non connecté
//        }

        // Recherche de l'utilisateur en base
        Utilisateur utilisateur = utilisateurService.findById(utilisateurSession.getNoUtilisateur());
        if (utilisateur == null) {
            model.addAttribute("messageErreur", "Utilisateur introuvable.");
            return "erreur"; // Page d'erreur si l'utilisateur n'est pas trouvé
        }
        UtilisateurDTO utilisateurDTO = new UtilisateurDTO();
        utilisateurDTO.setPseudo(utilisateur.getPseudo());
        utilisateurDTO.setNom(utilisateur.getNom());
        utilisateurDTO.setPrenom(utilisateur.getPrenom());
        utilisateurDTO.setEmail(utilisateur.getEmail());
        utilisateurDTO.setTelephone(utilisateur.getTelephone());
        utilisateurDTO.setRue(utilisateur.getRue());
        utilisateurDTO.setCodePostal(utilisateur.getCodePostal());
        utilisateurDTO.setVille(utilisateur.getVille());

        model.addAttribute("utilisateur", utilisateurSession);
        model.addAttribute("utilisateurDTO", utilisateurDTO);
        return "profil/monProfil"; // Affichage du profil utilisateur
    }

    /**
     * Met à jour les informations du profil de l’utilisateur connecté.
     */
    @PostMapping("/monProfil")
    public String modifierProfil(
            @ModelAttribute("utilisateurDTO") UtilisateurDTO utilisateurDTO,
            @ModelAttribute("utilisateurSession") Utilisateur utilisateurSession,
            Model model
    ) {
        if (utilisateurSession == null || utilisateurSession.getNoUtilisateur() == 0) {
            return "redirect:/login"; // Redirection vers la page de connexion
        }
        Utilisateur utilisateurModif = new Utilisateur();
        utilisateurModif.setNoUtilisateur(utilisateurSession.getNoUtilisateur());
        utilisateurModif.setPseudo(utilisateurDTO.getPseudo());
        utilisateurModif.setNom(utilisateurDTO.getNom());
        utilisateurModif.setPrenom(utilisateurDTO.getPrenom());
        // Comparaison du nouveau mots de passe et de sa confirmation
        if (utilisateurDTO.getNewMotDePasse() != null && !utilisateurDTO.getNewMotDePasse().isBlank()) {
            if (utilisateurDTO.getNewMotDePasse().equals(utilisateurDTO.getConfirmeMotDePasse())) {
                utilisateurModif.setMotDePasse(utilisateurDTO.getNewMotDePasse());
            } else {
                model.addAttribute("messageErreur", "Les mots de passe ne correspondent pas.");
                return "profil/monProfil";
            }
        }
        utilisateurModif.setEmail(utilisateurDTO.getEmail());
        utilisateurModif.setTelephone(utilisateurDTO.getTelephone());
        utilisateurModif.setRue(utilisateurDTO.getRue());
        utilisateurModif.setCodePostal(utilisateurDTO.getCodePostal());
        utilisateurModif.setVille(utilisateurDTO.getVille());
        // Préservation des données sensibles de l'utilisateur connecté
        utilisateurModif.setNoUtilisateur(utilisateurSession.getNoUtilisateur());
        utilisateurModif.setCredit(utilisateurSession.getCredit());
        utilisateurModif.setAdmin(utilisateurSession.isAdmin());
        System.out.println(utilisateurModif);

        // Enregistrement des modifications dans la base de données
        utilisateurService.update(utilisateurModif);

        // Mise à jour de la session utilisateur
        model.addAttribute("utilisateurSession", utilisateurModif);

        model.addAttribute("message", "Profil mis à jour avec succès !");
        return "redirect:/monProfil"; // Redirection vers le profil après mise à jour
    }

    /**
     * Affiche le profil public d’un utilisateur à partir de son pseudo.
     */
    @GetMapping("/profil/{pseudo}")
    public String afficherProfilParPseudo(@ModelAttribute("utilisateurSession") Utilisateur utilisateurSession, @PathVariable("pseudo") String pseudo, Model model) {
        Utilisateur utilisateur = utilisateurService.findByUserName(pseudo);

        if (utilisateur == null) {
            model.addAttribute("messageErreur", "Utilisateur non trouvé.");
            return "erreur"; // Redirection vers une page d'erreur ou retour à la recherche

        }
        UtilisateurDTO utilisateurDTO = new UtilisateurDTO();

        utilisateurDTO.setPseudo(utilisateur.getPseudo());
        utilisateurDTO.setNom(utilisateur.getNom());
        utilisateurDTO.setPrenom(utilisateur.getPrenom());
        utilisateurDTO.setEmail(utilisateur.getEmail());
        utilisateurDTO.setTelephone(utilisateur.getTelephone());
        utilisateurDTO.setRue(utilisateur.getRue());
        utilisateurDTO.setCodePostal(utilisateur.getCodePostal());
        utilisateurDTO.setVille(utilisateur.getVille());
        model.addAttribute("utilisateur", utilisateurSession);
        model.addAttribute("utilisateurDTO", utilisateurDTO);
        return "profil/profil";
    }
}
