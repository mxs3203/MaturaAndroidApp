package com.matura.drzavna.matura.main_menu_activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.matura.drzavna.matura.R;
import com.matura.drzavna.matura.models.BiologijaPitanje;
import com.matura.drzavna.matura.models.KnjizevnostPitanja;
import com.matura.drzavna.matura.models.Pig_Pitanja;
import com.matura.drzavna.matura.models.PovijestPitanja;
import com.matura.drzavna.matura.support.DatabaseHelper;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;


public class RjesavanjeVrsteIspita extends Activity {

    Context c;
    Realm realm;
    TextView title, pocetna;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_vrste_ispita);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.my_title_bar);


        c = this;

        Realm.init(this);
        realm = DatabaseHelper.resetRealm();

        Intent i = getIntent();
        String table_name = i.getStringExtra("table_name");
        System.out.println("koja bazica vrste ispita: "+table_name);
        setupTitleBar(DatabaseHelper.mapTableNameToPresentationValue(table_name));

        setupLinearLayout(table_name);

    }

    private void setupLinearLayout(final String table_name)
    {
        LinearLayout vrste_ispita = (LinearLayout) findViewById(R.id.vrste_ispita);
        ArrayList<TextView> vrste_ispita_array = DatabaseHelper.getVrsteIspita(this, table_name);

        for(TextView v : vrste_ispita_array) {
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(c, RjesavanjeGodineIspita.class);
                    i.putExtra("table_name", table_name);
                    startActivity(i);
                    finish();
                }
            });
            vrste_ispita.addView(v);
        }
    }



    private void setupTitleBar(String ispit)
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
        pocetna.setText("Predmeti");
        pocetna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        title = (TextView)findViewById(R.id.textViewTitle);
        title.setText(ispit);
    }


}
