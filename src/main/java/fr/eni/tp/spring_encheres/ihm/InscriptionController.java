package fr.eni.tp.spring_encheres.ihm;

import fr.eni.tp.spring_encheres.bll.UtilisateurService;
import fr.eni.tp.spring_encheres.bo.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

@Controller
public class InscriptionController {

    @Autowired
    private UtilisateurService utilisateurService;

    @GetMapping("/inscription")
    public String afficherFormulaireInscription(Model model) {
        model.addAttribute("utilisateur", new Utilisateur());
        return "inscription";
    }

    @PostMapping("/inscription")
    public String inscription(@ModelAttribute Utilisateur utilisateur, HttpSession session, Model model) {

        // Initialisation des valeurs par défaut
        utilisateur.setCredit(100);

        // Correction ici : setAdministrateur() n'existe pas, on utilise setAdmin()
        utilisateur.setAdmin(false);

        try {
            utilisateurService.enregistrerUtilisateur(utilisateur);
            session.setAttribute("utilisateur", utilisateur);
            return "redirect:/";
        } catch (Exception e) {
            model.addAttribute("erreur", "Erreur lors de l'inscription : " + e.getMessage());
            return "inscription";
        }
    }
    // Remarque : la méthode setAdministrateur() n'existait pas dans la classe Utilisateur.
    // L'attribut utilisé dans le modèle est 'admin' (de type boolean),
    // donc les accesseurs conformes à JavaBean sont : isAdmin() et setAdmin(boolean).
    // On utilise donc setAdmin(false) pour définir qu’un utilisateur n’est pas administrateur par défaut.

}
