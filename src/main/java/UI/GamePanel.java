package UI;

import Method.BoardImplementation;

import javax.swing.*;
import java.awt.*;

import static Config.Data.ballIcon;

/**
 * UI层主控，控制绘制主面板、组件、工具等栏目
 */
public class GamePanel {
    /**
     * 标题
     */
    private JFrame jFrame = new JFrame("GizmoBall");

    /**
     * 王哥布局管理器
     */
    private GridBagLayout gridBagLayout = new GridBagLayout();
    private GridBagConstraints constraints=new GridBagConstraints();

    private BoardImplementation board = new BoardImplementation();


    /**
     * 绘制面板
     */
    private BoardPanel boardPanel = new BoardPanel(board);
    /**
     * 组件面板
     */
    private ComponentPanel componentPanel = new ComponentPanel();
    /**
     * 工具面板
     */
    private ToolPanel toolPanel = new ToolPanel();
    /**
     * 模式面板
     */
    private PatternPanel patternPanel = new PatternPanel(boardPanel);

    public GamePanel() {
        jFrame.setBounds(300,100,950,750);

        jFrame.setLayout(gridBagLayout);
        constraints.fill = GridBagConstraints.BOTH;    //组件填充显示区域
        constraints.weightx=0.0;    //恢复默认值
        constraints.gridwidth = GridBagConstraints.REMAINDER;    //结束行

        jFrame.setJMenuBar(new MenuPanel(boardPanel));

        boardInit();
        toolBoxInit();

        jFrame.setResizable(false);
        jFrame.pack();
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
        jFrame.setIconImage(ballIcon.getImage());
    }

    public void boardInit() {
        boardPanel.setPreferredSize(new Dimension(750,750));
        boardPanel.setBackground(Color.BLACK);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 4;
        constraints.weightx = 0.75;
        constraints.weighty = 1;
        jFrame.add(boardPanel,constraints);
    }

    public void toolBoxInit() {
        componentPanel.setPreferredSize(new Dimension(200,400));
        componentPanel.setBackground(Color.WHITE);
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.weightx = 0.25;
        constraints.weighty = 0.5;
        jFrame.add(componentPanel,constraints);

        toolPanel.setPreferredSize(new Dimension(200,200));
        toolPanel.setBackground(Color.WHITE);
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.weightx = 0.25;
        constraints.weighty = 0.3;
        jFrame.add(toolPanel,constraints);

        patternPanel.setPreferredSize(new Dimension(200,150));
        patternPanel.setBackground(Color.WHITE);
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.weightx = 0.3;
        constraints.weighty = 0.2;
        jFrame.add(patternPanel,constraints);
    }
}
