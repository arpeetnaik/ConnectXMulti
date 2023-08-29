package cpsc2150.extendedConnectX.models;

import static org.junit.Assert.*;
import org.junit.Test;

public class TestGameBoardMem {
    int num = 9;

    private IGameBoard makeAGameBoard(int numRows, int numColumns, int numToWin) {
        return new GameBoard(numRows, numColumns, numToWin);
    }

    private String toString(char[][] gb, int numRows, int numColumns) {
        String sep = "|";

        for (int i = 0; i < numColumns; i++) {
            if (i <= num) {
                sep = sep.concat(" ");
            }
            sep = sep.concat(Integer.toString(i));
            sep = sep.concat("|");
        }
        sep = sep.concat("\n");

        for (int i = (numRows - 1); i >= 0; i--) {
            for (int j = 0; j < numColumns; j++) {
                BoardPosition pos = new BoardPosition(i, j);
                char c = gb[i][j];
                sep = sep.concat("|");
                sep = sep.concat(Character.toString(c));
                sep = sep.concat(" ");
            }
            sep = sep.concat("|");
            sep = sep.concat("\n");
        }
        return sep;
    }

    @Test
    public void testContructorRoutine() {
        int numRows = 52;
        int numColumns = 64;
        int numToWin = 10;

        IGameBoard gb = makeAGameBoard(numRows, numColumns, numToWin);

        char[][] expectedGrid = new char[numRows][numColumns];

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                expectedGrid[i][j] = ' ';
            }
        }

        assertEquals(toString(expectedGrid, numRows, numColumns), gb.toString());
    }

    @Test
    public void testConstructorMin() {
        int numRows = 3;
        int numColumns = 3;
        int numToWin = 3;

        IGameBoard gb = makeAGameBoard(numRows, numColumns, numToWin);

        char[][] expectedGrid = new char[numRows][numColumns];

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                expectedGrid[i][j] = ' ';
            }
        }
        assertEquals(toString(expectedGrid, numRows, numColumns), gb.toString());
    }

    @Test
    public void testConstructorMax() {
        int numRows = 100;
        int numColumns = 100;
        int numToWin = 25;

        IGameBoard gb = makeAGameBoard(numRows, numColumns, numToWin);

        char[][] expectedGrid = new char[numRows][numColumns];

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                expectedGrid[i][j] = ' ';
            }
        }
        assertEquals(toString(expectedGrid, numRows, numColumns), gb.toString());
    }

    @Test
    public void checkIfFreeRoutine() {
        int numRows = 3;
        int numColumns = 3;
        int numToWin = 3;
        int c = 1;
        IGameBoard gb = makeAGameBoard(numRows, numColumns, numToWin);
        gb.placeToken('X', c);
        gb.placeToken('O', c);

        boolean actualBL = gb.checkIfFree(c);
        boolean expectedBL = true;
        assertEquals(expectedBL, actualBL);
    }


    @Test
    public void testcheckIfFreeSpaceTaken() {
        int numRows = 3;
        int numColumns = 3;
        int numToWin = 3;
        int c = 1;
        IGameBoard gb = makeAGameBoard(numRows, numColumns, numToWin);
        gb.placeToken('X', c);
        boolean actualBL = gb.checkIfFree(c);
        for (int i = 0; i < numRows; i++) {
            BoardPosition val = new BoardPosition(i, c);
            if (gb.whatsAtPos(val) == 'X') {
                assertTrue(actualBL);
            }
        }
        boolean expectedBL = true;
        assertEquals(expectedBL, actualBL);
    }

    @Test
    public void testCheckIfFreeValid() {
        int numRows = 3;
        int numColumns = 3;
        int numToWin = 3;
        int c = 1;
        IGameBoard gb = makeAGameBoard(numRows, numColumns, numToWin);
        gb.placeToken('X', 1);
        boolean actualBL = gb.checkIfFree(c);
        char[][] expectedGrid = new char[numRows][numColumns];
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                if (i == 1 && j == 1) expectedGrid[i][j] = 'X';
                BoardPosition val = new BoardPosition(i, c);
                if (gb.whatsAtPos(val) == 'X') {
                    assertTrue(actualBL);
                }
                else expectedGrid[i][j] = ' ';
            }
        }
        boolean expectedBL = true;
        assertEquals(expectedBL, actualBL);

    }

    @Test
    public void testCheckHorizWinMiddle() {
        int numRows = 5;
        int numColumns = 5;
        int numToWin = 4;

        IGameBoard gb = makeAGameBoard(numRows, numColumns, numToWin);
        for (int j = 0; j <= 3; j++) {
            gb.placeToken('X', j);
        }
        for (int j = 0; j <= 2; j++) {
            gb.placeToken('O', j);
        }
        gb.placeToken('X', 3);
        gb.placeToken('O', 4);

        char[][] expectedGrid = new char[numRows][numColumns];

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                if (i == 0 && j == 0) expectedGrid[i][j] = 'X';
                else if (i == 0 && j == 1) expectedGrid[i][j] = 'X';
                else if (i == 0 && j == 2) expectedGrid[i][j] = 'X';
                else if (i == 0 && j == 3) expectedGrid[i][j] = 'X';
                else if (i == 1 && j == 0) expectedGrid[i][j] = 'O';
                else if (i == 1 && j == 1) expectedGrid[i][j] = 'O';
                else if (i == 1 && j == 2) expectedGrid[i][j] = 'O';
                else if (i == 1 && j == 3) expectedGrid[i][j] = 'X';
                else if (i == 0 && j == 4) expectedGrid[i][j] = 'O';
                else expectedGrid[i][j] = ' ';
            }
        }

        assertTrue(gb.checkHorizWin(new BoardPosition(0, 3), 'X'));
        assertEquals(toString(expectedGrid, numRows, numColumns), gb.toString());
    }

    @Test
    public void testCheckHorizWinRight() {
        int numRows = 4;
        int numColumns = 4;
        int numToWin = 3;

        IGameBoard gb = makeAGameBoard(numRows, numColumns, numToWin);
        for (int j = 0; j <= 2; j++) {
            gb.placeToken('X', j);
        }
        for (int j = 1; j <= 2; j++) {
            gb.placeToken('O', j);
        }

        char[][] expectedGrid = new char[numRows][numColumns];

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                if (i == 1 && j == 2) expectedGrid[i][j] = 'O';
                else if (i == 1 && j == 1) expectedGrid[i][j] = 'O';
                else if (i == 0 && j == 0) expectedGrid[i][j] = 'X';
                else if (i == 0 && j == 1) expectedGrid[i][j] = 'X';
                else if (i == 0 && j == 2) expectedGrid[i][j] = 'X';
                else expectedGrid[i][j] = ' ';
            }
        }

        assertTrue(gb.checkHorizWin(new BoardPosition(0, 2), 'X'));
        assertEquals(toString(expectedGrid, numRows, numColumns), gb.toString());

    }

    @Test
    public void testCheckHorizontalWinNoWin() {
        int numRows = 3;
        int numColumns = 3;
        int numToWin = 3;

        IGameBoard gb = makeAGameBoard(numRows, numColumns, numToWin);
        gb.placeToken('X', 1);

        char [][] expectedGrid = new char[numRows][numColumns];

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                if (i == 0 && j == 1) expectedGrid[i][j] = 'X';
                else expectedGrid[i][j] = ' ';
            }
        }

        assertFalse(gb.checkHorizWin(new BoardPosition(1, 1), 'X'));
        assertEquals(toString(expectedGrid, numRows, numColumns), gb.toString());
    }

    @Test
    public void testCheckHorizontalWinNotEnough() {
        int numRows = 5;
        int numColumns = 5;
        int numToWin = 4;

        IGameBoard gb = makeAGameBoard(numRows, numColumns, numToWin);
        gb.placeToken('O', 0);
        gb.placeToken('X', 1);
        gb.placeToken('X', 2);
        gb.placeToken('X', 3);
        gb.placeToken('O', 4);

        char [][] expectedGrid = new char[numRows][numColumns];

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                if (i == 0 && j == 0) expectedGrid[i][j] = 'O';
                else if (i == 0 && j == 1) expectedGrid[i][j] = 'X';
                else if (i == 0 && j == 2) expectedGrid[i][j] = 'X';
                else if (i == 0 && j == 3) expectedGrid[i][j] = 'X';
                else if (i == 0 && j == 4) expectedGrid[i][j] = 'O';
                else expectedGrid[i][j] = ' ';
            }
        }

        assertFalse(gb.checkHorizWin(new BoardPosition(0, 3), 'X'));
        assertEquals(toString(expectedGrid, numRows, numColumns), gb.toString());
    }

    @Test
    public void testCheckVerticalWinMiddle() {
        int numRows = 5;
        int numColumns = 5;
        int numToWin = 4;

        IGameBoard gb = makeAGameBoard(numRows, numColumns, numToWin);
        for (int i = 0; i <= 3; i++) {
            gb.placeToken('X', 2);
        }
        for (int i = 0; i <= 2; i++) {
            gb.placeToken('O', 3);
        }
        gb.placeToken('X', 3);
        gb.placeToken('O', 3);

        char [][] expectedGrid = new char[numRows][numColumns];

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                if (i == 0 && j == 2) expectedGrid[i][j] = 'X';
                else if (i == 1 && j == 2) expectedGrid[i][j] = 'X';
                else if (i == 2 && j == 2) expectedGrid[i][j] = 'X';
                else if (i == 3 && j == 2) expectedGrid[i][j] = 'X';
                else if (i == 0 && j == 3) expectedGrid[i][j] = 'O';
                else if (i == 1 && j == 3) expectedGrid[i][j] = 'O';
                else if (i == 2 && j == 3) expectedGrid[i][j] = 'O';
                else if (i == 3 && j == 3) expectedGrid[i][j] = 'X';
                else if (i == 4 && j == 3) expectedGrid[i][j] = 'O';
                else expectedGrid[i][j] = ' ';
            }
        }

        assertTrue(gb.checkVertWin(new BoardPosition(3, 2), 'X'));
        assertEquals(toString(expectedGrid, numRows, numColumns), gb.toString());
    }

    @Test
    public void testCheckVerticalWinLeft() {
        int numRows = 4;
        int numColumns = 4;
        int numToWin = 3;

        IGameBoard gb = makeAGameBoard(numRows, numColumns, numToWin);
        gb.placeToken('X', 0);
        gb.placeToken('O', 1);
        gb.placeToken('X', 0);
        gb.placeToken('O', 1);
        gb.placeToken('X', 0);




        char [][] expectedGrid = new char[numRows][numColumns];

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                if (i == 0 && j == 0) expectedGrid[i][j] = 'X';
                else if (i == 1 && j == 0) expectedGrid[i][j] = 'X';
                else if (i == 2 && j == 0) expectedGrid[i][j] = 'X';
                else if (i == 0 && j == 1) expectedGrid[i][j] = 'O';
                else if (i == 1 && j == 1) expectedGrid[i][j] = 'O';
                else expectedGrid[i][j] = ' ';
            }
        }

        assertTrue(gb.checkVertWin(new BoardPosition(2, 0), 'X'));
        assertEquals(toString(expectedGrid, numRows, numColumns), gb.toString());
    }

    @Test
    public void testCheckVerticalWinNoWin() {
        int numRows = 3;
        int numColumns = 3;
        int numToWin = 3;

        IGameBoard gb = makeAGameBoard(numRows, numColumns, numToWin);
        gb.placeToken('X', 1);

        char [][] expectedGrid = new char[numRows][numColumns];

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                if (i == 0 && j == 1) expectedGrid[i][j] = 'X';
                else expectedGrid[i][j] = ' ';
            }
        }

        assertFalse(gb.checkVertWin(new BoardPosition(1, 1), 'X'));
        assertEquals(toString(expectedGrid, numRows, numColumns), gb.toString());
    }

    @Test
    public void testCheckVerticalWinNotEnough() {
        int numRows = 5;
        int numColumns = 5;
        int numToWin = 4;

        IGameBoard gb = makeAGameBoard(numRows, numColumns, numToWin);
        gb.placeToken('O', 2);
        for (int i = 1; i <=3; i++) {
            gb.placeToken('X', 2);
        }
        gb.placeToken('O', 2);

        char [][] expectedGrid = new char[numRows][numColumns];

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                if (i == 0 && j == 2) expectedGrid[i][j] = 'O';
                else if (i == 1 && j == 2) expectedGrid[i][j] = 'X';
                else if (i == 2 && j == 2) expectedGrid[i][j] = 'X';
                else if (i == 3 && j == 2) expectedGrid[i][j] = 'X';
                else if (i == 4 && j == 2) expectedGrid[i][j] = 'O';
                else expectedGrid[i][j] = ' ';
            }
        }

        assertFalse(gb.checkVertWin(new BoardPosition(1, 2), 'X'));
        assertEquals(toString(expectedGrid, numRows, numColumns), gb.toString());
    }

    @Test
    public void testCheckDiagonalTopRight() {
        int numRows = 5;
        int numColumns = 5;
        int numToWin = 4;
        char [][] x = new char[5][5];
        IGameBoard gb = makeAGameBoard(numRows, numColumns, numToWin);

        gb.placeToken('X', 0);
        gb.placeToken('X', 1);
        gb.placeToken('X', 1);
        gb.placeToken('O', 2);
        gb.placeToken('O', 2);
        gb.placeToken('X', 2);
        gb.placeToken('O', 3);
        gb.placeToken('O', 3);
        gb.placeToken('O', 3);
        gb.placeToken('X', 3);
        gb.placeToken('O', 4);
        gb.placeToken('O', 4);

        char [][] expectedGrid = new char[numRows][numColumns];

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                if (i == 0 && j == 0) expectedGrid[i][j] = 'X';
                else if(i == 0 && j == 1) expectedGrid[i][j] = 'X';
                else if(i == 1 && j == 1) expectedGrid[i][j] = 'X';
                else if(i == 0 && j == 2) expectedGrid[i][j] = 'O';
                else if(i == 1 && j == 2) expectedGrid[i][j] = 'O';
                else if(i == 1 && j == 3) expectedGrid[i][j] = 'O';
                else if(i == 2 && j == 3) expectedGrid[i][j] = 'O';
                else if (i == 2 && j == 2) expectedGrid[i][j] = 'X';
                else if (i == 3 && j == 3) expectedGrid[i][j] = 'X';
                else if (i == 0 && j == 3) expectedGrid[i][j] = 'O';
                else if (i == 0 && j == 4) expectedGrid[i][j] = 'O';
                else if (i == 1 && j == 4) expectedGrid[i][j] = 'O';
                else expectedGrid[i][j] = ' ';
            }
        }
        assertTrue(gb.checkDiagWin(new BoardPosition(3, 3), 'X'));
        assertEquals(toString(expectedGrid, numRows, numColumns), gb.toString());
    }

    @Test
    public void testCheckDiagonalWinTopLeft() {
        int numRows = 5;
        int numColumns = 5;
        int numToWin = 4;

        IGameBoard gb = makeAGameBoard(numRows, numColumns, numToWin);

        gb.placeToken('X',4);
        gb.placeToken('O', 3);
        gb.placeToken('X', 3);
        gb.placeToken('O', 2);
        gb.placeToken('X', 2);
        gb.placeToken('X', 2);
        gb.placeToken('O', 1);
        gb.placeToken('O', 1);
        gb.placeToken('O', 1);
        gb.placeToken('X', 1);

        char [][] expectedGrid = new char[numRows][numColumns];

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                if (i == 0 && j == 1) expectedGrid[i][j] = 'O';
                else if (i == 1 && j == 1) expectedGrid[i][j] = 'O';
                else if (i == 2 && j == 1) expectedGrid[i][j] = 'O';
                else if (i == 3 && j == 1) expectedGrid[i][j] = 'X';
                else if (i == 0 && j == 2) expectedGrid[i][j] = 'O';
                else if (i == 1 && j == 2) expectedGrid[i][j] = 'X';
                else if (i == 2 && j == 2) expectedGrid[i][j] = 'X';
                else if (i == 0 && j == 3) expectedGrid[i][j] = 'O';
                else if (i == 1 && j == 3) expectedGrid[i][j] = 'X';
                else if (i == 0 && j == 4) expectedGrid[i][j] = 'X';
                else expectedGrid[i][j] = ' ';
            }
        }
        assertTrue(gb.checkDiagWin(new BoardPosition(3, 1), 'X'));
        assertEquals(toString(expectedGrid, numRows, numColumns), gb.toString());
    }

    @Test
    public void testCheckDiagonalWinNoWin() {
        int numRows = 5;
        int numColumns = 5;
        int numToWin = 4;

        IGameBoard gb = makeAGameBoard(numRows, numColumns, numToWin);

        gb.placeToken('X',4);
        gb.placeToken('O', 3);
        gb.placeToken('X', 3);
        gb.placeToken('O', 2);
        gb.placeToken('X', 2);
        gb.placeToken('X', 2);
        gb.placeToken('O', 1);
        gb.placeToken('O', 1);
        gb.placeToken('O', 1);
        gb.placeToken('O', 1);

        char [][] expectedGrid = new char[numRows][numColumns];

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                if (i == 0 && j == 1) expectedGrid[i][j] = 'O';
                else if (i == 1 && j == 1) expectedGrid[i][j] = 'O';
                else if (i == 2 && j == 1) expectedGrid[i][j] = 'O';
                else if (i == 3 && j == 1) expectedGrid[i][j] = 'O';
                else if (i == 0 && j == 2) expectedGrid[i][j] = 'O';
                else if (i == 1 && j == 2) expectedGrid[i][j] = 'X';
                else if (i == 2 && j == 2) expectedGrid[i][j] = 'X';
                else if (i == 0 && j == 3) expectedGrid[i][j] = 'O';
                else if (i == 1 && j == 3) expectedGrid[i][j] = 'X';
                else if (i == 0 && j == 4) expectedGrid[i][j] = 'X';
                else expectedGrid[i][j] = ' ';
            }
        }
        assertFalse(gb.checkDiagWin(new BoardPosition(3, 1), 'X'));
        assertEquals(toString(expectedGrid, numRows, numColumns), gb.toString());
    }

    @Test
    public void testCheckDiagonalWinMinBoardLeft() {
        int numRows = 3;
        int numColumns = 3;
        int numToWin = 3;

        IGameBoard gb = makeAGameBoard(numRows, numColumns, numToWin);



        gb.placeToken('X', 2);
        gb.placeToken('X', 1);
        gb.placeToken('X', 1);
        gb.placeToken('O', 0);
        gb.placeToken('O', 0);
        gb.placeToken('X', 0);


        char [][] expectedGrid = new char[numRows][numColumns];

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                if (i == 0 && j == 2) expectedGrid[i][j] = 'X';
                else if (i == 0 && j == 1) expectedGrid[i][j] = 'X';
                else if (i == 1 && j == 1) expectedGrid[i][j] = 'X';
                else if (i == 0 && j == 0) expectedGrid[i][j] = 'O';
                else if (i == 1 && j == 0) expectedGrid[i][j] = 'O';
                else if (i == 2 && j == 0) expectedGrid[i][j] = 'X';
                else expectedGrid[i][j] = ' ';
            }
        }
        assertTrue(gb.checkDiagWin(new BoardPosition(2, 0), 'X'));
        assertEquals(toString(expectedGrid, numRows, numColumns), gb.toString());
    }

    @Test
    public void testCheckDiagonalWinMinBoardRight() {
        int numRows = 3;
        int numColumns = 3;
        int numToWin = 3;

        IGameBoard gb = makeAGameBoard(numRows, numColumns, numToWin);



        gb.placeToken('X', 0);
        gb.placeToken('X', 1);
        gb.placeToken('X', 1);
        gb.placeToken('O', 2);
        gb.placeToken('O', 2);
        gb.placeToken('X', 2);


        char [][] expectedGrid = new char[numRows][numColumns];

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                if (i == 0 && j == 0) expectedGrid[i][j] = 'X';
                else if (i == 0 && j == 1) expectedGrid[i][j] = 'X';
                else if (i == 1 && j == 1) expectedGrid[i][j] = 'X';
                else if (i == 0 && j == 2) expectedGrid[i][j] = 'O';
                else if (i == 1 && j == 2) expectedGrid[i][j] = 'O';
                else if (i == 2 && j == 2) expectedGrid[i][j] = 'X';
                else expectedGrid[i][j] = ' ';
            }
        }
        assertTrue(gb.checkDiagWin(new BoardPosition(2, 2), 'X'));
        assertEquals(toString(expectedGrid, numRows, numColumns), gb.toString());
    }

    @Test
    public void testCheckDiagWinXStopO(){
        int numRows = 5;
        int numColumns = 5;
        int numToWin = 4;

        IGameBoard gb = makeAGameBoard(numRows, numColumns, numToWin);

        gb.placeToken('O',4);
        gb.placeToken('X', 3);
        gb.placeToken('O', 3);
        gb.placeToken('X', 2);
        gb.placeToken('O', 2);
        gb.placeToken('O', 2);
        gb.placeToken('O', 1);
        gb.placeToken('X', 1);
        gb.placeToken('X', 1);
        gb.placeToken('X', 1);

        char [][] expectedGrid = new char[numRows][numColumns];

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                if (i == 0 && j == 1) expectedGrid[i][j] = 'O';
                else if (i == 1 && j == 1) expectedGrid[i][j] = 'X';
                else if (i == 2 && j == 1) expectedGrid[i][j] = 'X';
                else if (i == 3 && j == 1) expectedGrid[i][j] = 'X';
                else if (i == 0 && j == 2) expectedGrid[i][j] = 'X';
                else if (i == 1 && j == 2) expectedGrid[i][j] = 'O';
                else if (i == 2 && j == 2) expectedGrid[i][j] = 'O';
                else if (i == 0 && j == 3) expectedGrid[i][j] = 'X';
                else if (i == 1 && j == 3) expectedGrid[i][j] = 'O';
                else if (i == 0 && j == 4) expectedGrid[i][j] = 'O';
                else expectedGrid[i][j] = ' ';
            }
        }
        assertFalse(gb.checkDiagWin(new BoardPosition(3, 1), 'O'));
        assertEquals(toString(expectedGrid, numRows, numColumns), gb.toString());
    }

    @Test
    public void testCheckDiagWinTwoDiagonals(){
        int numRows = 5;
        int numColumns = 5;
        int numToWin = 3;

        IGameBoard gb = makeAGameBoard(numRows, numColumns, numToWin);

        gb.placeToken('O',4);
        gb.placeToken('X', 0);
        gb.placeToken('X', 1);
        gb.placeToken('X', 1);
        gb.placeToken('O', 3);
        gb.placeToken('O', 3);
        gb.placeToken('X', 2);
        gb.placeToken('O', 2);
        gb.placeToken('X', 2);


        char [][] expectedGrid = new char[numRows][numColumns];

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                if (i == 0 && j == 4) expectedGrid[i][j] = 'O';
                else if (i == 0 && j == 0) expectedGrid[i][j] = 'X';
                else if (i == 0 && j == 1) expectedGrid[i][j] = 'X';
                else if (i == 1 && j == 1) expectedGrid[i][j] = 'X';
                else if (i == 0 && j == 3) expectedGrid[i][j] = 'O';
                else if (i == 1 && j == 3) expectedGrid[i][j] = 'O';
                else if (i == 0 && j == 2) expectedGrid[i][j] = 'X';
                else if (i == 1 && j == 2) expectedGrid[i][j] = 'O';
                else if (i == 2 && j == 2) expectedGrid[i][j] = 'X';
                else expectedGrid[i][j] = ' ';
            }
        }
        assertTrue(gb.checkDiagWin(new BoardPosition(2, 2), 'X'));
        assertEquals(toString(expectedGrid, numRows, numColumns), gb.toString());
    }

    @Test
    public void testCheckForTieFullGrid() {
        int numRows = 3;
        int numColumns = 3;
        int numToWin = 3;

        IGameBoard gb = makeAGameBoard(numRows, numColumns, numToWin);
        gb.placeToken('X', 0);
        gb.placeToken('O', 1);
        gb.placeToken('O', 2);
        gb.placeToken('O', 0);
        gb.placeToken('X', 1);
        gb.placeToken('X', 2);
        gb.placeToken('X', 0);
        gb.placeToken('X', 1);
        gb.placeToken('O', 2);

        char [][] expectedGrid = new char[numRows][numColumns];

        expectedGrid[0][0] = 'X';
        expectedGrid[0][1] = 'O';
        expectedGrid[0][2] = 'O';
        expectedGrid[1][0] = 'O';
        expectedGrid[1][1] = 'X';
        expectedGrid[1][2] = 'X';
        expectedGrid[2][0] = 'X';
        expectedGrid[2][1] = 'X';
        expectedGrid[2][2] = 'O';

        assertTrue(gb.checkTie());
        assertEquals(toString(expectedGrid, numRows, numColumns), gb.toString());
    }

    @Test
    public void testCheckForTieNoTie() {
        int numRows = 3;
        int numColumns = 3;
        int numToWin = 3;

        IGameBoard gb = makeAGameBoard(numRows, numColumns, numToWin);
        gb.placeToken('X', 1);

        char [][] expectedGrid = new char[numRows][numColumns];

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                if (i == 0 && j == 1) expectedGrid[i][j] = 'X';
                else expectedGrid[i][j] = ' ';
            }
        }

        assertFalse(gb.checkTie());
        assertEquals(toString(expectedGrid, numRows, numColumns), gb.toString());
    }

    @Test
    public void testCheckForTieAlmostFull() {
        int numRows = 3;
        int numColumns = 3;
        int numToWin = 3;

        IGameBoard gb = makeAGameBoard(numRows, numColumns, numToWin);
        gb.placeToken('O', 0);
        gb.placeToken('X', 1);
        gb.placeToken('O', 2);
        gb.placeToken('O', 0);
        gb.placeToken('X', 1);
        gb.placeToken('X', 2);
        gb.placeToken('O', 1);
        gb.placeToken('X', 2);

        char [][] expectedGrid = new char[numRows][numColumns];

        expectedGrid[0][0] = 'O';
        expectedGrid[0][1] = 'X';
        expectedGrid[0][2] = 'O';
        expectedGrid[1][0] = 'O';
        expectedGrid[1][1] = 'X';
        expectedGrid[1][2] = 'X';
        expectedGrid[2][0] = ' ';
        expectedGrid[2][1] = 'O';
        expectedGrid[2][2] = 'X';

        assertFalse(gb.checkTie());
        assertEquals(toString(expectedGrid, numRows, numColumns), gb.toString());
    }

    @Test
    public void testCheckForDrawNoDrawRoutine() {
        int numRows = 3;
        int numColumns = 3;
        int numToWin = 3;

        IGameBoard gb = makeAGameBoard(numRows, numColumns, numToWin);
        gb.placeToken('X', 0);
        gb.placeToken('O', 1);
        gb.placeToken('O', 0);
        gb.placeToken('X', 1);
        gb.placeToken('X', 2);


        char [][] expectedGrid = new char[numRows][numColumns];

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                if (i == 0 && j == 0) expectedGrid[i][j] = 'X';
                else if (i == 0 && j == 1) expectedGrid[i][j] = 'O';
                else if (i == 1 && j == 0) expectedGrid[i][j] = 'O';
                else if (i == 1 && j == 1) expectedGrid[i][j] = 'X';
                else if (i == 0 && j == 2) expectedGrid[i][j] = 'X';
                else expectedGrid[i][j] = ' ';
            }
        }

        assertFalse(gb.checkTie());
        assertEquals(toString(expectedGrid, numRows, numColumns), gb.toString());
    }

    @Test
    public void testWhatsAtPosMinPosition() {
        int numRows = 3;
        int numColumns = 3;
        int numToWin = 3;

        IGameBoard gb = makeAGameBoard(numRows, numColumns, numToWin);
        gb.placeToken('X' ,0);

        char [][] expectedGrid = new char[numRows][numColumns];

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                if (i == 0 && j == 0) expectedGrid[i][j] = 'X';
                else expectedGrid[i][j] = ' ';
            }
        }

        assertTrue(gb.whatsAtPos(new BoardPosition(0, 0)) == 'X');
        assertEquals(toString(expectedGrid, numRows, numColumns), gb.toString());
    }

    @Test
    public void testWhatsAtPosRightSide() {
        int numRows = 3;
        int numColumns = 3;
        int numToWin = 3;

        IGameBoard gb = makeAGameBoard(numRows, numColumns, numToWin);
        gb.placeToken('X', 2);

        char [][] expectedGrid = new char[numRows][numColumns];

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                if (i == 0 && j == 2) expectedGrid[i][j] = 'X';
                else expectedGrid[i][j] = ' ';
            }
        }

        assertTrue(gb.whatsAtPos(new BoardPosition(0, 2)) == 'X');
        assertEquals(toString(expectedGrid, numRows, numColumns), gb.toString());
    }

    @Test
    public void testWhatsAtPosDifferentChar() {
        int numRows = 3;
        int numColumns = 3;
        int numToWin = 3;

        IGameBoard gb = makeAGameBoard(numRows, numColumns, numToWin);
        gb.placeToken('X', 1);

        char [][] expectedGrid = new char[numRows][numColumns];

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                if (i == 0 && j == 1) expectedGrid[i][j] = 'X';
                else expectedGrid[i][j] = ' ';
            }
        }

        assertFalse(gb.whatsAtPos(new BoardPosition(0, 1)) == 'O');
        assertEquals(toString(expectedGrid, numRows, numColumns), gb.toString());
    }

    @Test
    public void testWhatsAtPosRoutineFullBoard(){
        int numRows = 3;
        int numColumns = 3;
        int numToWin = 3;

        IGameBoard gb = makeAGameBoard(numRows, numColumns, numToWin);
        gb.placeToken('X', 0);
        gb.placeToken('O', 0);
        gb.placeToken('X', 0);
        gb.placeToken('O', 1);
        gb.placeToken('O', 1);
        gb.placeToken('X', 1);
        gb.placeToken('X', 2);
        gb.placeToken('X', 2);
        gb.placeToken('O', 2);



        char [][] expectedGrid = new char[numRows][numColumns];

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                if (i == 0 && j == 0) expectedGrid[i][j] = 'X';
                else if(i == 1 && j == 0) expectedGrid[i][j] = 'O';
                else if(i == 2 && j == 0) expectedGrid[i][j] = 'X';
                else if(i == 0 && j == 1) expectedGrid[i][j] = 'O';
                else if(i == 1 && j == 1) expectedGrid[i][j] = 'O';
                else if(i == 2 && j == 1) expectedGrid[i][j] = 'X';
                else if(i == 0 && j == 2) expectedGrid[i][j] = 'X';
                else if(i == 1 && j == 2) expectedGrid[i][j] = 'X';
                else if(i == 2 && j == 2) expectedGrid[i][j] = 'O';
                else expectedGrid[i][j] = ' ';
            }
        }

        assertTrue(gb.whatsAtPos(new BoardPosition(1, 1)) == 'O');
        assertEquals(toString(expectedGrid, numRows, numColumns), gb.toString());
    }

    @Test
    public void testWhatsAtPosBlankChar(){
        int numRows = 3;
        int numColumns = 3;
        int numToWin = 3;

        IGameBoard gb = makeAGameBoard(numRows, numColumns, numToWin);
        gb.placeToken('X', 0);

        char [][] expectedGrid = new char[numRows][numColumns];

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                if (i == 0 && j == 0) expectedGrid[i][j] = 'X';
                else expectedGrid[i][j] = ' ';
            }
        }

        assertFalse(gb.whatsAtPos(new BoardPosition(0, 0)) == ' ');
        assertEquals(toString(expectedGrid, numRows, numColumns), gb.toString());
    }

    @Test
    public void testIsPlayerAtPosFalseRoutine() {
        int numRows = 3;
        int numColumns = 3;
        int numToWin = 3;

        IGameBoard gb = makeAGameBoard(numRows, numColumns, numToWin);
        gb.placeToken('X', 1);

        char [][] expectedGrid = new char[numRows][numColumns];

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                if (i == 0 && j == 1) expectedGrid[i][j] = 'X';
                else expectedGrid[i][j] = ' ';
            }
        }

        assertFalse(gb.isPlayerAtPos(new BoardPosition(0, 1), 'O'));
        assertEquals(toString(expectedGrid, numRows, numColumns), gb.toString());
    }

    @Test
    public void testIsPlayerAtPosColumnMin() {
        int numRows = 3;
        int numColumns = 3;
        int numToWin = 3;

        IGameBoard gb = makeAGameBoard(numRows, numColumns, numToWin);
        gb.placeToken('X', 0);
        gb.placeToken('X', 0);

        char [][] expectedGrid = new char[numRows][numColumns];

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                if (i == 1 && j == 0) expectedGrid[i][j] = 'X';
                else if(i == 0 && j == 0) expectedGrid[i][j] = 'X';
                else expectedGrid[i][j] = ' ';
            }
        }

        assertTrue(gb.isPlayerAtPos(new BoardPosition(1, 0), 'X'));
        assertEquals(toString(expectedGrid, numRows, numColumns), gb.toString());
    }

    @Test
    public void testIsPlayerAtPosRowMin() {
        int numRows = 3;
        int numColumns = 3;
        int numToWin = 3;

        IGameBoard gb = makeAGameBoard(numRows, numColumns, numToWin);
        gb.placeToken('X', 0);

        char [][] expectedGrid = new char[numRows][numColumns];

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                if (i == 0 && j == 0) expectedGrid[i][j] = 'X';
                else expectedGrid[i][j] = ' ';
            }
        }

        assertTrue(gb.isPlayerAtPos(new BoardPosition(0, 0), 'X'));
        assertEquals(toString(expectedGrid, numRows, numColumns), gb.toString());
    }

    @Test
    public void testIsPlayerAtPosColumnMax() {
        int numRows = 3;
        int numColumns = 3;
        int numToWin = 3;

        IGameBoard gb = makeAGameBoard(numRows, numColumns, numToWin);
        gb.placeToken('X', 2);

        char [][] expectedGrid = new char[numRows][numColumns];

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                if (i == 0 && j == 2) expectedGrid[i][j] = 'X';
                else expectedGrid[i][j] = ' ';
            }
        }

        assertTrue(gb.isPlayerAtPos(new BoardPosition(0, 2), 'X'));
        assertEquals(toString(expectedGrid, numRows, numColumns), gb.toString());
    }

    @Test
    public void testIsPlayerAtPosRowMax(){
        int numRows = 3;
        int numColumns = 3;
        int numToWin = 3;

        IGameBoard gb = makeAGameBoard(numRows, numColumns, numToWin);
        gb.placeToken('X', 0);
        gb.placeToken('O', 0);
        gb.placeToken('X', 0);

        char [][] expectedGrid = new char[numRows][numColumns];

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                if (i == 0 && j == 0) expectedGrid[i][j] = 'X';
                else if(i == 1 && j == 0) expectedGrid[i][j] = 'O';
                else if(i == 2 && j == 0) expectedGrid[i][j] = 'X';
                else expectedGrid[i][j] = ' ';
            }
        }

        assertTrue(gb.isPlayerAtPos(new BoardPosition(2, 0), 'X'));
        assertEquals(toString(expectedGrid, numRows, numColumns), gb.toString());
    }

    @Test
    public void testPlaceTokenRoutine() {
        int numRows = 3;
        int numColumns = 3;
        int numToWin = 3;

        IGameBoard gb = makeAGameBoard(numRows, numColumns, numToWin);
        gb.placeToken('O', 0);
        gb.placeToken('X', 1);
        gb.placeToken('A', 1);

        char [][] expectedGrid = new char[numRows][numColumns];

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                if (i == 0 && j == 0) expectedGrid[i][j] = 'O';
                else if (i == 0 && j == 1) expectedGrid[i][j] = 'X';
                else if (i == 1 && j == 1) expectedGrid[i][j] = 'A';
                else expectedGrid[i][j] = ' ';
            }
        }

        assertEquals(toString(expectedGrid, numRows, numColumns), gb.toString());
    }

    @Test
    public void testPlaceTokenRowMin() {
        int numRows = 3;
        int numColumns = 3;
        int numToWin = 3;

        IGameBoard gb = makeAGameBoard(numRows, numColumns, numToWin);
        gb.placeToken('X', 1);

        char [][] expectedGrid = new char[numRows][numColumns];

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                if (i == 0 && j == 1) expectedGrid[i][j] = 'X';
                else expectedGrid[i][j] = ' ';
            }
        }
        assertEquals(toString(expectedGrid, numRows, numColumns), gb.toString());
    }

    @Test
    public void testPlaceTokenColumnMin() {
        int numRows = 3;
        int numColumns = 3;
        int numToWin = 3;

        IGameBoard gb = makeAGameBoard(numRows, numColumns, numToWin);
        gb.placeToken('X', 0);

        char [][] expectedGrid = new char[numRows][numColumns];

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                if (i == 0 && j == 0) expectedGrid[i][j] = 'X';
                else expectedGrid[i][j] = ' ';
            }
        }
        assertEquals(toString(expectedGrid, numRows, numColumns), gb.toString());
    }

    @Test
    public void testPlaceMarkerColumnMax() {
        int numRows = 3;
        int numColumns = 3;
        int numToWin = 3;

        IGameBoard gb = makeAGameBoard(numRows, numColumns, numToWin);
        gb.placeToken('X', 2);
        gb.placeToken('O', 2);
        gb.placeToken('X', 2);

        char [][] expectedGrid = new char[numRows][numColumns];

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                if (i == 0 && j == 2) expectedGrid[i][j] = 'X';
                else if(i == 1 && j == 2) expectedGrid[i][j] = 'O';
                else if(i == 2 && j == 2) expectedGrid[i][j] = 'X';
                else expectedGrid[i][j] = ' ';
            }
        }
        assertEquals(toString(expectedGrid, numRows, numColumns), gb.toString());
    }

    @Test
    public void testPlaceTokenRowMax() {
        int numRows = 3;
        int numColumns = 3;
        int numToWin = 3;

        IGameBoard gb = makeAGameBoard(numRows, numColumns, numToWin);
        gb.placeToken('X', 1);
        gb.placeToken('O', 1);
        gb.placeToken('X', 1);


        char [][] expectedGrid = new char[numRows][numColumns];

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                if (i == 0 && j == 1) expectedGrid[i][j] = 'X';
                else if (i == 1 && j == 1) expectedGrid[i][j] = 'O';
                else if (i == 2 && j == 1) expectedGrid[i][j] = 'X';
                else expectedGrid[i][j] = ' ';
            }
        }
        assertEquals(toString(expectedGrid, numRows, numColumns), gb.toString());
    }
}

