import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.Rectangle;
import java.awt.*;
public class CutImg extends JPanel implements MouseListener, MouseMotionListener{
    private JFrame jf;
    private BufferedImage img;
    private int width, height;
    private int x1, y1, x2, y2; //表示选中的框对角的两个个点
    private boolean sure = false; //是否显示钩子
    private Cursor co = new Cursor(Cursor.HAND_CURSOR); //调用temp类下，鼠标状态为手部状态
    private States states = new States();
    private int currentState = states.DRAG_DEFAULT;
    private Rectangle[] positions = new Rectangle[8];
    private Cursor[] cursors = new Cursor[8];
    private Rectangle selected;
    private Point preP, curP;

    public CutImg (JFrame jf, BufferedImage img, int width, int height) {
        this.jf = jf;
        this.img = img;
        this.width = width;
        this.height = height;
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    public void setPoint() {
        //设置各个方框的范围
        int x = (x1+x2)/2;
        int y = (y1+y2)/2;
        int xmax = Math.max(x1, x2);
        int ymax = Math.max(y1, y2);
        int xmin = Math.min(x1, x2);
        int ymin = Math.min(y1, y2);
        positions[0] = new Rectangle(xmin-5, ymin-5, 10, 10); //左上角方块
        cursors[0] = new Cursor(Cursor.NW_RESIZE_CURSOR);
        positions[1] = new Rectangle(x-5, ymin-5, 10, 10);
        cursors[1] = new Cursor(Cursor.N_RESIZE_CURSOR);
        positions[2] = new Rectangle(xmax-5, ymin-5, 10, 10);
        cursors[2] = new Cursor(Cursor.NE_RESIZE_CURSOR);
        positions[3] = new Rectangle(xmin-5, y-5, 10, 10);
        cursors[3] = new Cursor(Cursor.W_RESIZE_CURSOR);
        positions[4] = new Rectangle(xmax-5, y-5, 10, 10);
        cursors[4] = new Cursor(Cursor.E_RESIZE_CURSOR);
        positions[5] = new Rectangle(xmin-5, ymax-5, 10, 10);
        cursors[5] = new Cursor(Cursor.SW_RESIZE_CURSOR);
        positions[6] = new Rectangle(x-5, ymax-5, 10, 10);
        cursors[6] = new Cursor(Cursor.S_RESIZE_CURSOR);
        positions[7] = new Rectangle(xmax-5, ymax-5, 10, 10);
        cursors[7] = new Cursor(Cursor.SE_RESIZE_CURSOR);
        selected = new Rectangle(xmin, ymin, Math.abs(x2-x1)-5, Math.abs(y2-y1)-5);
    }


    @Override
    public void paintComponent(Graphics g) {
        //绘制截屏窗口
        int x = (x1+x2)/2;
        int y = (y1+y2)/2;
        g.drawImage(img, 0, 0, width, height, this); //将图片绘制在窗口里面，图片来自于调用temp实例
        g.setColor(Color.GREEN);
        g.drawLine(x1, y1, x2, y1);
        g.drawLine(x1, y2, x2, y2);
        g.drawLine(x1, y1, x1, y2);
        g.drawLine(x2, y1, x2, y2);
        g.fillRect(x1-2, y1-2, 5, 5);
        g.fillRect(x-2, y1-2, 5, 5);
        g.fillRect(x2-2, y1-2, 5, 5);
        g.fillRect(x1-2, y-2, 5, 5);
        g.fillRect(x2-2, y-2, 5, 5);
        g.fillRect(x1-2, y2-2, 5, 5);
        g.fillRect(x-2, y2-2, 5, 5);
        g.fillRect(x2-2, y2-2, 5, 5);
        setPoint();
        g.setColor(new Color(119,136,153,128));
        g.fillRect(0,0,x1,height);
        g.fillRect(x1, 0, x2-x1, y1);
        g.fillRect(x2, 0, width-x2, height);
        g.fillRect(x1, y2, x2-x1,height-y2);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getClickCount()==2){
            Point p=e.getPoint();
            if(selected.contains(p)){
                if(selected.x+selected.width<this.getWidth()&&selected.y+selected.height<this.getHeight()){
                    MainFrame.get = img.getSubimage(selected.x,selected.y,selected.width,selected.height);
                }else{
                    int wid=selected.width,het=selected.height;
                    if(selected.x+selected.width>=this.getWidth()){ //如果截取的屏幕大于指定范围，则改变宽度
                        wid=this.getWidth()-selected.x;
                    }
                    if(selected.y+selected.height>=this.getHeight()){
                        het=this.getHeight()-selected.y;
                    }
                    MainFrame.get=img.getSubimage(selected.x,selected.y,wid,het);
                }
                jf.dispose();
                MainFrame.getInstance().update();
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (selected.contains(e.getPoint())) {
            this.setCursor(new Cursor(Cursor.MOVE_CURSOR));
            currentState = states.DRAG_MOVE;
        } else {
            int i;
            for (i = 0; i < 8; i++) {
                if (positions[i].contains(e.getPoint())) {
                    currentState = i;
                    this.setCursor(cursors[i]);
                    break;
                }
            }
            if (i >= 8) {
                this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));  //一般状态下鼠标的状态为正常形状
                currentState = states.DRAG_DEFAULT;
            }

        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.isPopupTrigger()) {
            if(currentState == states.DRAG_MOVE) {
                x1 = 0;
                y1 = 0;
                x2 = 0;
                y2 = 0;
                repaint();
            } else {
                jf.dispose();
                MainFrame.getInstance().update();
            }
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) { //鼠标按下的时候
        preP = e.getPoint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        curP = e.getPoint();
        if (currentState == states.DRAG_MOVE) {
            x1 += (curP.x-preP.x);
            y1 += (curP.y-preP.y);
            x2 += (curP.x-preP.x);
            y2 += (curP.y-preP.y);
            preP = curP;
        } else if (currentState == states.DRAG_UP) {
            y1 += (curP.y-preP.y);
            preP.y = curP.y;
        } else if (currentState == states.DRAG_DOWN) {
            y2 += (curP.y-preP.y);
            preP.y = curP.y;
        } else if (currentState == states.DRAG_LEFT) {
            x1 += (curP.x-preP.x);
            preP.x = curP.x;
        } else if (currentState == states.DRAG_RIGHT) {
            x2 += (curP.x-preP.x);
            preP.x = curP.x;
        } else if (currentState == states.DRAG_UP_LEFT) {
            x1 += (curP.x-preP.x);
            y1 += (curP.y-preP.y);
            preP = curP;
        } else if (currentState == states.DRAG_UP_RIGHT) {
            x2 += (curP.x-preP.x);
            y1 += (curP.y-preP.y);
            preP = curP;
        } else if(currentState == states.DRAG_DOWN_LEFT) {
            x1 += (curP.x-preP.x);
            y2 += (curP.y-preP.y);
            preP = curP;
        } else if (currentState == states.DRAG_DOWN_RIGHT) {
            x2 += (curP.x-preP.x);
            y2 += (curP.y-preP.y);
            preP = curP;
        } else {
            x1 = preP.x;
            y1 = preP.y;
            x2 = curP.x;
            y2 = curP.y;
        }
        this.repaint();
    }
}