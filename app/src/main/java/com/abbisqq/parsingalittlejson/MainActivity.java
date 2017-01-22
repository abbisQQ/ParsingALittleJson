package com.abbisqq.parsingalittlejson;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    Button btn;
    TextView tv;
    ProgressBar bar;
    HttpURLConnection connection;
    URL url;
    BufferedReader reader;
    InputStream stream;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button)findViewById(R.id.btn);
        tv = (TextView)findViewById(R.id.tv);
        bar = (ProgressBar)findViewById(R.id.myBar);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new myTask().execute("https://api.themoviedb.org/3/movie/550?api_key=");
            }
        });

    }



    private class myTask extends AsyncTask<String,String,String>{
        @Override
        protected String doInBackground(String... strings) {

            try {
                url = new URL(strings[0]);
                connection = (HttpURLConnection)url.openConnection();
                connection.connect();

                stream = connection.getInputStream();

                //passing the stream to the reader
                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();


                String line = "";

                while ((line =  reader.readLine())!=null){
                    buffer.append(line);

                }

                return buffer.toString();

            }catch (MalformedURLException e){
                e.printStackTrace();

            } catch (IOException e) {
                e.printStackTrace();

            }finally {
                if(connection!=null) connection.disconnect();
                try {
                    if(reader!=null) reader.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }




            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String results) {
            super.onPostExecute(results);
            btn.setVisibility(View.GONE);
            tv.setVisibility(View.VISIBLE);
            bar.setVisibility(View.INVISIBLE);
            tv.setText(results);
        }

        @Override
        protected void onPreExecute() {
            btn.setVisibility(View.GONE);
            tv.setVisibility(View.INVISIBLE);
            bar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }
    }


}
