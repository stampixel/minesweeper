package Minesweeper;

import Minesweeper.BottomPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.swing.border.LineBorder;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


/**
 * @author Mikail.Hussain
 *
 * This class creates a window which allows the user to enter their preferred difficulty
 */

public class DifficultySelection extends Window{

    JButton easy;
    JButton medium;
    JButton hard;
    JButton custom;
    JButton quit;

    int width;
    int height;
    int bombs;

    DifficultySelection() throws Exception {
        super();

        // Create border for buttons and labels
        LineBorder line = new LineBorder(Color.gray, 5, true);

        // Set frame title
        this.setTitle("Minesweeper");

        // Create buttons (rules, play, quit)
        easy = new JButton("Easy (9x9)");
        medium = new JButton("Medium (16x16)");
        hard = new JButton("Hard (24x24)");
        custom = new JButton("Custom");
        quit = new JButton("Quit");

        // Listen to the quit button for input
        quit.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        System.exit(0);
                    }
                }
        );

        // Listen to the easy button for input
        easy.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        new BoardWindow(9, 9, 10);
                        dispose();
                    }
                }
        );

        // Listen to the medium button for input
        medium.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        new BoardWindow(16, 16, 40);
                        dispose();
                    }
                }
        );

        // Listen to the medium button for input
        hard.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        new BoardWindow(24, 24, 99);
                        dispose();
                    }
                }
        );

        // Listen to the custom button for input
        custom.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        getDimensions();
                        new BoardWindow(width, height, bombs);
                        dispose();
                    }
                }
        );


        // Set button fonts
        easy.setFont(new Font("Roboto", Font.PLAIN, 25));
        medium.setFont(new Font("Roboto", Font.PLAIN, 25));
        hard.setFont(new Font("Roboto", Font.PLAIN, 25));
        custom.setFont(new Font("Roboto", Font.PLAIN, 25));
        quit.setFont(new Font("Roboto", Font.PLAIN, 25));

        // Set button size
        easy.setMaximumSize(new Dimension(250, 50));
        medium.setMaximumSize(new Dimension(250, 50));
        hard.setMaximumSize(new Dimension(250, 50));
        custom.setMaximumSize(new Dimension(250, 50));
        quit.setMaximumSize(new Dimension(150, 50));

        // Set button borders
        easy.setBorder(line);
        medium.setBorder(line);
        hard.setBorder(line);
        custom.setBorder(line);
        quit.setBorder(line);

        // Set buttons opaque
        easy.setOpaque(true);
        medium.setOpaque(true);
        hard.setOpaque(true);
        custom.setOpaque(true);
        quit.setOpaque(true);

        // Set button backgrounds red
        easy.setBackground(Color.LIGHT_GRAY);
        medium.setBackground(Color.LIGHT_GRAY);
        hard.setBackground(Color.LIGHT_GRAY);
        custom.setBackground(Color.LIGHT_GRAY);
        quit.setBackground(Color.LIGHT_GRAY);


        // Create title
        JLabel label = new JLabel("CHOOSE DIFFICULTY");
        label.setFont(new Font("Roboto", Font.PLAIN, 50));
        label.setBorder(line);
        label.setOpaque(true);
        label.setBackground(Color.LIGHT_GRAY);

        // Create bomb image
        ImageIcon bomb = new ImageIcon("MenuBomb.png");
        JLabel bombLabel = new JLabel(bomb);

        // Create panel, set transparent, and set layout
        JPanel panel = new JPanel();
        panel.setBackground(new Color(0, 0, 0, 0));
        BoxLayout boxlayout = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(boxlayout);

        // Create background panel
        BottomPanel background = new BottomPanel();

        // Align the buttons/title in the center of the menu
        bombLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        easy.setAlignmentX(Component.CENTER_ALIGNMENT);
        medium.setAlignmentX(Component.CENTER_ALIGNMENT);
        hard.setAlignmentX(Component.CENTER_ALIGNMENT);
        custom.setAlignmentX(Component.CENTER_ALIGNMENT);
        quit.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add all components to the panel, and add the panel to the frame
        panel.add(Box.createRigidArea(new Dimension(10, 30)));
        panel.add(bombLabel);
        panel.add(Box.createRigidArea(new Dimension(10, 10)));
        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(10, 10)));
        panel.add(easy);
        panel.add(Box.createRigidArea(new Dimension(10, 10)));
        panel.add(medium);
        panel.add(Box.createRigidArea(new Dimension(10, 10)));
        panel.add(hard);
        panel.add(Box.createRigidArea(new Dimension(10, 10)));
        panel.add(custom);
        panel.add(Box.createRigidArea(new Dimension(10, 10)));
        panel.add(quit);
        panel.add(Box.createRigidArea(new Dimension(10, 10)));
        background.add(panel);
        this.add(background);


        // Make the menu close when the window closes and make the frame and panels
        // visible
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

    }

    /**
     * Print menu
     *
     * @param args
     */

    public static void main(String[] args) throws Exception {
        new DifficultySelection();
        playMusic("Music.wav");


    }

    public static void playMusic(String location) {
        try {
            File musicPath = new File(location);

            if (musicPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
                clip.loop(Clip.LOOP_CONTINUOUSLY);
                Thread.sleep(clip.getMicrosecondLength() / 1000);
            } else {
                System.out.println("Can't find file");
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * This method gets the values of the width, height, and amount of bombs that the user wants
     */
    public void getDimensions() {
        boolean quit = false;
        width = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter the board width (6-24):"));
        while (quit == false) {
            if (width <= 5) {
                JOptionPane.showMessageDialog(this, "The value is too small! Try again.");
                width = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter the board width (6-24):"));
            } else if (width > 24) {
                JOptionPane.showMessageDialog(this, "The value is too big! Try again.");
                width = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter the board width (6-24):"));
            } else {
                quit = true;
            }
        }

        quit = false;
        height = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter the board height (6-24):"));
        while (quit == false) {
            if (height <= 5) {
                JOptionPane.showMessageDialog(this, "The value is too small! Try again.");
                height = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter the board height (6-24):"));
            } else if (height > 24) {
                JOptionPane.showMessageDialog(this, "The value is too big! Try again.");
                height = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter the board height (6-24):"));
            } else {
                quit = true;
            }
        }

        quit = false;
        bombs = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter the amount of bombs (5-99):"));
        while (quit == false) {
            if (bombs <= 4) {
                JOptionPane.showMessageDialog(this, "The value is too small! Try again.");
                bombs = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter the amount of bombs (5-99):"));
            } else if (bombs > 99) {
                JOptionPane.showMessageDialog(this, "The value is too big! Try again.");
                bombs = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter the amount of bombs(5-99):"));
            } else {
                quit = true;
            }
        }
    }

}