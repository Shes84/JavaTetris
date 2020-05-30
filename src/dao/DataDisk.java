package dao;

import dto.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataDisk implements Data {

    private final String filePath;

    public DataDisk(HashMap<String, String> param){
        this.filePath = param.get("path");
    }


    @Override
    public List<Player> loadData() {

        ObjectInputStream ois = null;
        List<Player> players = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(filePath));
            players = (List<Player>)ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try{
                ois.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        return players;
    }

    @Override
    public void saveData(Player player) {
        // get the data
        List<Player> players = this.loadData();
        // add the new data
        players.add(player);
        // write the new data
        ObjectOutputStream oos = null;

        try{
            oos = new ObjectOutputStream(
                    new FileOutputStream(filePath));
            oos.writeObject(player);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                oos.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

    }


   // public static void main(String[] args) throws IOException {
     //   DataDisk dd = new DataDisk();

     //   List<Player> players = new ArrayList<>();
       // players.add(new Player("Amy", 654));
       // players.add(new Player("Bob", 843));
      //  players.add(new Player("Cindy", 658));
      //  players.add(new Player("Dave", 733));
      // players.add(new Player("Mike", 789));

     //   dd.saveData(players);
      //  System.out.println(1111);
      //  System.out.println("saved");

      //  List<Player> dataFromDisk = dd.loadData();

     //   for(Player p: dataFromDisk){
      //      System.out.println(p.getName() + ":" + p.getPoint());
     //   }

   // }
}
