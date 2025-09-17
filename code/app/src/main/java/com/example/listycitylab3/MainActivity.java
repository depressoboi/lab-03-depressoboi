package com.example.listycitylab3;

import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements AddCityFragment.AddCityDialogListener {

    private ArrayList<City> dataList;
    private ListView cityList;
    private CityArrayAdapter cityAdapter;

    @Override
    public void addCity(City city) {
        dataList.add(city);
        cityAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] cities = { "Edmonton", "Vancouver", "Toronto" };

        String[] provinces = { "AB", "BC", "ON" };

        dataList = new ArrayList<City>();
        for(int i = 0; i < cities.length; i++){
            dataList.add(new City(cities[i], provinces[i]));
        }
        
        cityList = findViewById(R.id.city_list);
        cityAdapter = new CityArrayAdapter(this, dataList);
        cityList.setAdapter(cityAdapter);

        FloatingActionButton fab = findViewById(R.id.button_add_city);
        fab.setOnClickListener(view -> {
            AddCityFragment.newAddInstance().show(getSupportFragmentManager(), "Add City");
        });

        cityList.setOnItemClickListener((AdapterView<?> parent, android.view.View view, int position, long id) -> {
            City selected = dataList.get(position);
            AddCityFragment.newEditInstance(selected, position).show(getSupportFragmentManager(), "Edit City");
        });
    }

    @Override
    public void editCity(int position, City updatedCity){
        if (position >= 0 && position < dataList.size()) {
            dataList.set(position, updatedCity);
            cityAdapter.notifyDataSetChanged();
        }
    }
}