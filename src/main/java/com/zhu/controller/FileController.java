package com.zhu.controller;

import com.zhu.pojo.FileDTO;
import com.zhu.provider.OSSProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@Controller
public class FileController {
    @Autowired
    private OSSProvider ossProvider;

    @RequestMapping("/file/upload")
    @ResponseBody
    public FileDTO upload(HttpServletRequest request) throws Exception {
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = ((MultipartHttpServletRequest) request).getFile("editormd-image-file");
        MultipartFile multipartFile = new MockMultipartFile(file.getName(), file.getOriginalFilename(), file.getContentType(), file.getInputStream());
        String url = ossProvider.upload(multipartFile, file.getContentType());

        FileDTO fileDTO = new FileDTO();
        fileDTO.setUrl(url);
        fileDTO.setSuccess(1);
        return fileDTO;
    }
}
