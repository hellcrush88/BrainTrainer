package sky.braintrainer;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import static android.R.id.button1;
import static android.R.id.button2;
import static android.R.id.button3;
import static android.R.interpolator.linear;
import static sky.braintrainer.R.id.gridLayout;

public class MainActivity extends AppCompatActivity {


    Button startBtn, button0, button1,button2,button3, playagainbtn;
    TextView sumText, resultText, scoreText, timerText;

    // ArrayList for answers
    ArrayList<Integer> answers = new ArrayList<Integer>();
    int locationOfCorrectAnswer;

    int score = 0;
    int tries = 0;

    CountDownTimer cdTimer;
    RelativeLayout layout;
    LinearLayout upperLayout, lowerLayout;
    GridLayout gridLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        startBtn = (Button)findViewById(R.id.start_btn);
        sumText = (TextView)findViewById(R.id.sumText);
        resultText = (TextView)findViewById(R.id.resultText);
        scoreText = (TextView)findViewById(R.id.scoreText);
        timerText = (TextView)findViewById(R.id.timerText);
        playagainbtn = (Button) findViewById(R.id.playAgainBtn);
        layout =(RelativeLayout)findViewById(R.id.layout);
        gridLayout = (GridLayout)findViewById(R.id.gridLayout);
        upperLayout = (LinearLayout)findViewById(R.id.upper_layout);
        lowerLayout = (LinearLayout)findViewById(R.id.lower_layout);



        button0 = (Button)findViewById(R.id.gridBtn1);
        button1 = (Button)findViewById(R.id.gridBtn2);
        button2 = (Button)findViewById(R.id.gridBtn3);
        button3 = (Button)findViewById(R.id.gridBtn4);

        generateQuestion();

    }

    public void start(View view){
        startBtn.setVisibility(View.INVISIBLE);
        layout.setVisibility(View.VISIBLE);
        timer();
    }

    public void chooseAnswer(View view){
        String tag = view.getTag().toString();
        if(tag.equals(Integer.toString(locationOfCorrectAnswer))){
            score++;

            resultText.setText("Correct!");
        }else{

            resultText.setText("Wrong");
        }
        tries++;
        scoreText.setText(Integer.toString(score) + "/" + Integer.toString(tries));
        generateQuestion();
    }

    public void generateQuestion(){
        // Clear up available answers inside the arraylist before proceeding
        answers.clear();

        Random random = new Random();

        //Generate random number between 0 and 20
        int a = random.nextInt(21);
        int b = random.nextInt(21);
        sumText.setText(String.valueOf(a) + " + " + String.valueOf(b));

        locationOfCorrectAnswer = random.nextInt(4);

        int incorrectAnswer;

        for(int i = 0; i <4; i++){
            if(i == locationOfCorrectAnswer){
                answers.add(a + b);
            }else{
                // ensuring we dont have incorrect answer that happened to be the same as correct answer
                incorrectAnswer = random.nextInt(41);
                while(incorrectAnswer == a+b){
                    incorrectAnswer = random.nextInt(41);
                }
                answers.add(incorrectAnswer);
            }
        }
        button0.setText(Integer.toString(answers.get(0)));
        button1.setText(Integer.toString(answers.get(1)));
        button2.setText(Integer.toString(answers.get(2)));
        button3.setText(Integer.toString(answers.get(3)));
    }

    public void timer(){
        cdTimer = new CountDownTimer(9100, 1000) {
            @Override
            public void onTick(long msUntilFinished) {
                if(msUntilFinished/1000 <= 9){
                    timerText.setText("0" + String.valueOf(msUntilFinished/1000)+ "s");
                }else {
                    timerText.setText(String.valueOf(msUntilFinished / 1000) + "s");
                }
            }

            @Override
            public void onFinish() {
                timerText.setText("00s");
                resultText.setText("Your score is : " + Integer.toString(score) + "/" + Integer.toString(tries));
                playagainbtn.setVisibility(View.VISIBLE);
                for (int i = 0; i < gridLayout.getChildCount(); i++) {
                    for (int j = 0; j < upperLayout.getChildCount(); j++) {
                        ((Button) upperLayout.getChildAt(j)).setClickable(false);
                    }
                    for (int k = 0; k < lowerLayout.getChildCount(); k++) {
                        ((Button) lowerLayout.getChildAt(k)).setClickable(false);
                    }
                }
            }
        }.start();
    }

    public void playAgain(View view){
        // Resetting all values back to 0
        score = 0;
        tries = 0;
        timerText.setText("30s");
        scoreText.setText("0/0");
        resultText.setText("");
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            for (int j = 0; j < upperLayout.getChildCount(); j++) {
                ((Button) upperLayout.getChildAt(j)).setClickable(true);
            }
            for (int k = 0; k < lowerLayout.getChildCount(); k++) {
                ((Button) lowerLayout.getChildAt(k)).setClickable(true);
            }
        }
        playagainbtn.setVisibility(View.INVISIBLE);
        generateQuestion();
        timer();
    }
}
