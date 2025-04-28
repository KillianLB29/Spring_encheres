package fr.eni.tp.spring_encheres.dal;

import fr.eni.tp.spring_encheres.bo.Utilisateur;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UtilisateurDAOImpl implements UtilisateurDAO {

    private final JdbcTemplate jdbcTemplate;

    public UtilisateurDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Utilisateur> findAll() {
        String sql = "SELECT * FROM UTILISATEURS";
        return jdbcTemplate.query(sql, new UtilisateurRowMapper());
    }

    @Override
    public Utilisateur read(long noUtilisateur) {
        String sql = "SELECT * FROM UTILISATEURS WHERE no_utilisateur = ?";
        return jdbcTemplate.queryForObject(sql, new UtilisateurRowMapper(), noUtilisateur);
    }

    @Override
    public void save(Utilisateur utilisateur) {
        String sql = "INSERT INTO UTILISATEURS (pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                utilisateur.getPseudo(),
                utilisateur.getNom(),
                utilisateur.getPrenom(),
                utilisateur.getEmail(),
                utilisateur.getTelephone(),
                utilisateur.getRue(),
                utilisateur.getCodePostal(),
                utilisateur.getVille(),
                utilisateur.getMotDePasse(),
                utilisateur.getCredit());
    }

    @Override
    public void delete(long noUtilisateur) {
        String sql = "DELETE FROM UTILISATEURS WHERE no_utilisateur = ?";
        jdbcTemplate.update(sql, noUtilisateur);
    }

    @Override
    public Utilisateur findByPseudo(String pseudo) {
        String sql = "SELECT * FROM UTILISATEURS WHERE pseudo = ?";
        return jdbcTemplate.queryForObject(sql, new UtilisateurRowMapper(), pseudo);
    }

    private static class UtilisateurRowMapper implements RowMapper<Utilisateur> {
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
}
