package fr.eni.tp.spring_encheres.dal;

import fr.eni.tp.spring_encheres.bo.Categorie;

import java.util.List;

public interface CategorieDAO {

    List<Categorie> findAll();

    Categorie read(long idCategorie);

    void create(Categorie categorie);

    void delete(long idCategorie);

}
