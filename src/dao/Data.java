package dao;

import dto.Player;

import java.io.IOException;
import java.util.List;

/**
 * 数据持久层接口
 */
public interface Data {

    /**
     * load the data
     */
    public List<Player> loadData();

    /**
     * save the data
     */

    public void saveData(Player player) throws IOException;


}
