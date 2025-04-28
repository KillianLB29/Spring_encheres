package fr.eni.tp.spring_encheres.dal.impl;

import fr.eni.tp.spring_encheres.bo.Categorie;
import fr.eni.tp.spring_encheres.dal.CategorieDAO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CategorieDAOImplTest {

    @Autowired
    private CategorieDAO categorieDAO;

    @Test
    public void testFindAll() {
        List<Categorie> categories = categorieDAO.findAll();
        categories.forEach(System.out::println);
    }
    @Test
    public void testFindById() {
        Categorie categorie = categorieDAO.read(2);
        System.out.println(categorie);
    }
    @Test
    public void testSave() {
        Categorie categorie = new Categorie();
        categorie.setLibelle("test");
        categorieDAO.create(categorie);
        categorie = categorieDAO.read(6);
        System.out.println(categorie);
    }
    @Test
    public void testDelete() {
        List<Categorie> categories = categorieDAO.findAll();
        categories.forEach(System.out::println);
        System.out.println("Suppression de la categorie 6 test !");
        categorieDAO.delete(6);
        categories = categorieDAO.findAll();
        categories.forEach(System.out::println);
    }
}
