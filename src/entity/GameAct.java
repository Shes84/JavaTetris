package entity;

import config.GameConfig;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameAct {



    private Point[] actPoints =null;

    private final static int MIN_X = GameConfig.getSystemConfig().getMinX();
    private final static int MAX_X = GameConfig.getSystemConfig().getMaxX();
    private final static int MIN_Y = GameConfig.getSystemConfig().getMinY();
    private final static int MAX_Y = GameConfig.getSystemConfig().getMaxY();
    private final static List<Point[]> TYPE_CONFIG = GameConfig.getSystemConfig().getTypeConfig();
    private final static List<Boolean> TYPE_ROUND = GameConfig.getSystemConfig().getTypeRound();

    private int actCode;

    public GameAct(int actCode){

        this.init(actCode);
    }



    public void init(int actCode){
        this.actCode = actCode;
        Point[] points = TYPE_CONFIG.get(actCode);
        actPoints = new Point[points.length];
        for(int i=0; i<points.length;i++){
            actPoints[i] = new Point(points[i].x, points[i].y);
        }
    }



    public Point[] getActPoint(){
        return actPoints;
    }


    public boolean move(int moveX, int moveY, boolean[][] gameMap){
        for(int i=0; i<actPoints.length;i++){
            int newX = actPoints[i].x + moveX;
            int newY = actPoints[i].y + moveY;
            if(isOverBoundary(newX, newY, gameMap)) return false;
        }

        for(int i=0; i<actPoints.length;i++){
            actPoints[i].x += moveX;
            actPoints[i].y += moveY;
        }
        return true;
    }

    public void round(boolean[][] gameMap){
        if(!TYPE_ROUND.get(this.actCode)) {
            System.out.println(444);
            return;
        }
        for(int i=1; i<actPoints.length;i++){
            int newX = actPoints[0].y + actPoints[0].x - actPoints[i].y;
            int newY = actPoints[0].y - actPoints[0].x + actPoints[i].x;
            if(isOverBoundary(newX, newY, gameMap)){
                return;
            }
        }
        for(int i=1; i<actPoints.length;i++){
            int newX = actPoints[0].y + actPoints[0].x - actPoints[i].y;
            int newY = actPoints[0].y - actPoints[0].x + actPoints[i].x;
            actPoints[i].x = newX;
            actPoints[i].y = newY;
        }


    }

    private boolean isOverBoundary(int x, int y, boolean[][] gameMap){

        return x < MIN_X || x > MAX_X || y <MIN_Y || y >MAX_Y || gameMap[x][y];
    }

    public int getActCode(){
        return actCode;
    }
}
