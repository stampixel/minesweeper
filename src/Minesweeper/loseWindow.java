package Minesweeper;
/**
 * @author Andrew
 * Version 4
 * Changed constructor to have minutes, and seconds
 * Updated localization files
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;



public class loseWindow extends Window implements ActionListener {

    //create buttons
    JButton mainMenu;
    JButton quit;
    JLabel leaderboardText;
    JTextArea scoreboard;
    File leaderboardFile;

    ImageIcon lostIMG;
    JLabel imageHolder;
    JPanel topPanel;
    JPanel middlePanel;
    GridBagConstraints middleConstraints;
    JPanel bottomPanel;

    int minutes;
    int seconds;

    loseWindow(int gameMin, int gameSec) throws Exception {
        minutes = gameMin;
        seconds = gameSec;
        mainMenu = new JButton("Main Menu");
        quit = new JButton("Quit");

        this.setLayout(new BorderLayout(0, 0));

        //create label to display you lose text
        leaderboardText = new JLabel();
        leaderboardText.setText("Leaderboard");
        leaderboardText.setOpaque(true);
        leaderboardText.setBackground(Color.lightGray);
        leaderboardText.setForeground(Color.BLACK);
        leaderboardText.setFont(new Font("consolas", Font.PLAIN, 30));
        leaderboardText.setHorizontalTextPosition(JLabel.CENTER);
        leaderboardText.setVerticalTextPosition(JLabel.CENTER);

        //create a area on the window to display leaderboard
        scoreboard = new JTextArea();
        scoreboard.setPreferredSize(new Dimension(300, 200));
        scoreboard.setFont(new Font("consolas", Font.PLAIN, 20));
        scoreboard.setEditable(false);
        scoreboard.setBackground(Color.lightGray);


        //creating text and adding to label
        JLabel textHolder = new JLabel("You Lost");
        textHolder.setFont(new Font("consolas", Font.PLAIN, 60));
        topPanel = new JPanel();
        topPanel.setBackground(Color.lightGray);
        topPanel.setLayout(new BorderLayout());

        //display time elapsed
        //NEED TO SUPER TIME VARAIBLE
        JLabel timer = new JLabel("time spent: " + minutes + ":" + seconds);
        timer.setFont(new Font("consolas", Font.PLAIN, 30));
        timer.setOpaque(true);
        timer.setBackground(Color.lightGray); // Use light gray color
        timer.setForeground(Color.BLACK);

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

        //Create panel for "You Lost" text
        JPanel textPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        textPanel.setBackground(Color.lightGray);
        textPanel.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0)); // Add empty border for top spacing
        textPanel.add(textHolder);

        // Create top panel and add the text panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.lightGray);
        topPanel.add(textPanel, BorderLayout.CENTER);

        //create inner middle layout panels
        JPanel rightMiddle = new JPanel(new BorderLayout());
        rightMiddle.add(leaderboardText, BorderLayout.NORTH);
        rightMiddle.add(scoreboard, BorderLayout.CENTER);
        JPanel leftMiddle = new JPanel(new BorderLayout());

        //Add an empty border around the timer label to create spacing
        timer.setBorder(new EmptyBorder(10, 0, 0, 0));
        leftMiddle.add(timer, BorderLayout.CENTER);

        //set the layout manager for middle panel as GridBagLayout
        middlePanel = new JPanel(new GridBagLayout());

        //create a different variable name for GridBagConstraints, e.g., timerConstraints
        GridBagConstraints timerConstraints = new GridBagConstraints();
        timerConstraints.gridx = 0;
        timerConstraints.gridy = 0;
        timerConstraints.anchor = GridBagConstraints.NORTH;
        timerConstraints.insets = new Insets(0, 0, 20, 40);
        middlePanel.add(leftMiddle, timerConstraints);

        //Adjust the y-coordinate for the leader board
        timerConstraints.gridx = 1;
        timerConstraints.gridy = 0;
        timerConstraints.insets = new Insets(0, 40, 20, 0);
        middlePanel.add(rightMiddle, timerConstraints);

        //create bottom panel
        bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 70, 20));
        bottomPanel.add(mainMenu, BorderLayout.WEST);
        bottomPanel.add(quit, BorderLayout.EAST);


        //set background color
        topPanel.setBackground(Color.lightGray);
        middlePanel.setBackground(Color.lightGray);
        bottomPanel.setBackground(Color.lightGray);

        //add action listners to buttons
        mainMenu.addActionListener(this);
        quit.addActionListener(this);

        //button settings
        mainMenu.setPreferredSize(new Dimension(200, 70));
        mainMenu.setBackground(Color.RED);
        mainMenu.setFont(new Font("consolas", Font.PLAIN, 30));
        quit.setPreferredSize(new Dimension(150, 70));
        quit.setBackground(Color.RED);
        quit.setFont(new Font("consolas", Font.PLAIN, 30));

        //frame settings
        this.add(topPanel, BorderLayout.NORTH);
        this.add(middlePanel, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);
        this.setVisible(true);
    }

    //main method creates the lost window
    public static void main(String[] args) throws Exception {
        int min = 0;
        int sec = 33;
        new loseWindow(min, sec);
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
        //opens new window
        this.dispose();
        new MainMenu();

    }
}