package fr.eni.tp.spring_encheres.ihm;

import fr.eni.tp.spring_encheres.bll.UtilisateurService;
import fr.eni.tp.spring_encheres.bo.Utilisateur;
import fr.eni.tp.spring_encheres.exception.UtilisateurException;
import fr.eni.tp.spring_encheres.ihm.dto.UtilisateurDTO;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
public class ProfilController {

    private final UtilisateurService utilisateurService;

    public ProfilController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    /**
     * Affiche le profil de l’utilisateur actuellement connecté.
     */
    @GetMapping("/monProfil")
    public String afficherProfil(
            @ModelAttribute("utilisateurSession") Utilisateur utilisateurSession,
            Model model
    ) {
        UtilisateurDTO utilisateurDTO = new UtilisateurDTO();
        utilisateurDTO.setPseudo(utilisateurSession.getPseudo());
        utilisateurDTO.setNom(utilisateurSession.getNom());
        utilisateurDTO.setPrenom(utilisateurSession.getPrenom());
        utilisateurDTO.setEmail(utilisateurSession.getEmail());
        utilisateurDTO.setTelephone(utilisateurSession.getTelephone());
        utilisateurDTO.setRue(utilisateurSession.getRue());
        utilisateurDTO.setCodePostal(utilisateurSession.getCodePostal());
        utilisateurDTO.setVille(utilisateurSession.getVille());

        model.addAttribute("utilisateur", utilisateurSession);
        model.addAttribute("utilisateurDTO", utilisateurDTO);
        return "profil/monProfil"; // Affichage du profil utilisateur
    }

    /**
     * Met à jour les informations du profil de l’utilisateur connecté.
     */
    @PostMapping("/monProfil")
    public String modifierProfil(
            @ModelAttribute("utilisateurSession") Utilisateur utilisateurSession,
            @Valid @ModelAttribute("utilisateurDTO") UtilisateurDTO utilisateurDTO,
            BindingResult bindingResult,
            Model model
    ) {
        if (utilisateurSession == null || utilisateurSession.getNoUtilisateur() == 0) {
            return "redirect:/login"; // Redirection vers la page de connexion
        }
        Utilisateur utilisateurModif = new Utilisateur();
        // On vérifie si un nouveau mot de passe a été renseigné et ensuite on fait la comparaison du nouveau mots de passe et de sa confirmation
        if (utilisateurDTO.getNewMotDePasse() == null || utilisateurDTO.getNewMotDePasse().isBlank())
        {
            utilisateurModif.setMotDePasse(utilisateurSession.getMotDePasse());
        }
        else{
            if(utilisateurDTO.getNewMotDePasse().equals(utilisateurDTO.getConfirmeMotDePasse())){
                utilisateurModif.setMotDePasse(utilisateurDTO.getNewMotDePasse());
            }
            else{
               ObjectError error = new ObjectError("globalError", "Les mots de passe ne sont pas identiques");
               bindingResult.addError(error);
            }
        }
        if(!bindingResult.hasErrors()){
            utilisateurModif.setNoUtilisateur(utilisateurSession.getNoUtilisateur());
            utilisateurModif.setPseudo(utilisateurDTO.getPseudo());
            utilisateurModif.setNom(utilisateurDTO.getNom());
            utilisateurModif.setPrenom(utilisateurDTO.getPrenom());

            utilisateurModif.setEmail(utilisateurDTO.getEmail());
            utilisateurModif.setTelephone(utilisateurDTO.getTelephone());
            utilisateurModif.setRue(utilisateurDTO.getRue());
            utilisateurModif.setCodePostal(utilisateurDTO.getCodePostal());
            utilisateurModif.setVille(utilisateurDTO.getVille());
            utilisateurModif.setCredit(utilisateurSession.getCredit());
            utilisateurModif.setAdmin(utilisateurSession.isAdmin());

            // Enregistrement des modifications dans la base de données
            try{
                System.out.println(utilisateurModif);
                utilisateurService.update(utilisateurModif);
            }
            catch (UtilisateurException utilisateurException){
                utilisateurException.getMessages().forEach(message -> {
                    ObjectError error = new ObjectError("globalError", message);
                    System.out.println(message);
                    bindingResult.addError(error);
                });
            }
        }


        if(bindingResult.hasErrors()) {
            UtilisateurDTO utilDTO = new UtilisateurDTO();
            utilDTO.setPseudo(utilisateurSession.getPseudo());
            utilDTO.setNom(utilisateurSession.getNom());
            utilDTO.setPrenom(utilisateurSession.getPrenom());
            utilDTO.setEmail(utilisateurSession.getEmail());
            utilDTO.setTelephone(utilisateurSession.getTelephone());
            utilDTO.setRue(utilisateurSession.getRue());
            utilDTO.setCodePostal(utilisateurSession.getCodePostal());
            utilDTO.setVille(utilisateurSession.getVille());

            model.addAttribute("utilisateur", utilisateurSession);
            model.addAttribute("utilisateurDTO", utilisateurDTO);
            return "profil/monProfil"; // Affichage du profil utilisateur
        }
        // Mise à jour de la session utilisateur
        model.addAttribute("utilisateurSession", utilisateurModif);
        return "redirect:/monProfil"; // Redirection vers le profil après mise à jour
    }

    /**
     * Affiche le profil public d’un utilisateur à partir de son pseudo.
     */
    @GetMapping("/profil/{pseudoConsulter}")
    public String afficherProfilParPseudo(@ModelAttribute("utilisateurSession") Utilisateur utilisateurSession, @PathVariable("pseudoConsulter") String pseudo, Model model) {
        System.out.println(utilisateurSession);
        Utilisateur utilisateurConsulte = utilisateurService.findByUserName(pseudo);

        if (utilisateurConsulte == null) {
            model.addAttribute("messageErreur", "Utilisateur non trouvé.");
            return "erreur"; // Redirection vers une page d'erreur ou retour à la recherche

        }
        System.out.println(utilisateurSession);
        UtilisateurDTO utilisateurDTO = new UtilisateurDTO();

        utilisateurDTO.setPseudo(utilisateurConsulte.getPseudo());
        utilisateurDTO.setNom(utilisateurConsulte.getNom());
        utilisateurDTO.setPrenom(utilisateurConsulte.getPrenom());
        utilisateurDTO.setEmail(utilisateurConsulte.getEmail());
        utilisateurDTO.setTelephone(utilisateurConsulte.getTelephone());
        utilisateurDTO.setRue(utilisateurConsulte.getRue());
        utilisateurDTO.setCodePostal(utilisateurConsulte.getCodePostal());
        utilisateurDTO.setVille(utilisateurConsulte.getVille());
        System.out.println(utilisateurSession);
        model.addAttribute("utilisateur", utilisateurSession);
        model.addAttribute("utilisateurDTO", utilisateurDTO);
        System.out.println(utilisateurSession);
        return "profil/profil";
    }
    @ModelAttribute("utilisateurSession")
    public Utilisateur utilisateurSession(HttpSession session) {
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateurSession");
        return utilisateur != null ? utilisateur : new Utilisateur(); // ou null si tu veux gérer l'absence
    }
}
