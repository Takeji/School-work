/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sudokusolver;

/**
 
 * This program will solve one solution of a sudoku puzzle.
 * Must assign sudoku puzzle in the given 2D array "puzzle"
 * @author Jake
 */
public class SudokuSolver {

    static int puzzle[][];
    
    public static void main(String[] args) {
        
        //creates a blank sudoku puzzle, 0 = nothing;
        puzzle = new int[9][9];
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                puzzle[row][col] = 0;
            }
        }
        //Assigning sudoku puzzle
        puzzle[0][0] = 7;
        puzzle[0][1] = 9;
        puzzle[0][6] = 3;
        puzzle[1][5] = 6;
        puzzle[1][6] = 9;
        puzzle[2][4] = 3;
        puzzle[2][7] = 7;
        puzzle[2][8] = 6;
        puzzle[3][4] = 5;
        puzzle[3][8] = 2;
        puzzle[4][2] = 5;
        puzzle[4][3] = 4;
        puzzle[4][4] = 1;
        puzzle[4][5] = 8;
        puzzle[4][6] = 7;
        puzzle[5][0] = 4;
        puzzle[5][3] = 7;
        puzzle[6][0] = 6;
        puzzle[6][1] = 1;
        puzzle[6][4] = 9;
        puzzle[6][7] = 8;
        puzzle[7][2] = 2;
        puzzle[7][3] = 3;
        puzzle[8][2] = 9;
        puzzle[8][6] = 5;
        puzzle[8][7] = 4;
        try {
            solve(0, 0);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        printPuzzle();
    }
    
    
    static public void printPuzzle(){
      //  System.out.println("");
         for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                System.out.print(puzzle[row][col]);
                //seperates every 3rd col so there are boxes
                if ((col == 2) || (col == 5)) {
                    System.out.print("\t");
                }
            }
            //standard nextline
            System.out.println("");
            //prints another line to seperate every 3 row, to make boxes
            if ((row == 2) || (row == 5)) {
                System.out.println("");
            }
        }
    }

    static public void solve(int row, int col) throws Exception {
        //  printPuzzle();
        //  Thread.sleep( 1000 ) ;
        
        //Terminates recursive calls showing puzzle is solved
        if (row > 8) {
            throw new Exception("Solution found");
        }
        //If its not empty, check the next
        if (puzzle[row][col] != 0) {
            next(row, col);
            //find possible numbers for cell, starting from 1
        } else {
            for (int num = 1; num < 10; num++) {
                if (checkRow(row, num) && checkCol(col, num) && (checkBox(row, col, num))) {
                    puzzle[row][col] = num;
                    next(row, col);
                }
            }
            //nothing was found, reset to 0
            puzzle[row][col] = 0;
        }
    }
    //recursion step for solve
    static public void next(int row, int col) throws Exception {
        if (col < 8) {
            solve(row, col + 1);
        } else {
            solve(row + 1, 0);
        }
    }
    //checks if there is a similar number in the same row
    static boolean checkRow(int row, int num) {
        for (int col = 0; col < 9; col++) {
            if (puzzle[row][col] == num) {
                return false;
            }
        }

        return true;
    }
    //checks if there is a similar number in the same col
    static boolean checkCol(int col, int num) {
        for (int row = 0; row < 9; row++) {
            if (puzzle[row][col] == num) {
                return false;
            }
        }

        return true;
    }
    //Checks the box for a similar number
    static boolean checkBox(int row, int col, int num) {
        //Finds appropriate box
        row = (row / 3) * 3;
        col = (col / 3) * 3;
        //loops through designated box
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (puzzle[row + r][col + c] == num) {
                    return false;
                }
            }
        }

        return true;
    }
}
