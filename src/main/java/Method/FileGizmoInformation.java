package Method;

import Config.Shape;

import java.awt.*;
import java.io.Serializable;

/**
 * 将 Gizmo 组件中需要保存的属性信息简化和提取出来，便于后续文件的读写
 */
public class FileGizmoInformation implements Serializable {
    private int x;                          //x坐标
    private int y;                          //y坐标
    private int sizeRate = 1;               //缩放比例
    private Shape shape;                    //Shape类型
    private Image image;                    //图标
    private double angle = 0*Math.PI/180;   //旋转角度

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setSizeRate(int sizeRate) {
        this.sizeRate = sizeRate;
    }

    public int getSizeRate() {
        return sizeRate;
    }

    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public double getAngle() {
        return this.angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

}
