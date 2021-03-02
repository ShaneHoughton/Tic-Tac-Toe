package edu.moravian.csci299.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EndActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);
        int playerWins = getIntent().getIntExtra("player_wins", 0);
        int playerLosses = getIntent().getIntExtra("ai_wins", 0);
        int ties = getIntent().getIntExtra("ties", 0);
        boolean hasPlayerWon = getIntent().getBooleanExtra("hasPlayerWon", true);
        boolean hasTied = getIntent().getBooleanExtra("hasTied", true);
        boolean hasPlayerLost = getIntent().getBooleanExtra("hasPlayerLost", true);

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

        Button button = findViewById(R.id.playAgainButton);

        button.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        finish();
    }


}