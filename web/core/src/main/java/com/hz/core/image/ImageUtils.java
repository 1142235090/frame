package com.hz.core.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @explain
 * @Classname ImageUtils
 * @Date 2021/12/17 11:04
 * @Created by hanzhao
 */
public class ImageUtils {

    /**
     * 将图像转化为二位数组
     * @param bg 图片缓冲区
     * @return
     */
    public static int[][] getImagepix(BufferedImage bg){
        bg.getHeight();
        bg.getWidth();
        int num =Math.max(bg.getWidth(), bg.getHeight());
        int[][] imgdata = new int[num][num];
        for(int i=0;i<num;i++) {
            for(int j=0;j<num;j++) {
                if(i<bg.getWidth()&&j<bg.getHeight())
                    imgdata[i][j]=bg.getRGB(i, j);
                else imgdata[i][j]=0;
            }
        }
        return imgdata;
    }

    /**
     * 将图像转化为二位数组
     * @param path 图片路径
     * @return
     */
    public static int[][] getImagepix(String path){
        BufferedImage bg = readImageToBuffer(path);
        bg.getHeight();
        bg.getWidth();
        int width = bg.getHeight();
        int height = bg.getWidth();
        int[][] imgdata = new int[height][width];
        for(int i=0;i<height;i++) {
            for(int j=0;j<width;j++) {
                if(i<bg.getWidth()&&j<bg.getHeight())
                    imgdata[i][j]=bg.getRGB(i, j);
                else imgdata[i][j]=0;
            }
        }
        return imgdata;
    }

    /**
     * 将图片读取到buffer中
     * @param path
     * @return
     */
    public static BufferedImage readImageToBuffer(String path){
        File file = new File(path);
        BufferedImage buffimg = null;
        try {
            buffimg= ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffimg;
    }

    /**
     * 去色
     */
    public static void finishColor(String path,int alpha){
        int[][] imgdata = getImagepix(path);
        BufferedImage bufferedImage = new BufferedImage(imgdata.length, imgdata[0].length, BufferedImage.TYPE_INT_ARGB);
        for(int i=1;i<imgdata.length;i++) {
            for(int j=0;j<imgdata[i].length;j++) {
                Color color = new Color(imgdata[i][j]);
                int red = color.getRed();
                int blue = color.getBlue();
                int green = color.getGreen();
                int avg = (Math.max(Math.max(red, blue),green)+Math.max(Math.min(red, blue),green))/2;
                if(avg>=40){
                    avg+=60;
                    if(avg>255){
                        avg=255;
                    }
                    Color newcolor = new Color(avg,avg,avg);
                    int rgb = ((alpha * 255 / 10) << 24) | (newcolor.getRGB() & 0x00ffffff);
                    bufferedImage.setRGB(i, j, rgb);
                }
            }
        }
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
        g2d.drawImage(bufferedImage, 0, 0, null);
        try {
            ImageIO.write(bufferedImage, "png", new File("生成的图片地址.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        String path = "图片.JPG";
        finishColor(path,1);
    }
}
