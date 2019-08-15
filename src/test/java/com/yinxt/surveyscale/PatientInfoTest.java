package com.yinxt.surveyscale;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.UUID;

@Slf4j
public class PatientInfoTest {

    @Test
    public void testUUID(){
        UUID uuid = UUID.randomUUID();
        String uuidStr = uuid.toString().substring(0, 8);
        long ss = uuid.getMostSignificantBits();
        long dd = uuid.getLeastSignificantBits();
        int variant = uuid.variant();
        int verison =uuid.version();
        log.info("uuid={}, ss={}, dd={}, variant={}, version={}", uuidStr, ss, dd, variant, verison);

    }
}
