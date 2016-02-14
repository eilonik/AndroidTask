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
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ThirdTaskView extends TaskView {

    protected final int STATE_1 = 1;
    protected final int STATE_2 = 2;
    protected final int STATE_3 = 3;
    protected final String FACEBOOK_PACKAGE = "com.facebook.katana";
    protected final String TWITTER_PACKAGE = "com.twitter.android";
    protected final String TWITTER_SHARE_ACTIVITY = "com.twitter.android.composer.ComposerActivity";
    protected final String FACEBOOK_SHARE_URL = "http://m.facebook.com/sharer.php?u=";
    protected final String TWITTER_SHARE_URL = "https://twitter.com/intent/tweet?text=";
    protected final String FACEBOOK_URL = "www.facebook.com";
    protected final String SHARE_MESSAGE = "This is task 3!";

    protected int taskState = STATE_1;
    TextView textView = (TextView)findViewById(R.id.taskCompletionTexs);
    public ThirdTaskView(Context context) {
        super(context);
    }

    public ThirdTaskView(Context context, AttributeSet attrs) {
        super(context, attrs);
        textView.setVisibility(View.VISIBLE);
        enforceCaption(taskState);
        enforceImage(taskState);

    }

    @Override
    protected void handleTask() {

        switch (taskState) {

            case STATE_1:
                taskState = STATE_2;
                ProgressBar progressBar = (ProgressBar)findViewById(R.id.taskProgressBar);
                progressBar.setVisibility(View.INVISIBLE);


                // Sharing on Facebook
                if(isPackageInstalled(FACEBOOK_PACKAGE, parentTaskStripView.getContext())) {

                    shareStatusOnApp(FACEBOOK_PACKAGE, "", FACEBOOK_URL,
                            Intent.ACTION_SEND, "text/plain");
                }

                else {
                    shareStatusOnWeb(FACEBOOK_SHARE_URL + FACEBOOK_URL);
                }

                break;

            case STATE_2:
                taskState = STATE_3;
                textView.setText("Send intent");

                // Sharing on tweeter
                if(isPackageInstalled(TWITTER_PACKAGE, parentTaskStripView.getContext()))
                {
                    shareStatusOnApp(TWITTER_PACKAGE,
                            TWITTER_SHARE_ACTIVITY, SHARE_MESSAGE,
                            Intent.ACTION_VIEW, "text/*");

                }
                else
                {
                    shareStatusOnWeb(TWITTER_SHARE_URL + SHARE_MESSAGE);
                }

                break;

            case STATE_3:

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, SHARE_MESSAGE);
                intent.setType("text/plain");
                parentTaskStripView.getContext().startActivities(new Intent[]{intent});
                break;
        }

        // Setting image and caption after click handle
        enforceCaption(taskState);
        enforceImage(taskState);
    }

    // get the task state
    @Override
    protected long getTaskState() {
        return (long)this.taskState;
    }

    // sets the task state externally
    @Override
    protected void setState(long state) {
        int newState = (int)state;
        this.taskState = newState;
        enforceCaption(newState);
        enforceImage(newState);

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
                textView.setText("Invite friends");
                break;

        }
    }

    // enforces state image
    // in case of resume
    protected void enforceImage(int state) {

        switch(state) {
            case STATE_1:
                setButtonImage(getResources().getDrawable(R.mipmap.facebook_icon));
                break;

            case STATE_2:
                setButtonImage(getResources().getDrawable(R.mipmap.twitter_icon));
                progressBar.setVisibility(View.INVISIBLE);
                break;

            case STATE_3:
                setButtonImage(getResources().getDrawable(R.mipmap.intent_icon));
                progressBar.setVisibility(View.INVISIBLE);
                break;

        }
    }


    // This method checks if a package is installed on the device
    protected boolean isPackageInstalled(String packageName, Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    // This method shares a status on a specified app
    protected void shareStatusOnApp(String packageName, String className, String message,
                                    String action, String contentType) {
        Intent status = new Intent(action);

        if(className == "") {
            status.setPackage(packageName);
        }
        else {
            status.setClassName(packageName, className);
        }

        status.setType(contentType);
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