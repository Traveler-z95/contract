package com.jinhui.contract.security.conf;

import com.jinhui.contract.security.auth.CustomAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * WebSecurityAdapter
 */
@Configuration
@EnableWebSecurity
public class WebSecurityAdapter extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/static/**","/","/login/**","/error/**").permitAll()
                .antMatchers("/admin/**").authenticated()
//                .and().rememberMe().tokenValiditySeconds(3600)

                .and().formLogin().loginPage("/login").defaultSuccessUrl("/admin/home").permitAll()
                .and().logout().logoutUrl("/admin/logout").logoutSuccessUrl("/login").invalidateHttpSession(true).permitAll();

    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/image/**","/js/**","/css/**","/admin/css/**","/admin/js/**","/admin/lib/**","/admin/plugins/**");
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        return new CustomAuthenticationProvider();
    }
}
