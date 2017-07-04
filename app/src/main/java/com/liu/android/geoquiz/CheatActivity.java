package com.liu.android.geoquiz;

import android.content.Context;
import android.content.Intent;
import android.os.ParcelUuid;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    private static final String EXTRA_ANSWER_IS_TRUE = "com.liu.android.geoquiz.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN = "com.liu.android.geoquiz.answer_shown";
    private static final String KEY_IS_CHEATER1 = "com.liu.android.geoquiz.cheat1";
    private static final String KEY_IS_CHEATER2 = "com.liu.android.geoquiz.cheat2";

    private boolean mAnswerIsTrue;
    private TextView mAnswerTextView;
    private Button mShowAnswer;
    private boolean mUSeeAnswer;

    public static Intent newIntent(Context packageContext,boolean answerIsTrue){
        Intent i = new Intent(packageContext,CheatActivity.class);
        i.putExtra(EXTRA_ANSWER_IS_TRUE,answerIsTrue);
        return i;
    }

    public static boolean wasAnswerShown(Intent result){
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN,false);
    }

    public static boolean seenAnswer(Intent result){
        return result.getBooleanExtra(KEY_IS_CHEATER1,false);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE,false);

        mAnswerTextView = (TextView) findViewById(R.id.answerTextView);

        mShowAnswer = (Button) findViewById(R.id.showAnswerButton);


        //if (savedInstanceState != null){
           // mUSeeAnswer = savedInstanceState.getBoolean(KEY_IS_CHEATER,false);
        //}


        mShowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAnswerIsTrue){
                    mAnswerTextView.setText(R.string.true_button);
                }else{
                    mAnswerTextView.setText(R.string.false_button);
                }
                mUSeeAnswer = true;
                setAnswerShownResult(true,true);
            }
        });

        if (savedInstanceState !=null){
            mUSeeAnswer = savedInstanceState.getBoolean(KEY_IS_CHEATER1,false);
        }

        if (mUSeeAnswer){
            if (mAnswerIsTrue){
                mAnswerTextView.setText(R.string.true_button);
            }else{
                mAnswerTextView.setText(R.string.false_button);
            }
        }

    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean(KEY_IS_CHEATER1,mUSeeAnswer);
        savedInstanceState.putString(KEY_IS_CHEATER2, String.valueOf(mAnswerTextView));
    }




    private void setAnswerShownResult(boolean isAnswerShown,boolean uSeeAnswer){
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN,isAnswerShown);
        data.putExtra(KEY_IS_CHEATER1,uSeeAnswer);
        setResult(RESULT_OK,data);
    }

}
