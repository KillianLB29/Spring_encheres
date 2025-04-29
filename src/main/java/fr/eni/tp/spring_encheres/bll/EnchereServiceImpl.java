package fr.eni.tp.spring_encheres.bll;

import fr.eni.tp.spring_encheres.bo.Enchere;
import fr.eni.tp.spring_encheres.dal.EnchereDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("enchereService")
public class EnchereServiceImpl implements EnchereService {

    @Autowired
    private EnchereDAO enchereDAO;

    @Override
    public List<Enchere> findByArticleId(long idArticle) {
        return enchereDAO.read(idArticle);
    }

    @Override
    public void enregistrerEnchere(Enchere enchere) {
        enchereDAO.save(enchere);
    }

    @Override
    public void supprimerEnchere(long id) {
        enchereDAO.delete(id);
    }

    @Override
    public List<Enchere> findAll() {
        return enchereDAO.findAll();
    }
}
