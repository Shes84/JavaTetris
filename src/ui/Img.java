package ui;
import config.GameConfig;

import javax.swing.ImageIcon;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Img {


    // signature
    public static Image SIGN = null;
    // Points
    public static Image POINT = null;
    // remove the line
    public static Image RMLINE = null;
    // window
    public static Image WINDOW =null;
    // reaction
    public static Image RECT = null;
    // number
    public static Image NUMBER_LV = null;
    // database
    public static Image DB = null;
    // disk
    public static Image  DISK = null;
    // square
    public static Image ACT =null;
    // shadow
    public static Image SHADOW = null;
    // start button
    public static ImageIcon BT_START = null;
    // configuration button
    public static ImageIcon BT_CONFIG = null;
    // pause
    public static Image PAUSE = null;


    // next graph
    public static Image[] NEXT_ACT =null;
    // background list
    public static List<Image> BG_LIST = null;


    public static final String GRAPHICS_PATH = "graphics";

    static{
        setSkin("default");
    }


    public static void setSkin(String skin){
        String skinPath = GRAPHICS_PATH + "/" + skin;
        // signature
        SIGN = new ImageIcon(skinPath+ "/string/sign.png").getImage();
        // Points
        POINT = new ImageIcon(skinPath +"/string/point.png").getImage();
        // remove the line
        RMLINE = new ImageIcon(skinPath +"/string/rmline.png").getImage();
        // window
        WINDOW = new ImageIcon(skinPath + "/window/Window.png").getImage();
        // reaction
        RECT = new ImageIcon(skinPath + "/window/rect.png").getImage();
        // number
        NUMBER_LV = new ImageIcon(skinPath + "/string/num.png").getImage();
        // database
        DB = new ImageIcon(skinPath + "/string/db.png").getImage();
        // disk
        DISK = new ImageIcon(skinPath + "/string/disk.png").getImage();
        // square
        ACT = new ImageIcon(skinPath + "/game/rect.png").getImage();
        // shadow
        SHADOW = new ImageIcon(skinPath + "/game/shadow.png").getImage();
        // start button
        BT_START = new ImageIcon(skinPath + "/string/start.png");
        // configuration button
        BT_CONFIG = new ImageIcon(skinPath + "/string/config.png");
        // pause
        PAUSE = new ImageIcon(skinPath + "/string/pause.png").getImage();
        NEXT_ACT = new Image[GameConfig.getSystemConfig().getTypeConfig().size()];
        for(int i= 0; i<NEXT_ACT.length; i++){
            NEXT_ACT[i] = new ImageIcon(skinPath + "/game/" + i + ".png").getImage();
        }
        // get the background
        File dir = new File(skinPath + "/background");
        File[] dirs = dir.listFiles();
        BG_LIST = new ArrayList<>();
        for(File file: dirs){
            if(file.isDirectory() || file.isHidden()) continue;
            BG_LIST.add(new ImageIcon(file.getPath()).getImage());
            // System.out.println(file.getPath());
        }
    }

}
