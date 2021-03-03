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

    private final static int [][] gameButtonIds = new int[][]{
            {R.id.r0c0, R.id.r0c1, R.id.r0c2},
            {R.id.r1c0, R.id.r1c1, R.id.r1c2},
            {R.id.r2c0, R.id.r2c1, R.id.r2c2}
    };

    private Game game = new Game();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        String difficulty = getIntent().getStringExtra("difficulty");
        Log.e("PlayActivity", "got intent");

        makeButtons(gameButtonIds);

        AI ai;

        switch (difficulty) {
            case "easy":
                ai = new EasyAI();
                break;
            case "medium":
                ai = new MediumAI();
                break;
            case "hard":
                ai = new HardAI();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + difficulty);
        }
        game.setAI(ai);
        game.startNewRound();
        updateBoard();
    }


    /** Creates buttons by iterating over a list of buttons.
     * As each button is created setOnClickListener is called.
     * @param IdList a list of Ids which are used to create Button objects*/
    public void makeButtons(int [][] IdList){

        for (int[] ints : IdList) {
            for (int j = 0; j < IdList.length; j++) {
                ImageButton button = findViewById(ints[j]);
                button.setOnClickListener(this);
            }
        }
    }

    @Override
    public void onClick(View v) {
        //TODO: make the buttons change icons depending on whether a player clicks them or if the AI does
        int id = v.getId();

        if(game.hasStarted()) {
            for (int i = 0; i < gameButtonIds.length; i++) {
                for (int j = 0; j < gameButtonIds.length; j++) {
                    if (id == gameButtonIds[i][j]) {
                        if (game.playPiece(i, j)) {
                            updateBoard();
                            break;
                        }
                    }
                }
            }
        }

    }

    public void updateBoard(){
        for (int i = 0 ; i < 3; i++){
            for(int j = 0 ; j < 3 ; j++)
            {
                int imgResId = android.R.color.transparent;
                ImageButton b = findViewById(gameButtonIds[i][j]);

                if(game.getBoard().getPiece(i,j) == 'X'){
                    imgResId = R.mipmap.x_piece_foreground;

                }
                else if(game.getBoard().getPiece(i,j) == 'O'){
                    imgResId = R.mipmap.o_piece_foreground;
                }

                b.setBackgroundResource(imgResId);
            }
        }

        if(game.hasTied()|| game.hasAIWon()|| game.hasPlayerWon()){
            makeIntent();
        }
    }

   private String boardToString(){
        StringBuilder boardString = new StringBuilder();

       for (int i = 0 ; i < 3; i++){
           for(int j = 0 ; j < 3 ; j++)
           {
               boardString.append(game.getBoard().getPiece(i, j));
           }
       }

       return boardString.toString();
   }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        game.startNewRound();
        updateBoard();
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void makeIntent(){
        Intent intent = new Intent(this, EndActivity.class);
        intent.putExtra("player_wins", game.getPlayerWins());//fix
        intent.putExtra("ai_wins", game.getAIWins());//fix
        intent.putExtra("ties", game.getTies());
        intent.putExtra("boardString",boardToString());
        intent.putExtra("hasPlayerWon", game.hasPlayerWon());
        intent.putExtra("hasTied", game.hasTied());
        intent.putExtra("hasPlayerLost", game.hasAIWon());
        startActivityForResult(intent, 0);

    }

}