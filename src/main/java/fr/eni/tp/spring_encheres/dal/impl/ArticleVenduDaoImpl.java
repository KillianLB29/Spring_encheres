package fr.eni.tp.spring_encheres.dal.impl;

import fr.eni.tp.spring_encheres.dal.ArticleVenduDAO;
import fr.eni.tp.spring_encheres.bo.ArticleVendu;
import fr.eni.tp.spring_encheres.bo.Categorie;
import fr.eni.tp.spring_encheres.bo.Utilisateur;
import fr.eni.tp.spring_encheres.bo.Enchere;
import fr.eni.tp.spring_encheres.bo.Retrait;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ArticleVenduDaoImpl implements ArticleVenduDAO {

    private static final String SELECT_ALL = "SELECT no_article, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, Articles_vendus.no_utilisateur, Articles_vendus.no_categorie FROM ARTICLES_VENDUS INNER JOIN UTILISATEURS ON UTILISATEURS.no_utilisateur=ARTICLES_VENDUS.no_utilisateur INNER JOIN CATEGORIES on ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie;";
    private static final String INSERT_INTO = "INSERT INTO Articles_vendus (no_article, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie)VALUES (:no_article, :nom_article, :description, :date_debut_encheres, :date_fin_encheres, :prix_initial, :prix_vente, :no_utilisateur, :no_categorie);";
    private static final String SELECT_BY_ID = "SELECT no_article, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, Articles_vendus.no_utilisateur, Articles_vendus.no_categorie FROM ARTICLES_VENDUS INNER JOIN UTILISATEURS ON UTILISATEURS.no_utilisateur=ARTICLES_VENDUS.no_utilisateur INNER JOIN CATEGORIES on ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie;";
    //a changer la requête SQL
    private static final String DELETE = "SELECT no_article, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, Articles_vendus.no_utilisateur, Articles_vendus.no_categorie FROM ARTICLES_VENDUS INNER JOIN UTILISATEURS ON UTILISATEURS.no_utilisateur=ARTICLES_VENDUS.no_utilisateur INNER JOIN CATEGORIES on ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie;";


    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ArticleVenduDaoImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public void create(ArticleVendu article) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("No Article", article.getNoArticle());
        mapSqlParameterSource.addValue("Description", article.getDescription());
        mapSqlParameterSource.addValue("Date Debut d'Enchères", article.getDateDebutEncheres());
        mapSqlParameterSource.addValue("Date de fin des Enchères", article.getDateFinEncheres());
        mapSqlParameterSource.addValue("Prix initial", article.getMiseAPrix());
        mapSqlParameterSource.addValue("Prix de vente", article.getPrixVente());
        mapSqlParameterSource.addValue("Etat de vente", article.getEtatVente());
        mapSqlParameterSource.addValue("Categorie", article.getCategorie());
        mapSqlParameterSource.addValue("Lieu de retrait", article.getLieuRetrait());

        namedParameterJdbcTemplate.update(
                INSERT_INTO,
                mapSqlParameterSource
        );

    }

    /*@Override
    public void create(Film film) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("titre", film.getTitre());
        mapSqlParameterSource.addValue("annee", film.getAnnee());
        mapSqlParameterSource.addValue("duree", film.getDuree());
        mapSqlParameterSource.addValue("synopsis", film.getSynopsis());
        mapSqlParameterSource.addValue("id_realisateur", film.getRealisateur().getId());
        mapSqlParameterSource.addValue("id_genre", film.getGenre().getId());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(
                INSERT,
                mapSqlParameterSource,
                keyHolder,
                new String[]{"id"}
        );

        film.setId(keyHolder.getKey().longValue());
    }*/




    @Override
    public List<ArticleVendu> findAll() {
        List<ArticleVendu> articles = jdbcTemplate.query(
                SELECT_ALL,
                new FilmRowMapper()
        );
        return articles;
    }


    @Override
    public ArticleVendu read(long noArticle) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("long", noArticle);
        ArticleVendu article = jdbcTemplate.queryForObject(
                SELECT_BY_ID,
                mapSqlParameterSource,
                new FilmRowMapper()
        );
        return article;
    }


    @Override
    public void delete(long noArticle) {
//à faire
    }


}
