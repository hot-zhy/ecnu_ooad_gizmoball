package Method;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static Config.Data.*;

/**
 * 文件输出和输入操作
 */
public class FileIO {
    //将当前游戏面板中所有组件写入文件
    public static void save(List<PhysicsContact> components, String path) {
        List<FileGizmoInformation> list = new ArrayList<>();   //创建存入文件的组件列表，类型为FileGizmo
        //遍历传入的Gizmo列表，将其转换为FileGizmo形式加入list
        for(PhysicsContact i : components) {
            FileGizmoInformation temp = new FileGizmoInformation();
            temp.setX(i.getX());
            temp.setY(i.getY());
            temp.setAngle(i.getAngle());
            temp.setSizeRate(i.getSizeRate());
            temp.setShape(i.getShape());
            list.add(temp);
        }
        writeObjectToFile(list, path);  //将list信息存入文件
    }

    public static void writeObjectToFile(Object obj, String path) {
        File f = new File(path);
        try {
            FileOutputStream out = new FileOutputStream(f);
            ObjectOutputStream objOut = new ObjectOutputStream(out);
            objOut.writeObject(obj);
            objOut.flush();
            objOut.close();
            System.out.println("write success");
        } catch (IOException e) {
            System.out.println("write error");
            e.printStackTrace();
        }
    }

    //从文件中载入地图
    public static List<PhysicsContact> load(String path) {
        List<FileGizmoInformation> list = (List<FileGizmoInformation>) readObjectFromFile(path);  //从文件中读入FileGizmo类型的组件信息
        List<PhysicsContact> components = new ArrayList<>(); //创建Gizmo类型列表
        for(FileGizmoInformation i : list) {
            //根据类型设置组件图片样式
            switch (i.getShape()) {
                case Ball:
                    i.setImage(ballIcon.getImage());
                    break;
                case Circle:
                    i.setImage(circleIcon.getImage());
                    break;
                case Track:
                    i.setImage(trackIcon.getImage());
                    break;
                case Paddle:
                    i.setImage(paddleIcon.getImage());
                    break;
                case Square:
                    i.setImage(squareIcon.getImage());
                    break;
                case Absorber:
                    i.setImage(absorberIcon.getImage());
                    break;
                case Triangle:
                    i.setImage(triangleIcon.getImage());
                    break;
                default:
            }
            PhysicsContact gizmo = new PhysicsContact(i.getX(),i.getY(),i.getSizeRate(),i.getShape(),i.getImage());
            //设置角度
            if(i.getAngle() != 0) {
                gizmo.setAngle(i.getAngle());
                gizmo.updateBody();
            }
            components.add(gizmo);
        }
        return components;
    }

    public static Object readObjectFromFile(String path) {
        Object temp = null;
        File f = new File(path);
        try {
            FileInputStream in = new FileInputStream(f);
            ObjectInputStream objIn = new ObjectInputStream(in);
            temp = objIn.readObject();
            objIn.close();
            System.out.println("read success");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("read fail");
            e.printStackTrace();
        }
        return temp;
    }

}
