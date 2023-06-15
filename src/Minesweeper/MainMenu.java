package Minesweeper;

/**
 * @author Mikail.Hussain
 *
 * This is the main menu of the Minesweeper game
 * It includes a title, a rules button, a play button, and a quit button
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

        // Set frame title
        this.setTitle("Minesweeper");

        // Create buttons (rules, play, quit)
        rules = new JButton("Rules");
        play = new JButton("Play");
        quit = new JButton("Quit");

        // Set button fonts
        rules.setFont(new Font("Roboto", Font.PLAIN, 25));
        play.setFont(new Font("Roboto", Font.PLAIN, 25));
        quit.setFont(new Font("Roboto", Font.PLAIN, 25));

        // Make the quit button and action button
        quit.addActionListener(this);

        // Create title
        JLabel label = new JLabel("MINESWEEPER");
        label.setFont(new Font("Roboto", Font.PLAIN, 50));

        // Create bomb icon
        ImageIcon bomb = new ImageIcon("bomb3.png");
        JLabel bombLabel = new JLabel(bomb);


        // Create panel and set layout
        JPanel panel = new JPanel();
        BoxLayout boxlayout = new BoxLayout(panel, BoxLayout.Y_AXIS);


        // Align the buttons/title in the center of the menu
        bombLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        rules.setAlignmentX(Component.CENTER_ALIGNMENT);
        play.setAlignmentX(Component.CENTER_ALIGNMENT);
        quit.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add all components to the panel, and add the panel to the frame
        panel.setLayout(boxlayout);
        panel.add(bombLabel);
        panel.add(label);
        panel.add(rules);
        panel.add(play);
        panel.add(quit);
        this.add(panel);

        // Make the menu close when the window closes and make the frame and panel visible
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel.setBackground(Color.GRAY); // Make the panel gray
        panel.setVisible(true);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new MainMenu();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.exit(0); // Make the menu close when the quit button is pressed
    }
}