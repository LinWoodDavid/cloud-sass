package com.weimengchao.common.constant;

import org.apache.commons.lang3.StringUtils;

public enum PlatformType {

    unknown("unknown", "未知"),
    TIK_TOK("tiktok", "抖音小店"),
    ;

    /**
     * 平台
     */
    private final String platform;
    /**
     * 平台名称
     */
    private final String title;

    PlatformType(String platform, String title) {
        this.platform = platform;
        this.title = title;
    }

    public static PlatformType toPlatformType(String platform) {
        if (StringUtils.isBlank(platform)) {
            return unknown;
        }
        switch (platform) {
            case "tiktok":
                return TIK_TOK;
            default:
                return unknown;
        }
    }

}
