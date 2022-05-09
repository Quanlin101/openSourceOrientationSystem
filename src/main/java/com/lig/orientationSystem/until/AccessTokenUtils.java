package com.lig.orientationSystem.until;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AccessTokenUtils {
    static RestTemplate restTemplate = new RestTemplate();
//    @Value("${wx.corpid}") 不支持静态属性！
    public static String corpid = "corpid";
//    @Value("${wx.corpsecret}").

//    一个没解决的问题，在初始项目开源的时候遇到，导致只能clone开源:(
    /**
     * 如果这里不设置static并用value注入，依然不能用 git 的action secret，
     * 貌似是因为action secret 注入时机比spring autowire晚
     */
    public static String corpsecret = "corpsecret";

    public static String access_token = setToken();

    public static String setToken(){

        String url = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid="
                + AccessTokenUtils.corpid
                +"&corpsecret="
                +AccessTokenUtils.corpsecret;
//        System.out.println(AccessTokenUtils.corpid);
        ResponseEntity<String> result = restTemplate.getForEntity(url, String.class);
        String body = result.getBody();
//        System.out.println(body.toString());
        JSONObject jsonObject = JSONObject.parseObject(body);
        String access_token = String.valueOf(jsonObject.get("access_token"));
        AccessTokenUtils.access_token = access_token;
        if (access_token == "null"){
            return null;
        }
        //把access_token放在session里，方便拿取
        return access_token;
    }

}
