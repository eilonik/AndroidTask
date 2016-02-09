/**
 * A single com.example.eilon.taskstripview.TaskView Class
 */

package com.example.eilon.taskstripview;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Date;


public class TaskView extends LinearLayout {

    protected Button taskButton;
    protected TaskStripView parentTaskStripView;
    protected Date clickTime = new Date();
    protected boolean clicked = false;
    private boolean disableTextVisability = true;
    protected boolean timerOn = false;
    protected CountDownTimer timer;
    protected boolean completed = false;
    ProgressBar progressBar;
    TextView textView;



    // Constructors
    public TaskView(Context context) {
        super(context);
        initialize(context);
    }

    public TaskView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public void initialize(Context context) {
        inflate(context, R.layout.view_task, this);
        taskButton = (Button)findViewById(R.id.taskButton);

    }

    // Setters

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    /**
     * A method for setting A task image manually
     * @param image
     */
    public void setButtonImage(Drawable image) {
        taskButton.setBackground(image);
        taskButton.setText("");
        progressBar = (ProgressBar)findViewById(R.id.taskProgressBar);
        textView = (TextView)findViewById(R.id.taskCompletionTexs);
    }

    /**
     * A method setting a task caption
     * @param caption
     */
    public void setTaskCaption(String caption) {
        taskButton.setText(caption);
    }

    // Sets the parent element of the task
    public void setParent(TaskStripView parent) {
        parentTaskStripView = parent;
    }

    // This method starts the task
    public void start() {
        this.setVisibility(View.VISIBLE);
        setProgressBarTimer(progressBar);
        taskButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!clicked){
                    clicked = true;
                    clickTime = new Date();
                }
                handleTask();
            }
        });
    }

    // This method handles each task specifically
    // To be overridden by subclasses
    protected void handleTask() {
        if(clickTimeCheck() || !isTimerOn()) {
            progressBar.setVisibility(View.INVISIBLE);
            textView.setVisibility(View.VISIBLE);
            //clicked = true;
            parentTaskStripView.manageTask(Values.SECOND_TASK);
            setTimer();

        }
    }

    // Set a 24 hours timer
    protected void setTimer() {

        long elapsedTime = (new Date().getTime() - getClickTime());
        timerOn = true;
        CountDownTimer countDownTimer = new CountDownTimer(Values.PAUSE_HOURS * Values.MILLISECONDS_TO_HOURS
                - elapsedTime , 1000) {


            public void onTick(long millisUntilFinished) {
                Log.e("seconds remaining:", "" + millisUntilFinished / 1000);

            }

            public void onFinish() {
                timerOn = false;
                enableTask();
            }

        };
        this.timer = countDownTimer;
        countDownTimer.start();

    }

    // A method that enables the task
    protected void enableTask() {
        completed = false;
        if(disableTextVisability) {
            this.textView.setVisibility(View.INVISIBLE);
        }
        this.progressBar.setVisibility(View.VISIBLE);
        this.clickTime = new Date();
        this.clicked = false;
        this.start();
    }

    // Checks if 24 hours have passed since the last click
    protected boolean clickTimeCheck() {
        if(clicked) {
            Date currentTime = new Date();
            int differenceInHours = (int)((currentTime.getTime() - clickTime.getTime())
                    / Values.MILLISECONDS_TO_HOURS);
            if(differenceInHours < Values.PAUSE_HOURS) {
                return false;
            }

            return true;
        }

        else {

            return true;
        }

    }


    // Set task is completed
    // used externally when resuming the view
    protected void taskCompleted(long clickTime, boolean completeText) {
        this.clickTime.setTime(clickTime);
        this.progressBar.setVisibility(View.INVISIBLE);
        this.disableTextVisability = completeText;
        if(completeText) {
            this.textView.setVisibility(View.VISIBLE);
        }
        this.start();
    }

    // Setting a progress bar timer
    protected void setProgressBarTimer(final ProgressBar progressBar) {
        CountDownTimer countDownTimer;
        final int[] timerArray = {0};

        progressBar.setProgress(timerArray[0]);
        countDownTimer = new CountDownTimer(400,1000) {

            @Override
            public void onTick(long millisUntilFinished) {

                timerArray[0]++;
                progressBar.setProgress(timerArray[0]);

            }

            @Override
            public void onFinish() {
                timerArray[0]++;
                progressBar.setProgress(timerArray[0]);
            }
        };
        countDownTimer.start();

    }

    // getters

    // get click time
    protected long getClickTime() {
        return this.clickTime.getTime();
    }

    // check timer state
    protected boolean isTimerOn() {
        return timerOn;
    }

    // cancels the current timer
    protected void killTimer() {
        this.timer.cancel();
        this.timer = null;
        timerOn = false;
    }

}
