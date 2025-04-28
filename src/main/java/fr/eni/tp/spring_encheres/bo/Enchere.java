package fr.eni.tp.spring_encheres.bo;

import java.sql.Date;
import java.time.LocalDate;

public class Enchere {
    private LocalDate dateEnchere;
    private int montantEnchere;

    private Integer idUtilisateur;
    private Integer idArticle;

    // Constructeur par défaut
    public Enchere() {
    }

    // Constructeur avec LocalDate au lieu de java.sql.Date
    public Enchere(Integer idUtilisateur, Integer idArticle, LocalDate dateEnchere, int montantEnchere) {
        this.idUtilisateur = idUtilisateur;
        this.idArticle = idArticle;
        this.dateEnchere = dateEnchere;
        this.montantEnchere = montantEnchere;
    }

    // Getter pour dateEnchere, qui retourne un java.sql.Date
    public Date getDateEnchere() {
        return Date.valueOf(dateEnchere);  // Conversion de LocalDate en java.sql.Date
    }

    // Setter pour dateEnchere, qui prend un java.sql.Date
    public void setDateEnchere(Date dateEnchere) {
        this.dateEnchere = dateEnchere.toLocalDate();  // Conversion de java.sql.Date en LocalDate
    }

    // Getter pour montantEnchere
    public int getMontantEnchere() {
        return montantEnchere;
    }

    // Setter pour montantEnchere
    public void setMontantEnchere(int montantEnchere) {
        this.montantEnchere = montantEnchere;
    }

    // Getter pour idUtilisateur
    public Integer getIdUtilisateur() {
        return idUtilisateur;
    }

    // Setter pour idUtilisateur
    public void setIdUtilisateur(Integer idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    // Getter pour idArticle
    public Integer getIdArticle() {
        return idArticle;
    }

    // Setter pour idArticle
    public void setIdArticle(Integer idArticle) {
        this.idArticle = idArticle;
    }

    // Méthode toString pour afficher les informations de l'enchère
    @Override
    public String toString() {
        return "Enchere [dateEnchere=" + dateEnchere + ", montantEnchere=" + montantEnchere + ", idUtilisateur="
                + idUtilisateur + ", idArticle=" + idArticle + "]";
    }
}
