package fr.eni.tp.spring_encheres.dal.impl;

import fr.eni.tp.spring_encheres.bo.Retrait;
import fr.eni.tp.spring_encheres.dal.RetraitDAO;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class RetraitDAOImpl implements RetraitDAO {
    private final static String FIND_BY_ID = "SELECT * FROM RETRAITS WHERE no_article=:no_article";
    private final static String INSERT = "INSERT INTO RETRAITS VALUES (:no_article,:rue,:code_postal,:ville)";

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public RetraitDAOImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Retrait read(long idRetrait) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("no_article", idRetrait);
        Retrait retrait = namedParameterJdbcTemplate.queryForObject(FIND_BY_ID,mapSqlParameterSource,new RetraitRowMapper());
        return retrait;
        }

        @Override
    public void save(Retrait retrait,long idArticle) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("rue", retrait.getRue());
        mapSqlParameterSource.addValue("ville", retrait.getVille());
        mapSqlParameterSource.addValue("no_article",idArticle);
        mapSqlParameterSource.addValue("code_postal",retrait.getCode_Postal());
        namedParameterJdbcTemplate.update(INSERT,mapSqlParameterSource);
        }
}

class RetraitRowMapper implements RowMapper<Retrait> {
    public Retrait mapRow(ResultSet rs, int rowNum) throws SQLException {
        Retrait retrait = new Retrait();

        retrait.setRue(rs.getString("rue"));
        retrait.setCode_Postal(rs.getString("code_postal"));
        retrait.setVille(rs.getString("ville"));

        return retrait;
    }
}
