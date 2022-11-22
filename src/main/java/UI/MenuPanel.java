package UI;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * 实现游戏上方菜单栏的对应功能，包括“新建游戏”、“保存游戏”和“读取游戏”。
 */

public class MenuPanel extends JMenuBar {
    //一级菜单
    JMenu fileMenu = new JMenu("文件");
    //二级菜单
    JMenuItem newGame = new JMenuItem("新建游戏");
    JMenuItem saveGame = new JMenuItem("保存游戏");
    JMenuItem readGame = new JMenuItem("读取游戏");

    public MenuPanel(BoardPanel boardPanel) {
        fileMenu.add(newGame);
        fileMenu.add(saveGame);
        fileMenu.add(readGame);

        super.add(fileMenu);
        super.getComponent().setBackground(Color.WHITE);

        //新建游戏
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //新建游戏场景
                boardPanel.newScene();
            }
        });

        //保存游戏
        saveGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //禁用棋盘中的光标
                boardPanel.getBoard().setFocus(false);
                JFileChooser jFileChooser = new JFileChooser();
                jFileChooser.showSaveDialog(saveGame);

                File file = jFileChooser.getSelectedFile();
                String fileName = file.getAbsolutePath();
                if(!fileName.endsWith(".gzm")) {
                    fileName += ".gzm";
                }

                boardPanel.saveScene(fileName);  //保存文件
                boardPanel.getBoard().setFocus(true);    //恢复光标
            }
        });

        //读取游戏
        readGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boardPanel.getBoard().setFocus(false);   //禁用棋盘中的光标
                JFileChooser jFileChooser = new JFileChooser();
                jFileChooser.setFileFilter(new GzmFileFilter());
                jFileChooser.showOpenDialog(readGame);

                File file = jFileChooser.getSelectedFile();
                boardPanel.loadScene(file.getAbsolutePath());   //读取文件
                boardPanel.getBoard().setFocus(true);    //恢复光标
            }
        });

    }

    //过滤文件结尾为.gzm的文件
    class GzmFileFilter extends FileFilter {

        @Override
        public boolean accept(File f) {
            if(f.isDirectory()) return true;
            return f.getName().endsWith(".gzm");
        }

        @Override
        public String getDescription() {
            return ".gzm";
        }
    }
}
