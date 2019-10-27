package com.yinxt.surveyscale.controller;

import com.yinxt.surveyscale.dto.ImageBase64ReqDTO;
import com.yinxt.surveyscale.service.FileService;
import com.yinxt.surveyscale.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * 图片/文件下载和上传controller
 */
@RequestMapping(value = "file")
@RestController
public class FileController {

    @Autowired
    private FileService fileService;

    @RequestMapping(value = "qrCode/image/download")
    public void downloadQrCodeImage(HttpServletRequest request, HttpServletResponse response) {
        fileService.downloadQrCodeImage(request, response);
    }

    @RequestMapping(value = "upload")
    public Result uploadFile(@RequestParam("file") MultipartFile[] multipartFiles) {
        return fileService.uploadImage(multipartFiles);
    }

    @RequestMapping(value = "download")
    public void downloadFile(@RequestParam("fileNo") String fileNo, HttpServletResponse response) {
        fileService.downloadImage(fileNo, response);
    }

    @RequestMapping(value = "base64/upload")
    public Result uploadBase64File(@RequestBody @Valid ImageBase64ReqDTO imageBase64ReqDTO) {
        return fileService.uploadBase64File(imageBase64ReqDTO.getBase64Image());
    }
}
