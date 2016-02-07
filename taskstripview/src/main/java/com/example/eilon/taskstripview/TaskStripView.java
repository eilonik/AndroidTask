/**
 * A TaskStripView composed of 3 single TaskViews
 */

package com.example.eilon.taskstripview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.LinearLayout;


public class TaskStripView extends LinearLayout {
    public TaskStripView(Context context) {
        super(context);
        initialize(context);
    }

    public TaskStripView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);

        // getting views of each task
        TaskView firstTask = (TaskView)findViewById(R.id.firstTask);
        TaskView secondTask = (TaskView)findViewById(R.id.secondTask);
        TaskView thirdTask = (TaskView)findViewById(R.id.thirdTask);

        // retrieving image attributes of the tasks
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.TaskStripView);
        Drawable firstImage = attributes.getDrawable(R.styleable.TaskStripView_first_button_image);
        Drawable secondImage = attributes.getDrawable(R.styleable.TaskStripView_second_button_image);
        Drawable thirdImage = attributes.getDrawable(R.styleable.TaskStripView_third_button_image);

        attributes.recycle();

        // setting task images
        setImage(firstTask, firstImage);
        setImage(secondTask, secondImage);
        setImage(thirdTask, thirdImage);

    }

    private void initialize(Context context) {
        inflate(context, R.layout.task_strip_view, this);

    }

    // Setters

    // Sets the tasks images
    private void setImage(TaskView task, Drawable image) {
        if(image != null) {
            task.setButtonImage(image);
        }

    }
}
