package com.example.admin.mytripcart;

import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;


public class HistoryActivity extends AppCompatActivity {

    private ArrayList<RechargeReport> rechargeReports=new ArrayList<RechargeReport>();
    private CustomAdapter customAdapter;
    private ListView listView;
    private ProgressBar progressBar;
    DatePicker datePicker;
  /*  private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateView;
    private int year, month, day;*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_histoty);
        listView=(ListView)findViewById(R.id.listview);
//        dateView = (TextView) findViewById(R.id.textView3);
//        calendar = Calendar.getInstance();
//        year = calendar.get(Calendar.YEAR);

        android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.setDisplayShowHomeEnabled(true);
         ab.setIcon(R.drawable.mytripcart_inner);

//        month = calendar.get(Calendar.MONTH);
//        day = calendar.get(Calendar.DAY_OF_MONTH);
//        showDate(year, month+1, day);
        progressBar=(ProgressBar)findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);
        new PrePaidOperator().execute();

    }
//    @SuppressWarnings("deprecation")
//    public void setDate(View view) {
//        showDialog(999);
//        Toast.makeText(getApplicationContext(), "ca", Toast.LENGTH_SHORT)
//                .show();
//    }
//
//    @Override
//    protected Dialog onCreateDialog(int id) {
//        // TODO Auto-generated method stub
//        if (id == 999) {
//            return new DatePickerDialog(this, myDateListener, year, month, day);
//        }
//        return null;
//    }
//
//    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
//        @Override
//        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
//            // TODO Auto-generated method stub
//            // arg1 = year
//            // arg2 = month
//            // arg3 = day
//            showDate(arg1, arg2+1, arg3);
//        }
//    };
//
//    private void showDate(int year, int month, int day) {
//        dateView.setText(new StringBuilder().append(day).append("/")
//                .append(month).append("/").append(year));
//    }
private void showDatePicker() {
    DatePickerFragment date = new DatePickerFragment();
    /**
     * Set Up Current Date Into dialog
     */
    Calendar calender = Calendar.getInstance();
    Bundle args = new Bundle();
    args.putInt("year", calender.get(Calendar.YEAR));
    args.putInt("month", calender.get(Calendar.MONTH));
    args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
    date.setArguments(args);
    /**
     * Set Call back to capture selected date
     */
    date.setCallBack(ondate);
    date.show(getSupportFragmentManager(), "Date Picker");
}

    DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
           /* Toast.makeText(
                    HistoryActivity.this,
                    String.valueOf(year) + "-" + String.valueOf(monthOfYear)
                            + "-" + String.valueOf(dayOfMonth),
                    Toast.LENGTH_LONG).show();*/
        }
    };
    class PrePaidOperator extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
            String result = "";
            try {

                URL url = new URL("http://180.151.246.171:8888/satkar/rechargeReportFromApp.htm?username=STPL0000052");
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
                JSONArray jsonArray = new JSONArray(result);
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    RechargeReport rechargeReport = new RechargeReport();

                    rechargeReport.setAmount(jsonObject.getInt("amount"));
                    rechargeReport.setItemdesc(jsonObject.getString("itemdesc"));
                    rechargeReport.setRspcomm(jsonObject.getInt("rspcomm"));
                    rechargeReport.setCompcomm(jsonObject.getInt("compcomm"));
                    rechargeReport.setFrncomm(jsonObject.getInt("frncomm"));
                    rechargeReport.setDistcomm(jsonObject.getInt("distcomm"));
                    rechargeReport.setChargestatus(jsonObject.getString("chargestatus"));
                    rechargeReport.setTerminalid(jsonObject.getString("terminalid"));
                    rechargeReport.setChargeno(jsonObject.getLong("chargeno"));
                    rechargeReports.add(rechargeReport);

                }

            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Network Error Please try again", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            progressBar.setVisibility(View.GONE);
            customAdapter = new CustomAdapter(HistoryActivity.this, rechargeReports);
            listView.setAdapter(customAdapter);
        }
        }
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.date, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            if (id == R.id.date) {
                showDatePicker();
            }
            return super.onOptionsItemSelected(item);
        }
    }
