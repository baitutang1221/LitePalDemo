package com.example.administrator.litepaldemo.activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.administrator.litepaldemo.R;
import com.example.administrator.litepaldemo.adapter.MyBaseAdapter;
import com.example.administrator.litepaldemo.bean.Person;
import com.example.administrator.litepaldemo.tools.CacheManager;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.ArrayList;

import static com.example.administrator.litepaldemo.constant.Constant.PERSON_SQL_SEARCH_ALL;
import static com.example.administrator.litepaldemo.constant.Constant.PERSON_SQL_SEARCH_BY_NAME;
import static com.example.administrator.litepaldemo.constant.Constant.REQUEST_CODE_DELETE;
import static com.example.administrator.litepaldemo.constant.Constant.REQUEST_CODE_SEARCH;
import static com.example.administrator.litepaldemo.constant.Constant.REQUEST_CODE_UPDATE;

/**
 * LitePal 数据库的增删改查
 *
 */

public class MainActivity extends AppCompatActivity {

    private Button search;
    private Button insert;
    private Button delete;
    private Button update;

    private ListView listview;
    private ArrayList<Person> datas;
    private MyBaseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SQLiteDatabase db = Connector.getDatabase();
        datas = new ArrayList<>();
        initViews();
    }

    public void initViews() {
        search = (Button) findViewById(R.id.search);
        insert = (Button) findViewById(R.id.insert);
        delete = (Button) findViewById(R.id.delete);
        update = (Button) findViewById(R.id.update);

        listview = (ListView) findViewById(R.id.listview);
        adapter = new MyBaseAdapter(datas, this);
        CacheManager.getInstance().setAdapter(adapter); //保存一下
        listview.setAdapter(adapter);

        // 查询
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SEARCH);
            }
        });

        // 删除
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DeleteActivity.class);
                startActivityForResult(intent, REQUEST_CODE_DELETE);
            }
        });

        // 增加item
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //方式一
//                Person person1 = new Person(1, "Tom", 12);
//                person1.save();
//                Person person2 = new Person(2, "Jery", 14);
//                person2.save();
//                Person person3 = new Person(3, "Mark", 16);
//                person3.save();
//                Person person4 = new Person(4, "Cery", 18);
//                person4.save();

                //方式二
                Person person1 = new Person(1, "Tom", 12);
                Person person2 = new Person(2, "Jery", 14);
                Person person3 = new Person(3, "Mark", 16);
                Person person4 = new Person(4, "Cery", 18);
                datas.add(person1);
                datas.add(person2);
                datas.add(person3);
                datas.add(person4);
                DataSupport.saveAll(datas);

                mySendBroadcast(PERSON_SQL_SEARCH_ALL, "");
            }
        });

        // 修改字段
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
                startActivityForResult(intent, REQUEST_CODE_UPDATE);
            }
        });

    }

    public void mySendBroadcast(String action, String name) {
        Intent intent = new Intent(action);
        if (!name.equals("")) {
            intent.putExtra("name", name);
        }
        sendBroadcast(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            //查询
            case 111:
                if (data == null) return;
                String type = data.getStringExtra("type");
                switch (type) {
                    case "all":
                        mySendBroadcast(PERSON_SQL_SEARCH_ALL, "");
                        break;
                    default:
                        mySendBroadcast(PERSON_SQL_SEARCH_BY_NAME, type);
                        break;
                }
                break;
            //删除后显示
            case 222:
                mySendBroadcast(PERSON_SQL_SEARCH_ALL, "");
                break;
            //修改后显示
            case 333:
                mySendBroadcast(PERSON_SQL_SEARCH_ALL, "");
                break;
        }

    }


}
