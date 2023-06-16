package Minesweeper;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Rules extends Window implements ActionListener{

    JButton button;

    Rules () {
        super();
        this.setTitle("Rules");
        this.setLayout(null);
        this.getContentPane().setBackground(Color.GRAY);

        JLabel title = new JLabel();
        title.setText("MineSweeper Rules");
        title.setFont(new Font("consolas",Font.PLAIN, 35));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setVerticalAlignment(SwingConstants.TOP);
        title.setBounds(350, 0, 600, 100);

        JLabel rules1 = new JLabel();
        rules1.setText("1. The goal of the game is to find all the predetermined mines and mark them down on the game board.");
        rules1.setFont(new Font("consolas",Font.PLAIN, 25));
        rules1.setVerticalAlignment(JLabel.TOP);
        rules1.setHorizontalAlignment(JLabel.LEFT);
        rules1.setBounds(0, 150, 1280, 200);

        JLabel rules2 = new JLabel();
        rules2.setText("2. The number that appears on a square after it is clicked is the number of bombs surrounding it.");
        rules2.setFont(new Font("consolas",Font.PLAIN, 25));
        rules2.setVerticalAlignment(SwingConstants.TOP);
        rules2.setHorizontalAlignment(JLabel.LEFT);
        rules2.setBounds(0, 200, 1280, 200);

        JLabel rules3 = new JLabel();
        rules3.setText("3. Use these numbers to determine where the next bomb will be!");
        rules3.setFont(new Font("Roboto", Font.PLAIN, 25));
        rules3.setVerticalAlignment(JLabel.TOP);
        rules3.setHorizontalAlignment(JLabel.LEFT);
        rules3.setBounds(0,250,1280,200);

        JLabel rules4 = new JLabel();
        rules4.setText("4. Be careful! If you click on a mine the game will end.");
        rules4.setFont(new Font("consolas", Font.PLAIN, 25));
        rules4.setVerticalAlignment(JLabel.TOP);
        rules4.setHorizontalAlignment(JLabel.LEFT);
        rules4.setBounds(0,300,1280,200);

        JLabel rules5 = new JLabel();
        rules5.setText("5. Try to find all the mines in the least amount of time to place on top of the leaderboards!");
        rules5.setFont(new Font("consolas", Font.PLAIN, 25));
        rules5.setVerticalAlignment(JLabel.TOP);
        rules5.setHorizontalAlignment(JLabel.LEFT);
        rules5.setBounds(0,350,1280,200);

        button = new JButton();
        button.setBounds(525, 500, 200, 100);
        button.addActionListener(this);
        button.setText("Back to Main Menu");
        button.setFont(new Font("consolas", Font.BOLD , 16));
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setBackground(Color.RED);

        ImageIcon bomb1 = new ImageIcon("bomb1.png");
        JLabel bomb1Holder = new JLabel(bomb1);

        JPanel BottomLeft = new JPanel();
        BottomLeft.add(bomb1Holder);
        BottomLeft.setBackground(Color.GRAY);
        BottomLeft.setBounds(200,450,200,200);

        ImageIcon Fun = new ImageIcon("Fun1.png");
        JLabel FunHolder = new JLabel(Fun);

        JPanel BottomRight = new JPanel();
        BottomRight.add(FunHolder);
        BottomRight.setBackground(Color.GRAY);
        BottomRight.setBounds(850,450,200,200);

        this.add(title);
        this.add(rules1);
        this.add(rules2);
        this.add(rules3);
        this.add(rules4);
        this.add(rules5);
        this.add(button);
        this.add(BottomLeft);
        this.add(BottomRight);
        this.setVisible(true);




    }
    public static void main(String[] args) {
        new Rules();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {
            new MainMenu();
        }

    }
}