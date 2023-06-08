package Minesweeper;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

/**
 * KEVIN: REFORMATED
 *
 * @author Andrew
 * Version 2
 * Updated GUI Look
 * Added quit menu
 * Added lost image
 * added action for quit button
 * Need to add action for calling the game again
 * displayed text better
 * May add time spent, Still need to wait for time implemented in other class
 * will add time, and difficulty into constructor
 * Updated localization files
 */

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class loseWindow extends Window implements ActionListener {

    //create buttons
    JButton mainMenu;
    JButton quit;
    JLabel scoreText;
    JTextArea scoreboard;
    File leaderboardFile;

    ImageIcon lostIMG;
    JLabel imageHolder;
    JPanel topPanel;
    JPanel middlePanel;
    GridBagConstraints constraints;
    JPanel bottomPanel;


    loseWindow() throws Exception {
        mainMenu = new JButton("Main Menu");
        quit = new JButton("Quit");

        this.setLayout(new BorderLayout(0, 0));

        //create label to display you lose text
        scoreText = new JLabel();
        scoreText.setText("Leaderboard");
        scoreText.setFont(new Font("consolas", Font.PLAIN, 22));
        scoreText.setHorizontalTextPosition(JLabel.CENTER);
        scoreText.setVerticalTextPosition(JLabel.CENTER);

        //create a area on the window to display leaderboard
        scoreboard = new JTextArea();
        scoreboard.setPreferredSize(new Dimension(300, 150));
        scoreboard.setFont(new Font("consolas", Font.PLAIN, 16));
        scoreboard.setEditable(false);
        scoreboard.setBackground(Color.gray);

        // Read contents of the leaderboard file
        leaderboardFile = new File("leaderboard.txt");
        try (Scanner input = new Scanner(leaderboardFile)) {
            List<String> scores = new ArrayList<>();
            while (input.hasNextLine()) {
                String line = input.nextLine();
                scores.add(line);
            }
            input.close();

            // Set the contents of the scoreboard text field
            StringBuilder sb = new StringBuilder();
            for (String score : scores) {
                sb.append(score).append("\n");
            }
            scoreboard.setText(sb.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //creating image and adding to label
        lostIMG = new ImageIcon("lost.jpg");
        imageHolder = new JLabel(lostIMG);
        topPanel = new JPanel();
        topPanel.add(imageHolder);

        //create middle panel, and center panel to center both the text on top of the score board
        //and leader board scores
        middlePanel = new JPanel(new GridBagLayout());
        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weighty = 1.0;
        constraints.anchor = GridBagConstraints.CENTER;
        middlePanel.add(scoreText, constraints);

        constraints.gridy = 1;
        constraints.weighty = 0.0;
        middlePanel.add(scoreboard, constraints);

        //create bottom panel
        bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 20));
        bottomPanel.add(mainMenu, BorderLayout.WEST);
        bottomPanel.add(quit, BorderLayout.EAST);


        //set background color
        topPanel.setBackground(Color.GRAY);
        middlePanel.setBackground(Color.GRAY);
        bottomPanel.setBackground(Color.GRAY);

        //add action listners to buttons
        mainMenu.addActionListener(this);
        quit.addActionListener(this);

        //button settings
        mainMenu.setPreferredSize(new Dimension(150, 70));
        mainMenu.setBackground(Color.RED);
        mainMenu.setFont(new Font("consolas", Font.PLAIN, 20));
        quit.setPreferredSize(new Dimension(150, 70));
        quit.setBackground(Color.RED);
        quit.setFont(new Font("consolas", Font.PLAIN, 20));

        //frame settings
        this.add(topPanel, BorderLayout.NORTH);
        this.add(middlePanel, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);
        this.pack();
        this.setVisible(true);
    }

    //main method creates the lost window
    public static void main(String[] args) throws Exception {
        new loseWindow();
    }

    //button actions
    public void actionPerformed(ActionEvent e) {
        //when quit button is pressed close window
        if (e.getSource() == quit) {
            dispose();
        } else if (e.getSource() == mainMenu) {
            //when main menu button is clicked
            //will restart game
            try {
                restartGame();
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    }

    //method will restart game
    private void restartGame() throws Exception {
        new MainMenu();
    }
}