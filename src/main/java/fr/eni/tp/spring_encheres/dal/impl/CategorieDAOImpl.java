package fr.eni.tp.spring_encheres.dal.impl;

import fr.eni.tp.spring_encheres.bo.Categorie;
import fr.eni.tp.spring_encheres.dal.CategorieDAO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CategorieDAOImpl implements CategorieDAO {
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public CategorieDAOImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    private final static String SELECT_ALL = "SELECT * FROM CATEGORIES";
    private final static String SELECT_BY_ID = "SELECT * FROM CATEGORIES WHERE no_categorie = :no_categorie";
    private final static String INSERT = "INSERT INTO CATEGORIES VALUES (:libelle);";
    private final static String DELETE = "DELETE FROM CATEGORIES WHERE no_categorie = :no_categorie";

    @Override
    public List<Categorie> findAll() {
        List<Categorie> categories = jdbcTemplate.query(SELECT_ALL, new CategorieRowMapper());
        return categories;
    }

    @Override
    public Categorie read(long idCategorie) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("no_categorie", idCategorie);
        Categorie categorie = new Categorie();
        categorie = namedParameterJdbcTemplate.queryForObject(SELECT_BY_ID,params,new CategorieRowMapper());
        return categorie;
    }

    @Override
    public void create(Categorie categorie) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("libelle", categorie.getLibelle());
        namedParameterJdbcTemplate.update(INSERT,params);
    }

    @Override
    public void delete(long idCategorie) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("no_categorie", idCategorie);
        namedParameterJdbcTemplate.update(DELETE,params);
    }
}

class CategorieRowMapper implements RowMapper<Categorie> {

    @Override
    public Categorie mapRow(ResultSet rs, int rowNum) throws SQLException {
        Categorie categorie = new Categorie();
        categorie.setIdCategorie(rs.getLong("no_categorie"));
        categorie.setLibelle(rs.getString("libelle"));
        return categorie;
    }
}
