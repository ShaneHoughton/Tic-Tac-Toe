package edu.moravian.csci299.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.view.Gravity;

import java.util.List;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private final List<String> DIFFICULTY_LEVELS = Arrays.asList("easy", "medium", "hard");

    private Spinner spinner;

    /**
     * Initializes the Difficulty Level Spinner and the Start Button.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = findViewById(R.id.diffLevelSpinner);
        initializeDifficultyLevelSpinner();

        Button startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(this);
    }

    /**
     * Adds difficulty levels to Difficulty Level Spinner.
     * Sets initial spinner settings.
     */
    private void initializeDifficultyLevelSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, R.layout.spinner_item, DIFFICULTY_LEVELS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setGravity(Gravity.CENTER);
    }

    /**
     * When the Start Button is clicked, sends an intent to start PlayActivity.
     * The intent contains an extra named 'difficulty', containing the difficulty
     * the user selected with the Difficulty Level Spinner.
     * @param v View that got clicked. (In this case the Start Button)
     */
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, PlayActivity.class);

        String difficulty = ((String)spinner.getSelectedItem());
        intent.putExtra("difficulty", difficulty);

        startActivity(intent);
    }
}