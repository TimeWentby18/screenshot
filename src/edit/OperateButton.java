package edit;

import javax.swing.*;

import edit.ButtonControl;

import java.awt.*;
import javax.imageio.ImageIO;

import java.io.File;
import java.io.IOException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OperateButton extends JButton{
	
	protected enum ButtonStyle{
        export("选择图片"),
		save("图片存储地址"),
		preview("预览图片效果"),
		confirm("确认"),
		more("更多功能"),
		sighOut("退出");
		private String name;
		ButtonStyle(String name){
			this.name=name;
		}
		public String getButtonName() {
			return this.name;
		}
	}
	
	OperateButton(ButtonStyle style){
		super(style.getButtonName());
		addActionListener(new ButtonControl(style));	
	}
 
}
