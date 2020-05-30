package config;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.*;


public class GameConfig {

    private static FrameConfig FRAME_CONFIG = null;

    private static DataConfig DATA_CONFIG = null;

    private static SystemConfig SYSTEM_CONFIG = null;

    private static boolean debug = false;

    static {
        try {
            if(debug){
                // 创建XML读取器
                SAXReader reader = new SAXReader();
                // 读取XML数据
                Document doc = null;
                doc = reader.read("data/cfg.tld");
                // 获得XML文件的根节点
                Element game = doc.getRootElement();
                // 创建界面配置对象
                FRAME_CONFIG = new FrameConfig(game.element("frame"));
                // 创建数据访问配置对象
                DATA_CONFIG = new DataConfig(game.element("data"));
                // 创建系统配置对象
                SYSTEM_CONFIG = new SystemConfig(game.element("system"));

            }else{
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream("data/frameCfg.dat"));
                FRAME_CONFIG = (FrameConfig) ois.readObject();
                ois = new ObjectInputStream(new FileInputStream("data/dataCfg.dat"));
                DATA_CONFIG = (DataConfig) ois.readObject();

                SAXReader reader = new SAXReader();
                // 读取XML数据
                Document doc = null;
                doc = reader.read("data/cfg.tld");
                // 获得XML文件的根节点
                Element game = doc.getRootElement();

                SYSTEM_CONFIG = new SystemConfig(game.element("system"));

                //ois = new ObjectInputStream(new FileInputStream("data/systemCfg.dat"));
               // SYSTEM_CONFIG = (SystemConfig) ois.readObject();
                ois.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * 构造器私有化
     */
    private GameConfig(){}


    /**
     * 获得窗口配置
     */
    public static FrameConfig getFrameConfig(){
       return FRAME_CONFIG;
    }

    /**
     * 获得系统配置
     */
    public static SystemConfig getSystemConfig(){
        return SYSTEM_CONFIG;
    }

    /**
     * 获得数据配置
     */
    public static DataConfig getDataConfig(){
        return DATA_CONFIG;
    }

   /** public static void main(String[] args) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("data/frameCfg.dat"));
        oos.writeObject(FRAME_CONFIG);
        oos = new ObjectOutputStream(new FileOutputStream("data/systemCfg.dat"));
        oos.writeObject(SYSTEM_CONFIG);
        oos = new ObjectOutputStream(new FileOutputStream("data/dataCfg.dat"));
        oos.writeObject(DATA_CONFIG);
        oos.close();
    }*/

}
