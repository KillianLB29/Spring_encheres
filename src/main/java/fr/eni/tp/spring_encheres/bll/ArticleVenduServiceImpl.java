package fr.eni.tp.spring_encheres.bll;

import fr.eni.tp.spring_encheres.bo.ArticleVendu;
import fr.eni.tp.spring_encheres.bo.Enchere;
import fr.eni.tp.spring_encheres.dal.ArticleVenduDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ArticleVenduService")
public class ArticleVenduServiceImpl implements ArticleVenduService {

    private ArticleVenduDAO articleVenduDAO;

    public ArticleVenduServiceImpl(ArticleVenduDAO articleVenduDAO) {
        this.articleVenduDAO = articleVenduDAO;
    }

    @Override
    public List<ArticleVendu> consulterArticlesVendus() {
        return articleVenduDAO.findAll();
    }

    @Override
    public ArticleVendu consulterArticleParId(long id) {
        return articleVenduDAO.read(id);
    }

    @Override
    public void creerArticle(ArticleVendu article) {
        articleVenduDAO.create(article);
    }

    @Override
    public void supprimerArticle(long noArticle) {
        articleVenduDAO.delete(noArticle);
    }
}
