package Minesweeper;

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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class winWindow extends Window implements ActionListener {

    static String username = "";
    //create variables to be accessed from class regardless of method
    JButton button;
    JTextField textField;

    /**
     * Constructor
     * Create a new window, and display win or lose text.
     * Will also allow user to input username
     * save username onto file
     * Display leaderboard onto window
     *
     * @throws Exception
     */

    winWindow() throws Exception {
        super();

        //create the window
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        //create a label to display onto window
        JLabel winText = new JLabel();
        winText.setText("You Win!");
        winText.setFont(new Font("consolas", Font.PLAIN, 20));
        winText.setHorizontalTextPosition(JLabel.CENTER);
        winText.setVerticalTextPosition(JLabel.TOP);

        //set text field specifics
        textField = new JTextField();
        textField.setPreferredSize(new Dimension(250, 40));
        textField.setFont(new Font("consolas", Font.PLAIN, 20));
        textField.setText("Please enter Username");

        //create a area on the window to display leaderboard
        JTextArea scoreboard = new JTextArea();
        scoreboard.setPreferredSize(new Dimension(400, 500));
        scoreboard.setFont(new Font("consolas", Font.PLAIN, 16));
        scoreboard.setEditable(false);

        // Read contents of the leaderboard file
        File file = new File("leaderboard.txt");
        try (Scanner input = new Scanner(file)) {
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
        button.addActionListener(this);

        //make frame visible, and add other GUI components
        this.add(winText);
        this.add(textField);
        this.add(button);
        this.add(scoreboard);

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