package Minesweeper;

import javax.swing.*;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.*;
import java.io.File;
import javax.swing.border.LineBorder;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


/**
 * @author Mikail.Hussain
 *
 * This is the main menu of the Minesweeper game
 * It includes a title, a rules button, a play button, and a quit button
 */

public class MainMenu extends Window{

    JButton rules;
    JButton play;
    JButton quit;
    JPanel panel;
    BottomPanel background;

    MainMenu() {
        super();

        // Create border for buttons and labels
        LineBorder line = new LineBorder(Color.gray, 5, true);

        // Set frame title
        this.setTitle("Minesweeper");

        // Create buttons (rules, play, quit)
        rules = new JButton("Rules");
        play = new JButton("Play");
        quit = new JButton("Quit");

        // Listen to the quit button for input
        quit.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        System.exit(0);
                    }
                }
        );

        // Listen to the play button for input
        play.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        new DifficultySelection();
                        dispose();
                    }
                }
        );

        // Listen to the rules button for input
        rules.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        new Rules();
                        dispose();
                    }
                }
        );

        // Set button fonts
        rules.setFont(new Font("Roboto", Font.PLAIN, 25));
        play.setFont(new Font("Roboto", Font.PLAIN, 25));
        quit.setFont(new Font("Roboto", Font.PLAIN, 25));

        // Set button size
        rules.setMaximumSize(new Dimension(150, 50));
        play.setMaximumSize(new Dimension(150, 50));
        quit.setMaximumSize(new Dimension(150, 50));

        // Set button borders
        rules.setBorder(line);
        play.setBorder(line);
        quit.setBorder(line);

        // Set buttons opaque
        rules.setOpaque(true);
        play.setOpaque(true);
        quit.setOpaque(true);

        // Set button backgrounds red
        rules.setBackground(Color.LIGHT_GRAY);
        play.setBackground(Color.LIGHT_GRAY);
        quit.setBackground(Color.LIGHT_GRAY);


        // Create title
        JLabel label = new JLabel("MINESWEEPER");
        label.setFont(new Font("Roboto", Font.PLAIN, 50));
        label.setBorder(line);
        label.setOpaque(true);
        label.setBackground(Color.LIGHT_GRAY);

        // Create bomb image
        ImageIcon bomb = new ImageIcon("MenuBomb.png");
        JLabel bombLabel = new JLabel(bomb);

        // Create panel, set transparent, and set layout
        panel = new JPanel();
        panel.setBackground(new Color(0, 0, 0, 0));
        BoxLayout boxlayout = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(boxlayout);

        // Create background panel
        background = new BottomPanel();

        // Align the buttons/title in the center of the menu
        bombLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        rules.setAlignmentX(Component.CENTER_ALIGNMENT);
        play.setAlignmentX(Component.CENTER_ALIGNMENT);
        quit.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add all components to the panel, and add the panel to the frame
        panel.add(Box.createRigidArea(new Dimension(10, 30)));
        panel.add(bombLabel);
        panel.add(Box.createRigidArea(new Dimension(10, 10)));
        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(10, 10)));
        panel.add(rules);
        panel.add(Box.createRigidArea(new Dimension(10, 10)));
        panel.add(play);
        panel.add(Box.createRigidArea(new Dimension(10, 10)));
        panel.add(quit);
        background.add(panel);
        this.add(background);



        // Make the menu close when the window closes and make the frame and panels
        // visible
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

    }

    /**
     * Print menu
     * @param args
     */

    public static void main(String[] args) {
        // Play music
        playMusic("Music.wav");
        new MainMenu();
    }

    /**
     * This class plays music
     * @param location
     */
    public static void playMusic(String location) {
        try {
            File musicPath = new File(location);

            if (musicPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                System.out.println("Can't find file");
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}