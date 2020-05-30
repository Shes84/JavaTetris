package ui;

import javax.swing.*;
import java.awt.*;

public class LayerAbout extends Layer {

    public LayerAbout(int x, int y, int w, int h){
        super(x, y, w, h);
    }

    public void paint(Graphics g){
        this.createWindow(g);
        this.drawCenter(g, Img.SIGN);
    }
}
