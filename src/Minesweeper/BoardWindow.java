package Minesweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BoardWindow extends JFrame implements ActionListener {
    int mineHeight;
    int mineWidth;
    private static final int WIDTH = 600;
    private static final int HEIGHT = 700;
    public JButton[][] mineFieldArray;
    public int[][] buttonRepresentationArray;
    boolean firstClick;
    int row;
    int col;
    int mineNum;
    JPanel header;
    JPanel mineField;
    JPanel footer;


    BoardWindow(int mineRow, int mineCol, int mineNum) {
        mineHeight = 48;
        mineWidth = 48;
        firstClick = true;
        row = mineRow;
        col = mineCol;
        mineNum = this.mineNum;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(mineRow*mineWidth, mineCol*mineHeight+80+80);
        this.setVisible(true);

        this.setLayout(new BorderLayout(10, 10));


        header = new JPanel();
        mineField = new JPanel();
        footer = new JPanel();

        header.setBackground(Color.black);
        header.setPreferredSize(new Dimension(WIDTH, 80));

        mineField.setBackground(Color.darkGray);
        mineField.setLayout(new GridLayout(row, col, 3, 3));

        footer.setBackground(Color.gray);
        footer.setPreferredSize(new Dimension(WIDTH, 80));

        this.add(header, BorderLayout.NORTH);
        this.add(mineField, BorderLayout.CENTER);
        this.add(footer, BorderLayout.SOUTH);

        generateMineField(row, col);

        this.setVisible(true);
    }

    public static void main(String[] args) {
        new BoardWindow(9, 9, 9);
    }

    /**
     * Places the mines on the board
     *
     * @param board
     * @param mineLocations
     */
    public static void placeMines(int[][] board, int[][] mineLocations) {
        System.out.println(mineLocations.length);
        for (int i = 1; i < mineLocations.length; i++) {
            board[mineLocations[i][0]][mineLocations[i][1]] = 1; // Set the location to a mine
        }
    }

    /**
     * 0 represents empty square, 1 represents mine, 2 represents mine flag, 3 represents flagged mine
     *
     * @param row
     * @param col
     */
    public void generateMineField(int row, int col) {
        mineFieldArray = new JButton[row][col];
        buttonRepresentationArray = new int[row][col];

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                mineFieldArray[i][j] = new JButton();
                mineFieldArray[i][j].addActionListener(this);
                mineFieldArray[i][j].setSize(new Dimension(10, 10));
                mineFieldArray[i][j].setBackground(Color.RED);
                mineField.add(mineFieldArray[i][j]);
            }
        }
    }

    /**
     * @param mineNum
     * @param clickRow The row which represents the user's first click
     * @param clickCol The col which represents the user's first click
     */
    public void generateMinePosition(int clickRow, int clickCol) {
        // Min + (int)(Math.random() * ((Max - Min) + 1))
        int row, col;
        int mineCount = 1; // Start at one since the first mine in mineLocation represent the first square clicked
        row = buttonRepresentationArray.length;
        col = buttonRepresentationArray[0].length;

        int[][] mineLocations = new int[mineNum + 1][2]; // Example with 2 mines: {{ROW, COL}, {ROW, COL}}
        mineLocations[0][0] = clickRow;
        mineLocations[0][1] = clickCol;
        System.out.println(mineLocations[0][0] + " " + mineLocations[0][1]);

        while (mineCount != mineNum + 1) { // +1 since we added 1 to mineCount
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

        placeMines(buttonRepresentationArray, mineLocations);

    }

    /**
     * This method doesn't have access to what is passed into the constructor (reassign row/col inside constructor)
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {


        System.out.println(row);

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (e.getSource() == mineFieldArray[i][j]) {
                    if (firstClick) {
                        firstClick = false;
                        generateMinePosition(i, j);
                    }
                    System.out.println(i + " " + j);
                }
            }
        }


    }
}
