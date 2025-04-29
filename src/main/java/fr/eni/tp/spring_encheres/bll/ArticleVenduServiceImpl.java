package fr.eni.tp.spring_encheres.bll;

import fr.eni.tp.spring_encheres.bo.ArticleVendu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("ArticleVenduService")
public class ArticleVenduServiceImpl implements EnchereService {



    @Autowired
    ArticleVendu consulterArticleParId(long id) {}
}
