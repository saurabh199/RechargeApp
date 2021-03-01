package com.example.admin.mytripcart;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by Admin on 1/5/2016.
 */
public class NewLoginActivity extends Activity {
    EditText et1, et2;
    Button button;
    DatabaseHandler db;
    SQLiteDatabase sqLiteDatabase;
    private String pinnumber = "";
    String username = "";
    String password = "";
    ProgressBar progressBar;
    private SharedPreferences appSettings;
    Boolean isFirstTime;
    Dialog myDialog;
    String status = "";
    ProgressDialog progressDialog ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Boolean isFirstTime = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("isFirstTime", true);
        appSettings = getSharedPreferences("APP_NAME", MODE_PRIVATE);
        // Make sure you only run addShortcut() once, not to create duplicate shortcuts.
        if (!appSettings.getBoolean("shortcut", false)) {
                  addShortcut();
        }

        db = new DatabaseHandler(this);

        et1 = (EditText) findViewById(R.id.username);
        et2 = (EditText) findViewById(R.id.password);
        button = (Button) findViewById(R.id.btnLogin);

        db = new DatabaseHandler(getApplicationContext());
        List<Contact> listname = db.getAllContacts();

        if (listname != null && listname.size() > 0) {
            Intent in = new Intent(getApplicationContext(),SecondActivity.class);
             startActivity(in);
           /* username = listname.get(0).get_username();
            password = listname.get(0).get_password();
            Intent intent = new Intent(NewLoginActivity.this, NewSecondActivity.class);
            System.out.println("Username"+username+"Password"+password);*/
        }else{
           // Intent in = new Intent(getApplicationContext(), NewLoginActivity.class);
          //  startActivity(in);
        }
       /* else  if (isFirstTime) {

            //   Toast.makeText(LoginActivity.this, "First Run", Toast.LENGTH_SHORT).show();
            getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putBoolean("isFirstTime", false).commit();

        }*/


//         else {
//
//        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = et1.getText().toString();
                password = et2.getText().toString();

              //  if(!username.equals(username)) && !password.equals(password))

                new ValidateUser().execute();

            }
        });
    }

  class ValidateUser extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
            String result = "";
            try {
                System.out.println("Username password   --   " + username + password);
                URL url = new URL("http://180.151.246.171:8888/satkar/validateAndCreatePIN.htm?username=" + username + "&password=" + password);
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
            String status = "";
            String mobileno = "";
            try {
                JSONArray jsonArray = new JSONArray(result);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    status = jsonObject.getString("status");
                    pinnumber = jsonObject.getString("mobilepin");
                    mobileno = jsonObject.getString("mobileno");
                    System.out.println("   " + status + "    " + pinnumber + "  " + mobileno);
                    progressDialog = new ProgressDialog(NewLoginActivity.this);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setMessage("Authenticating...");
                    progressDialog.show();

                 //   Intent intent = new Intent(NewLoginActivity.this, SecondActivity.class);
               //     startActivity(intent);

                }

                if (!status.equalsIgnoreCase("INVALID")) {

                    db.addContact(new Contact(username, password));
                    Intent intent1 = new Intent(NewLoginActivity.this, SecondActivity.class);
                    startActivity(intent1);


                    String message = "Your Mobile PIN is " + pinnumber;
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(mobileno, null, message, null, null);
                    Toast.makeText(getApplicationContext(), "Mobile PIN sent to your registered Mobile Number!",
                            Toast.LENGTH_LONG).show();


//                    Intent intent = new Intent(NewLoginActivity.this, SecondActivity.class);
//                    startActivity(intent);

                } else {

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
                    progressDialog.dismiss();

                  /*  if(!username.equalsIgnoreCase(username)&&!password.equalsIgnoreCase(password))
                    {
                        Intent intent = new Intent(NewLoginActivity.this, SecondActivity.class);
                        startActivity(intent);
                    }*/

                    Toast.makeText(getApplicationContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show();

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

//        @Override
//        public void onBackPressed() {
//            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
//                    new ContextThemeWrapper(NewLoginActivity.this, android.R.style.Theme_Holo_Light_Dialog));
//            alertDialogBuilder.setTitle("Mytripcart").setIcon(R.mipmap.ic_launcher);
//            alertDialogBuilder.setTitle("Mytripcart");
//            alertDialogBuilder
//                    .setMessage("Do you want to exit ?")
//                    .setCancelable(false)
//                    .setPositiveButton("Yes",
//                            new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                    finish();
//                                }
//                            })
//                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            dialog.cancel();
//                        }
//                    })
//                    .setNeutralButton("More Apps",
//                            new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog,
//                                                    int which) {
//                                  //  Intent iMore = new Intent(
//                                         //   Intent.ACTION_VIEW,
//                                           // Uri.parse("https://play.google.com/store/apps/developer?id=AMTEE%20Apps&hl=en"));
//                                   // startActivity(iMore);
//                                }
//                            });
//            AlertDialog alertDialog = alertDialogBuilder.create();
//            alertDialog.show();
//        }


        /*class MpinOperator extends AsyncTask<Void, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(Void... voids) {
                String balance = "";
                System.out.println("Username password pin  --   "+username+"   "+password+"     "+pinnumber);
                try {
                    URL url = new URL("http://180.151.246.171:8888/satkar/validateUserWithPIN.htm?username="+username+"&password="+password+"&mpin="+pinnumber);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.connect();
                    InputStream in = new BufferedInputStream(conn.getInputStream());
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
                    String line = "";
                    while ((line = bufferedReader.readLine()) != null)
                        balance += line;
                    in.close();
                    conn.disconnect();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                return balance;
            }

            @Override
            protected void onPostExecute(String balance) {
                super.onPostExecute(balance);
                try {

                    if(!balance.equalsIgnoreCase("INVALID")) {
                        System.out.println("Balance   " + balance);

                            Intent intent=new Intent(NewLoginActivity.this,SecondActivity.class);
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show();

                        }
                    }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }*/
    }
    private void addShortcut() {
        //Adding shortcut for MainActivity
        //on Home screen
        Intent shortcutIntent = new Intent(getApplicationContext(), NewLoginActivity.class);
        shortcutIntent.setAction(Intent.ACTION_MAIN);

        Intent addIntent = new Intent();
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "Mytripcart");
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
                Intent.ShortcutIconResource.fromContext(getApplicationContext(),
                 R.drawable.ic_launcher));
        addIntent.putExtra("duplicate", false);
        addIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
        getApplicationContext().sendBroadcast(addIntent);

        SharedPreferences.Editor prefEditor = appSettings.edit();
        prefEditor.putBoolean("shortcut", true);
        prefEditor.commit();
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                new ContextThemeWrapper(NewLoginActivity.this, android.R.style.Theme_Holo_Light_Dialog));
        alertDialogBuilder.setTitle("Mytripcart").setIcon(R.mipmap.ic_launcher);
        alertDialogBuilder.setTitle("Mytripcart");
        alertDialogBuilder
                .setMessage("Do you want to exit ?")
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                            }
                        })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
//                .setNeutralButton("",
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog,
//                                                int which) {
////                                Intent iMore = new Intent(
////                                        Intent.ACTION_VIEW,
////                                        Uri.parse("https://play.google.com/store/apps/developer?id=AMTEE%20Apps&hl=en"));
////                                startActivity(iMore);
//                            }
//                        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}





