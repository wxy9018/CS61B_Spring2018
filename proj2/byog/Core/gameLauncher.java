package byog.Core;

import java.util.*;

/**
 * gameLauncher that displays the main menu and interacts with user input
 */
public class gameLauncher {

    private int WIDTH;
    private int HEIGHT;
    private boolean gameOver;
    private final Set<Character> validInput = Set.of('A', 'B', 'Q');
    private final LinkedList<String> options = new LinkedList<>(Arrays.asList("Main Menu Options:",
            "A: Start a new game",
            "B: Load a previously saved game",
            "Q: Quit"));

    // constructor that sets the parameters
    gameLauncher(int WIDTH, int HEIGHT) {
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        gameOver = false;
    }

    // displays the main menu and responds to user input
    void startGame() {
        // Prepare the canvas
        displayHandler display = new displayHandler(this.WIDTH, this.HEIGHT, this.validInput, this.options);
        display.canvasPrepare();

        while (!gameOver) {
            char userInput = display.menuDisplay();
            switch (userInput) {
                case 'A': // start a new game
                    new newGame(WIDTH,HEIGHT).startGame();
                    break;
                case 'B': // load a previously saved game (map and player position).
                    loadGame.load();
                    break;
                case 'Q': // quit game. break the loop
                    gameOver = true;
                    break;
                default: // invalid input. do nothing and continue to display the menu
                    //display.menuDisplay();
                    break;
            }
        }
        System.exit(0); // close the game window and quit the whole game
    }
}
