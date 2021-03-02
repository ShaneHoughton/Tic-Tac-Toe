package edu.moravian.csci299.tictactoe;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

    private Game game = new Game(); //make this private?
    private int playerPieceId; //make this private?

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        String difficulty = getIntent().getStringExtra("difficulty");
        //TextView diffText = findViewById(R.id.difficultyText);

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
//        if (game.getPlayerPiece() == 'O'){
//            playerPieceId = R.mipmap.o_piece;
//        }
//        else{
//            playerPieceId = R.mipmap.x_piece;
//        }
        game.startNewRound();
        updateBoard();

    }


    /** Creates buttons by iterating over a list of buttons.
     * As each button is created setOnClickListener is called.
     * @param IdList a list of Ids which are used to create Button objects*/
    public void makeButtons(int [][] IdList){

        for (int[] ints : IdList) {
            for (int j = 0; j < IdList.length; j++) {
                Button button = findViewById(ints[j]);
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
                int imgResId;
                Button b = findViewById(gameButtonIds[i][j]);
                if(game.getBoard().getPiece(i,j) == 'X'){
                    imgResId = R.drawable.x_foreground;
                }
                else if(game.getBoard().getPiece(i,j) == 'O'){
                    imgResId = R.drawable.o_foreground;
                }
                else{
                    imgResId = R.drawable.ic_launcher_foreground;
                }

                b.setBackgroundResource(imgResId);
            }
        }
        if(game.hasTied()||game.hasAIWon()||game.hasPlayerWon()){
            Log.e("updateBoard", "making intent call" );
            makeIntent();
            game.startNewRound();
            updateBoard();
        }
    }

    private char[] boardToArray(){
        char[] boardArray = new char[9];
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                boardArray[i + j] = game.getBoard().getPiece(i,j);
            }
        }
        return boardArray;
    }

    public void makeIntent(){
        Intent intent = new Intent(this, EndActivity.class);
        intent.putExtra("player_wins", game.getPlayerWins());
        intent.putExtra("ai_wins", game.getAIWins());
        intent.putExtra("ties", game.getTies());
        intent.putExtra("String","hello");
        intent.putExtra("hasPlayerWon", game.hasPlayerWon());
        intent.putExtra("hasTied", game.hasTied());
        intent.putExtra("hasPlayerLost", game.hasAIWon());
        startActivity(intent);

    }

}