package config;

import org.dom4j.Element;

import java.io.Serializable;

public class ButtonConfig implements Serializable {

    private final int buttonW;

    private final int buttonH;

    private final int startX;

    private final int startY;

    private final int userConfigX;

    private final int userConfigY;


    public ButtonConfig(Element button){
        //初始化buttonW
        this.buttonW = Integer.parseInt(button.attributeValue("w"));
        //初始化buttonH
        this.buttonH = Integer.parseInt(button.attributeValue("h"));
        this.startX = Integer.parseInt(button.element("start").attributeValue("x"));
        this.startY = Integer.parseInt(button.element("start").attributeValue("y"));
        this.userConfigX = Integer.parseInt(button.element("userConfig").attributeValue("x"));
        this.userConfigY = Integer.parseInt(button.element("userConfig").attributeValue("y"));


    }

    public int getButtonH() {
        return buttonH;
    }

    public int getButtonW() {
        return buttonW;
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public int getUserConfigX() {
        return userConfigX;
    }

    public int getUserConfigY() {
        return userConfigY;
    }
}
