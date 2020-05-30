package dao;

import dto.Player;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class DataBase implements Data {

    private final String driver;

    private final String dbUrl;

    private final String dbUser;

    private final String dbPwd;


    public DataBase(HashMap<String, String> param){
        this.driver = param.get("driver");
        this.dbUrl = param.get("dbUrl");
        this.dbUser = param.get("dbUser");
        this.dbPwd = param.get("dbPwd");

    }

    @Override
    public List<Player> loadData() {
        return null;
    }

    @Override
    public void saveData(Player player){

    }
}
