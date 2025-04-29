package fr.eni.tp.spring_encheres.bll;

import fr.eni.tp.spring_encheres.bo.Enchere;

import java.util.List;

public interface EnchereService {
    List<Enchere> findByArticleId(long idArticle);

    void enregistrerEnchere(Enchere enchere);

    void supprimerEnchere(long id);

    List<Enchere> findAll();
}
