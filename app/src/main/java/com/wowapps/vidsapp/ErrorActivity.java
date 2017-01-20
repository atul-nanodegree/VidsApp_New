package com.wowapps.vidsapp;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by atul.
 */
public class ErrorActivity extends Activity {

    private TextView dlgHeader, dlgBody;
    private Button dlgEnableBtn;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        setContentView(R.layout.erroactivity);

        dlgHeader = (TextView) findViewById(R.id.error_text1);
        dlgBody = (TextView) findViewById(R.id.error_text2);
        dlgEnableBtn = (Button) findViewById(R.id.error_button);
        dlgEnableBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("market://details?id=" + "com.google.android.youtube");
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + "com.google.android.youtube")));
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();


    }
    /*@Override
    public void onBackPressed() {
    }*/

}
