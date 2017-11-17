package com.example.administrator.litepaldemo.adapter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.litepaldemo.R;
import com.example.administrator.litepaldemo.bean.Person;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;

import static com.example.administrator.litepaldemo.constant.Constant.PERSON_SQL_SEARCH_ALL;
import static com.example.administrator.litepaldemo.constant.Constant.PERSON_SQL_SEARCH_BY_ID;
import static com.example.administrator.litepaldemo.constant.Constant.PERSON_SQL_SEARCH_BY_NAME;

/**
 * Created by Administrator on 2017\11\16 0016.
 */

public class MyBaseAdapter extends BaseAdapter {

    public ArrayList<Person> persons;
    public Context context;

    public MyBaseAdapter(final ArrayList<Person> pers, Context context) {
        this.persons = pers;
        this.context = context;

        IntentFilter intentFilter = new IntentFilter(PERSON_SQL_SEARCH_ALL);
        IntentFilter intentFilter2 = new IntentFilter(PERSON_SQL_SEARCH_BY_NAME);
        IntentFilter intentFilter3 = new IntentFilter(PERSON_SQL_SEARCH_BY_ID);

        context.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(persons == null){
                    return;
                }
                persons.clear();
                persons = (ArrayList<Person>) DataSupport.findAll(Person.class);
                notifyDataSetChanged();
            }
        }, intentFilter);

        context.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(persons == null) return;

                String name = intent.getStringExtra("name");
                if(name == null || name.equals("")) return;

                persons.clear();
                persons = (ArrayList<Person>) DataSupport.where("name = ?",name).find(Person.class);
                notifyDataSetChanged();
            }
        }, intentFilter2);

        context.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(persons == null) return;

                int id = intent.getIntExtra("id",0);

                persons.clear();
                persons = (ArrayList<Person>) DataSupport.where("id = ?", id+"").find(Person.class);
                notifyDataSetChanged();
            }
        }, intentFilter3);

    }

    @Override
    public int getCount() {
        if (persons == null) return 0;
        return persons.size();
    }

    @Override
    public Object getItem(int position) {
        return persons.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, null);
            holder.id_item = (TextView) convertView.findViewById(R.id.id_item);
            holder.name = (TextView) convertView.findViewById(R.id.name_item);
            holder.age = (TextView) convertView.findViewById(R.id.age_item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Person person = persons.get(position);
        holder.id_item.setText(String.valueOf(person.getId()));
        holder.name.setText(person.getName());
        holder.age.setText(String.valueOf(person.getAge()));

        return convertView;
    }

    public static class ViewHolder {
        public TextView id_item;
        public TextView name;
        public TextView age;
    }
}
