package fr.eni.tp.spring_encheres.bll;

import fr.eni.tp.spring_encheres.bo.Utilisateur;

import java.util.List;

public interface UtilisateurService {
    List<Utilisateur> getUtilisateur();

    Utilisateur findById(long idUtilisateur);

    void enregistrerUtilisateur(Utilisateur utilisateur);

    void supprimerUtilisateur(Utilisateur utilisateur);

    Utilisateur findByUserName(String username);

    void retirerPoints(long pointsRequis, long idUtilisateur);

    void ajouterPoint(long sommeARecredite, long idAncienEncherisseur);

}
