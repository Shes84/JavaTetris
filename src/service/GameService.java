package service;

public interface GameService {

    /**
     * 方向键上
     * @return
     */
    public boolean keyUp();

    /**
     * 方向键下
     * @return
     */
    public boolean keyDown();

    /**
     * 方向键左
     * @return
     */
    public boolean keyLeft();

    /**
     * 方向键右
     * @return
     */
    public boolean keyRight();

    /**
     * 三角
     * @return
     */
    public boolean keyFunctionUp();

    /**
     * 大叉
     * @return
     */
    public boolean keyFunctionDown();

    /**
     * 方块
     * @return
     */
    public boolean keyFunctionLeft();

    /**
     * 圆圈
     * @return
     */
    public boolean keyFunctionRight();

    /**
     * 启动主线程
     */
    public void startGame();

    /**
     * 游戏主要行为
     */
    public void mainAction();
}
