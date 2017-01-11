package com.vidsapp;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.vidsapp.util.VidsAppAds;

/**
 * Created by atul.
 */
public class FeedbackActivity extends BaseActivity {


    private EditText mEditText;
    private Button mFeedbackButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback);

        //Displaying banner ads at bottom of screen
        VidsAppAds vidsAppAds = new VidsAppAds(this);
        vidsAppAds.bannerAds(getResources().getString(R.string.banner_home_footer_favouritevideo));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setActionbarTitle("Feedback", false, R.id.toolbar);
        mEditText = (EditText) findViewById(R.id.feedback_edit);
        mEditText.setSelection(mEditText.getText().length());
        mFeedbackButton = (Button) findViewById(R.id.feedback_button);
        mFeedbackButton.setOnClickListener(new View.OnClickListener() {
            //@Override
            public void onClick(View v) {

                if (!NetworkUtil.isConnected(FeedbackActivity.this)) {
                    Snackbar.make(mFeedbackButton, "No internet connection.Check your connection and try again", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                } else {
                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + "wowappsforyou@gmail.com"));
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "VidsApp Feedback");
                    emailIntent.putExtra(Intent.EXTRA_TEXT, "Feedback goes here!!" + "\n" + "\n" + mEditText.getText().toString());
                    try {
                        startActivityForResult(Intent.createChooser(emailIntent, "Send Feedback to VidsApp..."), 101);
                        finish();
                    } catch (ActivityNotFoundException ex) {
                        Snackbar.make(mFeedbackButton, "There is no email client installed.", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 101) {
           /* if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(FeedbackActivity.this, "Order sent successfully", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(FeedbackActivity.this, "Order not sent.Please check your Internet settings", Toast.LENGTH_SHORT).show();

            }*/
        }

    }
}