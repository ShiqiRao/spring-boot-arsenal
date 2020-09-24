package com.example.shield.config;

import com.example.shield.domain.R;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import javax.sql.DataSource;
import java.io.PrintWriter;

@Configuration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final ObjectMapper mapper;
    private final DataSource dataSource;
    private final UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //开启登录配置
                .authorizeRequests()
                //表示account接口的访问不受限制
                .antMatchers("/account").permitAll()
                .antMatchers("/write").hasAuthority("WRITE_AUTHORITY")
                .antMatchers("/read").hasAnyAuthority("READ_AUTHORITY", "WRITE_AUTHORITY")
                .antMatchers("/administrator").hasRole("ADMINISTRATOR")
                .antMatchers("/staff").hasAnyRole("STAFF", "ADMINISTRATOR")
                //表示所有接口，登录之后就能访问
                .anyRequest().authenticated()
                .and()
                .formLogin()
                //.loginPage("/login") 添加该项将定义登录页面，未登录时，访问一个需要登录之后才能访问的接口，会自动跳转到该页面
                //登录处理接口
                .loginProcessingUrl("/session")
                //定义登录时，用户名的 key，默认为 username
                .usernameParameter("uname")
                //定义登录时，用户密码的 key，默认为 password
                .passwordParameter("pwd")
                //登录成功的处理器
                .successHandler((req, resp, authentication) -> {
                    resp.setContentType("application/json;charset=utf-8");
                    PrintWriter out = resp.getWriter();
                    out.write(mapper.writeValueAsString(R.<Void>ok(null)));
                    out.flush();
                })
                //登陆失败的处理器
                .failureHandler((req, resp, exception) -> {
                    resp.setContentType("application/json;charset=utf-8");
                    PrintWriter out = resp.getWriter();
                    out.write(mapper.writeValueAsString(R.<Void>failed("failed")));
                    out.flush();
                })
                //和表单登录相关的接口可以不受限制直接通过
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                //登出成功的处理器
                .logoutSuccessHandler((req, resp, authentication) -> {
                    resp.setContentType("application/json;charset=utf-8");
                    PrintWriter out = resp.getWriter();
                    out.write(mapper.writeValueAsString(R.<Void>ok(null)));
                    out.flush();
                })
                .permitAll()
                .and()
                .rememberMe()
                .rememberMeParameter("remember-me")
                .tokenRepository(persistentTokenRepository())
                .userDetailsService(userDetailsService)
                .and()
                .httpBasic()
                .disable()
                .csrf()
                .disable()
                .sessionManagement()
                //并发session上限为1
                .maximumSessions(1)
                //达到上限后是否阻止登陆
                .maxSessionsPreventsLogin(true)
                //失效session策略
                .expiredSessionStrategy(new ParallelismSessionExpiredStrategy(mapper))
                .sessionRegistry(sessionRegistry());
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        return tokenRepository;
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        //用于访问Session
        return new SessionRegistryImpl();
    }

    @Bean
    public static ServletListenerRegistrationBean httpSessionEventPublisher() {
        //用于告诉Spring 将session信息存储于sessionRegistry
        return new ServletListenerRegistrationBean(new HttpSessionEventPublisher());
    }

}
