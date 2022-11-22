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

import static Config.Tools.*;

//游戏右侧工具栏
public class ToolPanel extends JPanel {
    /**
     * 旋转
     */
    private GButton btnRotate = new GButton(Rotate);
    /**
     * 删除
     */
    private GButton btnRemove = new GButton(Remove);
    /**
     * 放大
     */
    private GButton btnZoomIn = new GButton(ZoomIn);
    /**
     * 缩小
     */
    private GButton btnZoomOut = new GButton(ZoomOut);

    private List<GButton> components;
    private ButtonListener game = new ButtonListener();

    public ToolPanel() {
        super.setBorder(new TitledBorder(new EtchedBorder(),"工具栏",TitledBorder.CENTER,TitledBorder.CENTER,new Font("微软雅黑",Font.BOLD,15)));

        //将按钮集成为list，便于绑定listener
        components = new ArrayList<>();
        components.add(btnRemove);
        components.add(btnRotate);
        components.add(btnZoomIn);
        components.add(btnZoomOut);

        for(GButton b : components) {
            super.add(b);
        }

        //bind Listener
        game.addListener(components, BtnMode.Tool);

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }
}
