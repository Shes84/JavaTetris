package ui;

import config.GameConfig;
import entity.GameAct;

import javax.swing.*;
import java.awt.*;

public class LayerGame extends Layer {

    public LayerGame(int x, int y, int w, int h){
        super(x, y, w, h);
    }

    private static final int SIZE_ROLL = GameConfig.getFrameConfig().getSizeRoll();

    private static final int LEFT_SIDE = 0;

    private static final int RIGHT_SIDE = GameConfig.getSystemConfig().getMaxX();

    private static final int LOSE_IDX = 8;

    public void paint(Graphics g){

        this.createWindow(g);
        GameAct act = this.dto.getGameAct();
        if(act!=null){
            Point[] points = act.getActPoint();
            // 绘制活动方块
            this.drawAct(g, points);
            // 绘制阴影
            this.drawShadow(points, g);
        } // 绘制活动地图
        this.drawMap(g);
        // 暂停游戏
        if(this.dto.isPause()){
            drawCenter(g, Img.PAUSE);
        }
    }

    private void drawMap(Graphics g) {
        // print the gameMap
        boolean[][] map = this.dto.getGameMap();

        // 计算当前堆积的方块的颜色
        int level = this.dto.getNowLevel();
        int imgIdx = level == 0? 0: (level-1) % 7  + 1;

        for(int x=0; x<map.length;x++){
            for(int y=0; y<map[x].length; y++){
                if(map[x][y]){
                    drawByPoint(x, y, imgIdx, g);
                }
            }

        }
    }

    private void drawAct(Graphics g, Point[] points) {
        // 绘制阴影
        this.drawShadow(points, g);
        int actCode = this.dto.getGameAct().getActCode();

        for(int i=0; i< points.length;i++){
            // print the square
            drawByPoint(points[i].x, points[i].y, actCode+1, g);
        }
    }

    private void drawByPoint(int x, int y, int imgIndex, Graphics g){
        imgIndex = this.dto.isStart()? imgIndex: LOSE_IDX;

        g.drawImage(Img.ACT,
                this.x+  (x  << SIZE_ROLL) +7,
                this.y + (y << SIZE_ROLL) + 7,
                this.x + (x + 1 << SIZE_ROLL)  + 7,
                this.y + (y + 1<<SIZE_ROLL)  + 7,
                imgIndex << SIZE_ROLL,0, (imgIndex+1)<<SIZE_ROLL, 1<<SIZE_ROLL,null);
    }

    /**
     * 绘制阴影
     * @param points 显示阴影的位置
     */

    private void drawShadow(Point[] points,Graphics g){
        if(!this.dto.isShowShadow()) return;


        int leftX = RIGHT_SIDE;
        int rightX = LEFT_SIDE;

        for(Point point: points){
            leftX =  Math.min(leftX, point.x);
            rightX = Math.max(rightX, point.x);
        }
        System.out.println(leftX + "," + rightX);
        g.drawImage( Img.SHADOW,
                this.x + SIZE + (leftX << SIZE_ROLL),
                this.y + SIZE,
                (rightX - leftX + 1) << SIZE_ROLL,
                this.h - (SIZE << 1), null
                );
    }

}
