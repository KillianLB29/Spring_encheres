package fr.eni.tp.spring_encheres.dal.impl;

import fr.eni.tp.spring_encheres.bo.Retrait;
import fr.eni.tp.spring_encheres.dal.RetraitDAO;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class RetraitDAOImpl implements RetraitDAO {
    private final static String FIND_BY_ID = "SELECT * FROM RETRAITS WHERE no_article=?";

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    NamedParameterJdbcTemplate t;

    class RetraitMapper implements RowMapper<Retrait> {
        public Retrait mapRow(ResultSet rs, int rowNum) throws SQLException {
            Retrait retrait = new Retrait();

            retrait.setRue(rs.getString("rue"));
            retrait.setCode_Postal(rs.getString("code_postal"));
            retrait.setVille(rs.getString("ville"));

            return retrait;
            }
        }

        @Override
    public Retrait read(long idRetrait) {
        t = namedParameterJdbcTemplate;
        Retrait retrait = t.getJdbcOperations().queryForObject(FIND_BY_ID, new RetraitMapper(), idRetrait);
        return retrait;
        }

        @Override
    public void save(Retrait retrait) {
        }
}
