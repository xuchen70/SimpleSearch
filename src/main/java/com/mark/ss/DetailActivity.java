package com.mark.ss;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        TextView text = (TextView) findViewById(R.id.text_detail);
        String content = getIntent().getStringExtra("content");
        if (!TextUtils.isEmpty(content)){
            text.setText(content);
        }
    }
}
