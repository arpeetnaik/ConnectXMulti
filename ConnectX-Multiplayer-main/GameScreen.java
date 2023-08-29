package cpsc2150.extendedConnectX.models;

/**
 * @author Arpeet Naik
 * @version 1.0
 */

import java.util.Scanner;

/**
 * @description This is where the main method is and controls the game's flow.
 *
 *
 * @correspondence none
 *
 * @invariants
 *  playerTurn = 'X' OR playerTurn = 'O'
 *  AND Board[i][j]: 0 <= i < MAX_ROW AND 0 <= j < MAX_COLUMN
 *  AND Board[i][j] == ' ' OR Board[i][j] == 'X' OR Board[i][j] == 'O'
 *  AND [there are no gaps between tokens placed]
 *  AND Board[0...MAX_ROW_NUM-1]
 *
 */


public class GameScreen {

    /**
     * @description main program to run the game
     *
     *
     * @pre none
     *
     * @post game runs until a winner or tie is determined
     */

    public static void main (String[] args){

        Character [] player;
        IGameBoard Board = null;
        Scanner scan = new Scanner(System.in);
        int row = 0;
        int column = 0;
        int numToWin = 0;
        int numberOfPlayers = 0;
        int numberOfTurns = 0;
        int playerInput = 0;
        String select = " ";
        char gameType = ' ';

        while(true) {
            while(true) {
                System.out.println("How many players?");
                playerInput = scan.nextInt();
                if((playerInput <= IGameBoard.MAX_PLAYERS) && (playerInput >=
                        IGameBoard.MIN_PLAYERS)) {
                    numberOfPlayers = playerInput;
                    break;
                }
                else if(playerInput > IGameBoard.MAX_PLAYERS){
                    System.out.println("Must be " + IGameBoard.MAX_PLAYERS + " players or fewer");
                }
                else if(playerInput < IGameBoard.MIN_PLAYERS){
                    System.out.println("Must be at least " + IGameBoard.MIN_PLAYERS + " players");
                }
            }
            player = new Character[numberOfPlayers];
            for(int i = 0; i < numberOfPlayers; i++) {
                player[i] = ' ';
            }
            scan.nextLine();
            while(numberOfTurns < numberOfPlayers) {
                System.out.println("Enter the character to represent player " + (numberOfTurns + 1));
                select = scan.nextLine();
                for (int i = 0; i < numberOfPlayers; i++) {
                    if (select.equals(player[i].toString())) {
                        System.out.println(select + " is already taken as a player token!");
                        break;
                    } else {
                        player[numberOfTurns] = select.charAt(0);
                        numberOfTurns++;
                        break;
                    }
                }
            }
            numberOfTurns = 0;

            while(true) {
                System.out.println("How many rows should be on the board?");
                playerInput = scan.nextInt();
                scan.nextLine();
                if ((playerInput <= IGameBoard.MAX_ROW) && (playerInput >=
                        IGameBoard.MIN_ROW)) {
                    row = playerInput;
                    break;
                }
                else {
                    System.out.println("Number of rows must be greater than 3 and less than 100");
                }
            }
            while(true) {
                System.out.println("How many columns should be on the board?");
                playerInput = scan.nextInt();
                scan.nextLine();
                if((playerInput <= IGameBoard.MAX_COLUMN) && (playerInput >=
                        IGameBoard.MIN_COLUMN)) {
                    column = playerInput;
                    break; }
                else {
                    System.out.println("Number of columns must be greater than 3 and less than 100");
                }
            }
            while(true) {
                System.out.println("How many in a row to win?");
                playerInput = scan.nextInt();
                scan.nextLine();
                if((playerInput <= IGameBoard.MAX_NUM_TO_WIN) && (playerInput >=
                        IGameBoard.MIN_NUM_TO_WIN)) {
                    if((playerInput > column) || (playerInput > row)) {
                        System.out.println(("Number in a row must be greater than or equal to number of rows and columns"));
                    } else {
                        numToWin = playerInput;
                        break; }
                } else {
                    System.out.println("Number in a row must be greater than 3 and less than 100");
                } }
            while(true) {
                System.out.println("Would you like a Fast Game (F/f) or a Memory Efficient Game (M/m)?");
                        select = scan.nextLine();
                if(select.equalsIgnoreCase("f")) {
                    gameType = 'f';
                    break; }
                else if(select.equalsIgnoreCase("m")){
                    gameType = 'm';
                    break; }
                else {
                    System.out.println("Please enter F or M");
                } }
            if(gameType == 'f') {
                Board = new GameBoard(row, column, numToWin);
            }
            else {
                Board = new GameBoardMem(row, column, numToWin);
            }
            System.out.println(Board.toString());
            while(true) {
                while (true) {
                    System.out.println("Player " + player[numberOfTurns] + ", what column do you want to place your marker in?");
                    playerInput = scan.nextInt();
                    if (playerInput < 0) {
                        System.out.println("Column cannot be less than 0");
                    }
                    else if (playerInput >= (Board.getNumColumns())) {
                        System.out.println("Column cannot be greater than " +
                               Board.getNumColumns());
                    }
    else if (!Board.checkIfFree(playerInput)) {
                        System.out.println("Column is full");
                    } else {
                       Board.placeToken(player[numberOfTurns], playerInput);
                        System.out.println(Board.toString());
                        break; }
                }


                if(Board.checkForWin(playerInput)) {
                    System.out.println("Player " + player[numberOfTurns] + " Won!");
                            scan.nextLine();
                    while(true) {
                        System.out.println("Would you like to play again? Y/N");
                        select = scan.nextLine();
                        if((select).equalsIgnoreCase("Y")) {
                            break; }
                        if((select).equalsIgnoreCase("N")) {
                            break; }
                    }
                    if((select).equalsIgnoreCase("Y")) {
                        Board = new GameBoard(row, column, numToWin);
                        numberOfTurns = 0;
                        break;
                    }
                    else {
                        break;
                    }
                }
                else if(Board.checkTie()) {
                    System.out.println("It's a tie!");
                    scan.nextLine();
                    while(true) {
                        System.out.println("Would you like to play again? Y/N");
                        select = scan.nextLine();
                        if((select).equalsIgnoreCase("Y")) {
                            break; }
                        if((select).equalsIgnoreCase("N")) {
                            break; }
                    }
                    if((select).equalsIgnoreCase("Y")) {
                        Board = new GameBoard(row, column, numToWin);
                        break;
                    }
                    else {
                        break;
                    }
                }
                numberOfTurns++;
                if(numberOfTurns >= numberOfPlayers) {
                    numberOfTurns = 0;
                }
            }
            if((select).equalsIgnoreCase("N")) {
                break;
            }
        }
    }
}



