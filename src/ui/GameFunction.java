package ui;

public class GameFunction {

    /**
     *  根据级别来调整睡眠时间
     * @param level 级别
     * @return 睡眠时间
     */
    public static long getSleepByLevel(int level){
        long sleep = -40* level + 740;
        sleep = sleep < 100? 100: sleep;
        return sleep;
    }
}
