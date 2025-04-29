package fr.eni.tp.spring_encheres.dal;

import fr.eni.tp.spring_encheres.bo.ArticleVendu;
import fr.eni.tp.spring_encheres.bo.Enchere;
import fr.eni.tp.spring_encheres.bo.Utilisateur;

import java.util.List;

public interface EnchereDAO {
    List<Enchere> read(long idArticle);

    void save(Enchere enchere);

    void delete(long id);

    List<Enchere> findAll();
}
