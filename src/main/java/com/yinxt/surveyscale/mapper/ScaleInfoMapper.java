package com.yinxt.surveyscale.mapper;

import com.yinxt.surveyscale.entity.ScaleInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 量表mapper
 */
@Component
public interface ScaleInfoMapper {

    int insertScaleInfo(ScaleInfo scaleInfo);

    List<ScaleInfo> selectScaleInfoList(@Param("scaleName") String scaleName, @Param("doctorId") String doctorId);

    ScaleInfo selectScaleInfo(@Param("scaleId") String scaleId);

    List<ScaleInfo> selectScaleInfoByIdList(@Param("scaleIdList") List<String> scaleIdList);

    int updateScaleInfo(ScaleInfo scaleInfo);

}
