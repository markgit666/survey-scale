package com.yinxt.surveyscale.service;

import com.alibaba.fastjson.JSON;
import com.yinxt.surveyscale.mapper.FileInfoMapper;
import com.yinxt.surveyscale.pojo.FileInfo;
import com.yinxt.surveyscale.common.enums.FileTypeEnum;
import com.yinxt.surveyscale.common.qrcode.MyQrCode;
import com.yinxt.surveyscale.common.redis.RedisUtil;
import com.yinxt.surveyscale.common.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

@Slf4j
@Service
public class FileService {

    @Value("${image.path}")
    private String imageRootPath;
    @Autowired
    private FileInfoMapper fileInfoMapper;

    public void downloadQrCodeImage(HttpServletRequest request, HttpServletResponse response) {
        String addr = request.getLocalAddr();
        int port = request.getRemotePort();
        log.info("端口localPost:{},remotePort:{},serverPort:{}", request.getLocalPort(), request.getRemotePort(), request.getServerPort());
        String scaleId = request.getParameter("scaleId");
        String url = request.getParameter("url");
        StringBuilder urlContentBuilder = new StringBuilder();
        urlContentBuilder.append("http://").append("192.168.1.147").append(":").append("8080").append("/").append(url).append("?").append("scaleId=").append(scaleId);
        log.info("二维码内容：{}", urlContentBuilder.toString());
        String urlContent = urlContentBuilder.toString();
        try {
            MyQrCode.qrCodeImage(urlContent, response);
        } catch (Exception e) {
            log.error("获取二维码异常：{}", e);
        }
    }

    /**
     * 上传图片
     *
     * @param multipartFiles
     */
    public Result uploadImage(MultipartFile[] multipartFiles) {
        log.info("上传图片：{}", JSON.toJSONString(multipartFiles.toString()));
        String imageFullPath = "";
        String fileName;
        String fileNo = "";
        List<String> fileNoList = new ArrayList<>();
        try {
            for (MultipartFile multipartFile : multipartFiles) {

                String originName = multipartFile.getOriginalFilename();
                fileNo = RedisUtil.getSequenceId("IMG");
                fileName = fileNo + originName.substring(originName.lastIndexOf("."));
                imageFullPath = imageRootPath + File.separator + fileName;
                File file = new File(imageFullPath);
                if (!file.exists()) {
                    file.mkdirs();
                }
                multipartFile.transferTo(file);

                /**
                 * 保存文件信息
                 */
                FileInfo fileInfo = new FileInfo();
                fileInfo.setFileNo(fileNo);
                fileInfo.setFileName(fileName);
                fileInfo.setFileType(FileTypeEnum.IMAGE.getCode());
                saveFileInfo(fileInfo);
                fileNoList.add(fileNo);
            }
        } catch (IOException e) {
            log.error("文件上传异常：", e);
            return Result.error();
        }
        log.info("文件上传成功：{}", imageFullPath);
        return Result.success(fileNoList);
    }

    /**
     * 保存文件信息
     *
     * @param fileInfo
     */
    public void saveFileInfo(FileInfo fileInfo) {
        fileInfoMapper.insertFileInfo(fileInfo);
    }

    /**
     * 保存base64图片
     *
     * @param base64Image
     */
    public Result uploadBase64File(String base64Image) {

        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decodeByte = decoder.decode(base64Image);
        String fileNo = RedisUtil.getSequenceId("IMG");
        String fileName = fileNo + ".png";
        String fullName = imageRootPath + File.separator + fileName;
        File file = new File(fullName);
        FileOutputStream fileOutputStream = null;
        BufferedOutputStream bufferedOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            bufferedOutputStream.write(decodeByte);
            bufferedOutputStream.flush();

            /**
             * 保存文件信息
             */
            FileInfo fileInfo = new FileInfo();
            fileInfo.setFileNo(fileNo);
            fileInfo.setFileName(fileName);
            fileInfo.setFileType(FileTypeEnum.IMAGE.getCode());
            saveFileInfo(fileInfo);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedOutputStream.close();
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return Result.success(fileNo);
    }

    /**
     * 图片下载
     *
     * @param fileNo
     * @param response
     */
    public void downloadImage(String fileNo, HttpServletResponse response) {
        FileInfo fileInfo = queryFileInfo(fileNo);
        if (fileInfo != null) {
            String fileName = fileInfo.getFileName();
            InputStream inputStream = null;
            BufferedInputStream bufferedInputStream = null;
            BufferedOutputStream bufferedOutputStream = null;
            String filePath = imageRootPath + File.separator + fileName;
            File file = new File(filePath);
            try {
                inputStream = new FileInputStream(file);
                bufferedInputStream = new BufferedInputStream(inputStream);
                bufferedOutputStream = new BufferedOutputStream(response.getOutputStream());
                response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("utf-8"), "ISO8859-1"));
                byte[] bytes = new byte[inputStream.available()];
                bufferedInputStream.read(bytes);
                bufferedOutputStream.write(bytes);
                bufferedOutputStream.flush();
            } catch (FileNotFoundException e) {
                log.error("找不到图片，下载失败", e);
                throw new RuntimeException();
            } catch (IOException e) {
                log.error("文件下载失败：", e);
            } finally {
                try {
                    inputStream.close();
                    bufferedInputStream.close();
                    bufferedOutputStream.close();
                } catch (IOException e) {
                    log.error("资源关闭异常", e);
                }
            }
        }
    }

    /**
     * 查询文件信息
     *
     * @param fileNo
     * @return
     */
    public FileInfo queryFileInfo(String fileNo) {
        FileInfo fileInfo = fileInfoMapper.selectFileInfo(fileNo);
        return fileInfo;
    }


}
