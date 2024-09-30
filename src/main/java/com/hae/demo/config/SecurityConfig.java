package com.hae.demo.config;

import com.hae.demo.filter.JwtRequestFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(auth -> auth.disable())   // CSRF(Cross-Site Request Forgery) 방어 기능 비활성화
                .headers(x -> x.frameOptions(y -> y.disable()))     // H2 - console, CK Editor 사용
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/user/register", "/mall/list",
                                "/img/**", "/js/**", "/css/**", "/error/**").permitAll()
                        .requestMatchers("/user/list", "/user/update", "/user/delete",
                                "/book/**", "/order/listAll", "/order/bookStat").hasAuthority("ROLE_ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(auth -> auth
                        .loginPage("/user/login")       // 내가 만든 로그인 폼 화면의 URL
                        .usernameParameter("uid")
                        .passwordParameter("pwd")
                        .defaultSuccessUrl("/user/loginSuccess", true)      // 로그인 후 해야할 일
                        .permitAll()
                )
                .logout(auth -> auth
                        .logoutUrl("/user/logout")
                        .invalidateHttpSession(true)    // 로그아웃시 세션 삭제
                        .deleteCookies("JSESSIONID")    // 로그아웃시 쿠키 삭제
                        .logoutSuccessUrl("/user/login")
                );
        return http.build();
    }

    // JWT 필터 빈 등록
    @Bean
    public JwtRequestFilter jwtRequestFilter() {
        return new JwtRequestFilter();
    }

    // AuthenticationManager 빈 등록
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
