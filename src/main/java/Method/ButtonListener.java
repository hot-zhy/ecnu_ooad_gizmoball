package Method;

import Config.GButton;
import Config.BtnMode;
import Config.Shape;
import Config.Tools;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * 组件栏和工具栏的按钮监听
 */
public class ButtonListener {
    private static Shape shape;
    private static BtnMode curMode;
    private static Tools tool;
    private static Image image;

    /**
     * 组件监听器
     * @param components
     * @param mode
     */
    public void addListener(List<GButton> components, BtnMode mode) {
        for (GButton button : components) {
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (BoardImplementation.isBuildMode()) {
                        curMode = mode;
                        BoardImplementation.setCurMode(curMode);
                        shape = button.getShape();
                        tool = button.getTool();
                        image = button.getImage();
                    }
                }
            });
        }
    }

    public static void clear() {
        shape = null;
        curMode = null;
        tool = null;
        image = null;
    }

    public static Shape getShape() {
        return shape;
    }

    public static Image getImage() {
        return image;
    }

    public static Tools getTool() {
        return tool;
    }
}
