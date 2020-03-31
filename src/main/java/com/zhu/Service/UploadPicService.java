package com.zhu.Service;

import com.aliyun.oss.OSSClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

//@Service
//public class UploadPicService {
//    // 允许上传的格式
//    private static final String[] IMAGE_TYPE = new String[]{".bmp", ".jpg", ".jpeg", ".gif", ".png"};
//
//    @Autowired
//    private OSSClient ossClient;
//
//    @Autowired
//    private AliyunConfig aliyunConfig;
//
//    public PicUploadResult upload(MultipartFile multipartFile) {
//        // 1. 对上传的图片进行校验: 这里简单校验后缀名
//        // 另外可通过ImageIO读取图片的长宽来判断是否是图片,校验图片的大小等。
//        // TODO 图片校验
//        boolean isLegal = false;
//        for (String type : IMAGE_TYPE) {
//            if (StringUtils.endsWithIgnoreCase(multipartFile.getOriginalFilename(), type)) {
//                isLegal = true;
//                break;  // 只要与允许上传格式其中一个匹配就可以
//            }
//        }
//        PicUploadResult picUploadResult = new PicUploadResult();
//        // 格式错误, 返回与前端约定的error
//        if (!isLegal) {
//            picUploadResult.setStatus("error");
//            return picUploadResult;
//        }
//
//        // 2. 准备上传API的参数
//        String fileName = multipartFile.getOriginalFilename();
//        String filePath = this.getFilePath(fileName);
//
//        // 3. 上传至阿里OSS
//        try {
//            ossClient.putObject(aliyunConfig.getBucketName(), filePath, new ByteArrayInputStream(multipartFile.getBytes()));
//        } catch (IOException e) {
//            e.printStackTrace();
//            // 上传失败
//            picUploadResult.setStatus("error");
//            return picUploadResult;
//        }
//
//        // 上传成功
//        picUploadResult.setStatus("done");
//        // 文件名(即直接访问的完整路径)
//        picUploadResult.setName(aliyunConfig.getUrlPrefix() + filePath);
//        // uid
//        picUploadResult.setUid(String.valueOf(System.currentTimeMillis()));
//        return picUploadResult;
//    }
//
//    /**
//     * 上传的目录
//     * 目录: 根据年月日归档
//     * 文件名: 时间戳 + 随机数
//     * @param fileName
//     * @return
//     */
//    private String getFilePath(String fileName) {
//        DateTime dateTime = new DateTime();
//        return "images/" + dateTime.toString("yyyy") + "/" + dateTime.toString("MM") + "/"
//                + dateTime.toString("dd") + "/" + System.currentTimeMillis() +
//                RandomUtils.nextInt(100, 9999) + "." + StringUtils.substringAfterLast(fileName, ".");
//    }
