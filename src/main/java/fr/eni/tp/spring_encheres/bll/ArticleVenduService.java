package fr.eni.tp.spring_encheres.bll;

import fr.eni.tp.spring_encheres.bo.*;
import java.util.List;

public interface ArticleVenduService {
    List<ArticleVendu> consulterArticlesVendus();
    ArticleVendu consulterArticleParId(long id);
    void creerArticle(ArticleVendu article);
    void supprimerArticle(long noArticle);
    List<ArticleVendu> consulterArticlesEnCoursDeVente();
    List<ArticleVendu> consulterEncheresTerminer(long idUtilisateur);
    List<ArticleVendu> consulterEncheresEnCours(long idUtilisateur);
    List<ArticleVendu> consulterEncheresParticipe(long idUtilisateur);
//    List<ArticleVendu> remplirEncheresEnCours(List<Enchere> encheres);

}
