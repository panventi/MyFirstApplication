package com.jnu.student;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView textView1, textView2;

    @SuppressLint({"SetTextI18n", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_4);

        textView1 = findViewById(R.id.hello);
        textView2 = findViewById(R.id.jnu);
        Button button = findViewById(R.id.button);

        textView1.setText("Hello");
        textView2.setText("JNU");

        button.setOnClickListener(v -> {
            String temp = textView1.getText().toString();
            textView1.setText(textView2.getText().toString());
            textView2.setText(temp);
            showToast();
            showAlertDialog();
        });
    }

    private void showToast() {
        Toast.makeText(this, "交换成功", Toast.LENGTH_SHORT).show();
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("交换成功")
                .show();
    }




    // 找到TextView控件
        //TextView textViewHelloWorld = findViewById(R.id.text_view_hello_world);

        // 获取字符串资源并设置到TextView中
        //String helloAndroid = getResources().getString(R.string.hello_android);
        //textViewHelloWorld.setText(helloAndroid);


        /*
         LinearLayout layout = new LinearLayout(this);
         layout.setOrientation(LinearLayout.VERTICAL);

         TextView textView = new TextView(this);
         textView.setText(R.string.hello_world_java);

         layout.addView(textView);

         setContentView(layout);
        */

}