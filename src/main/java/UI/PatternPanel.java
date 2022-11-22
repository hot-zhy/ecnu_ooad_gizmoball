package UI;

import Method.BoardImplementation;
import Method.ButtonListener;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 实现游戏界面右侧模式栏的绘制及其两个按钮对应的功能。
 */
public class PatternPanel extends JPanel {
    private JButton btnLay = new JButton("布局模式");
    private JButton btnPlay = new JButton("游玩模式");

    public PatternPanel(BoardPanel boardPanel) {
        super.setBorder(new TitledBorder(new EtchedBorder(),"模式栏",TitledBorder.CENTER,TitledBorder.CENTER,new Font("微软雅黑",Font.BOLD,15)));

        //布局模式
        btnLay.setBounds(0,25,150,15);
        btnLay.setFont(new Font("微软雅黑",Font.BOLD,21));
        btnLay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BoardImplementation.setBuildMode(true);
                boardPanel.build();
            }
        });
        super.add(btnLay);

        //游玩模式
        btnPlay.setBounds(0,100,150,15);
        btnPlay.setFont(new Font("微软雅黑",Font.BOLD,21));
        btnPlay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BoardImplementation.setBuildMode(false);
                boardPanel.build();
                ButtonListener.clear();
            }
        });
        super.add(btnPlay);

    }
}
