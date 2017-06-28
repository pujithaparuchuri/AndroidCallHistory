package com.calllog;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
* This class is related to network related activities like which network to be handled depending on the network like airtel,uninor,vodafone etc...
*/

public class Network extends AppCompatActivity{
    String s2=null;
    public void onCreate(Bundle b)
    {
        super.onCreate(b);
        setContentView(R.layout.network);
        TextView tv=(TextView)findViewById(R.id.TextView01);
        Bundle extras=getIntent().getExtras();
        s2=extras.getString("phone");
        try {
            InputStream is =getResources().openRawResource(R.raw.indiamobilesdata);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String s=null;
            boolean f=false;
            while ((s=br.readLine())!=null) {
                if (s.matches("(.*)" + s2 + "(.*)")) ;
                {
                    f=true;
                    if (s.matches("(.*)Airtel(.*)")) {
                        ImageView image = (ImageView) findViewById(R.id.ImageView01);
                        Bitmap bMap = BitmapFactory.decodeResource(getResources(), R.drawable.airtel);
                        image.setImageBitmap(bMap);
                    }
                    if (s.split(":")[1].equals("Uninor")) {
                        ImageView image = (ImageView) findViewById(R.id.ImageView01);
                        Bitmap bMap = BitmapFactory.decodeResource(getResources(), R.drawable.uninor);
                        image.setImageBitmap(bMap);
                    }
                    if (s.split(":")[1].equals("Vodafone")) {
                        ImageView image = (ImageView) findViewById(R.id.ImageView01);

                        Bitmap bMap = BitmapFactory.decodeResource(getResources(),
                                R.drawable.voda);
                        image.setImageBitmap(bMap);
                    }
                }
            }
            if(!f)
            tv.setText("Sorry Not Found !");

            is.close();
        } catch (Exception e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}