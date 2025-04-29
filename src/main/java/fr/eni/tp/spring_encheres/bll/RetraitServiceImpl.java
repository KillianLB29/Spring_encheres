package fr.eni.tp.spring_encheres.bll;

import fr.eni.tp.spring_encheres.bo.Retrait;
import fr.eni.tp.spring_encheres.dal.RetraitDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("retraitService")
public class RetraitServiceImpl implements RetraitService {

    @Autowired
    private RetraitDAO retraitDAO;

    @Override
    public Retrait findById(long idRetrait) {
        return retraitDAO.read(idRetrait);
    }

    @Override
    public void enregistrerRetrait(Retrait retrait) {
        retraitDAO.save(retrait);
    }
}
