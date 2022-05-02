package com.shaoye.chatmanager;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.shaoye.chatmanager.constant.MessageConstant;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!Settings.canDrawOverlays(this)) {
            ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (!Settings.canDrawOverlays(MainActivity.this)) {
                        Toast.makeText(MainActivity.this, "授权失败", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "授权成功", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
            launcher.launch(intent);
        }

        Button sendMessage = findViewById(R.id.send_vivo_broadcast);
        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendVivoMessage();
            }
        });
    }

    @SuppressWarnings("WrongConstant")
    private void sendVivoMessage() {
        Log.e(TAG, "sendVivoMessage: ");
        Intent intent = new Intent(MessageConstant.APPLICATION_NOTIFICATION);
        intent.putExtra(MessageConstant.PACKAGE_NAME, getPackageName());
        intent.putExtra(MessageConstant.CLASS_NAME, "com.shaoye.chatmanager.MainActivity");
        intent.putExtra(MessageConstant.APPLICATION_NOTIFICATION_NUMBER, 2);
        intent.addFlags(0x01000000);
        sendBroadcast(intent);
    }

}