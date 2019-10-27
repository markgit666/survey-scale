package com.yinxt.surveyscale.mapper;

import com.yinxt.surveyscale.entity.ScaleInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 量表mapper
 */
public interface ScaleInfoMapper {

    int insertScaleInfo(ScaleInfo scaleInfo);

    List<ScaleInfo> selectScaleInfoList(@Param("scaleName") String scaleName, @Param("doctorId") String doctorId);

    ScaleInfo selectScaleInfo(@Param("scaleId") String scaleId);

    int updateScaleInfo(ScaleInfo scaleInfo);

}
