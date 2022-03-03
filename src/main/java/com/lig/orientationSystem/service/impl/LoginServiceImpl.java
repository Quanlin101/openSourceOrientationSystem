//package com.lig.orientationSystem.service.impl;
//
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import com.lig.orientationSystem.controller.dto.UserDTO;
//import com.lig.orientationSystem.dao.UserMapper;
//import com.lig.orientationSystem.service.LoginService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class LoginServiceImpl extends ServiceImpl<UserMapper, UserDTO> implements LoginService {
//
//    @Autowired
//    UserMapper userMapper;
//    public UserDTO findById(int userId) {
//        return new UserDTO();
//    }
//
//
//    public String getToken(UserDTO userDTO) {
//        String token="";
//        token= JWT.create().withAudience(userDTO.getUserName())
//                .sign(Algorithm.HMAC256(userDTO.getPassword()));
//        return token;
//    }
//
//}
