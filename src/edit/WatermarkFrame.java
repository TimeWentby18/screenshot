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
	
	private Color[]  DetailFontColor= {Color.RED,Color.ORANGE,Color.YELLOW,Color.GREEN,Color.BLUE,Color.WHITE,Color.BLACK};
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
	

	private  WatermarkFrame() {
		setTitle("水印");
		setBounds(400,200,850,400);
		//setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
	
		ImageIcon icon=new ImageIcon("imagex.png");
		//setSize(icon.getIconWidth(),icon.getIconHeight());
		//Container c=this.getContentPane();
		JLabel label=new JLabel(icon);	
		//设置label的大小
		label.setBounds(0,0,icon.getIconWidth(),icon.getIconHeight());
		//获取窗口的第二层，将label放入
		getLayeredPane().add(label,new Integer(Integer.MIN_VALUE));
		//获取frame的顶层容器,并设置为透明
		JPanel j=(JPanel)getContentPane();
		j.setOpaque(false);
	
//		Container c=this.getContentPane();	
//        c.setLayout(new BorderLayout());
	
        
		
		//setUI(UIStyleName.systemLookAndFeel);
		setLayout(new BorderLayout());
		JPanel WPanel=new JPanel(new GridBagLayout());
//		GridBagConstraints  Gb= new GridBagConstraints();
//	    Gb.gridwidth=GridBagConstraints.REMAINDER;
//		Gb.fill=GridBagConstraints.HORIZONTAL;
		//JPanel panel01=new JPanel();
		
		

		
		/*进度条
		JWaterProgress=new JProgressBar();
		JWaterProgress.setMaximum(200);
		JWaterProgress.setMinimum(-100);
		JWaterProgress.setValue(0);
		JWaterProgress.setPreferredSize(new Dimension(780,30));
		JWaterProgress.setStringPainted(true);
		JWaterProgress.setBackground(Color.white);
		JWaterProgress.setForeground(new Color(143,194,106));
		Gb.gridwidth=0;
		WPanel.add(JWaterProgress,Gb);
		WPanel.setPreferredSize(new Dimension(0, 40));
		*/
		WPanel=getNorthJPanel();
		WPanel.setOpaque(false);
		add(WPanel,BorderLayout.NORTH);
		
		WPanel=getCenterJPanel();
		WPanel.setOpaque(false);
		add(WPanel,BorderLayout.CENTER);
		
		WPanel=getSouthJPanel();
		WPanel.setOpaque(false);
		add(WPanel,BorderLayout.SOUTH);
		
		WPanel=getEastJPanel();
		WPanel.setOpaque(false);
		add(WPanel,BorderLayout.EAST);
		
		setVisible(true);
		
	}
	
	public JPanel getEastJPanel()throws NullPointerException{
		 
		JPanel WPanel=new JPanel(new BorderLayout());
		test=new JLabel();
		test.setIcon(new ImageIcon("demo1.png"));
		//test =new JLabel("此处预览水印图片效果");
		 WPanel.add(test,BorderLayout.CENTER);
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
	
	 public JPanel getNorthJPanel() {
		 JPanel WPanel=new JPanel(new GridBagLayout()); 
		 GridBagConstraints  Gb= new GridBagConstraints();
			Gb.gridwidth=GridBagConstraints.REMAINDER;
			Gb.fill=GridBagConstraints.HORIZONTAL;
		   JExportPath=new JTextField(26);
		   ExportButton=new OperateButton(ButtonStyle.export);
			//JExportPath.setText("20");
			JExportPath.setFont(new Font("黑体",Font.PLAIN,18));
			JExportPath.setForeground(new Color(105,105,105));
			ExportButton.setText("选择图片");
			ExportButton.setFont(new Font("黑体",Font.PLAIN,20));
			ExportButton.setForeground(new Color(105,105,105));
			//ExportButton.setBorder(null);
			JExportPath.setEditable(true);
			//ExportButton=new OperateButton(ButtonStyle.preview);
			//ExportButton.setText("预览图片效果");
			ExportButton.setFont(new Font("黑体",Font.BOLD,20));
			ExportButton.setBackground(new Color(143,194,106));
			ExportButton.setVisible(true);
			ExportButton.setFocusPainted(false);
		    ExportButton.setForeground(new Color(143,194,106));  
		    Gb.gridwidth=1;
			WPanel.add(ExportButton,Gb);
		    Gb.gridwidth=0;
			WPanel.add(JExportPath,Gb);
			
					
			JSavePath=new JTextField(26);
			SaveButton=new OperateButton(ButtonStyle.save);
			JSavePath.setText("");
			JSavePath.setFont(new Font("黑体",Font.PLAIN,18));
			JSavePath.setForeground(new Color(105,105,105));
			JSavePath.setEditable(true);
			//SaveButton.setText("图片存储地址");
			SaveButton.setFont(new Font("黑体",Font.BOLD,20));
			SaveButton.setBackground(new Color(143,194,106));
			SaveButton.setVisible(true);
			SaveButton.setFocusPainted(false);
			SaveButton.setForeground( new Color(143,194,106));  
			Gb.gridwidth=1;
			WPanel.add(SaveButton,Gb);
			Gb.gridwidth=0;
			WPanel.add(JSavePath,Gb);
			
			return WPanel;
	 }
	
	 public JPanel getSouthJPanel() {
			
			JPanel WPanel=new JPanel();
			JWaterProgress=new JProgressBar();
			JWaterProgress.setMaximum(200);
			JWaterProgress.setMinimum(0);
			JWaterProgress.setValue(0);
			JWaterProgress.setPreferredSize(new Dimension(450,20));
			JWaterProgress.setStringPainted(true);
			JWaterProgress.setBackground(Color.white);

			JWaterProgress.setForeground(new Color(143,194,106));
			
			WPanel.add(JWaterProgress);
			WPanel.setPreferredSize(new Dimension(0, 40));
			return WPanel;
		}	
	 
	 public JPanel getCenterJPanel() {
			
			JPanel WPanel=new JPanel(new GridBagLayout());
			GridBagConstraints  Gb= new GridBagConstraints();
			Gb.gridwidth=GridBagConstraints.REMAINDER;
			Gb.fill=GridBagConstraints.BOTH;
			
			JWaterLabel=new JButton();
			JWaterLabel.setText("水印设置");
			JWaterLabel.setFont(new Font("黑体",Font.BOLD,20));
			JWaterLabel.setBackground(new Color(143,194,106));
			//JWaterLabel.setOpaque(false);
			JWaterLabel.setBorder(null);
			JWaterLabel.setVisible(true);
			JWaterLabel.setFocusPainted(false);
			JWaterLabel.setForeground(Color.WHITE ); //设置字体颜色
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
			
			
			JTextLabel.setText("水印内容：");
			JTextLabel.setFont(new Font("黑体",Font.BOLD,20));
			JText.setText("输入水印内容");
			JText.setFont(new Font("黑体",Font.PLAIN,18));
			JText.setForeground(new Color(105,105,105));
			JText.setEditable(true);
			Gb.gridwidth=1;
			WPanel.add(JTextLabel,Gb);
			Gb.gridwidth=0;
			WPanel.add(JText,Gb);
			
			JFontSizeLabel.setText("水印字体大小：");
			JFontSizeLabel.setFont(new Font("黑体",Font.BOLD,20));
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
			
			JFColor.setForeground(Color.BLACK);
			JFColor.setBackground(Color.WHITE);
			//JFColor.setBackground(new Color(255,0,0,0));
			JFStyle.setForeground(Color.BLACK);
			JFStyle.setBackground(Color.WHITE);
			JFLocation.setForeground(Color.BLACK);
			JFLocation.setBackground(Color.WHITE);
			JFontColorLabel.setText("水印颜色：");
			JFontColorLabel.setFont(new Font("黑体",Font.BOLD,20));
			JFColor.setSelectedIndex(1);
			Gb.gridwidth=1;
			WPanel.add(JFontColorLabel,Gb);
			Gb.gridwidth=0;
			WPanel.add(JFColor,Gb);
			
			JFontTypeLabel.setText("水印字体：");
			JFontTypeLabel.setFont(new Font("黑体",Font.BOLD,20));
			//JFType.setSelectedIndex(1);
			Gb.gridwidth=1;
			WPanel.add(JFontTypeLabel,Gb);
			Gb.gridwidth=0;
			WPanel.add(JFStyle,Gb);
			
			JFontLocationLabel.setText("水印位置：");
			JFontLocationLabel.setFont(new Font("黑体",Font.BOLD,20));
			JFLocation.setSelectedIndex(1);
			Gb.gridwidth=1;
			WPanel.add(JFontLocationLabel,Gb);
			Gb.gridwidth=0;
			WPanel.add(JFLocation,Gb);
			
			JFontTranYLabel.setText("透明度：");
			JFontTranYLabel.setFont(new Font("黑体",Font.BOLD,20));
			JTyan = new JSlider(JSlider.HORIZONTAL);
			JTyan.addChangeListener(new ChangeListener(){
				public void stateChanged(ChangeEvent e) {
					alpha = (float)JTyan.getValue()/100;
				}
			});
			;
			Gb.gridwidth=1;
			WPanel.add(JFontTranYLabel,Gb);
			Gb.gridwidth=0;
			//WPanel.add(JTyan,Gb);
			WPanel.add(JTyan, Gb);
			
			PreviewButton=new OperateButton(ButtonStyle.preview);
			//PreviewButton.setText("预览图片效果");
			PreviewButton.setFont(new Font("黑体",Font.BOLD,20));
			PreviewButton.setBackground(new Color(143,194,106));
			//PreviewButton.setBorder(null);
			PreviewButton.setVisible(true);
			PreviewButton.setFocusPainted(false);
			PreviewButton.setForeground(new Color(143,194,106));  
			Gb.gridwidth=1;
			WPanel.add(PreviewButton,Gb);
			
			WaterStartButton=new OperateButton(ButtonStyle.confirm);
			//WaterStartButton.setText("确认");
			WaterStartButton.setFont(new Font("黑体",Font.BOLD,20));
			WaterStartButton.setBackground(new Color(143,194,106));
			//WaterStartButton.setOpaque(false);
			//WaterStartButton.setBorder(null);
			WaterStartButton.setVisible(true);
			WaterStartButton.setFocusPainted(false);
		    WaterStartButton.setForeground(new Color(143,194,106));   //设置字体颜色
		    Gb.gridwidth=1;
			WPanel.add(WaterStartButton,Gb);
			
			MoreOperationButton=new OperateButton(ButtonStyle.more);
			//MoreOperationButton.setText("更多操作");
			MoreOperationButton.setFont(new Font("黑体",Font.BOLD,20));
			MoreOperationButton.setBackground(new Color(143,194,106));
			//MoreOperationButton.setBorder(null);
			MoreOperationButton.setVisible(true);
			MoreOperationButton.setFocusPainted(false);
			MoreOperationButton.setForeground(new Color(143,194,106) );  
			Gb.gridwidth=1;
			WPanel.add(MoreOperationButton,Gb);
			
			ExitButton=new OperateButton(ButtonStyle.sighOut);
			ExitButton.setText("退出");
			ExitButton.setFont(new Font("黑体",Font.BOLD,20));
			ExitButton.setBackground(new Color(143,194,106));
			//ExitButton.setBorder(null);
			//JWaterLabel.setEnabled(false); 
			ExitButton.setVisible(true);
			ExitButton.setFocusPainted(false);
			ExitButton.setForeground(new Color(143,194,106));   //设置字体颜色
			Gb.gridwidth=0;
			WPanel.add(ExitButton,Gb);
			
			
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
