package com.jnu.student;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
//import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 找到TextView控件
        TextView textViewHelloWorld = findViewById(R.id.text_view_hello_world);

        // 获取字符串资源并设置到TextView中
        String helloAndroid = getResources().getString(R.string.hello_android);
        textViewHelloWorld.setText(helloAndroid);


        /*
         LinearLayout layout = new LinearLayout(this);
         layout.setOrientation(LinearLayout.VERTICAL);

         TextView textView = new TextView(this);
         textView.setText(R.string.hello_world_java);

         layout.addView(textView);

         setContentView(layout);
        */
    }
}