package vn.hbm.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class CustomWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    @Bean("userLoginSuccessHandler")
    public AuthenticationSuccessHandler userLoginSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

    @Bean("customAuthenticationProvider")
    public AuthenticationProvider customAuthenticationProvider() {
        return new CustomAuthenticationProvider();
    }

    @Autowired
    private CustomAccessDeniedHandler accessDeniedHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers(
                            "/**",
                            "/home/**",
                            "/admin/**",
                            "/tools/**",
                            "/news/**",
                            "/assets/**",
                            "/js/**",
                            "/css/**",
                            "/img/**",
                            "/webjars/**").permitAll()
//                    .antMatchers("/user/**").hasRole("USER")
//                    .antMatchers("/admin/**").hasAnyAuthority("ADMIN")
                    .anyRequest().authenticated()
                .and()
                .exceptionHandling().and()
                .headers().frameOptions().disable().and()
                .formLogin()
                    .usernameParameter("j_username")
                    .passwordParameter("j_password")
                    .loginPage("/admin/login")
                    .loginProcessingUrl("/admin/login")
                    .successHandler(userLoginSuccessHandler())
                    .permitAll()
                .and()
                .logout()
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .logoutRequestMatcher(new AntPathRequestMatcher("/admin/logout"))
                    .logoutSuccessUrl("/admin/login?logout")
                    .permitAll()
                .and()
                .exceptionHandling()
                    .accessDeniedHandler(accessDeniedHandler);
    }
}