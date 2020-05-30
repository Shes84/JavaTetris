package config;

import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.Document;

public class ConfigReader {
     // construct a SAXReader to read cfg.tld
    public static void readConfig() throws Exception{
        SAXReader reader = new SAXReader();
        Document doc = reader.read("data/cfg.tld");
        Element game = doc.getRootElement();
        Element frame = game.element("frame");

    }

    public static void main(String[] args) throws Exception{
        readConfig();
    }
}
