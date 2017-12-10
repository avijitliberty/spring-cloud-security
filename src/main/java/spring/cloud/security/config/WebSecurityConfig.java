package spring.cloud.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
				
		http
		.formLogin()
        .loginPage("/login")
        .failureUrl("/login-error")
    .and()
        .logout()
        .logoutSuccessUrl("/home")
    .and()
        .authorizeRequests()
        .antMatchers("/", "/home").permitAll()
        .antMatchers("/hello").access("hasRole('ROLE_ADMIN')")
        .antMatchers("/time").access("hasRole('ROLE_USER')")
     .and()
        .exceptionHandling()
        .accessDeniedPage("/403.html");
		/*
        .authorizeRequests()
            .antMatchers("/", "/home").permitAll()
            .antMatchers("/hello").access("hasRole('ROLE_ADMIN')")
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .loginPage("/login").permitAll()
            .failureUrl("/login-error.html")
            .and()
            .logout().permitAll()
            .and()
			.exceptionHandling().accessDeniedPage("/login?error=true")*/;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
		    .inMemoryAuthentication()
		    .withUser("jim").password("demo").roles("ADMIN").and()
            .withUser("bob").password("demo").roles("USER").and()
            .withUser("ted").password("demo").roles("USER","ADMIN");
	}
	
	/*@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}*/

}
