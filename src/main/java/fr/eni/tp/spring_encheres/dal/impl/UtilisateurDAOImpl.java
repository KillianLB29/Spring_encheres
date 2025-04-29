package fr.eni.tp.spring_encheres.dal.impl;

import fr.eni.tp.spring_encheres.bo.Utilisateur;
import fr.eni.tp.spring_encheres.dal.UtilisateurDAO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UtilisateurDAOImpl implements UtilisateurDAO {

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public UtilisateurDAOImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    private static final String SELECT_ALL = "SELECT * FROM UTILISATEURS";
    private static final String INSERT = "INSERT INTO UTILISATEURS (pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit,administrateur) VALUES (:pseudo, :nom, :prenom, :email, :telephone,:rue,:code_postal, :ville, :motdepasse, :credit,0)";
    private static final String DELETE = "DELETE FROM UTILISATEURS WHERE no_utilisateur = :no_utilisateur";
    private static final String SELECT_BY_PSEUDO = "SELECT * FROM UTILISATEURS WHERE pseudo = :pseudo";

    @Override
    public List<Utilisateur> findAll() {
        return jdbcTemplate.query(SELECT_ALL, new UtilisateurRowMapper());
    }

    @Override
    public Utilisateur read(long noUtilisateur) {
        String sql = "SELECT * FROM UTILISATEURS WHERE no_utilisateur = ?";
        return jdbcTemplate.queryForObject(sql, new UtilisateurRowMapper(), noUtilisateur);
    }

    @Override
    public void save(Utilisateur utilisateur) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("pseudo", utilisateur.getPseudo());
        params.addValue("nom", utilisateur.getNom());
        params.addValue("prenom", utilisateur.getPrenom());
        params.addValue("email", utilisateur.getEmail());
        params.addValue("telephone", utilisateur.getTelephone());
        params.addValue("rue", utilisateur.getRue());
        params.addValue("code_postal", utilisateur.getCodePostal());
        params.addValue("ville", utilisateur.getVille());
        params.addValue("motdepasse", utilisateur.getMotDePasse());
        params.addValue("credit", utilisateur.getCredit());
        namedParameterJdbcTemplate.update(INSERT, params);
    }

    @Override
    public void delete(long noUtilisateur) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("no_utilisateur", noUtilisateur);
        namedParameterJdbcTemplate.update(DELETE, params);
    }

    @Override
    public Utilisateur findByPseudo(String pseudo) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("pseudo", pseudo);
        return namedParameterJdbcTemplate.queryForObject(SELECT_BY_PSEUDO, params, new UtilisateurRowMapper());
    }
}
 class UtilisateurRowMapper implements RowMapper<Utilisateur> {
     @Override
     public Utilisateur mapRow(ResultSet rs, int rowNum) throws SQLException {
         Utilisateur utilisateur = new Utilisateur();
         utilisateur.setNoUtilisateur(rs.getLong("no_utilisateur"));
         utilisateur.setPseudo(rs.getString("pseudo"));
         utilisateur.setNom(rs.getString("nom"));
         utilisateur.setPrenom(rs.getString("prenom"));
         utilisateur.setEmail(rs.getString("email"));
         utilisateur.setTelephone(rs.getString("telephone"));
         utilisateur.setRue(rs.getString("rue"));
         utilisateur.setCodePostal(rs.getString("code_postal"));
         utilisateur.setVille(rs.getString("ville"));
         utilisateur.setMotDePasse(rs.getString("mot_de_passe"));
         utilisateur.setCredit(rs.getInt("credit"));
         return utilisateur;
     }
 }