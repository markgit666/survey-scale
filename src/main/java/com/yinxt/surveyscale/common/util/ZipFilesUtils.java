package com.yinxt.surveyscale.common.util;

import lombok.extern.slf4j.Slf4j;


import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * @author yinxt
 * @version 1.0
 * @date 2020-05-03 17:25
 */
@Slf4j
public class ZipFilesUtils {

    private static final byte[] BUF = new byte[1024];

    private ZipFilesUtils() {
    }

    /**
     * 压缩
     *
     * @param files       多文件
     * @param zipFilePath 目标压缩文件路径
     * @throws IOException
     */
    public static void zip(List<File> files, String zipFilePath) throws IOException {
        log.info("启用ZIP压缩工具 >>>>>>>>>> ");
        if (files == null || files.size() == 0) return;
        if (StringUtils.isBlank(zipFilePath)) return;
        File zipFile = createFile(zipFilePath);
        FileOutputStream fileOutputStream = null;
        ZipOutputStream zipOutputStream = null;
        FileInputStream inputStream = null;
        try {
            fileOutputStream = new FileOutputStream(zipFile);
            zipOutputStream = new ZipOutputStream(fileOutputStream);
            for (File file : files) {
                log.info("正在打包文件 -> " + file.getAbsolutePath());
                if (file.exists()) {
                    zipOutputStream.putNextEntry(new ZipEntry(file.getName()));
                    inputStream = new FileInputStream(file);
                    int len;
                    while ((len = inputStream.read(BUF)) > 0) {
                        zipOutputStream.write(BUF, 0, len);
                    }
                    zipOutputStream.closeEntry();
                }
            }
            log.info("压缩完成 <<<<<<<<<< " + zipFile.getAbsolutePath());
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (zipOutputStream != null) {
                zipOutputStream.close();
            }
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
        }
    }

    /**
     * 压缩（将文件夹打包）
     *
     * @param fileDir     文件夹目录路径
     * @param zipFilePath 目标压缩文件路径
     * @throws IOException
     */
    public static void zipByFolder(String fileDir, String zipFilePath) throws IOException {
        File folder = new File(fileDir);
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();
            List<File> filesList = Arrays.asList(files);
            zip(filesList, zipFilePath);
        }
    }

    /**
     * 解压
     *
     * @param zipFile 压缩文件
     * @param descDir 目标文件路径
     * @return
     * @throws IOException
     */
    public static List<File> unzip(File zipFile, String descDir) throws IOException {
        List<File> files = new ArrayList<>();
        if (StringUtils.isNotBlank(descDir)) {
            log.info("启用ZIP解压工具 >>>>>>>>>> ");
            if (zipFile.exists() && zipFile.getName().endsWith(".zip")) {
                OutputStream outputStream = null;
                InputStream inputStream = null;
                try {
                    ZipFile zf = new ZipFile(zipFile);
                    Enumeration entries = zf.entries();
                    while (entries.hasMoreElements()) {
                        ZipEntry zipEntry = (ZipEntry) entries.nextElement();
                        String zipEntryName = zipEntry.getName();
                        log.info("正在解压文件 -> " + zipEntryName);
                        inputStream = zf.getInputStream(zipEntry);
                        String descFilePath = descDir + File.separator + zipEntryName;
                        File descFile = createFile(descFilePath);
                        files.add(descFile);
                        outputStream = new FileOutputStream(descFilePath);
                        int len;
                        while ((len = inputStream.read(BUF)) > 0) {
                            outputStream.write(BUF, 0, len);
                        }
                    }
                    log.info("解压完成 <<<<<<<<<< " + descDir);
                } finally {
                    if (null != inputStream) {
                        inputStream.close();
                    }
                    if (null != outputStream) {
                        outputStream.close();
                    }
                }
            }
        }
        return files;
    }

    private static File createFile(String filePath) throws IOException {
        File file = new File(filePath);
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    public static void main(String[] args) throws Exception {
        String srcPath = "/Users/yinxiaotian/myFile/excel/scalePaper/original/2020-05-03-001027/";
        String zipFilePath = "/Users/yinxiaotian/myFile/excel/scalePaper/zip/2020-05-03-001030/scalePaper.zip";
        zipByFolder(srcPath, zipFilePath);
    }

}
