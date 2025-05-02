package fr.eni.tp.spring_encheres.dal.impl;

import fr.eni.tp.spring_encheres.bo.ArticleVendu;
import fr.eni.tp.spring_encheres.bo.Enchere;
import fr.eni.tp.spring_encheres.bo.Utilisateur;
import fr.eni.tp.spring_encheres.dal.EnchereDAO;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public class EnchereDAOImpl implements EnchereDAO {

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public EnchereDAOImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    private final String SELECT_ALL = "SELECT no_utilisateur,no_article,date_enchere as date , montant_enchere as montant FROM ENCHERES";
    private final String SELECT_BY_ID = "SELECT no_utilisateur,no_article,date_enchere as date , montant_enchere as montant FROM ENCHERES WHERE no_article=:no_article";
    private final String INSERT = "INSERT INTO ENCHERES (no_utilisateur, no_article, date_enchere, montant_enchere) VALUES(:no_utilisateur, :no_article, :date_enchere,:montant_enchere);";
    private final String SELECT_BY_ID_UTIL = "SELECT no_utilisateur,no_article,date_enchere as date , montant_enchere as montant FROM ENCHERES WHERE no_utilisateur=:no_utilisateur";

    @Override
    public List<Enchere> read(long idArticle) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("no_article", idArticle);
        return namedParameterJdbcTemplate.query(SELECT_BY_ID, params, new EnchereRowMapper());
    }

    @Override
    public List<Enchere> readByUtilID(long idUtilisateur) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("no_utilisateur", idUtilisateur);
        return namedParameterJdbcTemplate.query(SELECT_BY_ID_UTIL, params, new EnchereRowMapper());
    }

    @Override
    public void save(Enchere enchere) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        long idUtil = enchere.getUtilisateur().getNoUtilisateur();
        params.addValue("no_utilisateur",idUtil);
        params.addValue("no_article", enchere.getArticleVendu().getNoArticle());
        params.addValue("date_enchere", enchere.getDateEnchere());
        params.addValue("montant_enchere",enchere.getMontantEnchere());
        namedParameterJdbcTemplate.update(INSERT, params);
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public List<Enchere> findAll() {
        List<Enchere> encheres = jdbcTemplate.query(SELECT_ALL, new EnchereRowMapper());
        return encheres;
    }
}

class EnchereRowMapper implements RowMapper<Enchere> {

    @Override
    public Enchere mapRow(ResultSet rs, int rowNum) throws SQLException {
        Enchere enchere = new Enchere();
        Utilisateur utilisateur = new Utilisateur();
        ArticleVendu articleVendu = new ArticleVendu();
        utilisateur.setNoUtilisateur(rs.getLong("no_utilisateur"));
        articleVendu.setNoArticle(rs.getLong("no_article"));
        enchere.setUtilisateur(utilisateur);
        enchere.setArticleVendu(articleVendu);
        Date date = rs.getDate("date");
        enchere.setDateEnchere(date);
        int montant = rs.getInt("montant");
        enchere.setMontantEnchere(montant);
        return enchere;
    }
}

