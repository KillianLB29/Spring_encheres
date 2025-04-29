package fr.eni.tp.spring_encheres.dal.impl;

import fr.eni.tp.spring_encheres.bo.Retrait;
import fr.eni.tp.spring_encheres.dal.RetraitDAO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RetraitDAOImplTest {

    @Autowired
    private RetraitDAO retraitDAO;

    @Test
    public void readTest(){
        System.out.println(retraitDAO.read(1));
    }

    @Test
    public void insertTest(){
        Retrait retrait = new Retrait("rue du test","75000","Paris");
        retraitDAO.save(retrait,5);
    }

}
