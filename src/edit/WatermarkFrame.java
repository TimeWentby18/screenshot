package edit;

import edit.OperateButton.ButtonStyle;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.image.BufferedImage;

public class WatermarkFrame extends JFrame {

	private String[] FontColor;
	private String[] FontStyle;
	private String[]	FontLocation;	
	private String[]	FontType;
	
	private Color[]  DetailFontColor= {Color.RED,Color.ORANGE,Color.YELLOW,Color.BLUE,Color.WHITE,Color.BLACK};
	private int[]      DetailFontStyle= {Font.PLAIN, Font.BOLD, Font.ITALIC, Font.BOLD | Font.ITALIC};
	private String[] DetailFontType;
	//private String[]	DetailFontLocation;	
	private  float  ImageAlpha=1.0f;
	
	private  JPanel JWaterPanel;
	private  JPanel Jtest;
	//private  JLabel JWaterLabel;
    private  JLabel JTextLabel;
	private  JLabel JFontSizeLabel;
	private  JLabel JFontColorLabel;
	private  JLabel JFontTypeLabel;
	private  JLabel JFontLocationLabel;
    private  JLabel JTranYLabel;
    private  JLabel JExportSavePath;
    private  JLabel JImageSavePath;
    
    private  JButton JWaterLabel;
    private  JButton ExportButton;
    private  JButton SaveButton;
    private  JButton PreviewButton; 
    private  JButton WaterStartButton; 
    private  JButton ExitButton;
    private  JButton MoreOperationButton;
    private  JLabel test;
    
    private  JComboBox JFColor;
	private  JComboBox JFType;
	private  JComboBox JFLocation;
	private  JComboBox JFTran;
	private  JComboBox JFStyle;
	
	
	private  JTextField JText;
	private  JTextField JFSize;
	private  JTextArea JLook;
	private  JTextField JExportPath;
	private  JTextField JSavePath;
	
	float alpha=0.5f;
	
	private  JSlider JTyan;
	
	private JProgressBar JWaterProgress;

	protected enum UIStyleName {
		systemLookAndFeel(UIManager.getSystemLookAndFeelClassName()), //系统界面
		motifLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel"), 
		crossPlatformLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()), 
		metalLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		private String className;

		UIStyleName(String className) {
			this.className = className;
		}

		public String getUIClassName() {
			return className;
		}
	}
	
	private static final WatermarkFrame mainFrame = new WatermarkFrame();
	public static WatermarkFrame getInstance() {
		return mainFrame;
	}
	
	private WatermarkFrame() {
		
		
		setSize(800,800);
		setTitle("贴水印");
		setBounds(500,300,800,400);
		setResizable(true);
		setLayout(new BorderLayout());
		setBackground(Color.WHITE);
		add(getWestJPanel(),BorderLayout.CENTER);
		Jtest=getEastJPanel();
		add(Jtest,BorderLayout.EAST);
		add(getSouthJPanel(),BorderLayout.SOUTH);
		setUI(UIStyleName.systemLookAndFeel);
		setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public JPanel getWestJPanel() {
		
		JPanel WPanel=new JPanel(new GridBagLayout());
		GridBagConstraints  Gb= new GridBagConstraints();
	    

		Gb.gridwidth=GridBagConstraints.REMAINDER;
		Gb.fill=GridBagConstraints.BOTH;
		
		JExportPath=new JTextField();
		ExportButton=new OperateButton(OperateButton.ButtonStyle.export);
		
		JExportPath.setText("");
		JExportPath.setFont(new Font("黑体",Font.PLAIN,18));
		JExportPath.setForeground(new Color(105,105,105));
		JExportPath.setEditable(true);
		//ExportButton=new OperateButton(ButtonStyle.preview);
		//ExportButton.setText("预览图片效果");
		ExportButton.setFont(new Font("黑体",Font.BOLD,20));
		ExportButton.setBackground(new Color(143,194,106));
		ExportButton.setVisible(true);
		ExportButton.setFocusPainted(false);
	    ExportButton.setForeground(Color.white );  
	    Gb.gridwidth=1;
		WPanel.add(JExportPath,Gb);
		Gb.gridwidth=0;
		WPanel.add(ExportButton,Gb);
		
		JWaterLabel=new JButton();
		JWaterLabel.setText("水印设置");
		JWaterLabel.setFont(new Font("黑体",Font.BOLD,20));
		JWaterLabel.setBackground(new Color(143,194,106));
		//JWaterLabel.setEnabled(false); 
		JWaterLabel.setVisible(true);
		JWaterLabel.setFocusPainted(false);
		JWaterLabel.setForeground(Color.white ); //设置字体颜色
		Gb.gridwidth=0;
		WPanel.add(JWaterLabel,Gb);
		
		JLabel JTextLabel=new JLabel();
		JLabel JFontSizeLabel=new JLabel();
		JLabel JFontColorLabel=new JLabel();
		JLabel JFontTypeLabel=new JLabel();
		JLabel JFontLocationLabel=new JLabel();
		JLabel JFontTranYLabel=new JLabel();
		
		JText=new JTextField();
		JFSize=new JTextField(16);
		
		
		JTextLabel.setText("水印内容:");
		JTextLabel.setFont(new Font("黑体",Font.PLAIN,18));
		JText.setText("输入水印内容");
		JText.setFont(new Font("黑体",Font.PLAIN,18));
		JText.setForeground(new Color(105,105,105));
		JText.setEditable(true);
		Gb.gridwidth=1;
		WPanel.add(JTextLabel,Gb);
		Gb.gridwidth=0;
		WPanel.add(JText,Gb);
		
		JFontSizeLabel.setText("水印字体大小:");
		JFontSizeLabel.setFont(new Font("黑体",Font.PLAIN,18));
		JFSize.setText("20");
		JFSize.setFont(new Font("黑体",Font.PLAIN,18));
		JFSize.setForeground(new Color(105,105,105));
		JFSize.setEditable(true);
		Gb.gridwidth=1;
		WPanel.add(JFontSizeLabel,Gb);
		Gb.gridwidth=0;
		WPanel.add(JFSize,Gb);
		
		FontColor=new String[] {"红色","橙色","黄色","绿色","蓝色","白色","黑色"};
		FontStyle	=	new String[] {"标准","粗体","斜体","粗体&斜体"};
		FontLocation=new String[] {"左上","左下","右上","右下","正中间","左上--右下","左下--右上"};
		
	    JFColor=new JComboBox<String>(FontColor);
		JFStyle=new JComboBox<String>(FontStyle);
		JFLocation=new JComboBox<String>(FontLocation);
		
		JFontColorLabel.setText("水印颜色:");
		JFontColorLabel.setFont(new Font("黑体",Font.PLAIN,18));
		JFColor.setSelectedIndex(1);
		Gb.gridwidth=1;
		WPanel.add(JFontColorLabel,Gb);
		Gb.gridwidth=0;
		WPanel.add(JFColor,Gb);
		
		JFontTypeLabel.setText("水印字体:");
		JFontTypeLabel.setFont(new Font("黑体",Font.PLAIN,18));
		//JFType.setSelectedIndex(1);
		Gb.gridwidth=1;
		WPanel.add(JFontTypeLabel,Gb);
		Gb.gridwidth=0;
		WPanel.add(JFStyle,Gb);
		
		JFontLocationLabel.setText("水印位置:");
		JFontLocationLabel.setFont(new Font("黑体",Font.PLAIN,18));
		JFLocation.setSelectedIndex(1);
		Gb.gridwidth=1;
		WPanel.add(JFontLocationLabel,Gb);
		Gb.gridwidth=0;
		WPanel.add(JFLocation,Gb);
		
		JFontTranYLabel.setText("透明度");
		JTyan = new JSlider(JSlider.HORIZONTAL);
		JTyan.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e) {
				alpha = (float)JTyan.getValue()/100;
			}
		});
		;
		//JTyan=new JSlider(0,100,50);
		//JTyan.setMajorTickSpacing(20);
		//JTyan.setMinorTickSpacing(5);
		//JTyan.setPaintTicks(true);
		//JTyan.setPaintLabels(true);
		Gb.gridwidth=1;
		WPanel.add(JFontTranYLabel,Gb);
		Gb.gridwidth=0;
		//WPanel.add(JTyan,Gb);
		WPanel.add(JTyan, Gb);
		
		PreviewButton=new OperateButton(ButtonStyle.preview);
		//PreviewButton.setText("预览图片效果");
		PreviewButton.setFont(new Font("黑体",Font.BOLD,20));
		PreviewButton.setBackground(new Color(143,194,106));
		PreviewButton.setVisible(true);
		PreviewButton.setFocusPainted(false);
		PreviewButton.setForeground(Color.white );  
		
		WaterStartButton=new OperateButton(ButtonStyle.confirm);
		//WaterStartButton.setText("确认");
		WaterStartButton.setFont(new Font("黑体",Font.BOLD,20));
		WaterStartButton.setBackground(new Color(143,194,106));
		//JWaterLabel.setEnabled(false); 
		WaterStartButton.setVisible(true);
		WaterStartButton.setFocusPainted(false);
		WaterStartButton.setForeground(Color.white );   //设置字体颜色
		
		MoreOperationButton=new OperateButton(ButtonStyle.more);
		//PreviewButton.setText("预览图片效果");
		MoreOperationButton.setFont(new Font("黑体",Font.BOLD,20));
		MoreOperationButton.setBackground(new Color(143,194,106));
		MoreOperationButton.setVisible(true);
		MoreOperationButton.setFocusPainted(false);
		MoreOperationButton.setForeground(Color.white );  
		
		ExitButton=new OperateButton(ButtonStyle.sighOut);
		//ExitButton.setText("退出");
		ExitButton.setFont(new Font("黑体",Font.BOLD,20));
		ExitButton.setBackground(new Color(143,194,106));
		//JWaterLabel.setEnabled(false); 
		ExitButton.setVisible(true);
		ExitButton.setFocusPainted(false);
		ExitButton.setForeground(Color.white );   //设置字体颜色
		
		Gb.gridwidth=1;
		WPanel.add(PreviewButton,Gb);
		Gb.gridwidth=1;
		WPanel.add(WaterStartButton,Gb);
		Gb.gridwidth=1;
		WPanel.add(MoreOperationButton,Gb);
		Gb.gridwidth=0;
		WPanel.add(ExitButton,Gb);
		
		JSavePath=new JTextField();
		SaveButton=new OperateButton(ButtonStyle.save);
		
		JSavePath.setText("");
		JSavePath.setFont(new Font("黑体",Font.PLAIN,18));
		JSavePath.setForeground(new Color(105,105,105));
		JSavePath.setEditable(true);
		//SaveButton=new OperateButton(ButtonStyle.preview);
		//ExportButton.setText("预览图片效果");
		SaveButton.setFont(new Font("黑体",Font.BOLD,20));
		SaveButton.setBackground(new Color(143,194,106));
		SaveButton.setVisible(true);
		SaveButton.setFocusPainted(false);
		SaveButton.setForeground(Color.white );  
		Gb.gridwidth=1;
		WPanel.add(JSavePath,Gb);
		Gb.gridwidth=0;
		WPanel.add(SaveButton,Gb);
		
		return WPanel;
	}
	
	 public JPanel getSouthJPanel() {
		
		JPanel WPanel=new JPanel();
		JWaterProgress=new JProgressBar();
		JWaterProgress.setMaximum(200);
		JWaterProgress.setMinimum(-100);
		JWaterProgress.setValue(0);
		JWaterProgress.setPreferredSize(new Dimension(780,30));
		JWaterProgress.setStringPainted(true);
		JWaterProgress.setBackground(Color.white);

		JWaterProgress.setForeground(new Color(143,194,106));
		
		WPanel.add(JWaterProgress);
		WPanel.setPreferredSize(new Dimension(0, 40));
		return WPanel;
	}	
	 
	 public JPanel getEastJPanel()throws NullPointerException{
		 
		JPanel WPanel=new JPanel();
		test=new JLabel();
		test.setIcon(new ImageIcon("demo1.png"));
		//test =new JLabel("此处预览水印图片效果");
		 WPanel.add(test);
		// JLook=new JTextArea("在这里可以预览图片效果",10,20);
		// JLook.setLineWrap(true);
		// JLook.setForeground(new Color(143,194,106));
		 //JLook.setFont(new Font(null, Font.PLAIN, 20));
		// WPanel.add(JLook);
		// JLabel label=new JLabel();
	     //label.setIcon(new ImageIcon("lufu.jpg"));
	     //WPanel.add(label);
		 return WPanel;
	 }
	 
	 /**
	  * 
	  * @return 进度条
	  */
	 public  JProgressBar getWaterProgress() {
			return JWaterProgress;
		}
	 
	 /**
	  * 
	  * @return 字体位置
	  */
	 public int getFontToward() {
			return JFLocation.getSelectedIndex();
		}
	 /**
	  * 
	  * @return 字体样式
	  */
	 public int getFontStyle() {
			return DetailFontStyle[JFStyle.getSelectedIndex()];
		}
	 
	 public int getFontSize() {
			return (int) (Integer.parseInt(JFSize.getText()));
			//return 20;
		}
	 /**
	  * 
	  * @return 水印的透明度
	  */
	 public float getWaterMarkAlpha() {
			return alpha;
		}
	 /**
	  * @return 返回水印的字体颜色
	  * 
	  */
	 public Color getFONTColor() {
		 return DetailFontColor[JFColor.getSelectedIndex()];
	 }
	 
	 /**
	  * @return 返回水印的字体
	  * 
	  */
	 public String getFONTType() {
		 return FontType[JFType.getSelectedIndex()];
	 }
	 
	 /**
	  * @return 返回水印的字体大小
	  * 
	  */
	 public int getFONTSize() {
		  return (int) (Integer.parseInt(JFSize.getText()));
	 }
	 
	 /**
	  * @return 返回水印的字体具体内容
	  * 
	  */
	 public String getFONTContent() {
		  return JText.getText();	 
		 //return "你好啊啊啊啊啊啊";
	 }
	 /**
	  * 
	  * @return 导出图片地址内容
	  */
	 public String getExportPath() {
		 return JExportPath.getText();
	 }
	 /**
	  * 
	  * @return 保存图片地址内容
	  */
	 public String getSavePath() {
		 return JSavePath.getText();
	 }
	 /**
	  * 
	  * @return 
	  */
	 
	 public JTextField getExportPathTextField() {
		 return JExportPath;
	 }
	 
	 
	 public JTextField getSavePathTextField() {
		 return JSavePath;
	 }
	 
	 public void setEastPanel(BufferedImage buffing) {
		 //JLabel label=new JLabel();
	      test.setIcon(new ImageIcon(buffing));
	     //test.add(label);
	 }
	 
		protected void setUI(UIStyleName className) {
			try {
				UIManager.setLookAndFeel(className.getUIClassName());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	 
}
