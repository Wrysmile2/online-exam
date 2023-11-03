package com.xyh.service.impl;


import com.xyh.service.FileUpLoadService;
import com.xyh.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@Slf4j
public class FileUpLoadServiceImpl implements FileUpLoadService {
    @Value("${img.basePath}")
    private String basePath;

    @Override
    public String uploadFile(MultipartFile multipartFile) throws IOException {
        long attachSize = multipartFile.getSize();
        String imgName = multipartFile.getOriginalFilename();
        String type = imgName.substring(imgName.lastIndexOf("."));
        String fileName = Utils.getUUID()+type;
        String path = basePath + fileName   ;
        // 进行文件读取
        File dir = new File(basePath);
        if(!dir.exists()){
            dir.mkdir();
        }
        multipartFile.transferTo(new File(path));
        return fileName;
    }
}
