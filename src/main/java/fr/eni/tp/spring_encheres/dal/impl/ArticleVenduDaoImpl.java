package fr.eni.tp.spring_encheres.dal.impl;

import fr.eni.tp.spring_encheres.dal.ArticleVenduDAO;
import fr.eni.tp.spring_encheres.bo.ArticleVendu;
import fr.eni.tp.spring_encheres.bo.Categorie;
import fr.eni.tp.spring_encheres.bo.Utilisateur;
import fr.eni.tp.spring_encheres.bo.Enchere;
import fr.eni.tp.spring_encheres.bo.Retrait;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ArticleVenduDaoImpl implements ArticleVenduDAO {

    @Override
    public void create(ArticleVendu article) {

    }

    @Override
    public List<ArticleVendu> findAll() {
        return List.of();
    }

    @Override
    public ArticleVendu read(long noArticle) {
        return null;
    }

    @Override
    public void delete(long noArticle) {

    }
}
