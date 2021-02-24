package edu.moravian.csci299.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class PlayActivity extends AppCompatActivity implements View.OnClickListener {

    private final static List<Integer> GameButtonIds = Arrays.asList(
            R.id.r0c0, R.id.r0c1, R.id.r0c2, R.id.r1c0, R.id.r1c1,
            R.id.r1c2, R.id.r2c0, R.id.r2c1, R.id.r2c2
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        String difficulty = getIntent().getStringExtra("difficulty");
        TextView diffText = findViewById(R.id.difficultyText);

        makeButtons(GameButtonIds);


        if (difficulty.equals("easy")){
            diffText.setText(R.string.easy);
        }
        else if (difficulty.equals("medium")){
            diffText.setText(R.string.medium);
        }
        else if (difficulty.equals("hard")){
            diffText.setText(R.string.hard);
        }

    }

    /** Creates buttons by iterating over a list of buttons.
     * As each button is created setOnClickListener is called.
     * @param IdList a list of Ids which are used to create Button objects*/
    public void makeButtons(List<Integer> IdList){
        for (Integer id: IdList){
            Button button = findViewById(id);
            button.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        //TODO: make the buttons change icons depending on whether a player clicks them or if the AI does
    }
}