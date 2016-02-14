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
    AttributeSet attrs;

    public TaskStripView(Context context) {
        super(context);
        initialize(context);
    }

    public TaskStripView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
        this.attrs = attrs;


        // getting views of each task
        tasks[Values.FIRST_TASK] = (TaskView)findViewById(R.id.firstTask);
        tasks[Values.SECOND_TASK] = (SecondTaskView)findViewById(R.id.secondTask);
        tasks[Values.THIRD_TASK] = (ThirdTaskView)findViewById(R.id.thirdTask);

        // retrieving image attributes of the tasks and initiating tasks
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.TaskStripView);
        initiateTasks(attributes);

        attributes.recycle();

    }

    // Initiates all existig tasks
    protected void initiateTasks(TypedArray attributes) {
        for(int task = Values.FIRST_TASK; task < tasks.length; task++) {
            setTaskAttributes(tasks[task], attributes.getDrawable(task),
                    attributes.getString(task + Values.NUM_OF_ATTRIBUTES));
            tasks[task].start();
        }
    }

    private void initialize(Context context) {
        inflate(context, R.layout.task_strip_view, this);
    }


    // This method sets the tasks attributes
    protected void setTaskAttributes(TaskView task, Drawable image, String caption) {

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

        initiateTasks(getContext().obtainStyledAttributes(attrs, R.styleable.TaskStripView));

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

        for(int i = Values.FIRST_TASK; i < tasks.length; i++) {
            editor.putLong("task_state" + i, tasks[i].getTaskState());
        }

        editor.apply();
    }

}