import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class HangmanGUI extends Application {
    static Button newGame, runGame;
    static TextField textField;
    static TextArea textArea;
    static char guess;

    //public static
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Hangman");

        newGame = new Button("Start New Game");
        newGame.setOnAction(e -> HangmanFX.main(new String[0]) );
        runGame = new Button("Confirm Guess");


        textField = new TextField();
        //textField.setTranslateX(10);
        //textField.setTranslateY(50);

        textArea = new TextArea();
        //textArea.setTranslateX(20); textArea.setTranslateY(80);
       // textArea.setPrefSize(220, 170);
        textArea.setWrapText(true);


        BorderPane layout = new BorderPane();
        //layout.getChildren().addAll(button, textField, textArea);
        layout.setCenter(textArea); layout.setBottom(textField); layout.setLeft(newGame); layout.setRight(runGame);


        Scene scene = new Scene(layout, 550, 350);
        primaryStage.setScene(scene);
        primaryStage.show();


    }

//====================================================================================//


    public static class HangmanFX {
        public static void main(String[] args) {
            // Complete this method

            Scanner kbd = new Scanner(String.valueOf(System.out));

            AtomicInteger guessCount = new AtomicInteger(5);
            String secretWord = Word(Grabber());
            String[] badGuess = new String[secretWord.length()];
            String[] showGuess = new String[secretWord.length()];
            Arrays.fill(showGuess, " _ ");
            Arrays.fill(badGuess, " _ ");


            textArea.setText(textArea.getText() + "\nI am thinking of a word that is " + secretWord.length() + " letters long.");
            textArea.setText(textArea.getText() + "\nYou have " + guessCount + " guess(es) left.\nPlease guess a letter: ");

            runGame.setOnAction(ae -> {
                if (guessCount.get() != 0) {
                    if(textField.getText().equals("") || !Character.isLetter(textField.getText().charAt(0))) {
                        textArea.setText(textArea.getText() + "\nPlease try entering a letter instead");
                    }
                    else {
                        guess = textField.getText().charAt(0);

                    for (int i = 0; i < secretWord.length(); i++) {
                        if (errorCheck(showGuess, guess).equals(guess + "") || errorCheck(badGuess, guess).equals(guess + "")) {
                            textArea.setText(textArea.getText() + "\nOops! You've already guessed that letter: ");
                            break;
                        } else if (stringToChar(secretWord)[i] == guess) {
                            textArea.setText(textArea.getText() + "\nGood guess: ");
                            for (int j = 0; j < showGuess.length; j++) {
                                if (stringToChar(secretWord)[j] == guess) {
                                    showGuess[j] = blankArray(secretWord, guess)[j];
                                    continue;
                                }
                            }
                            break;
                        } else if (errorCheck(stringArray((secretWord)), guess).equals("Error")) {
                            guessCount.getAndDecrement();
                            textArea.setText(textArea.getText() + "\nOops! That letter is not in my word: ");
                            for (int k = 0; k < badGuess.length; k++) {
                                if (badGuess[k] != " _ ") {
                                    continue;
                                } else {
                                    badGuess[k] = guess + "";
                                    break;
                                }
                            }
                            break;
                        }
                    }

                    for (String s : showGuess) {
                        textArea.setText(textArea.getText() + s);
                    }

                    textArea.setText(textArea.getText() + "\n-------------");


                }
                }  /* Do Nothing */
                // Display if player won or lost

                if (winCheck(showGuess)) {
                    textArea.setText(textArea.getText() + "\nCongratulations, you won!");
                } else if (guessCount.get() == 0) {
                    textArea.setText(textArea.getText() + "\nSorry, you ran out of guesses. The word was " + secretWord);
                }
                else if(!textField.getText().equals("") && Character.isLetter(textField.getText().charAt(0))){
                    textArea.setText(textArea.getText() + "\nYou have " + guessCount + " guess(es) left.\nPlease guess a letter: ");
                }
            });

        } // End of main method

        // Create methods here

        public static boolean winCheck(String[] showGuessArr) {
            boolean winChecker = true;
            for (String s : showGuessArr) {
                if ((s.equals(" _ "))) {
                    winChecker = false;
                    break;
                }
            }
            return winChecker;
        }

        public static String errorCheck(String[] showGuessArr, char guessedLetter) {
            String checker = "Error";
            for (String s : showGuessArr) {
                if (s.equals(guessedLetter + "")) {
                    checker = s + "";
                    break;
                }
            }
            return checker;
        }

        public static String[] stringArray(String secretWord) {
            String[] secretWordArr = new String[secretWord.length()];
            for (int i = 0; i < secretWordArr.length; i++) {
                secretWordArr[i] = stringToChar(secretWord)[i] + "";
            }
            return secretWordArr;
        }

        public static String[] blankArray(String secretWord, char guessedLetter) {
            String[] partialArr = new String[secretWord.length()];
            for (int i = 0; i < partialArr.length; i++) {
                partialArr[i] = (" _ ");

                if (stringToChar(secretWord)[i] == guessedLetter) {
                    partialArr[i] = guessedLetter + "";
                }
            }
            return partialArr;
        }

        public static String Word(int Grabber) {
            String wordGrabbed = " ";
            for (int i = Grabber; i <= Grabber; i++) {
                wordGrabbed = loadWords()[i];
            }
            return wordGrabbed;
        }

        public static int Grabber() {
            return (int) (Math.random() * 55909 + 1);
        }


        /* Helper Code -----------------------------------------------
         * You do not have to understand the provided helper methods
         * But you will have to know how/when to call these methods
         * Make sure to read the instructions
         * DO NOT make any changes to the methods below UNLESS specified
         * by the directions.
         */

        public static String[] loadWords() {
            /*
             * Returns a String array of valid words.
             * Also prints out the total number of words (Strings) in the array.
             */

            ArrayList<String> wordList = new ArrayList<String>();
            File f = new File("words.txt");
            String[] wordsArr = new String[wordList.size()];
            try {
                Scanner in = new Scanner(f);
                while (in.hasNext()) {
                    String word = in.next();
                    wordList.add(word);
                }
                in.close();
                textArea.setText("Loading words from the file......\n" +
                                 wordList.size() + " words loaded.\n" +
                                 "Welcome to the game, Hangman!\n" +
                                 "-------------");
                wordsArr = (String[]) wordList.toArray(wordsArr);
            } catch (FileNotFoundException ex) {
               textArea.setText("File not found.");
            }
            return wordsArr;
        }

        public static char[] stringToChar(String secretWord) {
            /**
             * takes a string which is a secretWord
             * Returns a char array of secretWord
             * You can use printArray method to test the output
             */
            char[] secretArr = new char[secretWord.length()];
            for (int i = 0; i < secretArr.length; i++) {
                secretArr[i] = secretWord.charAt(i);
            }
            return secretArr;
        }

        // End of Helper Code----------------------------------

    } //program ends
}
