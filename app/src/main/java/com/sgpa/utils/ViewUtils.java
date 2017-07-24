package com.sgpa.utils;


import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;

public class ViewUtils {

    public static String getValue(@NonNull View view, int id) {
        return getEditText(view, id).getText().toString();
    }

    public static EditText getEditText(@NonNull View view, int id) {
        return (EditText) view.findViewById(id);
    }
}
