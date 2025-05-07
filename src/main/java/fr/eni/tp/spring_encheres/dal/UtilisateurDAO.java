package fr.eni.tp.spring_encheres.dal;

import fr.eni.tp.spring_encheres.bo.Utilisateur;

import java.util.List;

public interface UtilisateurDAO {

    List<Utilisateur> findAll();

    Utilisateur read(long noUtilisateur);

    void save(Utilisateur utilisateur);

    void update(Utilisateur utilisateur);

    void delete(long noUtilisateur);

    Utilisateur findByPseudo(String pseudo);

    Utilisateur findByEmail(String email);
}
