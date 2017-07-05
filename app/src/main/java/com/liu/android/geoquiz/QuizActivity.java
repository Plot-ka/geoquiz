package com.liu.android.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final int REQUEST_CODE_CHEAT = 0;
    private static final String ROTATED_CHEATER = "CheatByRotate";

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private TextView mQuestionTextView;
    private Button mCheatButton;
    private boolean mIsCheater;

    private Question[] mQuestionsBank = new Question[]{
            new Question(R.string.question_oceans,true,false),
            new Question(R.string.question_mideast,false,false),
            new Question(R.string.question_africa,false,false),
            new Question(R.string.question_americas,true,false),
            new Question(R.string.question_asia,true,false)
    };

    private int mCurrentIndex = 0;

    private void updateQuestion(){  //实际并不是升级，只是重载
        int question = mQuestionsBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue){
        boolean answerIsTrue = mQuestionsBank[mCurrentIndex].isAnswerTrue();
        int messageResId = 0;

        if (mIsCheater){
            messageResId = R.string.judgment_toast;
        }else{
            if (userPressedTrue == answerIsTrue){
                messageResId = R.string.correct_toast;
            }else{
                messageResId = R.string.incorrect_toast;
            }
        }

        Toast.makeText(this,messageResId,Toast.LENGTH_SHORT).show();
    }

    private void cheater(){
        if (mIsCheater){
            mQuestionsBank[mCurrentIndex].setAnswerIsPress(true);
        }
    }


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);

        mTrueButton = (Button)findViewById(R.id.true_button);
        mFalseButton = (Button)findViewById(R.id.false_button);

        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cheater();

                mCurrentIndex = (mCurrentIndex+1)%mQuestionsBank.length;

                if (mQuestionsBank[mCurrentIndex].isAnswerIsPress() == false){
                    mIsCheater = false;
                }else{
                    mIsCheater = true;
                }
                //程序仍有错误，用户可通过闲点击ShowAnswer查看答案，退回QuizActivity再点击cheat（不点击
                //ShowAnswer）再退回QuizActivity清除作弊


                updateQuestion();
            }

        });



        mCheatButton = (Button) findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start CheatActivity
                boolean answerIsTrue = mQuestionsBank[mCurrentIndex].isAnswerTrue();
                Intent i = CheatActivity.newIntent(QuizActivity.this,answerIsTrue);
                startActivityForResult(i,REQUEST_CODE_CHEAT);
            }
        });

        if (savedInstanceState != null){
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX,0);
            mIsCheater = savedInstanceState.getBoolean(ROTATED_CHEATER,false);
        }

        updateQuestion();

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG,"onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX,mCurrentIndex);
        savedInstanceState.putBoolean(ROTATED_CHEATER,mIsCheater);
    }


    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        if (resultCode!= Activity.RESULT_OK){   //不等式成立则未点击show answer按钮
            return;
        }
        if (requestCode == REQUEST_CODE_CHEAT){   //等式成立则点击了cheat按钮
            if (data == null){     //等式成立则未点击show answer按钮
                return;
            }


            mIsCheater = CheatActivity.seenAnswer(data);


        }
    }



    @Override
    public void onStart(){
        super.onStart();
        Log.d(TAG,"onStart() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

}
