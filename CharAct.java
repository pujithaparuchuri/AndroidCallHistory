
package com.calllog;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Vector;

/**
* This class is used to project data in the pie chart
*/

public class CharAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_char);
        PieGraph pie = (PieGraph) findViewById(R.id.fl);

        float[] data =getIntent().getFloatArrayExtra("data");
        pie.setData(data);


        TextView ic=(TextView)findViewById(R.id.il);
        TextView mc=(TextView)findViewById(R.id.ml);
        TextView oc=(TextView)findViewById(R.id.ol);
        TextView ac=(TextView)findViewById(R.id.al);
           ic.append(String.valueOf((int)data[0]));
        mc.append(String.valueOf((int)data[1]));
        oc.append(String.valueOf((int)data[2]));
        ac.append(String.valueOf((int)(data[0]+data[1]+data[2])));


    }
}
