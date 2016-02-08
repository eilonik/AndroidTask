/**
 * A class extension for TaskView
 * containing additional functionality for The second task
 */
package com.example.eilon.taskstripview;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;

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
        if(clickTimeCheck()) {
            ProgressBar progressBar = (ProgressBar)findViewById(R.id.taskProgressBar);
            progressBar.setVisibility(View.INVISIBLE);
            parentTaskStripView.manageTask(Values.THIRD_TASK);
        }

        else {
            // Prompting a dialog when 24 hours haven't passed
            new AlertDialog.Builder(getContext())
                    .setTitle("Task not available!")
                    .setMessage("please try again tomorrow")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }
}
