package Minesweeper.Algorithms;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;

public class Leaderboard {
    public static void main(String[] args) throws Exception {

        int leaderboardLength = getFileLength() + 2;
        int[] leaderboardScores = new int[leaderboardLength];
        String[] leaderboardUsernames = new String[leaderboardLength];

        addToLeaderboard(leaderboardScores, leaderboardUsernames, 13, "kevin");

        for (int i = 0; i < leaderboardScores.length; i++) {
            System.out.println(leaderboardScores[i]);
        }
        leaderboard(leaderboardScores, leaderboardUsernames);
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
                if (leaderboardScores[j] < leaderboardScores[j + 1]) {
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


    /**

     Displays the All-Time Yahtzee High Scores from a leaderboard file.*
     @param leaderboardScores    An array to store the leaderboard scores.
     @param leaderboardUsernames An array to store the usernames associated with the leaderboard scores.
     @throws Exception If an error occurs while reading the leaderboard file.
     */
    public static void leaderboard(int[] leaderboardScores, String[] leaderboardUsernames) throws Exception {
        File file = new File("leaderboard.txt");
        Scanner fileScan = new Scanner(file);
        System.out.println();

        // Display leaderboard header
        System.out.println(" All-Time Yahtzee High Scores ");
        System.out.println("------------------------------");
        int lineCount = 0;
        // Iterates a maximum of 5 times, prints top 5 highest scores
        while (fileScan.hasNextLine() && lineCount < 5) {
            String currentLine = fileScan.nextLine();
            // locates position of '-' symbols, which are used for username and score identification in the file
            int locateDash1 = currentLine.indexOf("-");
            int locateDash2 = currentLine.lastIndexOf("-");
            // locates score by searching for characters between 2 "-" symbols in a line in the leaderboard file
            String getScoreLocation = currentLine.substring(locateDash1 + 1, locateDash2);

            // Parse(analyze and extract) the score and username from each line of the leaderboard file
            leaderboardScores[lineCount] = Integer.parseInt(getScoreLocation);
            leaderboardUsernames[lineCount] = currentLine.substring(0, locateDash1);

            // Displays leaderboard entry
            System.out.println((lineCount + 1) + ". " + leaderboardUsernames[lineCount] + " - " + leaderboardScores[lineCount]);
            lineCount++;
        }
        System.out.println();
        // closes scanner
        fileScan.close();
    }
}
