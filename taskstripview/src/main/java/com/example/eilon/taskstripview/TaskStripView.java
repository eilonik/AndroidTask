/**
 * A TaskStripView composed of 3 single TaskViews
 */

package com.example.eilon.taskstripview;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class TaskStripView extends LinearLayout {

    TaskView firstTask;
    SecondTaskView secondTask;
    ThirdTaskView thirdTask;
    private int lastActiveTask = Values.FIRST_TASK;

    public TaskStripView(Context context) {
        super(context);
        initialize(context);
    }

    public TaskStripView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);


        // getting views of each task
        firstTask = (TaskView)findViewById(R.id.firstTask);
        secondTask = (SecondTaskView)findViewById(R.id.secondTask);
        thirdTask = (ThirdTaskView)findViewById(R.id.thirdTask);

        // retrieving image attributes of the tasks
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.TaskStripView);
        Drawable firstImage = attributes.getDrawable(R.styleable.TaskStripView_first_button_image);
        Drawable secondImage = attributes.getDrawable(R.styleable.TaskStripView_second_button_image);
        Drawable thirdImage = attributes.getDrawable(R.styleable.TaskStripView_third_button_image);
        String firstCaption = attributes.getString(R.styleable.TaskStripView_first_caption);
        String secondCaption = attributes.getString(R.styleable.TaskStripView_second_caption);
        String thirdCaption = attributes.getString(R.styleable.TaskStripView_third_caption);

        attributes.recycle();

        // setting task attributes
        setTaskAttributes(firstTask, firstImage, firstCaption);
        setTaskAttributes(secondTask, secondImage, secondCaption);
        setTaskAttributes(thirdTask, thirdImage, thirdCaption);

        // starting the first task
        manageTask(Values.FIRST_TASK);

    }

    private void initialize(Context context) {
        inflate(context, R.layout.task_strip_view, this);
        Resources res = getResources();
    }


    // This method sets the tasks attributes
    private void setTaskAttributes(TaskView task, Drawable image, String caption) {

        // Sets the values of the caption and image for the task
        if(image != null) {
            task.setButtonImage(image);
        }

        if(caption != null) {
            task.setTaskCaption(caption);
        }

        // This method is setting the parent element variable of the task as the instance
        // of this TaskStripView. used in order to communicate with the tasks manager
        task.setParent(this);

    }

    // This method manages the exposure of the tasks
    public void manageTask(int task) {

        switch (task) {
            case Values.FIRST_TASK:
                lastActiveTask = Values.FIRST_TASK;
                firstTask.start();
                break;

            case Values.SECOND_TASK:
                lastActiveTask = Values.SECOND_TASK;
                secondTask.start();
                break;

            case Values.THIRD_TASK:
                lastActiveTask = Values.THIRD_TASK;
                thirdTask.start();
                break;

        }
    }

    // Handle view state
/*
    // save view state
    @Override
    public Parcelable onSaveInstanceState()
    {
        Bundle bundle = new Bundle();
        bundle.putParcelable("superState", super.onSaveInstanceState());
        bundle.putInt("activeTask", this.lastActiveTask);
        bundle.putInt("thirdTaskState", thirdTask.getTaskState());
        bundle.putLong("firstTaskClick", firstTask.getClickTime());
        bundle.putLong("secondTaskClick", secondTask.getClickTime());
        bundle.putLong("thirdTaskClick", thirdTask.getClickTime());
        return bundle;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state)
    {
        if (state instanceof Bundle)
        {
            Bundle bundle = (Bundle) state;
            this.lastActiveTask = bundle.getInt("activeTask");
            switch (this.lastActiveTask) {
                case Values.SECOND_TASK:
                    firstTask.taskCompleted(bundle.getLong("firstTaskClick"), true);
                    manageTask(Values.SECOND_TASK);
                    break;

                case Values.THIRD_TASK:
                    firstTask.taskCompleted(bundle.getLong("firstTaskClick"), true);
                    secondTask.taskCompleted(bundle.getLong("secondTaskClick"), false);
                    manageTask(Values.THIRD_TASK);
                    thirdTask.setTaskState(bundle.getInt("thirdTaskState"));
                    thirdTask.enforceCaption(bundle.getInt("thirdTaskState"));
                    break;
            }
            state = bundle.getParcelable("superState");
        }
        super.onRestoreInstanceState(state);
    }

*/
    // Code repetition - will be fixed soon

    // Retrieving view state when app is restarted
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        SharedPreferences prefs = getContext().getSharedPreferences("TASK_STRIP_VIEW" + this.getId(),
                Context.MODE_PRIVATE);
        int lastActiveTask = prefs.getInt("last_active_task", 1);
        int thirdTaskState = prefs.getInt("third_task_state", 1);
        long firstTaskClickTime = prefs.getLong("first_task_click_time", 0);
        long secondTaskClickTime = prefs.getLong("second_task_click_time", 0);

        switch (lastActiveTask) {
            case Values.SECOND_TASK:
                firstTask.taskCompleted(firstTaskClickTime, true);
                manageTask(Values.SECOND_TASK);
                break;

            case Values.THIRD_TASK:
                firstTask.taskCompleted(firstTaskClickTime, true);
                secondTask.taskCompleted(secondTaskClickTime, false);
                manageTask(Values.THIRD_TASK);
                thirdTask.setTaskState(thirdTaskState);
                thirdTask.enforceCaption(thirdTaskState);
                break;
        }
    }


    // Saving view state when app is closed
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        SharedPreferences.Editor editor = this.getContext().getSharedPreferences("TASK_STRIP_VIEW"
                        + this.getId(), Context.MODE_PRIVATE).edit();
        editor.putInt("last_active_task", lastActiveTask);
        editor.putLong("first_task_click_time", firstTask.getClickTime());
        editor.putLong("second_task_click_time", secondTask.getClickTime());
        editor.putInt("third_task_state", thirdTask.getTaskState());
        editor.apply();
    }



}
