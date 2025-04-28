package fr.eni.tp.spring_encheres.dal;

import fr.eni.tp.spring_encheres.bo.Utilisateur;

import java.util.List;

public interface UtilisateurDAO {

    List<Utilisateur> findAll();

    Utilisateur read(Long noUtilisateur);

    void insert(Utilisateur utilisateur);

    void delete(Utilisateur utilisateur);

    Utilisateur findByPseudo(String pseudo);

    void debiterCredit(Long noUtilisateur, int montant);

    void crediterCredit(Long noUtilisateur, int montant);
}
