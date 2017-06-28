package com.calllog;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

/**
* The main purpose of this class is to cache the contact name,number,date, type,duration for
all missed calls,dialled calls and incoming calls etc... with the history of call data.
*/

public class CallLog extends Activity {
    SQLiteDatabase mydb = null;
    String TableName = "AndroidLogCallHistory";
    Cursor c1, c2, c3;
    String Name = "", Phone = null, Phone1 = null;
    String duration = null;
    String type = null;
    int count;
    View row;
    int i = 0;
    ListView myList;
    ArrayList<String> l1 = new ArrayList<String>();
    ArrayList<String> l2 = new ArrayList<String>();
    ArrayList<String> ctype = new ArrayList<String>();
    ArrayList<String> nooftimes = new ArrayList<String>();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contactsresult);
        myList = (ListView) findViewById(R.id.mylist);
        myList.setAdapter(new MyCustomAdapter());
        myList.setCacheColorHint(0);
        myList.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int arg2, long arg3) {
                int s = Integer.parseInt(arg0.getItemAtPosition(arg2).toString());
                String s1 = l2.get(s);

                Intent it = new Intent(CallLog.this, Listofcalls.class);
                it.putExtra("no", s1);
                it.putExtra("nooftimes", nooftimes.get(arg2));
                it.putExtra("type", ctype.get(s));
                startActivity(it);
            }
        });
        TabLayout tabs=(TabLayout)findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("Calls").setIcon(android.R.drawable.stat_sys_phone_call));
        tabs.addTab(tabs.newTab().setText("Missed Calls").setIcon(android.R.drawable.sym_call_missed));
        tabs.addTab(tabs.newTab().setText("Received Calls").setIcon(android.R.drawable.sym_call_incoming));
        tabs.addTab(tabs.newTab().setText("Outgoing Calls").setIcon(android.R.drawable.sym_call_outgoing));

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                l1.clear();
                l2.clear();
                ctype.clear();
                nooftimes.clear();
                switch (tab.getPosition()) {
                    case 0: refresh("All Calls");
                        break;
                    case 1: refresh("missed call");
                        break;
                    case 2: refresh("incoming call");
                        break;
                    case 3: refresh("outgoing call");
                        break;
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        try {
            mydb = openOrCreateDatabase("DatabaseName5", MODE_PRIVATE, null);
            mydb.execSQL("CREATE TABLE IF NOT EXISTS "
                    + TableName
                    + " (CallName varchar,CallNumber varchar,CallDuration integer,CallType varchar,Time long);");
            c1 = mydb.rawQuery("SELECT * FROM " + TableName, null);
            boolean b = c1.moveToFirst();
            if (!b) {
                Cursor c = getContentResolver().query
                        (android.provider.CallLog.Calls.CONTENT_URI,
                                null, null, null,
                                android.provider.CallLog.Calls.DATE + " DESC ");
                startManagingCursor(c);
                int name = c.getColumnIndex(android.provider.CallLog.Calls.CACHED_NAME);
                int number = c.getColumnIndex(android.provider.CallLog.Calls.NUMBER);
                int datecolumn = c.getColumnIndex(android.provider.CallLog.Calls.DATE);
                int typeofcall = c.getColumnIndex(android.provider.CallLog.Calls.TYPE);
                int time = c.getColumnIndex(android.provider.CallLog.Calls.DURATION);
                if (c.moveToFirst()) {
                    do {
                        String callname = c.getString(name);
                        String callno = c.getString(number);
                        long created = c.getLong(datecolumn);
                        int duration = c.getInt(time);
                        int type = c.getInt(typeofcall);
                        String calltype = null;
                        switch (type) {
                            case android.provider.CallLog.Calls.MISSED_TYPE:
                                calltype = "missed call";
                                break;
                            case android.provider.CallLog.Calls.INCOMING_TYPE:
                                calltype = "incoming call";
                                break;
                            case android.provider.CallLog.Calls.OUTGOING_TYPE:
                                calltype = "outgoing call";
                                break;
                        }
                        mydb.execSQL("INSERT INTO " + TableName + "(CallName,CallNumber,CallDuration,CallType,Time)" + "VALUES('"
                                + callname + "','" + callno + "'," + duration + ",'" + calltype + "'," + created + ");");
                    } while (c.moveToNext());

                }
            } else {
                Cursor c3 = mydb.rawQuery("SELECT MAX(Time) FROM " + TableName, null);
                if (c3.moveToFirst()) {
                    long time2 = c3.getLong(0);
                    c3.close();
//System.out.println("max time is"+time2);
                    Cursor c = getContentResolver().query
                            (android.provider.CallLog.Calls.CONTENT_URI,
                                    null, null, null,
                                    android.provider.CallLog.Calls.DATE + " DESC ");
                    startManagingCursor(c);
                    int name = c.getColumnIndex(android.provider.CallLog.Calls.CACHED_NAME);
                    int number = c.getColumnIndex(android.provider.CallLog.Calls.NUMBER);
                    int datecolumn = c.getColumnIndex(android.provider.CallLog.Calls.DATE);
                    int typeofcall = c.getColumnIndex(android.provider.CallLog.Calls.TYPE);
                    int time = c.getColumnIndex(android.provider.CallLog.Calls.DURATION);
                    if (c.moveToFirst()) {
                        do {
                            long created2 = c.getLong(datecolumn);
                            if (created2 > time2) {
                                String callname = c.getString(name);
                                String callno = c.getString(number);
//System.out.println("n0 is "+callno);
                                long created = c.getLong(datecolumn);
// System.out.println("value is "+created);
                                int duration = c.getInt(time);
                                int type = c.getInt(typeofcall);
                                String calltype = null;
                                switch (type) {
                                    case android.provider.CallLog.Calls.MISSED_TYPE:
                                        calltype = "missed call";
                                        break;
                                    case android.provider.CallLog.Calls.INCOMING_TYPE:
                                        calltype = "incoming call";
                                        break;
                                    case android.provider.CallLog.Calls.OUTGOING_TYPE:
                                        calltype = "outgoing call";
                                        break;
                                }
                                mydb.execSQL("INSERT INTO " + TableName + "(CallName,CallNumber,CallDuration,CallType,Time)" + "VALUES('" + callname + "','" + callno + "'," + duration + ",'" + calltype + "'," + created + ");");
                            } else {
                                break;
                            }
                        } while (c.moveToNext());
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mydb != null) {
                //mydb.close();
            }
        }
        myList = (ListView) findViewById(R.id.mylist);
        myList.setAdapter(new MyCustomAdapter());
        myList.setCacheColorHint(0);
        myList.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int arg2, long arg3) {
                int s = Integer.parseInt(arg0.getItemAtPosition(arg2).toString());
                String s1 = l2.get(s);

                Intent it = new Intent(CallLog.this, Listofcalls.class);
                it.putExtra("no", s1);
                it.putExtra("nooftimes", nooftimes.get(arg2));
                it.putExtra("type", ctype.get(s));
                startActivity(it);
            }
        });
        refresh("All Calls");
    }


    public void refresh(String pos)
    {
        c2 = mydb.rawQuery("SELECT CallNumber,CallType,MAX(Time),COUNT(CallNumber),CallName FROM " + TableName + " GROUP BY CallNumber,CallType ORDER BY MAX(Time) DESC", null);
        if (c2.moveToFirst()) {
            do {
                long created = c2.getLong(2);
                Date date = new Date(created);
                String date2[] = date.toString().split(" ");
                String i1 = Integer.toString(date.getDate());
                String month = date2[1];
                String calldate = i1 + " " + month;
                String dateString = DateFormat.getDateTimeInstance().format(date);
                String[] dat = dateString.split(" ");
                String dat2 = dat[3];
                String[] tim = dat2.split(":");
                String calltime = "";
                if (dat.length == 5) {
                    calltime = tim[0] + ": " + tim[1] + " " + dat[4];

                } else {
                    if (Integer.parseInt(tim[0]) >= 12) {
                        if (Integer.parseInt(tim[0]) == 12) {
                            calltime = Integer.parseInt(tim[0]) + ": " + tim[1] + " " + "PM";
                        } else {
                            calltime = Integer.parseInt(tim[0]) - 12 + ": " + tim[1] + " " + "PM";
                        }
                    } else {
                        calltime = tim[0] + ": " + tim[1] + " " + "AM";
                    }
                }
                String name = c2.getString(4);
                String no = c2.getString(0) + " (" + c2.getString(3) + ")";
              if(c2.getString(1).equals(pos) || pos.equals("All Calls")) {
                  ctype.add(c2.getString(1));
                  nooftimes.add(c2.getString(3));
                  l2.add(c2.getString(0));
                  if (name.equals("null")) {
                      l1.add(no + "\n" + calldate + " " + calltime);
                  } else {
                      l1.add(c2.getString(4) + " (" + c2.getString(3) + ")" + " \n" + calldate + " " + calltime);
                  }
              }
            } while (c2.moveToNext());

            ((MyCustomAdapter)(myList.getAdapter())).notifyDataSetChanged();
        } else {
            TextView tv = new TextView(this);
            tv.setText("No Call Logs Found");
            tv.setTextSize(20);
        }

    }
    public class MyCustomAdapter extends BaseAdapter {

        public int getCount()
        {
            return l1.size();
        }
        public Object getItem(int position) {
            return position;
        }
        public long getItemId(int position) {
            return position;
        }
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            if(ctype.get(position).equals("missed call"))
            {
                row = inflater.inflate(R.layout.missedcall, parent, false);
                TextView textLabel = (TextView) row.findViewById(R.id.text);
                textLabel.setText(l1.get(position));
            }
            else if(ctype.get(position).equals("outgoing call"))
            {
                row = inflater.inflate(R.layout.outgoing, parent, false);
                TextView textLabel = (TextView) row.findViewById(R.id.text);
                textLabel.setText(l1.get(position));
            }
            else if(ctype.get(position).equals("incoming call"))
            {
                row = inflater.inflate(R.layout.incoming, parent, false);
                TextView textLabel = (TextView) row.findViewById(R.id.text);
                textLabel.setText(l1.get(position));
            }
            return (row);
        }
    }

}
