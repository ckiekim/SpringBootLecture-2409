package com.hae.demo.config;

import com.hae.demo.filter.JwtRequestFilter;
import com.hae.demo.service.MyOAuth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Autowired private MyOAuth2UserService myOAuth2UserService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(auth -> auth.disable())   // CSRF(Cross-Site Request Forgery) 방어 기능 비활성화
                .headers(x -> x.frameOptions(y -> y.disable()))     // H2 - console, CK Editor 사용
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/user/register", "/mall/list",
                                "/book/**",         // 통합 테스트 하는 경우
                                "/img/**", "/js/**", "/css/**", "/error/**").permitAll()
                        .requestMatchers("/user/list", "/user/update", "/user/delete",
                                "/order/listAll", "/order/bookStat").hasAuthority("ROLE_ADMIN")
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
                )
                .oauth2Login(auth -> auth
                        .loginPage("/user/login")
                        .userInfoEndpoint(user -> user.userService(myOAuth2UserService))
                        .defaultSuccessUrl("/user/loginSuccess", true)
                )
        ;
        return httpSecurity.build();
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
