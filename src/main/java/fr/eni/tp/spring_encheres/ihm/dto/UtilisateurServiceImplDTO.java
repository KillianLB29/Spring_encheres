package fr.eni.tp.spring_encheres.ihm.dto;

import fr.eni.tp.spring_encheres.bo.Utilisateur;
import fr.eni.tp.spring_encheres.dal.UtilisateurDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("utilisateurServiceDTO")
public class UtilisateurServiceImplDTO implements UtilisateurServiceDTO {

    private final UtilisateurDAO utilisateurDAO;

    @Autowired
    public UtilisateurServiceImplDTO(UtilisateurDAO utilisateurDAO) {
        this.utilisateurDAO = utilisateurDAO;
    }

    private UtilisateurDTO convertirEnDTO(Utilisateur utilisateur) {
        return new UtilisateurDTO(
                utilisateur.getNoUtilisateur(),
                utilisateur.getPseudo(),
                utilisateur.getNom(),
                utilisateur.getPrenom(),
                utilisateur.getEmail(),
                utilisateur.getTelephone(),
                utilisateur.getRue(),
                utilisateur.getCodePostal(),
                utilisateur.getVille(),
                utilisateur.getMotDePasse(),
                utilisateur.getCredit(),
                utilisateur.isAdmin()
        );
    }

    private Utilisateur convertirEnEntity(UtilisateurDTO utilisateurDTO) {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNoUtilisateur(utilisateurDTO.getNoUtilisateur());
        utilisateur.setPseudo(utilisateurDTO.getPseudo());
        utilisateur.setNom(utilisateurDTO.getNom());
        utilisateur.setPrenom(utilisateurDTO.getPrenom());
        utilisateur.setEmail(utilisateurDTO.getEmail());
        utilisateur.setTelephone(utilisateurDTO.getTelephone());
        utilisateur.setRue(utilisateurDTO.getRue());
        utilisateur.setCodePostal(utilisateurDTO.getCodePostal());
        utilisateur.setVille(utilisateurDTO.getVille());
        utilisateur.setMotDePasse(utilisateurDTO.getMotDePasse());
        utilisateur.setCredit(utilisateurDTO.getCredit());
        utilisateur.setAdmin(utilisateurDTO.isAdmin());
        return utilisateur;
    }

    @Override
    public List<UtilisateurDTO> getUtilisateur() {
        List<Utilisateur> utilisateurs = utilisateurDAO.findAll();
        return utilisateurs.stream().map(this::convertirEnDTO).toList();
    }

    @Override
    public UtilisateurDTO findById(long idUtilisateur) {
        Utilisateur utilisateur = utilisateurDAO.read(idUtilisateur);
        return utilisateur == null ? null : convertirEnDTO(utilisateur);
    }

    @Override
    public void enregistrerUtilisateur(UtilisateurDTO utilisateurDTO) {
        Utilisateur utilisateur = convertirEnEntity(utilisateurDTO);
        utilisateurDAO.save(utilisateur);
    }

    @Override
    public void supprimerUtilisateur(long idUtilisateur) {
        utilisateurDAO.delete(idUtilisateur);
    }

    @Override
    public UtilisateurDTO findByUserName(String username) {
        Utilisateur utilisateur = utilisateurDAO.findByPseudo(username);
        return utilisateur == null ? null : convertirEnDTO(utilisateur);
    }

    @Override
    public void retirerPoints(long pointsRequis, long idUtilisateur) {
        Utilisateur utilisateur = utilisateurDAO.read(idUtilisateur);
        if (utilisateur != null) {
            utilisateur.setCredit(utilisateur.getCredit() - pointsRequis);
            utilisateurDAO.update(utilisateur);
        }
    }

    @Override
    public void ajouterPoint(long sommeARecredite, long idAncienEncherisseur) {
        Utilisateur utilisateur = utilisateurDAO.read(idAncienEncherisseur);
        if (utilisateur != null) {
            utilisateur.setCredit(utilisateur.getCredit() + sommeARecredite);
            utilisateurDAO.update(utilisateur);
        }
    }

    @Override
    public void update(UtilisateurDTO utilisateurDTO) {
        Utilisateur utilisateur = convertirEnEntity(utilisateurDTO);
        utilisateurDAO.update(utilisateur);
    }
}
