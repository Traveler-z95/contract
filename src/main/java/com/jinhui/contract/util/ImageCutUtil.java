package com.jinhui.contract.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * 图像切割
 */
public class ImageCutUtil {

    private static Logger logger = LoggerFactory.getLogger(ImageCutUtil.class);
    /**
     * 图像切割（改）     *
     *
     * @param srcImageFile 源图像地址
     * @param dirImageFile 新图像地址
     * @param x            目标切片起点x坐标
     * @param y            目标切片起点y坐标
     * @param destWidth    目标切片宽度
     * @param destHeight   目标切片高度
     */
    public static void abscut(String srcImageFile, String dirImageFile, int x, int y, int destWidth,
                              int destHeight) {
        try {
            Image img;
            ImageFilter cropFilter;
            // 读取源图像
            BufferedImage bi = ImageIO.read(new File(srcImageFile));
            int srcWidth = bi.getWidth(); // 源图宽度
            int srcHeight = bi.getHeight(); // 源图高度
            if (srcWidth >= destWidth && srcHeight >= destHeight) {
                Image image = bi.getScaledInstance(srcWidth, srcHeight,
                        Image.SCALE_DEFAULT);
                // 改进的想法:是否可用多线程加快切割速度
                // 四个参数分别为图像起点坐标和宽高
                // 即: CropImageFilter(int x,int y,int width,int height)
                cropFilter = new CropImageFilter(x, y, destWidth, destHeight);
                img = Toolkit.getDefaultToolkit().createImage(
                        new FilteredImageSource(image.getSource(), cropFilter));
                BufferedImage tag = new BufferedImage(destWidth, destHeight,
                        BufferedImage.TYPE_INT_RGB);
                Graphics g = tag.getGraphics();
                g.drawImage(img, 0, 0, null); // 绘制缩小后的图
                g.dispose();
                // 输出为文件
                ImageIO.write(tag, "JPEG", new File(dirImageFile));
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public static void cut(InputStream is, File dirImageFile, int x, int y, int destWidth,
                           int destHeight) throws IOException {
        cutAndSave(ImageIO.read(is), dirImageFile, x, y, destWidth, destHeight);
    }

    /**
     * 裁剪并保存到指定文件
     * @param bi 文件
     * @param dirImageFile 目标文件
     * @param x x坐标
     * @param y y坐标
     * @param destWidth 宽度
     * @param destHeight 高度
     */
    public static void cutAndSave(BufferedImage bi, File dirImageFile, int x, int y, int destWidth,
                           int destHeight) {
        try {
            BufferedImage tag = cutImage(bi, x, y, destWidth, destHeight);
            ImageIO.write(tag, "JPEG", dirImageFile);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }


    public static byte[] cutImageForByte(BufferedImage image , int x, int y, int destWidth, int destHeight){
        BufferedImage bufferedImage = cutImage(image, x, y, destWidth, destHeight);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImage,"JPEG",outputStream);
            return outputStream.toByteArray();
        } catch (IOException e) {
            logger.error("{}.cutImageForByte方法发生异常,异常信息为:{}",ImageCutUtil.class.getCanonicalName(),e.getMessage());
        }

        return null;
    }


    /**
     * 获取裁剪后的图片
     * @param bi 裁剪前的图片
     * @param x x坐标
     * @param y y坐标
     * @param destWidth 宽度
     * @param destHeight 高度
     * @return
     */
    public static BufferedImage cutImage(BufferedImage bi , int x, int y, int destWidth, int destHeight) {
        Image img;
        ImageFilter cropFilter;
        // 读取源图像
        int srcWidth = bi.getWidth(); // 源图宽度
        int srcHeight = bi.getHeight(); // 源图高度
        if (srcWidth >= destWidth && srcHeight >= destHeight) {
            Image image = bi.getScaledInstance(srcWidth, srcHeight,
                    Image.SCALE_DEFAULT);
            // 改进的想法:是否可用多线程加快切割速度
            // 四个参数分别为图像起点坐标和宽高
            // 即: CropImageFilter(int x,int y,int width,int height)
            cropFilter = new CropImageFilter(x, y, destWidth, destHeight);
            img = Toolkit.getDefaultToolkit().createImage(
                    new FilteredImageSource(image.getSource(), cropFilter));
            BufferedImage tag = new BufferedImage(destWidth, destHeight,
                    BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            g.drawImage(img, 0, 0, null); // 绘制缩小后的图
            g.dispose();
            return tag;
        }
        return bi;
    }


    /**
     * 缩放图像
     *
     * @param srcImageFile 源图像文件地址
     * @param result       缩放后的图像地址
     * @param scale        缩放比例
     * @param flag         缩放选择:true 放大; false 缩小;
     */
    public static void scale(String srcImageFile, String result, int scale, boolean flag) {
        try {
            BufferedImage src = ImageIO.read(new File(srcImageFile)); // 读入文件
            int width = src.getWidth(); // 得到源图宽
            int height = src.getHeight(); // 得到源图长
            if (flag) {
                // 放大
                width = width * scale;
                height = height * scale;
            } else {
                // 缩小
                width = width / scale;
                height = height / scale;
            }
            Image image = src.getScaledInstance(width, height, Image.SCALE_DEFAULT);
            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            g.drawImage(image, 0, 0, null); // 绘制缩小后的图
            g.dispose();
            ImageIO.write(tag, "JPEG", new File(result));// 输出到文件流
        } catch (IOException e) {
           logger.error(e.getMessage());
        }
    }


    /**
     * 重新生成按指定宽度和高度的图像
     *
     * @param srcImageFile 源图像文件地址
     * @param result       新的图像地址
     * @param width       设置新的图像宽度
     * @param height      设置新的图像高度
     */
    public static void scale(String srcImageFile, String result, int width, int height) {
        scale(srcImageFile, result, width, height, 0, 0);
    }

    public static void scale(String srcImageFile, String result, int oldWidth, int oldHeight, int x, int y) {
        try {

            BufferedImage src = ImageIO.read(new File(srcImageFile)); // 读入文件

            int width = src.getWidth(); // 得到源图宽
            int height = src.getHeight(); // 得到源图长

            if (width > oldWidth) {
                width = oldWidth;
            }
            if (height > oldHeight) {
                height = oldHeight;
            }
            Image image = src.getScaledInstance(width, height, Image.SCALE_DEFAULT);
            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            g.drawImage(image, x, y, null); // 绘制缩小后的图
            g.dispose();
            ImageIO.write(tag, "JPEG", new File(result));// 输出到文件流
        } catch (IOException e) {
           logger.error(e.getMessage());
        }
    }

    /**
     * 图像类型转换 GIF->JPG GIF->PNG PNG->JPG PNG->GIF(X)
     */
    public static void convert(String source, String result) {
        try {
            File f = new File(source);
            if (f.canRead()) {
                BufferedImage src = ImageIO.read(f);
                if (f.canWrite()) {
                    ImageIO.write(src, "JPG", new File(result));
                }else {
                    logger.error("文件没有写的权限");
                }
            }else {
                logger.error("文件没有读的权限");
            }
        } catch (Exception e) {
           logger.error(e.getMessage());
        }
    }

    /**
     * 彩色转为黑白
     *
     * @param source
     * @param result
     */
    public static void gray(String source, String result) {
        try {
            BufferedImage src = ImageIO.read(new File(source));
            ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
            ColorConvertOp op = new ColorConvertOp(cs, null);
            src = op.filter(src, null);
            ImageIO.write(src, "JPEG", new File(result));
        } catch (IOException e) {
           logger.error(e.getMessage());
        }
    }
}
