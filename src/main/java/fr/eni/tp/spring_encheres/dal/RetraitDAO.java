package fr.eni.tp.spring_encheres.dal;

import fr.eni.tp.spring_encheres.bo.Retrait;

public interface RetraitDAO {
    Retrait read(long idRetrait);

    void save(Retrait retrait,long idArticle);

    void update(Retrait lieuRetrait, long noArticle);

    void delete(long idArticle);
}
