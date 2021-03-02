package edu.moravian.csci299.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private final List<String> diffLevels = Arrays.asList("easy", "medium", "hard");

    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = findViewById(R.id.diffLevelSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, R.layout.spinner_item, diffLevels);
        spinner.setGravity(Gravity.CENTER);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Button startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, PlayActivity.class);
        String difficulty = ((String)spinner.getSelectedItem());
        intent.putExtra("difficulty", difficulty);
        startActivity(intent);

    }

}