package com.epam.news.config;

import com.epam.news.security.AuthProviderImpl;
import com.epam.news.security.CustomAccessDeniedHandler;
import com.epam.news.util.MessageLocaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.epam.news.util.Constant.COMPONENT_SCAN_SECURITY;


@Configuration
@EnableWebSecurity
@ComponentScan(COMPONENT_SCAN_SECURITY)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private AuthProviderImpl authProvider;
    CustomAccessDeniedHandler customAccessDeniedHandler;

    @Autowired
    public void setAuthProviderImpl(AuthProviderImpl authProvider) {
        this.authProvider = authProvider;
    }
    @Autowired
    public void setCustomAccessDeniedHandler(CustomAccessDeniedHandler customAccessDeniedHandler) {
        this.customAccessDeniedHandler = customAccessDeniedHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/signInPage", "/signIn", "/signUp").anonymous()
                .antMatchers("/addPage",
                        "/edit/*",
                        "/delete/*",
                        "/deleteSetNews*",
                        "/newsListByAuthor").hasAnyAuthority("AUTHOR", "ADMIN")
                .antMatchers("/admin/**").hasAuthority("ADMIN")
                .antMatchers("/addComment").authenticated()
                .and().csrf().disable()
                .formLogin()
                .loginPage("/signInPage")
                .loginProcessingUrl("/signIn")
                .failureUrl("/signInPage?error=true")
                .usernameParameter("login")
                .passwordParameter("password")
                .and()
                .exceptionHandling()
                .accessDeniedHandler(customAccessDeniedHandler)
                .and().logout();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authProvider);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
