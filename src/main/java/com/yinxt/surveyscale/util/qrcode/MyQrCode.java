package com.yinxt.surveyscale.util.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class MyQrCode {
    private final static int WIDTH = 300;
    private final static int HEIGH = 300;
    private final static String IMAGE_FORMART = "png";

    public static void main(String[] args) {

        String content = "http://192.168.43.205:8080/Home";
        qrCode(content);
    }

    public static void qrCode(String content) {
        Map hintMap = new HashMap<>();
        hintMap.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hintMap.put(EncodeHintType.MARGIN, 2);

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, WIDTH, HEIGH, hintMap);
            Path file = Paths.get("/Users/yinxiaotian/文件/erweima");
            MatrixToImageWriter.writeToPath(bitMatrix, IMAGE_FORMART, file);
        } catch (Exception e) {
            log.error("二维码写入文件异常：", e);
        }
    }


    /**
     * 生成二维码并返回
     *
     * @param content
     * @param response
     * @throws Exception
     */
    public static void qrCodeImage(String content, HttpServletResponse response) throws Exception {
        Map hintMap = new HashMap<>();
        hintMap.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hintMap.put(EncodeHintType.MARGIN, 1);
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, WIDTH, HEIGH, hintMap);
        BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
        ImageIO.write(bufferedImage, IMAGE_FORMART, response.getOutputStream());
    }
}
