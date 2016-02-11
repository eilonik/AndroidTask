/**
 * A TaskStripView composed of 3 single TaskViews
 */

package com.example.eilon.taskstripview;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class TaskStripView extends LinearLayout {

    TaskView firstTask;
    SecondTaskView secondTask;
    ThirdTaskView thirdTask;
    TaskView tasks[] = {firstTask, secondTask, thirdTask};

    public TaskStripView(Context context) {
        super(context);
        initialize(context);
    }

    public TaskStripView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);


        // getting views of each task
        tasks[Values.FIRST_TASK] = (TaskView)findViewById(R.id.firstTask);
        tasks[Values.SECOND_TASK] = (SecondTaskView)findViewById(R.id.secondTask);
        tasks[Values.THIRD_TASK] = (ThirdTaskView)findViewById(R.id.thirdTask);

        // retrieving image attributes of the tasks
        //TODO:iterate over attributes array
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.TaskStripView);
        Drawable firstImage = attributes.getDrawable(R.styleable.TaskStripView_first_button_image);
        Drawable secondImage = attributes.getDrawable(R.styleable.TaskStripView_second_button_image);
        Drawable thirdImage = attributes.getDrawable(R.styleable.TaskStripView_third_button_image);
        String firstCaption = attributes.getString(R.styleable.TaskStripView_first_caption);
        String secondCaption = attributes.getString(R.styleable.TaskStripView_second_caption);
        String thirdCaption = attributes.getString(R.styleable.TaskStripView_third_caption);

        attributes.recycle();

        // setting task attributes
        setTaskAttributes(tasks[Values.FIRST_TASK], firstImage, firstCaption);
        setTaskAttributes(tasks[Values.SECOND_TASK], secondImage, secondCaption);
        setTaskAttributes(tasks[Values.THIRD_TASK], thirdImage, thirdCaption);

        // initiating tasks
        initiateTasks();


    }

    // Initiates all existig tasks
    protected void initiateTasks() {
        for(TaskView task : tasks) {
            task.start();
        }
    }

    private void initialize(Context context) {
        inflate(context, R.layout.task_strip_view, this);
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


    // Retrieving view state from shared preferences when app is restarted
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        SharedPreferences prefs = getContext().getSharedPreferences("TASK_STRIP_VIEW" + this.getId(),
                Context.MODE_PRIVATE);

        initiateTasks();
        for(int i = 0; i < tasks.length; i++) {
            long taskState = prefs.getLong("task_state" + i, 1);
            tasks[i].setState(taskState);
        }
    }


    // Saving view state when app is closed
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        SharedPreferences.Editor editor = this.getContext().getSharedPreferences("TASK_STRIP_VIEW"
                + this.getId(), Context.MODE_PRIVATE).edit();
        //editor.putInt("last_active_task", lastActiveTask);
        for(int i = 0; i < tasks.length; i++) {
            editor.putLong("task_state" + i, tasks[i].getTaskState());
        }

        editor.apply();
    }

}