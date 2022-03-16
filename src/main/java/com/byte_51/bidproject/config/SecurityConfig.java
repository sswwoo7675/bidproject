package com.byte_51.bidproject.config;

import com.byte_51.bidproject.security.service.BidUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//스프링 시큐리티 설정 클래스
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true) //어노테이션 기반의 접근제한을 설정할 수 있게 해줌
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private BidUserDetailsService userDetailsService;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public void configure(WebSecurity web) throws Exception{
        web.ignoring().antMatchers("/css/**","/js/**");
    }

    protected void configure(HttpSecurity http) throws Exception{
        http.formLogin()
                .failureUrl("/login?error=1")
                .loginPage("/login");

        http.csrf().disable();
        http.logout().logoutSuccessUrl("/main");

        http.rememberMe().tokenValiditySeconds(60*60*24*7).userDetailsService(userDetailsService);
    }
}
