/**
 * A single com.example.eilon.taskstripview.TaskView Class
 */

package com.example.eilon.taskstripview;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;



public class TaskView extends LinearLayout {

    protected Button taskButton;
    protected int taskID = R.integer.first_task;
    protected TaskStripView parentTaskStripView;

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
        TaskStripView t = (TaskStripView)this.getParent();

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
        taskButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                handleTask();
            }
        });
    }

    // This method handles the task for each task specifically
    // To be overridden by subclasses
    protected void handleTask() {
        parentTaskStripView.manageTask(Values.SECOND_TASK);
    }

}
