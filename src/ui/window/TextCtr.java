package ui.window;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class TextCtr extends JTextField {

    private int keyCode;

    private final String methodName;

    public TextCtr(int x, int y, int w, int h, String methodName){
        // 设置位置
        this.setBounds(x, y, w, h);
        // 初始化方法名
        this.methodName = methodName;
        // 设置键盘监听器
        this.addKeyListener(new KeyListener() {

            public void keyTyped(KeyEvent e) {}


            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {
                setKeyCode(e.getKeyCode());
            }
        });
    }

    public int getKeyCode(){
        return keyCode;
    }

    public void setKeyCode(int keyCode) {
        this.keyCode = keyCode;
        this.setText(KeyEvent.getKeyText(this.keyCode));
    }

    public String getMethodName() {
        return methodName;
    }
}
