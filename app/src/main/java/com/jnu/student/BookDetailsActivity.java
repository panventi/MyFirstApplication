package com.jnu.student;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class BookDetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        Button buttonOk = (Button) findViewById(R.id.button_item_details_ok);
        Button buttonCancel = (Button) findViewById(R.id.button_item_details_cancel);
        EditText editText = (EditText) findViewById(R.id.edittext_item_name);
        //获取到与当前活动相关联的 Intent 对象
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.getAction().equals("modify")) {
                //如果是修改，需要获取到item的title值放入编辑框
                editText.setText(intent.getStringExtra("title"));
            }
            buttonOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 将数据放入Intent中
                    intent.putExtra("title", editText.getText().toString());
                    // 设置结果码和返回的Intent对象
                    setResult(Activity.RESULT_OK, intent);
                    // 结束当前Activity
                    finish();
                }
            });
            buttonCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 点击cancel直接结束当前Activity
                    finish();
                }
            });
        }
    }
}