package com.weimengchao.common.tool;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class TimeTool {

    //Date 转 LocalDateTime
    public static LocalDateTime date2LocalDateTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).toLocalDateTime();
    }

    /**
     * Date 转 String
     *
     * @param dateTimeFormatter DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
     */
    public static String date2String(Date date, DateTimeFormatter dateTimeFormatter) {
        LocalDateTime localDateTime = date2LocalDateTime(date);
        return dateTimeFormatter.format(localDateTime);
    }

    /**
     * localDateTime 转 String
     *
     * @param localDateTime
     * @return
     */
    public static String localDateTime2Format(LocalDateTime localDateTime) {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(localDateTime);
    }

    public static String localDateTime2Format(LocalDateTime localDateTime, String format) {
        return DateTimeFormatter.ofPattern(format).format(localDateTime);
    }

    public static String localDateTime2Format(LocalDateTime localDateTime, DateTimeFormatter dateTimeFormatter) {
        return dateTimeFormatter.format(localDateTime);
    }

    public static LocalDateTime toLocalDateTime(String format, DateTimeFormatter dateTimeFormatter) {
        return LocalDateTime.parse(format, dateTimeFormatter);
    }

    public static LocalDateTime toLocalDateTime(String format) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(format, dateTimeFormatter);
    }

    public static LocalDateTime toLocalDateTime(long epochSecond) {
        return LocalDateTime.ofEpochSecond(epochSecond, 0, ZoneOffset.ofHours(8));
    }

    /**
     * string转Timestamp
     */
    public static Timestamp string2Timestamp(String format, DateTimeFormatter dateTimeFormatter) {
        LocalDateTime localDateTime = LocalDateTime.parse(format, dateTimeFormatter);
        return Timestamp.valueOf(localDateTime);
    }

    /**
     * LocalDateTime转换为Date
     *
     * @param localDateTime localDateTime
     */
    public static Date localDateTime2Date(LocalDateTime localDateTime) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDateTime.atZone(zoneId);
        return Date.from(zdt.toInstant());
    }

}
