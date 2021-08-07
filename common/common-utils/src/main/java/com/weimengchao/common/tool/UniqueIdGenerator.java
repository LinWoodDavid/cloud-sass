package com.weimengchao.common.tool;

import java.time.LocalDateTime;

/**
 * 唯一id生成器
 */
public class UniqueIdGenerator {

    //2位服务编号。集群部署每个都不相同
    private String workerId;

    public UniqueIdGenerator(String workerId) {
        this.workerId = workerId;
    }
//    private ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();

//    20200806

    public static void main(String[] args) {
//        LocalDateTime now = LocalDateTime.now();
//        int year = now.getYear() - 2019;
//        System.out.println(year);
//        12+6+2
//

        String a = "1165088195185051888";
        System.out.println(a.length());
    }

    public synchronized Long nextId(String type) {
        //12位日期+6位递增+2位机器编号
        String date = TimeTool.localDateTime2Format(LocalDateTime.now(), "yyMMddHHmmss");
        StringBuilder builder = new StringBuilder();
        builder.append(date);
        return 1L;
    }

}
