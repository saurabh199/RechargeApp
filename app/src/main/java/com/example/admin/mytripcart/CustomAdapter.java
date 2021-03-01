package com.example.admin.mytripcart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter{

     private HistoryActivity context;
    private ArrayList<RechargeReport> rechargeReports;
      private static LayoutInflater inflater=null;
    public CustomAdapter(HistoryActivity context,ArrayList<RechargeReport> rechargeReports) {
        // TODO Auto-generated constructor stub
       this.context=context;
        this.rechargeReports=rechargeReports;
         inflater = ( LayoutInflater )context.
                 getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return rechargeReports.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView tv1,tv2,tv3,tv4,tv5;

    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;       
        rowView = inflater.inflate(R.layout.recharge_custom_view, null);
        holder.tv1=(TextView) rowView.findViewById(R.id.text1);
        holder.tv2=(TextView) rowView.findViewById(R.id.text2);
        holder.tv3=(TextView) rowView.findViewById(R.id.text3);
        holder.tv4=(TextView) rowView.findViewById(R.id.text4);
        holder.tv5=(TextView) rowView.findViewById(R.id.text5);
        RechargeReport rechargeReport=rechargeReports.get(position);
        holder.tv1.setText("amount :   "+rechargeReport.getAmount());
        holder.tv2.setText("itemdesc :   "+rechargeReport.getItemdesc());
        holder.tv3.setText("Chargestatus :   "+rechargeReport.getChargestatus());
        holder.tv4.setText("TerminalId :   "+rechargeReport.getTerminalid());
        holder.tv5.setText("Chargeno :   "+rechargeReport.getChargeno());
        return rowView;
    }

} 