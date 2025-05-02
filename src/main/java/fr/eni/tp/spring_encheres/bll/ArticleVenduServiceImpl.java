package fr.eni.tp.spring_encheres.bll;

import fr.eni.tp.spring_encheres.bo.ArticleVendu;
import fr.eni.tp.spring_encheres.bo.Enchere;
import fr.eni.tp.spring_encheres.dal.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
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
    @Autowired
    private EnchereDAO enchereDAO;

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
                .filter(article -> article.getDateFinEncheres().after(nowDate)).collect(Collectors.toList())
                .stream().filter(a-> a.getDateDebutEncheres().before(nowDate))
                .sorted(Comparator.comparing(a -> a.getDateFinEncheres())).collect(Collectors.toList());
    }
    @Override
    public List<ArticleVendu> consulterEncheresTerminer(long idUtilisateur) {
        List<ArticleVendu> articles = articleVenduDAO.findAll();
        LocalDateTime now = LocalDateTime.now();
        Date nowDate = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
        articles = articles
                .stream().filter(a-> a.getDateFinEncheres().before(nowDate)).collect(Collectors.toList())
                .stream().filter(a-> a.getUtilisateur().getNoUtilisateur() == idUtilisateur).collect(Collectors.toList());
        articles.forEach(a-> a.setUtilisateur(utilisateurDAO.read(a.getUtilisateur().getNoUtilisateur())));
        return articles;
    }
    @Override
    public List<ArticleVendu> consulterEncheresEnCours(long idUtilisateur) {
        //Recupère tout les articles et je trie ceux qui sont vendu par l'utilisateur rentrer en paramètres et ceux qui sont actuellement en vente
        List<ArticleVendu> articles = articleVenduDAO.findAll();
        LocalDateTime now = LocalDateTime.now();
        Date nowDate = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
        articles = articles
                .stream().filter(a-> a.getUtilisateur().getNoUtilisateur() == idUtilisateur).collect(Collectors.toList())
                .stream().filter(a-> a.getDateFinEncheres().after(nowDate)).collect(Collectors.toList())
                .stream().filter(article -> article.getDateDebutEncheres().before(nowDate)).collect(Collectors.toList());

        articles.forEach(a-> a.setUtilisateur(utilisateurDAO.read(a.getUtilisateur().getNoUtilisateur())));
        return articles;
    }

    @Override
    public List<ArticleVendu> consulterEncheresParticipe(long idUtilisateur) {
        List<Enchere> mesEncheres =enchereDAO.readByUtilID(idUtilisateur);
        System.out.println(mesEncheres);
        List<ArticleVendu> articles = new ArrayList<>();
        for(Enchere e: mesEncheres)
        {
            articles.add(articleVenduDAO.read(e.getArticleVendu().getNoArticle()));
        }
        LocalDateTime now = LocalDateTime.now();
        Date nowDate = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
        articles = articles
                .stream().filter(a-> a.getDateFinEncheres().after(nowDate)).collect(Collectors.toList())
                .stream().filter(article -> article.getDateDebutEncheres().before(nowDate)).collect(Collectors.toList());
        articles.forEach(a-> a.setUtilisateur(utilisateurDAO.read(a.getUtilisateur().getNoUtilisateur())));
        //TODO trier les potentiels article en double si un utilisateur a enchéri plusieurs fois sur un même objet
        // car actuellement on récupère 1 exemplaire de l'article par enchère du client en cours
        return articles;
    }

    @Override
    public boolean isEnchereEnCours(long idArticle) {
        boolean isValid = true;
        ArticleVendu articleVendu = articleVenduDAO.read(idArticle);
        LocalDateTime now = LocalDateTime.now();
        Date nowDate = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
        if(!articleVendu.getDateDebutEncheres().before(nowDate))
            isValid=false;
        if(!articleVendu.getDateFinEncheres().after(nowDate))
            isValid=false;
        return isValid;
    }

//    @Override
//    public List<ArticleVendu> remplirEncheresEnCours(List<Enchere> encheres) {
//        List<ArticleVendu> articles = new ArrayList<>();
//        encheres.forEach(enchere -> articles.add(articleVenduDAO.read(enchere.getUtilisateur().getNoUtilisateur())));
//        return articles;
//    }
}
