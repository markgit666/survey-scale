package com.yinxt.surveyscale.controller;

import com.yinxt.surveyscale.dto.ImageBase64ReqDTO;
import com.yinxt.surveyscale.service.FileService;
import com.yinxt.surveyscale.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * 图片/文件下载和上传controller
 */
@Api(tags = {"file:"})
@RequestMapping(value = "file")
@RestController
public class FileController {

    @Autowired
    private FileService fileService;

    @ApiOperation(value = "下载答题二维码图片", notes = "下载答题二维码图片", httpMethod = "POST")
    @RequestMapping(value = "report/qrCode/image/download")
    public void downloadQrCodeImage(HttpServletRequest request, HttpServletResponse response) {
        fileService.downloadReportQrCodeImage(request, response);
    }

    @ApiOperation(value = "上传文件", notes = "上传文件", httpMethod = "POST")
    @RequestMapping(value = "upload")
    public Result uploadFile(@RequestParam("file") MultipartFile[] multipartFiles) {
        return fileService.uploadImage(multipartFiles);
    }

    @ApiOperation(value = "下载文件", notes = "根据文件编号，下载题目图片", httpMethod = "POST")
    @RequestMapping(value = "download")
    public void downloadFile(@RequestParam("fileNo") String fileNo, HttpServletResponse response) {
        fileService.downloadImage(fileNo, response);
    }

    @ApiOperation(value = "上传Base64文件", notes = "上传Base64文件", httpMethod = "POST")
    @RequestMapping(value = "base64/upload")
    public Result uploadBase64File(@RequestBody @Valid ImageBase64ReqDTO imageBase64ReqDTO) {
        return fileService.uploadBase64Image(imageBase64ReqDTO.getBase64Image());
    }
}
