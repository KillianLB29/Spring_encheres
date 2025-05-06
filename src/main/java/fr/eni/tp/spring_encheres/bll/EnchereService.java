package fr.eni.tp.spring_encheres.bll;

import fr.eni.tp.spring_encheres.bo.ArticleVendu;
import fr.eni.tp.spring_encheres.bo.Enchere;

import java.util.List;

public interface EnchereService {
    List<Enchere> findByArticleId(long idArticle);

    boolean enregistrerEnchere(Enchere enchere);

    void supprimerEnchere(long idArticle, long idUtilisateur);

    List<Enchere> findAll();

    Enchere meilleurEnchere(long idArticle);

//    List<Enchere> findByUtilID(long idUtilisateur);
}
