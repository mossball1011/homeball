package com.example.security.configs;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        String password = passwordEncoder().encode("1234"); // password

        auth.inMemoryAuthentication().withUser("user").password(password).roles("USER");
        auth.inMemoryAuthentication().withUser("manager").password(password).roles("USER","MANAGER");
        auth.inMemoryAuthentication().withUser("admin").password(password).roles("USER","MANAGER","ADMIN");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

/*    @Override
    public void configure(WebSecurity web) throws Exception {
        // 시큐리티 ignore 할 리소스 선정
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }*/

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/").permitAll()      // root 페이지는 permit을 올려 인증없이도 접근가능한 페이지
                .antMatchers("/mypage").hasRole("USER") // USER 권한 계정들이 가는 페이지
                .antMatchers("/messages").hasRole("MANAGER") // MANAGER 권한 계정들이 가는 페이지
                .antMatchers("/config").hasRole("ADMIN") // ADMIN 권한 계정들이 가는 페이지
                .anyRequest().authenticated()

        .and()
                .formLogin()
                ;
    }
}
