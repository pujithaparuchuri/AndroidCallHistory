package com.calllog;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Date;
import java.util.List;
import java.util.Vector;
/**
* This class mainly concentrates on the call list,which will be stored in list item
 the history like incoming call,missed call,call duration,dialled call,call type etc.. will be stored in sqlitedatabase and it will be pouplated by retreving from database.
*/

public class Listofcalls extends AppCompatActivity {

    String no;
List<CallItem> ll;
    float data[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listofcalls);
        no=getIntent().getStringExtra("no");
        TextView tv=(TextView)findViewById(R.id.tno);
        tv.setText(no);
        data=new float[3];
        ll=new Vector<CallItem>();
        loadData();
        MyCallCustomAdapter ma=new MyCallCustomAdapter();
        ListView lv=(ListView)findViewById(R.id.lview);
        lv.setAdapter(ma);
    }

    private void loadData() {
        SQLiteDatabase db=openOrCreateDatabase("DatabaseName5",MODE_PRIVATE,null);

        Cursor c = db.rawQuery("SELECT * FROM AndroidLogCallHistory where CallNumber=?", new String[]{no});
        while (c.moveToNext()) {
            CallItem ci=new CallItem();
            ci.setDtime(c.getString(c.getColumnIndex("Time")));
            ci.setDuration(c.getString(c.getColumnIndex("CallDuration")));
            ci.setCallType(c.getString(c.getColumnIndex("CallType")));
            if (ci.getCallType().equals("missed call"))
                data[0]++;
            else
            if (ci.getCallType().equals("incoming call"))
                data[1]++;
            if (ci.getCallType().equals("outgoing call"))
                data[2]++;


            ll.add(ci);
        }
        db.close();

        }

    public void doCall(View v)

    {
        //Intent it=new Intent(Intent.ACTION_DIAL);
        Intent it=new Intent(Intent.ACTION_CALL);
it.setData(Uri.parse("tel:"+no));
        startActivity(it);
    }
    public void doMessage(View v)
    {
        Intent it=new Intent(this,MessageActivity.class);
        it.putExtra("no",no);
        startActivity(it);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
     getMenuInflater().inflate(R.menu.mymenu,menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.graph)
        {
            Intent it=new Intent(this, CharAct.class);
            it.putExtra("data",data);
            startActivity(it);

        }
        else
        {
            Intent it=new Intent(this, Network.class);
            it.putExtra("phone",no.substring(0,2));
            startActivity(it);

        }
        return super.onOptionsItemSelected(item);
    }
    public class MyCallCustomAdapter extends BaseAdapter {

        public int getCount()
        {
            return ll.size();
        }
        public Object getItem(int position) {
            return position;
        }
        public long getItemId(int position) {
            return position;
        }
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            CallItem ci=ll.get(position);
            View row=null;
            if(ci.getCallType().equals("missed call"))
            {
                row = inflater.inflate(R.layout.missedcall, parent, false);
                TextView textLabel = (TextView) row.findViewById(R.id.text);
                Date d=new Date(Long.parseLong(ci.getDtime()));
                textLabel.setText(d.toString()+"\n"+ci.getDuration());
            }
            else if(ci.getCallType().equals("outgoing call"))
            {
                row = inflater.inflate(R.layout.outgoing, parent, false);
                TextView textLabel = (TextView) row.findViewById(R.id.text);
                Date d=new Date(Long.parseLong(ci.getDtime()));
                textLabel.setText(d.toString()+"\n"+ci.getDuration());
            }
            else if(ci.getCallType().equals("incoming call"))
            {
                row = inflater.inflate(R.layout.incoming, parent, false);
                TextView textLabel = (TextView) row.findViewById(R.id.text);
                Date d=new Date(Long.parseLong(ci.getDtime()));
                textLabel.setText(d.toString()+"\n"+ci.getDuration());
            }
            return (row);
        }
    }

}
