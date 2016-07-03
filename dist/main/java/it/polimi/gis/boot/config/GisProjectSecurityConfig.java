
package it.polimi.gis.boot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfTokenRepository;

@Configuration
@EnableWebSecurity
@Order(10)
public class GisProjectSecurityConfig
    extends WebSecurityConfigurerAdapter
{

    @Override
    protected void configure(HttpSecurity http)
        throws Exception
    {
        http
        .authorizeRequests()
        .antMatchers("/**").permitAll()
        .and()
        .authorizeRequests().anyRequest().fullyAuthenticated().and()
        .formLogin().and().csrf()
        .disable();
    }

    private CsrfTokenRepository csrfTokenRepository() {
        org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository repository = new org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository();
         repository.setHeaderName("X-XSRF-TOKEN");
        return repository;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        PasswordEncoder encoder = new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
        return encoder;
    }

}
