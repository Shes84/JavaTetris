package ui;

import javax.swing.*;
import java.awt.*;

public class LayerNext extends Layer {


    public LayerNext(int x, int y, int w, int h){
        super(x, y, w, h);
    }

    public void paint(Graphics g){

        this.createWindow(g);
        // 如果游戏已经开始，那么开始画下一个方块
        if(this.dto.isStart()){
            g.drawImage(Img.NEXT_ACT[this.dto.getNext()], this.x + 32, this.y + 32, null);
        }
    }

}
