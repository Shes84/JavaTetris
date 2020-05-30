package ui;

import javax.swing.*;
import java.awt.*;

public class LayerLevel extends Layer {

    // image for the title
    private static final Image IMG_LV = new ImageIcon("graphics/string/level.png").getImage();
    private static final int IMG_LVW = IMG_LV.getWidth(null);


    public LayerLevel(int x, int y, int w, int h){
        super(x, y, w, h);
    }

    public void paint(Graphics g){
        this.createWindow(g);

        // set the position of the title to be in the center
        int centerX = (this.w - IMG_LVW >> 1);

        g.drawImage(IMG_LV, this.x +centerX, this.y + PADDING, null);
        this.drawNumber(g, this.dto.getNowLevel(), centerX, 32);
    }

}
