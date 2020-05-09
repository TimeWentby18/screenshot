package main;

import javax.swing.filechooser.FileFilter;
import java.io.File;

//用于FileChooser的过滤器，过滤指定的文件格式
public class MyFilter {
    //JPG过滤器
    static class JPGFilter extends FileFilter {

        @Override
        public boolean accept(File f) {
            return (f.isFile() && f.toString().toLowerCase().endsWith(".jpg"));
        }

        @Override
        public String getDescription() {
            return "*.JPG(JPG图像)";
        }
    }

    //PNG过滤器
    static class PNGFilter extends FileFilter {

        @Override
        public boolean accept(File f) {
            return (f.isFile() && f.toString().toLowerCase().endsWith(".png"));
        }

        @Override
        public String getDescription() {
            return "*.PNG(PNG图像)";
        }
    }

    //BMP过滤器
    static class BMPFilter extends FileFilter {

        @Override
        public boolean accept(File f) {
            return (f.isFile() && f.toString().toLowerCase().endsWith(".bmp"));
        }

        @Override
        public String getDescription() {
            return "*.BMP(BMP图像)";
        }
    }

    //GIF过滤器
    static class GIFFilter extends FileFilter {

        @Override
        public boolean accept(File f) {
            return (f.isFile() && f.toString().toLowerCase().endsWith(".gif"));
        }

        @Override
        public String getDescription() {
            return "*.GIF(GIF图像)";
        }
    }
}
