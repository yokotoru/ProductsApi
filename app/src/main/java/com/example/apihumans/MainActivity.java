package com.example.apihumans;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Adapter pAdapter;
    private List<mask> ListDrink=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView drinkView=findViewById(R.id.BD);
        pAdapter=new Adapter(MainActivity.this,ListDrink);
        drinkView.setAdapter(pAdapter);
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);




        new GetDrink().execute();
    }
    private class GetDrink extends AsyncTask<Void,Void,String>
    {


        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL ( "https://ngknn.ru:5001/NGKNN/кузнецоваон/api/Products/");
                HttpURLConnection connection=(HttpURLConnection) url.openConnection();

                BufferedReader reader=new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder result=new StringBuilder();
                String line="";

                while ((line=reader.readLine())!=null){
                    result.append(line);
                }
                return result.toString();
            }
            catch (Exception exception)
            {
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                {
                    JSONArray tempArray= new JSONArray(s);
                    for (int i=0;i<tempArray.length();i++)
                    {
                        JSONObject productJson=tempArray.getJSONObject(i);
                        mask tempBar = new mask(
                                productJson.getInt("Id"),
                                productJson.getString("Products"),
                                productJson.getInt("Amount"),
                                productJson.getString("Image")
                        );
                        ListDrink.add(tempBar);
                        pAdapter.notifyDataSetInvalidated();
                    }
                }
            }
            catch (Exception ignored)
            {

            }
        }
    }

    public void Add_bt(View v)
    {
        Intent intent = new Intent(this, Add_timetable.class);
        startActivity(intent);
    }
}