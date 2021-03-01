package com.example.admin.mytripcart;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

/**
 * Created by Admin on 12/11/2015.
 */
public class LoginActivity extends Activity {
    SQLiteDatabase db;
    EditText et1, et2;
    Button login;
    ProgressBar progressBar;

    private SharedPreferences appSettings;
    Boolean isFirstTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        appSettings = getSharedPreferences("APP_NAME", MODE_PRIVATE);
        // Make sure you only run addShortcut() once, not to create duplicate shortcuts.
        if (!appSettings.getBoolean("shortcut", false)) {
            addShortcut();
        }
        progressBar = (ProgressBar) findViewById(R.id.progress);
        et1 = (EditText) findViewById(R.id.username);
        et2 = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.btnLogin);
        Boolean isFirstTime = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("isFirstTime", true);
        if (isFirstTime) {
            //   Toast.makeText(LoginActivity.this, "First Run", Toast.LENGTH_SHORT).show();
            getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putBoolean("isFirstTime", false).commit();
        } else {
            Intent in = new Intent(getApplicationContext(), SecondActivity.class);
            startActivity(in);
        }
        DatabaseHandler db = new DatabaseHandler(this);
        /**
         * CRUD Operations
         * */
        // Inserting Contacts
        Log.d("Insert: ", "Inserting ..");
         db.addContact(new Contact("UserName", "Password"));
        // Reading all contacts
//        Log.d("Reading: ", "Reading all contacts..");
//        List<Contact> contacts = db.getAllContacts();
//
//        for (Contact cn : contacts) {
//
//
//            String log = "Id: "+cn.getID()+" ,UserName: " + cn.get_username() + " ,Password: " + cn.get_password();
//            // Writing Contacts to log
//            Log.d("UserName: ", log);
//        }
//        SharedPreferences app_preferences = PreferenceManager
//                .getDefaultSharedPreferences(LoginActivity.this);
//
//        SharedPreferences.Editor editor = app_preferences.edit();
//        isFirstTime = app_preferences.getBoolean("isFirstTime", true);
//        if (isFirstTime) {
//            editor.putBoolean("isFirstTime", false);
//            editor.commit();
//
//        }else{
////app open directly
//        }
        // tv = (TextView) findViewById(R.id.textView1);
     //   db = openOrCreateDatabase("MyDatabase", MODE_PRIVATE, null);


      //     db.execSQL("insert into mytable values('" + username + "','" + password + "')");
        login.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                String userName=et1.getText().toString();
                String password=et2.getText().toString();
                DatabaseHandler.addContact(userName, password);
                   Intent intent =new Intent(LoginActivity.this,SecondActivity.class);
                     startActivity(intent);
//                sendsms();
//                gen7DigitNumber();
                if (et1.getText().toString().equals("admin") &&

                        et2.getText().toString().equals("admin")) {
                    //    progressBar.setVisibility(View.VISIBLE);
                    Object view=null;

                    final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setMessage("Authenticating...");
                    progressDialog.show();


                    //    String email = _emailText.getText().toString();
                    //   String password = _passwordText.getText().toString();

                    // TODO: Implement your own authentication logic here.

                    new android.os.Handler().postDelayed(
                            new Runnable() {
                                public void run() {
                                    // On complete call either onLoginSuccess or onLoginFailed
                                    //           onLoginSuccess();
                                    // onLoginFailed();
                                    progressDialog.dismiss();
                                }
                            }, 16000);

                    Intent intent1 = new Intent(LoginActivity.this, NewSecondActivity.class);
                    startActivity(intent1);
                    //      Toast.makeText(getApplicationContext(), "Redirecting...", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Wrong Credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


//    private void sendsms() {
//        String phoneNumber = "+9599525679";
//        String message = "Welcome to sms";
//        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + phoneNumber));
//        intent.putExtra("sms_body", message);
//        Toast.makeText(getApplicationContext(), "sms sent", Toast.LENGTH_SHORT).show();
//        startActivity(intent);
//    }
//    public String gen7DigitNumber()
//    {
//        Random rng = new Random();
//        int val = rng.nextInt(10000000);
//        return String.format("%07d", val);
//    }
    private void addShortcut() {
        //Adding shortcut for MainActivity
        //on Home screen
        Intent shortcutIntent = new Intent(getApplicationContext(), LoginActivity.class);
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
}

//        login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (et1.getText().toString().equals("admin") &&
//
//                        et2.getText().toString().equals("admin")) {
//                    Toast.makeText(getApplicationContext(), "Redirecting...", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(getApplicationContext(), "Wrong Credentials", Toast.LENGTH_SHORT).show();
//                }
//                });
//
//}
//    }
//}

//    HttpClient client = new DefaultHttpClient();
//    HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); //Timeout Limit
//    HttpResponse response;
//    JSONObject json = new JSONObject();
//    try{
//        HttpPost post = new HttpPost(URL);
//        post.setHeader("Content-type", "application/json");
//        json.put("username", userName);
//        json.put("password", password);
//        StringEntity se = new StringEntity( json.toString());
//        Log.v("json_text", json.toString());
//        se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
//        post.setEntity(se);
//        response = client.execute(post);
//                    /*Checking response */
//        if(response!=null){
//            String temp = EntityUtils.toString(response.getEntity()); //Get the data in the entity
//            Log.v("response", temp);
//        }}
//    catch(Exception e){
//        e.printStackTrace();
//        //createDialog("Error", "Cannot Estabilish Connection");
//    }
//    public void insert(View v)
//    {
//        String username=et1.getText().toString();
//        String password=et2.getText().toString();
//        et1.setText("");
//        et2.setText("");
//        db.execSQL("insert into mytable values('"+username+"','"+password+"')");
//        //   System.out.println(username+" "+password);
//        Toast.makeText(this, "values inserted successfully.", Toast.LENGTH_LONG).show();
//    }


