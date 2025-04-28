package fr.eni.tp.spring_encheres.bll;

import fr.eni.tp.spring_encheres.bo.Utilisateur;
import fr.eni.tp.spring_encheres.dal.UtilisateurDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("utilisateurService")
public class UtilisateurServiceImpl implements UtilisateurService {

    private final UtilisateurDAO utilisateurDAO;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UtilisateurServiceImpl(UtilisateurDAO utilisateurDAO, PasswordEncoder passwordEncoder) {
        this.utilisateurDAO = utilisateurDAO;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<Utilisateur> getUtilisateur() {
        return utilisateurDAO.findAll();
    }

    @Override
    public Utilisateur findById(long idUtilisateur) {
        return utilisateurDAO.read(idUtilisateur);
    }

    @Override
    public void enregistrerUtilisateur(Utilisateur utilisateur) {
        utilisateur.setMotDePasse(passwordEncoder.encode(utilisateur.getMotDePasse()));
        utilisateurDAO.save(utilisateur);
    }

    @Override
    public void supprimerUtilisateur(Utilisateur utilisateur) {
        utilisateurDAO.delete(utilisateur.getNoUtilisateur());
    }

    @Override
    public Utilisateur findByUserName(String username) {
        return utilisateurDAO.findByPseudo(username);
    }

    @Override
    public void retirerPoints(long pointsRequis, long idUtilisateur) {
        Utilisateur utilisateur = utilisateurDAO.read(idUtilisateur);
        if (utilisateur != null) {
            utilisateur.setCredit(utilisateur.getCredit() - (int) pointsRequis);
            utilisateurDAO.save(utilisateur);
        }
    }

    @Override
    public void ajouterPoint(long sommeARecredite, long idAncienEncherisseur) {
        Utilisateur utilisateur = utilisateurDAO.read(idAncienEncherisseur);
        if (utilisateur != null) {
            utilisateur.setCredit(utilisateur.getCredit() + (int) sommeARecredite);
            utilisateurDAO.save(utilisateur);
        }
    }
}
