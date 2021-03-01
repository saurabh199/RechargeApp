package com.example.admin.mytripcart;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;
//import com.example.admin.m_pinapp.fragments.PrepaidFragment;
//import com.example.admin.m_pinapp.fragments.DataCardFragment;
//import com.example.admin.m_pinapp.fragments.PostpadFragment;

/**
 * Created by Admin on 12/14/2015.
 */
public class MainFragment extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if(!prefs.getBoolean("firstTime", true)) {
         //   new MobileOperator().execute();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTime", false);
            editor.commit();
        }
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        android.support.v7.app.ActionBar ab =getSupportActionBar();
        ab.setDisplayShowHomeEnabled(true);
        ab.setIcon(R.drawable.mytripcart_inner);

        //  ab.setLogo(R.drawable.mytripcart_inner);
//        ActionBar actionBar =  ((AppCompatActivity)getActivity()).getSupportActionBar();
//

//        actionBar.setDisplayHomeAsUpEnabled(true);
//      //  ActionBar actionBar = ((ActionBarActivity) activity).getSupportActionBar();
    //    setTitle("ABC");
    //    getActionBar().setIcon(R.drawable.mytripcart_inner);
    //    toolbar = (Toolbar) findViewById(R.id.toolbar);

        //setSupportActionBar(toolbar);
//        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
//       actionBar.setIcon(R.drawable.mytripcart_inner);


        //    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
      // actionBar.setDisplayUseLogoEnabled(false);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new PrepaidFragment(), "PREPAID");
        adapter.addFragment(new PostpadFragment(), "POSTPAID");
        adapter.addFragment(new DataCardFragment(), "DATACARD");
        adapter.addFragment(new DTHFragment(), "DTH");
        viewPager.setAdapter(adapter);
    }
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onBackPressed() {
        super.onPause();
    }


 public boolean onCreateOptionsMenu(Menu menu) {
              // Inflate the menu; this adds items to the action bar if it is present.
              getMenuInflater().inflate(R.menu.menu_main, menu);
               return true;
        }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
          int id=item.getItemId();
        if(id==R.id.logout) {
            Intent intent = new Intent(this,SecondActivity.class);
            startActivity(intent);
        }
        if(id==R.id.history)
        {
            Intent intent1=new Intent(this,HistoryActivity.class);
            startActivity(intent1);
        }
       return super.onOptionsItemSelected(item);
    }
}



