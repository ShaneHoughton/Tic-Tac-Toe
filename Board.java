package edu.moravian.csci299.tictactoe;

import androidx.annotation.NonNull;

import java.util.Arrays;

/**
 * A Tic-Tac-Toe game board. It is always 3x3 and contains the chars ' ' (space) for an empty spot,
 * 'X' and 'O' for player moves.
 */
public class Board implements Cloneable {
    /** The actual board of characters */
    private final char[][] board = new char[3][3];

    /**
     * Construct a new board that is filled in completely with spaces.
     */
    public Board() {
        for (int i = 0 ; i < this.board.length; i++){
            for(int j = 0 ; j < this.board.length ; j++)
            {
                this.board[i][j] = ' ';
                }
            }
    }

    /**
     * Construct a new board that is filled in with the same contents of the other board.
     */
    public Board(Board board) {
        for (int i = 0 ; i < this.board.length; i++){
            System.arraycopy(board.board[i], 0, this.board[i], 0, this.board.length);
        } // use System.arraycopy() to copy a given Board object's board array.
    }

    /**
     * Gets a piece on the board (either 'X' or 'O') or if it is empty (returning ' ').
     * @param r the row on the board to look up (0-2)
     * @param c the column on the board to look up (0-2)
     * @return one of 'X', 'O', or ' '
     */
    public char getPiece(int r, int c) {
        return this.board[r][c];
    }

    /**
     * Returns true if the location on the board is empty.
     * @param r the row on the board to look up (0-2)
     * @param c the column on the board to look up (0-2)
     * @return same as getPiece(r, c) == ' '
     */
    public boolean isLocationEmpty(int r, int c) {
        return this.board[r][c] == ' ';
    }

    /**
     * Returns the number of pieces of the given type across the entire board.
     * @param piece the piece to check for, one of ' ', 'X', or 'O'
     * @return the number of those pieces on the board
     */
    public int countPieces(char piece) {
        int pieceCount = 0;
        for (char[] chars : this.board) { // iterate over each character array in the board
            for (int j = 0; j < this.board.length; j++) { // then iterate over each character in the array
                if (chars[j] == piece) {
                    pieceCount++;
                }
            }
        }
        return pieceCount;
    }

    /**
     * Play a piece onto the board, checking to make sure that the space is empty first.
     * @param r the row where to place the piece (0-2)
     * @param c the column where to place the piece (0-2)
     * @param piece the piece to place, either 'X' or 'O'
     * @return true if the space was empty and the play was successful, false otherwise
     */
    public boolean playPiece(int r, int c, char piece) {
        if (!isGameOver() && this.board[r][c] == ' '){
            this.board[r][c] = piece;
            return true;
        }
        return false;
    }

    /**
     * @param piece the piece to check for a win with
     * @return true if the player with the given piece has won, false otherwise
     */
    public boolean hasWon(char piece) {
        for(int i = 0 ; i < this.board.length; i++){
            if (board[i][0] == piece && board[i][1] == piece && board[i][2] == piece) //checking the ith row for a win
                return true;
            if (board[0][i] == piece && board[1][i] == piece && board[2][i] == piece) //checking the ith column for a win
                return true;
        }

        return board[0][0] == piece && board[1][1] == piece && board[2][2] == piece ||
                board[2][0] == piece && board[1][1] == piece && board[0][2] == piece; // checking diagonals
    }

    /**
     * @return true if the board is full of pieces (no ' ' pieces on the board) and neither X or O
     * has won
     */
    public boolean hasTied() {
        for (int i = 0; i < this.board.length; i++){
            for (int j = 0; j < this.board.length; j++){
                if (isLocationEmpty(i,j))
                    return false;
            }
        }
        return !hasWon('X') && !hasWon('O');
    }

    /**
     * @return true if X or O has won or the game is tied
     */
    public boolean isGameOver() {
        return (hasWon('X') || hasWon('O') || hasTied());
    }


    ////////// General Object Methods //////////

    @NonNull
    @Override
    protected Object clone() { return new Board(this); }

    @Override
    public String toString() { return "Board{board=" + Arrays.deepToString(board) + '}'; }

    @Override
    public boolean equals(Object o) {
        return this == o || (o != null && getClass() == o.getClass() && Arrays.deepEquals(board, ((Board)o).board));
    }

    @Override
    public int hashCode() { return Arrays.deepHashCode(board); }
}
