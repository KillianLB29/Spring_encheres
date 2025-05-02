package fr.eni.tp.spring_encheres.bll;

import fr.eni.tp.spring_encheres.bo.ArticleVendu;
import fr.eni.tp.spring_encheres.bo.Enchere;
import fr.eni.tp.spring_encheres.dal.ArticleVenduDAO;
import fr.eni.tp.spring_encheres.dal.EnchereDAO;
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

    @Override
    public List<Enchere> findByArticleId(long idArticle) {
        return enchereDAO.read(idArticle);
    }

    @Override
    public void enregistrerEnchere(Enchere enchere) {
        enchereDAO.save(enchere);
    }

    @Override
    public void supprimerEnchere(long id) {
        enchereDAO.delete(id);
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
            return meilleureEnchere;
        }
    }

//    @Override
//    public List<Enchere> findByUtilID(long idUtilisateur) {
//        return enchereDAO.readByUtilID(idUtilisateur);
//    }


}
