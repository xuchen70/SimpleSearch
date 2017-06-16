package com.mark.ss;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        TextView text = (TextView) findViewById(R.id.text_detail);
        View layout = findViewById(R.id.layout_detail);
        layout.setOnClickListener(this);
        String content = getIntent().getStringExtra("content");
        if (!TextUtils.isEmpty(content)){
            text.setText(content);
        }
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
