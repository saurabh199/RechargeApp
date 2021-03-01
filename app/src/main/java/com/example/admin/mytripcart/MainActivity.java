package com.example.admin.mytripcart;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView responseData;
    private ProgressBar progressBar;
    private Button button, recharge;
DatabaseHandler db;
    TextView textView;
String username="";
    String password="";
    String status = "";
    String mobileno = "";
    String pinnumber="";
SecondActivity secondActivity;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.balance);
        recharge = (Button) findViewById(R.id.recharge);
        textView = (TextView) findViewById(R.id.balance);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("balance");
            textView.setText("Rs "+value);
        }

        android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.setDisplayShowHomeEnabled(true);
        ab.setIcon(R.drawable.mytripcart_inner);
        //  setTitle("Mytripcart");
        //   initview();

        send();

    }
    private void send() {
        recharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog = ProgressDialog.show(MainActivity.this, "", "Loading...");

                new Thread() {

                    public void run() {
                        try{
                            sleep(10000);
                        } catch (Exception e) {

                            Log.e("tag", e.getMessage());

                        }

// dismiss the progress dialog

                        progressDialog.dismiss();

                    }

                }.start();

                Intent ink = new Intent(MainActivity.this, MainFragment.class);
                startActivity(ink);
            }
        });
    }

    private void initview() {
        responseData = (TextView) findViewById(R.id.text);
        //   button = (Button) findViewById(R.id.button);
        progressBar = (ProgressBar) findViewById(R.id.progress);
    }



    /*class BalanceOperator extends AsyncTask<Void,Void,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
            String Balance = "";
            try {
                db = new DatabaseHandler(getApplicationContext());

                List<Contact> listn = db.getAllContacts();

                if (listn != null && listn.size() > 0) {
                    username = listn.get(0).get_username();
                    password = listn.get(0).get_password();
                }
                URL url = new URL("http://180.151.246.171:8888/satkar/validateAndCreatePIN.htm?username=" + username + "&password=" + password);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();
                InputStream in = new BufferedInputStream(conn.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
                String line = "";
                while((line = bufferedReader.readLine()) != null)
                    Balance += line;
                in.close();
                conn.disconnect();

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            return Balance;

        }

        @Override
        protected void onPostExecute(String Balance) {
            super.onPostExecute(Balance);
            try {

                JSONArray jsonArray = new JSONArray(Balance);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    status = jsonObject.getString("status");
                   pinnumber = jsonObject.getString("mobilepin");
                    mobileno = jsonObject.getString("mobileno");
                    textView.setText("Balance: " + pinnumber);


                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }

    }*/














    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logout) {
            Intent intent = new Intent(this, SecondActivity.class);
            startActivity(intent);
        }
        if(id==R.id.history)
        {
            Intent intent1=new Intent(this,HistoryActivity.class);
            startActivity(intent1);
        }

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    //    DatabaseHandler db;

//                              progressDialog = new ProgressDialog(NewLoginActivity.this);
//                              progressDialog.setIndeterminate(true);
//                              progressDialog.setMessage("Authenticating...");
//                              progressDialog.show();

                    //   Intent intent = new Intent(NewLoginActivity.this, SecondActivity.class);
                    //     startActivity(intent);

                }

//                          if (!status.equalsIgnoreCase("INVALID")) {
//
//                              db.addContact(new Contact(username, password));
//                              Intent intent1 = new Intent(NewLoginActivity.this, SecondActivity.class);
//                              startActivity(intent1);
//
//
//                              String message = "Your Mobile PIN is " + pinnumber;
//                              SmsManager smsManager = SmsManager.getDefault();
//                              smsManager.sendTextMessage(mobileno, null, message, null, null);
//                              Toast.makeText(getApplicationContext(), "Mobile PIN sent to your registered Mobile Number!",
//                                      Toast.LENGTH_LONG).show();
//
//
////                    Intent intent = new Intent(NewLoginActivity.this, SecondActivity.class);
////                    startActivity(intent);
//
//                          } else {

//                    registerReceiver(new BroadcastReceiver(){
//                    @Override
//                    public void onReceive(Context arg0, Intent arg1) {
//                        switch (getResultCode())
//                        {
//                            case Activity.RESULT_OK:
//                                Toast.makeText(getApplicationContext(), "SMS Recived!",
//                                        Toast.LENGTH_LONG).show();
//                                break;
//                            case Activity.RESULT_CANCELED:
//                                Toast.makeText(getApplicationContext(), "SMS not Recived!",
//                                        Toast.LENGTH_LONG).show();
//                                break;
//                        }
//                    }
//                }, new IntentFilter(DELIVERED));
                   /* progressDialog.setIndeterminate(true);
                    progressDialog = new ProgressDialog(NewLoginActivity.this);*/
                //progressDialog.dismiss();

                  /*  if(!username.equalsIgnoreCase(username)&&!password.equalsIgnoreCase(password))
                    {
                        Intent intent = new Intent(NewLoginActivity.this, SecondActivity.class);
                        startActivity(intent);
                    }*/

                //     Toast.makeText(getApplicationContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show();








                  //        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                requestDataFromServer();
//            }
//
//            private void requestDataFromServer() {
//                progressBar.setVisibility(View.VISIBLE);
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("deviceId", getDeviceId());
//                ApplicationFL.getInstance().addToRequestQueue(requestData("http://fundoapp.com/rcregistration.asmx/totalMoney", params), "requestSample");
//            }

//            private CustomJsonObjectRequest requestData(String url, Map<String, String> params) {
//                CustomJsonObjectRequest customJsonObjectRequest = new CustomJsonObjectRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        Log.e("response", response.toString());
//                        try {
//                            if (response.getString("responseCode").equals("1")) {
//                                responseData.setText("Remaining Balance: " + response.getString("remainingBalance"));
//                            } else if (response.getString("responseCode").equals("0")) {
//                                responseData.setText(" " + response.getString("remainingBalance"));
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                        progressBar.setVisibility(View.GONE);
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        progressBar.setVisibility(View.GONE);
//                    }
//                });
//                return customJsonObjectRequest;
//            }

                  //            private String getDeviceId() {
//                String identifier = null;
//                TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//                if (tm != null)
//                    identifier = tm.getDeviceId();
//                if (identifier == null || identifier.length() == 0)
//                    identifier = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
//                return identifier;
//            }
//        });
//    }
//    class MpinOperator extends AsyncTask<Void, Void, String> {
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//        @Override
//        protected String doInBackground(Void... voids) {
//            String balance = "";
//
//            try {
//                db = new DatabaseHandler(getApplicationContext());
//
//                List<Contact> listname = db.getAllContacts();
//
//                if (listname != null && listname.size() > 0) {
//                    username = listname.get(0).get_username();
//                    password = listname.get(0).get_password();
//
//                }
//                System.out.println("Username password pin  --   " + username + "   " + password + "     " + pinnumber);
////                pinnumber=showResult1.getText().toString()+""+
//                URL url = new URL("http://180.151.246.171:8888/satkar/validateUserWithPIN.htm?username=" + username + "&password=" + password + "&mpin=" + pinnumber);
//                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                conn.setRequestMethod("GET");
//                conn.connect();
//                InputStream in = new BufferedInputStream(conn.getInputStream());
//                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
//                String line = "";
//                while ((line = bufferedReader.readLine()) != null)
//                    balance += line;
//                in.close();
//                conn.disconnect();
//
//            } catch (Exception e) {
//                e.printStackTrace();
//                Toast.makeText(getApplicationContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
//            }
//
//            return balance;
//        }
//
//        @Override
//        protected void onPostExecute(String balance) {
//            super.onPostExecute(balance);
//            try {
//
//                System.out.println("Balance   " + balance);
//                if (!balance.equalsIgnoreCase("INVALID")) {
//                    Intent intent = new Intent(MainActivity.this, MainFragment.class);
//                    startActivity(intent);
//
//                } else {
//                    final ProgressDialog progressDialog = new ProgressDialog(SecondActivity.this);
//                    progressDialog.setIndeterminate(true);
//                    progressDialog.setMessage("Authenticating...");
//                    progressDialog.hide();
//                    new android.os.Handler().postDelayed(
//                            new Runnable() {
//                                public void run() {
//                                    // On complete call either onLoginSuccess or onLoginFailed
//                                    //           onLoginSuccess();
//                                    // onLoginFailed();
//                                    progressDialog.dismiss();
//                                }
//                            }, 16000);
//                    Toast.makeText(getApplicationContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
//
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
