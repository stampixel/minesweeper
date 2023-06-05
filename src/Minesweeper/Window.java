package Minesweeper;

import javax.swing.*;
import java.awt.*;

public abstract class Window extends JFrame {
    Window() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false); // Prevent frame from being resized
        this.setSize(500, 500); // Sets width:height
        this.setVisible(true); // Make frame visible
    }
}
