package com.serjeshs.usersvk.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

//    @Autowired
//    private AuthenticationManager authenticationManager;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
//                .antMatchers("/css/**", "/signin").permitAll()
//                .antMatchers("/css/**", "/signin", "vk.com/**", "oauth.vk.com/**").permitAll()
//                .antMatchers("/css/**", "/signin", "vk.com/**", "oauth.vk.com/**", "/callback").permitAll()
                .antMatchers("/css/**", "/webjars/**", "/js/**", "/signin", "vk.com/**", "oauth.vk.com/**", "/callback").permitAll()
//                .antMatchers("/**").permitAll()
//                    .antMatchers("/index.html").permitAll()
                .anyRequest().authenticated()
                .and().formLogin().loginPage("/login").defaultSuccessUrl("/index").permitAll()
                .and().logout().logoutUrl("/logout").permitAll();
//                .and().logout().logoutUrl("/login").permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
//        return null;
    }
    //        }*/
    //
    //        /*if(SecurityContextHolderAwareRequestWrapper.isUserInRole("ADMIN")){
    //
    //                .withUser("uit05oaa").password("123").roles("USER");
    //                .and()
    //                .withUser("root").password("root123").roles("ADMIN")
    //        auth.inMemoryAuthentication()
    //    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//    @Autowired

//    }
//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return new PasswordEncoder() {
//            @Override
//            public String encode(CharSequence charSequence) {
//                return charSequence.toString();
//            }
//
//            @Override
//            public boolean matches(CharSequence charSequence, String s) {
//                return charSequence.toString().equals(s);
//            }
//        };
//    }
}
