import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class MainFrame extends JFrame implements ActionListener {
    private static MainFrame mainFrame = null;
    private JPanel centerJPanel;  //整体中央面板
    private JPanel southJPanel;   //整体底部面板
    private JButton startButton, quitButton; //开始和退出按钮
    private JLabel welcome;   //初始的欢迎标签
    private JTabbedPane jtp;  //标签面板，显示多张截图预览
    private BufferedImage get;  //获取的截屏图像(原始图像)
    private int index = 0;   //图片索引，每截一张图就加一

    static MainFrame getInstance() {
        return mainFrame;
    }

    JTabbedPane getJtp() {
        return jtp;
    }

    private MainFrame() {
        super("截图工具");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); //使用系统界面风格
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.updateComponentTreeUI(this);
        //设置窗口属性
        this.setSize(550, 480);
        this.setLocationRelativeTo(null);  //窗口居中显示
        this.setUndecorated(true);
        this.setOpacity(0.85f);  //半透明化
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponent(); //初始化组件
        mainFrame = this;
    }

    private void initComponent() {
        centerJPanel = new JPanel(new BorderLayout());
        southJPanel = new JPanel(new BorderLayout());
        jtp = new JTabbedPane(JTabbedPane.LEFT, JTabbedPane.SCROLL_TAB_LAYOUT);
        //中央面板
        JLabel title1 = new JLabel("截图预览区");
        title1.setFont(new Font("幼圆", Font.BOLD, 20));
        title1.setForeground(new Color(119, 119, 119));
        JPanel titlePanel1 = new JPanel();
        titlePanel1.setBackground(new Color(200, 200, 200));
        titlePanel1.add(title1);
        welcome = new JLabel("欢迎使用(*^_^*)", JLabel.CENTER);
        welcome.setFont(new Font("幼圆", Font.BOLD, 40));
        welcome.setForeground(new Color(143, 194, 106));
        centerJPanel.add(titlePanel1, BorderLayout.NORTH);
        centerJPanel.add(welcome, BorderLayout.CENTER);
        centerJPanel.setBackground(Color.white);
        //底部面板
        startButton = new JButton(new ColorIcon(new Color(143, 194, 106), 120, 30));
        startButton.setText("开始截图");
        startButton.setHorizontalTextPosition(SwingConstants.CENTER);
        startButton.setVerticalTextPosition(SwingConstants.CENTER);
        startButton.setMargin(new Insets(0, 0, 0, 0));
        startButton.setFont(new Font("幼圆", Font.BOLD, 20));
        startButton.setFocusPainted(false);
        startButton.setFocusable(false);
        startButton.setBackground(new Color(143, 194, 106));
        startButton.setForeground(Color.white);
        quitButton = new JButton(new ColorIcon(new Color(143, 194, 106), 70, 30));
        quitButton.setText("退出");
        quitButton.setHorizontalTextPosition(SwingConstants.CENTER);
        quitButton.setVerticalTextPosition(SwingConstants.CENTER);
        quitButton.setMargin(new Insets(0, 0, 0, 0));
        quitButton.setFont(new Font("幼圆", Font.BOLD, 20));
        quitButton.setFocusPainted(false);
        quitButton.setFocusable(false);
        quitButton.setBackground(new Color(143, 194, 106));
        quitButton.setForeground(Color.white);
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 0));
        buttonPanel.setBackground(new Color(200, 200, 200));
        buttonPanel.add(startButton);
        buttonPanel.add(quitButton);
        southJPanel.setBackground(new Color(200, 200, 200));
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
    public void actionPerformed(ActionEvent e) {   //绑定事件
        Object source = e.getSource();
        if (source == startButton) {
            doStart();
        } else if (source == quitButton) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        //创建线程
        SwingUtilities.invokeLater(MainFrame::new);
    }

    //开始截图(部分实现)
    private void doStart() {
        try {
            this.setVisible(false);  //截图时隐藏窗口
            Robot robot = new Robot();  //用来获取屏幕图像的类
            Toolkit toolkit = Toolkit.getDefaultToolkit();  //获取屏幕宽高的类
            Thread.sleep(400);  //随眠400ms,避免截到主窗口
            Dimension di = toolkit.getScreenSize();
            Rectangle rec = new Rectangle(0, 0, di.width, di.height);
            BufferedImage image = robot.createScreenCapture(rec);
            get = image.getSubimage(200, 300, 600, 500);  //获取截图需要在这里实现
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
            jtp.addTab("图片" + (++index), picPanel);
            jtp.setSelectedComponent(picPanel);
        }
    }
}
