package com.test.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class SubActivity extends AppCompatActivity {

    Button btn_on;
    Button btn_off;
    Button btn_home;
    Button btn_up;
    Button btn_down;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        btn_on = findViewById(R.id.btn_on);
        btn_off = findViewById(R.id.btn_off);
        btn_home = findViewById(R.id.btn_home);
        btn_up = findViewById(R.id.btn_up);
        btn_down = findViewById(R.id.btn_down);

        /*************ON************/

        btn_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(), "ON으로 변경되었습니다.", Toast.LENGTH_LONG).show();
                UrlConnection("http://10.10.141.66:8080/android/updateData/ON");
            }
        });

        /*************OFF************/

        btn_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(), "OFF으로 변경되었습니다.", Toast.LENGTH_LONG).show();
                UrlConnection("http://10.10.141.66:8080/android/updateData/OFF");
            }
        });

        /*************UP************/

        btn_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(), "1'C 올렸습니다.", Toast.LENGTH_LONG).show();
                UrlConnection("http://10.10.141.66:8080/android/pTemp");
            }
        });

        /*************DOWN************/

        btn_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(), "1'C 내렸습니다.", Toast.LENGTH_LONG).show();
                UrlConnection("http://10.10.141.66:8080/android/mTemp");
            }
        });

        /*************HOME************/

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(SubActivity.this, MainActivity.class);
                startActivity(intent);
                UrlConnection("http://10.10.141.66:8080/android/dataList");
            }
        });
    }

    public void UrlConnection(String url){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String page = url;

                try {
                    URL url = new URL(page);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    StringBuilder stringBuilder = new StringBuilder();

                    if(conn != null){

                        conn.setConnectTimeout(10000);
                        conn.setRequestMethod("GET");
                        conn.setUseCaches(false);

                        if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){

                            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

                            while(true){
                                String line = bufferedReader.readLine();
                                if(line == null) break;
                                stringBuilder.append(line + "\n");
                            }
                            bufferedReader.close();
                        }
                        conn.disconnect();
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } //End of run()

        }); //End of Thread
        thread.start();
    }
}