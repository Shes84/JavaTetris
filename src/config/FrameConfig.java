package config;


import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.dom4j.Element;

public class FrameConfig implements Serializable {

    private final String title;

    private final int windowUp;

    private final int width;

    private final int height;

    private final int padding;

    private final int border;

    private final int sizeRoll;

    private final int loseIdx;

    private ButtonConfig buttonConfig;

    /**
     *  图层属性
     *
     */
    private List<LayerConfig> layersConfig;



    public FrameConfig(Element frame){
        this.width = Integer.parseInt(frame.attributeValue("width"));
        this.height = Integer.parseInt(frame.attributeValue("height"));
        this.border = Integer.parseInt(frame.attributeValue("border"));
        this.padding = Integer.parseInt(frame.attributeValue("padding"));
        this.title = frame.attributeValue("title");
        this.windowUp = Integer.parseInt(frame.attributeValue("windowUp"));
        this.sizeRoll = Integer.parseInt(frame.attributeValue("sizeRoll"));
        this.loseIdx = Integer.parseInt(frame.attributeValue("loseIdx"));

        List<Element> layers = frame.elements("layer");

        layersConfig = new ArrayList<LayerConfig>();

        for(Element layer:layers){
            LayerConfig lc = new LayerConfig(
                    layer.attributeValue("class"),
                    Integer.parseInt(layer.attributeValue("x")),
                    Integer.parseInt(layer.attributeValue("y")),
                    Integer.parseInt(layer.attributeValue("w")),
                    Integer.parseInt(layer.attributeValue("h"))
            );
            layersConfig.add(lc);
        }

        // 初始化按钮属性
        buttonConfig = new ButtonConfig(frame.element("button"));
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public int getPadding(){
        return padding;
    }

    public int getBorder(){
        return border;
    }

    public int getWindowUp(){
        return windowUp;
    }

    public List<LayerConfig> getLayersConfig(){
        return layersConfig;
    }

    public String getTitle() {
        return title;
    }

    public int getSizeRoll() {
        return sizeRoll;
    }

    public int getLoseIdx() {
        return loseIdx;
    }

    public ButtonConfig getButtonConfig(){
        return buttonConfig;
    }

}

