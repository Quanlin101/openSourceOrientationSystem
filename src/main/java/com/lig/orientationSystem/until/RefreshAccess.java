package com.lig.orientationSystem.until;

import com.lig.orientationSystem.until.error.GlobalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@Slf4j
//定时器
// https://blog.csdn.net/Peng945/article/details/95518455
public class RefreshAccess {

    AccessTokenUtils accessTokenUtils = new AccessTokenUtils();
    // 第一次延迟1秒执行，当执行完后7100秒再执行
    @Scheduled(initialDelay = 1000, fixedDelay = 7000 * 1000)
    public void setAccessToken() {
        String access_token = accessTokenUtils.setToken();
        if (access_token == null){
            log.error("自动更新出错，检查access_token获取的配置");
            throw new GlobalException("更新access_token出错");
        }
    }
}
