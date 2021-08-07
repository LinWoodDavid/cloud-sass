package com.weimengchao.common.constant;

import lombok.Getter;

@Getter
public enum TableType {

    EVALUATE(1, "evaluate"),//评价表
    ORDER_INFO(2, "order_info"),//订单表
    ORDER_ITEM(3, "order_item"),//订单项表
    CONSIGNEE(4, "consignee"),//收件人表
    SHOP_INFO(5, "shop_info"),//店铺表
    EVALUATE_HISTORY(6, "evaluate_history"),//评价历史表
    EXPORT_TASK(7, "export_task"),//导出任务
    ;

    /**
     * 编号
     */
    private final int id;

    /**
     * 表名
     */
    private final String tableName;

    TableType(int id, String tableName) {
        this.id = id;
        this.tableName = tableName;
    }
}
