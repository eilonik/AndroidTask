/**
 * A class extension for TaskView
 * containing additional functionality for The third task
 */
package com.example.eilon.taskstripview;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
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
                if(isPackageInstalled("com.facebook.katana", parentTaskStripView.getContext())) {
                    //TODO
                }

                else {
                    shareStatusOnWeb("http://m.facebook.com/sharer.php?u=" + Uri.encode(message));
                }

                break;

            case STATE_2:
                taskState = STATE_3;
                textView.setText("Send intent");

                // Sharing on tweeter
                if(isPackageInstalled("com.twitter.android", parentTaskStripView.getContext()))
                {
                    shareStatusOnApp("com.twitter.android",
                            "com.twitter.android.composer.ComposerActivity", message);

                }
                else
                {
                    shareStatusOnWeb("https://twitter.com/intent/tweet?text=" + message);
                }

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
    // in case of resume
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


    // This method checks if a package is installed on the device
    protected boolean isPackageInstalled(String packageName, Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            Log.e("TWITTER","INSTALLED");
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("TWITTER","NOT INSTALLED");
            return false;
        }
    }

    // This method shares a status on a specified app
    protected void shareStatusOnApp(String packageName, String className, String message) {
        Intent status;

        if(className == "") {
            status = new Intent(Intent.ACTION_SEND);
            status.setPackage(packageName);
            status.putExtra(Intent.EXTRA_SUBJECT, message);
            status.setType("text/plain");
        }
        else {
            status = new Intent(Intent.ACTION_VIEW);
            status.setClassName(packageName, className);
            status.setType("text/*");
        }
        Log.e("MSG", message);
        status.putExtra(android.content.Intent.EXTRA_TEXT, message);
        parentTaskStripView.getContext().startActivities(new Intent[]{status});
    }

    // This method shares a status through a link in case the target app is not installed
    protected void shareStatusOnWeb(String shareUrl) {
        Uri uri = Uri.parse(shareUrl);
        Intent status = new Intent(Intent.ACTION_VIEW, uri);
        parentTaskStripView.getContext().startActivities(new Intent[]{status});
    }

}
