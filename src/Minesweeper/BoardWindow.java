package Minesweeper;

/**
 * @author kevin.tang
 * 2023.06.15
 */

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;

/**
 * extends a JFrame and uses the action listener
 */
public class BoardWindow extends JFrame implements ActionListener {
    /**
     * Since this is a subclass, all variables can be assigned as private, this makes it so other classes can't directly access them.
     * This is good as it prevent other parts of your code from accidentally changing the value of a variable.
     *
     * Variables that are unmodified throughout the program also contain the final keyword, this makes the code easier to understand and read.
     */
    private String difficulty;
    private final JButton[][] mineFieldArray;
    private final int[][] mineRepresentationArray;
    private final int mineHeight;
    private final int mineWidth;
    private boolean firstClick;
    private final int ROW;
    private final int COL;
    private final int mineNum;
    private final JPanel header;
    private final JPanel mineField;
    private final JButton quit;
    private final JLabel flagNumber;
    private final JLabel timeDisplay;
    private final ImageIcon mine;
    private final ImageIcon flag;
    private final ImageIcon coveredSquare;
    private final int[][] navigation;
    private final boolean[][] flagArray;
    private final ImageIcon[] mineImg;
    private boolean checkLose;
    private int flagCount;
    private final Timer timer;
    private int time;

    /**
     * Constructor that takes in the amount of rows and columns, as well as the number of mines inside the field.
     * @param mineRow
     * @param mineCol
     * @param mineNum
     */
    BoardWindow(int mineRow, int mineCol, int mineNum) {
        flagCount = mineNum;

        // This array is values that, when applied to a specific cell inside a 2d array, allows you to view the contents of all 8 neighboring cells.
        navigation = new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}, {1, 1}, {-1, 1}, {1, -1}, {-1, -1}};

        // This generates an array, each index corresponds with the amount of mines there are, which corresponds to an image.
        mineImg = new ImageIcon[9];
        for (int i = 0; i < mineImg.length; i++) {
            mineImg[i] = new ImageIcon(i + ".png");
        }

        // Other needed images
        mine = new ImageIcon("Mine.png");
        flag = new ImageIcon("Flag.png");
        coveredSquare = new ImageIcon("ClickableSquare.png");

        // Height and width of each mine in pixels
        mineHeight = 48;
        mineWidth = 48;

        // Used for the first click algorithm (game spawns mines on the first click, as well as making sure the neighboring 8 cells don't have mines in them)
        firstClick = true;

        checkLose = false;

        // Creates row and col varibles using the values passed into the constructor, as well as number of mines
        ROW = mineRow;
        COL = mineCol;
        this.mineNum = mineNum;

        // Crates the minefield
        mineFieldArray = new JButton[ROW][COL]; // Each cell is a JButton, this array is used to register clicks
        mineRepresentationArray = new int[ROW][COL]; // This array is used to store what each cell IS (0 represents covered empty squares, 1 uncovered empty square, 2 represents mines)
        flagArray = new boolean[ROW][COL]; // boolean array storing whether each cell contains a flag or not

        // Creating the window
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(COL * mineWidth, ROW * mineHeight + 80); // Math to calculate exactly how big it should be, we add 80 pixels to the height since we need room for header (quit, timer, flags)
        this.setVisible(true);
        this.setLayout(new BorderLayout(10, 10));

        // two JPanels, header stores the quit button, timer, and amount of flags, minefield is the actual game itself
        header = new JPanel();
        mineField = new JPanel();

        header.setBackground(Color.black);
        header.setPreferredSize(new Dimension(800, 80));

        // Create quit button
        quit = new JButton("Quit");
        quit.setFont(new Font("Roboto", Font.PLAIN, 25));
        quit.setPreferredSize(new Dimension(100, 72));
        quit.setBorder(new LineBorder(Color.gray, 5, true));
        quit.setBackground(Color.lightGray);
        quit.setOpaque(true);

        // Create label that displays number of flags
        flagNumber = new JLabel("Flags Left: ", SwingConstants.CENTER);
        flagNumber.setFont(new Font("Roboto", Font.PLAIN, 25));
        flagNumber.setPreferredSize(new Dimension(100, 72));
        flagNumber.setBorder(new LineBorder(Color.red, 5, true));
        flagNumber.setBackground(Color.lightGray);
        flagNumber.setOpaque(true);

        // Create label that displays time
        timeDisplay = new JLabel("Time", SwingConstants.CENTER);
        timeDisplay.setFont(new Font("Roboto", Font.PLAIN, 25));
        timeDisplay.setPreferredSize(new Dimension(100, 72));
        timeDisplay.setBorder(new LineBorder(Color.yellow, 5, true));
        timeDisplay.setBackground(Color.lightGray);
        timeDisplay.setOpaque(true);

        quit.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        System.exit(0);
                    }
                }
        );

        header.setLayout(new BorderLayout(5, 5));

        JPanel quitPanel = new JPanel();
        quitPanel.setLayout(new FlowLayout());
        quitPanel.add(quit);
        quitPanel.setBackground(Color.black);

        header.add(flagNumber, BorderLayout.EAST);
        header.add(quitPanel, BorderLayout.CENTER);
        header.add(timeDisplay, BorderLayout.WEST);

        mineField.setBackground(Color.darkGray);
        mineField.setLayout(new GridLayout(ROW, COL, 3, 3));


        this.add(header, BorderLayout.NORTH);
        this.add(mineField, BorderLayout.CENTER);

        generateMineField(ROW, COL); // Generates the minefield


        // Starts the timer, which will increment each second
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                time++;
                if (time < 100000) {
                    timeDisplay.setText(Integer.toString(time));
                } else {
                    ((Timer) (e.getSource())).stop();
                }
            }
        });
        timer.setInitialDelay(0);
        timer.start();


        this.setVisible(true);
        UpdateBoard(); // Update the board so the user sees the latest changes
    }

    public static void main(String[] args) {
        new BoardWindow(16, 8, 10);
    }

    /**
     * Places the mines on the board
     *
     * @param mineLocations
     */
    public void placeMines(int[][] mineLocations) { // Mine locations are a 2d array, each subarray represents the locations where there are mines
        for (int i = 9; i < mineLocations.length; i++) { // Starts at index 9, because the first 9 index of mineLocations stores squares where mines shouldn't exist on
            mineRepresentationArray[mineLocations[i][0]][mineLocations[i][1]] = 2; // Set the location to a mine (2 represents a mine)
        }
    }

    /**
     * This loops through the entirety of mineRepresentationArray and ensures that the latest changes to the array registers
     */
    public void UpdateBoard() {
        for (int i = 0; i < mineRepresentationArray.length; i++) {
            for (int j = 0; j < mineRepresentationArray[0].length; j++) {

                // Sets squares to the coveredSquare image (0 represents actual covered square, 2 represents mine)
                if (mineRepresentationArray[i][j] == 0 || mineRepresentationArray[i][j] == 2) {
                    mineFieldArray[i][j].setIcon(coveredSquare);
                } else if (mineRepresentationArray[i][j] == 1) { // If the square is uncovered
                    // Loop through all the adjacent squares using the navigation array and counts teh amount of adjacent mines
                    int mineCount = 0;
                    for (int k = 0; k < navigation.length; k++) {
                        try {
                            if (mineRepresentationArray[i + navigation[k][0]][j + navigation[k][1]] == 2) {
                                mineCount += 1;
                            }
                        } catch (Exception e) {
                            // Need a try and catch, because navigation array doesn't know the user is clicking on the edge of teh board, spawning an arrayindexoutofrange error
                            /** X represents the cell that has been clicked on
                             * O O O
                             * O O X
                             * O O O
                             */
                        }
                    }
                    // Sets the appropriate image for the uncovered square based on amount of adjacent mines
                    mineFieldArray[i][j].setIcon(mineImg[mineCount]);
                }

                // If there's a flag at that specific location, we overwrite the image as a flag
                if (flagArray[i][j]) {
                    mineFieldArray[i][j].setIcon(flag);
                }

                // Updates the flag counter at the top of the window
                flagNumber.setText(String.valueOf(flagCount));
            }
        }
    }

    /**
     * Returns a boolean indicating whether there is an adjacent mine or not
     * @param row
     * @param col
     * @return
     */
    public boolean checkAdjacentMine(int row, int col) {
        boolean adjacent = false;

        // Loops through neighboring cells using navigation array
        for (int i = 0; i < navigation.length; i++) {
            try {
                if (mineRepresentationArray[row + navigation[i][0]][col + navigation[i][1]] == 2) {
                    adjacent = true;
                }
            } catch (Exception e) {
                // Prevent arrayindexoutrofrange
            }
        }
        return adjacent;
    }

    /**
     * Using a flood-fill algorithm, this computes each cell that should automatically be uncovered based on the current cell the user clicked on.
     * Recursive method
     *
     * @param row
     * @param col
     */
    public void floodFill(int row, int col) {
        int fillRow;
        int fillCol;

        // Loops through the navigation array
        for (int i = 0; i < navigation.length; i++) {
            try {
                // Adds the navigation values to the current row and column
                fillRow = row + navigation[i][0];
                fillCol = col + navigation[i][1];
                if (mineRepresentationArray[fillRow][fillCol] == 0) { // If the cell is covered
                    if (checkAdjacentMine(fillRow, fillCol)) { // Check if there are neighboring mines, if so, we uncover the square and stop flood filling in that direction
                        mineRepresentationArray[fillRow][fillCol] = 1;
                        continue;
                    }
                    mineRepresentationArray[fillRow][fillCol] = 1; // Otherwise (no neighboring mines), we recursively run the floodFill method on the new cell
                    floodFill(fillRow, fillCol);
                }
            } catch (Exception e) {
                // catch arrayindexoutofrange
            }
        }
    }

    /**
     * Method used to show the user ALL mines on the board once they've lost
     */
    public void showMine() {
        for (int i = 0; i < mineRepresentationArray.length; i++) {
            for (int j = 0; j < mineRepresentationArray[0].length; j++) {
                if (mineRepresentationArray[i][j] == 2) {
                    mineFieldArray[i][j].setIcon(mine);
                }
            }
        }
    }

    /**
     * Generates the minefield itself
     *
     * @param row
     * @param col
     */
    public void generateMineField(int row, int col) {
        // Loops through the amonut of rows and columns
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                // Makes a JButton for each of the mines
                mineFieldArray[i][j] = new JButton();
                mineFieldArray[i][j].addActionListener(this);

                // We need this in order to pass the i and j values to the mouse listener
                int finalI = i;
                int finalJ = j;

                // Adds a mouse listener for each button, this will be used to track right clicks (flags being placed)
                mineFieldArray[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        // getButton() will return 3 which represents a right click
                        if (e.getButton() == 3 && mineRepresentationArray[finalI][finalJ] != 1) { // If the is right clicked and the cell is not uncovered yet
                            if (flagCount <= 0) { // If the flag count is 0 or less, it won't allow you to place more
                                if (flagArray[finalI][finalJ]) { // if the square contains a flag, remove it
                                    flagArray[finalI][finalJ] = false;
                                    flagCount++;
                                }
                            } else {
                                if (flagArray[finalI][finalJ]) { // if the flag count isn't 0 and there is a flag, increase the flag count as you've just removed an existing flag
                                    flagCount++;
                                } else { // decrease, meaning you placed a flag
                                    flagCount--;
                                }
                                flagArray[finalI][finalJ] = !flagArray[finalI][finalJ]; // Switch the actual values
                            }
                            UpdateBoard(); // Show the latest changes to the user
                        }
                    }
                });
                mineField.add(mineFieldArray[i][j]); // Finally, we add the mine to the constructed mine to the array itself
            }
        }
    }

    /**
     * @param clickRow The row which represents the user's first click
     * @param clickCol The col which represents the user's first click
     */
    public void generateBoard(int clickRow, int clickCol) {
        int row, col;
        int mineCount = 9; // Start at 9 since the first mine in mineLocation represent the first square clicked, and the neightboring 8 squares can't be mines
        row = mineRepresentationArray.length;
        col = mineRepresentationArray[0].length;

        // Adds 9 to the length, as they are used to keep track of where you shouldn't place a mine on
        int[][] mineLocations = new int[mineNum + 9][2]; // Example with 2 mines: {{ROW, COL}, {ROW, COL}}
        mineLocations[0][0] = clickRow;
        mineLocations[0][1] = clickCol;

        for (int i = 0; i < navigation.length; i++) { // Generates safe zone around mine, setting the first 9 indexs of teh mineLocation to the safe zone
            if (clickRow + navigation[i][0] < ROW && clickCol + navigation[i][1] < COL)
                mineLocations[i + 1][0] = clickRow + navigation[i][0];
            mineLocations[i + 1][1] = clickCol + navigation[i][1];
        }

        // While the mine-count isn't equal to the amount of mine wanted
        while (mineCount != mineNum + 9) { // +9 since we added 9 to mineCount
            int mineRow, mineCol;
            boolean mineExist;

            // Generates a random mine location and making sure it doesn't exist inside of teh mineLocations array
            do {
                mineExist = false;
                mineRow = (int) (Math.random() * (row));
                mineCol = (int) (Math.random() * (col));

                for (int i = 0; i < mineLocations.length; i++) { // Don't have to add 9 to the index, as at this moment, mineLocations is used to track places where you shouldn't place mines
                    for (int j = 0; j < mineLocations[0].length; j++) {
                        if (mineRow == mineLocations[i][0] && mineCol == mineLocations[i][1]) {
                            mineExist = true;
                            break;
                        }
                    }
                }
            } while (mineExist);

            // Adding the mine to the mineLocations array
            mineLocations[mineCount][0] = mineRow;
            mineLocations[mineCount][1] = mineCol;

            mineCount++; // Increase mine count
        }

        placeMines(mineLocations); // When placing the actual mine, we ignore the safezoned indexs
    }

    /**
     * Tracks actions, when user clicks the mines, etc
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Loops through the entire array to find the specific cell that triggered the actionPerformed (the cell that the user clicked on)
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                if (e.getSource() == mineFieldArray[i][j] && !flagArray[i][j]) { // Checks if the current cell is the one that's clicked on, and making sure that it isn't a flag
                    if (firstClick) { // Checks if it's the first click
                        firstClick = false;
                        generateBoard(i, j);
                        mineRepresentationArray[i][j] = 1; // Uncover the square
                        floodFill(i, j);
                    } else if (mineRepresentationArray[i][j] == 0) { // If user clicked on a covered square
                        mineRepresentationArray[i][j] = 1; // Uncover the square
                        if (!checkAdjacentMine(i, j)) { // If there are no adjacent mines, run the floodfill algorithm
                            floodFill(i, j);
                        }
                    } else if (mineRepresentationArray[i][j] == 2) { // If user clicked on a mine
                        showMine(); // Show the user the location of all mines
                        checkLose = true; // Sets lose to true
                    }
                    UpdateBoard(); // Ensure the board is up-to-date with information
                    break; // Break, since we don't have to check the rest of the loop as we've already found the one the user clicked on
                }
            }
        }

        // Setting difficulty to the appropriate value based on the size of board as well as amount of mines
        if (ROW == 9 && COL == 9 && mineNum == 10) {
            difficulty = "Beginner";
        } else if (ROW == 16 && COL == 16 && mineNum == 40) {
            difficulty = "Intermediate";
        } else if (ROW == 24 && COL == 24 && mineNum == 99) {
            difficulty = "Advanced";
        } else {
            difficulty = "custom";
        }

        if (checkWin()) { // Runs if the user wins
            try {
                new winWindow(difficulty, time);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }

        if (checkLose) { // Runs if teh user loses
            try {
                Thread.sleep(2000);
                new loseWindow(time);
                dispose();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    // Check if user won the game or not
    public boolean checkWin() {
        boolean win = true;

        // Making sure every square that isn't a mine is uncovered
        for (int i = 0; i < mineRepresentationArray.length; i++) {
            for (int j = 0; j < mineRepresentationArray[0].length; j++) {
                if (mineRepresentationArray[i][j] == 0) {
                    win = false;
                    break;
                }
            }
        }
        return win;
    }
}