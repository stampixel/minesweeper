package Minesweeper;

/**
 * @author Mikail
 */

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainMenu extends Window implements ActionListener {
    JButton rules;
    JButton play;
    JButton quit;

    MainMenu() {
        super();

        rules = new JButton("Rules");
        play = new JButton("Play");
        quit = new JButton("Quit");
        JLabel label = new JLabel("MINESWEEPER");
        label.setFont(new Font("Serif", Font.PLAIN, 50));

        JPanel panel = new JPanel();
        BoxLayout boxlayout = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setBorder(new EmptyBorder(new Insets(600, 300, 680, 420)));

        rules.addActionListener(this);
        panel.setLayout(boxlayout);
        panel.add(label);
        panel.add(rules);
        panel.add(play);
        panel.add(quit);
        this.add(panel);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel.setBackground(Color.GRAY);
        panel.setVisible(true);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new MainMenu();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}

