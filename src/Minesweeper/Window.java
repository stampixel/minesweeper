package Minesweeper;

import javax.swing.*;

public abstract class Window extends JFrame {
    Window() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(1280, 720);
    }
}
