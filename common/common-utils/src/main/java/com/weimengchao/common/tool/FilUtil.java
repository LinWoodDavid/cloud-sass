package com.weimengchao.common.tool;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.URL;

public class FilUtil {

    /**
     * 图片转JPG
     *
     * @param url
     * @return
     */
    public static byte[] ImageToJPG(String url) throws IOException {
        String magicNumberCode = getMagicNumberCodeByUrl(url);
        ImageType imageType = ImageType.getByMagicNumberCode(magicNumberCode);
        if (ImageType.JPEG.equals(imageType)) {
            try (InputStream inputStream = new URL(url).openStream()) {
                return toByteArray(inputStream);
            }
        } else if (ImageType.NOT_EXITS_ENUM.equals(imageType)) {
            throw new RuntimeException("未识别图片类型url:" + url);
        } else {
            BufferedImage bufferedImage = ImageIO.read(new URL(url));
            BufferedImage newBufferedImage = new BufferedImage(bufferedImage.getWidth(),
                    bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
            newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, null, null);
            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                ImageIO.write(newBufferedImage, ImageType.JPEG.getFileTypeName(), outputStream);
                return outputStream.toByteArray();
            }
        }
    }


    /**
     * InputStream转化为byte[]数组
     *
     * @param input
     * @return
     * @throws IOException
     */
    public static byte[] toByteArray(InputStream input) throws IOException {
        try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int n = 0;
            while (-1 != (n = input.read(buffer))) {
                output.write(buffer, 0, n);
            }
            return output.toByteArray();
        }
    }

    /**
     * 根据 url 网址获取文件魔数
     *
     * @param url url
     * @return 文件头
     */
    public static String getMagicNumberCodeByUrl(String url) throws IOException {
        //根据URL网址获取输入流
        try (InputStream inputStream = new URL(url).openStream()) {
            return getMagicNumberCode(inputStream);
        }
    }

    public static String getMagicNumberCodeByFile(String filePath) throws IOException {
        try (FileInputStream inputStream = new FileInputStream(new File(filePath))) {
            return getMagicNumberCode(inputStream);
        }
    }

    public static String getMagicNumberCode(InputStream inputStream) throws IOException {
        // 获取文件头字节数组
        byte[] fileHeaderBytes = getFileHeaderBytes(inputStream);
        //将字节数组转换成16进制字符串
        return new BigInteger(1, fileHeaderBytes).toString(16);
    }

    /**
     * 读取文件头字节数组
     *
     * @param inputStream 输入流
     * @return
     * @throws IOException
     */
    private static byte[] getFileHeaderBytes(InputStream inputStream) throws IOException {
        byte[] bytes = new byte[28];
        inputStream.read(bytes, 0, 28);
        return bytes;
    }

}
