package Minesweeper;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;

public abstract class Window extends JFrame {
    String font;
    int smallFont;
    int mediumFont;
    int largeFont;
    int titleFont;
    int leaderboardLength;
    int[] leaderboardScores;
    String[] leaderboardUsernames;

    Window() throws Exception {

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(1280, 720);

        font = "Roboto";
        smallFont = 15;
        mediumFont = 20;
        largeFont = 30;
        titleFont = 50;

        leaderboardLength = getFileLength() + 2;
        leaderboardScores = new int[leaderboardLength];
        leaderboardUsernames = new String[leaderboardLength];
    }


    public static int getFileLength() throws Exception {
        // initializes file object
        File file = new File("leaderboard.txt");
        // initializes scanner object to read through file
        Scanner fileScan = new Scanner(file);
        // initializes accumulator to track the number of lines inside the method
        int counter = 0;
        // loops as long as there is a next line within the file
        while (fileScan.hasNextLine()) {
            // increments counter by 1
            counter++;
            // reads next line
            fileScan.nextLine();
        }
        // returns the number of lines within the file
        return counter;
    }

    public static void addToLeaderboard(int[] leaderboardScores, String[] leaderboardUsernames, int userTotalScore, String username) throws Exception {
        // Declares and initializes new file object to "Leaderboard.txt"
        File file = new File("leaderboard.txt");
        // Declares and initializes new PrintWriter and FileWriter
        PrintWriter pw = new PrintWriter(new FileWriter(file, true), true);
        // Declares and initializes new Scanner object
        Scanner fileScan = new Scanner(file);

        // Retrieve existing leaderboard scores and usernames
        try {
            for (int i = 0; i < 5; i++) {
                if (fileScan.hasNextLine()) {
                    String currentLine = fileScan.nextLine();
                    int locateDash1 = currentLine.indexOf("-");
                    int locateDash2 = currentLine.lastIndexOf("-");
                    String getScoreLocation = currentLine.substring(locateDash1 + 1, locateDash2);
                    leaderboardScores[i] = Integer.parseInt(getScoreLocation);
                    leaderboardUsernames[i] = currentLine.substring(0, locateDash1);
                } else {
                    // Handle the case where there are fewer than 5 lines in the file
                    leaderboardScores[i] = 0;
                    leaderboardUsernames[i] = "";
                }
            }
            // Catches any exceptions or errors in the code
        } catch (Exception e) {
            System.out.println("Creating leaderboard...\nCheck back in next time in the welcome screen to see updated rankings!");
        }
// Calls getFileLength method and sets the current user score and username to the last index of the array, or index of the length of the file
        int fileLength = getFileLength();
        leaderboardScores[fileLength] = userTotalScore;
        leaderboardUsernames[fileLength] = username;

        // Bubblesorts all scores and usernames in the leaderboard file in descending order
        for (int i = 0; i < leaderboardScores.length - 1; i++) {
            for (int j = 0; j < leaderboardScores.length - i - 1; j++) {
                if (leaderboardScores[j] > leaderboardScores[j + 1]) {
                    int temp = leaderboardScores[j];
                    String tempName = leaderboardUsernames[j];
                    leaderboardScores[j] = leaderboardScores[j + 1];
                    leaderboardUsernames[j] = leaderboardUsernames[j + 1];
                    leaderboardScores[j + 1] = temp;
                    leaderboardUsernames[j + 1] = tempName;
                }
            }
        }

        // Clears leaderboard file
        pw.close();


        pw = new PrintWriter(new FileWriter(file));

        // Prints all scores in descending order to the leaderboard file
        for (int i = 0; i < leaderboardScores.length; i++) {
            System.out.println();
            if (leaderboardUsernames[i] != null && leaderboardScores[i] != 0) {
                pw.println(leaderboardUsernames[i] + "-" + leaderboardScores[i] + "-");
            }
        }
        pw.close();
    }
}
