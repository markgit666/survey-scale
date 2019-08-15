package com.yinxt.surveyscale.util.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.encoder.QRCode;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class MyQrCode {

    public static void main(String[] args) {

        String content = "http://192.168.43.205:8080/Home";
        qrCode(content);
    }

    public static void qrCode(String content) {

        int width = 300;
        int heigh = 300;
        String imageFormart = "png";

        Map hintMap = new HashMap<>();
        hintMap.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hintMap.put(EncodeHintType.MARGIN, 2);

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, width, heigh, hintMap);
            Path file = Paths.get("/Users/yinxiaotian/文件/erweima");
            MatrixToImageWriter.writeToPath(bitMatrix, imageFormart, file);
        } catch (Exception e) {
            log.error("二维码写入文件异常：", e);
        }
    }
}
