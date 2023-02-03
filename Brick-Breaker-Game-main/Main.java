package org.example;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        JFrame frame=new JFrame();
        frame.setTitle("Brick Bricker");
        frame.setSize(700,600);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GamePlay gameplay=new GamePlay();
        frame.add(gameplay);
        frame.setVisible(true);


    }
}