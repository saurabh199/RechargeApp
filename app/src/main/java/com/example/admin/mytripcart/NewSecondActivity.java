package com.example.admin.mytripcart;

/**
 * Created by Admin on 1/1/2016.
 */
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

//import com.startapp.android.publish.StartAppAd;

public class NewSecondActivity extends Activity {
    // private StartAppAd startAppAd;

    public String str = "";
    Character op = 'q';
    int i, num, numtemp;
    EditText showResult;
    Button call, back, contact, dial;
    MediaPlayer mp;
    private InputMethodManager im;
    MyPref myPrefs;
    ProgressBar progressBar;
    int count=0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newlayout);
        myPrefs = new MyPref(getApplicationContext());
        showResult = (EditText) findViewById(R.id.p1);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        showResult.addTextChangedListener(passwordWatcher);
        showResult.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int inType = showResult.getInputType(); // backup the input type
                showResult.setInputType(InputType.TYPE_NULL); // disable soft input
                showResult.onTouchEvent(event); // call native handler
                showResult.setInputType(inType); // restore input type
                return true;
            }
        });
//        startAppAd = new StartAppAd(this);
//        startAppAd.showAd();
//        startAppAd.loadAd();

        addListenerButton();

    }
    private final TextWatcher passwordWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //    textView.setVisibility(View.VISIBLE);
        }

        public void afterTextChanged(Editable s) {
            if (s.length() < 6) {
                //  textView.setVisibility(View.GONE);

            } else{

                final ProgressDialog progressDialog = new ProgressDialog(NewSecondActivity.this);
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
                Intent intent1 = new Intent(NewSecondActivity.this, MainActivity.class);
                startActivity(intent1);
                //  textView.setText("You have entered : " + passwordEditText.getText());
            }
        }
    };

    private void addListenerButton() {
      //  call = (Button) findViewById(R.id.call);
        back = (Button) findViewById(R.id.del);
        // contact = (Button) findViewById(R.id.contact);
        // dial = (Button) findViewById(R.id.dial);

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strUserName = showResult.getText().toString();


                if (strUserName.trim().equals("") || strUserName.trim().equals("")) {

                    Toast.makeText(NewSecondActivity.this, "plz dial number ", Toast.LENGTH_SHORT).show();
                } else {
                    System.out.println("string text" + showResult.getText().toString());
                    myPrefs.setWeight(showResult.getText().toString());
//                    Intent i = new Intent(MainActivity.this, Calling.class);
//                    startActivity(i);
                    finish();
                }
            }
        });
//        contact.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Intent i = new Intent(MainActivity.this, Dialer.class);
////                startActivity(i);
//                finish();
//            }
//
//
//        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mp = MediaPlayer.create(MainActivity.this, R.raw.beep);
//                mp.start();
//                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                    @Override
//                    public void onCompletion(MediaPlayer mp) {
//                        mp.stop();
//                        mp.release();
//                    }
//                });
                String text = showResult.getText().toString();

                if (text.length() > 0) {

                    showResult.setText(text.substring(0, text.length() - 1));
                    str = str.substring(0, str.length() - 1);
                    showResult.setSelection(showResult.getText().length());
                }
            }

        });

    }


//    @Override
//    protected void onPause() {
//       mp  super.onPause();
//       .pause();
//        mp.release();
//    }

    public void btn1Clicked(View v) {
        insert(7);
        showResult.setSelection(showResult.getText().length());
//        mp = MediaPlayer.create(MainActivity.this, R.raw.seven);
//        mp.start();
//        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mp) {
//                mp.stop();
//                mp.release();
//            }
//        });
    }

    public void btn2Clicked(View v) {
//        count++;
//        if(count==10){
//            startAppAd.showAd();
//            startAppAd.loadAd();
//            count=0;
//        }
        insert(8);
        showResult.setSelection(showResult.getText().length());
//        mp = MediaPlayer.create(MainActivity.this, R.raw.eight);
//        mp.start();
//        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mp) {
//                mp.stop();
//                mp.release();
//            }
//        });

    }

    public void btn3Clicked(View v) {
        insert(9);
        showResult.setSelection(showResult.getText().length());
//        mp = MediaPlayer.create(MainActivity.this, R.raw.nine);
//        mp.start();
//        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mp) {
//                mp.stop();
//                mp.release();
//            }
//        });
    }

    public void btn4Clicked(View v) {
        insert(4);

        showResult.setSelection(showResult.getText().length());
//        mp = MediaPlayer.create(MainActivity.this, R.raw.four);
//        mp.start();
//        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mp) {
//                mp.stop();
//                mp.release();
//            }
//        });

    }

    public void btn5Clicked(View v) {
        insert(5);
        showResult.setSelection(showResult.getText().length());
//        mp = MediaPlayer.create(MainActivity.this, R.raw.five);
//        mp.start();
//        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mp) {
//                mp.stop();
//                mp.release();
//            }
//        });
    }

    public void btn6Clicked(View v) {

        insert(6);
        showResult.setSelection(showResult.getText().length());
//        mp = MediaPlayer.create(MainActivity.this, R.raw.six);
//        mp.start();
//        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mp) {
//                mp.stop();
//                mp.release();
//            }
//        });
    }

    public void btn7Clicked(View v) {
        insert(1);
        showResult.setSelection(showResult.getText().length());
//        mp = MediaPlayer.create(MainActivity.this, R.raw.one);
//        mp.start();
//        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mp) {
//                mp.stop();
//                mp.release();
//            }
//        });
    }

    public void btn8Clicked(View v) {
        insert(2);
        showResult.setSelection(showResult.getText().length());
//        mp = MediaPlayer.create(MainActivity.this, R.raw.two);
//        mp.start();
//        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mp) {
//                mp.stop();
//                mp.release();
//            }
//        });
    }

    public void btn9Clicked(View v) {
        insert(3);
        showResult.setSelection(showResult.getText().length());
//        mp = MediaPlayer.create(MainActivity.this, R.raw.three);
//        mp.start();
//        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mp) {
//                mp.stop();
//                mp.release();
//            }
//        });
    }

    public void zero(View v) {

        insert(0);
        showResult.setSelection(showResult.getText().length());
//        mp = MediaPlayer.create(MainActivity.this, R.raw.zero);
//        mp.start();
//        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mp) {
//                mp.stop();
//                mp.release();
//            }
//        });
    }


    private void insert(int j) {
        if (str.length() < 10) {
            str = str + Integer.toString(j);
        }
        //  num = Integer.valueOf(str).intValue();
        showResult.setText(str);


    }
//    private void perform() {
//        // TODO Auto-generated method stub
//        str = "";
//        numtemp = num;
//    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Intent intent = new Intent(MainActivity.this, SelectActivity.class);
//        startActivity(intent);

        finish();
    }
}
