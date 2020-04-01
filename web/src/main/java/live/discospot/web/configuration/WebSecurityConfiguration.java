package live.discospot.web.configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Web security configuration.
 *
 * @author <a href="mailto:o.kuchynski@gmail.com">Aleh Kuchynski</a>
 * @since 1.0
 */
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/login", "/webjars/**", "/css/**", "/js/**", "/img/**").permitAll()
                .anyRequest().authenticated()
                .and()
                    .oauth2Login()
                    .loginPage("/login")
                    .defaultSuccessUrl("/dashboard")
                    .failureUrl("/loginFailure")
                .and()
                    .logout()
                    .logoutUrl("/logout")
                    .deleteCookies()
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .logoutSuccessUrl("/");
    }
}
