package de.szut.passkeeper.activity;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import de.szut.passkeeper.R;
import de.szut.passkeeper.model.Security;

public class NotificationActivity extends Activity{

    private Button btnCopyUsername;
    private Button btnCopyPassword;
    private Button btnBack;
    Bundle extras;
    String name;
    String pwd;
    //String loginData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }/*
        setContentView(R.layout.notification_layout);

        extras = this.getIntent().getExtras();
        pwd =  extras.getString("PWD");
        name =  extras.getString("NAME");
        //String loginData = this.getIntent().getData().toString();



        btnCopyUsername = (Button) findViewById(R.id.btnCopyUsername);
        btnCopyUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("label", name);
                clipboard.setPrimaryClip(clip);
                finish();
            }
        });
        btnCopyPassword = (Button) findViewById(R.id.btnCopyPassword);
        btnCopyPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("label", pwd);
                clipboard.setPrimaryClip(clip);
                finish();
            }
        });
        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
    @Override
    protected void onNewIntent(Intent intent){
        //loginData = intent.getData().toString();
        extras =  intent.getExtras();
    }*/
}