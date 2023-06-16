package Minesweeper;

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
 * This is the main menu of the Minesweeper game
 * It includes a title, a rules button, a play button, and a quit button
 */

public class MainMenu extends Window implements ActionListener {

    JButton rules;
    JButton play;
    JButton quit;

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
        quit.addActionListener(this);

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
        ImageIcon bomb = new ImageIcon("bomb3.png");
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
     *
     * @param args
     */

    public static void main(String[] args) {
        new MainMenu();
        playMusic("Windmill_Isle.wav");


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
     * Action button method
     */

    @Override
    public void actionPerformed(ActionEvent e) {
        System.exit(0); // Make the menu close when the quit button is pressed
    }


    /**
     * This class creates a panel which serves as the background panel for the main menu
     * of a minesweeper game. The class prints an image onto the panel so that other panels
     * can be added on to it.
     *
     * @author mikail.hussain
     */

    class BottomPanel extends JPanel {
        Image img;

        @Override
        protected void paintComponent(Graphics g) {
            try {
                img = ImageIO.read(new File("mineBoard.png"));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            g.drawImage(img, 0, 0, null);
        }
    }
}