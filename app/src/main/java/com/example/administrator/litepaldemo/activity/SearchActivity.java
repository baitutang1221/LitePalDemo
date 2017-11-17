package com.example.administrator.litepaldemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.administrator.litepaldemo.R;

import static com.example.administrator.litepaldemo.constant.Constant.REQUEST_CODE_SEARCH;


/**
 * Created by Administrator on 2017\11\16 0016.
 */

public class SearchActivity extends AppCompatActivity {

    private Button search_all_button;
    private Button search_name_button;
    private EditText name_editText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        search_all_button = (Button) findViewById(R.id.search_all_button);
        search_name_button = (Button) findViewById(R.id.search_name_button);
        name_editText = (EditText) findViewById(R.id.name_editText);

        //查询所有的人
        search_all_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("type", "all");
                SearchActivity.this.setResult(REQUEST_CODE_SEARCH, intent);
                SearchActivity.this.finish();
            }
        });


        //查询姓名为xxx的人
        search_name_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameString = name_editText.getText().toString();
                if (nameString == null || nameString == "") return;

                Intent intent = new Intent();
                intent.putExtra("type", nameString);
                SearchActivity.this.setResult(REQUEST_CODE_SEARCH, intent);
                SearchActivity.this.finish();
            }
        });
    }


}
