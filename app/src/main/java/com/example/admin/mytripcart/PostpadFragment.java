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

public class PostpadFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private String[] arraySpinner;
    public static final String mypreference = "mypref";
    SharedPreferences sharedpreferences;
    List<String> arrayList = new ArrayList<String>();
    private LinkedHashMapAdapter<String, String> acadapter;
    Spinner spinner;
    ArrayList<App_InfoPojo> listAdapterData;
    ArrayList<App_InfoPojo> list;
    boolean locked = true;
    ConnectivityManager cm;
    Shreadpreference shareprefs;
    EditText edt1,edt2;
    Button button;
    String operator="";
    String opcode="";
    String username="";
    String operatorDesc ="";
    String amount="";
    String mobileNo="";
    DatabaseHandler db;
    PostpaidHandler postpaidHandler;
    ProgressDialog progressDialog;
    SQLiteDatabase sqLiteDatabase;
    private String url = "http://180.151.246.171:8888/satkar/getMobileOperatorsApp.htm?operatorCategory=POSTPAID";
    private static String TAG = PostpadFragment.class.getName();

    public PostpadFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        //  return inflater.inflate(R.layout.fragment_postpad, container, false);
        View view = inflater.inflate(R.layout.fragment_postpad, container, false);
        spinner = (Spinner) view.findViewById(R.id.postpaidOperator);
        edt1 = (EditText) view.findViewById(R.id.prepaid);
        edt2 = (EditText) view.findViewById(R.id.amount);
        button = (Button) view.findViewById(R.id.Process);
        postpaidHandler = new PostpaidHandler(getActivity());
     //button= (Button)view.findViewById(R.id.progress);
     //   shareprefs = new Shreadpreference(getActivity());// on create view (getActivity)
        cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mobileNo = edt1.getText().toString();
                if(TextUtils.isEmpty(mobileNo)) {
                    // edt1.setError("Error");
                    Toast.makeText(getActivity(), "Please fill the Required field", Toast.LENGTH_LONG).show();
                    return;
                }
                operator=spinner.getSelectedItem().toString();
                String[] pqr=operator.split("<@@>");
                System.out.println(pqr[0]);

                opcode=spinner.getSelectedItem().toString();
                String[] pqr1=operator.split("");
                System.out.println(pqr1[1]);
                amount = edt2.getText().toString();
                if(TextUtils.isEmpty(amount)) {
                    Toast.makeText(getActivity(), "Please fill the Required field", Toast.LENGTH_LONG).show();
                    //  edt2.setError("Error");
                    return;
                }
                else {
                    new PostpaidOperator().execute();

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
                            }, 8000);
/*
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


                    Toast.makeText(getActivity(), "Recharge Successfull", Toast.LENGTH_SHORT).show();*/
                }
            }
        });






//        sharedpreferences = getSharedPreferences(mypreference,
//                Context.MODE_PRIVATE);
//
//        if (sharedpreferences.contains()) {
//            .setText(sharedpreferences.getString(Name, ""));
//        }
//        if (sharedpreferences.contains()) {
//            email.setText(sharedpreferences.getString(Email, ""));
        //  sharedpreferences = getPreference(acadapter, Context.MODE_PRIVATE);


        //   dataFromDatabase();
//        autocomplete.setAdapter(acadapter);
//        List<String> categories = new ArrayList<String>();
//        categories.add("Idea");
//        categories.add("Aircel");
//        categories.add("Airtel");
//        categories.add("Reliance");
//        categories.add("Vodafone");
//        ArrayAdapter<String> LTRadapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, categories);
//        LTRadapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
//        spinner.setAdapter(LTRadapter);
//        mapData = new LinkedHashMap<String, String>();
//        mapData.put("shamu", "Nexus 6");
//        mapData.put("fugu", "Nexus Player");
//        mapData.put("volantisg", "Nexus 9 (LTE)");
//        mapData.put("volantis", "Nexus 9 (Wi-Fi)");
//        mapData.put("hammerhead", "Nexus 5 (GSM/LTE)");
//        mapData.put("razor", "Nexus 7 [2013] (Wi-Fi)");
//        mapData.put("razorg", "Nexus 7 [2013] (Mobile)");
//        mapData.put("mantaray", "Nexus 10");
//        mapData.put("occam", "Nexus 4");
//        mapData.put("nakasi", "Nexus 7 (Wi-Fi)");
//        mapData.put("nakasig", "Nexus 7 (Mobile)");
//        mapData.put("tungsten", "Nexus Q");


//        LinkedHashMapAdapter<String, String> acadapter = new LinkedHashMapAdapter<String, String>(this, android.R.layout.simple_list_item_1, mapData, LinkedHashMapAdapter.FLAG_FILTER_ON_KEY);
//        acadapter.setDropDownViewResource(this, android.R.layout.simple_dropdown_item_1line, mapData, LinkedHashMapAdapter.FLAG_FILTER_ON_KEY);
//       HashMap<String, String> mapData = new HashMap<String, String>();
//        mapData.put("shamu", "Nexus 6");
//        mapData.put("fugu", "Nexus Player");
//        mapData.put("volantisg", "Nexus 9 (LTE)");
//        mapData.put("volantis", "Nexus 9 (Wi-Fi)");
//        mapData.put("hammerhead", "Nexus 5 (GSM/LTE)");
//        mapData.put("razor", "Nexus 7 [2013] (Wi-Fi)");
//        mapData.put("razorg", "Nexus 7 [2013] (Mobile)");
//        mapData.put("mantaray", "Nexus 10");
//        mapData.put("occam", "Nexus 4");
//        mapData.put("nakasi", "Nexus 7 (Wi-Fi)");
//        mapData.put("nakasig", "Nexus 7 (Mobile)");
//        mapData.put("tungsten", "Nexus Q");

//        LinkedHashMapAdapter<String> LTRadapter = new LinkedHashMapAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item, mapData);
//        LTRadapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
//      //  spinner.setAdapter(LTRadapter);
        //    spinner = (Spinner) findViewById(R.id.spinner);
        //   spinner.setAdapter(LTRadapter);
        // spinner.setOnItemSelectedListener(this);
        //  new MobileOperator().execute();
//        SharedPreferences prefs = getPreference(getApplicationContext(), Context.MODE_PRIVATE);
//        if (!prefs.getBoolean("firstTime", true)) {
//            new MobileOperator().execute();
//            SharedPreferences.Editor editor = prefs.edit();
//            editor.putBoolean("firstTime", false);
//            editor.commit();
//        }
 ;

       /* SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        if(!prefs.getBoolean("firstTime", false)) {
            // run your one time code
            // new MobileOperator().execute();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTime", true);
            editor.commit();
        }*/
       /* if (cm.getActiveNetworkInfo() != null) {
            System.out.println("Check-----------" + cm.getActiveNetworkInfo());
            if (shareprefs.getURLRUNONETIME() == false) {
                new PostOperator().execute();
            shareprefs.setURLRUNONETIME(false);
            }
        }*/
     //   new PostOperator().execute();
       // new MobileOperator().execute();
        return view;
    }
    @Override
    public void onResume() {

        new PostOperator().execute();
        super.onResume();
    }

    class PostOperator extends AsyncTask<Void, Void, String> {
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
                postpaidHandler = new PostpaidHandler(getActivity());
                URL url = new URL("http://180.151.246.171:8888/satkar/getMobileOperatorsApp.htm?operatorCategory=POSTPAID");
             //    System.out.println("*****PostPaid*******");
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
                //System.out.println("Postpaid");
                //  Context context = getApplicationContext();
                JSONArray jsonArray = new JSONArray(result);
                String[] spinnerArray = new String[jsonArray.length()];
                LinkedHashMap<String, String> spinnerMap = new LinkedHashMap<String, String>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                   String operator = jsonObject.getString("operator");
                    String opcode = jsonObject.getString("opcode");

                    postpaidHandler.addPostOperator(new com.example.admin.mytripcart.PostOperator(operator,opcode));
                   // postpaidHandler.addPostOperator(new PostOperator(operator, opcode));
                  //  postpaidHandler.addPostOperator(new PostOperator());

                    if(spinnerMap.size()>= 0) {

                        spinnerMap.put(opcode, operator);
                        spinnerArray[i] = operator;
                        acadapter = new LinkedHashMapAdapter<String, String>(getActivity(), android.R.layout.simple_spinner_item, spinnerMap);
                        acadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner.setAdapter(acadapter);
                    }
                   /* arrayList.add(operator);
                    arrayList.add(opcode);

                    //Toast.makeText(MainActivity.this,"success "+result, Toast.LENGTH_SHORT).show();

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
    class PostpaidOperator extends AsyncTask<Void,Void,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
            String recharge = "";
            try {

                db = new DatabaseHandler(getActivity());

                List<Contact> listn = db.getAllContacts();

                if (listn != null && listn.size() > 0) {
                    username = listn.get(0).get_username();
                }
                System.out.println("Username *********************    --   " + username);
                System.out.println(username + "        " + mobileNo + "     " + amount + "       " + operator+"      "+opcode);
               //  URL url = new URL("http://180.151.246.171:8888/satkar/paymentFromApp.htm?username="+username+"&mobileNo="+mobileNo+"&amount="+amount+"&operator="+operator+"&opcode="+opcode);
                // URL url = new URL("http://180.151.246.171:8888/satkar/paymentFromApp.htm?username=" + username + "&mobileNo=" + mobileNo + "&amount=" + amount + "&operatorDesc=" + operatorDesc + "&operator=" + operator);

            //  URL url=new URL("http://180.151.246.171:8888/satkar/paymentFromApp.htm?username="+username+"&mobileNo="+mobileNo+"&amount="+amount+"&&operator="+operator+"&opcode="+opcode);
                URL url=new URL("http://180.151.246.171:8888/satkar/paymentFromApp.htm?username=STPL0000052&mobileNo=9911190700&amount=100&operatorDesc=VODAFONE%20POSTPAID&operator=HVOP");
                  // URL url = new URL("http://180.151.246.171:8888/satkar/paymentFromApp.htm?username="+username+"&mobileNo="+mobileNo+"&amount="+amount+"&operator="+operator+"&opcode="+opcode);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();
                InputStream in = new BufferedInputStream(conn.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
                String line = "";
                while ((line = bufferedReader.readLine()) != null)
                    recharge += line;
                in.close();
                conn.disconnect();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return recharge;
        }

        @Override
        protected void onPostExecute(String recharge) {
            super.onPostExecute(recharge);
            try {
                String status = "";

              //    recharge = "1<@@>Your payment has been processed successfully.<@@>ZR85CPBNLRKYGIMAUWAB6SECS7IYLQ00PYS<@@>D3SZ9A<@@>D3SZ9A<@@>24/05/2014 05:02:23 PM<@@>HIDP<@@>01505057883<@@>200";

                    String[] xyz = recharge.split("<@@>");
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
                    //   JSONArray jsonArray = new JSONArray(recharge);

              /*  for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    System.out.println("****************************************"+recharge);*/

//                    username = jsonObject.getString("username").toString();
//                    mobileNo = jsonObject.getString("mobileNo").toString();
//
//                    amount = jsonObject.getString("amount").toString();
//                    operator = jsonObject.getString("operator").toString();
//                    opcode = jsonObject.getString("opcode");
//                    System.out.println(username + "        " + mobileNo + "     " + amount + "       " + operator + "          " + opcode);


                    /*if (!status.equalsIgnoreCase("INVALID")) {
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
                                                dialog.cancel();
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

//    private void dataFromDatabase() {
//        JsonArrayRequest req=new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//                Log.d(TAG, response.toString());
//                try {
//                    for (int i = 0; i < response.length(); i++) {
//                        App_InfoPojo pojo = new App_InfoPojo();
//                        JSONObject data = (JSONObject) response.get(i);
//
//                        pojo.setOpcode(data.getString("opcode"));
//                        pojo.setOperator(data.getString("operator"));
//
////                        db=new DatabaseHandler(MainActivity.this);
////                        db.addApp(new App_InfoPojo(data.getString("opcode"), data.getString("operator")));
//
//                        listAdapterData.add(pojo);
//                    }
//                    }catch(JSONException e){
//                        e.printStackTrace();
//                    }
//                ArrayAdapter<String> LTRadapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, list);
//                LTRadapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
//                spinner.setAdapter(LTRadapter);

//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                VolleyLog.d(TAG, "Error: " + volleyError.getMessage());
//                System.out.println("mtechError  " + volleyError.getMessage());
//             //   pDialog.setVisibility(View.GONE);
//            }
//        });
//        AppController.getInstance().addToRequestQueue(req);
                }

//}