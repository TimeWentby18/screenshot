package edit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

import edit.OperateButton.ButtonStyle;

import javax.imageio.ImageIO;

//绘图工具
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class AddWaterMarkGra {
	
	private final static int DOWN_RIGHT = 0;//右下
	private final static int DOWN_LEFT= 1;//左下
	private final static int UP_RIGHT = 2;//右上
	private final static int UP_LEFT= 3;//左上
	private final static int MIDDLE = 4;//正中
	private final static int UP_LEFT_TO_DOWN_RIGHT = 5;//左上到右下
	private final static int UP_RIGHT_TO_DOWN_LEFT = 6;
	
	/**
	 * 
	 * @param filePath
	 * @param font
	 * @param color
	 * @param toward
	 * @param mark
	 * @param alpha
	 * @return  返回一个已经对图片缓冲流进行操作的BufferedImage对象，输出参数改变
	 */
	public static BufferedImage AddwaterMark(String filePath, Font font,
			Color color, int toward, String mark, float alpha) {
		BufferedImage buffImage=Addwatermark(new File(filePath),  font,
				 color, toward,mark,  alpha);
		return buffImage;
	}
	/**
	 * 
	 * @param file
	 * @param font
	 * @param color
	 * @param toward
	 * @param mark
	 * @param alpha
	 * @return 返回一个已经对图片缓冲流进行操作的BufferedImage对象
	 */
	public static BufferedImage Addwatermark(File file, Font font,
			Color color, int toward, String mark, float alpha) {
		BufferedImage buffImage=GetMyBufferedImg(file);
		Graphics2D g2d=buffImage.createGraphics();		
		//设置水印画笔的各种参数
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		//设置字体样式
		g2d.setColor(color);
		g2d.setFont(font);
		//获取图像宽高
		double IWidth=buffImage.getWidth();
		double IHeight=buffImage.getHeight();
		//获取字符串的宽高，以利于计算起点以及旋转角度
		Point SideLength=getStringTwoSide(mark, g2d);	
		//判断水印位置，并且旋转水印文字
		if(toward==UP_LEFT_TO_DOWN_RIGHT) {
			//g2d.rotate(theta, x, y);
		}else if(toward==UP_RIGHT_TO_DOWN_LEFT) {
			//g2d.rotate(theta, x, y);
		}
		//判断字符串所在位置
		Point StringLocation;
		StringLocation= CalPointStringLocation(toward,SideLength,IWidth,IHeight);
		//设置水印内容以及水印位置
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,alpha));
		g2d.drawString(mark,StringLocation.x ,StringLocation.y );
		return buffImage;
	} 
	
	//private static BufferedImage GetMyBufferedImg(File file,float scale) {
	    private static BufferedImage GetMyBufferedImg(File file) {
		//取得文件的Image实例，用于取得图片的宽与高以及各种属性，以用来初始化可操作BufferedImage对象
		Image image=GetMyImage(file);
		BufferedImage BuffImage=null;
		try {
			BuffImage=ImageIO.read(file);
		}catch(IOException e){
			e.printStackTrace();
		}
		//获取抽象lmage的高和宽
		int width=image.getWidth(null);
	 	int heigth=image.getHeight(null);
	 	BuffImage= new BufferedImage(width, heigth, BufferedImage.TYPE_INT_RGB);
		//设置画笔,在已经建立好的BuffImage上直接操作
	    Graphics2D g2d=BuffImage.createGraphics();
	    //给画笔设置属性
	    g2d.setBackground(Color.white);
	    //g2d.addRenderingHints(hints);
	    //直接绘制图片
	    g2d.fillRect(0, 0, width, heigth);
	    g2d.drawImage(image, 0, 0, width, heigth, null);
	    /*
	     * if(scale != 1.0f){
			//缩放图片
			BufferedImage filteredBufImage =new BufferedImage((int) (width*scale), (int) (height*scale),BufferedImage.TYPE_INT_RGB); //过滤后的图像
			AffineTransform transform = new AffineTransform(); //仿射变换对象
			transform.setToScale(scale, scale); //设置仿射变换的比例因子	
			AffineTransformOp imageOp = new AffineTransformOp(transform, null);//创建仿射变换操作对象			
			imageOp.filter(buffImg, filteredBufImage);//过滤图像，目标图像在filteredBufImage
			buffImg = filteredBufImage;	
		}
		*/
		return BuffImage;
	}
	/**
	 * 
	 * @param file
	 * @return  返回需处理图片的lmage对象实例
	 */
	private static Image GetMyImage(File file) {
		Image image=null;
		
		//判断是否是BMP图片格式，与其他文件格式分开处理
		if(ElseMethod.getFileExtension(file.getName()).equals("bmp")){
			FileInputStream in = null;
			try {
				in = new FileInputStream(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			//Image=BmpLoader.read(in)；
		}  else {
			try {
					image=ImageIO.read(file);
			}catch(Exception e) {
				e.printStackTrace();
			}
	    }
		
		return image;
	}
	/**
	 * 获取绘画该字符串所需的高度和宽度
	 * @param str String 所要测量的字符串
	 * @param g2d Graphics2D
	 * @return Point
	 */
	private static Point getStringTwoSide(String demo, Graphics2D g2d){
		FontMetrics fontMetrics = g2d.getFontMetrics();
		int SWidth = fontMetrics.stringWidth(demo);
		int SAscent = fontMetrics.getAscent();
		return new Point(SWidth, SAscent);
	}
/**
 * 
 * @param toward
 * @param p
 * @param Width
 * @param Height
 * @return 字体的左下角的坐标
 */

    private static Point CalPointStringLocation(int toward,Point p,double Width,double Height) {
    	int x = 0, y = 0;
		if(toward == UP_LEFT_TO_DOWN_RIGHT){
			double hypotenuse = Math.sqrt(Math.pow(Width, 2)
					+ Math.pow(Height, 2));
			double tempX = (p.x/2) * Width/hypotenuse;
			double tempY = (p.x/2) * Height/hypotenuse;
			x = (int) (Width/2 - tempX);
			y = (int) (Height/2 - tempY);
		}else if(toward == UP_RIGHT_TO_DOWN_LEFT){
			double hypotenuse = Math.sqrt(Math.pow(Width, 2)
					+ Math.pow(Height, 2));
			double tempX = (p.x/2) * Width/hypotenuse;
			double tempY = (p.x/2) * Height/hypotenuse;
			x = (int) (Width/2 - tempX);
			y = (int) (Height/2 + tempY);
		}else if(toward == DOWN_RIGHT){
			x = (int)Width - p.x;
			y = (int)Height - p.y;
		}else if(toward == DOWN_LEFT){
			x = 5;
			y = (int)Height - p.y;
		}else if(toward == UP_RIGHT){
			x = (int)Width - p.x - 3;
			y = p.y;
		}else if(toward == UP_LEFT){
			x = 5;
			y = p.y;
		}else if(toward == MIDDLE){
			x = (int)Width/2-p.x/2;
			y = (int)Height/2;
		}
		return new Point(x, y);
    }
/**
 * 算法选择
 * @return RenderingHints的一个对象
 */
    private static RenderingHints getMyRenderingHints(){
    	RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,// 抗锯齿提示键。
    			RenderingHints.VALUE_ANTIALIAS_ON);// 抗锯齿提示值——使用抗锯齿模式完成呈现。
    	rh.put(RenderingHints.KEY_TEXT_ANTIALIASING ,// 文本抗锯齿提示键。
    			RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_VRGB);//要求针对 LCD 显示器优化文本显示
    	rh.put(RenderingHints.KEY_ALPHA_INTERPOLATION,// Alpha 插值提示值
    			RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY );// Alpha 插值提示值——选择偏重于精确度和视觉质量的 alpha 混合算法
    	rh.put(RenderingHints.KEY_RENDERING,// 呈现提示键。
    			RenderingHints.VALUE_RENDER_QUALITY);// 呈现提示值——选择偏重输出质量的呈现算法
    	rh.put(RenderingHints.KEY_STROKE_CONTROL ,//笔划规范化控制提示键。
    			RenderingHints.VALUE_STROKE_NORMALIZE);//几何形状应当规范化，以提高均匀性或直线间隔和整体美观。
    	rh.put(RenderingHints.KEY_COLOR_RENDERING  ,//颜色呈现提示键。
    			RenderingHints.VALUE_COLOR_RENDER_QUALITY );// 用最高的精确度和视觉质量执行颜色转换计算。
    	return rh;
    }
}