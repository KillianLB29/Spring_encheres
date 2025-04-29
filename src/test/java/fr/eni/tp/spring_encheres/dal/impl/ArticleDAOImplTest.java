package fr.eni.tp.spring_encheres.dal.impl;

import fr.eni.tp.spring_encheres.dal.ArticleVenduDAO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ArticleDAOImplTest {

    @Autowired
    private ArticleVenduDAO articleVenduDAO;

    @Test
    public void testRead() {
        System.out.println(articleVenduDAO.read(1));
    }
}
