package com.fpt.g2.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fpt.g2.constant.CommonConstant;
import com.fpt.g2.entity.Setting;
import com.fpt.g2.entity.User;
import com.fpt.g2.entity.UserRole;
import com.fpt.g2.repository.SettingRepository;
import com.fpt.g2.repository.UserRepository;
import com.fpt.g2.repository.UserRoleRepository;
import com.fpt.g2.utils.GooglePojo;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository repository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private SettingRepository settingRepository;
    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;
    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;
    @Value("${google.redirect.uri}")
    private String uri;
    @Value("${google.link.get.token}")
    private String token;
    @Value("${google.link.get.user_info}")
    private String userInfo;

    public String getToken(final String code) throws ClientProtocolException, IOException {
        String link = token;
        String response = Request.Post(link)
                .bodyForm(Form.form().add("client_id", clientId)
                        .add("client_secret", clientSecret)
                        .add("redirect_uri", uri).add("code", code)
                        .add("grant_type", "authorization_code").build())
                .execute().returnContent().asString();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(response).get("access_token");
        return node.textValue();
    }
    public GooglePojo getUserInfo(final String accessToken) throws ClientProtocolException, IOException {
        String link = userInfo + accessToken;
        String response = Request.Get(link).execute().returnContent().asString();
        ObjectMapper mapper = new ObjectMapper();
        GooglePojo googlePojo = mapper.readValue(response, GooglePojo.class);
        return googlePojo;
    }
    public void saveGooglePojo(GooglePojo googlePojo){
        User user = repository.findByEmailOrUsernameOrMobile(googlePojo.getEmail());
        if(user == null){
            user = new User();
            user.setEmail(googlePojo.getEmail());
            user.setUsername(googlePojo.getEmail().split("@")[0]);
            user.setFullName(googlePojo.getName());
            user.setMobile(CommonConstant.EMPTY_STRING);
            user.setSignupType(CommonConstant.SIGNUP_GOOGLE);
            repository.saveAndFlush(user);

            Setting setting = settingRepository.findByTypeAndTitle(CommonConstant.SETTING_TYPE_ROLE,CommonConstant.ROLE_STUDENT);

            UserRole userRole = new UserRole();
            userRole.setUser(user);
            userRole.setSetting(setting);
            userRole.setActive(true);
            userRoleRepository.save(userRole);
        }
        else{
            user.setFullName(googlePojo.getName());
            repository.save(user);
        }
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.findByEmailOrUsernameOrMobile(email);
        if (user == null) {
            System.out.println("User not found! " + email);
            throw new UsernameNotFoundException("User " + email + " was not found in the database");
        }

        List<GrantedAuthority> grantList = new ArrayList<>();
        if (user.getRoles() != null) {
            for (UserRole userRole : user.getRoles()) {
                GrantedAuthority authority = new SimpleGrantedAuthority(userRole.getSetting().getTitle());
                grantList.add(authority);
            }
        }

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword() == null ? "" :user.getPassword(), grantList);

        return userDetails;
    }

    public User getUserByUsername(String username){
        User user = repository.findUserByUsernameAndDeleteFlagIsFalse(username);
        return user;
    }

    void addUser() {
        User user = new User();
        user.setUsername("admin");
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setPassword(bCryptPasswordEncoder.encode("vuducbinh"));
        repository.save(user);
    }
}
