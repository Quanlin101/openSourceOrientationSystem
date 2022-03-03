package com.lig.orientationSystem.service.impl;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class OssUtils {
    @Autowired
    private OSS client;
    @Value("${file.oss.bucket-name}")
    private String bucketName;
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

    public  String upload(MultipartFile multipartFile, String fileName){

        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        String datePath = format.format(new Date());
        String fileURL = null;

        if(multipartFile == null){
            return null;
        }

        try {
            //文件新名字
            String originalFilename = multipartFile.getOriginalFilename();
            String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            String newName = fileName+suffix;
            //创建文件路径
            String fileUrl = datePath +"/" + newName;
            //上传文件
            fileURL =  "https://orientation-system.oss-cn-beijing.aliyuncs.com/" + fileUrl;
            PutObjectResult putObjectResult = client.putObject(bucketName, fileUrl, multipartFile.getInputStream());
            //设置权限 这里是私有
            client.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
        }catch (OSSException oe){
            oe.printStackTrace();
        }catch (ClientException ce){
            ce.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        finally {
//            //关闭
//            client.shutdown();
//        }
        return fileURL;
    }


}
