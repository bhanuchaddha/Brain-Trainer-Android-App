package com.ben.braintrainer;

import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button goButton;
    Button playAgain;
    Button answer0;
    Button answer1;
    Button answer2;
    Button answer3;
    TextView scoreTextView;
    TextView timerTextView;
    TextView questionTextView;
    TextView successTextView;
    ConstraintLayout gameLayout;
    ArrayList<Integer> answers = new ArrayList<>();
    int correctAnswerPosition;

    private int totalQuestion = 0;
    private int correctAnswerCount = 0;

    private boolean onGoing = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goButton = findViewById(R.id.goButton);
        playAgain = findViewById(R.id.playAgain);
        answer0 = findViewById(R.id.answer0);
        answer1 = findViewById(R.id.answer1);
        answer2 = findViewById(R.id.answer2);
        answer3 = findViewById(R.id.answer3);
        scoreTextView = findViewById(R.id.scoreTextView);
        timerTextView = findViewById(R.id.timerTextView);
        questionTextView = findViewById(R.id.questionTextView);
        successTextView = findViewById(R.id.successTextView);
        gameLayout = findViewById(R.id.gameLayout);

        goButton.setVisibility(View.VISIBLE);
        gameLayout.setVisibility(View.INVISIBLE);
        successTextView.setVisibility(View.INVISIBLE);


    }

    public void playAgain(View view){
        totalQuestion = 0;
        correctAnswerCount = 0;
        scoreTextView.setText(correctAnswerCount+"/"+totalQuestion);
        successTextView.setVisibility(View.INVISIBLE);

        startGame(view); // passes dummy view
    }

    public void startGame(View view){
        goButton.setVisibility(View.INVISIBLE);
        playAgain.setVisibility(View.INVISIBLE);
        gameLayout.setVisibility(View.VISIBLE);
        showNewQuestion();
        onGoing = true;
        new CountDownTimer(10000,1000){
            @Override
            public void onTick(long millisUntilFinished) {
                timerTextView.setText((millisUntilFinished/1000)+"s");
            }

            @Override
            public void onFinish() {
                playAgain.setVisibility(View.VISIBLE);
                onGoing=false;
                Toast.makeText(getApplicationContext(), "Game Over!!", Toast.LENGTH_SHORT).show();
            }
        }.start();
    }



    public void chooseAnswer(View view){
        if(onGoing){
            int selectedAnswerPosition = Integer.parseInt(view.getTag().toString());
            if (selectedAnswerPosition == correctAnswerPosition){
                correctAnswerCount++;
                successTextView.setText("Yo Correct!! :)");
            }else {
                successTextView.setText("Wrong :(");
            }
            scoreTextView.setText(correctAnswerCount+"/"+totalQuestion);

            successTextView.setVisibility(View.VISIBLE);
            showNewQuestion();
        }else {
            Toast.makeText(this, "Click Play again\n to start new game", Toast.LENGTH_SHORT).show();
        }

    }

    private void showNewQuestion() {

        //Reset
        answers.clear();


        Random rand = new Random();

        int a = rand.nextInt(21);
        int b = rand.nextInt(21);

        questionTextView.setText(a + " + " + b);

        int correctAnswer = a + b;
        correctAnswerPosition = rand.nextInt(4);
        for (int i = 0; i < 4; i++) {
            if (i == correctAnswerPosition) {
                answers.add(correctAnswer);
            }else {
                int ans = rand.nextInt(41);
                while (ans == correctAnswer || answers.contains(ans)) {
                    ans = rand.nextInt(41);
                }
                answers.add(ans);
            }

        }

        answer0.setText(answers.get(0).toString());
        answer1.setText(answers.get(1).toString());
        answer2.setText(answers.get(2).toString());
        answer3.setText(answers.get(3).toString());
        totalQuestion++;
    }
}
