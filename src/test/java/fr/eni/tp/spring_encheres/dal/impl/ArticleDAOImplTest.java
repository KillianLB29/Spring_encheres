package fr.eni.tp.spring_encheres.dal.impl;

import fr.eni.tp.spring_encheres.bo.ArticleVendu;
import fr.eni.tp.spring_encheres.bo.Categorie;
import fr.eni.tp.spring_encheres.bo.Utilisateur;
import fr.eni.tp.spring_encheres.dal.ArticleVenduDAO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@SpringBootTest
public class ArticleDAOImplTest {

    @Autowired
    private ArticleVenduDAO articleVenduDAO;

    @Test
    public void testRead() {
        System.out.println(articleVenduDAO.read(1));
    }

    @Test
    public void testFindAll() {
        articleVenduDAO.findAll().forEach(System.out::println);
    }

    @Test
    public void testSave() {
        LocalDate localDate = LocalDate.now();
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        LocalDate localDatefin = LocalDate.now().plusDays(1); // Ajoute 1 jour
        Date dateFin = Date.from(localDatefin.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Utilisateur util = new Utilisateur();
        util.setNoUtilisateur(1);
        Categorie cate = new Categorie();
        cate.setIdCategorie(1);
        ArticleVendu articleVendu = new ArticleVendu(6,"T-shirt test","description test",date,dateFin,333,0,"",util,cate,null,"testUrl");
        articleVenduDAO.create(articleVendu);
    }

    @Test
    public void testDelete() {
        articleVenduDAO.delete(6);
        articleVenduDAO.findAll().forEach(System.out::println);
    }
}
