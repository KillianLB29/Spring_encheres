package fr.eni.tp.spring_encheres.ihm.dto;

import fr.eni.tp.spring_encheres.bo.Categorie;
import fr.eni.tp.spring_encheres.bo.Retrait;
import fr.eni.tp.spring_encheres.bo.Utilisateur;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

public class ArticleVenduDTO {
    @NotBlank(message = "Le nom de l'article doit etre renseigné")
    private String nomArticle;
    @NotBlank(message = "La description de l'article doit etre renseigné")
    private String description;
    @NotNull(message = "La date de début d'enchère de l'article doit etre renseigné")
    private Date dateDebutEncheres;
    @NotNull(message = "La date de fin d'enchère de l'article doit etre renseigné")
    private Date dateFinEncheres;
    @Min(value = 1,message = "L'article doit avoir une mise a prix supérieur a 0")
    private long miseAPrix;
    private Categorie categorie;
    @NotNull(message = "L'image de l'article doit etre renseigné")
    private MultipartFile image;
    @NotBlank(message = "La rue du lieu de retrait de l'article doit etre renseigné")
    private String rue;
    @NotBlank(message = "Le code postal du lieu de retrait de l'article doit etre renseigné")
    private String codePostal;
    @NotBlank(message = "La ville du lieu de retrait de l'article doit etre renseigné")
    private String ville;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ArticleVenduDTO{");
        sb.append("nomArticle='").append(nomArticle).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", dateDebutEncheres=").append(dateDebutEncheres);
        sb.append(", dateFinEncheres=").append(dateFinEncheres);
        sb.append(", miseAPrix=").append(miseAPrix);
        sb.append(", categorie=").append(categorie);
        sb.append(", image=").append(image);
        sb.append(", rue='").append(rue).append('\'');
        sb.append(", codePostal='").append(codePostal).append('\'');
        sb.append(", ville='").append(ville).append('\'');
        sb.append('}');
        return sb.toString();
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

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
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

    public ArticleVenduDTO() {
    }

    public ArticleVenduDTO(String nomArticle, String description, Date dateDebutEncheres, Date dateFinEncheres, long miseAPrix, Categorie categorie, MultipartFile image, String rue, String codePostal, String ville) {
        this.nomArticle = nomArticle;
        this.description = description;
        this.dateDebutEncheres = dateDebutEncheres;
        this.dateFinEncheres = dateFinEncheres;
        this.miseAPrix = miseAPrix;
        this.categorie = categorie;
        this.image = image;
        this.rue = rue;
        this.codePostal = codePostal;
        this.ville = ville;
    }
}
