package fr.eni.tp.spring_encheres.bll;


import fr.eni.tp.spring_encheres.bo.*;

import java.util.List;

public interface CategorieService {
    List<Categorie> consulterCategories();
    List<Categorie> consulterCategorieParId(long idCategorie);
    void createCategorie(Categorie categorie);
    void supprimerCategorie(long idCategorie);


}
