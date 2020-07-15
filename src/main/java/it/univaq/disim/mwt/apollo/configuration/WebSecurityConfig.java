package it.univaq.disim.mwt.apollo.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import it.univaq.disim.mwt.apollo.presentation.CustomAccessDeniedHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

	@Bean
	public AccessDeniedHandler accessDeniedHandler(){
	    return new CustomAccessDeniedHandler();
	}

    @Bean
    public AjaxAwareAuthenticationEntryPoint authenticationEntryPoint() {
        return new AjaxAwareAuthenticationEntryPoint("/login");
    }
	
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.headers().disable().csrf().disable().formLogin()
                .loginPage("/")
                .loginProcessingUrl("/login")
                .failureUrl("/?error=invalidlogin")
                .defaultSuccessUrl("/surveys/dashboard", false).and().logout()
                .logoutSuccessUrl("/").and().exceptionHandling().accessDeniedHandler(accessDeniedHandler()).and()
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint()).and()
                .authorizeRequests()
                .antMatchers("/administration/**").authenticated()
                .antMatchers("/administration/**").hasRole("ADMIN")
                // Specificare le url che sono soggette ad autenticazione ed autorizzazione
                .antMatchers("/surveys/**", "/answers/**", "/questions/**", "/questiongroups/**", "/user/update/**", "/forms/survey/findbysurveypaginated", "/forms/survey/**/answer/**").authenticated()
                .antMatchers("/", "/static/**", "/favicon.ico", "/forms/survey/**/fill", "/forms/survey/create", "/user/create/**", "/utility/**").permitAll();

    }
}
