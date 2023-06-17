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
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.*;
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
    String difficulty;
    int min;
    int sec;


    /**
     * Constructor Create a new window, and display win or lose text. Will also
     * allow user to input user name save user name onto file Display leader board
     * onto window
     *
     * @throws Exception
     */
    winWindow(String gameDiff, int gameSec) throws Exception {
        super();
        difficulty = gameDiff;
        sec = gameSec;
        //create the window
        this.setLayout(new BorderLayout(0, 0));

        // Create border for buttons and labels
        LineBorder lineBorder = new LineBorder(Color.gray, 5, true);

        // create label to display you lose text
        winText = new JLabel();
        winText.setText("You Win!");
        winText.setOpaque(true);
        winText.setBackground(Color.lightGray);
        winText.setForeground(Color.BLACK);
        winText.setFont(new Font("Roboto", Font.PLAIN, 30));
        winText.setHorizontalTextPosition(JLabel.CENTER);
        winText.setVerticalTextPosition(JLabel.CENTER);
        winText.setBorder(lineBorder);

        //set text field specifics
        textField = new JTextField();
        textField.setPreferredSize(new Dimension(250, 40));
        textField.setFont(new Font("Roboto", Font.PLAIN, 20));
        textField.setText("Please enter Username:");
        textField.setBackground(Color.lightGray);
        textField.setBorder(lineBorder);


        //create a area on the window to display leaderboard.txt
        scoreboard = new JTextArea();
        scoreboard.setPreferredSize(new Dimension(300, 200));
        scoreboard.setFont(new Font("Roboto", Font.PLAIN, 20));
        scoreboard.setEditable(false);
        scoreboard.setBackground(Color.lightGray);

        //Read contents of the leaderboard.txt file
        leaderboardFile = new File("leaderboard.txt.txt");
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
        leaderboard.setFont(new Font("Roboto", Font.PLAIN, 25));

        //Instantiate mainMenu and quit usernameButtons
        mainMenu = new JButton("Main Menu");
        quit = new JButton("Quit");

        //Set the font and preferred size for the usernameButtons
        mainMenu.setFont(new Font("Roboto", Font.PLAIN, 30));
        mainMenu.setPreferredSize(new Dimension(200, 70));
        mainMenu.setBorder(lineBorder);
        quit.setFont(new Font("Roboto", Font.PLAIN, 30));
        quit.setPreferredSize(new Dimension(150, 70));
        quit.setBorder(lineBorder);

        // Set the main menu and quit buttons opaque
        mainMenu.setOpaque(true);
        quit.setOpaque(true);

        // Set the background color for the usernameButtons
        mainMenu.setBackground(Color.lightGray);
        quit.setBackground(Color.lightGray);

        //Add action listeners to the usernameButtons
        mainMenu.addActionListener(this);
        quit.addActionListener(this);

        //usernameButton to read user input
        usernameButton = new JButton("Submit");
        usernameButton.setFont(new Font("Roboto", Font.PLAIN, 30));
        usernameButton.setPreferredSize(textField.getPreferredSize());
        usernameButton.addActionListener(this);
        usernameButton.setBackground(Color.lightGray);
        usernameButton.setBorder(lineBorder);

        //Add components to inner top panel
        JPanel usernamePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        usernamePanel.add(textField);
        usernamePanel.add(usernameButton);
        usernamePanel.setBorder(lineBorder);

        //innerTop panel
        JPanel innerTop = new JPanel();
        innerTop.setBackground(new Color(0, 0, 0, 0));
        innerTop.add(winText);

        //inner down panel
        JPanel innerDown = new JPanel(new BorderLayout());
        innerDown.setBackground(Color.lightGray);
        innerDown.add(usernamePanel, BorderLayout.CENTER);

        //top panel settings
        topPanel = new JPanel(new BorderLayout());
        topPanel.add(innerTop,BorderLayout.NORTH);
        topPanel.add(innerDown,BorderLayout.SOUTH);
        topPanel.setBackground(new Color(0, 0, 0, 0));

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
        innerUpPanel.setBackground(new Color(0, 0, 0, 0));
        innerUpPanel.add(timer);
        innerUpPanel.add(time);

        //Add components to innerDownPanel
        //need to super a variable for difficulty
        //need to add variable here
        JLabel mode = new JLabel("Difficulty: " + difficulty);
        mode.setFont(new Font("consolas", Font.PLAIN, 30));
        JPanel innerDownPanel = new JPanel();
        innerDownPanel.setBackground(new Color(0, 0, 0, 0));
        innerDownPanel.add(mode);

        //Set GridBagConstraints for middlePanel
        JPanel middlePanel = new JPanel(new GridBagLayout());
        middlePanel.setBackground(Color.lightGray);
        middlePanel.setBorder(lineBorder);

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
        topPanel.setBackground(new Color(0, 0, 0, 0));
        usernamePanel.setBackground(Color.lightGray);
        bottomPanel.setBackground(new Color(0, 0, 0, 0));

        //Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(0, 0, 0, 0));

        topPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        middlePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        bottomPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(topPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(10, 30)));
        mainPanel.add(middlePanel);
        mainPanel.add(Box.createRigidArea(new Dimension(10, 30)));
        mainPanel.add(bottomPanel);

        BottomPanel background = new BottomPanel();

        background.add(mainPanel);

        //Make frame visible and add other GUI components
        this.add(background);

        this.setVisible(true);
    }

    //main method
    public static void main(String[] args) throws Exception {
        String tempDiff = "ez";
        int tempSec = 59;
        new winWindow(tempDiff, tempSec);
    }

    //if submit button is pressed, take the text from the textfield
    //and save to a leaderboard.txt file
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == usernameButton) {
            username = textField.getText();
            try {
                PrintWriter out = new PrintWriter(new FileWriter("leaderboard.txt", true));
                out.println(username + "-" + sec+ "-");
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
            System.exit(0);
        }
    }

    /**
     * This class creates a panel which serves as the background panel for the main menu
     * of a minesweeper game. The class prints an image onto the panel so that other panels
     * can be added on to it.
     *
     * @author mikail.hussain
     */

    class BottomPanel extends JPanel {
        Image img;

        @Override
        protected void paintComponent(Graphics g) {
            try {
                img = ImageIO.read(new File("BG.png"));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            g.drawImage(img, 0, 0, null);
        }
    }

}