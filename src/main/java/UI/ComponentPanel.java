package UI;

import Config.BtnMode;
import Config.GButton;
import Method.ButtonListener;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static Config.Shape.*;


/**
 * 右侧组件面板，实现游戏界面右侧组件栏的绘制及其各个组件对应的功能。
 */
public class ComponentPanel extends JPanel {
    /**
     * 控制焦点
     */
    private GButton btnFinger = new GButton(Finger);
    /**
     * 球
     */
    private GButton btnBall = new GButton(Ball);
    /**
     * 漩涡
     */
    private GButton btnAbsorber = new GButton(Absorber);
    /**
     * 三角形
     */
    private GButton btnTriangle = new GButton(Triangle);
    /**
     * 圆形
     */
    private GButton btnCircle = new GButton(Circle);
    /**
     * 矩形
     */
    private GButton btnSquare = new GButton(Square);
    /**
     * 轨道
     */
    private GButton btnTrack = new GButton(Track);
    /**
     * 挡板
     */
    private GButton btnPaddle = new GButton(Paddle);
    /**
     * 弯道
     */
    private GButton btnCorner = new GButton(Corner);
    /**
     * 按钮组件列表
     */
    private List<GButton> components;
    private ButtonListener game = new ButtonListener();

    /**
     * 组件面板构造函数
     */
    public ComponentPanel() {
        //文字
        super.setBorder(new TitledBorder(new EtchedBorder(),"组件栏",TitledBorder.CENTER,TitledBorder.CENTER,new Font("微软雅黑",Font.BOLD,15)));

        //将按钮集成为list，便于绑定listener
        components = new ArrayList<>();
        components.add(btnFinger);
        components.add(btnBall);
        components.add(btnCircle);
        components.add(btnSquare);
        components.add(btnTriangle);
        components.add(btnPaddle);
        components.add(btnAbsorber);
        components.add(btnTrack);
        components.add(btnCorner);

        //向Panel中加入按钮
        for(GButton b : components) {
            super.add(b);
        }

        //bind Listener
        game.addListener(components, BtnMode.Shape);

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }
}
