package edit;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;

public class ElseMethod {
	/**
	 * 
	 * @param getFileExtension
	 * @return 文件的后缀名字
	 */
	public static String getFileExtension(String filename){
		return filename.substring(filename.lastIndexOf(".")+1).toLowerCase();
	}
	
	public static String getNewFileName(String filepath) {
		String new_Filename = "\\New_"
				+ filepath.substring(filepath.lastIndexOf("\\") + 1);
		return new_Filename;
	}
	
	public static BufferedImage addWaterMark() throws NullPointerException{
		WatermarkFrame WFrame=WatermarkFrame.getInstance();
		Font font=new Font("楷体",WFrame.getFontStyle(),WFrame.getFontSize());
		String filePath=WFrame.getExportPath();
	    Color color=WFrame.getFONTColor();
		int toward=WFrame.getFontToward();
		String mark=WFrame.getFONTContent();
		float alpha= WFrame.getWaterMarkAlpha();
		BufferedImage BuffImage=AddWaterMarkGra.AddwaterMark(1,filePath, font, color, toward, mark, alpha);
		return BuffImage;
		//String PreviewImage=getPreviewImagePath();
		//return addWaterMark(PreviewImage);
	}
}
