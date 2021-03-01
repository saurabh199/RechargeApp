package com.example.admin.mytripcart;


import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Ankita on 29-06-2015.
 */
public class MyPref {
    private final String DBNAME = "easyTouch";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor spEditor;
    private Context context;
    private final String POLICY_READ = "policyRead";
    private final String COUNT = "count";
    private final String WEIGHT = "weight";
    int count;
    boolean policyRead;
    String weight;

    public MyPref(Context context) {
        this.context = context;
        sharedPreferences = this.context.getSharedPreferences(DBNAME,
                Context.MODE_PRIVATE);
    }


    public String getWeight() {
        return sharedPreferences.getString(WEIGHT, "");
    }

    public void setWeight(String weight) {
        spEditor = sharedPreferences.edit();
        spEditor.putString(WEIGHT, weight);
        spEditor.commit();
    }

    public boolean isPolicyRead() {
        return sharedPreferences.getBoolean(POLICY_READ, false);
    }

    public void setPolicyRead(boolean policyRead) {
        spEditor = sharedPreferences.edit();
        spEditor.putBoolean(POLICY_READ, policyRead);
        spEditor.commit();
    }

//    public int getWeight() {
//        return sharedPreferences.getInt(WEIGHT, 0);
//    }
//
//    public void setWeight(int weight) {
//        spEditor = sharedPreferences.edit();
//        spEditor.putInt(WEIGHT, weight);
//        spEditor.commit();
//    }

    public int getCount() {
        return sharedPreferences.getInt(COUNT, 0);
    }

    public void setCount(int count) {
        spEditor = sharedPreferences.edit();
        spEditor.putInt(COUNT, count);
        spEditor.commit();
    }
}
