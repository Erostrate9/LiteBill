package com.syx.litebill;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView settingTv1;
    private ImageView backIv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        settingTv1=findViewById(R.id.setting_tv_1);
        backIv=findViewById(R.id.setting_iv_back);

        settingTv1.setOnClickListener(this);
        backIv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.setting_tv_1:
                Toast.makeText(this, "都说了没什么可设置的！", Toast.LENGTH_SHORT).show();
                break;
            case R.id.setting_iv_back:
                finish();
                break;
        }
    }
}