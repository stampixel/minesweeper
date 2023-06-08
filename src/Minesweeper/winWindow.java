package Minesweeper;

/**
 * @author Andrew
 * Version 2
 * Updated GUI Look
 * Added consistent background
 * Added image icon showing a win image
 * changed button to red
 * Updated localization files
 */

import java.awt.BorderLayout;
import java.awt.Color;

/**
 * @author Andrew
 */

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class winWindow extends Window implements ActionListener {

    static String username = "";
    //create variables to be accessed from class regardless of method
    ImageIcon lostIMG;
    JLabel imageHolder;
    JTextArea scoreboard;
    File leaderboardFile;
    JButton button;
    JTextField textField;
    JPanel topPanel;
    JPanel middlePanel;
    JPanel bottomPanel;

    /**
     * Constructor
     * Create a new window, and display win or lose text.
     * Will also allow user to input user name
     * save user name onto file
     * Display leader board onto window
     *
     * @throws Exception
     */

    winWindow() throws Exception {
        super();

        //create the window
        this.setLayout(new BorderLayout(0, 0));

        //create win image and add to label
        lostIMG = new ImageIcon("win.jpg");
        imageHolder = new JLabel(lostIMG);

        //set text field specifics
        textField = new JTextField();
        textField.setPreferredSize(new Dimension(250, 40));
        textField.setFont(new Font("consolas", Font.PLAIN, 20));
        textField.setText("Please enter Username");
        textField.setBackground(Color.GRAY);

        //create a area on the window to display leaderboard
        scoreboard = new JTextArea();
        scoreboard.setPreferredSize(new Dimension(300, 200));
        scoreboard.setFont(new Font("consolas", Font.PLAIN, 16));
        scoreboard.setEditable(false);
        scoreboard.setBackground(Color.GRAY);

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

        //button to read user input
        button = new JButton("Submit");
        button.setFont(new Font("consolas", Font.PLAIN, 16));
        button.setPreferredSize(textField.getPreferredSize());
        button.addActionListener(this);
        button.setBackground(Color.RED);

        //top panel settings
        topPanel = new JPanel();
        topPanel.add(imageHolder, BorderLayout.NORTH);
        topPanel.setBackground(Color.GRAY);

        //Create middle panel and set its layout manager to FlowLayout
        middlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        middlePanel.add(textField);
        middlePanel.add(button);
        middlePanel.setBackground(Color.GRAY);

        //bottom panel
        bottomPanel = new JPanel();
        bottomPanel.add(scoreboard);
        bottomPanel.setBackground(Color.GRAY);

        //make frame visible, and add other GUI components
        this.add(topPanel, BorderLayout.NORTH);
        this.add(middlePanel, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);

        this.pack();
        this.setVisible(true);
    }

    public static void main(String[] args) throws Exception {

        new winWindow();

    }

    public void actionPerformed(ActionEvent e) {

        //append to file, and get username and display to window
        //store username into a file
        try {
            PrintWriter output = new PrintWriter(new FileWriter("leaderboard.txt", true));
            username = textField.getText();
            output.println(username + "difficuluty " + "time");
            output.close();

            button.setEnabled(false);
            textField.setEditable(false);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        button.setEnabled(false);
        textField.setEditable(false);

    }
}