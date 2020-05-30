package service;

import config.GameConfig;
import dto.GameDto;
import entity.GameAct;

import java.awt.*;
import java.util.Map;
import java.util.Random;

public class GameTertis implements GameService{

    private GameDto dto;

    private Random random = new Random();

    private static final int MAX_TYPES = GameConfig.getSystemConfig().getTypeConfig().size() -1;

    // 升级行数
    private static final int LEVEL_UP = GameConfig.getSystemConfig().getLevelUp();

    // 消行可得分数
    private static final Map<Integer, Integer> PLUS_POINTS = GameConfig.getSystemConfig().getPlusPoints();

    public GameTertis(GameDto dto){
        this.dto = dto;
    }


    @Override
    public boolean keyUp() {
        if(this.dto.isPause()) return true;
        synchronized (this.dto){
            this.dto.getGameAct().round(this.dto.getGameMap());
            return true;
        }
    }

    public boolean keyDown() {
        if(this.dto.isPause()) return true;
        synchronized (this.dto) {
            if (this.dto.getGameAct().move(0, 1, this.dto.getGameMap())) {
                return false;
            }

            // get the game map object
            boolean[][] map = this.dto.getGameMap();
            Point[] act = this.dto.getGameAct().getActPoint();
            for (int i = 0; i < act.length; i++) {
                map[act[i].x][act[i].y] = true;
            }
            // 判断消行，并且获得经验值
            int getExp = this.plusExp();
            // 根据经验值，进行升级操作
            if (getExp > 0) {
                this.plusPoint(getExp);
            }
            // generate the next square
            this.dto.getGameAct().init(this.dto.getNext());
            // generate the next next square
            this.dto.setNext(random.nextInt(MAX_TYPES));
            // 检查游戏是否失败
            if (isLose()) {
                this.dto.setStart(false);
            }
        }
        return true;
    }



    public boolean keyLeft() {
        if(this.dto.isPause()) return true;
        synchronized (this.dto) {
            this.dto.getGameAct().move(-1, 0, this.dto.getGameMap());
        }
        return true;
    }

    public boolean keyRight() {
        if(this.dto.isPause()) return true;
        synchronized (this.dto) {
            this.dto.getGameAct().move(1, 0, this.dto.getGameMap());
        }
        return true;
    }

    @Override
    public boolean keyFunctionUp() {
     //   int point = this.dto.getCurPoint();
      //  int rmLine = this.dto.getNowRemoveLine();
      //  int lv = this.dto.getNowLevel();
      //  point += 10;
     //   rmLine += 1;
    //    if (rmLine %20 == 0){
     //       lv += 1;
     //   }

     //   this.dto.setCurPoint(point);
      //  this.dto.setNowLevel(lv);
       // this.dto.setNowRemoveLine(rmLine);
        this.dto.setCheat(false);
        plusPoint(4);
        return true;

    }

    /**
     * 快速下落
     * @return
     */
    @Override
    public boolean keyFunctionDown() {
        if(this.dto.isPause()) return true;
        while(!this.keyDown());
        return true;

    }

    /**
     * 控制阴影是否打开
     * @return
     */
    @Override
    public boolean keyFunctionLeft() {
        this.dto.setShowShadow();
        return true;

    }

    @Override
    public boolean keyFunctionRight() {
        this.dto.setPause();
        return true;
    }





    /**
     *  加分升经验值操作
     * @return 加分
     */
    private int plusExp(){
        //获得游戏地图
        boolean[][] map = this.dto.getGameMap();
        int exp = 0;
        for(int y=0; y<GameDto.GAMEZONE_H; y++){
            // 判断是否可以消行
            if(this.canRemove(y, map)){
                // 如果可以消行，那么进行消行操作
                this.removeLine(y, map);
                exp += 1;
            }
        }
        return exp;
    }

    /**
     *  判断是否可以消行
     * @param y 消行的行数
     * @param map 游戏地图
     * @return 是否可以消行
     */

    private boolean canRemove(int y, boolean[][] map) {
        for(int x = 0; x < GameDto.GAMEZONE_W; x++){
            if(!map[x][y]) return false;
        }
        return true;
    }

    private void removeLine(int rowNumber, boolean[][]map) {
        for(int x=0; x<GameDto.GAMEZONE_W; x++){
            for(int y = rowNumber; y>0; y--){
                map[x][y] = map[x][y-1];
            }
            map[x][0] = false;
        }
    }

    /**
     * 根据新增经验值调整等级
     * @param getExp 新增经验值
     */
    private void plusPoint(int getExp) {
        int curLevel = this.dto.getNowLevel();
        int rmLine = this.dto.getNowRemoveLine();
        int curPoint = this.dto.getCurPoint();
        if(rmLine % LEVEL_UP + getExp >= LEVEL_UP){
            //等级上升一级
            curLevel +=1;
            this.dto.setNowLevel(curLevel);
        }
        this.dto.setNowRemoveLine(rmLine + getExp);
        this.dto.setCurPoint(curPoint + PLUS_POINTS.get(getExp));
    }
    @Override
    public void startGame(){
        // 随机生成下一个方块
        this.dto.setNext(random.nextInt(MAX_TYPES));
        // 随机生成当前方块
        this.dto.setGameAct(new GameAct(random.nextInt(MAX_TYPES)));
        // 游戏状态设为开始
        this.dto.setStart(true);
        // 初始化dto
        this.dto.dtoInit();
    }

    /**
     * 检查游戏是否失败
     */
    private boolean isLose(){
        // 获得当前活动方块
        Point[] points = this.dto.getGameAct().getActPoint();
        // 获得当前游戏地图
        boolean[][] map = this.dto.getGameMap();

        for(int i= 0; i< points.length; i++){
            if(map[points[i].x][points[i].y]) return true;
        }

        return false;

    }


    /**
     * 游戏主要行为
     */
    @Override
    public void mainAction(){
        // 方块不停地落下
        this.keyDown();
    }





}
