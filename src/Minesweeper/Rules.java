package Minesweeper;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class Rules extends Window implements ActionListener{

    JButton button;

    Rules () throws Exception {
        super();
        this.setTitle("Rules");

        // Create border for buttons and labels
        LineBorder line = new LineBorder(Color.gray, 5, true);

        JLabel title = new JLabel();
        title.setText("Minesweeper Rules");
        title.setFont(new Font("Roboto",Font.PLAIN, 35));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setVerticalAlignment(SwingConstants.TOP);
        title.setBounds(350, 10, 600, 70);
        title.setBorder(line);
        title.setOpaque(true);
        title.setBackground(Color.lightGray);

        JLabel bottomLabel = new JLabel();
        bottomLabel.setVerticalAlignment(JLabel.TOP);
        bottomLabel.setHorizontalAlignment(JLabel.LEFT);
        bottomLabel.setBounds(0, 140, 1280, 250);
        bottomLabel.setOpaque(true);
        bottomLabel.setBackground(Color.lightGray);
        bottomLabel.setBorder(line);


        JLabel rules1 = new JLabel();
        rules1.setText("1. The goal of the game is to find all the predetermined mines and mark them down on the game board.");
        rules1.setFont(new Font("Roboto",Font.PLAIN, 24));
        rules1.setVerticalAlignment(JLabel.TOP);
        rules1.setHorizontalAlignment(JLabel.LEFT);
        rules1.setBounds(10, 150, 1270, 200);

        JLabel rules2 = new JLabel();
        rules2.setText("2. The number that appears on a square after it is clicked is the number of bombs surrounding it.");
        rules2.setFont(new Font("Roboto",Font.PLAIN, 24));
        rules2.setVerticalAlignment(SwingConstants.TOP);
        rules2.setHorizontalAlignment(JLabel.LEFT);
        rules2.setBounds(10, 200, 1270, 200);

        JLabel rules3 = new JLabel();
        rules3.setText("3. Use these numbers to determine where the next bomb will be!");
        rules3.setFont(new Font("Roboto", Font.PLAIN, 24));
        rules3.setVerticalAlignment(JLabel.TOP);
        rules3.setHorizontalAlignment(JLabel.LEFT);
        rules3.setBounds(10,250,1270, 200);

        JLabel rules4 = new JLabel();
        rules4.setText("4. Be careful! If you click on a mine the game will end.");
        rules4.setFont(new Font("Roboto", Font.PLAIN, 24));
        rules4.setVerticalAlignment(JLabel.TOP);
        rules4.setHorizontalAlignment(JLabel.LEFT);
        rules4.setBounds(10,300,1270,200);

        JLabel rules5 = new JLabel();
        rules5.setText("5. Try to find all the mines in the least amount of time to place on top of the leaderboards!");
        rules5.setFont(new Font("Roboto", Font.PLAIN, 24));
        rules5.setVerticalAlignment(JLabel.TOP);
        rules5.setHorizontalAlignment(JLabel.LEFT);
        rules5.setBounds(10,350,1270,200);

        button = new JButton();
        button.setBounds(525, 500, 200, 100);
        button.addActionListener(this);
        button.setText("Back to Main Menu");
        button.setFont(new Font("Roboto", Font.BOLD , 18));
        button.setOpaque(true);
        button.setBackground(Color.lightGray);
        button.setBorder(line);

        ImageIcon bomb1 = new ImageIcon("bomb1.png");
        JLabel bomb1Holder = new JLabel(bomb1);

        JPanel BottomLeft = new JPanel();
        BottomLeft.add(bomb1Holder);
        BottomLeft.setBackground(new Color(0, 0, 0, 0));
        BottomLeft.setBounds(200,450,200,200);

        ImageIcon Fun = new ImageIcon("Fun1.png");
        JLabel FunHolder = new JLabel(Fun);

        JPanel BottomRight = new JPanel();
        BottomRight.add(FunHolder);
        BottomRight.setBackground(new Color(0, 0, 0, 0));
        BottomRight.setBounds(850,450,200,200);

        BottomPanel background = new BottomPanel();
        background.setLayout(null);

        background.add(title);
        background.add(rules1);
        background.add(rules2);
        background.add(rules3);
        background.add(rules4);
        background.add(rules5);
        background.add(button);
        background.add(bottomLabel);
        background.add(BottomLeft);
        background.add(BottomRight);
        this.add(background);
        this.setVisible(true);




    }
    public static void main(String[] args) throws Exception {
        new Rules();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {
            try {
                new MainMenu();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            dispose();
        }
    }
}