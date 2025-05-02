package fr.eni.tp.spring_encheres.dal.impl;

import fr.eni.tp.spring_encheres.dal.ArticleVenduDAO;
import fr.eni.tp.spring_encheres.bo.ArticleVendu;
import fr.eni.tp.spring_encheres.bo.Categorie;
import fr.eni.tp.spring_encheres.bo.Utilisateur;
import fr.eni.tp.spring_encheres.bo.Enchere;
import fr.eni.tp.spring_encheres.bo.Retrait;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ArticleVenduDaoImpl implements ArticleVenduDAO {

    private static final String SELECT_ALL = "SELECT no_article, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie,urlimage FROM ARTICLES_VENDUS;";
    private static final String INSERT_INTO = "INSERT INTO ARTICLES_VENDUS (nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie,urlimage) VALUES (:nom_article, :description, :date_debut_encheres, :date_fin_encheres, :prix_initial, :prix_vente, :no_utilisateur, :no_categorie,:urlimage);";
    private static final String SELECT_BY_ID = "SELECT no_article, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie,urlimage FROM ARTICLES_VENDUS WHERE no_article = :no_article;";
    //a changer la requête SQL
    private static final String DELETE_BY_ID = "DELETE FROM ARTICLES_VENDUS WHERE no_article = :no_article;";


    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ArticleVenduDaoImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public void create(ArticleVendu article) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("nom_article", article.getNomArticle());
        mapSqlParameterSource.addValue("description", article.getDescription());
        mapSqlParameterSource.addValue("date_debut_encheres", article.getDateDebutEncheres());
        mapSqlParameterSource.addValue("date_fin_encheres", article.getDateFinEncheres());
        mapSqlParameterSource.addValue("prix_initial", article.getMiseAPrix());
        mapSqlParameterSource.addValue("prix_vente", article.getPrixVente());
        mapSqlParameterSource.addValue("no_utilisateur", article.getUtilisateur().getNoUtilisateur());
        mapSqlParameterSource.addValue("no_categorie", article.getCategorie().getIdCategorie());
        mapSqlParameterSource.addValue("urlimage", article.getUrlImage());

        namedParameterJdbcTemplate.update(
                INSERT_INTO,
                mapSqlParameterSource
        );

    }


    @Override
    public List<ArticleVendu> findAll() {
        List<ArticleVendu> articles = jdbcTemplate.query(
                SELECT_ALL,
                new ArticleVenduRowMapper()
        );
        return articles;
    }


    @Override
    public ArticleVendu read(long noArticle) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("no_article", noArticle);
        ArticleVendu article = namedParameterJdbcTemplate.queryForObject(SELECT_BY_ID, mapSqlParameterSource, new ArticleVenduRowMapper());
        return article;
    }


    @Override
    public void delete(long noArticle) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("no_article", noArticle);
        namedParameterJdbcTemplate.update(DELETE_BY_ID, mapSqlParameterSource);
    }

    }


    class ArticleVenduRowMapper implements RowMapper<ArticleVendu> {
        @Override
        public ArticleVendu mapRow(ResultSet rs, int rowNum) throws SQLException {
            ArticleVendu article = new ArticleVendu();
            article.setNoArticle(rs.getLong("no_article"));
            article.setNomArticle(rs.getString("nom_article"));
            article.setDescription(rs.getString("description"));
            article.setDateDebutEncheres(rs.getDate("date_debut_encheres"));
            article.setDateFinEncheres(rs.getDate("date_fin_encheres"));
            article.setMiseAPrix(rs.getLong("prix_initial"));
            article.setPrixVente(rs.getLong("prix_vente"));
            article.setUrlImage(rs.getString("urlimage"));

            Utilisateur utilisateur = new Utilisateur();
            utilisateur.setNoUtilisateur(rs.getLong("no_utilisateur"));

            article.setUtilisateur(utilisateur);

            Categorie categorie = new Categorie();
            categorie.setIdCategorie(rs.getLong("no_categorie"));
            article.setCategorie(categorie);

            return article;
        }
    }

