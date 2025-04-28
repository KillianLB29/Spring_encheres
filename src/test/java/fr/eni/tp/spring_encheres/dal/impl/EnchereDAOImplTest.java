package fr.eni.tp.spring_encheres.dal.impl;

import fr.eni.tp.spring_encheres.dal.EnchereDAO;
import fr.eni.tp.spring_encheres.bo.Enchere;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

}
