package com.lig.orientationSystem.until;

import com.lig.orientationSystem.until.error.GlobalException;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
//定时器
// https://blog.csdn.net/Peng945/article/details/95518455
public class RefreshAccess {

    // 第一次延迟1秒执行，当执行完后7100秒再执行
    @Scheduled(initialDelay = 1000, fixedDelay = 7000 * 1000)
    public void setAccessToken() {
        String access_token = AccessTokenUtils.setToken();
        if (access_token == null){
            throw new GlobalException("更新access_token出错");
        }
    }
}
