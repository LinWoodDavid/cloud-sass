package com.weimengchao.common.tool;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

public abstract class DateHandler {

    /**
     * 日期分割
     *
     * @param beginDate beginDate
     * @param lastDate  lastDate
     * @param times     单位时间
     * @param unit      时间单位 支持 DAYS、HOURS、MINUTES
     * @throws Exception Exception
     */
    public void execute(LocalDateTime beginDate, LocalDateTime lastDate, long times, TimeUnit unit) throws Exception {
        long between = between(beginDate, lastDate, unit);
        if (between <= times) {
            process(beginDate, lastDate);
        } else {
            LocalDateTime endDate = plus(beginDate, times, unit);
            do {
                process(beginDate, endDate);
                beginDate = endDate;
                endDate = plus(endDate, times, unit);
            } while (endDate.isBefore(lastDate));
            process(beginDate, lastDate);
        }
    }

    private long between(LocalDateTime beginDate, LocalDateTime lastDate, TimeUnit unit) {
        if (TimeUnit.DAYS.equals(unit)) {
            return Duration.between(beginDate, lastDate).toDays();
        } else if (TimeUnit.HOURS.equals(unit)) {
            return Duration.between(beginDate, lastDate).toHours();
        } else if (TimeUnit.MINUTES.equals(unit)) {
            return Duration.between(beginDate, lastDate).toMinutes();
        } else {
            throw new RuntimeException("不支持的时间类型unit=" + unit);
        }
    }

    private LocalDateTime plus(LocalDateTime localDateTime, long times, TimeUnit unit) {
        if (TimeUnit.DAYS.equals(unit)) {
            return localDateTime.plusDays(times);
        } else if (TimeUnit.HOURS.equals(unit)) {
            return localDateTime.plusHours(times);
        } else if (TimeUnit.MINUTES.equals(unit)) {
            return localDateTime.plusMinutes(times);
        } else {
            throw new RuntimeException("不支持的时间类型unit=" + unit);
        }
    }

    public abstract void process(LocalDateTime beginDate, LocalDateTime endDate) throws Exception;

}
