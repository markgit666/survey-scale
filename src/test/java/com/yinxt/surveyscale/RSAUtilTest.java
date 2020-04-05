package com.yinxt.surveyscale;

import com.alibaba.fastjson.JSON;
import com.yinxt.surveyscale.common.exeption.LogicException;
import com.yinxt.surveyscale.common.util.RSAUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * @author yinxt
 * @version 1.0
 * @date 2019-11-08 21:40
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class RSAUtilTest {
    @Value("${rsa.key.private}")
    private String privateKey;
    @Value("${rsa.key.public}")
    private String publicKey;

    @Test
    public void test() {
        String content = "t9NZLjUz/+mbp4vBs00vRE88xHiEljXwkDBDfD8jR+KK8jpYGRnDiZ6+jcoMBb74nquxn22jDnQtx9W5eZHqRuvKA9H97GZbT6KKt1xKsC3WXrPSlTIvdti4XCJh8oItuL3J6NUoljwF/e7sP/uCPEcQocH6a//l66LOXHZinNM=";
        String urlContent = "t9NZLjUz%2F%20mbp4vBs00vRE88xHiEljXwkDBDfD8jR%20KK8jpYGRnDiZ6%20jcoMBb74nquxn22jDnQtx9W5eZHqRuvKA9H97GZbT6KKt1xKsC3WXrPSlTIvdti4XCJh8oItuL3J6NUoljwF%2Fe7sP%2FuCPEcQocH6a%2F%2Fl66LOXHZinNM%3D";
        try {
            String encryptContent = URLDecoder.decode(urlContent, "UTF-8");
            String target = encryptContent.replace(" ", "+");
            String decryptContent = RSAUtil.decrypt(target, privateKey);
            log.info("明文：{}", JSON.toJSONString(decryptContent));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void decryptTest() {
        String reportId = "ngHYhwUQ6/J8XGEIkI8N/zypWzkugs1JbpX6Zb1D3fypZlrsblcrZQrlX3ShFqi6XsB1NeXFW21NLAMjJWOXkNuftRfzIV/KP3N4xVTRhoXGKu5HFi9yMcPkDsykoHlwc9PtGIUrnI4EwnHYLexW43cDvQXQBPVMmENge5D66Io=";
        try {
            String decryptReportId = RSAUtil.decrypt(reportId, privateKey);
            log.info("明文：{}", decryptReportId);
        } catch (Exception e) {
            log.error("报告表id解密失败：", e);
            throw new LogicException("报告表不存在");
        }

    }

    @Test
    public void encryptTest() {
        try {
            String reportId = "rp_001";
            String encryptReportId = RSAUtil.encrypt(reportId, publicKey);
            log.info("密文：{}", encryptReportId);
        } catch (Exception e) {
            log.info("加密异常：{}", e);
        }

    }
}
