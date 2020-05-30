package config;

import org.dom4j.Element;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SystemConfig implements Serializable {

    private final int minX;

    private final int maxX;

    private final int minY;

    private final int maxY;

    private final int levelUp;

    private static List<Point[]> typeConfig;

    private static List<Boolean> typeRound;

    private static Map<Integer, Integer> plusPoints;

    public SystemConfig(Element system) {
        this.minX = Integer.parseInt(system.attributeValue("minX"));
        this.maxX = Integer.parseInt(system.attributeValue("maxX"));
        this.minY = Integer.parseInt(system.attributeValue("minY"));
        this.maxY = Integer.parseInt(system.attributeValue("maxY"));
        this.levelUp = Integer.parseInt(system.attributeValue("levelUp"));
        List<Element> rects = system.elements("rect");
        typeConfig = new ArrayList<Point[]>();
        typeRound = new ArrayList<Boolean>();

        for(Element rect: rects){
            // 是否可旋转
            this.typeRound.add(Boolean.parseBoolean(rect.attributeValue("round")));
            List<Element> pointConfig = rect.elements("point");
            // 创建point数组
            Point[] points = new Point[pointConfig.size()];
            // 初始化point数组
            for(int i=0; i<points.length; i++){
                int x = Integer.parseInt(pointConfig.get(i).attributeValue("x"));
                int Y = Integer.parseInt(pointConfig.get(i).attributeValue("Y"));
                points[i] = new Point(x, Y);
            }
            // 把point数组放到typeConfig中
            typeConfig.add(points);

            this.plusPoints = new HashMap<>();
            List<Element> plusPointCfg = system.elements("plusPoint");

            for(Element e: plusPointCfg){
                int rm = Integer.parseInt(e.attributeValue("rm"));
                int point = Integer.parseInt(e.attributeValue("point"));
                this.plusPoints.put(rm, point);
            }


        }


    }

    public int getMaxX() {
        return maxX;
    }

    public int getMaxY() {
        return maxY;
    }

    public int getMinX() {
        return minX;
    }

    public int getMinY() {
        return minY;
    }

    public List<Point[]> getTypeConfig() {
        return typeConfig;
    }

    public int getLevelUp() {
        return levelUp;
    }

    public List<Boolean> getTypeRound() {
        return typeRound;
    }

    public Map<Integer, Integer> getPlusPoints(){
        return plusPoints;
    }
}
