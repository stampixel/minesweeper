package Minesweeper;

import javax.swing.*;

public abstract class BoardWindow extends JFrame {
    BoardWindow() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(600, 700);
        this.setVisible(true);
    }
}
