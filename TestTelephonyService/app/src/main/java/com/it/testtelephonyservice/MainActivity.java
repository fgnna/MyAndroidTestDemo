package com.it.testtelephonyservice;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.CallLog;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import org.w3c.dom.Text;

import static android.R.attr.start;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.callout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    ContentResolver resolver = getContentResolver();
                    /* 测试其他应用删除系统通话记录是否会影响到 监听服务的判断，，
                        实测结果 是不会*/
                    try {
                            try {
                                int res = resolver.delete(CallLog.Calls.CONTENT_URI,
                                        CallLog.Calls.NUMBER + "=?", new String[]{"11111"});
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                /*
                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                    intent.putExtra("555555555", "dddddddddddddd");
                    startActivity(intent);
                */
            }
        });

        String number = getIntent().getStringExtra("555555555");
        if (!TextUtils.isEmpty(number)) {
            Intent intent = new Intent(getApplicationContext(), CallingServiceImpl.class);
            intent.putExtra("appoutcall", true);
            startService(intent);

            Intent intent1 = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
            startActivity(intent1);
        }

    }
}
