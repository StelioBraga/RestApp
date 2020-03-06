package com.example.rest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.rest.models.Pais;
import com.example.rest.utils.ExportUtils;
import com.example.rest.utils.PaisAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String LG = "stl";
    public List<Pais> paisList = new ArrayList<>();
    RecyclerView rv_pais;
    Button bt_search;
    FloatingActionButton floating_action_button,floating_export_xml,floating_export_cvs,floating_export_xls;
    EditText te_search;
    Context ctx;
    boolean isFABOpen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // inicialization
        ctx = this;
        rv_pais = findViewById(R.id.rv_pais);
        bt_search = findViewById(R.id.bt_search);
        te_search = findViewById(R.id.te_search);
        floating_export_xml = findViewById(R.id.floating_export_xml);
        floating_export_cvs = findViewById(R.id.floating_export_csv);
        floating_export_xls = findViewById(R.id.floating_export_xls);
        floating_action_button = findViewById(R.id.floating_action_button);


        rv_pais.setLayoutManager(new LinearLayoutManager(this)); // set the list layout style to vertical.
        Log.v(LG, "iniciado.");

        //rv
        PaisAdapter posts = new PaisAdapter(paisList,ctx);
        rv_pais.setAdapter(posts);
        posts.notifyDataSetChanged();

        //search button
        bt_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String countrName  = te_search.getText().toString();
                fetchRestCountries(countrName);
            }
        });

        floating_action_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFABOpen){
                    showFABMenu();
                }else {
                    closeFABMenu();
                }

            }
        });

        //export to xml button
        floating_export_xml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (paisList.size() > 0) {
                    ExportUtils.exportToXML(ctx, paisList);
                } else {
                    Toast.makeText(ctx, "Sem nenhum pais para exportar.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //export to xls button
        floating_export_xls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (paisList.size() > 0) {
                    ExportUtils.exportToXLS(ctx, paisList);
                } else {
                    Toast.makeText(ctx, "Sem nenhum pais para exportar.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //export to csv button
        floating_export_cvs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (paisList.size() > 0) {
                    ExportUtils.exportToCSV(ctx, paisList);
                } else {
                    Toast.makeText(ctx, "Sem nenhum pais para exportar.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //fetch REST
    public void fetchRestCountries(String countryName) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://restcountries.eu/rest/v2/name/" + countryName ;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                int count = response.length();

                for (int i = 0; i < count; i++) {
                    try {
                        JSONObject paisObj = response.getJSONObject(i);
                        String name = paisObj.getString("name");
                        String capital = paisObj.getString("capital");
                        String region = paisObj.getString("region");
                        int population = paisObj.getInt("population");
                        double area = paisObj.getDouble("area");
                        JSONArray timezones = paisObj.getJSONArray("timezones");
                        String nativeName = paisObj.getString("nativeName");
                        String flag = paisObj.getString("flag");
                        String sub_region = paisObj.getString("subregion");

                        Pais pais = new Pais(name, capital, region, population, area, timezones, nativeName, flag, sub_region);
                        paisList.add(pais);
                        Log.v(LG,"Adicionou um pais com o nome: " + pais.getTimezones());

                    } catch (JSONException e) {
                        //
                        Log.v(LG,"Nao encontrou ");
                    }
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v(LG, "Pais não encontrado.");
            }
        });

        queue.add(request);
        queue.start();
    }




    private void showFABMenu() {
        isFABOpen = true;
        int height = floating_export_cvs.getHeight();

        floating_export_cvs.animate().translationY(-height - 4);
        floating_export_xls.animate().translationY(-height - height - 4 - 4);
        floating_export_xml.animate().translationY(-height - height - 4 - height - 4 - 4);
    }

    private void closeFABMenu() {
        isFABOpen = false;
        floating_export_cvs.animate().translationY(0);
        floating_export_xls.animate().translationY(0);
        floating_export_xml.animate().translationY(0);
    }

}
