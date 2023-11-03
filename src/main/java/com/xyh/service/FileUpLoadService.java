package com.xyh.service;


import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface FileUpLoadService {
    String uploadFile(MultipartFile multipartFile) throws IOException;
}
