package ui.window;
import config.FrameConfig;
import config.GameConfig;
import ui.window.PanelGame;
import util.FrameUtil;

import javax.swing.JFrame;


public class FrameGame extends JFrame{

    public FrameGame(PanelGame panelGame){

        //获得游戏配置
        FrameConfig fcfg = GameConfig.getFrameConfig();

        // set the title
        this.setTitle(fcfg.getTitle());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // set the size of the window
        this.setSize(fcfg.getWidth(),fcfg.getHeight());
        this.setResizable(false);
        // 调用setFrameCenter，使得界面居中
        FrameUtil.setFrameCenter(this);

        this.setContentPane(panelGame);

        this.setVisible(true);

    }
}
