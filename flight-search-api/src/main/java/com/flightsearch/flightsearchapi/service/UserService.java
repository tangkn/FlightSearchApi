package com.flightsearch.flightsearchapi.service;

import com.flightsearch.flightsearchapi.entity.UserInfo;
import com.flightsearch.flightsearchapi.repository.UserInfoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    private PasswordEncoder passwordEncoder;


    public UserInfo createUser(UserInfo userInfo){
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        return userInfoRepository.save(userInfo);
    }
}
