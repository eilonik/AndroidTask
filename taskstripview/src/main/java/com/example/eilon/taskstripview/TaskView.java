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
    protected boolean disableTextVisability = true;
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
        setProgressBarTimer(progressBar);
        taskButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!clicked){
                    clickTime = new Date();
                }
                handleTask();
            }
        });
    }

    // This method handles each task specifically
    // To be overridden by subclasses
    protected void handleTask() {
        if(!clicked || clickTimeCheck()) {
            clicked = true;
            progressBar.setVisibility(View.INVISIBLE);
            textView.setVisibility( (disableTextVisability) ? View.VISIBLE : View.INVISIBLE);
        }
    }


    // Checks if 24 hours have passed since the last click
    protected boolean clickTimeCheck() {

        Date currentTime = new Date();
        int differenceInHours = (int)((currentTime.getTime() - clickTime.getTime())
                / Values.MILLISECONDS_TO_HOURS);
        if(differenceInHours < /*Values.PAUSE_HOURS*/10000) {
            Log.e("HERE","FALSE");
            return false;
        }
        Log.e("HERE","TRUE");
        return true;

    }


    // Set task is completed
    // In case 24 hours haven't passed
    protected void taskCompleted() {
        progressBar.setVisibility(View.INVISIBLE);
        clicked = true;
        this.textView.setVisibility( (disableTextVisability) ? View.VISIBLE : View.INVISIBLE);


        start();
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

    // get the task state when view is dispatched
    protected long getTaskState() {
        if(!clicked) {
            return 0;
        }

        return this.clickTime.getTime();
    }


    // this method sets the task state when the view resumes
    protected void setState(long state) {
        this.clickTime.setTime(state);
        if(clickTimeCheck()) {
            start();
        }

        else {
            taskCompleted();
        }
    }

}