package byog.lab6;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class MemoryGame {
    private int width;
    private int height;
    private int round;
    private Random rand;
    private boolean gameOver;
    private boolean playerTurn;
    private int encourageIndex;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        int seed = Integer.parseInt(args[0]);
        MemoryGame game = new MemoryGame(40, 40, seed);
        game.startGame();
    }

    public void startGame() {
        //TODO: Set any relevant variables before the game starts
        //TODO: Establish Game loop
        round = 0;
        gameOver = false;

        while (!gameOver) {
            playerTurn = false;
            encourageIndex = rand.nextInt(ENCOURAGEMENT.length);
            round++;
            drawFrame("Round: " + round);
            StdDraw.pause(1500);
            String targetString = generateRandomString(round);
            flashSequence(targetString);

            playerTurn = true;
            encourageIndex = rand.nextInt(ENCOURAGEMENT.length);
            String userInput = solicitNCharsInput(round);
            if (userInput.equals(targetString)) {
                StdDraw.pause(500);
                drawFrame("Congratulations! You are moving to round " + (round + 1));
                StdDraw.pause(1500);
                drawFrame("");
                StdDraw.pause(1000);
            } else {
                StdDraw.pause(500);
                drawFrame("Game Over! You made it to round: " + round);
                gameOver = true;
            }
        }
    }

    public MemoryGame(int width, int height, int seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        this.rand = new Random(seed);
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        //TODO: Initialize random number generator
    }

    public String generateRandomString(int n) {
        //TODO: Generate random string of letters of length n
        char[] result = new char[n];
        for (int i = 0; i < n; i++) {
            int nextChar = rand.nextInt(26);
            result[i] = CHARACTERS[nextChar];
        }
        String randomString = String.valueOf(result); // cannot use randomString = result.toString(), since that will tranlate the address of [result] to a string
        // System.out.println(randomString);
        return randomString;
    }

    public void drawFrame(String s) {
        //TODO: Take the string and display it in the center of the screen
        //TODO: If game is not over, display relevant game information at the top of the screen

        StdDraw.clear(Color.BLACK);
        Font largeFont = new Font("Arial Bold", Font.BOLD,30);
        Font smallFont = new Font("Arial Bold", Font.ITALIC,20);

        if (!gameOver) { // Draw the UI
            StdDraw.setFont(smallFont);
            StdDraw.textLeft(1, height - 1, "Round: " + round);
            StdDraw.text(width/2, height - 1, playerTurn ? "Type!" : "Watch!"); // good way of combining conditional statement into the syntax
            StdDraw.textRight(width - 1, height - 1, ENCOURAGEMENT[encourageIndex]);
            StdDraw.line(0, height - 2, width, height - 2);
        }

        // draw the game text
        StdDraw.setFont(largeFont);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(width/2, height/2, s);
        StdDraw.show();
    }

    public void flashSequence(String letters) {
        //TODO: Display each character in letters, making sure to blank the screen between letters
        char[] display = letters.toCharArray();
        int displayTime = 1000;
        int waitTime = 500;
        for (char letter : display) {
            drawFrame(String.valueOf(letter));
            StdDraw.pause(displayTime);
            drawFrame("");
            StdDraw.pause(waitTime);
        }

    }

    public String solicitNCharsInput(int n) {
        //TODO: Read n letters of player input
        String display = "";
        int pauseTime = 100;
        drawFrame(display);
        // StdDraw does not support waiting for user input, thus has to use a dead loop and check every a few milliseconds before get the target string length
        while (display.length() < n) { // cannot use for loop here, since if there is no user input, this loop has to grow while waiting.
            if (!StdDraw.hasNextKeyTyped()) {
                StdDraw.pause(100); // did not find user input, pause 50mS and check again
                continue;
            }
            display += String.valueOf(StdDraw.nextKeyTyped());
            drawFrame(display);
            StdDraw.pause(pauseTime);
        }
        return display;
    }
}
