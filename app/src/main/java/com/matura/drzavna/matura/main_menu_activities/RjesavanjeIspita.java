package com.matura.drzavna.matura.main_menu_activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.matura.drzavna.matura.R;
import com.matura.drzavna.matura.models.Ispit;
import com.matura.drzavna.matura.support.DatabaseHelper;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class RjesavanjeIspita extends Activity {

    TextView title, pocetna;
    ImageView back;
    Context c;
    ArrayList<String> ispiti;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_rjesavanje_ispita);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.my_title_bar);
        c = this;
        setupTitleBar();
        setAllIspiti();
    }

    private void setAllIspiti() {
        Realm.init(this);
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Ispit> pitanja = realm.where(Ispit.class).findAll().sort("id");
        ispiti = new ArrayList<String>();
        for(Ispit pitanje : pitanja) {
            String name = DatabaseHelper.mapTableNameToPresentationValue(pitanje.getIme());
            if(!ispiti.contains(name))
                ispiti.add(name);
        }
        ArrayAdapter<Ispit> adapter = new ArrayAdapter(this, R.layout.jedan_ispit, R.id.textViewIspit, ispiti);
        ListView list = (ListView)findViewById(R.id.svi_ispiti);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println(DatabaseHelper.mapListViewValuesToTableName(ispiti.get(i)));
                Intent intent = new Intent(c,RjesavanjeVrsteIspita.class);
                try{
                    String parts[] = DatabaseHelper.mapListViewValuesToTableName(ispiti.get(i)).split(",");
                    intent.putExtra("table_name", parts[0]);
                }catch (Exception e) {
                    intent.putExtra("table_name", DatabaseHelper.mapListViewValuesToTableName(ispiti.get(i)));
                }
                startActivity(intent);
                finish();
            }
        });


    }

    private void setupTitleBar()
    {
        back = (ImageView) findViewById(R.id.imageViewNavBarIcon1);
        back.setImageResource(R.drawable.nav_bar_arrow);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        pocetna = (TextView)findViewById(R.id.textViewBarBack);
        pocetna.setText("Početna");
        pocetna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        title = (TextView)findViewById(R.id.textViewTitle);
        title.setText("Rješavanje ispita");
    }
}
