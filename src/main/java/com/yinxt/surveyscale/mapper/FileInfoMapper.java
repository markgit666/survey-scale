package com.yinxt.surveyscale.mapper;

import com.yinxt.surveyscale.entity.FileInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * 文档（文件/图片）mapper
 */
@Component
public interface FileInfoMapper {

    int insertFileInfo(FileInfo fileInfo);

    FileInfo selectFileInfo(@Param("fileNo") String fileNo);

}
