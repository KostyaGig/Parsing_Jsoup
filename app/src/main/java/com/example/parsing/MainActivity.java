package com.example.parsing;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private Document docs;
    private Thread tread;
    private Runnable rin;
    private TextView tv;
    private TextView textView3;
    private Elements tbodyOneChildren2;
    private Elements tbodyOneChildren4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.tv);
        textView3 = findViewById(R.id.textView3);
        getTitleSiteAnewThread();

    }


    private void getTitleSiteAnewThread() {
        rin = new Runnable() {
            @Override
            public void run() {
                getWevSite();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv.setText(tbodyOneChildren2.get(1).text());
                        textView3.setText(tbodyOneChildren4.get(1).text());
                    }
                });
            }

        };
        tread = new Thread(rin);
        tread.start();
    }

    private void getWevSite() {
        try {
            docs = Jsoup.connect("https://www.banki.ru/products/currency/cash/smolensk/").get();
            Elements tbody = docs.getElementsByTag("tbody");
            Element tbodyOne = tbody.get(0);
            Elements tbodyChildren = tbodyOne.children();
            Element  tbodyOneChildren = tbodyChildren.get(0);
            Element  tbodyOneChildren3 = tbodyChildren.get(1);
             tbodyOneChildren2 =  tbodyOneChildren.children();
             tbodyOneChildren4 =  tbodyOneChildren3.children();
            Log.d("myTag",tbodyOneChildren2.get(0) + tbodyOneChildren2.get(1).text());
            Log.d("myTag",tbodyOneChildren4.get(1).text());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}