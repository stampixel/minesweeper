package Minesweeper.Algorithms;

/**
 * @author Kevin Tang
 * THIS FILE WAS USED ONLY TO TEST ALGORITHMS
 */
public class MineAlgorithm {

    public static void main(String[] args) {
        int[][] board = generateBoard(9, 9);
        generateMine(board, 30, 2, 2);

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * 0 represents empty square, 1 represents mine, 2 represents mine flagged by used
     *
     * @param row
     * @param cols
     * @return
     */
    public static int[][] generateBoard(int row, int col) {
        int[][] board = new int[row][col];

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                board[i][j] = 0;
            }
        }

        return board;
    }

    /**
     * @param board
     * @param mineNum
     * @param clickRow The row which represents the user's first click
     * @param clickCol The col which represents the user's first click
     */
    public static void generateMine(int[][] board, int mineNum, int clickRow, int clickCol) {
        // Min + (int)(Math.random() * ((Max - Min) + 1))
        int row, col;
        int mineCount = 1; // Start at one since the first mine in mineLocation represent the first square clicked
        row = board.length;
        col = board[0].length;

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

        placeMines(board, mineLocations);

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
}
