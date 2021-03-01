package com.example.admin.mytripcart;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
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

public class SecondActivity extends Activity {
    private String pinnumber = "";

    public String str1 = "";
    public String str2 = "";
    public String str3 = "";
    public String str4 = "";
    public String str5 = "";
    public String str6 = "";
    String username = "";
    String password = "";
    Button forget;
    Character op = 'q';
    int i, num, numtemp;
    EditText showResult1, showResult2, showResult3, showResult4, showResult5, showResult6, showResult, nextResult;
    Button back;
    ProgressDialog progressDialog;
    private InputMethodManager im;
    MyPref myPrefs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);

        myPrefs = new MyPref(getApplicationContext());
        showResult1 = (EditText) findViewById(R.id.p1);
        showResult2 = (EditText) findViewById(R.id.p2);
        showResult3 = (EditText) findViewById(R.id.p3);
        showResult4 = (EditText) findViewById(R.id.p4);
        showResult5 = (EditText) findViewById(R.id.p5);
        showResult6 = (EditText) findViewById(R.id.p6);

        forget = (Button) findViewById(R.id.forget);
        back = (Button) findViewById(R.id.del);
        showResult1.setGravity(Gravity.CENTER);
        showResult2.setGravity(Gravity.CENTER);
        showResult3.setGravity(Gravity.CENTER);
        showResult4.setGravity(Gravity.CENTER);
        showResult5.setGravity(Gravity.CENTER);
        showResult6.setGravity(Gravity.CENTER);

        showResult6.addTextChangedListener(passwordWatcher);
        showResult1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int inType = showResult1.getInputType(); // backup the input type
                showResult1.setInputType(InputType.TYPE_NULL); // disable soft input
                showResult1.onTouchEvent(event); // call native handler
                showResult1.setInputType(inType); // restore input type
                return true;
            }
        });
        showResult2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int inType = showResult2.getInputType(); // backup the input type
                showResult2.setInputType(InputType.TYPE_NULL); // disable soft input
                showResult2.onTouchEvent(event); // call native handler
                showResult2.setInputType(inType); // restore input type
                return true;
            }
        });
        showResult3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int inType = showResult3.getInputType(); // backup the input type
                showResult3.setInputType(InputType.TYPE_NULL); // disable soft input
                showResult3.onTouchEvent(event); // call native handler
                showResult3.setInputType(inType); // restore input type
                return true;
            }
        });
        showResult4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int inType = showResult4.getInputType(); // backup the input type
                showResult4.setInputType(InputType.TYPE_NULL); // disable soft input
                showResult4.onTouchEvent(event); // call native handler
                showResult4.setInputType(inType); // restore input type
                return true;
            }
        });
        showResult5.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int inType = showResult5.getInputType(); // backup the input type
                showResult5.setInputType(InputType.TYPE_NULL); // disable soft input
                showResult5.onTouchEvent(event); // call native handler
                showResult5.setInputType(inType); // restore input type
                return true;
            }
        });
        showResult6.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int inType = showResult6.getInputType(); // backup the input type
                showResult6.setInputType(InputType.TYPE_NULL); // disable soft input
                showResult6.onTouchEvent(event); // call native handler
                showResult6.setInputType(inType); // restore input type
                return true;
            }
        });
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ForgetOperator().execute();
                System.out.println("   " + pinnumber);
            }
        });
//        showResult1.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                int inType1 = showResult1.getInputType(); // backup the input type
//                showResult1.setInputType(InputType.TYPE_NULL); // disable soft input
//                showResult1.onTouchEvent(event); // call native handler
//                showResult1.setInputType(inType1); // restore input type
//                return true;
//            }
//        });
//
//        showResult2.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                int inType2 = showResult2.getInputType(); // backup the input type
//                showResult2.setInputType(InputType .TYPE_NULL); // disable soft input
//                showResult2.onTouchEvent(event); // call native handler
//                showResult2.setInputType(inType2); // restore input type
//                return true;
//            }
//        });
//
//        showResult3.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                int inType3 = showResult3.getInputType(); // backup the input type
//                showResult3.setInputType(InputType.TYPE_NULL); // disable soft input
//                showResult3.onTouchEvent(event); // call native handler
//                showResult3.setInputType(inType3); // restore input type
//                return true;
//            }
//        });
//
//        showResult4.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                int inType4 = showResult4.getInputType(); // backup the input type
//                showResult4.setInputType(InputType.TYPE_NULL); // disable soft input
//                showResult4.onTouchEvent(event); // call native handler
//                showResult4.setInputType(inType4); // restore input type
//                return true;
//            }
//        });
//
//        showResult5.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                int inType5 = showResult5.getInputType(); // backup the input type
//                showResult5.setInputType(InputType.TYPE_NULL); // disable soft input
//                showResult5.onTouchEvent(event); // call native handler
//                showResult5.setInputType(inType5); // restore input type
//                return true;
//            }
//        });
//
//        showResult6.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                int inType6 = showResult6.getInputType(); // backup the input type
//                showResult6.setInputType(InputType.TYPE_NULL); // disable soft input
//                showResult6.onTouchEvent(event); // call native handler
//                showResult6.setInputType(inType6); // restore input type
//                return true;
//            }
//        });
        addListenerButton();
    }

    private final TextWatcher passwordWatcher = new TextWatcher() {

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //    textView.setVisibility(View.VISIBLE);
        }

        public void afterTextChanged(Editable s) {

            String pin1 = showResult1.getText().toString();
            String pin2 = showResult2.getText().toString();
            String pin3 = showResult3.getText().toString();
            String pin4 = showResult4.getText().toString();
            String pin5 = showResult5.getText().toString();
            String pin6 = showResult6.getText().toString();

            if (!pin1.equalsIgnoreCase("") && !pin2.equalsIgnoreCase("") && !pin3.equalsIgnoreCase("")
                    && !pin4.equalsIgnoreCase("") && !pin5.equalsIgnoreCase("") && !pin6.equalsIgnoreCase("")) {
                {

                    progressDialog = new ProgressDialog(SecondActivity.this);
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
                            }, 16000);

//                Intent intent1 = new Intent(SecondActivity.this, MainActivity.class);
//                startActivity(intent1);

                    pinnumber = pin1 + "" + pin2 + ""
                            + pin3 + "" + pin4 + ""
                            + pin5 + "" + pin6;
                    System.out.println("pinvalue-" + pinnumber);

                    new MpinOperator().execute();
                    //  textView.setText("You have entered : " + passwordEditText.getText());
                }
            }

            /*if (s.length() < 1) {
                //  textView.setVisibility(View.GONE);

            } else */
        }
    };


    private void addListenerButton() {
    }

    public void btn1Clicked(View v) {
        showResult = (EditText) findViewById(getCurrentFocus().getId());
        insert(1, showResult);
        if (!showResult6.hasFocus()) {
            nextResult = (EditText) findViewById(getCurrentFocus().getId() + 1);
            nextResult.requestFocus();
        }
    }

    public void btn2Clicked(View v) {
        showResult = (EditText) findViewById(getCurrentFocus().getId());
        insert(2, showResult);
        if (!showResult6.hasFocus()) {
            nextResult = (EditText) findViewById(getCurrentFocus().getId() + 1);
            nextResult.requestFocus();
        }
    }

    public void btn3Clicked(View v) {
        showResult = (EditText) findViewById(getCurrentFocus().getId());
        insert(3, showResult);
        if (!showResult6.hasFocus()) {
            nextResult = (EditText) findViewById(getCurrentFocus().getId() + 1);
            nextResult.requestFocus();
        }
    }

    public void btn4Clicked(View v) {
        showResult = (EditText) findViewById(getCurrentFocus().getId());
        insert(4, showResult);
        if (!showResult6.hasFocus()) {
            nextResult = (EditText) findViewById(getCurrentFocus().getId() + 1);
            nextResult.requestFocus();
        }
    }

    public void btn5Clicked(View v) {
        showResult = (EditText) findViewById(getCurrentFocus().getId());
        insert(5, showResult);
        if (!showResult6.hasFocus()) {
            nextResult = (EditText) findViewById(getCurrentFocus().getId() + 1);
            nextResult.requestFocus();
        }
    }

    public void btn6Clicked(View v) {
        showResult = (EditText) findViewById(getCurrentFocus().getId());

        insert(6, showResult);


        if (!showResult6.hasFocus()) {
            nextResult = (EditText) findViewById(getCurrentFocus().getId() + 1);

            nextResult.requestFocus();

        }
    }

    public void btn7Clicked(View v) {
        showResult = (EditText) findViewById(getCurrentFocus().getId());
        insert(7, showResult);
        if (!showResult6.hasFocus()) {
            nextResult = (EditText) findViewById(getCurrentFocus().getId() + 1);
            nextResult.requestFocus();
        }
    }

    public void btn8Clicked(View v) {
        showResult = (EditText) findViewById(getCurrentFocus().getId());

        insert(8, showResult);
        if (!showResult6.hasFocus()) {
            nextResult = (EditText) findViewById(getCurrentFocus().getId() + 1);
            nextResult.requestFocus();
        }
    }

    public void btn9Clicked(View v) {
        showResult = (EditText) findViewById(getCurrentFocus().getId());
        insert(9, showResult);
        if (!showResult6.hasFocus()) {
            nextResult = (EditText) findViewById(getCurrentFocus().getId() + 1);
            nextResult.requestFocus();
        }
    }

    public void zeroClicked(View v) {
        showResult = (EditText) findViewById(getCurrentFocus().getId());
        insert(0, showResult);
        if (!showResult6.hasFocus()) {
            nextResult = (EditText) findViewById(getCurrentFocus().getId() + 1);
            nextResult.requestFocus();
        }
    }

    public void delete(View v) {

        if (!showResult1.hasFocus()) {
          showResult = (EditText) findViewById(getCurrentFocus().getId());
            showResult.setSelection(nextResult.getText().length());

            nextResult = (EditText) findViewById(getCurrentFocus().getId() - 1);
            String text = nextResult.getText().toString();
            if (text.length() > 0) {

                nextResult.setText(text.substring(0, text.length() - 1));

                if (nextResult.length() == 0) {
                    showResult6.setText("");
                }
                nextResult.requestFocus();
            }

        }
    }

       /* showResult = (EditText) findViewById(getCurrentFocus().getId());

        if (!showResult6.hasFocus())
            nextResult = (EditText) findViewById(getCurrentFocus().getId());
        else
            nextResult = (EditText) findViewById(getCurrentFocus().getId() - 1);
        String text = nextResult.getText().toString();

        if (text.length() > 0) {
            //  text = nextResult.getText().toString();
            nextResult.setText(text.substring(0, text.length() - 1));
            nextResult = (EditText) findViewById(getCurrentFocus().getId());
            nextResult.requestFocus();
            showResult5.requestFocus();
            nextResult = (EditText) findViewById(getCurrentFocus().getId());
            showResult6.setText(text.substring(0, text.length() - 1));
            showResult.requestFocus();*/
//            if (showResult.length() == 0) {
//
//                nextResult = (EditText) findViewById(getCurrentFocus().getId());
//              //  nextResult.requestFocus();
//             //   nextResult = (EditText) findViewById(getCurrentFocus().getId() - 1);
//            }




//    if(showResult6.hasFocus())
//
//    {
//        nextResult = (EditText) findViewById(getCurrentFocus().getId() - 1);
//        nextResult.requestFocus();
//        nextResult = (EditText) findViewById(getCurrentFocus().getId() + 1);
//    }
//
//    else
//
//    {
//        nextResult.requestFocus();
//    }


            /*if (showResult6.hasFocus()){
                nextResult = (EditText) findViewById(getCurrentFocus().getId() - 1);
                nextResult.requestFocus();
                nextResult = (EditText) findViewById(getCurrentFocus().getId()+1);
            }else{*/

           // }

    private void insert(int j, EditText showResult) {
        showResult.setText(Integer.toString(j));
    }

    DatabaseHandler db;

    class MpinOperator extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
            String balance = "";

            try {
                db = new DatabaseHandler(getApplicationContext());

                List<Contact> listname = db.getAllContacts();

                if (listname != null && listname.size() > 0) {
                    username = listname.get(0).get_username();
                    password = listname.get(0).get_password();
                }
                System.out.println("Username password pin  --   " + username + "   " + password + "     " + pinnumber);
//                pinnumber=showResult1.getText().toString()+""+
                URL url = new URL("http://180.151.246.171:8888/satkar/validateUserWithPIN.htm?username=" + username + "&password=" + password + "&mpin=" + pinnumber);
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
                Toast.makeText(getApplicationContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
            }

            return balance;
        }

        @Override
        protected void onPostExecute(String balance) {
            super.onPostExecute(balance);
            try {

                System.out.println("Balance   " + balance);
                if (!balance.equalsIgnoreCase("INVALID")) {
                    Intent intent = new Intent(SecondActivity.this, MainActivity.class);
                    intent.putExtra("balance",balance);
                    startActivity(intent);

                } else {
//                    final ProgressDialog progressDialog = new ProgressDialog(SecondActivity.this);
//                    progressDialog.setIndeterminate(true);
//                    progressDialog.setMessage("Authenticating...");
//                    progressDialog.dismiss();
//                    new android.os.Handler().postDelayed(
//                            new Runnable() {
//                                public void run() {
//                                    // On complete call either onLoginSuccess or onLoginFailed
//                                    //           onLoginSuccess();
//                                    // onLoginFailed();
                                    progressDialog.dismiss();

                          //  }, 16000);
                    Toast.makeText(getApplicationContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    class ForgetOperator extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
            String balance = "";
            try {

                db = new DatabaseHandler(getApplicationContext());

                List<Contact> listn = db.getAllContacts();

                if (listn != null && listn.size() > 0) {
                    username = listn.get(0).get_username();
                    password = listn.get(0).get_password();
                }

                System.out.println("Username password   --   " + username + password);
                URL url = new URL("http://180.151.246.171:8888/satkar/validateAndCreatePIN.htm?username=" + username + "&password=" + password);
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
            String status = "";
            String mobileno = "";
            try {

                JSONArray jsonArray = new JSONArray(balance);
                for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                status = jsonObject.getString("status");
                pinnumber = jsonObject.getString("mobilepin");
                mobileno = jsonObject.getString("mobileno");
                  System.out.println("   " + status + "    " + pinnumber + "  " + mobileno);
              }

                if (!status.equalsIgnoreCase("INVALID")) {
                    String message = "Your Mobile PIN is " + pinnumber;
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(mobileno, null, message, null, null);
                    Toast.makeText(getApplicationContext(), "Mobile PIN sent to your registered Mobile Number!",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
//                    progressDialog.setIndeterminate(false);
//                    progressDialog.setMessage("Authenticating...");
//                    progressDialog.dismiss();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
//    @Override
//    public void onBackPressed() {
//        moveTaskToBack(true);
//    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                new ContextThemeWrapper(SecondActivity.this, android.R.style.Theme_Holo_Light_Dialog));

        alertDialogBuilder.setTitle("Mytripcart").setIcon(R.mipmap.ic_launcher);
        alertDialogBuilder.setTitle("Mytripcart");
        alertDialogBuilder
                .setMessage("Do you want to exit ?")
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                moveTaskToBack(true);
                                }
                        })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
