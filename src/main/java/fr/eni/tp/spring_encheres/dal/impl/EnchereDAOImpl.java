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

    private final String SELECT_ALL = "SELECT * FROM ENCHERES";

    @Override
    public List<Enchere> read(Integer idArticle) {
        return List.of();
    }

    @Override
    public void save(Enchere enchere) {

    }

    @Override
    public void delete(long id) {

    }

    @Override
    public List<Enchere> findAll() {
        return List.of();

    }
}

class EnchereRowMapper implements RowMapper<Enchere> {

    @Override
    public Enchere mapRow(ResultSet rs, int rowNum) throws SQLException {
        Enchere enchere = new Enchere();
        Utilisateur utilisateur = new Utilisateur();
        ArticleVendu articleVendu = new ArticleVendu();
        articleVendu.setNoArticle(rs.findColumn("no_article"));
        utilisateur.setNoUtilisateur(rs.findColumn("no_utilisateur"));
        enchere.setUtilisateur(utilisateur);
        enchere.setArticleVendu(articleVendu);
        Date dateEnchere = rs.getDate("date_enchere");
        enchere.setDateEnchere(dateEnchere);
        enchere.setMontantEnchere(rs.findColumn("montant_enchere"));
        return enchere;
    }
}

