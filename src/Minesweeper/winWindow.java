package Minesweeper;
/**
 * @author Andrew
 * Version 4
 * Changed Constructor to allow both difficulty, min, and second variable
 * Updated localization files
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class winWindow extends Window implements ActionListener {

    static String username = "";

    //create variables to be accessed from class regardless of method
    ImageIcon lostIMG;
    JLabel imageHolder;
    JTextArea scoreboard;
    File leaderboardFile;
    JButton usernameButton;
    JTextField textField;
    JPanel topPanel;
    JPanel middlePanel;
    JPanel bottomPanel;
    JLabel winText;
    JButton mainMenu;
    JButton quit;

    //Constructor Variables
    String difficuluty;
    int min;
    int sec;


    /**
     * Constructor Create a new window, and display win or lose text. Will also
     * allow user to input user name save user name onto file Display leader board
     * onto window
     *
     * @throws Exception
     */
    winWindow(String gameDiff, int gameMin, int gameSec) throws Exception {
        super();
        difficuluty = gameDiff;
        min = gameMin;
        sec = gameSec;
        //create the window
        this.setLayout(new BorderLayout(0, 0));

        // reate label to display you lose text
        winText = new JLabel();
        winText.setText("You Win");
        winText.setOpaque(true);
        winText.setBackground(Color.lightGray);
        winText.setForeground(Color.BLACK);
        winText.setFont(new Font("consolas", Font.PLAIN, 30));
        winText.setHorizontalTextPosition(JLabel.CENTER);
        winText.setVerticalTextPosition(JLabel.CENTER);

        //set text field specifics
        textField = new JTextField();
        textField.setPreferredSize(new Dimension(250, 40));
        textField.setFont(new Font("consolas", Font.PLAIN, 20));
        textField.setText("Please enter Username");
        textField.setBackground(Color.white);
        textField.setBorder(new LineBorder(Color.lightGray));

        //create a area on the window to display leaderboard
        scoreboard = new JTextArea();
        scoreboard.setPreferredSize(new Dimension(300, 200));
        scoreboard.setFont(new Font("consolas", Font.PLAIN, 20));
        scoreboard.setEditable(false);
        scoreboard.setBackground(Color.lightGray);

        //Read contents of the leaderboard file
        leaderboardFile = new File("leaderboard.txt");
        try (Scanner input = new Scanner(leaderboardFile)) {
            List<String> scores = new ArrayList<>();
            while (input.hasNextLine()) {
                String line = input.nextLine();
                scores.add(line);
            }
            input.close();

            //Set the contents of the scoreboard text field
            StringBuilder sb = new StringBuilder();
            for (String score : scores) {
                sb.append(score).append("\n");
            }
            scoreboard.setText(sb.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //create text label
        JLabel leaderboard = new JLabel("Leaderboard");
        leaderboard.setFont(new Font("consolas", Font.PLAIN, 25));

        //Instantiate mainMenu and quit usernameButtons
        mainMenu = new JButton("Main Menu");
        quit = new JButton("Quit");

        //Set the font and preferred size for the usernameButtons
        mainMenu.setFont(new Font("consolas", Font.PLAIN, 30));
        mainMenu.setPreferredSize(new Dimension(200, 70));
        quit.setFont(new Font("consolas", Font.PLAIN, 30));
        quit.setPreferredSize(new Dimension(150, 70));

        //Set the background color for the usernameButtons
        mainMenu.setBackground(Color.RED);
        quit.setBackground(Color.RED);

        //Add action listeners to the usernameButtons
        mainMenu.addActionListener(this);
        quit.addActionListener(this);

        //usernameButton to read user input
        usernameButton = new JButton("Submit");
        usernameButton.setFont(new Font("consolas", Font.PLAIN, 30));
        usernameButton.setPreferredSize(textField.getPreferredSize());
        usernameButton.addActionListener(this);
        usernameButton.setBackground(Color.RED);
        usernameButton.setBorder(new LineBorder(Color.RED));

        //Add components to inner top panel
        JPanel usernamePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        usernamePanel.add(textField);
        usernamePanel.add(usernameButton);

        //innerTop panel
        JPanel innerTop = new JPanel();
        innerTop.setBackground(Color.lightGray);
        innerTop.add(winText);

        //inner down panel
        JPanel innerDown = new JPanel(new BorderLayout());
        innerDown.setBackground(Color.lightGray);
        innerDown.add(usernamePanel, BorderLayout.CENTER);

        //top panel settings
        topPanel = new JPanel(new BorderLayout());
        topPanel.add(innerTop,BorderLayout.NORTH);
        topPanel.add(innerDown,BorderLayout.SOUTH);
        topPanel.setBackground(Color.lightGray);

        //Create inner panels
        JPanel middleRightPanel = new JPanel();
        JPanel middleLeftPanel = new JPanel();

        //Set layout manager for middleRightPanel and middleLeftPanel
        middleRightPanel.setLayout(new BoxLayout(middleRightPanel, BoxLayout.Y_AXIS));
        middleLeftPanel.setLayout(new BoxLayout(middleLeftPanel, BoxLayout.Y_AXIS));

        //Add components to inner panels
        JLabel timer = new JLabel("Time Elapsed: ");
        timer.setFont(new Font("consolas", Font.PLAIN, 30));
        JLabel time = new JLabel(min + ":" + sec);
        time.setFont(new Font("consolas", Font.PLAIN, 30));
        JPanel innerUpPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        innerUpPanel.setBackground(Color.lightGray);
        innerUpPanel.add(timer);
        innerUpPanel.add(time);

        //Add components to innerDownPanel
        //need to super a variable for difficulty
        //need to add variable here
        JLabel mode = new JLabel("Difficuluty: " + difficuluty);
        mode.setFont(new Font("consolas", Font.PLAIN, 30));
        JPanel innerDownPanel = new JPanel();
        innerDownPanel.setBackground(Color.lightGray);
        innerDownPanel.add(mode);

        //Set GridBagConstraints for middlePanel
        JPanel middlePanel = new JPanel(new GridBagLayout());
        middlePanel.setBackground(Color.lightGray);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);

        //Add components to middlePanel using GridBagConstraints
        middlePanel.add(innerUpPanel, gbc);

        gbc.gridy++;
        middlePanel.add(innerDownPanel, gbc);

        gbc.gridy = 0;
        gbc.gridx++;
        gbc.anchor = GridBagConstraints.CENTER;
        middlePanel.add(leaderboard, gbc);

        gbc.gridy++;
        middlePanel.add(scoreboard, gbc);

        //Create bottom panel
        bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 70, 20));
        bottomPanel.add(mainMenu, BorderLayout.WEST);
        bottomPanel.add(quit, BorderLayout.EAST);

        //Set background color for panels
        topPanel.setBackground(Color.lightGray);
        usernamePanel.setBackground(Color.lightGray);
        bottomPanel.setBackground(Color.lightGray);

        //Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(middlePanel,BorderLayout.CENTER);
        mainPanel.add(bottomPanel,BorderLayout.SOUTH);

        //Set usernameButton settings
        mainMenu.setPreferredSize(new Dimension(200, 70));
        mainMenu.setBackground(Color.RED);
        mainMenu.setFont(new Font("consolas", Font.PLAIN, 30));
        quit.setPreferredSize(new Dimension(150, 70));
        quit.setBackground(Color.RED);
        quit.setFont(new Font("consolas", Font.PLAIN, 30));

        //Make frame visible and add other GUI components
        this.add(mainPanel);

        this.setVisible(true);
    }

    //main method
    public static void main(String[] args) throws Exception {
        String tempDiff = "ez";
        int tempMin = 1;
        int tempSec = 59;
        new winWindow(tempDiff, tempMin, tempSec);
    }

    //if submit button is pressed, take the text from the textfield
    //and save to a leaderboard file
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == usernameButton) {
            username = textField.getText();
            try {
                PrintWriter out = new PrintWriter(new FileWriter("leaderboard.txt", true));
                out.println(username + "|" + min + ":" + sec);
                out.close();
                usernameButton.setEnabled(false);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } else if (e.getSource() == mainMenu) {
            this.dispose();
            new MainMenu();
        } else if (e.getSource() == quit) {
            this.dispose();
        }
    }

}