package Minesweeper;

import javax.swing.*;
import java.awt.*;

public abstract class Window extends JFrame {
    String font;
    int smallFont;
    int mediumFont;
    int largeFont;
    int titleFont;

    Window() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(1280, 720);

        font = "Roboto";
        smallFont = 15;
        mediumFont = 20;
        largeFont = 30;
        titleFont = 50;
    }
}
