package com.yinxt.surveyscale;

import com.alibaba.fastjson.JSON;
import com.yinxt.surveyscale.common.constant.Constant;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author yinxt
 * @version 1.0
 * @date 2020-04-04 14:24
 */
@SpringBootTest
@Slf4j
public class SpitTest {

    @Test
    public void testSplit() {
        String item = "dafsdf&-&2";
        String[] optionArray = item.split(Constant.SCORE_SPLIT);
        log.info("分隔后的数组={}", optionArray);
        log.info("分隔后的数组={}", JSON.toJSONString(optionArray));
    }
}
