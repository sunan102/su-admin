package com.sunan.admin.api.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class IdUtils {

    public static String prefix_buyer = "BR";

    /**
     * 获取uuid
     *
     * @return
     */
    public static String createUuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 生成相关的ID
     *
     * @return
     */
    public static String createId(String prefix) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMddHHmmssSSS");
        Date date = new Date();
        String dateStr = simpleDateFormat.format(date);
        Random random = new Random();
        return prefix + dateStr + random.nextInt(1000);
    }
}
