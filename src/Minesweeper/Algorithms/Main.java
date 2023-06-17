package Minesweeper.Algorithms;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * @author Kevin Tang
 * THIS FILE WAS USED ONLY TO TEST ALGORITHMS
 */
public class Main {

    public static void main(String[] args) {
        new Main();
    }

    public Main() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(new TestPane());
        frame.pack();
        frame.setVisible(true);
    }
}

class TestPane extends JPanel {
    JLabel label;
    Timer timer;
    int count;
    public TestPane() {
        label = new JLabel("...");
        setLayout(new GridBagLayout());
        add(label);
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                count++;
                if (count < 100000) {
                    label.setText(Integer.toString(count));
                } else {
                    ((Timer) (e.getSource())).stop();
                }
            }
        });
        timer.setInitialDelay(0);
        timer.start();
    }
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(200, 200);
    }
}