package fr.eni.tp.spring_encheres.dal.impl;

import fr.eni.tp.spring_encheres.bo.ArticleVendu;
import fr.eni.tp.spring_encheres.bo.Utilisateur;
import fr.eni.tp.spring_encheres.dal.EnchereDAO;
import fr.eni.tp.spring_encheres.bo.Enchere;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class EnchereDAOImplTest {

    @Autowired
    private EnchereDAO enchereDAO;

    @Test
    public void findAllTest() {
        List<Enchere> encheres = enchereDAO.findAll();
        encheres.forEach(System.out::println);
    }
    @Test
    public void findByIdTest() {
        List<Enchere> encheres = enchereDAO.read(3);
        encheres.forEach(System.out::println);
    }
    @Test
    public void insertTest() {
        Enchere enchere = new Enchere();
        Utilisateur util = new Utilisateur();
        util.setNoUtilisateur(2);
        ArticleVendu articleVendu = new ArticleVendu();
        articleVendu.setNoArticle(3);
        enchere.setArticleVendu(articleVendu);
        enchere.setUtilisateur(util);
        enchere.setMontantEnchere(110);
        LocalDate localDate = LocalDate.now();
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        enchere.setDateEnchere(date);
        enchereDAO.save(enchere);
    }

}
