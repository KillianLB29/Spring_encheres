package fr.eni.tp.spring_encheres.bll;

import fr.eni.tp.spring_encheres.bo.Retrait;

public interface RetraitService {
    Retrait findById(long idRetrait);
    void enregistrerRetrait(Retrait retrait);
}
