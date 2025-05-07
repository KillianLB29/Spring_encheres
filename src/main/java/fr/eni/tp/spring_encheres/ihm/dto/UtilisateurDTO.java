package fr.eni.tp.spring_encheres.ihm.dto;

import fr.eni.tp.spring_encheres.bo.Utilisateur;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class UtilisateurDTO {
    @NotBlank(message = "Le pseudo est obligatoire")
    private String pseudo;
    @NotBlank(message = "Le nom est obligatoire")
    private String nom;
    @NotBlank(message = "Le prenom est obligatoire")
    private String prenom;
    private String NewMotDePasse;
    private String ConfirmeMotDePasse;
    @NotBlank(message = "L'email est obligatoire")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$",message = "L'email n'est pas au bon format")
    private String email;
    @NotBlank(message = "Le telephone est obligatoire")
    private String telephone;
    @NotBlank(message = "La rue est obligatoire")
    private String rue;
    @NotBlank(message = "Le codePostal est obligatoire")
    private String codePostal;
    @NotBlank(message = "La ville est obligatoire")
    private String ville;


    // Constructeur
    public UtilisateurDTO(long noUtilisateur, String pseudo, String nom, String prenom, String email, String telephone, String rue, String codePostal, String ville, String motDePasse, long credit, boolean admin) {
        this.pseudo = pseudo;
        this.nom = nom;
        this.prenom = prenom;
        this.NewMotDePasse = NewMotDePasse;
        this.ConfirmeMotDePasse = ConfirmeMotDePasse;
        this.email = email;
        this.telephone = telephone;
        this.rue = rue;
        this.codePostal = codePostal;
        this.ville = ville;
    }

    public UtilisateurDTO() {

    }
    public UtilisateurDTO(Utilisateur utilisateur) {
        this.pseudo = utilisateur.getPseudo();
        this.nom = utilisateur.getNom();
        this.prenom = utilisateur.getPrenom();
        this.email = utilisateur.getEmail();
        this.telephone = utilisateur.getTelephone();
        this.rue = utilisateur.getRue();
        this.codePostal = utilisateur.getCodePostal();
        this.ville = utilisateur.getVille();
    }


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("UtilisateurDTO{");
        sb.append("pseudo='").append(pseudo).append('\'');
        sb.append(", nom='").append(nom).append('\'');
        sb.append(", prenom='").append(prenom).append('\'');
        sb.append(", NewMotDePasse='").append(NewMotDePasse).append('\'');
        sb.append(", ConfirmeMotDePasse='").append(ConfirmeMotDePasse).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", telephone='").append(telephone).append('\'');
        sb.append(", rue='").append(rue).append('\'');
        sb.append(", codePostal='").append(codePostal).append('\'');
        sb.append(", ville='").append(ville).append('\'');
        sb.append('}');
        return sb.toString();
    }

    // Getters et Setters
    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNewMotDePasse() {
        return NewMotDePasse;
    }

    public void setNewMotDePasse(String newMotDePasse) {
        this.NewMotDePasse = newMotDePasse;
    }

    public String getConfirmeMotDePasse() {
        return ConfirmeMotDePasse;
    }

    public void setConfirmeMotDePasse(String confirmeMotDePasse) {
        this.ConfirmeMotDePasse = confirmeMotDePasse;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

}
