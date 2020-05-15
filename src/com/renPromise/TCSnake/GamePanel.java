package com.renPromise.TCSnake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GamePanel extends JPanel { // 继承面板类，才能具备面板功能

    int length;
    // 存放蛇x轴的坐标
    int[] snakeX = new int[600];
    // 存放蛇y轴的坐标
    int[] snakeY = new int[600];
    int delay = 100;

    // 食物坐标
    int foodX;
    int foodY;

    int score;
    boolean isStart = false;
    boolean isDead = false;

    String direction; // 蛇头的方向

    // 蛇属性初始化的方法
    public void init() {
        // 初始化蛇的长度；
        length = 3;

        direction = "R";

        snakeX[0] = 200;
        snakeY[0] = 375;

        //第一节身子
        snakeX[1] = 175;
        snakeY[1] = 375;

        // 第二节
        snakeX[2] = 150;
        snakeX[2] = 375;

        foodX = 325;
        foodY = 200;

        // 初始化中积分清零
        score = 0;
    }

    public GamePanel() {
        init();

        // 获取焦点
        this.setFocusable(true);
        // 添加一个键盘监听
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                int key = e.getKeyCode(); // 获取按键对应的码

                if (key == KeyEvent.VK_SPACE) {
                    if (isDead) {
                        init();
                        isDead = false;
                    } else {
                        isStart = !isStart;
                        repaint();
                    }
                }
                switch (key) {
                    case KeyEvent.VK_UP : direction = "U";
                    break;
                    case KeyEvent.VK_DOWN : direction = "D";
                    break;
                    case KeyEvent.VK_LEFT : direction = "L";
                    break;
                    case KeyEvent.VK_RIGHT : direction = "R";
                    break;
                    case KeyEvent.VK_1 : delay = 100;
                    break;
                    case KeyEvent.VK_2 : delay = 50;
                    break;
                    case KeyEvent.VK_3 : delay = 20;
                    break;
                }
            }
        });

        new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 每100ms蛇要动一下
                if (isStart && isDead == false) {// 游戏开始
                    // 身子先动
                    for (int i = length - 1; i > 0; i--) {
                        snakeX[i] = snakeX[i - 1];
                        snakeY[i] = snakeY[i - 1];
                    }
                    //再让头动：根据蛇头的方向来判断坐标的走向：
                    if (direction.equals("R")) {
                        snakeX[0] = snakeX[0] + 25;
                    }
                    if (direction.equals("L")) {
                        snakeX[0] = snakeX[0] - 25;
                    }
                    if (direction.equals("U")) {
                        snakeY[0] = snakeY[0] - 25;
                    }
                    if (direction.equals("D")) {
                        snakeY[0] = snakeY[0] + 25;
                    }

                    // 边界问题处理；注意边界处最好是25的倍数
                    if (snakeX[0] > 750) { //右边界
                        snakeX[0] = 25;
                    }
                    if (snakeX[0] < 25) {
                        snakeX[0] = 750;
                    }
                    if (snakeY[0] < 75) {//上边界
                        snakeY[0] = 725;
                    }
                    if (snakeY[0] > 725) {//下边界
                        snakeY[0] = 75;
                    }

                    //当蛇头和食物碰撞的时候，食物的坐标跟着改变
                    if (snakeX[0] == foodX && snakeY[0] == foodY) {
                        // 蛇长度 + 1；
                        length++;
                        // 食物的坐标要改变
                        foodX = 25 + 25 * (new Random().nextInt(28));
                        foodY = 100 + 25 * (new Random().nextInt(24));
                        score = (length - 3) * 10;
                    }

                    //死亡的判定逻辑
                    for (int i =1; i < length; i++) {
                        if (snakeX[0] == snakeX[i] && snakeY[0] == snakeY[i]) {
                            isDead = true;
                        }
                    }

                    repaint();
                }
            }
        }).start();
    }

    // 面板中的内容就在这个方法中，这个方法不用我们自己调用，底层自己调用
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // 清屏效果
        // 设置背景颜色；
        this.setBackground(new Color(163, 209, 208));
        // 画图片
        Images.headerImg.paintIcon(this, g, 10, 10);
        //调节一下画笔的颜色
        g.setColor(new Color(164, 147, 125));
        // 矩形范围
        g.fillRect(10, 70,770,685);

        // 画蛇头
        if (direction.equals("L")) {
            Images.leftImg.paintIcon(this, g, snakeX[0], snakeY[0]);
        } else if (direction.equals("R")) {
            Images.rightImg.paintIcon(this, g, snakeX[0], snakeY[0]);
        } else if (direction.equals("U")) {
            Images.upImg.paintIcon(this, g, snakeX[0], snakeY[0]);
        } else if (direction.equals("D")) {
            Images.downImg.paintIcon(this, g, snakeX[0], snakeY[0]);
        }

        //蛇身子
        for (int i = 1; i < length; i++) {
            Images.bodyImg.paintIcon(this, g, snakeX[i], snakeY[i]);
        }
        Images.bodyImg.paintIcon(this, g, snakeX[2], snakeY[2]);

        // 画上食物
        Images.foodImg.paintIcon(this,g, foodX, foodY);
        // 画上记分板
        g.setFont(new Font("微软雅黑", Font.BOLD, 20));
        g.setColor(new Color(255,255,255));
        g.drawString("积分" + score, 620, 40);

        if (isStart == false) {
            // 在面板中间画一行文字：
            g.setColor(new Color(169, 17, 22));
            g.setFont(new Font("微软雅黑", Font.BOLD, 40));
            g.drawString("点击空格开始游戏", 250, 330);
        }

        if (isDead) {
            g.setColor(new Color(169, 17, 22));
            g.setFont(new Font("微软雅黑", Font.BOLD, 30));
            g.drawString("蛇吃掉了自己的尾巴！\n按空格键重新开始游戏", 150, 330);
            g.drawString("\n您的最终积分为: " + score, 250, 480);
        }
    }
}
