package fr.eni.tp.spring_encheres.bo;

public class Categorie {
    private long idCategorie;
    private String libelle;


    public Categorie() {

    }

    public Categorie(long idCategorie, String libelle) {
        this.idCategorie = idCategorie;
        this.libelle = libelle;
    }


    public long getIdCategorie() {
        return idCategorie;
    }


    public void setIdCategorie(Integer idCategorie) {
        this.idCategorie = idCategorie;
    }


    public String getLibelle() {
        return libelle;
    }


    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }


    @Override
    public String toString() {
        return "Categorie [idCategorie=" + idCategorie + ", libelle=" + libelle + "]";
    }

}
