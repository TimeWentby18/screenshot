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

//添加到JTabbedPane中的图片面板，包含自己的按钮和事件
class PicPanel extends JPanel implements ActionListener {
    JButton copyButton, saveButton, closeButton, editButton;
    BufferedImage get;
    MainFrame mainFrame = MainFrame.getInstance();

    public PicPanel(BufferedImage get) {
        super(new BorderLayout());
        this.get = get;
        initPicPanel();
    }

    private void initPicPanel() {
        copyButton = new JButton(new ColorIcon(new Color(143, 194, 106), 100, 20));
        copyButton.setText("复制到剪贴板");
        setStyle(copyButton);
        saveButton = new JButton(new ColorIcon(new Color(143, 194, 106), 60, 20));
        saveButton.setText("保存");
        setStyle(saveButton);
        closeButton = new JButton(new ColorIcon(new Color(143, 194, 106), 60, 20));
        closeButton.setText("放弃");
        setStyle(closeButton);
        editButton = new JButton(new ColorIcon(new Color(143, 194, 106), 80, 20));
        editButton.setText("图片编辑");
        setStyle(editButton);
        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 1, 0));
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
        button.setFont(new Font("幼圆", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setFocusable(false);
        button.setBackground(new Color(143, 194, 106));
        button.setForeground(Color.white);
        button.setMargin(new Insets(0, 0, 0, 0));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source.equals(copyButton)) {
            doCopy(get);
        } else if (source.equals(saveButton)) {
            doSave(get);
        } else if (source.equals(closeButton)) {
            doClose(this);
        } else if (source.equals(editButton)) {
            doEdit(get);
        }
    }

    //复制到剪切板
    void doCopy(BufferedImage image) {
        if (image == null) {
            JOptionPane.showMessageDialog(this, "复制的图片不存在！", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Transferable content = new Transferable() {
            @Override
            public DataFlavor[] getTransferDataFlavors() {
                return new DataFlavor[]{DataFlavor.imageFlavor};
            }

            @Override
            public boolean isDataFlavorSupported(DataFlavor flavor) {
                return DataFlavor.imageFlavor.equals(flavor);
            }

            @Override
            public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
                if (isDataFlavorSupported(flavor)) {
                    return image;
                }
                throw new UnsupportedFlavorException(flavor);
            }
        };
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(content, null);
        JLabel success = new JLabel("<html><h1 style=\"color:rgb(143,194,106)\">成功复制到剪切板(*^_^*)</h1></html>");
        JOptionPane.showMessageDialog(this, success, null, JOptionPane.PLAIN_MESSAGE);
    }

    //保存图片
    void doSave(BufferedImage image) {
        if (image == null) {
            JOptionPane.showMessageDialog(this, "保存的图片不能为空！", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }
        JFileChooser chooser = new JFileChooser(".");
        chooser.addChoosableFileFilter(new MyFilter.JPGFilter());
        chooser.addChoosableFileFilter(new MyFilter.PNGFilter());
        chooser.addChoosableFileFilter(new MyFilter.BMPFilter());
        chooser.addChoosableFileFilter(new MyFilter.GIFFilter());
        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File origin = chooser.getSelectedFile();
            String fileName = origin.toString().toLowerCase();
            String suffix = "PNG";  //默认格式为PNG
            FileFilter filter = chooser.getFileFilter();
            File finalFile = origin;
            if (filter instanceof MyFilter.JPGFilter) {
                if (!fileName.endsWith(".jpg")) {
                    fileName = fileName + ".jpg";
                    finalFile = new File(fileName);
                }
                suffix = "JPG";
            } else if (filter instanceof MyFilter.PNGFilter) {
                if (!fileName.endsWith(".png")) {
                    fileName = fileName + ".png";
                    finalFile = new File(fileName);
                }
                suffix = "PNG";
            } else if (filter instanceof MyFilter.BMPFilter) {
                if (!fileName.endsWith(".bmp")) {
                    fileName = fileName + ".bmp";
                    finalFile = new File(fileName);
                }
                suffix = "BMP";
            } else if (filter instanceof MyFilter.GIFFilter) {
                if (!fileName.endsWith(".gif")) {
                    fileName = fileName + ".gif";
                    finalFile = new File(fileName);
                }
                suffix = "GIF";
            } else {
                fileName = fileName + ".png";
                finalFile = new File(fileName);
            }
            try {
                if (ImageIO.write(image, suffix, finalFile)) {
                    JLabel success = new JLabel("<html><h1 style=\"color:rgb(143,194,106)\">图片保存成功(*^_^*)</h1></html>");
                    JOptionPane.showMessageDialog(MainFrame.getInstance(), success, null, JOptionPane.PLAIN_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(MainFrame.getInstance(), "保存失败！", "错误", JOptionPane.ERROR_MESSAGE);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //关闭
    void doClose(Component c) {
        mainFrame.getJtp().remove(c);
        System.gc();
    }

    //编辑图片(未实现)
    void doEdit(BufferedImage image) {
        doSave(image);
        mainFrame.setVisible(false);
        //在此调出编辑界面
    }
}