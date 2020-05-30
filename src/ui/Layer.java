package ui;

import config.FrameConfig;
import config.GameConfig;
import dto.GameDto;

import javax.swing.*;
import java.awt.*;

// construct the window
public abstract class Layer {

    /**
     * 边框宽度
     */
    protected static int SIZE;

    /**
     * 内边距
     */
    protected static int PADDING;

    static {
        FrameConfig fcfg = GameConfig.getFrameConfig();
        SIZE = fcfg.getBorder();
        PADDING = fcfg.getPadding();
    }


    public static final int IMG_W = Img.WINDOW.getWidth(null);
    public static final int IMG_H = Img.WINDOW.getHeight(null);


    protected static final int IMG_RECT_H = Img.RECT.getHeight(null);
    private static final int IMG_RECT_W = Img.RECT.getWidth(null);

    private static int rectW;

    private static final Font DEF_FONT = new Font("黑体", Font.BOLD, 20);

    // the coordinate of the upper left x
    protected final int x;

    // the coordinate of the upper left y
    protected final int y;

    // the width of the window
    protected final int w;

    // the height of the window
    protected final int h;

    protected  GameDto dto = null;


    protected static final int NUMBER_w = Img.NUMBER_LV.getWidth(null) / 10;
    private static final int NUMBER_H = Img.NUMBER_LV.getHeight(null);

    protected Layer(int x, int y, int w, int h){
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.rectW = this.w - (PADDING << 1);
    }

    public void setDto(GameDto dto){
        this.dto = dto;
    }
    // create the window  #只有子类可以访问
    protected void createWindow(Graphics g){
        g.drawImage(Img.WINDOW, x, y, x+SIZE, y+SIZE, 0,0, SIZE, SIZE, null);
        g.drawImage(Img.WINDOW, x+SIZE, y, x+w-SIZE, y+SIZE, SIZE, 0, IMG_W-SIZE, SIZE, null);
        g.drawImage(Img.WINDOW, x+w-SIZE, y, x+w, y+SIZE, IMG_W-SIZE,0, IMG_W, SIZE, null);
        g.drawImage(Img.WINDOW, x, y+SIZE, x+SIZE, y+h-SIZE,0,SIZE, SIZE, IMG_H-SIZE, null);
        g.drawImage(Img.WINDOW, x+SIZE, y+SIZE, x+w-SIZE, y+h-SIZE,SIZE, SIZE, IMG_W - SIZE, IMG_H - SIZE, null);
        g.drawImage(Img.WINDOW, x+w-SIZE, y+SIZE,x+w, y+h-SIZE, IMG_W-SIZE, SIZE, IMG_W,IMG_H-SIZE, null );
        g.drawImage(Img.WINDOW, x, y+h-SIZE, x+SIZE, y+h,0, IMG_H-SIZE, SIZE, IMG_H, null);
        g.drawImage(Img.WINDOW, x+SIZE, y+h-SIZE, x+w-SIZE, y+h, SIZE, IMG_H-SIZE, IMG_W-SIZE, IMG_H, null);
        g.drawImage(Img.WINDOW, x+w-SIZE, y+h-SIZE,x+w, y+h, IMG_W-SIZE, IMG_H-SIZE, IMG_W, IMG_H, null);
    }

    abstract public void paint(Graphics g);

    // display the number
    protected void drawNumber(Graphics g, int num, int x, int y){
        String strNum = Integer.toString(num);
        for(int i=0; i<strNum.length();i++){
            int bit = strNum.charAt(i) - '0';
            g.drawImage(Img.NUMBER_LV,
                    this.x + x + NUMBER_w*i, this.y+y+16,
                    this.x+x+NUMBER_w*(i+1) , this.y+y+NUMBER_H,
                    bit * NUMBER_w,  0,
                    (bit+1)*NUMBER_w, NUMBER_H,null);
        }
    }

    protected void drawRect(int y, String title, String number, double percent, Graphics g){
        // initialize the value
        int rect_X = this.x + PADDING;
        int rect_y = this.y + y;
        // draw the background
        int h = 32;
        // set the color
        g.setColor(Color.BLACK);
        g.fillRect(rect_X, rect_y, rectW, IMG_RECT_H + 4 );
        g.setColor(Color.WHITE);
        g.fillRect(rect_X + 1, rect_y+1, rectW-2, IMG_RECT_H + 2);
        g.setColor(Color.BLACK);
        g.fillRect(rect_X + 2, rect_y+2, rectW-4, IMG_RECT_H);

        // draw the experience part
        int w = (int)(percent * (rectW - 4));
        // get the color
        int subIdx = (int)(percent * IMG_RECT_W) -1;
        g.drawImage(Img.RECT, rect_X + 2, rect_y + 2,
                rect_X+ 2 + w, rect_y + 2 + IMG_RECT_H,
                subIdx, 0, subIdx+1, IMG_RECT_H, null);

        g.setColor(Color.WHITE);
        g.setFont(DEF_FONT);
        g.drawString(title, rect_X + 4, rect_y + 20);
        if(number != null){
            g.drawString(number, rect_X + 230, rect_y + 20);
        }


    }

    // draw at the center
    protected void drawCenter(Graphics g, Image img){
        int imgW = img.getWidth(null);
        int imgH = img.getHeight(null);
        g.drawImage(img, this.x + (this.w - imgW >> 1), this.y +(this.h-imgH >>1), null);
    }



}
