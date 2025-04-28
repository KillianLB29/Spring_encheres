package fr.eni.tp.spring_encheres.bo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ArticleVendu {
    private long noArticle;
    private String nomArticle;
    private String description;
    private LocalDate dateDebutEncheres;
    private LocalDate dateFinEncheres;
    private double miseAPrix;
    private double prixVente;
    private String etatVente;
    private Utilisateur utilisateur;
    private Categorie categorie;
    private Retrait lieuRetrait;
    private List<Enchere> encheresEnCours = new ArrayList<>();

    public ArticleVendu() {

    }

    public ArticleVendu(long noArticle, String nomArticle, String description, LocalDate dateDebutEncheres, LocalDate dateFinEncheres, double miseAPrix, double prixVente, String etatVente, Utilisateur utilisateur, Categorie categorie, Retrait lieuRetrait) {
        this.noArticle = noArticle;
        this.nomArticle = nomArticle;
        this.description = description;
        this.dateDebutEncheres = dateDebutEncheres;
        this.dateFinEncheres = dateFinEncheres;
        this.miseAPrix = miseAPrix;
        this.prixVente = prixVente;
        this.etatVente = etatVente;
        this.utilisateur = utilisateur;
        this.categorie = categorie;
        this.lieuRetrait = lieuRetrait;
    }

    public long getNoArticle() {
        return noArticle;
    }

    public void setNoArticle(long noArticle) {
        this.noArticle = noArticle;
    }

    public String getNomArticle() {
        return nomArticle;
    }

    public void setNomArticle(String nomArticle) {
        this.nomArticle = nomArticle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDateDebutEncheres() {
        return dateDebutEncheres;
    }

    public void setDateDebutEncheres(LocalDate dateDebutEncheres) {
        this.dateDebutEncheres = dateDebutEncheres;
    }

    public LocalDate getDateFinEncheres() {
        return dateFinEncheres;
    }

    public void setDateFinEncheres(LocalDate dateFinEncheres) {
        this.dateFinEncheres = dateFinEncheres;
    }

    public double getMiseAPrix() {
        return miseAPrix;
    }

    public void setMiseAPrix(double miseAPrix) {
        this.miseAPrix = miseAPrix;
    }

    public double getPrixVente() {
        return prixVente;
    }

    public void setPrixVente(double prixVente) {
        this.prixVente = prixVente;
    }

    public String getEtatVente() {
        return etatVente;
    }

    public void setEtatVente(String etatVente) {
        this.etatVente = etatVente;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public Retrait getLieuRetrait() {
        return lieuRetrait;
    }

    public void setLieuRetrait(Retrait lieuRetrait) {
        this.lieuRetrait = lieuRetrait;
    }

    public List<Enchere> getEncheresEnCours() {
        return encheresEnCours;
    }

    public void setEncheresEnCours(List<Enchere> encheresEnCours) {
        this.encheresEnCours = encheresEnCours;
    }

    @Override
    public String toString() {
        return "ArticleVendu{" +
                "noArticle=" + noArticle +
                ", nomArticle='" + nomArticle + '\'' +
                ", description='" + description + '\'' +
                ", dateDebutEncheres=" + dateDebutEncheres +
                ", dateFinEncheres=" + dateFinEncheres +
                ", miseAPrix=" + miseAPrix +
                ", prixVente=" + prixVente +
                ", etatVente='" + etatVente + '\'' +
                ", utilisateur=" + utilisateur +
                ", categorie=" + categorie +
                ", lieuRetrait=" + lieuRetrait +
                ", encheresEnCours=" + encheresEnCours +
                '}';
    }
}
