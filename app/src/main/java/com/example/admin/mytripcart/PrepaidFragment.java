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

public class PrepaidFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    EditText edt1, edt2;
    SharedPreferences sharedPreferences;
    Button process;
    LinearLayout lin_save_undo;
    Spinner spinner;
    ProgressDialog progressDialog;
    DatabaseHandler db;
    private String[] arraySpinner;
    List<String> arrayList = new ArrayList<String>();

    private LinkedHashMapAdapter<String, String> acadapter;
    String operator = "";
    String opcode = "";
    String username = "";
    String amount = "";
    String mobileNo = "";
    String response = "";
    boolean locked = true;
 //   String operatorDesc="";
    ConnectivityManager cm;
    Shreadpreference shareprefs;
    ArrayList<Operator> listAdapterData;
    ArrayList<Operator> list;
    DatabaseOperator databaseOperator;
    SQLiteDatabase sqLiteDatabase;
    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = null;
   // static HttpResponse httpResponse = null;
    public PrepaidFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_prapaid, container, false);
        edt1 = (EditText) view.findViewById(R.id.prepaid);


        edt2 = (EditText) view.findViewById(R.id.amount);
        spinner = (Spinner) view.findViewById(R.id.prepaidOperator);

       /* Fragment currentFragment = getFragmentManager().findFragmentByTag("FRAGMENT");
        FragmentTransaction fragTransaction = getFragmentManager().beginTransaction();
        fragTransaction.detach(currentFragment);
        fragTransaction.attach(currentFragment);
        fragTransaction.commit();*/

        db = new DatabaseHandler(getActivity());
        databaseOperator = new DatabaseOperator(getActivity());
        // List<Contact> listname = db.getAllContacts();
       /* if(edt1.getText().toString().equals(mobileNo) &&
        edt2.getText().toString().equals(amount)) {

        //if (edt1 != null && edt1.length() > 0 &&edt2 != null && edt2.length() > 0) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
        new ContextThemeWrapper(getActivity(), android.R.style.Theme_Holo_Light_Dialog));

            alertDialogBuilder.setTitle("Mytripcart").setIcon(R.mipmap.ic_launcher);
            alertDialogBuilder.setTitle("Mytripcart");
            alertDialogBuilder
                    .setMessage("Recharge Failed")
                    .setCancelable(false)
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                }
                            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();*/


        //    }

        process = (Button) view.findViewById(R.id.Process);
        {

            shareprefs = new Shreadpreference(getActivity());// on create view (getActivity)
            cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            process.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                        mobileNo = edt1.getText().toString();

                        if (TextUtils.isEmpty(mobileNo)) {
                            // edt1.setError("Error");
                            Toast.makeText(getActivity(), "Please fill the Required field", Toast.LENGTH_LONG).show();
                            return;
                        }
                        operator = spinner.getSelectedItem().toString();
                        String[] pqr = operator.split("<@@>");

                        System.out.println(pqr[0]);
                        //operator = spinner.getSelectedItem().toString();
                        opcode = spinner.getSelectedItem().toString();
                        String[] pqr1 = opcode.split("<@@>");
                        System.out.println(pqr1[0]);
                        amount = edt2.getText().toString();

                        if (TextUtils.isEmpty(amount)) {
                            Toast.makeText(getActivity(), "Please fill the Required field", Toast.LENGTH_LONG).show();
                            //  edt2.setError("Error");
                            return;
                        } else {

                            new PrePaidOperator().execute();
                            progressDialog = new ProgressDialog(getActivity());
                            progressDialog.setIndeterminate(true);
                            progressDialog.setMessage("Recharging...");
                            progressDialog.show();
                            new android.os.Handler().postDelayed(
                                    new Runnable() {
                                        public void run() {

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

                        }



                   /* Fragment currentFragment = getFragmentManager().findFragmentByTag("FRAGMENT");
                    FragmentTransaction fragTransaction = getFragmentManager().beginTransaction();
                    fragTransaction.detach(currentFragment);
                    fragTransaction.attach(currentFragment);
                    fragTransaction.commit();*/

                   /* if (edt1.getText().toString().length() == 0)
                        edt1.setError("Mobile number is required!");
                    if (edt2.getText().toString().length() == 0)
                        edt2.setError("amount is required!");
*/
                  /*  progressDialog = new ProgressDialog(getActivity());
                    progressDialog.setIndeterminate(true);
                    progressDialog.setMessage("Authenticating...");
                    progressDialog.show();
                    new android.os.Handler().postDelayed(
                            new Runnable() {
                                public void run() {
                                    // On complete call either onLoginSuccess or onLoginFailed
                                    //           onLoginSuccess();
                                    // onLoginFailed();
                                    progressDialog.dismiss();
                                }
                            }, 16000);*/




                   /* PrepaidFragment frg;
                    frg = null;
                    frg = getFragmentManager().findFragmentByTag("Your_Fragment_TAG");
                    final android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.detach(frg);
                    ft.attach(frg);
                    ft.commit();*/

/*
                    if (mobileNo != null && amount != null) {
                      //
                        progressDialog = new ProgressDialog(getActivity());
                        progressDialog.setIndeterminate(true);
                        progressDialog.setMessage("Authenticating...");
                        progressDialog.show();
                        new android.os.Handler().postDelayed(
                                new Runnable() {
                                    public void run() {
                                        // On complete call either onLoginSuccess or onLoginFailed
                                        //           onLoginSuccess();
                                        // onLoginFailed();
                                        progressDialog.dismiss();
                                    }
                                }, 10000);
                        Toast.makeText(getActivity(), "Recharge Successfull ", Toast.LENGTH_SHORT).show();
                    } else if (!mobileNo.isEmpty() && !amount.isEmpty()) {

                        Toast.makeText(getActivity(), "Invalid Credential ", Toast.LENGTH_SHORT).show();
                    }*/
                   /* else if(mobileNo == null && amount == null) {
                        Toast.makeText(getActivity(),"Invalid Credential " , Toast.LENGTH_SHORT).show();
                    }*/
                    }






                                       }

                );
            }


       /* if (cm.getActiveNetworkInfo() != null) {
            System.out.println("Check-----------" + cm.getActiveNetworkInfo());
            if (shareprefs.getURLRUNONETIME() == false) {
                new MobileOperator().execute();
                shareprefs.setURLRUNONETIME(false);
            }
        }*/
            return view;
    }




    @Override
    public void onResume() {

        new MobileOperator().execute();
        super.onResume();
    }


    class MobileOperator extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
//			 public ArrayList<String> opcode() {
//	         ArrayList<String> arrCountries = new ArrayList<String>();
            String result = "";
            try {
                databaseOperator = new DatabaseOperator(getActivity());
                URL url = new URL("http://180.151.246.171:8888/satkar/getMobileOperatorsApp.htm?operatorCategory=PREPAID");
             //   System.out.println("**********PREPAID*****************");
               /* SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
                if (!prefs.getBoolean("firstTime", false)) {
                    // run your one time code
                    // new MobileOperator().execute();
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean("firstTime", true);
                    editor.commit();
                }*/
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
             //   System.out.println("Prepaid  ");
                //  ArrayList<String> inputArray=new ArrayList<String>();
                //  Context context = getApplicationContext();
                JSONArray jsonArray = new JSONArray(result);
                String[] spinnerArray = new String[jsonArray.length()];
                LinkedHashMap<String, String> spinnerMap = new LinkedHashMap<String, String>();

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                   String operator = jsonObject.getString("operator");
                   String opcode = jsonObject.getString("opcode");
                    databaseOperator.addOperator(new Operator(operator, opcode));

                    if(spinnerMap.size()>= 0)
                    {
                        spinnerMap.put(opcode, operator);
                        spinnerArray[i] = operator;
                        acadapter = new LinkedHashMapAdapter<String, String>(getActivity(), android.R.layout.simple_spinner_item, spinnerMap);
                        acadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner.setAdapter(acadapter);

                       /* spinnerMap.put(opcode, operator);
                        spinnerArray[i] = operator;
                        acadapter = new LinkedHashMapAdapter<String, String>(getActivity(), android.R.layout.simple_spinner_item, spinnerMap);
                        acadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        //ArrayAdapter<String> adapter =new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item, spinnerMap);
                        // adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        //  spinner.setAdapter(adapter);
//                        //  spinner = (Spinner) view.findViewById(R.id.spinner);
                        spinner.setAdapter(acadapter);*/
                    }
                  /*  spinnerMap.put(opcode, operator);
                    spinnerArray[i] = operator;
                    acadapter = new LinkedHashMapAdapter<String, String>(getActivity(), android.R.layout.simple_spinner_item, spinnerMap);
                    acadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(acadapter);*/
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class PrePaidOperator extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
            String result = "";
            try {
               /* String str = "";
                // split the array using ':' as a delimiter
                String[] parts = str.split(":");
                System.out.println("Using : as a delimiter " + Arrays.toString(parts));
                // split the array using 'd' as a delimiter
                parts = str.split("d");
                System.out.println(Arrays.toString(parts));
                String str2 = "This is a string to tokenize";
                // tokenize the string into words simply by splitting with " "
                parts = str2.split("");
                System.out.println(Arrays.toString(parts));*/
                db = new DatabaseHandler(getActivity());
                List<Contact> listn = db.getAllContacts();
                if (listn != null && listn.size() > 0) {
                    username = listn.get(0).get_username();
                }
                //   System.out.println("Username *********************    --   " + username);
                URL url =new URL("http://180.151.246.171:8888/satkar/rechargeFromApp.htm?username=STPL0000052&mobileNo=9911190700&amount=100&operatorDesc=VODAFONE&operator=HVOD");
               // URL url=new URL("http://180.151.246.171:8888/satkar/rechargeFromApp.htm?username="+username+"&mobileNo="+mobileNo+"&amount="+amount+"&operatorDesc="+operator+"&operator="+opcode);
             //  URL url = new URL("http://180.151.246.171:8888/satkar/rechargeFromApp.htm?username=" + username + "&mobileNo=" + mobileNo + "&amount=" + amount + "&operatorDesc=" + operator + "&operator=" + opcode);
                System.out.println(username + "        " + mobileNo + "     " + amount + "       " + operator + "          " + opcode);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
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

//                JSONObject jObject = null;
//                jObject = new JSONObject(result);
//                jObject = new JSONObject(result.substring(3));
//               // jObject = new JSONObject(jObject.substring(3));
//                JSONArray jsonImageArray = jObject.getJSONArray(result);

      //          JSONArray jsonArray = new JSONArray(result);
//                for (int i = 0; i < jsonArray.length(); i++) {
//                    //JSONObject responseJSonObj = new JSONObject( URLDecoder.decode(result, "UTF-8") );
//                    JSONObject jsonObject = jsonArray.getJSONObject(i);
//                    //System.out.println(" " + result);
//                    status = result;
//                    System.out.println("****************" + result);
                  /*username = jsonObject.getString("username");
                    mobileNo = jsonObject.getString("mobileNo");
                    amount = jsonObject.getString("amount");
                    operator = jsonObject.getString("operator");
                    opcode = jsonObject.getString("opcode");*/


               /* final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        new ContextThemeWrapper(getActivity(), android.R.style.Theme_Holo_Light_Dialog));
                alertDialogBuilder.setTitle("Mytripcart").setIcon(R.mipmap.ic_launcher);
                alertDialogBuilder.setTitle("Mytripcart");
                alertDialogBuilder
                        .setMessage("Recharge Successfull")
                        .setCancelable(false)
                        .setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        alertDialogBuilder.setCancelable(true);
                                    }
                                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();*/
          /*      if(amount.equals(0))
                {
                    edt1.setError("0 amount is invalid");
                }
              if (!mobileNo.equalsIgnoreCase("INVALID")&&
              !amount.equalsIgnoreCase("INVALID")&&!operator.equalsIgnoreCase("INVALID")&&!opcode.equalsIgnoreCase("INVALID")) {
                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    new ContextThemeWrapper(getActivity(), android.R.style.Theme_Holo_Light_Dialog));
                    alertDialogBuilder.setTitle("Mytripcart").setIcon(R.mipmap.ic_launcher);
                    alertDialogBuilder.setTitle("Mytripcart");
                    alertDialogBuilder
                            .setMessage("Recharge Successfull")
                            .setCancelable(false)
                            .setPositiveButton("Yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                          alertDialogBuilder.setCancelable(true);
                                        }
                                    });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }*/

                }catch(Exception e)
                {

                    e.printStackTrace();
                }

            }
                    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
