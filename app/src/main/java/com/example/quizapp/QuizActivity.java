package com.example.quizapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    TextView questionText, progressText;
    RadioGroup radioGroup;
    RadioButton optionA, optionB, optionC, optionD;
    Button submitButton;
    ProgressBar progressBar;

    List<Question> questions;
    int currentIndex = 0;
    int score = 0;
    String userName;
    boolean answered = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        userName = getIntent().getStringExtra("USER_NAME");

        questionText  = findViewById(R.id.questionText);
        progressText  = findViewById(R.id.progressText);
        radioGroup    = findViewById(R.id.radioGroup);
        optionA       = findViewById(R.id.optionA);
        optionB       = findViewById(R.id.optionB);
        optionC       = findViewById(R.id.optionC);
        optionD       = findViewById(R.id.optionD);
        submitButton  = findViewById(R.id.submitButton);
        progressBar   = findViewById(R.id.progressBar);

        loadQuestions();
        progressBar.setMax(questions.size());
        showQuestion();

        submitButton.setOnClickListener(v -> {
            if (!answered) {
                // --- SUBMIT phase ---
                int selectedId = radioGroup.getCheckedRadioButtonId();
                if (selectedId == -1) {
                    Toast.makeText(this, "Please select an answer", Toast.LENGTH_SHORT).show();
                    return;
                }
                answered = true;
                checkAnswer(selectedId);
                submitButton.setText("Next");
                disableOptions();
            } else {
                // --- NEXT phase ---
                currentIndex++;
                if (currentIndex < questions.size()) {
                    showQuestion();
                } else {
                    // Go to Results
                    Intent intent = new Intent(QuizActivity.this, ResultsActivity.class);
                    intent.putExtra("USER_NAME", userName);
                    intent.putExtra("SCORE", score);
                    intent.putExtra("TOTAL", questions.size());
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void loadQuestions() {
        questions = new ArrayList<>();
        questions.add(new Question("What is the capital of Australia?",
            new String[]{"Sydney", "Melbourne", "Canberra", "Brisbane"}, 2));
        questions.add(new Question("Which planet is closest to the Sun?",
            new String[]{"Earth", "Venus", "Mercury", "Mars"}, 2));
        questions.add(new Question("What is 12 x 12?",
            new String[]{"132", "144", "124", "148"}, 1));
        questions.add(new Question("Who wrote 'Romeo and Juliet'?",
            new String[]{"Charles Dickens", "Mark Twain", "William Shakespeare", "Jane Austen"}, 2));
        questions.add(new Question("What is the chemical symbol for Water?",
            new String[]{"O2", "H2O", "CO2", "HO"}, 1));
    }

    private void showQuestion() {
        answered = false;
        submitButton.setText("Submit");
        radioGroup.clearCheck();
        resetOptionColors();
        enableOptions();

        Question q = questions.get(currentIndex);
        questionText.setText(q.getQuestion());
        optionA.setText(q.getOptions()[0]);
        optionB.setText(q.getOptions()[1]);
        optionC.setText(q.getOptions()[2]);
        optionD.setText(q.getOptions()[3]);

        // Update progress
        progressBar.setProgress(currentIndex);
        progressText.setText("Question " + (currentIndex + 1) + " of " + questions.size());
    }

    private void checkAnswer(int selectedId) {
        RadioButton[] options = {optionA, optionB, optionC, optionD};
        int correctIndex = questions.get(currentIndex).getCorrectIndex();

        RadioButton selected = findViewById(selectedId);
        int selectedIndex = radioGroup.indexOfChild(selected);

        if (selectedIndex == correctIndex) {
            selected.setBackgroundColor(Color.parseColor("#4CAF50")); // Green
            score++;
        } else {
            selected.setBackgroundColor(Color.parseColor("#F44336")); // Red
            options[correctIndex].setBackgroundColor(Color.parseColor("#4CAF50")); // Green
        }
    }

    private void disableOptions() {
        optionA.setEnabled(false);
        optionB.setEnabled(false);
        optionC.setEnabled(false);
        optionD.setEnabled(false);
    }

    private void enableOptions() {
        optionA.setEnabled(true);
        optionB.setEnabled(true);
        optionC.setEnabled(true);
        optionD.setEnabled(true);
    }

    private void resetOptionColors() {
        int defaultColor = Color.TRANSPARENT;
        optionA.setBackgroundColor(defaultColor);
        optionB.setBackgroundColor(defaultColor);
        optionC.setBackgroundColor(defaultColor);
        optionD.setBackgroundColor(defaultColor);
    }
}
