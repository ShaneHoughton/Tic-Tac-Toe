package edu.moravian.csci299.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class EndActivity extends AppCompatActivity implements View.OnClickListener {
    private final static int[] IMAGE_VIEW_IDS = new int[]{
            R.id.imageR0C0, R.id.imageR0C1, R.id.imageR0C2,
            R.id.imageR1C0, R.id.imageR1C1, R.id.imageR1C2,
            R.id.imageR2C0, R.id.imageR2C1, R.id.imageR2C2,
    };

    private int numPlayerWins, numAIWins, numTies;
    private boolean hasPlayerWon, hasTied, hasAIWon;
    private String boardString;

    /**
     * Updates Game Info, Game Status, and listens for clicks on Play Again Button.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        getAllIntentExtras();

        updateGameInfoText();
        updateGameStatusText();
        displayBoard(boardString);

        Button playAgainButton = findViewById(R.id.playAgainButton);
        playAgainButton.setOnClickListener(this);
    }

    /**
     * Puts all intent extras into class fields.
     */
    private void getAllIntentExtras() {
        Intent intent = getIntent();

        numPlayerWins = intent.getIntExtra("playerWins", 0);
        numAIWins = intent.getIntExtra("aiWins", 0);
        numTies = intent.getIntExtra("ties", 0);
        hasPlayerWon = intent.getBooleanExtra("hasPlayerWon", false);
        hasTied = intent.getBooleanExtra("hasTied", false);
        hasAIWon = intent.getBooleanExtra("hasAIWon", false);
        boardString = intent.getStringExtra("boardString");
    }

    /**
     * Updates the text for the Game Info TextView to display the
     * number of wins, losses, and ties.
     */
    private void updateGameInfoText() {
        String s = "Wins: " + numPlayerWins + " | Losses: " + numAIWins + " | Ties: " + numTies;

        TextView v = findViewById(R.id.gameInfo);
        v.setText(s);
    }

    /**
     * Updates the text for the Game Status TextView.
     */
    private void updateGameStatusText() {
        TextView gameStatus = findViewById(R.id.gameStatus);
        gameStatus.setText(getGameStatus());
    }

    /**
     * Returns a String Resource that represents the game status.
     * @return Id of the String resource that has the game status.
     */
    private int getGameStatus()
    {
        if (hasPlayerWon) {
            return R.string.win;
        }
        else if (hasAIWon) {
            return R.string.loss;
        }
        else if (hasTied) {
            return R.string.tie;
        }
        else {
            throw new IllegalStateException("Game status is not valid.");
        }
    }

    /**
     * Assigns the IMAGE IDs to an image resource corresponding to X or O in the given string
     * @param boardString string representing the board from the game played.
     */
    public void displayBoard(String boardString) {
        for (int i = 0; i < IMAGE_VIEW_IDS.length; i++) {
            if (boardString.charAt(i) == 'X') {
                ((ImageView) findViewById(IMAGE_VIEW_IDS[i])).setImageResource(R.mipmap.x_piece_foreground);
            } else if (boardString.charAt(i) == 'O') {
                ((ImageView) findViewById(IMAGE_VIEW_IDS[i])).setImageResource(R.mipmap.o_piece_foreground);
            }
        }
    }

    /**
     * Finishes this activity and resumes the previous activity when
     * the Play Again Button is clicked.
     * @param v View that was clicked. (In this case the Play Again Button)
     */
    @Override
    public void onClick(View v) {
        setResult(0);
        finish();
    }






}