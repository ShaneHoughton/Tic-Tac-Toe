package edu.moravian.csci299.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class EndActivity extends AppCompatActivity implements View.OnClickListener {
    private final static int [] imageIds = new int[]{
            R.id.imageR0C0, R.id.imageR0C1, R.id.imageR0C2,
            R.id.imageR1C0, R.id.imageR1C1, R.id.imageR1C2,
            R.id.imageR2C0, R.id.imageR2C1, R.id.imageR2C2,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);
        int playerWins = getIntent().getIntExtra("player_wins", 0);
        int playerLosses = getIntent().getIntExtra("ai_wins", 0);
        int ties = getIntent().getIntExtra("ties", 0);
        String boardString = getIntent().getStringExtra("boardString");
        boolean hasPlayerWon = getIntent().getBooleanExtra("hasPlayerWon", true);
        boolean hasTied = getIntent().getBooleanExtra("hasTied", true);
        boolean hasPlayerLost = getIntent().getBooleanExtra("hasPlayerLost", true);
        Log.d("endAct", "this is the string" + boardString);

        String gameInfo = "Wins: " + playerWins + " | Losses: " + playerLosses + " | Ties: " + ties;

        ((TextView)findViewById(R.id.gameInfo)).setText(gameInfo);
        ((TextView)findViewById(R.id.gameOver)).setText(R.string.game_over);
        ((TextView)findViewById(R.id.gameOver)).setText(R.string.game_over);
        if(hasPlayerWon){
            ((TextView)findViewById(R.id.gameStatus)).setText(R.string.win);
        }
        else if(hasPlayerLost){
            ((TextView)findViewById(R.id.gameStatus)).setText(R.string.loss);

        }
        else if(hasTied){
            ((TextView)findViewById(R.id.gameStatus)).setText(R.string.tie);
        }
        displayBoard(boardString);

        Button button = findViewById(R.id.playAgainButton);

        button.setOnClickListener(this);


    }

    public void displayBoard(String boardString){
        for(int i = 0; i < imageIds.length; i ++){
            if(boardString.charAt(i) == 'X'){
                ((ImageView)findViewById(imageIds[i])).setImageResource(R.mipmap.x_piece_foreground);
            }
            else if (boardString.charAt(i) == 'O'){
                ((ImageView)findViewById(imageIds[i])).setImageResource(R.mipmap.o_piece_foreground);
            }
        }
    }

    @Override
    public void onClick(View v) {
        setResult(0);
        finish();
    }


}