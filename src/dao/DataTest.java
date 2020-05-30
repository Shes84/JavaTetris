package dao;

import dto.Player;

import java.util.ArrayList;
import java.util.List;

public class DataTest implements Data {

    @Override
    public List<Player> loadData(){
       List<Player> players = new ArrayList<Player>();
        players.add(new Player("a", 1));
        players.add(new Player("a", 10));
        players.add(new Player("a", 100));
       players.add(new Player("a", 1000));
        players.add(new Player("a", 10000));
        return players;
    };

    @Override
    public void saveData(Player player){
       // System.out.println();
    };
}
