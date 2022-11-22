package Method;

import Config.Shape;
import Config.Data;
import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.jbox2d.dynamics.contacts.Contact;

import java.awt.*;

/**
 * 在物理世界中添加刚体，并监听其碰撞，更新物体
 */
public class PhysicsContact {
    /**
     * x坐标
     */
    private int x;
    /**
     * y坐标
     */
    private int y;
    /**
     * 物理世界和屏幕的缩放比例
     */
    private int sizeRate = 1;
    /**
     * 刚体组件，对应的刚体描述类是BodyDef
     */
    private Body body;
    /**
     * 组件形状
     */
    private Shape shape;
    /**
     * 组件的图标
     */
    private Image image;
    /**
     * 旋转角度
     */
    private double angle = 0*Math.PI/180;
    /**
     * 行数
     */
    private static int LINECOUNT;
    /**
     * 一个格子的单位长度
     */
    private final static int size = 30;
    /**
     * 物理世界,物理世界就是物体、形状与约束相互作用的集合，可以在物理世界中创建和删除所需的刚体或关节以实现所需要的物理模拟
     */
    private static World myWorld;

    /**
     * gizmo构造函数，同时创建刚体组件
     * @param x
     * @param y
     * @param sizeRate
     * @param shape
     * @param image
     */
    public PhysicsContact(int x, int y, int sizeRate, Shape shape, Image image){
        this.x = x;
        this.y = y;
        this.sizeRate = sizeRate;
        this.shape = shape;
        this.image = image;
        createBody();
    }

    /**
     * 创建刚体组件
     */
    public void createBody(){
        //判断要创建刚体的类型
        switch (shape){
            case Ball:
                addBall(x, y);
                break;
            case Absorber:
                addSquare(x, y, sizeRate);
                break;
            case Triangle:
                addTriangle(x, y, sizeRate);
                break;
            case Circle:
                addCircle(x, y, sizeRate);
                break;
            case Square:
                addSquare(x, y, sizeRate);
                break;
            case Paddle:
                addPaddle(x, y, sizeRate);
                break;
            case Track:
                addTrack(x, y);
                break;
            case Corner:
                addSquare(x, y, sizeRate);
                break;

        }
        //设置用户数据
        body.setUserData(shape);
    }

    /**
     * 添加四条边界
     * @param x
     * @param y
     */
    public static void addSingleBoarder(int x, int y) {
        //BodyDef: A body definition holds all the data needed to construct a rigid body.
        BodyDef def = new BodyDef();
        def.type = BodyType.STATIC;
        def.position.set(x * size * LINECOUNT, y * size * LINECOUNT);
        Body body = myWorld.createBody(def);
        //A convex polygon shape. Polygons have a maximum number of vertices equal to _maxPolygonVertices. In most cases you should not need many vertices for a convex polygon.
        PolygonShape box = new PolygonShape();
        if (y == x) {
            box.setAsBox(size * LINECOUNT, 0);
        } else {
            box.setAsBox(0, size * LINECOUNT);
        }
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = box;
        // The restitution (elasticity) usually in the range [0,1].
        fixtureDef.restitution = 1;
        //底边吸收
        if(x == 0 && y == 0) {
            fixtureDef.restitution = 0;
            body.setUserData(Shape.Bound);
        }
        body.createFixture(fixtureDef);
    }

    /**
     * 添加刚体球
     * @param x
     * @param y
     */
    public void addBall(int x,int y){
        Data.ballCount++;
        y = 25 - y;
        float r = size / 2.0f;

        //新建刚体描述
        BodyDef bd=new BodyDef();
        //设置刚体类型为动态
        bd.type = BodyType.DYNAMIC;
        //设置刚体位置
        bd.position=new Vec2((x) * size + r / 2.0f, (y) * size - r / 2.0f);
        //允许休眠
        bd.allowSleep=true;
        //为刚体挂载描述信息
        body = myWorld.createBody(bd);


        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(r/1.5f);
        //新建刚体物理描述，描述摩擦系数、弹性系数、密度
        FixtureDef fixtureDef = new FixtureDef();
        //设置形状
        fixtureDef.shape = circleShape;
        //设置密度
        fixtureDef.density = 1;
        //设置弹性系数
        fixtureDef.restitution = 1;
        body.createFixture(fixtureDef);
        body.setLinearVelocity(new Vec2(0,-10000));
        body.setGravityScale(10);
    }

    /**
     * 添加矩形/弯轨道/黑洞刚体
     * @param x
     * @param y
     * @param sizeRate
     */
    public void addSquare(int x, int y, int sizeRate) {
        //从物理世界到屏幕大小的转化
        int a = sizeRate * size;
        y = 25 - y;
        BodyDef def = new BodyDef();
        //刚体类型为静态
        def.type = BodyType.STATIC;
        def.gravityScale = 0;
        def.position.set(x * size + a / 2.0f, y * size - a / 2.0f);
        def.userData = image;
        body = myWorld.createBody(def);
        //多边形
        PolygonShape box = new PolygonShape();
        //将多边形设置为矩形
        box.setAsBox(a / 2.0f, a / 2.0f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = box;
        fixtureDef.density = 50;
        fixtureDef.friction=5;
        fixtureDef.restitution = 1;
        if (shape == Shape.Absorber || shape == Shape.Corner)
            //黑洞的弹性系数为0，完全非弹性碰撞，不会反弹，为1时为完全弹性碰撞
            fixtureDef.restitution = 0;
        body.createFixture(fixtureDef);
    }

    /**
     * 添加圆形刚体组件
     * @param x
     * @param y
     * @param sizeRate
     */
    private void addCircle(int x, int y, int sizeRate) {
        y = 25 - y;
        int r = sizeRate * size;
        BodyDef def = new BodyDef();
        def.type = BodyType.STATIC;
        def.angle = (float) - angle;
        def.gravityScale = 0;
        def.position.set(x * size + r / 2.0f, y * size - r / 2.0f);
        body = myWorld.createBody(def);
        CircleShape circle = new CircleShape();
        circle.setRadius(r / 2.0f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 50;
        fixtureDef.restitution = 1;
        fixtureDef.friction=5;   //摩擦
        body.createFixture(fixtureDef);
    }

    /**
     * 添加三角形刚体组件
     * @param x
     * @param y
     * @param sizeRate
     */
    public void addTriangle(int x, int y, int sizeRate) {
        y = 25 - y;
        BodyDef def = new BodyDef();
        def.type = BodyType.STATIC;
        def.gravityScale = 0;
        int a = sizeRate * size;
        def.position.set(x * size + a / 2.0f, y * size - a / 2.0f);
        def.angle = (float) - angle;
        body = myWorld.createBody(def);
        PolygonShape shape = new PolygonShape();
        float r = a / 2.0f;
        shape.set(new Vec2[]{new Vec2(-r, -r), new Vec2(-r, r), new Vec2(r, -r)}, 3);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 50;
        fixtureDef.restitution = 1;
        fixtureDef.friction=5;   //摩擦
        body.createFixture(fixtureDef);
    }

    /**
     * 添加挡板刚体组件
     * @param x
     * @param y
     * @param sizeRate
     */
    public void addPaddle(int x, int y, int sizeRate){
        y = 25 - y;
        BodyDef def = new BodyDef();
        def.type = BodyType.STATIC;
        int a = sizeRate * size;
        def.position.set(x * size + a/ 2.0f, y * size - size * 0.875f);
        body = myWorld.createBody(def);
        //多边形
        PolygonShape shape = new PolygonShape();
        //多边形绘制为矩形
        shape.setAsBox(0.5f * a, 0.125f * a);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.restitution = 1.5f;
        fixtureDef.friction=5;   //摩擦
        body.createFixture(fixtureDef);
    }

    /**
     * 添加弯道刚体组件
     * @param x
     * @param y
     */
    public void addTrack(int x, int y){
        y = 25 - y;
        BodyDef def = new BodyDef();
        def.type = BodyType.STATIC;
        def.position.set((x + 0.875f) * size, y * size - size);
        body = myWorld.createBody(def);
        PolygonShape shape = new PolygonShape();
        //多边形绘制为矩形
        shape.setAsBox(0.005f * size, size);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape=shape;
        //反弹
        fixtureDef.restitution =1;
        fixtureDef.density=1;
        body.createFixture(fixtureDef);
    }

    /**
     * 设置物理世界
     * @param world
     */
    public static void setWorld(World world) {
        PhysicsContact.myWorld = world;
        PhysicsContact.LINECOUNT = BoardImplementation.getLineCount();
        //注册监听碰撞器
        myWorld.setContactListener(new ContactListener() {
            //监听碰撞
            @Override
            public void beginContact(Contact contact) {
                //碰撞物体
                Body body1 = contact.getFixtureA().getBody();
                //被碰撞物体
                Body body2 = contact.getFixtureB().getBody();
                //被碰撞物体也是球，完全弹性碰撞，二者交换
                if (body2.getUserData() == Shape.Ball) {
                    Body b = body1;
                    body1 = body2;
                    body2 = b;
                }
                //碰撞物体是球
                if (body1.getUserData() == Shape.Ball) {
                    //被碰撞物体是漩涡黑洞
                    if (body2.getUserData() == Shape.Absorber) {
                        //将球的信息销毁
                        body1.setUserData(null);
                        Data.ballCount--;
                        //在物理世界中销毁球
                        myWorld.destroyBody(body1);
                    }
                    //碰到弯轨改变小球速度
                    else if (body2.getUserData() == Shape.Corner) {
                            body1.setLinearVelocity(new Vec2(-10000, 0));
                    }
                    //碰到下边界销毁小球
                    else if (body2.getUserData() == Shape.Bound) {
                        body1.setUserData(null);
                        Data.ballCount--;
                        myWorld.destroyBody(body1);
                    }
                }
            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold manifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse contactImpulse) {

            }
        });
    }

    /**
     * 放大、缩小、旋转物体时，首先销毁原来的物体，再重新创新新的改变后的物体
     */
    public void updateBody() {
        myWorld.destroyBody(body);
        createBody();
    }

    /**
     * 返回刚体长度
     * @return
     */
    public static int getLength() {
        return size * LINECOUNT;
    }

    public Body getBody() {
        return body;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
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

    public Image getImage() {
        return image;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public static void setLineCount(int LINECOUNT) {
        PhysicsContact.LINECOUNT = LINECOUNT;
    }

}
