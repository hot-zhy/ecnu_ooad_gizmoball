package Config;

import javax.swing.*;

/**
 * 组件和工具的图片数据
 */
public class Data {
    //球
    public static ImageIcon ballIcon = new ImageIcon(Data.class.getResource("/images/ball.png"));
    //圆形
    public static ImageIcon circleIcon = new ImageIcon(Data.class.getResource("/images/circle.png"));
    //漩涡吸收器
    public static ImageIcon absorberIcon = new ImageIcon(Data.class.getResource("/images/black_hole.png"));
    //矩形
    public static ImageIcon squareIcon = new ImageIcon(Data.class.getResource("/images/rectangle.png"));
    //三角形
    public static ImageIcon triangleIcon = new ImageIcon(Data.class.getResource("/images/triangle.png"));
    //垂直的轨道
    public static ImageIcon trackIcon = new ImageIcon(Data.class.getResource("/images/rail.png"));
    //水平可移动挡板
    public static ImageIcon paddleIcon = new ImageIcon(Data.class.getResource("/images/left_rold.png"));
    //控制焦点
    public static ImageIcon fingerIcon = new ImageIcon(Data.class.getResource("/images/finger.png"));
    //四分之一圆的轨道
    public static ImageIcon cornerIcon = new ImageIcon(Data.class.getResource("/images/quarter_circle.png"));

    //删除
    public static ImageIcon removeIcon = new ImageIcon(Data.class.getResource("/images/delete.png"));
    //旋转
    public static ImageIcon rotateIcon = new ImageIcon(Data.class.getResource("/images/rotate_left.png"));
    //放大
    public static ImageIcon zoomInIcon = new ImageIcon(Data.class.getResource("/images/zoom_out.png"));
    //缩小
    public static ImageIcon zoomOutIcon = new ImageIcon(Data.class.getResource("/images/zoom_in.png"));

    //球的个数
    public static int ballCount = 0;

}
