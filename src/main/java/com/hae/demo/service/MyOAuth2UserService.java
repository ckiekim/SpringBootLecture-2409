package com.hae.demo.service;

import com.hae.demo.entity.MyUserDetails;
import com.hae.demo.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Slf4j
@Service
public class MyOAuth2UserService extends DefaultOAuth2UserService {
    @Autowired private UserService userService;
    @Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;

    // Provider(Github, Google 등)로부터 받은 userRequest 데이터에 대해 후처리하는 메소드
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        String uid, email, uname;
        String hashedPwd = bCryptPasswordEncoder.encode("Social Login");
        User user = null;

        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("getAttributes(): " + oAuth2User.getAttributes());

        String provider = userRequest.getClientRegistration().getRegistrationId();
        switch(provider) {
            case "github":
                int id = oAuth2User.getAttribute("id");
                uid = provider + "_" + id;
                user = userService.getUserByUid(uid);
                if (user == null) {                // 가입이 안되어 있으므로 가입 진행
                    uname = oAuth2User.getAttribute("name");
                    uname = (uname == null) ? "github_user" : uname;
                    email = oAuth2User.getAttribute("email");
                    user = User.builder()
                            .uid(uid).pwd(hashedPwd).uname(uname).email(email)
                            .provider(provider).regDate(LocalDate.now()).role("ROLE_USER")
                            .build();
                    userService.registerUser(user);
                    user = userService.getUserByUid(uid);
                    log.info("깃허브 계정을 통해 회원가입이 되었습니다.");
                }
                break;

            case "google":
            case "facebook":
            case "naver":
            case "kakao":
                break;
        }
        return new MyUserDetails(user, oAuth2User.getAttributes());
    }
}
