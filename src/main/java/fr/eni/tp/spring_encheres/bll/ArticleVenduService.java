package fr.eni.tp.spring_encheres.bll;

import fr.eni.tp.spring_encheres.bo.*;
import java.util.List;

public interface ArticleVenduService {
    List<ArticleVendu> consulterArticlesVendus();
    ArticleVendu consulterArticleParId(long id);
    void creerArticle(ArticleVendu article);
    void updateArticle(ArticleVendu article);
    void supprimerArticle(long noArticle);
    List<ArticleVendu> consulterArticlesEnCoursDeVente();
    List<ArticleVendu> consulterEncheresTerminer(long idUtilisateur);
    List<ArticleVendu> consulterEncheresEnCours(long idUtilisateur);
    List<ArticleVendu> consulterEncheresParticipe(long idUtilisateur);
    boolean isEnchereEnCours(long idArticle);
    boolean isEnchereNonCommencer(long idArticle);

    List<ArticleVendu> consulterEncheresOuvertes(long noUtilisateur, String filtre, String categorie);

    List<ArticleVendu> consulterEncheresEnCoursFiltrer(long noUtilisateur, String filtre, String categorie);

    List<ArticleVendu> consulterEncheresRemportes(long noUtilisateur, String filtre, String categorie);
//    List<ArticleVendu> remplirEncheresEnCours(List<Enchere> encheres);

}
