package com.test.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private TextView status_json;
    private TextView temp_json;
    Button btn_move;
    Button btn_main_up;
    Button btn_main_down;


    static List<SVDATA> data;

    Handler handler = new Handler();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        status_json = findViewById(R.id.status_json);
        temp_json = findViewById(R.id.temp_json);
        btn_move = findViewById(R.id.btn_move);


        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(MainActivity.this, SubActivity.class);
                startActivity(intent);
            }
        });


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String page = "http://10.10.141.66:8080/android/dataList";

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

                    Gson gson = new Gson();

                    Type type = new TypeToken<List<SVDATA>>() {}.getType();
                    data = gson.fromJson(String.valueOf(stringBuilder),type);

                    String status = data.get(0).getStatus();
                    int temp = data.get(0).getTemp();

                    st_println("보일러 상태 : " + status);

                    tp_println("온도 : " + temp);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } //End of run()

        }); //End of Thread
        thread.start();

    }

    public void st_println(final String data){
        handler.post(new Runnable() {
            @Override
            public void run() {
                status_json.append(data + "\n");
            }
        });

    }

    public void tp_println(final String data){
        handler.post(new Runnable() {
            @Override
            public void run() {
                temp_json.append(data + "\n");
            }
        });

    }


}