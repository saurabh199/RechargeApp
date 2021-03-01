//package com.example.admin.mytripcart;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.database.sqlite.SQLiteDatabase;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import java.io.BufferedInputStream;
//import java.io.BufferedReader;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.ArrayList;
//
///**
// * Created by Admin on 1/4/2016.
// */
//public class Login extends Activity {
//
//    DatabaseHandler db;
//    SQLiteDatabase sqLiteDatabase;
//    private static final String TAG = "Login";
//    TextView tv;
//    Button login;
//    EditText et1, et2;
//    private String pinnumber = "";
//    ArrayList  listAdapterData;
//    ArrayList list;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.login);
//        et1 = (EditText) findViewById(R.id.username);
//        et2 = (EditText) findViewById(R.id.password);
//        login = (Button) findViewById(R.id.btnLogin);
//        login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String username = et1.getText().toString();
//                String password = et2.getText().toString();
//                db.addContact(new Contact(username, password));
//
//            }
//
//        });
//
//        //  new MPINOperator().execute();
//        //  new MpinOperator().execute();
////        login.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                username = et1.getText().toString();
////                password = et2.getText().toString();
////                et1.setText("");
////               et2.setText("");
////
////
////                //insert data into able
////                db.execSQL("insert into mytable values('" + username + "','" + password + "')");
////                Toast.makeText(Login.this, "values inserted successfully.", Toast.LENGTH_LONG).show();
////            }
////        });
//        //create database if not already exist
//
//      //  db = openOrCreateDatabase("Mydb", MODE_PRIVATE, null);
//
//        //create new table if not already exist
//
//      //  db.execSQL("create table if not exists mytable(username varchar, password varchar)");
//        Log.d("Reading: ", "Reading all contacts..");
//      //  String log =  " UserName: " +mytable.username() + " ,Password: " + mytable.getpassword();
////            // Writing Contacts to log
////            Log.d("UserName: ", log);
//      // List<String> contacts = db.getAllContacts();
//        new LoginOperator().execute();
//    }
//    class LoginOperator extends AsyncTask<Void, Void, String> {
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//        @Override
//        protected String doInBackground(Void... voids) {
//            String result = "";
//            try {
//              //  System.out.println(username+"    "+password+" ");
//                URL url = new URL("http://180.151.246.171:8888/satkar/validateAndCreatePIN.htm?username=STPL0000052&password=abc");
//                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                conn.setRequestMethod("GET");
//                conn.connect();
//                InputStream in = new BufferedInputStream(conn.getInputStream());
//                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
//                String line = "";
//                while ((line = bufferedReader.readLine())!= null)
//                    result += line;
//                in.close();
//                conn.disconnect();
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return result;
//        }
//
//        @Override
//        protected void onPostExecute(final String result) {
//            super.onPostExecute(result);
//             pinnumber=result;
//            class MpinOpertaor extends AsyncTask<Void, Void, String> {
//                @Override
//                protected void onPreExecute() {
//                    super.onPreExecute();
//                }
//                @Override
//                protected String doInBackground(Void... voids) {
//                    String bal = "";
//                    try {
//                   System.out.println(username+"    "+password+"   "+pinnumber);
//                        URL url = new URL ("http://180.151.246.171:8888/satkar/validateUserWithPIN.htm?username="+username+"&password="+password+"&mpin="+pinnumber+")");
//                       HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                        conn.setRequestMethod("GET");
//                        conn.connect();
//                        InputStream in = new BufferedInputStream(conn.getInputStream());
//                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
//                        String line = "";
//                        while ((line = bufferedReader.readLine()) != null)
//                            bal += line;
//                        in.close();
//                        conn.disconnect();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    return bal;
//                }
//                @Override
//                protected void onPostExecute(String bal) {
//                    super.onPostExecute(bal);
//                    try
//                    {
//                          if(bal.equals("INVALID"))
//                            {
//
//                       Toast.makeText(getApplicationContext(), "Wrong Credentials", Toast.LENGTH_SHORT).show();
//                         }
//                        if(result.equals("VALID"))
//                        {
//                            Intent intent =new Intent(Login.this,SecondActivity.class);
//                            startActivity(intent);
//                    }
//            /*if(username="username"&&password="password"&&mpin="pinnumber") {
//            }*/
//                       /* String username =et1.getText().toString();
//                        String password =et2.getText().toString();
//                        String pin = pinnumber.getBytes().toString();*/
//
//                        System.out.println("success or not      " + result);
//                    }catch(Exception e)
//                    {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }
//    }
//}