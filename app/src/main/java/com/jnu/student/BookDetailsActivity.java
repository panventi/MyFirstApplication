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
        Button buttonOk = (Button) findViewById(R.id.confirm_button);
        Button buttonCancel = (Button) findViewById(R.id.cancel_button);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editBookItemName = findViewById(R.id.edittext_item_name);
                EditText editBookItemPrice = findViewById(R.id.edittext_item_img);
                // 在需要返回数据的地方创建一个Intent对象
                Intent returnIntent = new Intent();
                // 将数据放入Intent中
                returnIntent.setAction("add");
                //returnIntent.putExtra("type","add");
                returnIntent.putExtra("name", editBookItemName.getText().toString());
                returnIntent.putExtra("imgId", editBookItemPrice.getText().toString());
                // 设置结果码和返回的Intent对象
                setResult(RESULT_OK, returnIntent);
                // 结束当前Activity
                finish();
            }
        });

        // 设置取消按钮的点击事件
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 关闭当前Activity
                finish();
            }
        });
    }
}