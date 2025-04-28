package fr.eni.tp.spring_encheres.dal;

import fr.eni.tp.spring_encheres.bo.Retrait;

public interface RetraitDAO {
    Retrait read(long id);

    void save(Retrait retrait);

    void delete(long id);

    void findAll();
}
