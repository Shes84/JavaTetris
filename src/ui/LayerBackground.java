package ui;

import javax.swing.*;
import java.awt.*;

public class LayerBackground extends Layer {

   // private static final Image IMG_BG_TMP = new ImageIcon("graphics/background/bg01.jpg").getImage();

    public LayerBackground(int x, int y, int w, int h){
        super(x, y, w, h);
    }

    public void paint(Graphics g){

        int bgIdx = this.dto.getNowLevel() % Img.BG_LIST.size();
        g.drawImage(Img.BG_LIST.get(bgIdx), 0, 0, 1162, 654, null);
    }
}
