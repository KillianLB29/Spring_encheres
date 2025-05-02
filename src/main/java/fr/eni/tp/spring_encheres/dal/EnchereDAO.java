package fr.eni.tp.spring_encheres.dal;

import fr.eni.tp.spring_encheres.bo.ArticleVendu;
import fr.eni.tp.spring_encheres.bo.Enchere;
import fr.eni.tp.spring_encheres.bo.Utilisateur;

import java.util.List;

public interface EnchereDAO {
    List<Enchere> read(long idArticle);

    List<Enchere> readByUtilID(long idUtilisateur);

    void save(Enchere enchere);

    void delete(long idArticle,long idUtilisateur);

    List<Enchere> findAll();
}
