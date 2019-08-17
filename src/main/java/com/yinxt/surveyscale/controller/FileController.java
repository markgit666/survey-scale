package com.yinxt.surveyscale.controller;

import com.yinxt.surveyscale.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 图片/文件下载和上传controller
 */
@CrossOrigin
@RequestMapping(value = "file")
@RestController
public class FileController {

    @Autowired
    private FileService fileService;

    @RequestMapping(value = "qrCode/image/download")
    public void downloadQrCodeImage(HttpServletRequest request, HttpServletResponse response) {
        fileService.downloadQrCodeImage(request, response);
    }
}
