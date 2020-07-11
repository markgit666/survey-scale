package com.yinxt.surveyscale;

import org.apache.coyote.OutputBuffer;

import java.io.*;

/**
 * @author yinxt
 * @version 1.0
 * @date 2020-06-16 22:48
 */
public class FileTest {

    public static void main(String[] args) throws Exception {
        String path = "/Users/yinxiaotian/文件/target/timg.jpeg";
        InputStream inputStream = new FileInputStream(path);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

        String target = "/Users/yinxiaotian/文件/target2";
        File file = new File(target);
        if (!file.exists()){
            file.mkdir();
        }
        target += File.separator + "a.jpg";
        OutputStream outputStream = new FileOutputStream(target);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
        byte[] bytes = new byte[bufferedInputStream.available()];
        while (-1 != bufferedInputStream.read(bytes)) {
            bufferedOutputStream.write(bytes);
        }
        System.out.println("写入完毕");
    }
}
