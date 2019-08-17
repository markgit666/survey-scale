package com.yinxt.surveyscale.service;

import com.yinxt.surveyscale.util.qrcode.MyQrCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Service
public class FileService {

    public void downloadQrCodeImage(HttpServletRequest request, HttpServletResponse response) {
        String addr = request.getLocalAddr();
        int port = request.getRemotePort();
        log.info("端口localPost:{},remotePort:{},serverPort:{}", request.getLocalPort(), request.getRemotePort(), request.getServerPort());
        String scaleId = request.getParameter("scaleId");
        String url = request.getParameter("url");
        StringBuilder urlContentBuilder = new StringBuilder();
        urlContentBuilder.append("http://").append(addr).append(":").append("8080").append("/").append(url).append("?").append("scaleId=").append(scaleId);
        log.info("二维码内容：{}", urlContentBuilder.toString());
        String urlContent = urlContentBuilder.toString();
        try {
            MyQrCode.qrCodeImage(urlContent, response);
        } catch (Exception e) {
            log.error("获取二维码异常：{}", e);
        }
    }
}
