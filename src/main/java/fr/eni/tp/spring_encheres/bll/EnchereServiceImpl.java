package fr.eni.tp.spring_encheres.bll;

import fr.eni.tp.spring_encheres.bo.ArticleVendu;
import fr.eni.tp.spring_encheres.bo.Enchere;
import fr.eni.tp.spring_encheres.dal.ArticleVenduDAO;
import fr.eni.tp.spring_encheres.dal.CategorieDAO;
import fr.eni.tp.spring_encheres.dal.EnchereDAO;
import fr.eni.tp.spring_encheres.dal.UtilisateurDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service("enchereService")
public class EnchereServiceImpl implements EnchereService {

    @Autowired
    private EnchereDAO enchereDAO;
    @Autowired
    private ArticleVenduDAO articleVenduDAO;
    @Autowired
    private UtilisateurDAO utilisateurDAO;

    @Override
    public List<Enchere> findByArticleId(long idArticle) {
        return enchereDAO.read(idArticle);
    }

    @Override
    public void enregistrerEnchere(Enchere enchere) {
        List<Enchere> encheres = enchereDAO.read(enchere.getArticleVendu().getNoArticle());
        encheres = encheres.stream()
                .filter(e -> e.getUtilisateur().getNoUtilisateur()==enchere.getUtilisateur().getNoUtilisateur())
                .collect(Collectors.toList());
        if(encheres.size() > 0){
            enchereDAO.delete(enchere.getArticleVendu().getNoArticle(),enchere.getUtilisateur().getNoUtilisateur());
        }
        enchereDAO.save(enchere);
    }

    @Override
    public void supprimerEnchere(long idArticle,long idUtilisateur) {
        enchereDAO.delete(idArticle,idUtilisateur);
    }

    @Override
    public List<Enchere> findAll() {
        return enchereDAO.findAll();
    }

    @Override
    public Enchere meilleurEnchere(long idArticle) {
        List<Enchere> encheres = enchereDAO.read(idArticle);
        if(encheres.size() == 0)
        {
            return new Enchere();
        }
        else{
            Enchere meilleureEnchere = encheres.stream()
                    .max(Comparator.comparing(Enchere::getMontantEnchere))
                    .orElse(null);
            meilleureEnchere.setUtilisateur(utilisateurDAO.read(meilleureEnchere.getUtilisateur().getNoUtilisateur()));
            return meilleureEnchere;
        }
    }

//    @Override
//    public List<Enchere> findByUtilID(long idUtilisateur) {
//        return enchereDAO.readByUtilID(idUtilisateur);
//    }


}
