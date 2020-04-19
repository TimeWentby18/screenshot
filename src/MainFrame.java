import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainFrame extends JFrame implements ActionListener{
    private JPanel centerJPanel;
    private JPanel southJPanel;
    private JButton startButton, quitButton;
    private JLabel welcome;
    private JTabbedPane jtp;  //标签面板，显示多张截图预览
    private BufferedImage get;
    private int index = 0;  //图片索引

    private MainFrame() {
        super("截图工具");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); //使用系统界面风格
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.updateComponentTreeUI(this);
        //设置窗口属性
        this.setSize(550,480);
        this.setLocationRelativeTo(null);  //窗口居中显示
        this.setUndecorated(true);
        this.setOpacity(0.85f);  //半透明化
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponent(); //初始化组件
    }

    private void initComponent() {
        centerJPanel = new JPanel(new BorderLayout());
        southJPanel = new JPanel(new BorderLayout());
        jtp = new JTabbedPane(JTabbedPane.LEFT, JTabbedPane.SCROLL_TAB_LAYOUT);
        //中央面板
        JLabel title1 = new JLabel("截图预览区");
        title1.setFont(new Font("幼圆",Font.BOLD,20));
        title1.setForeground(new Color(119,119,119));
        JPanel titlePanel1 = new JPanel();
        titlePanel1.setBackground(new Color(200,200,200));
        titlePanel1.add(title1);
        welcome = new JLabel("欢迎使用(*^_^*)", JLabel.CENTER);
        welcome.setFont(new Font("幼圆",Font.BOLD,40));
        welcome.setForeground(new Color(143,194,106));
        centerJPanel.add(titlePanel1, BorderLayout.NORTH);
        centerJPanel.add(welcome, BorderLayout.CENTER);
        centerJPanel.setBackground(Color.white);
        //底部面板
        startButton = new JButton(new ColorIcon(new Color(143,194,106),120,30));
        startButton.setText("开始截图");
        startButton.setHorizontalTextPosition(SwingConstants.CENTER);
        startButton.setVerticalTextPosition(SwingConstants.CENTER);
        startButton.setMargin(new Insets(0,0,0,0));
        startButton.setFont(new Font("幼圆",Font.BOLD,20));
        startButton.setFocusPainted(false);
        startButton.setFocusable(false);
        startButton.setBackground(new Color(143,194,106));
        startButton.setForeground(Color.white);
        quitButton = new JButton(new ColorIcon(new Color(143,194,106),70,30));
        quitButton.setText("退出");
        quitButton.setHorizontalTextPosition(SwingConstants.CENTER);
        quitButton.setVerticalTextPosition(SwingConstants.CENTER);
        quitButton.setMargin(new Insets(0,0,0,0));
        quitButton.setFont(new Font("幼圆",Font.BOLD,20));
        quitButton.setFocusPainted(false);
        quitButton.setFocusable(false);
        quitButton.setBackground(new Color(143,194,106));
        quitButton.setForeground(Color.white);
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,50,0));
        buttonPanel.setBackground(new Color(200,200,200));
        buttonPanel.add(startButton);
        buttonPanel.add(quitButton);
        southJPanel.setBackground(new Color(200,200,200));
        southJPanel.add(buttonPanel, BorderLayout.CENTER);
        southJPanel.setBorder(BorderFactory.createTitledBorder("功能区"));
        //添加到窗口
        this.getContentPane().add(centerJPanel, BorderLayout.CENTER);
        this.getContentPane().add(southJPanel, BorderLayout.SOUTH);
        //添加事件监听
        startButton.addActionListener(this);
        quitButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {  //绑定事件
        Object source = e.getSource();
        if (source == startButton) {
            doStart();
        } else if (source == quitButton) {
            System.exit(0);
        }
    }

    //用于给windows风格设置按钮背景颜色
    private class ColorIcon implements Icon {
        private Color color;
        private int width;
        private int height;

        public ColorIcon(Color color, int width, int height) {
            this.color = color;
            this.width = width;
            this.height = height;
        }

        public int getIconWidth() {
            return width;
        }

        public int getIconHeight() {
            return height;
        }

        public void paintIcon(Component c, Graphics g, int x, int y) {
            g.setColor(color);
            g.fillRect(x, y, width, height);
        }
    }

    //开始截图(部分实现)
    private void doStart() {
        try {
            this.setVisible(false);  //截图时隐藏窗口
            Robot robot = new Robot();  //用来获取屏幕图像的类
            Toolkit toolkit = Toolkit.getDefaultToolkit();  //获取屏幕宽高的类
            Thread.sleep(400);  //随眠400ms,避免截到主窗口
            Dimension di = toolkit.getScreenSize();
            Rectangle rec = new Rectangle(0,0,di.width,di.height);
            BufferedImage image = robot.createScreenCapture(rec);
            get = image.getSubimage(200,300,600,500);  //获取截图需要在这里实现
            update();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //更新主界面
    private void update() {
        this.setVisible(true);  //让窗口重新显示
        SwingUtilities.updateComponentTreeUI(this);  //更新组件
        if (get != null) {
            if (index == 0) {
                centerJPanel.remove(welcome);
                centerJPanel.add(jtp, BorderLayout.CENTER);
            }
            PicPanel picPanel = new PicPanel(get);
            jtp.addTab("图片"+(++index), picPanel);
            jtp.setSelectedComponent(picPanel);
        }
    }

    //添加到JTabbedPane中的图片面板，包含自己的按钮和事件
    private class PicPanel extends JPanel implements ActionListener {
        JButton copyButton,saveButton,closeButton,editButton;
        BufferedImage get;

        public PicPanel(BufferedImage get) {
            super(new BorderLayout());
            this.get = get;
            initPicPanel();
        }

        private void initPicPanel() {
            copyButton = new JButton(new ColorIcon(new Color(143,194,106),100,20));
            copyButton.setText("复制到剪贴板");
            setStyle(copyButton);
            saveButton = new JButton(new ColorIcon(new Color(143,194,106),60,20));
            saveButton.setText("保存");
            setStyle(saveButton);
            closeButton = new JButton(new ColorIcon(new Color(143,194,106),60,20));
            closeButton.setText("放弃");
            setStyle(closeButton);
            editButton = new JButton(new ColorIcon(new Color(143,194,106),80,20));
            editButton.setText("图片编辑");
            setStyle(editButton);
            JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,1,0));
            southPanel.add(copyButton);
            southPanel.add(saveButton);
            southPanel.add(closeButton);
            southPanel.add(editButton);
            JLabel icon = new JLabel(new ImageIcon(get));
            this.add(new JScrollPane(icon), BorderLayout.CENTER);
            this.add(southPanel, BorderLayout.SOUTH);
            copyButton.addActionListener(this);
            saveButton.addActionListener(this);
            closeButton.addActionListener(this);
            editButton.addActionListener(this);
        }

        //设置按钮样式
        private void setStyle(JButton button) {
            button.setHorizontalTextPosition(SwingConstants.CENTER);
            button.setVerticalTextPosition(SwingConstants.CENTER);
            button.setFont(new Font("幼圆",Font.BOLD,14));
            button.setFocusPainted(false);
            button.setFocusable(false);
            button.setBackground(new Color(143,194,106));
            button.setForeground(Color.white);
            button.setMargin(new Insets(0,0,0,0));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();
            if(source.equals(copyButton)) {
                doCopy(get);
            } else if(source.equals(saveButton)) {
                try {
                    doSave(get);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            } else if(source.equals(closeButton)) {
                doClose(this);
            } else if(source.equals(editButton)) {
                doEdit(get);
            }
        }
    }

    //复制到剪切板
    private void doCopy(BufferedImage image) {
        if (image == null) {
            JOptionPane.showMessageDialog(this,"复制的图片不存在！","错误",JOptionPane.ERROR_MESSAGE);
            return;
        }
        Transferable content = new Transferable() {
            @Override
            public DataFlavor[] getTransferDataFlavors() {
                return new DataFlavor[] {DataFlavor.imageFlavor};
            }

            @Override
            public boolean isDataFlavorSupported(DataFlavor flavor) {
                return DataFlavor.imageFlavor.equals(flavor);
            }

            @Override
            public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
                if (isDataFlavorSupported(flavor)) {
                    return image;
                }
                throw new UnsupportedFlavorException(flavor);
            }
        };
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(content,null);
        JLabel success = new JLabel("<html><h1 style=\"color:rgb(143,194,106)\">成功复制到剪切板(*^_^*)</h1></html>");
        JOptionPane.showMessageDialog(this,success,null,JOptionPane.PLAIN_MESSAGE);
    }

    //保存图片(未实现)
    private void doSave(BufferedImage image) throws IOException {
        if (image == null) {
            JOptionPane.showMessageDialog(this,"保存的图片不能为空！","错误",JOptionPane.ERROR_MESSAGE);
            return;
        }
        JFileChooser chooser = new JFileChooser(".");
        chooser.addChoosableFileFilter(new JPGFilter());
        chooser.addChoosableFileFilter(new PNGFilter());
        chooser.addChoosableFileFilter(new BMPFilter());
        chooser.addChoosableFileFilter(new GIFFilter());
        if(chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File origin = chooser.getSelectedFile();
            String fileName = origin.toString().toLowerCase();
            String suffix = "PNG";  //默认格式为PNG
            FileFilter filter = chooser.getFileFilter();
            File finalFile = null;
            if (filter instanceof JPGFilter) {
                if (!fileName.endsWith(".jpg")) {
                    fileName = fileName + ".jpg";
                    finalFile = new File(fileName);
                } else {
                    finalFile = origin;
                }
                suffix = "JPG";
            } else if (filter instanceof PNGFilter) {
                if (!fileName.endsWith(".png")) {
                    fileName = fileName + ".png";
                    finalFile = new File(fileName);
                } else {
                    finalFile = origin;
                }
                suffix = "PNG";
            } else if (filter instanceof BMPFilter) {
                if (!fileName.endsWith(".bmp")) {
                    fileName = fileName + ".bmp";
                    finalFile = new File(fileName);
                } else {
                    finalFile = origin;
                }
                suffix = "BMP";
            } else if (filter instanceof GIFFilter) {
                if (!fileName.endsWith(".gif")) {
                    fileName = fileName + ".gif";
                    finalFile = new File(fileName);
                } else {
                    finalFile = origin;
                }
                suffix = "GIF";
            } else {
                fileName = fileName + ".png";
                finalFile = new File(fileName);
            }
            if (ImageIO.write(image,suffix,finalFile)) {
                JLabel success = new JLabel("<html><h1 style=\"color:rgb(143,194,106)\">保存成功(*^_^*)</h1></html>");
                JOptionPane.showMessageDialog(this,success,null, JOptionPane.PLAIN_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,"保存失败！","错误",JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    //关闭
    private void doClose(Component c) {
        jtp.remove(c);
        c = null;
        System.gc();
    }

    //编辑图片(未实现)
    private void doEdit(BufferedImage get) {
        System.out.println("doEdit()");
    }

    //JPG过滤器
    private static class JPGFilter extends FileFilter {

        @Override
        public boolean accept(File f) {
            return (f.isDirectory() && f.toString().toLowerCase().endsWith(".jpg"));
        }

        @Override
        public String getDescription() {
            return "*.JPG(JPG图像)";
        }
    }

    //PNG过滤器
    private static class PNGFilter extends FileFilter {

        @Override
        public boolean accept(File f) {
            return (f.isDirectory() && f.toString().toLowerCase().endsWith(".png"));
        }

        @Override
        public String getDescription() {
            return "*.PNG(PNG图像)";
        }
    }

    //BMP过滤器
    private static class BMPFilter extends FileFilter {

        @Override
        public boolean accept(File f) {
            return (f.isDirectory() && f.toString().toLowerCase().endsWith(".bmp"));
        }

        @Override
        public String getDescription() {
            return "*.BMP(BMP图像)";
        }
    }

    //GIF过滤器
    private static class GIFFilter extends FileFilter {

        @Override
        public boolean accept(File f) {
            return (f.isDirectory() && f.toString().toLowerCase().endsWith(".gif"));
        }

        @Override
        public String getDescription() {
            return "*.GIF(GIF图像)";
        }
    }

    //主函数
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new);
    }
}
