package ui;

import config.GameConfig;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Level;

public class LayerPoint extends Layer {

    // 升级行数
    private static final int LEVEL_UP = GameConfig.getSystemConfig().getLevelUp();

    // 消行Y坐标
    private final int rmLineY;

    // 分数Y坐标
    private final int pointY;

    private final int POINT_BIT = 5;

    // 消行X坐标
    private final int comX;

    // 经验值Y坐标
    private final int expY;

    // 经验值槽的宽度
    private final int expW;

    public LayerPoint(int x, int y, int w, int h){

        super(x, y, w, h);
        // initialize the Y and X coordinate
        this.comX = this.w - POINT_BIT * NUMBER_w - PADDING ;
        this.pointY = PADDING;
        this.rmLineY = this.pointY + Img.POINT.getHeight(null) + PADDING;
        this.expY = this.rmLineY + Img.POINT.getHeight(null) + PADDING;
        this.expW = this.w - (PADDING<<1);

    }

    public void paint(Graphics g){

        this.createWindow(g);
        // draw the title
        g.drawImage(Img.POINT, this.x + PADDING, this.y + pointY+7, null);
        // display the title
        this.drawNumber(g, this.dto.getCurPoint(), comX, pointY);
        // draw the level for removing
        g.drawImage(Img.RMLINE, this.x + PADDING, this.y + rmLineY +7, null);
        // display the removing level
        this.drawNumber(g, this.dto.getNowRemoveLine(), comX, rmLineY);
        int rmLine = this.dto.getNowRemoveLine();
        this.drawRect(expY,"下一级", null, (double)(rmLine % LEVEL_UP)/(double)LEVEL_UP, g);
    }

    @Deprecated
    private Color getNowColor(double hp, double maxHp){
       int colorR = 0;
       int colorG = 255;
       int colorB = 0;
       double halfHp = maxHp / 2;
       if (hp > halfHp){
           colorR = 255 - (int)((hp - halfHp) / (maxHp / 2) * 255);
           colorG = 255;
       }else{
           colorR = 255;
           colorG = (int)(hp / (maxHp / 2) * 255);
       }

       return new Color(colorR, colorG, colorB);
    }

}
