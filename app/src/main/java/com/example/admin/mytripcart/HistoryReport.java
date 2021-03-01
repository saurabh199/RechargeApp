package com.example.admin.mytripcart;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class HistoryReport extends Activity {
    String username = "";
    DatabaseHandler db;
    ListView listView;
    private LinkedHashMapAdapter<String, String> acadapter;
    /*String itemdesc = "";
    String rspcomm = "";
    String compcomm = "";
    String frncomm = "";
    String distcomm = "";
    String chargestatus = "";
    String terminalid = "";
    String chargeno = "";*/
    Adapter adapter;
    List<String> arrayList = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_layout);
        listView = (ListView) findViewById(R.id.list);
        new HistoryOperator().execute();
    }

    class HistoryOperator extends AsyncTask<Void, Void, String> {
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
                db = new DatabaseHandler(getApplicationContext());

                List<Contact> listname = db.getAllContacts();

                if (listname != null && listname.size() > 0) {
                    username = listname.get(0).get_username();

                }
                URL url = new URL("http://180.151.246.171:8888/satkar/rechargeReportFromApp.htm?username=STPL0000052");
                System.out.println("**********History*****************" + result);
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
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
                System.out.println("Prepaid  ");

                JSONArray jsonArray = new JSONArray(result);

                String[] spinnerArray = new String[jsonArray.length()];
                ArrayList<String> arrayList =new ArrayList<>();
             //   LinkedHashMap<String, String> spinnerMap = new LinkedHashMap<String, String>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    int amount = Integer.parseInt(jsonObject.getString("amount"));
                    String itemdesc = jsonObject.getString("itemdesc");
                   /* int rspcomm = Integer.parseInt(jsonObject.getString("rspcomm").toString());
                    int compcomm = Integer.parseInt(jsonObject.getString("compcomm").toString());
                    int frncomm = Integer.parseInt(jsonObject.getString("frncomm").toString());
                    int distcomm = Integer.parseInt(jsonObject.getString("distcomm").toString());*/
                    String chargestatus = jsonObject.getString("chargestatus");
                    String terminalid = jsonObject.getString("terminalid");
                    String chargeno = jsonObject.getString("chargeno");
                    arrayList.add(result);
                    System.out.println("-------------------" + arrayList);
                }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, arrayList);
                    listView.setAdapter(adapter);

//                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
//                        getApplicationContext(),
//                        android.R.layout.simple_list_item_1,
//                         arrayList);
//                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, arrayList);
//                    listView.setAdapter(adapter);
//                   acadapter = new LinkedHashMapAdapter<String, String>(getApplicationContext(),
//                   android.R.layout.list_content, spinnerMap);
                    // listView=(TextView)findViewById(R.id.list);

                 /* ArrayAdapter adapter=new  ArrayAdapter(getApplicationContext(),R.layout.history_layout,arrayOfUsers);
                  ListView listView=(ListView)findViewById(R.id.list);
                  listView.setAdapter(adapter);*/

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
