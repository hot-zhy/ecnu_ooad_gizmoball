package Method;

import Config.BtnMode;
import Config.Shape;
import org.jbox2d.collision.AABB;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;


import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.List;

/**
 * 保存游戏棋盘中的物体信息列表，为实现文件操作做准备。对物体进行旋转、放大等操作，
 * 并将其画在JFrame窗口，实现布局模式。
 * 将BoardPanel添加的物体List画出来，并设置物理世界来模拟碰撞
 */
public class BoardImplementation {
    /**
     *  The world class manages all physics entities, dynamic simulation, and asynchronous queries. The world also contains efficient memory management facilities.
     */
    private World myWorld;
    /**
     * An axis-aligned bounding box.
     */
    private AABB myAABB;
    /**
     * A 2D column vector
     */
    private static final Vec2 gravity = new Vec2(0f,-10f); //重力
    /**
     * 物理世界与屏幕环境缩放比例
     */
    private static final int sizeRate = 1;
    /**
     * 模拟的时间步进
     */
    private static final float timeStep = 2.0f/300.0f;
    /**
     * 位置迭代次数
     */
    private static final int itera = 25;
    /**
     * 速度迭代次数
     */
    private static final int vItera = 25;
    /**
     * 行数
     */
    public static int LINECOUNT = 25;
    /**
     * 初始坐标
     */
    public static final int X0 = 0;
    public static final int Y0 = 0;

    /**
     * 单元格宽度
     */
    private int rowHeight = 30;
    /**
     * 当前按钮类型，是组件还是工具
     */
    public static BtnMode curMode;
    /**
     * 是否为设计布局模式
     */
    private static boolean buildMode = true;
    /**
     * 光标是否在棋盘中,是否在绘制棋盘
     */
    private boolean isFocus = true;
    /**
     * 组件列表
     */
    List<PhysicsContact> components = new ArrayList<>();

    /**
     * 新建物理世界，这个物理世界可以管理所有的物理实体，动态模拟，同步查询。这个世界还包含高效的内存管理设置
     */
    public void newWorld(){
        //打开设计模式
        buildMode = true;
        //新建包围盒
        myAABB=new AABB();
        //包围盒左上角顶点
        myAABB.lowerBound.set(-100.0f,-100.0f);
        //包围盒右下角顶点
        myAABB.upperBound.set(100.0f,100.0f);
        //为物理世界添加重力
        myWorld = new World(gravity);

        //设置行列数
        PhysicsContact.setLineCount(LINECOUNT);
        //为gizmo游戏设置物理世界
        PhysicsContact.setWorld(myWorld);
        //添加四边
        for (int i = 0; i <= 1; i++){
            for (int j = 0; j <= 1; j++)
                PhysicsContact.addSingleBoarder(i, j);
        }
    }

    /**
     * 在窗口绘制添加过的组件
     * @param g2D
     * @param grid
     * @param length
     */
    public void paintComponents(Graphics2D g2D, int grid, int length){
        //实时坐标
        double px, py;
        //相对棋盘坐标
        int x, y;
        //缩放比例
        int sizeRate;
        //当前组件图片
        Image curImg;
        this.rowHeight = grid;
        for (int i = 0; i < components.size(); i++){
            AffineTransform transform = (AffineTransform) g2D.getTransform().clone();
            PhysicsContact gizmo = components.get(i);
            if (gizmo.getBody().getUserData() == null) {
                myWorld.destroyBody(gizmo.getBody());
                continue;
            }
            x = gizmo.getX();
            y = gizmo.getY();
            curImg = gizmo.getImage();


            sizeRate = gizmo.getSizeRate();
/**
 * 设计模式
 */
            if (buildMode){
                px = Coordinate(x) + X0;
                py = Coordinate(y) + Y0;
                if (gizmo.getShape() == Shape.Triangle) {
                    g2D.setTransform(getTransform(px + 0.5 * sizeRate * rowHeight, py + 0.5 * sizeRate * rowHeight, -gizmo.getBody().getAngle(), g2D.getTransform()));
                }
            }
            else {
                Vec2 position = gizmo.getBody().getPosition();
                px = position.x / PhysicsContact.getLength() * LINECOUNT * grid + X0;
                py = position.y / PhysicsContact.getLength()  * LINECOUNT * grid;
                if (gizmo.getShape() != Shape.Ball) {    //ball
                    if (gizmo.getShape() == Shape.Paddle){
                        px -= gizmo.getSizeRate() * rowHeight / 2.0f;
                        py += 0.875f * rowHeight;
                    }
                    else if (gizmo.getShape() == Shape.Track){   //track
                        //if(gizmo.isDir()) px -= 0.875 * rowHeight;  //left
                        //else px -= 0.875 * rowHeight;   //right
                        px -= 0.875 * rowHeight;
                        py += rowHeight;
                    } else {
                        px -= gizmo.getSizeRate() * rowHeight / 2.0f;
                        py += gizmo.getSizeRate() * rowHeight / 2.0f;
                    }

                } else {
                    px -= rowHeight / 4.0f;
                    py += rowHeight / 4.0f;
                }
                //System.out.println(px+"  "+py);
                py = length - py;
                //System.out.println(py);

                if (gizmo.getShape() != Shape.Ball)
                    g2D.setTransform(getTransform(px + 0.5 * sizeRate * rowHeight, py + 0.5 * sizeRate * rowHeight, -gizmo.getBody().getAngle(), g2D.getTransform()));
            }

            sizeRate = gizmo.getSizeRate();

            if (gizmo.getShape() == Shape.Ball){
                g2D.drawImage(curImg, (int)px+5, (int)py+5, (int)(rowHeight/1.5), (int)(rowHeight/1.5), null);//*2
            }else if (gizmo.getShape() == Shape.Track){
                g2D.setColor(new Color(241, 162, 138));
                g2D.fill(paintPaddle(px, py, sizeRate));
            }else if (gizmo.getShape() == Shape.Paddle){
                g2D.drawImage(curImg, (int)px, (int)py, sizeRate * rowHeight, sizeRate * rowHeight, null);
            }
            else if (gizmo.getShape() == Shape.Corner) {
                g2D.drawImage(curImg, (int)px, (int)py - sizeRate*rowHeight, sizeRate * rowHeight, sizeRate * rowHeight, null);
            }
            else {
                g2D.drawImage(curImg, (int)px, (int)py, sizeRate * rowHeight, sizeRate * rowHeight, null);
            }
            g2D.setTransform(transform);
        }
    }

    /**
     * 在board中绘制挡板
     * @param x
     * @param y
     * @param sizeRate
     * @return
     */
    private RoundRectangle2D paintPaddle(double x, double y, int sizeRate) {
        double X, Y;
        double length = 0.25 * rowHeight;
        double weight = Coordinate(2);

        X = x + 0.75 * rowHeight;
        Y = y;
        RoundRectangle2D d = new RoundRectangle2D.Double(X, Y, length, weight, 0.5 * length, 0.5 * length);
        return d;
    }

    /**
     * 画面旋转组件：图形以点(x,y)为轴点，旋转angle弧度
     * @param x
     * @param y
     * @param angle
     * @param transform
     * @return
     */
    private AffineTransform getTransform(double x, double y, double angle, AffineTransform transform) {
        transform.rotate(angle, x, y);
        return transform;
    }

    public void rotateGizmo(PhysicsContact gizmo){
        if(gizmo!=null){
            gizmo.setAngle(gizmo.getAngle()+Math.PI/2);
            gizmo.updateBody();
        }
    }

    /**
     * 获取真实坐标
     * @param i
     * @return
     */
    private double Coordinate(double i)
    {
        return i * rowHeight;
    }

    /**
     * 获取物体
     * @param x
     * @param y
     * @return
     */
    public PhysicsContact getGizmo(int x, int y) {
        for (int i = 0; i < components.size(); i++) {
            PhysicsContact temp = components.get(i);
            int tempX = temp.getX();
            int tempY = temp.getY();
            int sizeRate = temp.getSizeRate();
            if (x >= tempX && x < tempX + sizeRate && y >= tempY && y < tempY + sizeRate)
                return temp;
        }
        return null;
    }

    /**
     * 放大物体，先销毁原来的，再创建新的
     * @param gizmo
     */
    public void zoomInGizmo(PhysicsContact gizmo){
        if (gizmo != null){
            int sizeRate = gizmo.getSizeRate();
            gizmo.setSizeRate(sizeRate + 1);
            gizmo.updateBody();
        }
    }

    /**
     * 缩小物体
     * @param gizmo
     */
    public void zoomOutGizmo(PhysicsContact gizmo){
        if (gizmo != null){
            int sizeRate = gizmo.getSizeRate();
            gizmo.setSizeRate(sizeRate - 1);
            gizmo.updateBody();
        }
    }

    /**
     * 旋转物体
     * @param gizmo
     */


    /**
     * 删除物体
     * @param gizmo
     */
    public void deleteGizmo(PhysicsContact gizmo){
        if (gizmo != null){
            myWorld.destroyBody(gizmo.getBody());
            components.remove(gizmo);
        }
    }

    /**
     * 移动挡板
     * @param dx
     * @param dy
     */
    public void keyMove(int dx, int dy){
        for (PhysicsContact gizmo : components) {
            if (gizmo.getShape() == Shape.Paddle ) {
                Vec2 position = gizmo.getBody().getPosition();
                gizmo.getBody().setTransform(new Vec2(position.x + dx, position.y + dy), 0);
            }
        }
    }

    /**
     * 将新的物体加入文件列表
     * @param newGizmo
     */
    public void addComponents(PhysicsContact newGizmo){
        components.add(newGizmo);
    }

    /**
     * 新建游戏，清空所有刚体组件
     */
    public void newScene() {
        newWorld();
        components.clear();
    }

    /**
     * 保存游戏
     * @param fileName
     */
    public void saveScene(String fileName) {
        FileIO.save(components, fileName);
    }

    /**
     * 读取游戏
     * @param fileName
     */
    public void loadScene(String fileName) {
        World nowWorld = myWorld;
        newWorld();
        List<PhysicsContact> list = FileIO.load(fileName);
        if (components == null) {
            myWorld = nowWorld;
        } else {
            components = list;
        }
    }

    /**
     * 判断能否在该处添加物体，检测(x,y)处有无物体
     * @param x
     * @param y
     * @param size
     * @param shape
     * @return
     */
    public boolean canAdd(int x, int y, int size,Shape shape){
        int j=y;
        int bound=y+size;
        //弯轨由于实现方法时上移了图片，所以检测时对应上移
        if(shape == Shape.Corner){
             j=y-size;
             bound=y;
        }
        for (int i = x; i < x + size; i++) {
            for ( ; j < bound; j++) {
                if (getGizmo(i, j) != null) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 判断是否可以旋转
     * @param x
     * @param y
     * @param size
     * @param curGizmo
     * @return
     */
    public boolean canRotate(int x, int y, int size, PhysicsContact curGizmo){
        int dx, dy;
        for (PhysicsContact giz : components){
            dx = giz.getX();
            dy = giz.getY();
            if (dx >= x && dy <= y && dx <= x + size && dy >= y - size && giz.getShape() != curGizmo.getShape()){
                return false;
            }
        }
        return true;
    }

    public boolean isFocus() {
        return isFocus;
    }

    public void setFocus(boolean focus) {
        isFocus = focus;
    }

    public static int getLineCount() {
        return LINECOUNT;
    }

    public static void setBuildMode(boolean buildMode) {
        BoardImplementation.buildMode = buildMode;
    }

    public static boolean isBuildMode() {
        return buildMode;
    }

    public static BtnMode getCurMode() {
        return curMode;
    }

    public static void setCurMode(BtnMode curMode) {
        BoardImplementation.curMode = curMode;
    }

    public void setRowHeight(int rowHeight) {
        this.rowHeight = rowHeight;
    }

    public static int getSizeRate() {
        return sizeRate;
    }

    public void setStep(){
        myWorld.step(timeStep, vItera, itera);
    }

    public static float getTimeStep() {
        return timeStep;
    }

    public void updateComponents(){
        for (PhysicsContact gizmo : components) {
            gizmo.updateBody();
        }
    }
}
