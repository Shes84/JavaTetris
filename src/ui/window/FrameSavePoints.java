package ui.window;

import control.GameControl;
import util.FrameUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class FrameSavePoints extends JFrame {

    private JButton btOK = null;

    private JLabel lbPoint = null;

    private JTextField jtTxt = null;

    private JLabel errorMSG = null;

    private GameControl gameControl = null;

    public FrameSavePoints(GameControl gameControl){
        this.gameControl = gameControl;
        this.setTitle("saved");
        this.setSize(456, 228);
        // show in the center
        FrameUtil.setFrameCenter(this);
        this.setResizable(false);
        this.setLayout(new BorderLayout());
        this.createCom();
        this.createAction();
    }

    /**
     * 显示窗口
     */
    public void showWindow(int point){
        this.lbPoint.setText("Your point is " + point);
        this.setVisible(true);

    }

    /**
     *  添加事件监听器（btOK）
     */
    private void createAction(){
        // 添加事件监听至YES键
        this.btOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = jtTxt.getText();
                if(name.length() > 16 || name == null || " ".equals(name)){
                    errorMSG.setText("Invalid Name");
                }else{
                    setVisible(false);
                    try {
                        gameControl.savePoint(name);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                }
            }
        });
    }


    /**
     *  初始化控件
     */
    private void createCom(){
        // 创建北部面板
        JPanel north = new JPanel(new FlowLayout(FlowLayout.LEFT));
        // 创建分数文字
        this.lbPoint = new JLabel();
        // 把分数文字添加到北部面板
        north.add(lbPoint);
        // 初始化errorMSG
        this.errorMSG = new JLabel();
        // 设置errorMSG的颜色为红色
        this.errorMSG.setForeground(Color.RED);
        // 在北部面板中添加errorMSG
        north.add(this.errorMSG);
        // 把北部面板放置于面板的北部
        this.add(north, BorderLayout.NORTH);

        // 创建中心面板
        JPanel center = new JPanel(new FlowLayout(FlowLayout.LEFT));
        // 在中心面板中添加输入名字的标签
        center.add(new JLabel("Please enter your name: "));
        this.jtTxt = new JTextField(10);
        center.add(jtTxt);
        // 把中心面板添加到面板的中心位
        this.add(center, BorderLayout.CENTER);


        // 创建确定按钮
        this.btOK = new JButton("Yes");
        // 创建南部面板，为流式布局
        JPanel south = new JPanel(new FlowLayout(FlowLayout.CENTER));
        // 面板上添加确定按钮
        south.add(btOK);
        // 在南部添加该面板
        this.add(south, BorderLayout.SOUTH);

    }

}
