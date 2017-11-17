package com.example.administrator.litepaldemo.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.litepaldemo.R;
import com.example.administrator.litepaldemo.bean.Person;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;

import static com.example.administrator.litepaldemo.constant.Constant.REQUEST_CODE_UPDATE;


/**
 * Created by Administrator on 2017\11\16 0016.
 */

public class UpdateActivity extends AppCompatActivity {

    private ArrayList<Person> persons;
    private Button search_button;
    private Button save_update;
    private EditText search_id_et;
    private TextView id_item;
    private EditText name_item;
    private EditText age_item;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        search_button = (Button) findViewById(R.id.search_button);
        save_update = (Button) findViewById(R.id.save_update);
        search_id_et = (EditText) findViewById(R.id.search_id_et);
        id_item = (TextView) findViewById(R.id.id_item);
        name_item = (EditText) findViewById(R.id.name_item);
        age_item = (EditText) findViewById(R.id.age_item);

        //根据 id 号查询出本条item的信息
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = search_id_et.getText().toString();
                if (id == null || id.equals("")) {
                    Toast.makeText(UpdateActivity.this, "can not be null", Toast.LENGTH_SHORT).show();
                    return;
                }

                persons = (ArrayList<Person>) DataSupport.where("id = ?", id + "").find(Person.class);
                if(persons==null || persons.size()==0) {
                    Toast.makeText(UpdateActivity.this, "can not find", Toast.LENGTH_SHORT).show();
                    return;
                }

                id_item.setText(String.valueOf(persons.get(0).getId()));
                name_item.setText(persons.get(0).getName());
                age_item.setText(String.valueOf(persons.get(0).getAge()));
            }
        });

        // 针对查询出来的这一条进行修改
        save_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id_item.getText() == null || name_item.getText() == null || age_item.getText() == null) {
                    Toast.makeText(UpdateActivity.this, "can not be null", Toast.LENGTH_SHORT).show();
                }

                ContentValues values = new ContentValues();
                values.put("name", name_item.getText().toString());
                values.put("age", age_item.getText().toString());
                if(persons==null || persons.size()==0) {
                    Toast.makeText(UpdateActivity.this, "can not find", Toast.LENGTH_SHORT).show();
                    return;
                }
                DataSupport.update(Person.class, values, persons.get(0).getId());

                backToMain();
            }
        });
    }

    public void backToMain() {
        Intent intent = new Intent();
        this.setResult(REQUEST_CODE_UPDATE, intent);
        this.finish();
    }


}
