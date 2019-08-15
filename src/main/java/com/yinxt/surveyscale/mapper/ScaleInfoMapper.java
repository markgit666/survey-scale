package com.yinxt.surveyscale.mapper;

import com.yinxt.surveyscale.pojo.ScaleInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 量表mapper
 */
public interface ScaleInfoMapper {

    int insertScaleInfo(ScaleInfo scaleInfo);

    List<ScaleInfo> selectScaleInfoList(@Param("scaleName") String scaleName);

    ScaleInfo selectScaleInfo(@Param("scaleId") String scaleId);

    int updateScaleInfo(ScaleInfo scaleInfo);

}
