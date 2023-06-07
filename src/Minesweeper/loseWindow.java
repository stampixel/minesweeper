package Minesweeper;

/**
 * @author Andrew
 */

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class loseWindow extends Window  {

    public static void main(String[] args) throws Exception {

        new loseWindow ();

    }

    loseWindow () throws Exception{

        //create the window
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));


        //create label to display you lose text
        JLabel loseText = new JLabel();
        loseText.setText("You lost :(");
        loseText.setFont(new Font("consolas", Font.PLAIN, 20));
        loseText.setHorizontalTextPosition(JLabel.CENTER);
        loseText.setVerticalTextPosition(JLabel.CENTER);

        //create a area on the window to display leaderboard
        JTextArea scoreboard = new JTextArea();
        scoreboard.setPreferredSize(new Dimension(400, 500));
        scoreboard.setFont(new Font("consolas", Font.PLAIN, 16));
        scoreboard.setEditable(false);

        // Read contents of the leaderboard file
        File file = new File("leaderBoard.txt");
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

        frame.setVisible(true);
        frame.add(loseText);
        frame.add(scoreboard);
    }

}