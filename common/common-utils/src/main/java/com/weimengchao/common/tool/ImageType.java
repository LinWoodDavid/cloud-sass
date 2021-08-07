package com.weimengchao.common.tool;


import org.apache.commons.lang3.StringUtils;

/**
 * 文件类型与对应的文件魔数枚举类
 */
public enum ImageType {

    /**
     * JPEG  (jpg)
     */
    JPEG("JPG", "FFD8FF"),

    /**
     * PNG
     */
    PNG("PNG", "89504E47"),

    /**
     * GIF
     */
    GIF("GIF", "47494638"),

    /**
     * TIFF (tif)
     */
    TIFF("TIF", "49492A00"),

    /**
     * Windows bitmap (bmp)
     */
    BMP("BMP", "424D"),

    BMP_16("BMP", "424D228C010000000000"), //16色位图(bmp)

    BMP_24("BMP", "424D8240090000000000"), //24位位图(bmp)

    BMP_256("BMP", "424D8E1B030000000000"), //256色位图(bmp)

    NOT_EXITS_ENUM("", "");

    //文件类型对应的名称
    private final String fileTypeName;

    //文件类型对应的魔数
    private final String magicNumberCode;

    ImageType(String fileTypeName, String magicNumberCode) {
        this.fileTypeName = fileTypeName;
        this.magicNumberCode = magicNumberCode;
    }

    public String getFileTypeName() {
        return fileTypeName;
    }

    public String getMagicNumberCode() {
        return magicNumberCode;
    }


    /**
     * 根据文件类型获取文件类型魔数编码
     * 默认返回标准件
     *
     * @param magicNumberCode - 文件类型魔数编码
     * @return
     */
    public static ImageType getByMagicNumberCode(String magicNumberCode) {
        if (StringUtils.isNotBlank(magicNumberCode)) {
            String code = magicNumberCode.toUpperCase();
            for (ImageType type : values()) {
                if (code.startsWith(type.getMagicNumberCode())) {
                    return type;
                }
            }
        }
        return ImageType.NOT_EXITS_ENUM;
    }

    /**
     * 根据文件类型后缀名获取枚举
     *
     * @param fileTypeName - 文件类型后缀名
     * @return
     */
    public static ImageType getByFileTypeName(String fileTypeName) {
        if (StringUtils.isNotBlank(fileTypeName)) {
            String name = fileTypeName.toUpperCase();
            for (ImageType type : values()) {
                if (type.getFileTypeName().equals(name)) {
                    return type;
                }
            }
        }
        return ImageType.NOT_EXITS_ENUM;
    }

}
