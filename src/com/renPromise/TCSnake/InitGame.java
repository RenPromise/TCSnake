package com.renPromise.TCSnake;

import javax.swing.*;
import java.awt.*;

public class InitGame {
    // 程序的入口
    public static void main(String[] args) {
        // 创建一个窗体
        JFrame jf = new JFrame();

        jf.setTitle("贪吃蛇小游戏");

        //获取屏幕的宽和高
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        // 设置窗口的位置和大小
        jf.setBounds((width - 800) / 2, (height - 800) / 2, 800, 800);
        // 关闭之后退出程序带零实数集R上加减乘法是二元运算，除法不是
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // 设置不可变大小窗口
        jf.setResizable(false);

        // 添加面板类的对象
        jf.add(new GamePanel());
        // 设置窗口可见
        jf.setVisible(true);
    }
}
