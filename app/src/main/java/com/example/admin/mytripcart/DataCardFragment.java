package com.example.admin.mytripcart;

/**
 * Created by Admin on 12/14/2015.
 */

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class DataCardFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    Spinner spinner;
    private String[] arraySpinner;
    List<String> arrayList = new ArrayList<String>();
    private LinkedHashMapAdapter<String, String> acadapter;
    boolean locked = true;
    ConnectivityManager cm;
    Shreadpreference shareprefs;
DatabaseHandler db;
    String username="";
    String mobileNo="";
    String amount="";
    String operator="";
    String opcode="";
    EditText edt1,edt2;
    Button process;
    ProgressDialog progressDialog;
    DatacardHandler datacardHandler;
    SQLiteDatabase sqLiteDatabase;
    public DataCardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_datacard, container, false);
        // Inflate the layout for this fragment
        //   return inflater.inflate(R.layout.fragment_dth, container, false);
        edt1 = (EditText) view.findViewById(R.id.prepaid);
        edt2 = (EditText) view.findViewById(R.id.amount);
        spinner = (Spinner) view.findViewById(R.id.datacardOperator);
        datacardHandler = new DatacardHandler(getActivity());
        db = new DatabaseHandler(getActivity());
        List<Contact> listname = db.getAllContacts();

        if (listname != null && listname.size() > 0) {
        }
        process = (Button) view.findViewById(R.id.Process);
        {
            shareprefs = new Shreadpreference(getActivity());// on create view (getActivity)
            cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            process.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mobileNo = edt1.getText().toString();
                    if(TextUtils.isEmpty(mobileNo)) {
                        // edt1.setError("Error");
                        Toast.makeText(getActivity(), "Please fill the Required field", Toast.LENGTH_LONG).show();
                        return;
                    }
                    operator = spinner.getSelectedItem().toString();
                    String[] pqr=operator.split("<@@>");
                    System.out.println(pqr[0]);
                    opcode = spinner.getSelectedItem().toString();
                    String[] pqr1=operator.split("<@@>");
                    System.out.println(pqr1[0]);
                    amount = edt2.getText().toString();
                    if(TextUtils.isEmpty(amount)) {
                        Toast.makeText(getActivity(), "Please fill the Required field", Toast.LENGTH_LONG).show();
                        //  edt2.setError("Error");
                        return;
                    }
                    else {
                        new DataOperator().execute();
                        progressDialog = new ProgressDialog(getActivity());
                        progressDialog.setIndeterminate(true);
                        progressDialog.setMessage("Recharging...");
                        progressDialog.show();
                        new android.os.Handler().postDelayed(
                                new Runnable() {
                                    public void run() {
                                        // On complete call either onLoginSuccess or onLoginFailed
                                        //           onLoginSuccess();
                                        // onLoginFailed();
                                        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                                new ContextThemeWrapper(getActivity(), android.R.style.Theme_Holo_Light_Dialog));
                                        alertDialogBuilder.setTitle("Mytripcart").setIcon(R.mipmap.ic_launcher);
                                        alertDialogBuilder.setTitle("Mytripcart");
                                        alertDialogBuilder
                                                .setMessage("Recharge Successfull")
                                                .setCancelable(false)
                                                .setPositiveButton("OK",
                                                        new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialog, int id) {
                                                                dialog.cancel();
                                                                //    alertDialogBuilder.setCancelable(true);
                                                            }
                                                        });

                                        AlertDialog alertDialog = alertDialogBuilder.create();
                                        alertDialog.show();
                                        Toast.makeText(getActivity(), "Recharge Successfull", Toast.LENGTH_LONG).show();
                                       progressDialog.dismiss();
                                    }
                                }, 10000);
               /*     AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            new ContextThemeWrapper(getActivity(), android.R.style.Theme_Holo_Light_Dialog));

                    alertDialogBuilder.setTitle("Mytripcart").setIcon(R.mipmap.ic_launcher);
                    alertDialogBuilder.setTitle("Mytripcart");
                    alertDialogBuilder
                            .setMessage("Recharge Successfull")
                            .setCancelable(false)
                            .setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {

                                        }

                                    });
//                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int id) {
//                                    dialog.cancel();
//                                }
//                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();


                    Toast.makeText(getActivity(), "Recharge Successfull", Toast.LENGTH_SHORT).show();*/
                    }
                }
            });
        }


        //   spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
//        List<String> categories = new ArrayList<String>();
//        categories.add("Idea");
//        categories.add("Aircel");
//        categories.add("Airtel");
//        categories.add("Reliance");
//        categories.add("Vodafone");
//        ArrayAdapter<String> LTRadapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, categories);
//        LTRadapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
//        spinner.setAdapter(LTRadapter);
      /*  SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        if(!prefs.getBoolean("firstTime", false)) {
            // run your one time code
            // new MobileOperator().execute();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTime", true);
            editor.commit();
        }*/
        // new MobileOperator().execute();
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
//        if(!prefs.getBoolean("firstTime", false)) {
//            SharedPreferences.Editor editor = prefs.edit();
//            editor.putBoolean("firstTime", true);
//            editor.commit();
//        }
//        if (cm.getActiveNetworkInfo() != null) {
//            System.out.println("Check-----------" + cm.getActiveNetworkInfo());
//            if (shareprefs.getURLRUNONETIME() == false) {
//                new MobileOperator().execute();
////                shareprefs.setURLRUNONETIME(true);
////            } else if (shareprefs.getURLRUNONETIME() != false) {
//               shareprefs.setURLRUNONETIME(false);
//            }
//        }


      //  new MobileOperator().execute();
            return view;
        }

    @Override
    public void onResume() {
    new DataCardOperator().execute();
      //  new MobileOperator().execute();
        super.onResume();
    }

    class DataCardOperator extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
//			 public ArrayList<String> opcode() {
//	              ArrayList<String> arrCountries = new ArrayList<String>();
            String result = "";
            try {
                datacardHandler = new DatacardHandler(getActivity());
                URL url = new URL("http://180.151.246.171:8888/satkar/getMobileOperatorsApp.htm?operatorCategory=DATACARD");
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
                if(!prefs.getBoolean("firstTime", false)) {
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean("firstTime", true);
                    editor.commit();
                }
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                //conn.setRequestMethod("GET");
                conn.connect();
                InputStream in = new BufferedInputStream(conn.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
                String line = "";
                while ((line = bufferedReader.readLine()) != null)
                    result += line;
                in.close();
                conn.disconnect();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return result;
        }
       String[] spinnerArray =null;
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                System.out.println("Datacard  ");
                //  Context context = getApplicationContext();
                JSONArray jsonArray = new JSONArray(result);

                if(spinnerArray!=null)
                System.out.println("spinnerArray    "+spinnerArray.length);

                spinnerArray = new String[jsonArray.length()];
                LinkedHashMap<String, String> spinnerMap = new LinkedHashMap<String, String>();

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    operator = jsonObject.getString("operator");
                    opcode = jsonObject.getString("opcode");
                    datacardHandler.addDataOperator(new CardOperator(operator,opcode));
                  //  datacardHandler.addDataOperator(new CardOperator(operator,opcode));
                  //  System.out.println(operator + "****************" + opcode);
                    if(spinnerMap.size()>= 0) {

                        spinnerMap.put(opcode, operator);
                        spinnerArray[i] = operator;
                        acadapter = new LinkedHashMapAdapter<String, String>(getActivity(), android.R.layout.simple_spinner_item, spinnerMap);
                        acadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner.setAdapter(acadapter);
                    }
                    //Toast.makeText(MainActivity.this,"success "+result, Toast.LENGTH_SHORT).show();
/*
                    spinnerMap.put(opcode, operator);
                    spinnerArray[i] = operator;

                    acadapter = new LinkedHashMapAdapter<String, String>(getActivity(), android.R.layout.simple_spinner_item, spinnerMap);
                    acadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    //ArrayAdapter<String> adapter =new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item, spinnerMap);
                    // adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    //  spinner.setAdapter(adapter);

                    // spinner = (Spinner) findViewById(R.id.spinner);
                    spinner.setAdapter(acadapter);*/
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

        class DataOperator extends AsyncTask<Void,Void,String>
        {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(Void... voids) {
                String result = "";
                try {
/*
                    String str = "";
                    // split the array using ':' as a delimiter
                    String[] parts = str.split(":");
                    System.out.println("Using : as a delimiter "+ Arrays.toString(parts));
                    // split the array using 'd' as a delimiter
                    parts = str.split("d");
                    System.out.println(Arrays.toString(parts));
                    String str2 = "This is a string to tokenize";
                    // tokenize the string into words simply by splitting with " "
                    parts = str2.split(" ");
                    System.out.println(Arrays.toString(parts));*/
                    db = new DatabaseHandler(getActivity());
                    List<Contact> listn = db.getAllContacts();
                    if (listn != null && listn.size() > 0) {
                        username = listn.get(0).get_username();
                    }
                    System.out.println("Username *********************    --   " + username);
                    URL url =new URL("http://180.151.246.171:8888/satkar/rechargeFromApp.htm?username=STPL0000052&mobileNo=9911190700&amount=100&operatorDesc=VODAFONE&operator=HVOD");

                   // URL url = new URL("http://180.151.246.171:8888/satkar/rechargeFromApp.htm?username=STPL0000052&mobileNo=9911190700&amount=100&operatorDesc=VODAFONE&operator=HVOD");
                    System.out.println(username+ "        "+mobileNo+"     "+amount+ "       "+operator+"          "+opcode);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.connect();
                    InputStream in = new BufferedInputStream(conn.getInputStream());
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
                    String line = "";
                    while((line = bufferedReader.readLine()) != null)
                        result += line;
                    in.close();
                    conn.disconnect();

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                return result;
            }
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                try {
                    String status = "";

                    String[] xyz = result.split("<@@>");
                    System.out.println(xyz[0]);
                    System.out.println(xyz[1]);
                    System.out.println(xyz[2]);
                    System.out.println(xyz[3]);
                    System.out.println(xyz[4]);
                    System.out.println(xyz[5]);
                    System.out.println(xyz[6]);
                    System.out.println(xyz[7]);
                    System.out.println(xyz[8]);

                    //  System.out.println("       "+username);
                  /*  JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        username = jsonObject.getString("username").toString();
                        mobileNo = jsonObject.getString("mobileNo").toString();
                        amount = jsonObject.getString("amount").toString();
                        operator = jsonObject.getString("operator").toString();
                        opcode = jsonObject.getString("opcode");
                        System.out.println(username + "        " + mobileNo + "     " + amount + "       " + operator + "          " + opcode);
                    }*/
                   /* if (!result.equalsIgnoreCase("INVALID")) {
//                    String message = "Your Mobile PIN is " + pinnumber;
//                    SmsManager smsManager = SmsManager.getDefault();
//                    smsManager.sendTextMessage(mobileno, null, message, null, null);
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                new ContextThemeWrapper(getActivity(), android.R.style.Theme_Holo_Light_Dialog));

                        alertDialogBuilder.setTitle("Mytripcart").setIcon(R.mipmap.ic_launcher);
                        alertDialogBuilder.setTitle("Mytripcart");
                        alertDialogBuilder
                                .setMessage("Recharge Successfull")
                                .setCancelable(false)
                                .setPositiveButton("OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                            }
                                        });
//                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int id) {
//                                    dialog.cancel();
//                                }
//                            });
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
//                    Toast.makeText(getContext(), "Mobile PIN sent to your registered Mobile Number!",
//                            Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getContext(), "Recharge Failed", Toast.LENGTH_SHORT).show();
                    }*/
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        }




























        @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}