package com.liu.android.geoquiz;

/**
 * Created by Administrator on 2017/6/30.
 */

public class Question {

    private int mTextResId;
    private boolean mAnswerTrue;
    private boolean mAnswerIsPress;

    public Question(int textResId,boolean answerTrue,boolean answerIsPress){
        mAnswerTrue = answerTrue;
        mTextResId = textResId;
        mAnswerIsPress = answerIsPress;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public boolean isAnswerIsPress() {
        return mAnswerIsPress;
    }

    public void setAnswerIsPress(boolean answerIsPress) {
        mAnswerIsPress = answerIsPress;
    }
}
