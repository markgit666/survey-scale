package com.yinxt.surveyscale;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SurveyScaleApplicationTests.class)
public class FileTest {

    @Test
    public void decryptFile() {
        File file = new File("/Users/yinxiaotian/文件/第五章(1).pdf");
        InputStream inputStream = null;
        BufferedInputStream bufferedInputStream = null;
        BufferedOutputStream bufferedOutputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = new FileInputStream(file);
            bufferedInputStream = new BufferedInputStream(inputStream);
            File targetFile = new File("/Users/yinxiaotian/文件/第五章.doc");
            outputStream = new FileOutputStream(targetFile);
            bufferedOutputStream = new BufferedOutputStream(outputStream);
            byte[] bytes = new byte[bufferedInputStream.available()];
            int length = bufferedInputStream.read(bytes);
            while (length != -1) {
                bufferedOutputStream.write(bytes, 0, length);
                length = bufferedInputStream.read(bytes);
            }
            bufferedOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
                outputStream.close();
                bufferedInputStream.close();
                bufferedOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
