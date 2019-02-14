package com.sunan.admin.api.utils;


import com.sunan.admin.api.common.SelectItem;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2018/4/13.
 */
public class CommonUtils {


    /**
     * 获取总元素个数的sql
     *
     * @param sql
     * @return
     */
    public static String getCountSql(StringBuilder sql) {
        StringBuilder sb_count = new StringBuilder();
        sb_count.append("SELECT count(0) from (  ");
        sb_count.append(sql);
        sb_count.append(") temp_count_table");

        return sb_count.toString();
    }

    /**
     * 获取带分页列表的sql
     *
     * @param nowPage 从1开始的页码
     * @return
     */
    public static String getPageSql(StringBuilder sql, Integer nowPage, Integer size) {
        sql.append("LIMIT " + ((nowPage - 1) * size) + "," + size);
        return sql.toString();
    }


    /**
     * 日期格式化yyyy-MM-dd
     *
     * @param date
     */
    public static String getDateStr1(Date date) {
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(date);
        } else {
            return "";
        }
    }

    /**
     * 日期格式化yyyy-MM-dd HH:mm:ss
     *
     * @param date
     */
    public static String getDateStr2(Date date) {
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.format(date);
        } else {
            return "";
        }
    }

    /**
     * 日期格式化yyyy-MM-dd HH:mm
     *
     * @param date
     */
    public static String getDateStr3(Date date) {
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            return sdf.format(date);
        } else {
            return "";
        }
    }

    /**
     * 日期格式化yyyy-MM-dd
     *
     * @param date
     */
    public static String getDateStr4(Date date) {
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            return sdf.format(date);
        } else {
            return "";
        }
    }

    /**
     * 日期字符串转日期类型yyyy-MM-dd HH:mm
     */
    public static Date getDate3(String dateStr) {
        if (dateStr != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            try {
                return sdf.parse(dateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 生成6位短信验证码
     *
     * @return
     */
    public static String createSmsVerifyCode() {
        String verifyCode = String
                .valueOf(new Random().nextInt(899999) + 100000);//生成短信验证码
        return verifyCode;
//        return "123456";
    }

    /**
     * 获取带有时间戳的文件名
     *
     * @param filename
     * @return
     */
    public static String getFileNameWithTimestamp(String filename) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Date now = new Date();
        return filename + sdf.format(now);
    }

    /**
     * 获取带星号的电话
     *
     * @param tel
     * @return
     */
    public static String getTelWithStar(String tel) {
        if (!StringUtils.isEmpty(tel)) {
            return tel.replaceAll("(\\d{4})?\\d{4}(.*)", "$1****$2");
        }
        return tel;
    }

    /**
     * 获取带星号的银行账号
     *
     * @param tel
     * @return
     */
    public static String getBankNumWithStar(String tel) {
        if (!StringUtils.isEmpty(tel)) {
            return tel.replaceAll("(\\d{7})?\\d{7}(.*)", "$1*******$2");
        }
        return tel;
    }

    /**
     * 插入请选择的选项
     *
     * @return
     */
    public static List<SelectItem> insertPlease(List<SelectItem> list) {
        list.add(0, new SelectItem("请选择", "", true));
        return list;
    }

    /**
     * 计算2个小数的乘法，结果四舍五入向上取整
     *
     * @param a
     * @param b
     * @return
     */
    public static BigDecimal multiplyPrice(BigDecimal a, BigDecimal b) {
        return a.multiply(b).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 文件大小格式化
     *
     * @param length
     * @return
     */
    public static String formatFileSize(long length) {
        String result = null;
        int sub_string = 0;
        if (length >= 1073741824) {
            sub_string = String.valueOf((float) length / 1073741824).indexOf(
                    ".");
            result = ((float) length / 1073741824 + "000").substring(0,
                    sub_string + 3) + "GB";
        } else if (length >= 1048576) {
            sub_string = String.valueOf((float) length / 1048576).indexOf(".");
            result = ((float) length / 1048576 + "000").substring(0,
                    sub_string + 3) + "MB";
        } else if (length >= 1024) {
            sub_string = String.valueOf((float) length / 1024).indexOf(".");
            result = ((float) length / 1024 + "000").substring(0,
                    sub_string + 3) + "KB";
        } else if (length < 1024)
            result = Long.toString(length) + "B";

        return result;
    }
}
