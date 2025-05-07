package fr.eni.tp.spring_encheres.ihm;

import fr.eni.tp.spring_encheres.bll.UtilisateurService;
import fr.eni.tp.spring_encheres.bo.Utilisateur;
import fr.eni.tp.spring_encheres.ihm.dto.UtilisateurDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AdminController {

    @Autowired
    private UtilisateurService utilisateurService;  // Injection du service pour accéder aux utilisateurs

    // Méthode pour afficher les détails d'un utilisateur par son pseudo
    @GetMapping("/admin/utilisateur/{pseudo}")
    public String afficherUtilisateur(@PathVariable String pseudo, Model model) {
        // Récupérer l'utilisateur depuis la base de données via le service
        Utilisateur utilisateur = utilisateurService.findByUserName(pseudo);

        if (utilisateur == null) {
            // Gérer le cas où l'utilisateur n'existe pas
            model.addAttribute("error", "Utilisateur introuvable");
            return "admin/error";  // Afficher une page d'erreur si l'utilisateur n'existe pas
        }

        // Convertir l'objet Utilisateur en UtilisateurDTO
        UtilisateurDTO utilisateurDTO = new UtilisateurDTO(utilisateur);

        // Ajouter l'objet UtilisateurDTO au modèle pour l'afficher dans la vue
        model.addAttribute("utilisateurDTO", utilisateurDTO);

        // Retourner le nom de la vue pour afficher les informations utilisateur
        return "admin/utilisateur-detail";  // Le nom de la page HTML où tu affiches les détails
    }
}
