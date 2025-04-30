package fr.eni.tp.spring_encheres.bll;

import fr.eni.tp.spring_encheres.bo.ArticleVendu;
import fr.eni.tp.spring_encheres.bo.Enchere;
import fr.eni.tp.spring_encheres.dal.ArticleVenduDAO;
import fr.eni.tp.spring_encheres.dal.CategorieDAO;
import fr.eni.tp.spring_encheres.dal.RetraitDAO;
import fr.eni.tp.spring_encheres.dal.UtilisateurDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service("ArticleVenduService")
public class ArticleVenduServiceImpl implements ArticleVenduService {

    @Autowired
    private ArticleVenduDAO articleVenduDAO;
    @Autowired
    private UtilisateurDAO utilisateurDAO;
    @Autowired
    private CategorieDAO categorieDAO;
    @Autowired
    private RetraitDAO retraitDAO;

    public ArticleVenduServiceImpl(ArticleVenduDAO articleVenduDAO) {
        this.articleVenduDAO = articleVenduDAO;
    }

    @Override
    public List<ArticleVendu> consulterArticlesVendus() {
        return articleVenduDAO.findAll();
    }

    @Override
    public ArticleVendu consulterArticleParId(long id) {
        ArticleVendu article = articleVenduDAO.read(id);
        article.setUtilisateur(utilisateurDAO.read(article.getUtilisateur().getNoUtilisateur()));
        article.setCategorie(categorieDAO.read(article.getCategorie().getIdCategorie()));
        article.setLieuRetrait(retraitDAO.read(id));
        return article;
    }

    @Override
    public void creerArticle(ArticleVendu article) {
        articleVenduDAO.create(article);
    }

    @Override
    public void supprimerArticle(long noArticle) {
        articleVenduDAO.delete(noArticle);
    }
    @Override
    public List<ArticleVendu> consulterArticlesEnCoursDeVente() {
        LocalDateTime now = LocalDateTime.now();
        Date nowDate = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());

        List<ArticleVendu> articles = articleVenduDAO.findAll();

        articles.forEach(articleVendu -> articleVendu.setUtilisateur(utilisateurDAO.read(articleVendu.getUtilisateur().getNoUtilisateur())));

        return articles.stream()
                .filter(article -> article.getDateFinEncheres().after(nowDate))
                .sorted(Comparator.comparing(a -> a.getDateFinEncheres()))
                .collect(Collectors.toList());
    }
}
