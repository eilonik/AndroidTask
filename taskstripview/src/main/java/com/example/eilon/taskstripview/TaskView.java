package com.example.eilon.taskstripview; /**
 * A single com.example.eilon.taskstripview.TaskView Class
 */

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;


public class TaskView extends LinearLayout {
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
    }
}
