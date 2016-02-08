/**
 * A class extension for TaskView
 * containing additional functionality for The second task
 */
package com.example.eilon.taskstripview;

import android.content.Context;
import android.util.AttributeSet;

public class SecondTaskView extends TaskView {
    public SecondTaskView(Context context) {
        super(context);
    }

    public SecondTaskView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    protected void handleTask() {
        parentTaskStripView.manageTask(Values.THIRD_TASK);
    }
}
