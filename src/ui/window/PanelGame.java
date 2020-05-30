package ui.window;

import config.FrameConfig;
import config.GameConfig;
import config.LayerConfig;
import control.GameControl;
import control.PlayerControl;
import dto.GameDto;
import ui.Img;
import ui.Layer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

public class PanelGame extends JPanel {

    private final static int BT_W = GameConfig.getFrameConfig().getButtonConfig().getButtonW();
    private final static int BT_H = GameConfig.getFrameConfig().getButtonConfig().getButtonH();
    private final static int START_X = GameConfig.getFrameConfig().getButtonConfig().getStartX();
    private final static int START_Y = GameConfig.getFrameConfig().getButtonConfig().getStartY();
    private final static int USER_CFG_X = GameConfig.getFrameConfig().getButtonConfig().getUserConfigX();
    private final static int USER_CFG_Y = GameConfig.getFrameConfig().getButtonConfig().getUserConfigY();


   private List<Layer> layers = null;


   private JButton btnStart;

   private JButton btnConfig;

   private GameControl gameControl  = null;

    public PanelGame(GameControl gameControl, GameDto dto){
        //连接游戏控制器
        this.gameControl = gameControl;
        //设置布局管理器为自由布局
        this.setLayout(null);
        // initialize the component
        initComponent();
        // initialize the layer
        initLayer(dto);
        // 安装键盘监听器
        this.addKeyListener(new PlayerControl(gameControl));

    }


    // install the player control
    public void setGameControl(PlayerControl playerControl){
        this.addKeyListener(playerControl);

    }

    private void initComponent(){
        // 初始化start按钮
        this.btnStart = new JButton(Img.BT_START);
       // this.btnStart = new JButton(Img.BT_START);
        // 设置按钮位置
        btnStart.setBounds(START_X,START_Y, BT_W, BT_H);
        // 添加start按钮的事件监听
        this.btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameControl.start();
                requestFocus();
            }
        });
        // 添加按钮到面板
        this.add(btnStart);
        // 初始化setting按钮
        this.btnConfig = new JButton(Img.BT_CONFIG);
        // 设置按钮位置
        this.btnConfig.setBounds(USER_CFG_X, USER_CFG_Y, BT_W, BT_H);
        // 添加btnConfig的事件监听器
        this.btnConfig.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameControl.showUserConfig();
            }
        });
        // 添加按钮到面板
        this.add(this.btnConfig);
    }


    private void initLayer(GameDto dto){
        try{
            // get the game config
            FrameConfig fcfg = GameConfig.getFrameConfig();
            // get the layer config
            List<LayerConfig> layersCfg = fcfg.getLayersConfig();
            // construct layer parameter
            layers = new ArrayList<Layer>(layersCfg.size());
            // construct each layer
            for(LayerConfig layerCfg: layersCfg){
                // get the class
                Class<?> cls = Class.forName(layerCfg.getClassName());
                // get the constructor function
                Constructor<?> ctr = cls.getConstructor(int.class, int.class, int.class, int.class);
                // call the constructor function
                Layer layer = (Layer) ctr.newInstance(
                        layerCfg.getX(), layerCfg.getY(), layerCfg.getW(), layerCfg.getH()
                );
                // set the game data object
                layer.setDto(dto);
                layers.add(layer);
            }


        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        // construct the windows
        for(int i=0; i<layers.size(); i++){
            layers.get(i).paint(g);
        }



    }

    public void setgameControl(GameControl gameControl) {
        this.gameControl = gameControl;
    }

    /**
     * 按钮是否可以点击
     */
    public void buttonSwitch(boolean onOff){
        this.btnConfig.setEnabled(onOff);
        this.btnStart.setEnabled(onOff);


    }
}
