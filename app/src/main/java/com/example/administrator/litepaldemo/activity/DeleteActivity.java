package com.example.administrator.litepaldemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.administrator.litepaldemo.R;
import com.example.administrator.litepaldemo.bean.Person;
import com.example.administrator.litepaldemo.tools.CacheManager;

import org.litepal.crud.DataSupport;

import static com.example.administrator.litepaldemo.constant.Constant.REQUEST_CODE_DELETE;


/**
 * Created by Administrator on 2017\11\16 0016.
 */

public class DeleteActivity extends AppCompatActivity {

    private Button clear_button;
    private Button delete_id_button;
    private EditText id_editText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        clear_button = (Button) findViewById(R.id.clear_button);
        delete_id_button = (Button) findViewById(R.id.delete_id_button);
        id_editText = (EditText) findViewById(R.id.id_editText);

        //清空数据库
        clear_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataSupport.deleteAll(Person.class);
                CacheManager.getInstance().getAdapter().notifyDataSetChanged();
                backToMain();
            }
        });


        //根据id删除某一个
        delete_id_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idString = id_editText.getText().toString();
                long id = Long.valueOf(idString);
                if (idString == null || idString == "") return;

                DataSupport.delete(Person.class, id);
                CacheManager.getInstance().getAdapter().notifyDataSetChanged();
                backToMain();
            }
        });
    }

    public void backToMain() {
        Intent intent = new Intent();
        DeleteActivity.this.setResult(REQUEST_CODE_DELETE, intent);
        DeleteActivity.this.finish();
    }

}
