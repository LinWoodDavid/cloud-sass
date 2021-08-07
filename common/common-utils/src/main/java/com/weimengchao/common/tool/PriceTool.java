package com.weimengchao.common.tool;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PriceTool {

    /**
     * 元转分，确保price保留两位有效数字
     *
     * @param price 元
     * @return 分
     */
    public static BigDecimal yuan2Fen(Double price) {
        if (price == null) {
            return BigDecimal.ZERO;
        }
        return new BigDecimal(price).multiply(new BigDecimal(100))
                .setScale(0, RoundingMode.HALF_UP);
    }

    /**
     * 元转分，确保price保留两位有效数字
     *
     * @param price 元
     * @return 分
     */
    public static BigDecimal yuan2Fen(String price) {
        if (StringUtils.isBlank(price)) {
            return BigDecimal.ZERO;
        }
        return new BigDecimal(price).multiply(new BigDecimal(100))
                .setScale(0, RoundingMode.HALF_UP);
    }

    /**
     * 分转元
     *
     * @param price 分
     * @return 元
     */
    public static BigDecimal fen2Yuan(Long price) {
        if (price == null) {
            return BigDecimal.ZERO;
        }
        BigDecimal feeNo = new BigDecimal(price);
        return feeNo.divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);//分转元
    }

    /**
     * 分转元
     *
     * @param price 分
     * @return 元
     */
    public static BigDecimal fen2Yuan(String price) {
        if (StringUtils.isBlank(price)) {
            return BigDecimal.ZERO;
        }
        BigDecimal feeNo = new BigDecimal(price);
        return feeNo.divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);//分转元
    }

}
