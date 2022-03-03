//package com.lig.orientationSystem.controller;
//
//import com.lig.orientationSystem.controller.dto.R;
//import com.lig.orientationSystem.controller.dto.UserDTO;
//import com.lig.orientationSystem.until.JWTUtils;
//import com.lig.orientationSystem.until.token.PassToken;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.web.bind.annotation.*;
//
//@CrossOrigin
//@RestController
//@RequestMapping("/myLogin")
//public class LoginController {
//
//    @Autowired
//    LoginServiceImpl loginService;
//
//
////    管理员登陆
//    @PassToken
//    @PostMapping
//    public R loginSuccess(@RequestBody UserDTO userDTO) {
//
//        UserDTO login = loginService.findById(userDTO.getId());
//        if (login == null) {
//            return R.error("登陆失败，账户不存在");
//        } else {
//            if (!userDTO.getPassword().equals(login.getPassword())) {
//                return R.error("登陆失败，用户名或密码错误");
//            }
//            else {
//                return R.ok(JWTUtils.createToken(String.valueOf(userDTO.getId())));
//            }
//
//        }
//    }
//}
