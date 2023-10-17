package com.bryle_sanico.guessnum_activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private int selectedNumber;
    private int attempts = 0;

    private EditText guessInput;
    private Button guessButton, resetButton, exitButton;
    private TextView hintText, attemptsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        guessInput = findViewById(R.id.guess_input);
        guessButton = findViewById(R.id.guessBtn);
        hintText = findViewById(R.id.hintTxt);
        attemptsText = findViewById(R.id.attemptsTxt);
        resetButton = findViewById(R.id.resetBtn);
        exitButton = findViewById(R.id.exitBtn);
        // Generate a random number between 1 and 100.
        Random random = new Random();
        selectedNumber = random.nextInt(100) + 1;

    }

    public void actionBtn(View view) {
        // Use the button's ID to determine which action to take.
        int buttonId = view.getId();
        // Set the initial range for the guesses.
        int lowerBound = 1;
        int upperBound = 100;
        if (buttonId == R.id.guessBtn) {

            checkGuess(lowerBound, upperBound);
        } else if (buttonId == R.id.resetBtn) {
            // Reset the game state.
            Random random = new Random();
            selectedNumber = random.nextInt(100) + 1;
            attempts = 0;
            lowerBound = 1;
            upperBound = 100;
            hintText.setText("");
            attemptsText.setText("Attempts: 0");
            guessInput.setText("");
            guessButton.setEnabled(true);
            resetButton.setEnabled(false);
        } else if (buttonId == R.id.exitBtn) {
            // Exit the application.
            finish();
        }
    }
    private void checkGuess(int lowerBound, int upperBound) {
        String guessStr = guessInput.getText().toString();

        if (!guessStr.isEmpty()) {
            int guess = Integer.parseInt(guessStr);
            attempts++;
            attemptsText.setText("Attempts: " + attempts);

            /* Calculate the new range for the guesses, based on the user's guess.
            *  Gives the user a 25% chance of winning by biasing the range of guesses towards the correct number.
            * */
            int newLowerBound = lowerBound;
            int newUpperBound = upperBound;
            if (guess < selectedNumber) {
                newLowerBound = guess + 1;
            } else if (guess > selectedNumber) {
                newUpperBound = guess - 1;
            }

            // Check if the user guessed the correct number.
            if (guess == selectedNumber) {
                hintText.setText("Congratulations! You guessed it!");
                guessButton.setEnabled(false);
            }

            // Check if the user have reached the maximum number of attempts.
            else if (attempts >= 100) {
                hintText.setText("Sorry, you've reached the maximum number of attempts. The correct number was " + selectedNumber);
                guessButton.setEnabled(false);
                resetButton.setEnabled(true);
            }

            // Otherwise, update the range for the guesses and tell the user whether their guess was too high or too low.
            else {
                lowerBound = newLowerBound;
                upperBound = newUpperBound;

                if (guess < selectedNumber) {
                    hintText.setText("HIGHER");
                } else {
                    hintText.setText("LOWER");
                }
            }
        }
    }
}
