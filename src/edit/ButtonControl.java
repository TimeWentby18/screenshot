package edit;


import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.filechooser.FileNameExtensionFilter;

import edit.ElseMethod;
import edit.OperateButton.ButtonStyle;

import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonControl implements ActionListener {
	private WatermarkFrame WFrame;
	private ButtonStyle style;
	private JFileChooser FileChooser;
	
	
    public ButtonControl(ButtonStyle style) {
    	this.style=style;
    }
    
    
	public void actionPerformed(ActionEvent e)throws NullPointerException {
		WFrame=WatermarkFrame.getInstance();
		if(style.equals(ButtonStyle.export)) {
			String string1=getExportImageString();
			if(string1!=null) {
				WFrame.getExportPathTextField().setText(string1);
				WFrame.getSavePathTextField().setText(string1.substring(0, string1.lastIndexOf("\\")) + ElseMethod.getNewFileName(string1));
			}
			
		}else if(style.equals(ButtonStyle.save)) {
			String string2=getSaveImageString();
			if(string2!=null) {
				WFrame.getSavePathTextField().setText(string2+ ElseMethod.getNewFileName(WFrame.getExportPath()));
			}
		}else if(style.equals(ButtonStyle.preview)){
			//JOptionPane.showMessageDialog(null,"预览操作成功");
			String PreviewImage=getPreviewImagePath();
			BufferedImage buffing=addWaterMark(PreviewImage);
			WFrame.setEastPanel(buffing);
			//WFrame.getEastJPanel().add(new JButton("登陆"));
			//WFrame.add(WFrame.getEastJPanel(),BorderLayout.EAST);
		}else if(style.equals(ButtonStyle.confirm)){
			//JOptionPane.showMessageDialog(null,"确认操作成功！");
			String PreviewImage=getPreviewImagePath();
			//生成预览图片的文件对象
			//File file=new File(PreviewImage);
			//生成预览图片的一个缓冲图像
			BufferedImage buffing=addWaterMark(PreviewImage);
			ImageSaveToDES(buffing,WFrame.getSavePath());
			WFrame.getWaterProgress().setValue(WFrame.getWaterProgress().getMaximum());
		}else if(style.equals(ButtonStyle.sighOut)) {
			JOptionPane.showMessageDialog(null,"退出操作成功！");
		}
	}
	
	
	/**
	 * 
	 * @return 返回文件选择器选中的文件字符串
	 */
	public String getExportImageString() {
		String ExportPath=WFrame.getExportPath();
		
		FileChooser =new JFileChooser();
		FileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		FileChooser.addChoosableFileFilter(new FileNameExtensionFilter("图片文件(*.bmp, *.gif, *.jpg, *.jpeg,)", "bmp",
				"gif", "jpg", "jpeg"));
		FileChooser.setFileFilter(new  FileNameExtensionFilter("png文件(*.png)","png"));
		
		int result=FileChooser.showOpenDialog(null);
		if(result==JFileChooser.APPROVE_OPTION) {
			File file=FileChooser.getSelectedFile();
			return file.getAbsolutePath();
		}
		return null;
	}
	
	
	/**
	 * 
	 * @return 返回文件选择器保存的文件字符串
	 */
	public String getSaveImageString() {
		String SavePath=WFrame.getSavePath();
		
		FileChooser =new JFileChooser();
		FileChooser.setSelectedFile(new File("新图片"));
		//FileChooser.addChoosableFileFilter(new FileNameExtensionFilter("图片文件(*.bmp, *.gif, *.jpg, *.jpeg,)", "bmp",
		//		"gif", "jpg", "jpeg"));
		//FileChooser.setFileFilter(new  FileNameExtensionFilter("png文件(*.png)","png"));
		
		int result=FileChooser.showSaveDialog(null);
		if(result==JFileChooser.APPROVE_OPTION) {
			File file=FileChooser.getSelectedFile();
			return file.getAbsolutePath();
		}
		return null;
	}
	
	
	/**
	 * 
	 * @return 主界面中选择的图片取出路径
	 */
	public String getPreviewImagePath() {
		return WFrame.getExportPath();
	}
 
	public  BufferedImage addWaterMark(String filePath) {
		Font font=new Font("楷体",WFrame.getFontStyle(),WFrame.getFontSize());
		Color color=WFrame.getFONTColor();
		int toward=WFrame.getFontToward();
		String mark=WFrame.getFONTContent();
		float alpha= WFrame.getWaterMarkAlpha();
		BufferedImage BuffImage=AddWaterMarkGra.AddwaterMark(filePath, font, color, toward, mark, alpha);
		return BuffImage;
	}
	
	
	/**
	 * 生成添加文字后的图片
	 * @param buffImg 图像加文字之后的BufferedImage对象
	 * @param savePath 图像加文字之后的保存路径
	 */
	private void ImageSaveToDES(BufferedImage buffImg, String savePath) {
		int temp = savePath.lastIndexOf(".") + 1;
		try {
			ImageIO.write(buffImg, savePath.substring(temp), new File(savePath));//写图片
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	
	/**
	 * 
	 * @return 
	 */
	public BufferedImage addWaterMark2() {
		String PreviewImage=getPreviewImagePath();
		return addWaterMark(PreviewImage);
	}
}
