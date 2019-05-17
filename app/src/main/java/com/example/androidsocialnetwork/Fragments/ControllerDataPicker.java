package com.example.androidsocialnetwork.Fragments;

import android.app.DatePickerDialog;
import android.widget.DatePicker;



public class ControllerDataPicker implements DatePickerDialog.OnDateSetListener{

    private ProfileFragment profileFragment;
    private int year;
    private int month;
    private int day;
    public ControllerDataPicker( ProfileFragment profileFragment) {
        this.profileFragment= profileFragment;
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        profileFragment.changeTextViewDate (dayOfMonth,month,year);
    }
}
