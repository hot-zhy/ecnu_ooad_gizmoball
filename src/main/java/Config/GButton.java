package Config;

import javax.swing.*;
import java.awt.*;

import static Config.Data.*;

/**
 * 组件和工具的集合，这是放在界面上的按钮，继承JButton方便设置样式
 */
public class GButton extends JButton {
    //工具
    private Tools tool;
    //组件
    private Shape shape;
    //按钮的图片
    private Image image;
    //组件按钮的长宽
    private static final int sWID = 63;
    //工具按钮的长宽
    private static final int tWID = 65;

    /**
     * 构造组件按钮的函数
     * @param shape
     */
    public GButton(Shape shape) {
        this.shape = shape;
        switch (shape) {
            case Ball:
                ballIcon.setImage(ballIcon.getImage().getScaledInstance(sWID,sWID,Image.SCALE_DEFAULT));
                this.setImage(ballIcon.getImage());
                this.setIcon(ballIcon);
                break;
            case Circle:
                circleIcon.setImage(circleIcon.getImage().getScaledInstance(sWID,sWID,Image.SCALE_DEFAULT));
                this.setImage(circleIcon.getImage());
                this.setIcon(circleIcon);
                break;
            case Track:
                trackIcon.setImage(trackIcon.getImage().getScaledInstance(sWID,sWID,Image.SCALE_DEFAULT));
                this.setImage(trackIcon.getImage());
                this.setIcon(trackIcon);
                break;
            case Paddle:
                paddleIcon.setImage(paddleIcon.getImage().getScaledInstance(sWID,sWID,Image.SCALE_DEFAULT));
                this.setImage(paddleIcon.getImage());
                this.setIcon(paddleIcon);
                break;
            case Square:
                squareIcon.setImage(squareIcon.getImage().getScaledInstance(sWID,sWID,Image.SCALE_DEFAULT));
                this.setImage(squareIcon.getImage());
                this.setIcon(squareIcon);
                break;
            case Absorber:
                absorberIcon.setImage(absorberIcon.getImage().getScaledInstance(sWID,sWID,Image.SCALE_DEFAULT));
                this.setImage(absorberIcon.getImage());
                this.setIcon(absorberIcon);
                break;
            case Triangle:
                triangleIcon.setImage(triangleIcon.getImage().getScaledInstance(sWID,sWID,Image.SCALE_DEFAULT));
                this.setImage(triangleIcon.getImage());
                this.setIcon(triangleIcon);
                break;
            case Finger:
                fingerIcon.setImage(fingerIcon.getImage().getScaledInstance(sWID,sWID,Image.SCALE_DEFAULT));
                this.setImage(fingerIcon.getImage());
                this.setIcon(fingerIcon);
                break;
            case Corner:
                cornerIcon.setImage(cornerIcon.getImage().getScaledInstance(sWID,sWID,Image.SCALE_DEFAULT));
                this.setImage(cornerIcon.getImage());
                this.setIcon(cornerIcon);
            default:
        }
        this.setBorderPainted(false);       //按钮无边框
        this.setFocusPainted(true);         //默认选中
        this.setOpaque(false);              //按钮背景透明
        this.setContentAreaFilled(false);   //icon作为按钮样式
        this.setMargin(new Insets(0,0,0,0));    //填满整个button区域
    }

    /**
     * 构造工具按钮的函数
     * @param tool
     */
    public GButton(Tools tool) {
        this.tool = tool;
        switch (tool) {
            case Remove:
                removeIcon.setImage(removeIcon.getImage().getScaledInstance(tWID,tWID,Image.SCALE_DEFAULT));
                this.setImage(removeIcon.getImage());
                this.setIcon(removeIcon);
                break;
            case Rotate:
                rotateIcon.setImage(rotateIcon.getImage().getScaledInstance(tWID,tWID,Image.SCALE_DEFAULT));
                this.setImage(rotateIcon.getImage());
                this.setIcon(rotateIcon);
                break;
            case ZoomIn:
                zoomInIcon.setImage(zoomInIcon.getImage().getScaledInstance(tWID,tWID,Image.SCALE_DEFAULT));
                this.setImage(zoomInIcon.getImage());
                this.setIcon(zoomInIcon);
                break;
            case ZoomOut:
                zoomOutIcon.setImage(zoomOutIcon.getImage().getScaledInstance(tWID,tWID,Image.SCALE_DEFAULT));
                this.setImage(zoomOutIcon.getImage());
                this.setIcon(zoomOutIcon);
                break;
            default:
        }
        this.setBorderPainted(false);       //按钮无边框
        this.setFocusPainted(true);         //默认选中
        this.setOpaque(false);              //按钮背景透明
        this.setContentAreaFilled(false);   //icon作为按钮样式
        this.setMargin(new Insets(0,0,0,0));    //填满整个button区域
    }

    public Tools getTool() {
        return tool;
    }

    public Shape getShape() {
        return shape;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Image getImage() {
        return image;
    }
}
