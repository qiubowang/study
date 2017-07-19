package com.example.wangqiubo.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class SharedPrefereceActivity extends AppCompatActivity {
    private Button mButton = null;
    private TextView view1 = null;
    private TextView view2 = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_preferece);

        createSharedPreferrence();

        mButton = (Button)findViewById(R.id.share_button);
        mButton.setOnClickListener((v) -> {
            SharedPreferences per = getSharedPreferences("bobo",Context.MODE_PRIVATE);
            view1.setText(per.getString("first","第一个"));
            view2.setText(per.getString("second","第二个"));
            view1.refreshDrawableState();
            view2.refreshDrawableState();
        });

        view1 = (TextView)findViewById(R.id.text_view1);
        view2 = (TextView)findViewById(R.id.text_view2);
    }

    public void  createSharedPreferrence(){
        SharedPreferences preferences = getSharedPreferences("bobo",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("first", "one");
        editor.putString("second", "two");
        editor.commit();
    }
}
