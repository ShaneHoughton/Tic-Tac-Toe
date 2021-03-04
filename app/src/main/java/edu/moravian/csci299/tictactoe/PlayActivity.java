package edu.moravian.csci299.tictactoe;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class PlayActivity extends AppCompatActivity implements View.OnClickListener {

    private final static int[][] BOARD_BUTTON_IDS = new int[][]{
            {R.id.r0c0, R.id.r0c1, R.id.r0c2},
            {R.id.r1c0, R.id.r1c1, R.id.r1c2},
            {R.id.r2c0, R.id.r2c1, R.id.r2c2}
    };

    private Game gameModel;
    private String difficulty;

    /**
     * Creates gameModel from ViewModelProvider and sets difficulty from intent extra.
     * Updates Difficulty Text, listens for clicks on all Board Buttons,
     * initializes gameModel, and updates the Board.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        gameModel = new ViewModelProvider(this).get(Game.class);
        difficulty = getIntent().getStringExtra("difficulty");

        addOnClickListenersToBoardButtons();
        initializeGameModel();
        updateAllSquares();
        updatePlayerTextView();
    }

    /**
     * Used to start a new game after the EndActivity has finished
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        gameModel.startNewRound();
        updatePlayerTextView();
        updateAllSquares();
    }

    @Override
    public void onClick(View v) { /* IGNORE */}

    /**
     * Updates the Player's piece TextView to inform the player what their piece is.
     */
    private void updatePlayerTextView() {
        TextView v = findViewById(R.id.playerPieceText);
        char playerPiece = gameModel.getPlayerPiece();

        if(playerPiece == 'O')
            v.setText(R.string.player_o);

        else if(playerPiece == 'X'){
            v.setText(R.string.player_x);
        }
    }

    /**
     * Sets the OnClickListener for every Board Button.
     */
    private void addOnClickListenersToBoardButtons() {
        for (int row = 0; row < BOARD_BUTTON_IDS.length; row++) {
            for (int col = 0; col < BOARD_BUTTON_IDS[row].length; col++) {
                addOnClickListenerToBoardButton(BOARD_BUTTON_IDS[row][col], row, col);
            }
        }
    }

    /**
     * Sets AI and starts new round if game hasn't started yet.
     */
    private void initializeGameModel() {
        if (!gameModel.hasStarted())
        {
            gameModel.setAI(createAIFromDifficulty(difficulty));
            gameModel.startNewRound();
        }
    }

    /**
     * Creates and returns an instance of an AI subclass.
     * based on the difficulty specified.
     * @param difficulty A String containing the difficulty value.
     * @return An AI object for the respective difficulty.
     */
    private AI createAIFromDifficulty(String difficulty)
    {
        switch (difficulty) {
            case "easy":
                return new EasyAI();
            case "medium":
                return new MediumAI();
            case "hard":
                return new HardAI();
            default:
                throw new IllegalStateException("Unexpected difficulty value: " + difficulty);
        }
    }

    /**
     * Adds an onClickListener to a Board Button.
     * The listener checks if the game has started and
     * tries to place a piece on the square the button represents.
     * If the piece was able to be placed it then updates the board.
     * @param id id of the Board Button.
     * @param row row that the Board Button is in.
     * @param col column that the Board button is in.
     */
    private void addOnClickListenerToBoardButton(int id, int row, int col)
    {
        ImageButton b = findViewById(id);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gameModel.hasStarted()) {
                    if (gameModel.playPiece(row, col)) {
                        updateAllSquares();
                        checkIsGameOver();
                    }
                }
            }
        });
    }

    /**
     * Updates the image on every square on the board to reflect the game status.
     */
    private void updateAllSquares() {
        for (int row = 0 ; row < 3; row++) {
            for(int col = 0 ; col < 3 ; col++) {
                updateSquare(row, col);
            }
        }
    }

    /**
     * Starts end activity if game is over.
     */
    private void checkIsGameOver() {
        if (gameModel.hasTied() || gameModel.hasPlayerWon() || gameModel.hasAIWon()) {
            startEndActivity();
        }
    }

    /**
     * Updates the background of the Board Button
     * to match the piece on the respective square in the gameModel.
     * @param row the row the Board Button is in.
     * @param col the column the Board Button is in.
     */
    private void updateSquare(int row, int col) {
        ImageButton b = findViewById(BOARD_BUTTON_IDS[row][col]);

        char piece = gameModel.getBoard().getPiece(row,col);
        int id = getImageResourceForPiece(piece);

        b.setBackgroundResource(id);
    }

    /**
     * Gets the image resource for a tic-tac-toe piece.
     * @param piece char that is type of tic-tac-toe piece.
     * @return ID of image resource corresponding to the piece type.
     */
    private int getImageResourceForPiece(char piece) {
        switch (piece) {
            case 'X':
                return R.mipmap.x_piece_foreground;
            case 'O':
                return R.mipmap.o_piece_foreground;
            default:
                return android.R.color.transparent;
        }
    }

    /**
     * Converts the board from the finished game into a string.
     * @return String representation of the game board.
     */
    private String boardToString() {
        StringBuilder boardString = new StringBuilder();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                boardString.append(gameModel.getBoard().getPiece(i, j));
            }
        }

        return boardString.toString();
    }

    /**
     * Starts an EndActivity with an intent containing past game statistics.
     */
    private void startEndActivity() {
        Intent intent = new Intent(this, EndActivity.class);

        intent.putExtra("playerWins", gameModel.getPlayerWins());
        intent.putExtra("aiWins", gameModel.getAIWins());
        intent.putExtra("ties", gameModel.getTies());
        intent.putExtra("hasPlayerWon", gameModel.hasPlayerWon());
        intent.putExtra("hasTied", gameModel.hasTied());
        intent.putExtra("hasAIWon", gameModel.hasAIWon());
        intent.putExtra("boardString", boardToString());

        startActivityForResult(intent, 0);
    }

}