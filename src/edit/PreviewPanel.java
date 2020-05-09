package edit;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

//import javax.swing.event.MouseInputEvent;
import javax.swing.event.MouseInputListener;

import edit.OperateButton.ButtonStyle;

import java.awt.image.BufferedImage;



public class PreviewPanel extends JPanel {
	//BufferedImage buffImg=ElseMethod.addWaterMark2();
	BufferedImage buffImg;
	private static final long serialVersionUID = 1L;
     
					
	public PreviewPanel(BufferedImage buffImg)throws NullPointerException {	
		this.buffImg=buffImg;
		int imgHeight = buffImg.getHeight();
		int imgWidth = buffImg.getWidth();
		}
       public void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D) g;//二维图形处理
				if (buffImg != null) {
					g2d.drawImage(buffImg, 0, 0, this);
	   }	
	}
}


