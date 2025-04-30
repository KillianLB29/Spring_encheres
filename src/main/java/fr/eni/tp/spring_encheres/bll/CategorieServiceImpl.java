package fr.eni.tp.spring_encheres.bll;

import fr.eni.tp.spring_encheres.bo.Categorie;
import fr.eni.tp.spring_encheres.dal.CategorieDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service("Categorie")

public class CategorieServiceImpl implements CategorieService {

   @Autowired
    private CategorieDAO categorieDAO;

    public CategorieServiceImpl(CategorieDAO categorieDAO) {
        this.categorieDAO = categorieDAO;
    }

    @Override
    public List<Categorie> consulterCategories() {
        return categorieDAO.findAll();
    }

    @Override
    public Categorie consulterCategorieParId(long idCategorie) {
        return categorieDAO.read(idCategorie);

    }


    @Override
    public void createCategorie(Categorie categorie) {
        categorieDAO.create(categorie);

    }

    @Override
    public void supprimerCategorie(long idCategorie) {
        categorieDAO.delete(idCategorie);

    }

}
