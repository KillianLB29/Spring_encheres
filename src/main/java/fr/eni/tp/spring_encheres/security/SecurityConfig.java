package fr.eni.tp.spring_encheres.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

import javax.sql.DataSource;

//@Configuration
public class SecurityConfig {

//    @Bean
//    UserDetailsManager userDetailsManager(DataSource dataSource) {
//        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
//        jdbcUserDetailsManager.setUsersByUsernameQuery("Select pseudo as username , mot_de_passe as password ,1 from UTILISATEURS where pseudo=?;");
//        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("SELECT pseudo AS username, role AS authority FROM membre JOIN ROLES ON admin = is_admin WHERE email = ?");
//        return jdbcUserDetailsManager;
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
}
