package com.example.shopcom;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class OrdersFragment extends Fragment {
    Context context;
    RecyclerView recyclerView;
    CustomAdapterOrder customAdapter;
    MyDatabaseHelper myDB;
    ArrayList<String> name, description, price, category,image;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = container.getContext();
        View view = inflater.inflate(R.layout.fragment_orders, container, false);
        myDB = new MyDatabaseHelper(context);
        recyclerView = view.findViewById(R.id.recyclerview);
        name = new ArrayList<>();
        description = new ArrayList<>();
        price = new ArrayList<>();
        category = new ArrayList<>();
        image = new ArrayList<>();
        String username = SharedData.sharedString;
        storeDataInArrays(username);
        customAdapter = new CustomAdapterOrder(context, name, description, price, image);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        return view;
    }
    void storeDataInArrays(String username) {
        Cursor cursor = myDB.readOrderData(username);

        if (cursor.getCount() == 0) {
            Toast.makeText(context, "No Past Orders", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                name.add(cursor.getString(0));
                price.add(cursor.getString(2));
                description.add(cursor.getString(3));
                image.add(cursor.getString(4));
            }
        }
        cursor.close();
    }
}