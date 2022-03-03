package com.lig.orientationSystem.controller;

import com.alibaba.fastjson.JSONObject;
import com.lig.orientationSystem.controller.dto.R;
import com.lig.orientationSystem.service.impl.InterviewerServiceImpl;
import com.lig.orientationSystem.until.AccessTokenUtils;
import com.lig.orientationSystem.until.JWTUtils;
import com.lig.orientationSystem.until.token.PassToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;

@CrossOrigin
@RestController
@RequestMapping("/WXLogin")
public class WXLoginController {
    @Autowired
    RestTemplate restTemplate;
    @Value("${wx.corpid}")
    String corpid;
    @Value("${wx.corpsecret}")
    String corpsecret;

    @Autowired
    InterviewerServiceImpl interviewerService;
    @GetMapping
    @PassToken
    public R getAccessToken(String code){

        String access_token = AccessTokenUtils.access_token;
        String url_mid =  "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token="
                +access_token
                +"&code="
                +code;
        ResponseEntity<String> result_mid = restTemplate.getForEntity(url_mid, String.class);
        String body_mid = result_mid.getBody();
        JSONObject jsonObject_mid = JSONObject.parseObject(body_mid);
        String UserId = String.valueOf(jsonObject_mid.get("UserId"));
        String errcode = String.valueOf(jsonObject_mid.get("errcode"));
//        System.out.println("errcode" + errcode);
        if (UserId == "null"){
            return R.error("UserId为空");
        }
        Integer interviewer_id = interviewerService.isExist(UserId);
        if (interviewer_id == null){
            R r = new R(false,5,"您没有权限进入此应用，请联系该应用的管理员", null);
            return r;
        }
        String token = JWTUtils.createToken(UserId);
        System.out.println(token);
        return R.ok(token);
    }
}
