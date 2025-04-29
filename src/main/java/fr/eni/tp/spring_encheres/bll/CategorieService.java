package fr.eni.tp.spring_encheres.bll;

import fr.eni.tp.spring_encheres.bo.ArticleVendu;
import fr.eni.tp.spring_encheres.bo.Categorie;

import java.util.List;

public interface CategorieService {
    List<Categorie> consulterCategories();
    Categorie consulterCategorieParId(long id);
    void createCategorie(Categorie categorie);
    void supprimerCategorie(long id);


}
