package ui.window;

import control.GameControl;
import ui.Img;
import util.FrameUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FrameConfig extends JFrame {

    private JButton btOK = new JButton("OK");

    private JButton btCancel = new JButton("Cancel");

    private JButton btUser = new JButton("Apply");

    private TextCtr[] keyTexts = new TextCtr[8];

    private JLabel error_msg = new JLabel();

    private JList skinList = null;

    private JPanel skinView = null;

    private DefaultListModel skinData = new DefaultListModel();

    private static final Image IMG_PSP = new ImageIcon("data/psp.jpg").getImage();

    private Image IMG_VIEW = new ImageIcon("graphics/view.jpg").getImage();

    private Image[] imgViewLists = null;

    private static String[] METHOD_NAMES = {
            "keyRight", "keyUp", "keyLeft", "keyDown",
            "keyFunctionLeft", "keyFunctionUp", "keyFunctionRight", "keyFunctionDown"
    };

    private static final String PATH = "data/control.dat";

    private GameControl gameControl;

    //TODO test
    public FrameConfig(GameControl gameControl){
        this.gameControl = gameControl;
        // 设置布局管理器为边界布局
        this.setLayout(new BorderLayout());
        // 初始化按键框
        this.initKeyText();
        // 添加主面板
        this.add(this.createMainPanel(), BorderLayout.CENTER);
        this.add(this.createButtonPanel(), BorderLayout.SOUTH);
        // 设置不能变大小
        this.setResizable(false);
        // 设置窗口大小
        this.setSize(635, 356);

        //居中显示
        FrameUtil.setFrameCenter(this);


    }

    /**
     * 初始化按键框
     */
    private void initKeyText() {
        int x = 0;
        int y = 45;
        int w = 60;
        int h = 20;
        for(int i=0; i< 4; i++){
            keyTexts[i] = new TextCtr(x, y, w, h, METHOD_NAMES[i]);
            y += 28;
        }
        x = 520;
        y = 60;
        for(int i= 4; i<8; i++){
            keyTexts[i] = new TextCtr(x, y, w, h, METHOD_NAMES[i]);
            y += 28;
        }

        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PATH));
            HashMap<Integer, String> cfgSet = (HashMap)ois.readObject();
            ois.close();
            Set<Map.Entry<Integer, String>> entrySet = cfgSet.entrySet();

            for(Map.Entry<Integer, String> e: entrySet){
                for(TextCtr tc: keyTexts){
                    if(tc.getMethodName().equals(e.getValue())){
                        tc.setKeyCode(e.getKey());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 创建按钮面板
     * @return
     */
    private JPanel createButtonPanel() {
        // 创建按钮面板，流式布局，右对齐
        JPanel jb = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        // 给确定按钮增加事件监听
        this.btOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(writeConfig()){
                // 写入成功后，关闭窗口
                setVisible(false);
                gameControl.setOver();

                    }
            }
        });
        jb.add(this.btOK);

        // 给取消按钮增加事件监听
        this.btCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 关闭窗口
                setVisible(false);
                gameControl.setOver();
            }
        });

        jb.add(this.btCancel);
        // 给应用按钮增加事件监听
        this.btUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                writeConfig();
                gameControl.repaint();
            }
        });

        jb.add(this.btUser);

        this.error_msg.setForeground(Color.BLUE);
        this.error_msg.setFont(new Font("黑体", Font.BOLD, 14));
        jb.add(error_msg);
        return jb;
    }


    /**
     * 写入玩家设置
     */
    private Boolean writeConfig() {
        HashMap<Integer, String> keySet = new HashMap<>();
        for(int i=0; i< this.keyTexts.length; i++){
            if(this.keyTexts[i].getKeyCode() == 0){
                this.error_msg.setText("Wrong Button");
                return false;
            }
            keySet.put(this.keyTexts[i].getKeyCode(), this.keyTexts[i].getMethodName());
        }

        if(keySet.size() != 8){
            this.error_msg.setText("Duplicate Keys");
            return false;
        }

        //写入对象流
        ObjectOutputStream oos = null;
        try {
            Img.setSkin(this.skinData.get(this.skinList.getSelectedIndex()).toString());
            oos = new ObjectOutputStream(new FileOutputStream(PATH));
            oos.writeObject(keySet);
            oos.close();
        } catch (Exception e) {
            this.error_msg.setText(e.getMessage());
            return false;
        }
        this.error_msg.setText(null);
        return true;
    }

    /**
     * 创建主面板（选项卡）
     * @return
     */
    private JTabbedPane createMainPanel() {
        JTabbedPane jtp = new JTabbedPane();
        jtp.addTab("set control", this.createControlPanel());
        jtp.addTab("skin control", this.createSkinPanel());

        return jtp;
    }

    private JPanel createSkinPanel() {
        JPanel jpanel = new JPanel(new BorderLayout());
        File dir = new File(Img.GRAPHICS_PATH);
        File[] files = dir.listFiles();
        this.imgViewLists = new Image[files.length];
        int index = 0;
        for(int i= 0; i<files.length; i++){
            if(files[i].isHidden()) continue;
            this.skinData.addElement(files[i].getName());
            this.imgViewLists[index] = new ImageIcon(files[i].getPath()+"/view.jpg").getImage();
            index += 1;


        }

        this.skinList = new JList(this.skinData);
        this.skinList.setSelectedIndex(0);
        this.skinList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                repaint();
            }
        });

        this.skinView = new JPanel(){
          @Override
          public void paintComponent(Graphics g){
              Image showImg = imgViewLists[skinList.getSelectedIndex()];
              int x = this.getWidth() - showImg.getWidth(null) >> 1;
              int y = this.getHeight() - showImg.getHeight(null) >> 1;
              g.drawImage(showImg, x, y, null);

          }
        };

        jpanel.add(new JScrollPane(this.skinList), BorderLayout.WEST);
        jpanel.add(this.skinView, BorderLayout.CENTER);


        return jpanel;
    }

    private JPanel createControlPanel(){
        JPanel jp = new JPanel(){
            @Override
            public void paintComponent(Graphics g){
                g.drawImage(IMG_PSP, 0, 0, null);
            }

        };

        // 设置布局管理器
        jp.setLayout(null);
        for(int i= 0; i<keyTexts.length; i++){
            jp.add(keyTexts[i]);
        }
        return jp;
    }

}
