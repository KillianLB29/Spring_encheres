package fr.eni.tp.spring_encheres.bll;

import fr.eni.tp.spring_encheres.bo.*;
import fr.eni.tp.spring_encheres.dal.*;
import fr.eni.tp.spring_encheres.exception.EnchereException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    public void creerArticle(ArticleVendu article) throws EnchereException {
        EnchereException enchereException = new EnchereException();
        boolean isValid = true;
        isValid&= isNomArticleValid(article.getNomArticle(),enchereException);
        isValid&=isDescriptionValid(article.getDescription(),enchereException);
        isValid&=isDateDebutEncheresValid(article.getDateDebutEncheres(),enchereException);
        isValid&=isDateFinEncheresValid(article.getDateFinEncheres(),enchereException);
        isValid&=isMiseAPrixValid(article.getMiseAPrix(),enchereException);
        isValid&=isUtilisateurValid(article.getUtilisateur(),enchereException);
        isValid&=isCategorieValid(article.getCategorie(),enchereException);
        isValid&=isLieuRetraitValid(article.getLieuRetrait(),enchereException);
        isValid&=isUrlImageValid(article.getUrlImage(),enchereException);
        if(isValid){
            articleVenduDAO.create(article);
            retraitDAO.save(article.getLieuRetrait(),article.getNoArticle());
        }
        else {
            throw enchereException;
        }

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

    private boolean isNomArticleValid(String nomArticle, EnchereException enchereException) {
        boolean isValid = true;
        if(nomArticle == null || nomArticle.isEmpty()||nomArticle.isBlank())
        {
            isValid=false;
            enchereException.addMessage("Le nom de l'article doit être renseigné.");
        }
        if(nomArticle.length()>30){
            isValid=false;
            enchereException.addMessage("Le nom de l'article ne peut pas faire plus de 30 caractères");
        }
        return isValid;
    }
    private boolean isDescriptionValid(String description, EnchereException enchereException) {
        boolean isValid = true;
        if(description == null || description.isEmpty()||description.isBlank())
        {
            isValid=false;
            enchereException.addMessage("La description de l'article doit tre renseignée");
        }
        if(description.length()>300){
            isValid=false;
            enchereException.addMessage("La description de l'article ne peut pas faire plus de 300 caractères");
        }
        return isValid;
    }
    private boolean isDateDebutEncheresValid(Date dateDebutEncheres, EnchereException enchereException) {
        boolean isValid = true;
        if(dateDebutEncheres == null){
            isValid=false;
            enchereException.addMessage("La date de debut d'enchère doit être renseignée");
        }
        return isValid;
    }
    private boolean isDateFinEncheresValid(Date dateFinEncheres, EnchereException enchereException) {
        boolean isValid = true;
        if(dateFinEncheres == null){
            isValid=false;
            enchereException.addMessage("La date de fin d'enchère doit être renseignée");
        }
        return isValid;
    }
    private boolean isMiseAPrixValid(long miseAPrix, EnchereException enchereException) {
        boolean isValid = true;
        if(miseAPrix<0){
            isValid=false;
            enchereException.addMessage("La mise a prix initial de l'article doit être supérieure a 0");
        }
        return isValid;
    }
    private boolean isCategorieValid(Categorie categorie, EnchereException enchereException) {
        boolean isValid = true;
        if(categorie==null){
            isValid=false;
            enchereException.addMessage("Il faut obligatoirement sélectionner une catégorie pour l'article");
        }
        if(categorie.getLibelle() == null || categorie.getLibelle().isEmpty()||categorie.getLibelle().isBlank())
        {
            isValid=false;
            enchereException.addMessage("Le libellé de catégorie doit être renseigné");
        }
        if(categorie.getLibelle().length()>30){
            isValid=false;
            enchereException.addMessage("Le libellé de catégorie ne doit pas faire plus de 30 caractères");
        }
        return isValid;
    }
    private boolean isUtilisateurValid(Utilisateur utilisateur, EnchereException enchereException) {
        boolean isValid = true;
        Utilisateur utilDAO = utilisateurDAO.read(utilisateur.getNoUtilisateur());
        if(utilDAO.getNoUtilisateur() == 0){
            isValid=false;
            enchereException.addMessage("L'utilisateur qui essaye de créer l'article n'existe pas");
        }
        return isValid;
    }
    private boolean isLieuRetraitValid(Retrait lieuRetrait, EnchereException enchereException) {
        boolean isValid = true;
        if(lieuRetrait.getRue() == null || lieuRetrait.getRue().isEmpty()||lieuRetrait.getRue().isBlank())
        {
            isValid=false;
            enchereException.addMessage("La rue du lieu de retrait doit être renseignée");
        }
        if(lieuRetrait.getRue().length()>30){
            isValid=false;
            enchereException.addMessage("La rue ne doit pas faire plus de 30 caractères");
        }
        if(lieuRetrait.getCode_Postal() == null || lieuRetrait.getCode_Postal().isEmpty()||lieuRetrait.getCode_Postal().isBlank())
        {
            isValid=false;
            enchereException.addMessage("Le code postal lieu de retrait doit être renseigné");
        }
        if(lieuRetrait.getCode_Postal().length() != 5){
            isValid=false;
            enchereException.addMessage("La code postal du lieu de retrait doit faire 5 caractères");
        }
        if(lieuRetrait.getVille() == null || lieuRetrait.getVille().isEmpty()||lieuRetrait.getVille().isBlank())
        {
            isValid=false;
            enchereException.addMessage("La ville du lieu de retrait doit être renseignée");
        }
        if(lieuRetrait.getVille().length()>30){
            isValid=false;
            enchereException.addMessage("La ville ne doit pas faire plus de 30 caractères");
        }
        return isValid;
    }
    private boolean isUrlImageValid(String urlImage, EnchereException enchereException) {
        boolean isValid = true;
        if(urlImage == null || urlImage.isEmpty()||urlImage.isBlank())
        {
            isValid=false;
            enchereException.addMessage("Il a y un problème avec l'image qui vient d'être envoyé");
        }
        return isValid;
    }



//    @Override
//    public List<ArticleVendu> remplirEncheresEnCours(List<Enchere> encheres) {
//        List<ArticleVendu> articles = new ArrayList<>();
//        encheres.forEach(enchere -> articles.add(articleVenduDAO.read(enchere.getUtilisateur().getNoUtilisateur())));
//        return articles;
//    }
}
