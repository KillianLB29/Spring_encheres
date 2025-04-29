package fr.eni.tp.spring_encheres.bll;

import fr.eni.tp.spring_encheres.bo.*;
import java.util.List;

public interface ArticleVenduService {
    List<ArticleVendu> consulterArticlesVendus();
    ArticleVendu consulterArticleParId(long id);
    void creerArticle(ArticleVendu article, Utilisateur utilisateur);
    void supprimerArticle(ArticleVendu, long noArticle);


}
