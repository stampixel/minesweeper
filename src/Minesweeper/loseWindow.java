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

public class loseWindow extends Window {

    loseWindow() throws Exception {
        this.setLayout(new FlowLayout(FlowLayout.CENTER));


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
        this.add(loseText);
        this.add(scoreboard);

        this.pack();
        this.setVisible(true);
    }

    public static void main(String[] args) throws Exception {

        new loseWindow();

    }
}