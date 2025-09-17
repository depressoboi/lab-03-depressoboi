package com.example.listycitylab3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AddCityFragment extends DialogFragment {
    interface AddCityDialogListener {
        void addCity(City city);
        void editCity(int position, City updatedCity);
    }
    private AddCityDialogListener listener;

    // args
    private static final String ARG_CITY = "arg_city";
    private static final String ARG_POSITION = "arg_position";

    // state from args
    @Nullable private  City city;
    private int position = -1;
    private boolean isEditMode = false;


    public static AddCityFragment newAddInstance() {
        AddCityFragment f = new AddCityFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, -1);
        f.setArguments(b);
        return f;
    }

    public static AddCityFragment newEditInstance(City city, int position){
        AddCityFragment f = new AddCityFragment();
        Bundle b = new Bundle();
        b.putSerializable(ARG_CITY, city);
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AddCityDialogListener) {
            listener = (AddCityDialogListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement AddCityDialogListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstances){
        super.onCreate(savedInstances);
        Bundle args = getArguments();
        if (args != null){
            Object ser = args.getSerializable(ARG_CITY);
            if(ser instanceof City) this.city = (City)ser;
            this.position = args.getInt(ARG_POSITION, -1);
            this.isEditMode = (this.city != null && this.position >= 0);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_city, null);
        EditText editCity = view.findViewById(R.id.edit_city_text);
        EditText editProvince = view.findViewById(R.id.edit_province_text);
        //AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        if(isEditMode && city != null){
            editCity.setText(city.getCityName());
            editProvince.setText(city.getProvinceName());
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        return builder
                .setView(view)
                .setTitle(isEditMode ? "Edit City" : "Add a City")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK", (dialog, which) -> {
                    String cityName = editCity.getText().toString();
                    String provinceName = editProvince.getText().toString();

                    if (isEditMode && city != null) {
                        city.setCity(cityName);
                        city.setProvince(provinceName);
                        listener.editCity(position,city);
                    } else {
                        listener.addCity(new City(cityName, provinceName));
                    }

                })
                .create();
    }
}
