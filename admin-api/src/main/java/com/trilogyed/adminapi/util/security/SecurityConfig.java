package com.trilogyed.adminapi.util.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
/*import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;*/

import javax.sql.DataSource;

@Configuration
//use this tiny SecurityConfig class when trying to run all of the tests bc otherwise all of them fail bc security creates issues
//also comment out the 2 security dependencies in the pom.xml file :)
//public class SecurityConfig{}
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    DataSource dataSource;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder authBuilder) throws Exception {

        PasswordEncoder encoder = new BCryptPasswordEncoder();

        authBuilder.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(
                        "select username, password, enabled from users where username = ?")
                .authoritiesByUsernameQuery(
                        "select username, authority from authorities where username = ?")
                .passwordEncoder(encoder);
    }

    public void configure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.httpBasic();

        httpSecurity.authorizeRequests()
                .mvcMatchers(HttpMethod.GET, new String[] {"/customers", "/invoices","/invoiceItems","/levelUp","/products"}).hasAuthority("EMPLOYEE")
                .mvcMatchers(HttpMethod.PUT, "/products/inventory").hasAuthority("EMPLOYEE")
                .mvcMatchers(HttpMethod.PUT, new String[] {"/customers", "/invoices","/invoiceItems","/levelUp","/products"}).hasAuthority("TEAM_LEAD")
                .mvcMatchers(HttpMethod.POST, "/customers").hasAuthority("TEAM_LEAD")
                .mvcMatchers(HttpMethod.POST, new String[] {"/customers", "/invoices","/invoiceItems","/levelUp","/products"}).hasAuthority("MANAGER")
                .mvcMatchers(HttpMethod.PUT, new String[] {"/customers", "/invoices","/invoiceItems","/levelUp","/products"}).hasAuthority("MANAGER")
                .mvcMatchers(HttpMethod.GET, new String[] {"/customers", "/invoices","/invoiceItems","/levelUp","/products"}).hasAuthority("MANAGER")
                .mvcMatchers(HttpMethod.DELETE, new String[] {"/customers", "/invoices","/invoiceItems","/levelUp","/products"}).hasAuthority("ADMIN")
                .anyRequest().permitAll();

        httpSecurity
                .formLogin()
                .loginPage("/login")
                .failureUrl("/login?error=true")
                .defaultSuccessUrl("/login/home"); //add a home endpoint in the controller bruhv

        httpSecurity.
                logout()
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login")
                .deleteCookies("JSESSIONID")
                .deleteCookies("XSRF-TOKEN")
                .invalidateHttpSession(true); // TODO - this doesn't seem to invalidate the session

        httpSecurity.
                csrf().
                csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
    }
}

