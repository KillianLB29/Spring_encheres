package fr.eni.tp.spring_encheres.bll;

import fr.eni.tp.spring_encheres.bo.Utilisateur;
import fr.eni.tp.spring_encheres.dal.UtilisateurDAO;
import fr.eni.tp.spring_encheres.exception.UtilisateurException;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

@Service("utilisateurService")
public class UtilisateurServiceImpl implements UtilisateurService {

    private final UtilisateurDAO utilisateurDAO;
   // private final PasswordEncoder passwordEncoder;

    @Autowired
    public UtilisateurServiceImpl(UtilisateurDAO utilisateurDAO ) {
        this.utilisateurDAO = utilisateurDAO;
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
        utilisateur.setMotDePasse(utilisateur.getMotDePasse());
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
    public void update(Utilisateur utilisateur) throws UtilisateurException {
        UtilisateurException utilisateurException = new UtilisateurException();
        boolean isValid = true;
        isValid&=isPseudoValid(utilisateur.getPseudo(),utilisateurException);
        isValid&=isNomValid(utilisateur.getNom(),utilisateurException);
        isValid&=isPrenomValid(utilisateur.getPrenom(),utilisateurException);
        isValid&=isEmailValid(utilisateur.getEmail(),utilisateurException);
        isValid&=isMotDePasseValid(utilisateur.getMotDePasse(),utilisateurException);
        isValid&=isTelephoneValid(utilisateur.getTelephone(),utilisateurException);
        isValid&=isRueValid(utilisateur.getRue(),utilisateurException);
        isValid&=isCodePostalValid(utilisateur.getCodePostal(),utilisateurException);
        isValid&=isVilleValid(utilisateur.getVille(),utilisateurException);

        if(isValid){
            utilisateurDAO.update(utilisateur);
        }
        else{
            throw utilisateurException;
        }


    }

    public boolean isPseudoValid(String pseudo, UtilisateurException utilisateurException) {
        boolean valid = true;
        if(pseudo == null || pseudo.isEmpty() || pseudo.isBlank()) {
            valid = false;
            utilisateurException.addMessage("Pseudo non renseigné : le pseudo ne peut pas etre vide");
        }
        if(pseudo.length()<5 || pseudo.length()>30)
        {
            valid = false;
            utilisateurException.addMessage("Votre pseudo doit faire entre 5 et 30 caractères");
        }
        return valid;
    }
    public boolean isNomValid(String nom, UtilisateurException utilisateurException) {
        boolean valid = true;
        if(nom == null || nom.isEmpty() ||nom.isBlank()) {
            valid = false;
            utilisateurException.addMessage("Nom non renseigné : le nom ne peut pas etre vide");
        }
        if(nom.length()>30){
            valid = false;
            utilisateurException.addMessage("Le nom ne doit pas faire plus de 30 caractères");
        }
        return valid;
    }
    public boolean isPrenomValid(String prenom, UtilisateurException utilisateurException) {
        boolean valid = true;
        if(prenom == null || prenom.isEmpty() ||prenom.isBlank()) {
            valid = false;
            utilisateurException.addMessage("Prenom non renseigné : le nom ne peut pas etre vide");
        }
        if(prenom.length()>30){
            valid = false;
            utilisateurException.addMessage("Le prenom ne doit pas faire plus de 30 caractères");
        }
        return valid;
    }
    public boolean isEmailValid(String email, UtilisateurException utilisateurException) {
        boolean valid = true;
        if(email == null || email.isEmpty() ||email.isBlank()) {
            valid = false;
            utilisateurException.addMessage("Email non renseigné : le nom ne peut pas etre vide");
        }
        if(email.length()>100){
            valid = false;
            utilisateurException.addMessage("L'email ne doit pas faire plus de 100 caractères");
        }
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
        if(!Pattern.matches(regex, email)){
            valid = false;
            utilisateurException.addMessage("L'email ne respect pas le format d'un email (Exemple : exemple@exemple.com)");
        }
        return valid;
    }
    public boolean isTelephoneValid(String telephone, UtilisateurException utilisateurException) {
        boolean valid = true;
        if(telephone == null || telephone.isEmpty() ||telephone.isBlank()) {
            valid = false;
            utilisateurException.addMessage("Telephone non renseigné : le telephone ne peut pas etre vide");
        }
        if(telephone.length()<10 || telephone.length()>11){
            valid = false;
            utilisateurException.addMessage("Votre numéro de telephone doit faire 10 caractères");
        }
        return valid;
    }
    public boolean isRueValid(String rue, UtilisateurException utilisateurException) {
        boolean valid = true;
        if(rue == null || rue.isEmpty() ||rue.isBlank()) {
            valid = false;
            utilisateurException.addMessage("rue non renseigné : la rue ne peut pas etre vide");
        }
        if(rue.length()>30){
            valid = false;
            utilisateurException.addMessage("La rue doit faire moins de 30 caractères");
        }

        return valid;
    }
    public boolean isCodePostalValid(String codePostal, UtilisateurException utilisateurException) {
        boolean valid = true;
        if(codePostal == null || codePostal.isEmpty() ||codePostal.isBlank()) {
            valid = false;
            utilisateurException.addMessage("Code postal non renseigné : le code postal ne peut pas etre vide");
        }
        if(codePostal.length()<5 || codePostal.length()>7){
            valid = false;
            utilisateurException.addMessage("Le code postal doit faire 5 caractères");
        }
        return valid;
    }
    public boolean isVilleValid(String ville, UtilisateurException utilisateurException) {
        boolean valid = true;
        if(ville == null || ville.isEmpty() ||ville.isBlank()) {
            valid = false;
            utilisateurException.addMessage("Ville non renseigné : la ville ne peut pas etre vide");
        }
        if(ville.length()>30){
            valid = false;
            utilisateurException.addMessage("La ville doit faire moins de 30 caractères");
        }
        return valid;
    }

    public boolean isMotDePasseValid(String motDePasse, UtilisateurException utilisateurException) {
        boolean valid = true;
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{6,}$";
        if(!Pattern.matches(regex, motDePasse)){
            valid = false;
            utilisateurException.addMessage("Le mot de passe doit faire au moins 6 caractères et contenir : 1 majuscule, 1 minuscule, 1 chiffre");
        }
        return valid;
    }




}
