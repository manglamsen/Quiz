package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity {
    TextView questionlabel1, questionCountlabel1, scorelable1;
    EditText answerEdt;
    Button sumbitButton;
    ProgressBar progressBar;
    ArrayList<QuestionModel> questionModelArrayList;
    int currentPosition = 1;
    int numberOfCurrectAnswer = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        questionCountlabel1 = findViewById(R.id.noQuestions);
        questionlabel1 = findViewById(R.id.question);
        scorelable1 = findViewById(R.id.score);

        answerEdt = findViewById(R.id.answer);
        sumbitButton = findViewById(R.id.sumbit);
        progressBar = findViewById(R.id.progress);

        questionModelArrayList = new ArrayList<>();
        setUpQuestion();
        setData();

        sumbitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckAnswer();

            }
        });

    }

    public void CheckAnswer() {
        String answerString = answerEdt.getText().toString().trim();
        if (answerString.equalsIgnoreCase((questionModelArrayList.get(currentPosition).getAnswer()))) {
            new SweetAlertDialog(MainActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("GOOD JOB")
                    .setContentText("RIGHT ANSWER")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            currentPosition++;
                            setData();

                            answerEdt.setText(" ");
                            sweetAlertDialog.dismiss();

                        }
                    })
                    .show();
        } else {
            new SweetAlertDialog(MainActivity.this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("WRONG ANSWER")
                    .setContentText(" THE RIGHT ANSWER IS =" + questionModelArrayList.get(currentPosition).getAnswer())
                    .setConfirmText("OK")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {

                            currentPosition++;
                            numberOfCurrectAnswer--;
                            setData();

                            answerEdt.setText(" ");
                            sweetAlertDialog.dismissWithAnimation();


                        }
                    })
                    .show();
        }
        int x = ((currentPosition + 1) * 100) / questionModelArrayList.size();
        progressBar.setProgress(x);

    }


    public void setUpQuestion() {
        questionModelArrayList.add(new QuestionModel("what is 1+2", "3"));
        questionModelArrayList.add(new QuestionModel("what is 3+2", "5"));
        questionModelArrayList.add(new QuestionModel("what is 12/3", "4"));
        questionModelArrayList.add(new QuestionModel("what is 15/5", "3"));
        questionModelArrayList.add(new QuestionModel("what is 1+2*6", "13"));
        questionModelArrayList.add(new QuestionModel("what is 16+2", "18"));
        questionModelArrayList.add(new QuestionModel("what is 10*5/2", "25"));
    }

    public void setData() {
        if (questionModelArrayList.size() > currentPosition)
        {
            questionlabel1.setText(questionModelArrayList.get(currentPosition).getQuestionString());
            questionCountlabel1.setText("Question number " + currentPosition);
            scorelable1.setText("Score :" + numberOfCurrectAnswer + "/" + questionModelArrayList.size());
            numberOfCurrectAnswer++;
        }
         else
        {
            new SweetAlertDialog(MainActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("YOU HAVE SUCCESSFULLY COMPLETED THE QUIZ")
                    .setContentText("YOUR SCORE IS - " + numberOfCurrectAnswer + "/" + questionModelArrayList.size())
                    .setConfirmText("RESTART QUIZ")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();
                            currentPosition=0;
                            numberOfCurrectAnswer=0;
                            progressBar.setProgress(0);
                            setData();
                        }
                    })

                    .setCancelText("CLOSE")
                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();
                            finish();
                        }
                    })

                    .show();
        }


    }
}
