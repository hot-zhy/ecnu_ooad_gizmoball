package UI;

import Config.BtnMode;
import Config.Shape;
import Config.Tools;
import Method.BoardImplementation;
import Method.ButtonListener;
import Method.PhysicsContact;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;

import static Method.BoardImplementation.*;

/**
 * 棋盘面板绘制类，继承Jpanel
 * 实现游戏棋盘界面的静态绘制（布局模式下的棋盘界面）。包括背景网格的绘制、组件的
 * 摆放以及组件的旋转、放大、缩小、删除的界面显示效果。负责将用户在界面操作的信息（前
 * 端各个 Panel 的信息）传递给后端 BoardImpl 类并控制线程刷新。
 * 响应用户行为,将用户添加的物体加入List中
 */
public class BoardPanel extends JPanel {
    /**
     * 面板实现
     */
    private BoardImplementation board;
    /**
     * 屏幕尺寸
     */
    private int grid;
    /**
     * 期盼长度
     */
    private int length;
    /**
     * 线程
     */
    private GThread gThread;
    /**
     * 当前组件
     */
    private static PhysicsContact curGizmo;

    public BoardPanel(BoardImplementation newBoard) {
        board = newBoard;
        grid = getGridSize();
        board.setRowHeight(grid);
        board.newWorld();

        //监听对于游戏棋盘的鼠标点击事件
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                length = getMinLength();

                int x = (e.getX() - X0) / grid;
                int y = (e.getY() - Y0) / grid;
                int sizeRate = getSizeRate();

                setCurMode(getCurMode());
                curGizmo = board.getGizmo(x,y);

                //鼠标点击的是组件
                if(getCurMode() == BtnMode.Shape) {
                    //点击的是手指组件
                    if(ButtonListener.getShape() == Shape.Finger) {
                        ButtonListener.clear();
                    }
                    //点击的是球体组件
                    if(ButtonListener.getShape() == Shape.Ball)
                        sizeRate = 1;
                    //点击的是挡板或弯道组件
                    else if(ButtonListener.getShape() == Shape.Paddle || ButtonListener.getShape() == Shape.Track)
                        sizeRate = 2;

                    if(board.canAdd(x, y, sizeRate, ButtonListener.getShape())) {
                        //添加组件
                        if(ButtonListener.getShape() == Shape.Track) {
                            //轨道的左侧和右侧
                            PhysicsContact right = new PhysicsContact(x, y, sizeRate, ButtonListener.getShape(), ButtonListener.getImage());
                            PhysicsContact left = new PhysicsContact(x-1, y, sizeRate, ButtonListener.getShape(), ButtonListener.getImage());
                            board.addComponents(right);
                            board.addComponents(left);
                        }
                        else {
                            PhysicsContact temp = new PhysicsContact(x, y, sizeRate, ButtonListener.getShape(), ButtonListener.getImage());
                            board.addComponents(temp);
                        }
                    }
                }
                //鼠标点的工具
                else if(getCurMode() == BtnMode.Tool) {
                    if(ButtonListener.getTool() == Tools.Rotate) {
                        if(board.canRotate(x, y, curGizmo.getSizeRate(), curGizmo))
                            board.rotateGizmo(curGizmo);
                    }
                    else if(ButtonListener.getTool() == Tools.Remove) {
                        board.deleteGizmo(curGizmo);
                    }
                    else if(ButtonListener.getTool() == Tools.ZoomIn) {
                        board.zoomInGizmo(curGizmo);
                    }
                    else if(ButtonListener.getTool() == Tools.ZoomOut) {
                        board.zoomOutGizmo(curGizmo);
                    }
                }
                //更新屏幕
                updateScreen();
            }
        });

        //监听上下左右键盘事件，移动挡板
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int code = e.getKeyCode();

                if(isBuildMode()) return;

                int x = 0, y = 0;
                switch (code) {
                    case KeyEvent.VK_LEFT:
                        x = -5;
                        break;
                    case KeyEvent.VK_RIGHT:
                        x = 5;
                        break;
                    case KeyEvent.VK_UP:
                        y = 5;
                        break;
                    case KeyEvent.VK_DOWN:
                        y = -5;
                        break;
                }
                board.keyMove(x, y);
            }
        });

        addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if(board.isFocus()) requestFocus();
            }
        });
        //更新屏幕
        updateScreen();

    }


    /**
     * 绘制
     * @param g  the <code>Graphics</code> context in which to paint
     */
    public void paint(Graphics g) {
        Image image = new BufferedImage(getWidth(), getHeight(), Image.SCALE_DEFAULT);
        image.getGraphics().drawRect(0, 0, getWidth(), getHeight());
        length = getMinLength();

        //绘制背景
        Graphics2D graphics2D = (Graphics2D) image.getGraphics();
        super.setBackground(this.getBackground());
        graphics2D.setColor(this.getBackground());
        graphics2D.fill(new Rectangle(0, 0, getWidth(), getHeight()));

        //绘画横纵线
        drawTable(graphics2D);

        board.paintComponents(graphics2D, grid, length);

        g.clearRect(0, 0, getWidth(), getHeight());
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
    }

    //绘画横纵线
    public void drawTable(Graphics2D graphics2D) {
        graphics2D.setColor(Color.WHITE);
        grid = getGridSize();

        for(int i = 0; i <= LINECOUNT; i++) {
            Line2D row = new Line2D.Double(X0,Y0+grid*i,length,Y0+grid*i);
            Line2D col = new Line2D.Double(X0+grid*i,Y0,X0+grid*i,length);
            graphics2D.draw(row);
            graphics2D.draw(col);
        }
    }

    /**
     * 刷新界面
     */
    public void updateScreen() {
        repaint();
    }

    public void build() {
        if(isBuildMode()) {
            gThread.interrupt();
            board.updateComponents();
        }
        else {
            gThread = new GThread();
            gThread.start();
            this.requestFocus();
        }
    }

    public void newScene() {
        board.newScene();
        updateScreen();
    }

    public void saveScene(String fileName) {
        board.saveScene(fileName);
    }

    public void loadScene(String fileName) {
        board.loadScene(fileName);
        updateScreen();
    }

    public BoardImplementation getBoard() {
        return board;
    }

    public int getGridSize(){
        return getMinLength()/(LINECOUNT);
    }

    public int getMinLength(){
        return Math.min(getHeight(), getWidth());
    }

    class GThread extends Thread {
        @Override
        public void run() {
            while(!isBuildMode()) {
                //调用BoardImplementation中的setStep方法
                board.setStep();
                //更新面板
                updateScreen();
                try {
                    Thread.sleep((long) (getTimeStep() * 1000));
                } catch (InterruptedException e) {
                    break;
                }
            }
            //更新面板
            updateScreen();
        }
    }


}
