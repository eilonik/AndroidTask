/**
 * A class extension for TaskView
 * containing additional functionality for The third task
 */
package com.example.eilon.taskstripview;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ThirdTaskView extends TaskView {

    protected final int STATE_1 = 1;
    protected final int STATE_2 = 2;
    protected final int STATE_3 = 3;


    protected int taskState = STATE_1;
    protected String message = "This is task 3!";
    TextView textView = (TextView)findViewById(R.id.taskCompletionTexs);
    public ThirdTaskView(Context context) {
        super(context);
    }

    public ThirdTaskView(Context context, AttributeSet attrs) {
        super(context, attrs);
        textView.setText("Share on\nFacebook");
        textView.setVisibility(View.VISIBLE);

    }

    @Override
    protected void handleTask() {
        switch (taskState) {

            case STATE_1:
                taskState = STATE_2;
                textView.setText("Share on\nTweeter");
                ProgressBar progressBar = (ProgressBar)findViewById(R.id.taskProgressBar);
                progressBar.setVisibility(View.INVISIBLE);


                // Sharing on Facebook
                Intent facebookStatus = new Intent(Intent.ACTION_VIEW);
                facebookStatus.setData(Uri.parse("http://m.facebook.com/sharer.php?u="
                        + Uri.encode(message)));
                parentTaskStripView.getContext().startActivity(facebookStatus);
                break;

            case STATE_2:
                taskState = STATE_3;
                textView.setText("Send intent");

                // Sharing on tweeter
                Intent tweet = new Intent(Intent.ACTION_VIEW);
                tweet.setData(Uri.parse("http://twitter.com/?status=" + Uri.encode(message)));
                parentTaskStripView.getContext().startActivity(tweet);
                break;

            case STATE_3:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, message);
                intent.setType("text/plain");
                parentTaskStripView.getContext().startActivities(new Intent[]{intent});
                break;
        }
    }

    // get the task state
    protected int getTaskState() {
        return this.taskState;
    }

    // sets the task state externally
    protected void setTaskState(int state) {
        this.taskState = state;
    }

    // enforces state caption
    protected void enforceCaption(int state) {
        switch(state) {
            case STATE_1:
                textView.setText("Share on\nFacebook");
                break;

            case STATE_2:
                textView.setText("Share on\nTweeter");
                break;

            case STATE_3:
                textView.setText("Send intent");
                break;

        }
    }
}
