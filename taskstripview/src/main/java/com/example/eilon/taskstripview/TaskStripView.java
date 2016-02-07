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
        String firstCaption = attributes.getString(R.styleable.TaskStripView_first_caption);
        String secondCaption = attributes.getString(R.styleable.TaskStripView_second_caption);
        String thirdCaption = attributes.getString(R.styleable.TaskStripView_third_caption);
        attributes.recycle();

        // setting task attributes
        setTaskAttributes(firstTask, firstImage, firstCaption);
        setTaskAttributes(secondTask, secondImage, secondCaption);
        setTaskAttributes(thirdTask, thirdImage, thirdCaption);

    }

    private void initialize(Context context) {
        inflate(context, R.layout.task_strip_view, this);

    }

    // Setters

    // Sets the tasks attributes
    private void setTaskAttributes(TaskView task, Drawable image, String caption) {

        if(image != null) {
            task.setButtonImage(image);
        }

        if(caption != null) {
            task.setTaskCaption(caption);
        }
    }
}
