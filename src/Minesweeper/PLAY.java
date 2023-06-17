package Minesweeper;

/**
 * @author Kevin Tang
 *
 * RUN THIS FILE TO PLAY THE GAME
 */

import static Minesweeper.MainMenu.playMusic; // Import the music player from the mainMenu file

public class PLAY {
    public static void main(String[] args) throws Exception {
        playMusic("Music.wav");
        new MainMenu();
    }
}
