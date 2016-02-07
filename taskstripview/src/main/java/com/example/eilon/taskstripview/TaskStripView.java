/**
 * A TaskStripView composed of 3 single TaskViews
 */

package com.example.eilon.taskstripview;
import android.content.Context;
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
    }

    private void initialize(Context context) {
        inflate(context, R.layout.task_strip_view, this);
    }
}
