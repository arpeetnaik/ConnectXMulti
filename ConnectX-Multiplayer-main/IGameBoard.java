package cpsc2150.extendedConnectX.models;

/**
 * @author Arpeet Naik
 * @version 1.0
 */

/**
 * A GameBoard is a grid with MAX_ROW by MAX_COLUMN that holds char values
 *  the position 0,0 should be at the bottom left
 *
 * Initialization ensures: self is at most ROW_LEN by MAX_COLUMN
 *                         AND self is empty with an empty board
 *
 * Defines: self[MAX_ROW][MAX_COLUMN] = [any letter] - holds the char values for the game board
 *          MAX_ROW = Z - the number of rows of self
 *          MAX_COLUMN = Z - the number of columns of self
 *          MAX_NUM_TO_WIN = Z - the number of tokens in a row needed to win the game
 *
 * Constraints: self is MAX_ROW by MAX_COLUMN
 *              AND [there are no gaps between tokens placed]
 *              AND self[i][j]: 0 <= i < MAX_ROW AND 0 <= j < MAX_COLUMN
 *              AND self[i][j] == ' ' OR self[i][j] == 'X' OR self[i][j] == 'O'
 */

public interface IGameBoard {

    int MAX_ROW = 100;
    int MAX_COLUMN = 100;
    int MIN_ROW = 3;
    int MIN_COLUMN = 3;
    int MAX_NUM_TO_WIN = 25;
    int MIN_NUM_TO_WIN = 3;
    int MAX_PLAYERS = 10;
    int MIN_PLAYERS = 2;


    /**
     * @description This method checks to see if the column can accept a token
     *
     * @param c - the column we would like to place the token in
     * @return iff (c == ' ') then true AND iff else then false
     * @pre 0 <= c < MAX_COLUMN_NUM
     * @post iff (c == ' ') token is placed inside empty space ('X' OR 'O') AND
     * [i][j] Board include (' ' OR 'X' OR 'O') AND gameBoard = #gameBoard
     */

    public default boolean checkIfFree(int c) {
        BoardPosition val = null;
        for(int i = 0; i < this.getNumRows(); i++) {
            val = new BoardPosition(i, c);
            if(whatsAtPos(val) == (' ')) {
                return true;
            }
        }
        return false;
    }

    public void placeToken(char p, int c);

    /**
     * @description This method checks to see if the last token placed in the column won the game
     *
     * @param c - the column we would like to place the token in
     * @return iff c placed wins game then true AND iff else than false
     * @pre pos == [latest play/move]
     * @post iff checkHorizWin == true than win OR iff checkDiagWin == true than win
     * OR iff checkVertWin == true than win
     */

    public default boolean checkForWin(int c) {
        BoardPosition temp = null;
        BoardPosition val = null;
        char character = ' ';
        for(int i = 0; i < getNumRows(); i++) {
            val = new BoardPosition(i, c);
            if(whatsAtPos(val) == (' ')) {
                temp = new BoardPosition(i - 1, c);
                character = whatsAtPos(temp);
                break;
            }
            if(i == (getNumRows() - 1)) {
                temp = new BoardPosition(i, c);
                character = whatsAtPos(temp);
                break;
            }
        }
        if(checkHorizWin(temp, character)) {
            return true;
        }
        if(checkVertWin(temp, character)) {
            return true;
        }
        if(checkDiagWin(temp, character)) {
            return true;
        }
        return false;
    }

    /**
     * @description This method checks the board for open spots to see if there is a tie
     *
     * @return true iff no open spots left and false iff otherwise
     * @pre [iff game is not won]
     * @post iff all pos == 'X' OR 'O' AND check win functions != win then checkTie == true
     * AND theBoard = #theBoard
     */
    public default boolean checkTie() {
        BoardPosition val = null;
        for(int i = 0; i < getNumColumns(); i++) {
            for(int j = 0; j < getNumRows(); j++) {
                val = new BoardPosition(j, i);
                if(whatsAtPos(val) == (' ')) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * @description This method checks to see if the last token placed resulted in a horizontal win
     *
     * @param pos - position of the token on the board
     * @param p   - the character that would take up the spot
     * @return true iff NUM_TO_WIN in a row horizontally AND false iff otherwise
     * @pre LOWEST_COLUMN <= lastPos.getRow() < MAX_ROW_NUM AND LOWEST_COLUMN <= lastPos.getColumn() < MAX_COLUMN NUM
     * AND [lastPos was last token placement] AND [pos is within valid bounds]
     * @post [checkHorizWin == true iff NUM_TO_WIN in a row horizontally and player wins] AND
     * [checkHorizWin == false iff not NUM_TO_WIN in a row horizontally and next players turn] AND
     * theBoard = #theBoard
     */

    public default boolean checkHorizWin(BoardPosition pos, char p) {
        int count = 0;
        int row = pos.getRow();
        int column = pos.getColumn();
        BoardPosition temp = null;
        //checks to the right of pos
        while(true) {
            temp = new BoardPosition(row, column);
            if(whatsAtPos(temp) == (p)) {
                count++;
                //makes sure c doesn't go out of bounds
                if ((column + 1) < getNumRows()) {
                    column++;
                }
                else {
                    break;
                }
            }
            else {
                column = pos.getColumn();
                break;
            }
        }

        count--;
        while(true) {
            temp = new BoardPosition(row, column);
            if(whatsAtPos(temp) == (p)) {
                count++;
                if((column - 1) >= 0) {
                    column--;
                }
                else {
                    break;
                }
            }
            else {
                break;
            }
        }
        if(count >= getNumToWin()) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * @description This method checks to see if the last token placed resulted in a vertical win
     *
     * @param pos - position of the token on the board
     * @param p   - the character that would take up the spot
     * @return true iff NUM_TO_WIN in a row vertically AND false iff otherwise
     * @pre LOWEST_COLUMN <= lastPos.getRow() < MAX_ROW_NUM AND LOWEST_COLUMN <= lastPos.getColumn() < MAX_COLUMN NUM
     * AND [lastPos was last token placement] AND [pos is within valid bounds]
     * @post [checkVertWin == true iff NUM_TO_WIN in a row vertically AND player wins] AND
     * [checkVertWin == false iff not NUM_TO_WIN in a row vertically and next players turn] AND
     * theBoard = #theBoard
     */

    public default boolean checkVertWin(BoardPosition pos, char p) {
        int count = 0;
        int row = pos.getRow();
        int column = pos.getColumn();
        BoardPosition temp = null;
        //checks down from pos
        while(true) {
            temp = new BoardPosition(row, column);
            if (whatsAtPos(temp) == (p)) {
                count++;
                if ((row - 1) >= 0) {
                    row--;
                } else {
                    break;
                }
            }
            else {
                break;
            }
        }
        if(count >= getNumToWin()) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * @description This method checks to see if the last token placed resulted in a diagonal win
     *
     * @param pos - position of the token on the board
     * @param p   - the character that would take up the spot
     * @return true iff NUM_TO_WIN in a row diagonally AND false iff otherwise
     * @pre LOWEST_COLUMN <= lastPos.getRow() < MAX_ROW_NUM AND LOWEST_COLUMN <= lastPos.getColumn() < MAX_COLUMN NUM
     * AND [lastPos was last token placement] AND [pos is within valid bounds]
     * @post [checkDiagWin == true iff NUM_TO_WIN in a row diagonally AND player wins] AND
     * [checkDiagWin == false iff not NUM_TO_WIN in a row vertically and next players turn] AND
     * theBoard = #theBoard
     */

    public default boolean checkDiagWin(BoardPosition pos, char p) {
        int count1 = 1;
        int count2 = 1;
        int row = pos.getRow();
        int column = pos.getColumn();
        BoardPosition temp = null;
        //checks up and to the right
        count1--;
        while(true) {
            temp = new BoardPosition(row, column);
            if(whatsAtPos(temp) == (p)) {
                count1++;
                //makes sure r does not go out of bounds
                if((row + 1) < getNumRows()) {
                    row++; }
                else {
                    break;
                }
                //makes sure c does not go out of bounds
                if((column + 1) < getNumColumns()) {
                    column++; }
                else {
                    break;
                }
            }
            else {
                break;
            }
        }
        row = pos.getRow();
        column = pos.getColumn();
        //checks up and to the left
        count2--;
        while(true) {
            temp = new BoardPosition(row, column);
            if(whatsAtPos(temp) == (p)) {
                count2++;
                //makes sure r does not go out of bounds
                if((row + 1) < getNumRows()) {
                    row++;
                }
                else {
                    break; }
                //makes sure c does not go out of bounds
                if((column - 1) >= 0) {
                    column--; }
                else {
                    break;
                }
            }
            else {
                break;
            }
        }
        row = pos.getRow();
        column = pos.getColumn();
        //checks down and to the left
        count1--;
        while(true) {
            temp = new BoardPosition(row, column);
            if(whatsAtPos(temp) == (p)) {
                count1++;
                //makes sure r does not go out of bounds
                if((row - 1) >= 0) {
                    row--; }
                else { break;
                }
                //makes sure c does not go out of bounds
                if((column - 1) >= 0) {
                    column--; }
                else {
                    break;
                }
            }
            else {
                break;
            }
        }
        row = pos.getRow();
        column = pos.getColumn();
        //checks down and to the right
        count2--;
        while(true) {
            temp = new BoardPosition(row, column);
            if(whatsAtPos(temp) == (p)) {
                count2++;
                //makes sure r does not go out of bounds
                if ((row - 1) >= 0) {
                    row--;
                } else {
                    break;
                }
                //makes sure c does not go out of bounds
                if ((column + 1) < getNumColumns()) {
                    column++;
                } else {
                    break;
                }
            }
            else {
                break;
            }
        }
        row = pos.getRow();
        column = pos.getColumn();
        if((count1 >= getNumToWin()) || (count2 >= getNumToWin())) {
            return true;
        }
        else {
            return false;
        }
    }

    public char whatsAtPos(BoardPosition pos);

    /**
     * @description This method checks to see if player is at the position selected
     *
     * @param pos    - position of the token on the board
     * @param player - variable for player to move on board
     * @return true iff (player == pos) AND iff otherwise then false
     * @pre [pos is within valid bounds]
     * @post [function returns true if player is in the gameBoard at the specified position]
     */

    default boolean isPlayerAtPos(BoardPosition pos, char player) {
        if(whatsAtPos(pos) == player) {
            return true;
        }
        else {
            return false;
        }
    }

    public int getNumRows();
    public int getNumColumns();
    public int getNumToWin();


}