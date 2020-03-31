package com.zhu.provider;

import com.aliyun.oss.OSSClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class OSSProvider {

    @Value("${aliyun.oss.endpoint}")
    private String endpoint;
    @Value("${aliyun.accessKeyId}")
    private String accessKeyId;
    @Value("${aliyun.accessKeySecret}")
    private String accessKeySecret;

    @Value("${aliyun.oss.bucketName}")
    private String bucketName;
    @Value("${aliyun.oss.urlPrefix}")
    private String urlPrefix;

    //下载 图片至 OSS服务器
    public String upload(MultipartFile multipartFile,String businessType){
        // 创建 ossClient 通过这个实例 上传
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);

        String ext = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(".")).toLowerCase();
        //根据自定义规则，得到文件名
        String fileName = getFileName(businessType, ext);

        String url = null;

        try {
            ossClient.putObject(bucketName,fileName,new ByteArrayInputStream(multipartFile.getBytes()));
            url = urlPrefix + fileName;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return url;
    }

    private String getFileName(String businessType , String ext){
        String sdf = new SimpleDateFormat("yyyyMMdd").format(new Date());
        if(StringUtils.isEmpty(businessType)){
            businessType = "default";
        }
        String uuid = UUID.randomUUID().toString().replace("-","");
        return businessType + "/" + sdf + "/" + uuid + ext;
    }
}
