/**
 * A single com.example.eilon.taskstripview.TaskView Class
 */

package com.example.eilon.taskstripview;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.LinearLayout;



public class TaskView extends LinearLayout {

    Button taskButton;

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
     * @param Drawable image
     */
    public void setButtonImage(Drawable image) {
        taskButton.setBackground(image);
        taskButton.setText("");
    }
}
