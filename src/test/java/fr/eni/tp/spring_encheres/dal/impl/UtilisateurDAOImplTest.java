package fr.eni.tp.spring_encheres.dal.impl;


import fr.eni.tp.spring_encheres.bo.Utilisateur;
import fr.eni.tp.spring_encheres.dal.UtilisateurDAO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

@SpringBootTest
public class UtilisateurDAOImplTest {
    @Autowired
    private UtilisateurDAO utilisateurDAO;

    @Test
    public void testFindAll() {
        List<Utilisateur> utils = utilisateurDAO.findAll();
        utils.forEach(util -> System.out.println(util));
    }
    @Test
    public void testFindByPseudo() {
        Utilisateur util = utilisateurDAO.findByPseudo("admin_user");
        System.out.println(util);
    }
    @Test
    public void testSave(){
        Utilisateur util = new Utilisateur(4,"WOWCRAZY","nameTest","surnameTest","emailTest@Test.com","0101010101","Rue du test","01111","Test","mdpTest",200);
        utilisateurDAO.save(util);
    }

    @Test
    public void testFindById() {
        Utilisateur util = utilisateurDAO.read(5);
        System.out.println(util);
    }
    @Test
    public void testDelete() {
        utilisateurDAO.delete(5);
        utilisateurDAO.findAll().forEach(util -> System.out.println(util));
    }
    @Test
    public void testUtilisateurByEmail() {
        Utilisateur util = utilisateurDAO.findByEmail("john.doe@example.com");
        System.out.println(util);
    }

}
