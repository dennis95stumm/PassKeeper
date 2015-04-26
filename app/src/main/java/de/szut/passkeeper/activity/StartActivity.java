package de.szut.passkeeper.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.util.Vector;

import de.szut.passkeeper.R;
import de.szut.passkeeper.interfaces.IUserProperty;
import de.szut.passkeeper.utility.DataLoaderTask;


public class StartActivity extends Activity {
    private Vector<IUserProperty> vectorUserDatabaseProperty;
    private Handler mHandler;
    public static final int TASK_COMPLETE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_layout);
        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message inputMessage) {
                DataLoaderTask task = (DataLoaderTask)inputMessage.obj;
                vectorUserDatabaseProperty = task.getPropertyVector();
                setStartActivity();
            }
        };
        DataLoaderTask dataLoaderTask = new DataLoaderTask(this);
        dataLoaderTask.startTask();
    }

    private void setStartActivity() {
        if (vectorUserDatabaseProperty.size() == 0) {
            Intent intent = new Intent(this, CreateDatabaseActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, ListDatabaseActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    public void handleState(DataLoaderTask dataLoaderTask, int state) {
        switch (state) {
            case TASK_COMPLETE:
                Message completeMessage = mHandler.obtainMessage(state, dataLoaderTask);
                completeMessage.sendToTarget();
                break;
        }
    }
}
