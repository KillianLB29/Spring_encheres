package fr.eni.tp.spring_encheres.bll;

import fr.eni.tp.spring_encheres.bo.ArticleVendu;
import fr.eni.tp.spring_encheres.bo.Enchere;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ArticleVenduService")
public class ArticleVenduServiceImpl implements EnchereService {


    @Override
    public List<Enchere> findByArticleId(Integer idArticle) {
        return List.of();
    }

    @Override
    public void enregistrerEnchere(Enchere enchere) {

    }

    @Override
    public void supprimerEnchere(long id) {

    }

    @Override
    public List<Enchere> findAll() {
        return List.of();
    }
}
