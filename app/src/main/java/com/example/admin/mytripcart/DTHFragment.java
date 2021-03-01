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
import android.widget.LinearLayout;
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

public class DTHFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    EditText edt1,edt2;
    Button button;
    LinearLayout lin_save_undo;
    Spinner spinner;
    private String[] arraySpinner;
    public static final String mypreference = "mypref";
    SharedPreferences sharedpreferences;
    List<String> arrayList = new ArrayList<String>();

    boolean locked = true;
    ConnectivityManager cm;
    Shreadpreference shareprefs = null;
    String username = "";
    String mobileNo = "";
    String operator = "";
    String opcode = "";
    String amount = "";
    DatabaseHandler db;

    ProgressDialog progressDialog;
    private LinkedHashMapAdapter<String, String> acadapter;
    DthHandler dthHandler;
    SQLiteDatabase sqLiteDatabase;
    public DTHFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dth, container, false);
        // Inflate the layout for this fragment
        //   return inflater.inflate(R.layout.fragment_dth, container, false);
        spinner = (Spinner) view.findViewById(R.id.dthOperator);
        edt1 = (EditText) view.findViewById(R.id.prepaid);
        edt2 = (EditText) view.findViewById(R.id.amount);
        button=(Button) view.findViewById(R.id.Process);
        cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        dthHandler = new DthHandler(getActivity());
        db = new DatabaseHandler(getActivity());
        List<Contact> listname = db.getAllContacts();

        if (listname != null && listname.size() > 0) {
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             mobileNo=edt1.getText().toString();
                if(TextUtils.isEmpty(mobileNo)) {
                    // edt1.setError("Error");
                    Toast.makeText(getActivity(), "Please fill the Required field", Toast.LENGTH_LONG).show();
                    return;
                }
             operator=spinner.getSelectedItem().toString();
                String[] pqr=operator.split("<@@>");

                System.out.println(pqr[0]);
                opcode=spinner.getSelectedItem().toString();
                String[] pqr1=operator.split("<@@>");

                System.out.println(pqr1[0]);
             amount= edt2.getText().toString();
                if(TextUtils.isEmpty(amount)) {
                    Toast.makeText(getActivity(), "Please fill the Required field", Toast.LENGTH_LONG).show();
                    //  edt2.setError("Error");
                    return;
                }
                else {
                    new DTHOperator().execute();

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
                  /*  AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
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

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();

                    Toast.makeText(getActivity(), "Recharge Successfull", Toast.LENGTH_SHORT).show();*/
                }
            }
        });
      //  new MobileOperator().execute();
            return view;
        }

    @Override
    public void onResume() {

        new DTHMobilesOperator().execute();
        super.onResume();
    }

        class DTHMobilesOperator extends AsyncTask<Void, Void, String> {
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
                    dthHandler = new DthHandler(getActivity());
                    URL url = new URL("http://180.151.246.171:8888/satkar/getMobileOperatorsApp.htm?operatorCategory=DTH");
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
                    if (!prefs.getBoolean("firstTime", false)) {
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

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                try {
                    System.out.println("DTH");
                    //  Context context = getApplicationContext();
                    JSONArray jsonArray = new JSONArray(result);
                    String[] spinnerArray = new String[jsonArray.length()];
                    LinkedHashMap<String, String> spinnerMap = new LinkedHashMap<String, String>();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        operator = jsonObject.getString("operator");
                        opcode = jsonObject.getString("opcode");
                        dthHandler.adddthOperator(new Dthoperator(operator, opcode));
                        //  datacardHandler.addDataOperator(new CardOperator(operator,opcode));
                       // System.out.println(operator + "****************" + opcode);
                        if(spinnerMap.size()>= 0) {

                            spinnerMap.put(opcode, operator);
                            spinnerArray[i] = operator;
                            acadapter = new LinkedHashMapAdapter<String, String>(getActivity(), android.R.layout.simple_spinner_item, spinnerMap);
                            acadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner.setAdapter(acadapter);
                        }
                        //Toast.makeText(MainActivity.this,"success "+result, Toast.LENGTH_SHORT).show();

                      /*  spinnerMap.put(opcode, operator);
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
    class DTHOperator extends AsyncTask<Void,Void,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
            String result = "";
            try {

              /*  String str = "";
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
                System.out.println(username+ "        "+mobileNo+"     "+amount+ "       "+operator+"          "+opcode);
              //  URL url =new URL("http://180.151.246.171:8888/satkar/rechargeFromApp.htm?username=STPL0000052&mobileNo=9911190700&amount=100&operatorDesc=VODAFONE&operator=HVOD");
                URL url=new URL("http://180.151.246.171:8888/satkar/rechargeFromApp.htm?username=STPL0000052&mobileNo=9911190700&amount=100&operatorDesc=VODAFONE&operator=HVOD");
               // URL url = new URL("http://180.151.246.171:8888/satkar/rechargeFromApp.htm?username="+username+"&mobileNo="+mobileNo+"&amount="+amount+"&operatorDesc="+operator+"&operator="+opcode);
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
               /* JSONArray jsonArray = new JSONArray(result);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    username = jsonObject.getString("username").toString();
                    mobileNo = jsonObject.getString("mobileNo").toString();
                    amount = jsonObject.getString("amount").toString();
                    operator = jsonObject.getString("operator").toString();
                    opcode = jsonObject.getString("opcode");
                    System.out.println(username + "        " + mobileNo + "     " + amount + "       " + operator + "          " + opcode);
                }*/
                progressDialog.dismiss();
                if (!result.equalsIgnoreCase("INVALID")) {

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

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
//
                } else {
                    Toast.makeText(getContext(), "Recharge Failed", Toast.LENGTH_SHORT).show();
                }
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

