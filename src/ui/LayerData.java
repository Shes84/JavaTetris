package ui;

import config.GameConfig;
import dto.Player;

import java.awt.*;
import java.util.List;

public abstract class LayerData extends Layer {

    /**
     * 最大数据行
     */
    private static final int MAX_ROW = GameConfig.getDataConfig().getMaxRow();

    private static int START_Y = 0;

    private static int SPA = 0;

    private static final int RECT_H = IMG_RECT_H + 4;


    protected LayerData(int x, int y, int w, int h) {
        super(x, y, w, h);
        SPA = (h - RECT_H * 5 - (PADDING << 1) - Img.DB.getHeight(null)) / MAX_ROW;
        START_Y = PADDING + Img.DB.getHeight(null) + SPA;
    }

    @Override
    abstract public void paint(Graphics g);

    /**
     * 绘制该窗口所有槽值
     *
     * @param imgTitle 图片标题
     * @param players 数据源
     * @param g 画笔
     */

    public void showData(Image imgTitle, List<Player> players, Graphics g){

        g.drawImage(imgTitle, this.x + PADDING, this.y + PADDING, null);
        int curPoint = this.dto.getCurPoint();
        for (int i=0; i < MAX_ROW; i++){
            Player p = players.get(i);
            int recordPoint = p.getPoint();
            double percent = (double) curPoint / (double)recordPoint;
            percent = percent > 1 ? 1.0: percent;
            String strPoint = recordPoint == 0?null : Integer.toString(recordPoint);
            this.drawRect(START_Y + i * (RECT_H + SPA) , p.getName(), strPoint, percent, g);
        }

    }
}
