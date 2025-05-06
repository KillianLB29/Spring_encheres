package fr.eni.tp.spring_encheres.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
public class SecurityConfiguration {

    @Autowired
    CustomLoginSuccessHandler customLoginSuccessHandler;

    @Bean
    UserDetailsManager userDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        jdbcUserDetailsManager.setUsersByUsernameQuery("Select pseudo as username , mot_de_passe as password ,1 from UTILISATEURS where pseudo=?;");
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("SELECT pseudo AS username, role AS authority FROM UTILISATEURS JOIN ROLES ON administrateur = is_admin WHERE pseudo = ?");
        return jdbcUserDetailsManager;
    }
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(
                auth -> {
                    auth
                            .requestMatchers(HttpMethod.GET, "/encheres/*").hasRole("MEMBRE")
                            .requestMatchers(HttpMethod.GET, "/articles/creer").hasRole("MEMBRE")
                            .requestMatchers(HttpMethod.POST, "/articles/creer").hasRole("MEMBRE")
                            .requestMatchers(HttpMethod.POST, "/encherir/*").hasRole("MEMBRE")
                            .requestMatchers(HttpMethod.GET, "/monProfil").hasRole("MEMBRE")
                            .requestMatchers(HttpMethod.POST, "/monProfil").hasRole("MEMBRE")
                            .requestMatchers(HttpMethod.GET,"/profil/*").hasRole("MEMBRE")
                            .requestMatchers(HttpMethod.POST, "/test").permitAll()
                            .requestMatchers(HttpMethod.POST,"/login/connect/*").permitAll()
                            .requestMatchers(HttpMethod.POST,"/inscription").permitAll()
                            .requestMatchers(HttpMethod.POST,"/login").permitAll()
                            .requestMatchers(HttpMethod.GET,"/login").permitAll()
                            .requestMatchers("/").permitAll()
                            .requestMatchers("/css/*").permitAll()
                            .requestMatchers("/img/*").permitAll()
                            .anyRequest().authenticated();
                }
        );

        http.formLogin(form -> {
            form.loginPage("/login").permitAll();
            form.successHandler(customLoginSuccessHandler);
        });

        http.logout(logout ->
                logout
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .clearAuthentication(true)
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/?logout")
        );

        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
