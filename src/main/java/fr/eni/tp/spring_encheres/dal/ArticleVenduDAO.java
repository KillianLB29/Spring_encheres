package fr.eni.tp.spring_encheres.dal;

import fr.eni.tp.spring_encheres.bo.ArticleVendu;

import java.util.List;

public interface ArticleVenduDAO {
    void create(ArticleVendu article);
    List<ArticleVendu> findAll();
    ArticleVendu read(long noArticle);
    void delete(long noArticle);
    //boolean peut-être plus tard
}
