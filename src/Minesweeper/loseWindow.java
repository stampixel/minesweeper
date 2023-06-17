package Minesweeper;

/**
 * @author Andrew
 * Version 5
 * Mikail fixed up the GUI and made the window look cleaner
 */

import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;


public class loseWindow extends Window implements ActionListener {

    //create buttons
    JButton mainMenu;
    JButton quit;
    JLabel leaderboardText;
    JTextPane scoreboard;
    File leaderboardFile;

    ImageIcon lostIMG;
    JLabel imageHolder;
    JPanel topPanel;
    JPanel middlePanel;
    GridBagConstraints middleConstraints;
    JPanel bottomPanel;

    int seconds;

    loseWindow(int gameSec) throws Exception {

        // Create border for buttons and labels
        LineBorder lineBorder = new LineBorder(Color.gray, 5, true);

        seconds = gameSec;
        mainMenu = new JButton("Main Menu");
        mainMenu.setOpaque(true);
        mainMenu.setBackground(Color.lightGray);
        mainMenu.setBorder(lineBorder);
        quit = new JButton("Quit");
        quit.setOpaque(true);
        quit.setBackground(Color.lightGray);
        quit.setBorder(lineBorder);

        this.setLayout(new BorderLayout(0, 0));


        //create label to display you lose text
        leaderboardText = new JLabel("Leaderboard:", SwingConstants.CENTER);
        leaderboardText.setOpaque(true);
        leaderboardText.setBackground(Color.lightGray);
        leaderboardText.setForeground(Color.BLACK);
        leaderboardText.setFont(new Font("Roboto", Font.PLAIN, 30));

        //create a area on the window to display leaderboard.txt
        scoreboard = new JTextPane();
        scoreboard.setPreferredSize(new Dimension(300, 200));
        scoreboard.setFont(new Font("Roboto", Font.PLAIN, 20));
        scoreboard.setEditable(false);
        scoreboard.setBackground(Color.lightGray);

        // Center the text
        StyledDocument doc = scoreboard.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);


        //creating text and adding to label
        JLabel textHolder = new JLabel("You Lost!");
        textHolder.setFont(new Font("Roboto", Font.PLAIN, 60));
        textHolder.setBackground(Color.lightGray);
        textHolder.setOpaque(true);
        textHolder.setBorder(lineBorder);
        topPanel = new JPanel();
        topPanel.setBackground(new Color(0, 0, 0, 0));
        topPanel.setLayout(new BorderLayout());

        //display time elapsed
        //NEED TO SUPER TIME VARAIBLE
        JLabel timer = new JLabel("Time spent: " + seconds);
        timer.setFont(new Font("Roboto", Font.PLAIN, 30));
        timer.setOpaque(true);
        timer.setBackground(Color.lightGray); // Use light gray color
        timer.setForeground(Color.BLACK);
        timer.setBorder(lineBorder);

        // Read contents of the leaderboard.txt file
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
        textPanel.setBackground(new Color(0, 0, 0, 0));
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
        rightMiddle.setBorder(lineBorder);
        JPanel leftMiddle = new JPanel(new BorderLayout());
        leftMiddle.setBorder(lineBorder);

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
        mainMenu.setFont(new Font("Roboto", Font.PLAIN, 30));
        quit.setPreferredSize(new Dimension(150, 70));
        quit.setFont(new Font("Roboto", Font.PLAIN, 30));

        BottomPanel background = new BottomPanel();
        background.setLayout(new BoxLayout(background, BoxLayout.Y_AXIS));

        topPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        middlePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        bottomPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        topPanel.setBackground(new Color(0, 0, 0, 0));
        middlePanel.setBackground(new Color(0, 0, 0, 0));
        bottomPanel.setBackground(new Color(0, 0, 0, 0));


        //frame settings
        background.add(topPanel);
        background.add(middlePanel);
        background.add(bottomPanel);
        this.add(background);
        this.setVisible(true);
    }

    //main method creates the lost window
    public static void main(String[] args) throws Exception {
        int min = 0;
        int sec = 33;
        new loseWindow(sec);
    }

    //button actions
    public void actionPerformed(ActionEvent e) {
        //when quit button is pressed close window
        if (e.getSource() == quit) {
            dispose();
            System.exit(0);
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