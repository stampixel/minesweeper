package Minesweeper;

import java.util.Arrays;

public class FloodFill {

    public static void floodFill(int[][] board, int row, int col, boolean[][] visited) {
        // Check if the current cell is out of bounds or already visited
        if (row < 0 || row >= board.length || col < 0 || col >= board[0].length || visited[row][col]) {
            return;
        }

        // Check if the current cell is a mine or has adjacent mines
        if (board[row][col] != 0) {
            return;
        }

        // Mark the current cell as visited
        visited[row][col] = true;

        // Recursively call floodFill on adjacent cells
        floodFill(board, row - 1, col - 1, visited);
        floodFill(board, row - 1, col, visited);
        floodFill(board, row - 1, col + 1, visited);
        floodFill(board, row, col - 1, visited);
        floodFill(board, row, col + 1, visited);
        floodFill(board, row + 1, col - 1, visited);
        floodFill(board, row + 1, col, visited);
        floodFill(board, row + 1, col + 1, visited);
    }

    public static void main(String[] args) {
        // Sample Minesweeper board (5x5)
        int[][] board = {
                {0, 0, 0, 0, 0},
                {0, 1, 1, 0, 0},
                {0, 1, 0, 0, 0},
                {0, 1, 1, 0, 0},
                {0, 0, 0, 0, 0}
        };

        // Starting cell coordinates
        int startRow = 2;
        int startCol = 2;

        // Array to keep track of visited cells
        boolean[][] visited = new boolean[board.length][board[0].length];

        // Call floodFill
        floodFill(board, startRow, startCol, visited);

        // Print the resulting visited cells
        for (int i = 0; i < visited.length; i++) {
            for (int j = 0; j < visited[0].length; j++) {
                if (visited[i][j]) {
                    System.out.println("Visited cell: (" + i + ", " + j + ")");
                }
            }
        }
    }
}
