package edu.moravian.csci299.tictactoe;

/**
 * The medium AI tries the following plays in this order:
 *  - that will win for the AI
 *  - that will block the opponent from winning
 *  - randomly
 */
public class MediumAI extends AI {
    /**
     * Tries to find a location to win, then a location to block, and finally chooses randomly.
     * @param board the board to play on
     * @param piece the piece to play (either 'X' or 'O')
     */
    @Override
    public void play(Board board, char piece) {
        // TODO
        playRandomMove(board, piece);
    }
}
