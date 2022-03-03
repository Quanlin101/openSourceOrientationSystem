package com.lig.orientationSystem.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "file.oss")
@Data
public class OssConfig {
    //访问密钥
    private String accessKeyId;
    private String accessKeySecret;
    //bucket的名字
    private String bucketName;
    //访问域名
    private String endpoint;

    @Bean
    public OSS ossClient() {
        return new OSSClientBuilder().build(this.endpoint,this.accessKeyId,this.accessKeySecret);
    }
}
