package fr.eni.tp.spring_encheres.dal;

import fr.eni.tp.spring_encheres.bo.ArticleVendu;
import fr.eni.tp.spring_encheres.bo.Enchere;
import fr.eni.tp.spring_encheres.bo.Utilisateur;

public interface EnchereDAO {
    Enchere read(Integer idArticle);

    void save(Enchere enchere, Integer prixEnchere, ArticleVendu articleVendu, Utilisateur utilisateur);

    Integer readAncienEncherisseur(Integer idArticle);

    Integer readAncienOffre(Integer idArticle);
}
