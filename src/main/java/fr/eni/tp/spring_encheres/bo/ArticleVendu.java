package fr.eni.tp.spring_encheres.bo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ArticleVendu {
    private long noArticle;
    private String nomArticle;
    private String description;
    private Date dateDebutEncheres;
    private Date dateFinEncheres;
    private long miseAPrix;
    private long prixVente;
    private String etatVente;
    private Utilisateur utilisateur;
    private Categorie categorie;
    private Retrait lieuRetrait;
    private String urlImage;

    public ArticleVendu() {

    }

    public ArticleVendu(long noArticle, String nomArticle, String description, Date dateDebutEncheres, Date dateFinEncheres, long miseAPrix, long prixVente, String etatVente, Utilisateur utilisateur, Categorie categorie, Retrait lieuRetrait, String urlImage) {
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
        this.urlImage = urlImage;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
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

    public Date getDateDebutEncheres() {
        return dateDebutEncheres;
    }

    public void setDateDebutEncheres(Date dateDebutEncheres) {
        this.dateDebutEncheres = dateDebutEncheres;
    }

    public Date getDateFinEncheres() {
        return dateFinEncheres;
    }

    public void setDateFinEncheres(Date dateFinEncheres) {
        this.dateFinEncheres = dateFinEncheres;
    }

    public long getMiseAPrix() {
        return miseAPrix;
    }

    public void setMiseAPrix(long miseAPrix) {
        this.miseAPrix = miseAPrix;
    }

    public long getPrixVente() {
        return prixVente;
    }

    public void setPrixVente(long prixVente) {
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


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ArticleVendu{");
        sb.append("noArticle=").append(noArticle);
        sb.append(", nomArticle='").append(nomArticle).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", dateDebutEncheres=").append(dateDebutEncheres);
        sb.append(", dateFinEncheres=").append(dateFinEncheres);
        sb.append(", miseAPrix=").append(miseAPrix);
        sb.append(", prixVente=").append(prixVente);
        sb.append(", etatVente='").append(etatVente).append('\'');
        sb.append(", utilisateur=").append(utilisateur);
        sb.append(", categorie=").append(categorie);
        sb.append(", lieuRetrait=").append(lieuRetrait);
        sb.append(", urlImage='").append(urlImage).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
