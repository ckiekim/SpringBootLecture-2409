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
import java.util.Map;

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
                            .regDate(LocalDate.now()).role("ROLE_USER").provider(provider)
                            .build();
                    userService.registerUser(user);
                    user = userService.getUserByUid(uid);
                    log.info("깃허브 계정을 통해 회원가입이 되었습니다.");
                }
                break;
            case "google":
                String gid = oAuth2User.getAttribute("sub");
                uid = provider + "_" + gid;
                user = userService.getUserByUid(uid);
                if (user == null) {                // 가입이 안되어 있으므로 가입 진행
                    uname = oAuth2User.getAttribute("name");
                    uname = (uname == null) ? "google_user" : uname;
                    email = oAuth2User.getAttribute("email");
                    user = User.builder()
                            .uid(uid).pwd(hashedPwd).uname(uname).email(email)
                            .regDate(LocalDate.now()).role("ROLE_USER").provider(provider)
                            .build();
                    userService.registerUser(user);
                    user = userService.getUserByUid(uid);
                    log.info("구글 계정을 통해 회원가입이 되었습니다.");
                }
                break;
            case "facebook":
                String fid = oAuth2User.getAttribute("id");
                uid = provider + "_" + fid;
                user = userService.getUserByUid(uid);
                if (user == null) {
                    uname = oAuth2User.getAttribute("name");
                    uname = (uname == null) ? "facebook_user" : uname;
                    email = oAuth2User.getAttribute("email");
                    user = User.builder()
                            .uid(uid).pwd(hashedPwd).uname(uname).email(email)
                            .regDate(LocalDate.now()).role("ROLE_USER").provider(provider)
                            .build();
                    userService.registerUser(user);
                    user = userService.getUserByUid(uid);
                    log.info("페이스북 계정을 통해 회원가입이 되었습니다.");
                }
                break;
            case "naver":
                Map<String, Object> response = (Map) oAuth2User.getAttribute("response");
                String nid = (String) response.get("id");
                uid = provider + "_" + nid;
                user = userService.getUserByUid(uid);
                if (user == null) {				// 가입이 안되어 있으므로 가입 진행
                    uname = (String) response.get("nickname");
                    uname = (uname == null) ? "naver_user" : uname;
                    email = (String) response.get("email");
                    user = User.builder()
                            .uid(uid).pwd(hashedPwd).uname(uname).email(email)
                            .regDate(LocalDate.now()).role("ROLE_USER").provider(provider)
                            .build();
                    userService.registerUser(user);
                    user = userService.getUserByUid(uid);
                    log.info("네이버 계정을 통해 회원가입이 되었습니다.");
                }
                break;
            case "kakao":
                long kid = (long) oAuth2User.getAttribute("id");
                uid = provider + "_" + kid;
                user = userService.getUserByUid(uid);
                if (user == null) {				// 가입이 안되어 있으므로 가입 진행
                    Map<String, String> properties = (Map) oAuth2User.getAttribute("properties");
                    Map<String, Object> account = (Map) oAuth2User.getAttribute("kakao_account");
                    uname = (String) properties.get("nickname");
                    uname = (uname == null) ? "kakao_user" : uname;
                    email = (String) account.get("email");
                    user = User.builder()
                            .uid(uid).pwd(hashedPwd).uname(uname).email(email)
                            .regDate(LocalDate.now()).role("ROLE_USER").provider(provider)
                            .build();
                    userService.registerUser(user);
                    user = userService.getUserByUid(uid);
                    log.info("카카오 계정을 통해 회원가입이 되었습니다.");
                }
                break;
        }
        return new MyUserDetails(user, oAuth2User.getAttributes());
    }
}
