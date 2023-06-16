package Minesweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BoardWindow extends JFrame implements ActionListener {
    private static final int WIDTH = 600;
    private static final int HEIGHT = 700;
    public JButton[][] mineFieldArray;
    public int[][] mineRepresentationArray;
    public int[][] mineAdjacentMatrix;
    int mineHeight;
    int mineWidth;
    boolean firstClick;
    int row;
    int col;
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


    BoardWindow(int mineRow, int mineCol, int mineNum) {
        one = new ImageIcon("1.png");
        two = new ImageIcon("2.png");
        three = new ImageIcon("3.png");
        four = new ImageIcon("4.png");
        five = new ImageIcon("5.png");
        six = new ImageIcon("6.png");
        seven = new ImageIcon("7.png");
        eight = new ImageIcon("8.png");
        mine = new ImageIcon("Mine.png");
        flag = new ImageIcon("Flag.png");
        emptySquare = new ImageIcon("EmptySquare.png");
        coveredSquare = new ImageIcon("ClickableSquare.png");

        mineHeight = 48;
        mineWidth = 48;

        firstClick = true;
        row = mineRow;
        col = mineCol;

        mineFieldArray = new JButton[row][col];
        mineRepresentationArray = new int[row][col];
        mineAdjacentMatrix = new int[row][col];

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
        mineField.setLayout(new GridLayout(row, col, 3, 3));

        footer.setBackground(Color.gray);
        footer.setPreferredSize(new Dimension(WIDTH, 80));

        this.add(header, BorderLayout.NORTH);
        this.add(mineField, BorderLayout.CENTER);
        this.add(footer, BorderLayout.SOUTH);

        generateMineField(row, col);

        this.setVisible(true);
        UpdateBoard();
    }

    public static void main(String[] args) {
        new BoardWindow(9, 9, 50);
    }

    /**
     * Places the mines on the board
     *
     * @param mineLocations
     */
    public void placeMines(int[][] mineLocations) {
        for (int i = 1; i < mineLocations.length; i++) {
            mineRepresentationArray[mineLocations[i][0]][mineLocations[i][1]] = 2; // Set the location to a mine
        }
    }

    public void UpdateBoard() { // Have a premade board that stores the adjacent stuff
        for (int i = 0; i < mineRepresentationArray.length; i++) {
            for (int j = 0; j < mineRepresentationArray[0].length; j++) {
                if (mineRepresentationArray[i][j] == 0) {
                    mineFieldArray[i][j].setIcon(coveredSquare);
                } else if (mineRepresentationArray[i][j] == 1) {
                    mineFieldArray[i][j].setIcon(emptySquare);
                }
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
     * 0 represents empty square, 1 represents clicked empty square, 2 represents mine, 3 represents mine flag, 4 represents flagged mine
     *
     * @param row
     * @param col
     */
    public void generateMineField(int row, int col) {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                mineFieldArray[i][j] = new JButton();
                mineFieldArray[i][j].addActionListener(this);
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
        int mineCount = 1; // Start at one since the first mine in mineLocation represent the first square clicked
        row = mineRepresentationArray.length;
        col = mineRepresentationArray[0].length;

        int[][] mineLocations = new int[mineNum + 1][2]; // Example with 2 mines: {{ROW, COL}, {ROW, COL}}
        mineLocations[0][0] = clickRow;
        mineLocations[0][1] = clickCol;

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

        placeMines(mineLocations);
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
                        generateBoard(i, j);

                    } else if (mineRepresentationArray[i][j] == 0) { // If user clicked on a covered square
                        mineRepresentationArray[i][j] = 1; // Uncover the square
                        // run flood fill algorithm
                    } else if (mineRepresentationArray[i][j] == 2) { // If user clicked on a mine
                        showMine();
                    }
                    UpdateBoard();
                }
            }
        }


    }
}
