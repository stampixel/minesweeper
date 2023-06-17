package Minesweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.TimeUnit;

public class BoardWindow extends JFrame implements ActionListener {
    private static final int WIDTH = 600;
    private static final int HEIGHT = 700;
    public JButton[][] mineFieldArray;
    public int[][] mineRepresentationArray;
    public int[][] mineAdjacentMatrix;
    int mineHeight;
    int mineWidth;
    boolean firstClick;
    int ROW;
    int COL;
    int mineNum;
    JPanel header;
    JPanel mineField;
    JPanel footer;
    ImageIcon one;
    ImageIcon two;
    ImageIcon three;
    ImageIcon four;
    ImageIcon five;
    ImageIcon six;
    ImageIcon seven;
    ImageIcon eight;
    ImageIcon mine;
    ImageIcon flag;
    ImageIcon emptySquare;
    ImageIcon coveredSquare;
    int[][] navigation;
    boolean[][] flagArray;
    ImageIcon[] mineImg;
    boolean checkLose;
    int flagCount;

    BoardWindow(int mineRow, int mineCol, int mineNum) {
        flagCount = mineNum;

        navigation = new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}, {1, 1}, {-1, 1}, {1, -1}, {-1, -1}}; // up, right, down, left (clockwise check 8 adjacent squares)

        mineImg = new ImageIcon[9];

        for (int i = 0; i < mineImg.length; i++) {
            mineImg[i] = new ImageIcon(i + ".png");
        }


        mine = new ImageIcon("Mine.png");
        flag = new ImageIcon("Flag.png");
        coveredSquare = new ImageIcon("ClickableSquare.png");

        mineHeight = 48;
        mineWidth = 48;

        firstClick = true;
        ROW = mineRow;
        COL = mineCol;

        checkLose = false;

        mineFieldArray = new JButton[ROW][COL];
        mineRepresentationArray = new int[ROW][COL];
        mineAdjacentMatrix = new int[ROW][COL];
        flagArray = new boolean[ROW][COL];

        this.mineNum = mineNum;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(mineRow * mineWidth, mineCol * mineHeight + 80 + 80);
        this.setVisible(true);

        this.setLayout(new BorderLayout(10, 10));


        header = new JPanel();
        mineField = new JPanel();
        footer = new JPanel();

        header.setBackground(Color.black);
        header.setPreferredSize(new Dimension(WIDTH, 80));

        mineField.setBackground(Color.darkGray);
        mineField.setLayout(new GridLayout(ROW, COL, 3, 3));

        footer.setBackground(Color.gray);
        footer.setPreferredSize(new Dimension(WIDTH, 80));

        this.add(header, BorderLayout.NORTH);
        this.add(mineField, BorderLayout.CENTER);
        this.add(footer, BorderLayout.SOUTH);

        generateMineField(ROW, COL);

        this.setVisible(true);
        UpdateBoard();
    }

    public static void main(String[] args) {
        new BoardWindow(8, 8, 10);
    }

    /**
     * Places the mines on the board
     *
     * @param mineLocations
     */
    public void placeMines(int[][] mineLocations) {
        for (int i = 9; i < mineLocations.length; i++) {
            mineRepresentationArray[mineLocations[i][0]][mineLocations[i][1]] = 2; // Set the location to a mine
        }
    }

    public void UpdateBoard() { // Have a premade board that stores the adjacent stuff
        for (int i = 0; i < mineRepresentationArray.length; i++) {
            for (int j = 0; j < mineRepresentationArray[0].length; j++) {

                if (mineRepresentationArray[i][j] == 0) {

                    mineFieldArray[i][j].setIcon(coveredSquare);

                } else if (mineRepresentationArray[i][j] == 1) {
                    int mineCount = 0;
                    for (int k = 0; k < navigation.length; k++) {
                        try {
                            if (mineRepresentationArray[i + navigation[k][0]][j + navigation[k][1]] == 2) {
                                mineCount += 1;
                            }
                        } catch (Exception e) {
//                            System.out.println("outside of square, pass");
                        }
                    }

                    mineFieldArray[i][j].setIcon(mineImg[mineCount]);
                }
                if (flagArray[i][j]) {
                    mineFieldArray[i][j].setIcon(flag);
                }

            }
        }
    }

    public boolean checkAdjacentMine(int row, int col) {
        boolean adjacent = false;
        for (int i = 0; i < navigation.length; i++) {
            try {
                if (mineRepresentationArray[row + navigation[i][0]][col + navigation[i][1]] == 2) {
                    adjacent = true;
                }
            } catch (Exception e) {

            }
        }
        return adjacent;
    }

    public void floodFill(int row, int col) {
        int fillRow;
        int fillCol;
        for (int i = 0; i < mineRepresentationArray.length; i++) {
            for (int j = 0; j < mineRepresentationArray[0].length; j++) {
            }
        }

        for (int i = 0; i < navigation.length; i++) {
            try {

                fillRow = row + navigation[i][0];
                fillCol = col + navigation[i][1];
                if (mineRepresentationArray[fillRow][fillCol] == 0) {
                    if (checkAdjacentMine(fillRow, fillCol)) {
                        mineRepresentationArray[fillRow][fillCol] = 1;
                        continue;
                    }
                    mineRepresentationArray[fillRow][fillCol] = 1;

                    floodFill(fillRow, fillCol);
                }
            } catch (Exception e) {

            }

        }
    }

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
     * 0 represents empty square, 1 uncovered square, 2 represents mine
     *
     * @param row
     * @param col
     */
    public void generateMineField(int row, int col) {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                mineFieldArray[i][j] = new JButton();
                mineFieldArray[i][j].addActionListener(this);
                int finalI = i;
                int finalJ = j;
                mineFieldArray[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (e.getButton() == 3 && mineRepresentationArray[finalI][finalJ] != 1) {
                            System.out.println("asdfaSDF");
                            flagArray[finalI][finalJ] = !flagArray[finalI][finalJ]; // Switch
                            UpdateBoard();
                        }
                    }
                });
                mineField.add(mineFieldArray[i][j]);
            }
        }
    }

    /**
     * @param clickRow The row which represents the user's first click
     * @param clickCol The col which represents the user's first click
     */
    public void generateBoard(int clickRow, int clickCol) {
        // Min + (int)(Math.random() * ((Max - Min) + 1))
        int row, col;
        int mineCount = 9; // Start at one since the first mine in mineLocation represent the first square clicked
        row = mineRepresentationArray.length;
        col = mineRepresentationArray[0].length;

        int[][] mineLocations = new int[mineNum + 9][2]; // Example with 2 mines: {{ROW, COL}, {ROW, COL}}
        mineLocations[0][0] = clickRow;
        mineLocations[0][1] = clickCol;

        for (int i = 0; i < navigation.length; i++) { // Generates safe zone around mine
            if (clickRow + navigation[i][0] < ROW && clickCol + navigation[i][1] < COL)
                mineLocations[i + 1][0] = clickRow + navigation[i][0];
            mineLocations[i + 1][1] = clickCol + navigation[i][1];
        }

        while (mineCount != mineNum + 9) { // +1 since we added 1 to mineCount
            int mineRow, mineCol;
            boolean mineExist;

            do {
                mineExist = false;
                mineRow = (int) (Math.random() * (row));
                mineCol = (int) (Math.random() * (col));

                for (int i = 0; i < mineLocations.length; i++) {
                    for (int j = 0; j < mineLocations[0].length; j++) {
                        if (mineRow == mineLocations[i][0] && mineCol == mineLocations[i][1]) {
                            mineExist = true;
                            break;
                        }
                    }
                }
            } while (mineExist);

            mineLocations[mineCount][0] = mineRow;
            mineLocations[mineCount][1] = mineCol;

            mineCount++;
        }

        placeMines(mineLocations);
    }

    /**
     * This method doesn't have access to what is passed into the constructor (reassign row/col inside constructor)
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                if (e.getSource() == mineFieldArray[i][j] && !flagArray[i][j]) {
                    if (firstClick) {
                        firstClick = false;
                        generateBoard(i, j);
                        mineRepresentationArray[i][j] = 1; // Uncover the square
                        if (!checkAdjacentMine(i, j)) {
                            floodFill(i, j);
                        }
                    } else if (mineRepresentationArray[i][j] == 0) { // If user clicked on a covered square
                        mineRepresentationArray[i][j] = 1; // Uncover the square
                        if (!checkAdjacentMine(i, j)) {
                            floodFill(i, j);
                        }
                    } else if (mineRepresentationArray[i][j] == 2) { // If user clicked on a mine
                        showMine();

                        checkLose = true;

                    }
                    UpdateBoard();
                    break;

                }
            }
        }

        if (checkWin()) {
            System.out.println("you win");
            // Navigate to the win menu here
        }
        if (checkLose) {
            System.out.println("You loose");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }

    }

    public boolean checkWin() {
        boolean win = true;
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
