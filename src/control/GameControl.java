package control;

import config.DataInterfaceConfig;

import config.GameConfig;
import dao.Data;
import dao.DataTest;
import dto.GameDto;
import dto.Player;
import service.GameService;
import service.GameTertis;
import ui.GameFunction;
import ui.window.FrameGame;
import ui.window.FrameSavePoints;
import ui.window.PanelGame;
import ui.window.FrameConfig;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class GameControl {

    // game interface
    private Data dataA;

    private Data dataB;

    // 游戏层界面
    private PanelGame panelGame;

    // 游戏逻辑层
    private GameService gameService;
    // 记录玩家动作
    private Map<Integer, Method> action;
    // 用户配置页面
    private FrameConfig frameConfig;
    // 用户保存分数页面
    private FrameSavePoints frameSavePoints;

    private Thread gameThread = null;

    private GameDto dto;



    public GameControl() {
        // 创建游戏数据源
        this.dto = new GameDto();
        // 创建游戏逻辑块（连接游戏数据源）
        this.gameService = new GameTertis(dto);
        // 创建数据接口A
        this.dataA = new DataTest();
        this.dto.setDbRecord(dataA.loadData());
        // 记录本地数据到磁盘
        this.dataB = createDataObject(GameConfig.getDataConfig().getDataB());
        this.dto.setDiskRecord(dataB.loadData());
        // 创建游戏面板
        this.panelGame = new PanelGame(this, dto);
        // 读取用户配置
        this.setControlConfig();
        // 把自己丢进去，初始化用户配置窗口
        this.frameConfig = new FrameConfig(this);
        // 把自己丢进去，初始化保存分数页面窗口
        this.frameSavePoints = new FrameSavePoints(this);
        // 初始化游戏主窗口（安装游戏页面）
        new FrameGame(this.panelGame);
    }

    /**
     * 读取玩家控制设置
     */
    private void setControlConfig(){
        // 创建键盘码与方法名的映射
        this.action = new HashMap<>();
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("data/control.dat"));
            HashMap<Integer, String> cfgSet = (HashMap)ois.readObject();
            ois.close();
            Set<Map.Entry<Integer, String>> entrySet = cfgSet.entrySet();
            for(Map.Entry<Integer, String> e: entrySet){
                action.put(e.getKey(), this.gameService.getClass().getMethod(e.getValue()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 创建数据对象
     * @param cfg
     * @return
     */

    private Data createDataObject(DataInterfaceConfig cfg){
        try{
            // 获得类对象
            Class<?> cls = Class.forName(cfg.getClassName());
            // 获得构造器
            Constructor<?> ctr = cls.getConstructor(HashMap.class);
            // 创建对象
            return (Data) ctr.newInstance(cfg.getParam());
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 根据玩家控制来决定行为
     * @param keyCode
     */
    public void actionByKeyCode(int keyCode){
        try {
            if(action.containsKey(keyCode)){
                // 调用方法
                action.get(keyCode).invoke(this.gameService);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.panelGame.repaint();

    }


    public void keyRound() {
        this.gameService.keyUp();
        this.panelGame.repaint();
    }

    public void keyDown() {
        this.gameService.keyDown();
        this.panelGame.repaint();
    }

    public void keyLeft() {
        this.gameService.keyLeft();
        this.panelGame.repaint();
    }

    public void keyRight() {
        this.gameService.keyRight();
        this.panelGame.repaint();
    }

    /**
     * 显示用户配置窗口
     */
    public void showUserConfig() {
        this.frameConfig.setVisible(true);
    }

    /**
     * 子窗口关闭事件
     */
    public void setOver() {
        // 刷新页面
        this.panelGame.repaint();
        // 重新加载设置好的值
        this.setControlConfig();
    }

    /**
     * 开始按钮事件
     */
    public void start() {
        // 面板按钮设置为不可点击
        this.panelGame.buttonSwitch(false);
        // 关闭窗口（保存分数窗口以及配置窗口）
        this.frameSavePoints.setVisible(false);
        this.frameConfig.setVisible(false);
        // 游戏数据初始化
        this.gameService.startGame();
        // 创建线程对象
        this.gameThread = new MainThread();
        // 启动线程
        this.gameThread.start();
        this.panelGame.repaint();

    }

    private void afterLose(){
        if(!this.dto.isCheat()){
            // 保存得分窗口，并显示窗口
            this.frameSavePoints.showWindow(this.dto.getCurPoint());
            // 可以重新按按钮
            this.panelGame.buttonSwitch(true);
        }

    }

    /**
     *  保存分数
     * @param name 玩家昵称
     */
    public void savePoint(String name) throws IOException {
        Player player = new Player(name, this.dto.getCurPoint());
        // 保存记录到数据库
        this.dataA.saveData(player);
        // 保存记录到本地
        this.dataA.saveData(player);
        // 设置数据库数据到游戏
        this.dto.setDbRecord(this.dataA.loadData());
        // 设置磁盘数据到游戏
        this.dto.setDiskRecord(this.dataB.loadData());
        // 刷新画面
        this.panelGame.repaint();

    }

    public void repaint() {
        this.panelGame.repaint();
    }



    private class MainThread extends Thread{
        public void run(){
            // 刷新画面
            panelGame.repaint();
            while(dto.isStart()){
                try {
                    // sleep for 0.5s
                    Thread.sleep(dto.getSleepTime());
                    if(dto.isPause()){
                        continue;
                    }
                    // 游戏主行为
                    gameService.mainAction();
                    panelGame.repaint();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            afterLose();
        }

    }
}
