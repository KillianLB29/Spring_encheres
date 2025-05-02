package fr.eni.tp.spring_encheres.bll;

import fr.eni.tp.spring_encheres.bo.UtilisateurDTO;
import java.util.List;

public interface UtilisateurServiceDTO {

    List<UtilisateurDTO> getUtilisateur();

    UtilisateurDTO findById(long idUtilisateur);

    void enregistrerUtilisateur(UtilisateurDTO utilisateurDTO);

    void supprimerUtilisateur(long idUtilisateur);

    UtilisateurDTO findByUserName(String username);

    void retirerPoints(long pointsRequis, long idUtilisateur);

    void ajouterPoint(long sommeARecredite, long idAncienEncherisseur);

    void update(UtilisateurDTO utilisateurDTO);
}
