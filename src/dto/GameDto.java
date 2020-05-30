package dto;

import config.GameConfig;
import entity.GameAct;
import ui.GameFunction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameDto {

    public static final int GAMEZONE_W = GameConfig.getSystemConfig().getMaxX() + 1;
    public static final int GAMEZONE_H = GameConfig.getSystemConfig().getMaxY() + 1;

    // constructor
    public GameDto(){
        dtoInit();

    }

    // initialize dto
    public void dtoInit(){
        this.gameMap = new boolean[GAMEZONE_W][GAMEZONE_H];
        // initialize all the game objects
        this.nowRemoveLine = 0;
        this.curPoint = 0;
        this.level = 0;
        this.pause = false;
        this.cheat = false;
        this.sleepTime = GameFunction.getSleepByLevel(this.level);

    }

    private List<Player> diskRecord;

    private List<Player> dbRecord;

    private boolean[][] gameMap;

    private GameAct gameAct;

    private int next;

    private int level;

    private int curPoint;

    private int nowRemoveLine;

    private boolean start;

    private boolean showShadow;

    private boolean pause;

    private boolean cheat;

    private long sleepTime;

    public List<Player> getDiskRecord(){
        return diskRecord;
    }

    public void setDiskRecord(List<Player> diskRecord){
        this.diskRecord = this.setFillRecord(diskRecord);
    }

    public List<Player> getDbRecord(){
        return dbRecord;
    }

    public void setDbRecord(List<Player> dbRecord) {
        this.dbRecord = this.setFillRecord(dbRecord);
    }

    public List<Player> setFillRecord(List<Player> players){
        if(players == null){
            players = new ArrayList<Player>();
        }
        while(players.size() < 5){
            players.add(new Player("No data", 0));
        }

        Collections.sort(players);
        return players;
    }

    public boolean[][] getGameMap() {
        return gameMap;
    }

    public void setGameMap(boolean[][] gameMap) {
        this.gameMap = gameMap;
    }

    public GameAct getGameAct() {
        return gameAct;
    }

    public void setGameAct(GameAct gameAct) {
        this.gameAct = gameAct;
    }

    public int getNext() {
        return next;
    }

    public void setNext(int next) {
        this.next = next;
    }

    public int getNowLevel() {
        return level;
    }

    public void setNowLevel(int level) {
        this.level = level;
        this.sleepTime = GameFunction.getSleepByLevel(this.level);
    }

    public int getCurPoint() {
        return curPoint;
    }

    public void setCurPoint(int curPoint) {
        this.curPoint = curPoint;
    }

    public int getNowRemoveLine() {
        return nowRemoveLine;
    }

    public void setNowRemoveLine(int nowRemoveLine) {
        this.nowRemoveLine = nowRemoveLine;
    }

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public boolean isShowShadow() {
        return showShadow;
    }

    public void setShowShadow() {
        this.showShadow =!this.showShadow;
    }

    public boolean isPause() {
        return pause;
    }

    public void setPause() {
        if(isStart()){
            this.pause = !this.pause;
        }
    }

    public boolean isCheat() {
        return cheat;
    }

    public void setCheat(boolean cheat) {
        this.cheat = cheat;
    }

    public long getSleepTime() {
        return sleepTime;
    }

}
