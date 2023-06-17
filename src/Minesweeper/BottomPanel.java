package Minesweeper;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * This class creates a panel with an image as its background which serves as a background panel for other components
 *
 * @author Mikail.Hussain
 */
public class BottomPanel extends JPanel {
    Image img;

    @Override
    protected void paintComponent(Graphics g) {
        try {
            img = ImageIO.read(new File("BG.png"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        g.drawImage(img, 0, 0, null);
    }
}